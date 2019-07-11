package cz.GravelCZ.PasteeeAPI.Utils;

import java.util.HashMap;
import java.util.Map;

public class HeaderUtil {

	public static Map<String, String> authTokenWithJSONContent(String key) {
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("X-Auth-Token", key);
		map.put("Content-Type", "application/json");
		return map;
	}

	public static Map<String, String> authToken(String key) {
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("X-Auth-Token", key);
		return map;
	}

}
