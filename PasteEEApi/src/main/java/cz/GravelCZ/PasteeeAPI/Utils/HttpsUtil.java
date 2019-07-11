package cz.GravelCZ.PasteeeAPI.Utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.Map;
import java.util.Map.Entry;

import javax.net.ssl.HttpsURLConnection;

public class HttpsUtil {

	public static Triple<Boolean, Integer, String> GET(String url, Map<String, String> headers) {
		return request("GET", url, headers, null);
	}
	
	public static Triple<Boolean, Integer, String> POST(String url, Map<String, String> headers, byte[] body) {
		return request("POST", url, headers, body);
	}
	
	public static Triple<Boolean, Integer, String> request(String method, String url, Map<String, String> headers, byte[] body) {
		try {
			HttpsURLConnection conn = (HttpsURLConnection) new URL(url).openConnection();
			conn.setRequestMethod(method.toUpperCase());
			
			for (Entry<String, String> s : headers.entrySet()) {
				conn.addRequestProperty(s.getKey(), s.getValue());
			}
			
			if (method.equalsIgnoreCase("post") && body != null && body.length != 0) {
				conn.setDoOutput(true);
				conn.getOutputStream().write(body);
			}
			
			int code = conn.getResponseCode();
			String response = null;
			boolean error;
			if (code >= 200 && code < 300) {
				error = false;
				response = readAll(conn.getInputStream());
			} else {
				error = true;
				response = readAll(conn.getErrorStream());
			}
			return Triple.create(error, code, response);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	private static String readAll(InputStream is) throws IOException {
		if (is == null) {
			return null;
		}
		BufferedReader br = new BufferedReader(new InputStreamReader(is));
		String line;
		StringBuffer sb = new StringBuffer();
		while ((line = br.readLine()) != null) {
			sb.append(line);
		}
		return sb.toString();
	}
	
}
