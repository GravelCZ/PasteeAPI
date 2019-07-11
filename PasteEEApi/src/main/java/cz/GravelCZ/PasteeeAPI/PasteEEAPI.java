package cz.GravelCZ.PasteeeAPI;

import java.util.List;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import cz.GravelCZ.PasteeeAPI.Requests.SinglePasteRequest;
import cz.GravelCZ.PasteeeAPI.Requests.UserAuthenticationRequest;
import cz.GravelCZ.PasteeeAPI.Responses.BasicResponse;
import cz.GravelCZ.PasteeeAPI.Responses.BasicResponse.ErrorObject;
import cz.GravelCZ.PasteeeAPI.Responses.GetSyntaxResponse;
import cz.GravelCZ.PasteeeAPI.Responses.ListPasteResponse;
import cz.GravelCZ.PasteeeAPI.Responses.ListSyntaxesResponse;
import cz.GravelCZ.PasteeeAPI.Responses.PasteResponse;
import cz.GravelCZ.PasteeeAPI.Responses.UserAuthenticationResponse;
import cz.GravelCZ.PasteeeAPI.Utils.Endpoints;
import cz.GravelCZ.PasteeeAPI.Utils.HeaderUtil;
import cz.GravelCZ.PasteeeAPI.Utils.HttpsUtil;
import cz.GravelCZ.PasteeeAPI.Utils.Pair;
import cz.GravelCZ.PasteeeAPI.Utils.PasteSection;
import cz.GravelCZ.PasteeeAPI.Utils.Syntax;
import cz.GravelCZ.PasteeeAPI.Utils.Triple;
import cz.GravelCZ.PasteeeAPI.Utils.Json.PasteSectionSerializer;
import cz.GravelCZ.PasteeeAPI.Utils.Json.SyntaxDeserializer;

public class PasteEEAPI {

	private String key;
	private boolean usingAsUser = false;
	
	private boolean debug = false;
	
	private Gson gson = new GsonBuilder()
			.disableHtmlEscaping()
			.setPrettyPrinting()
			.registerTypeAdapter(Syntax.class, new SyntaxDeserializer())
			.registerTypeAdapter(PasteSection.class, new PasteSectionSerializer())
			.create();
	
	/**
	 * Inits the API with this key get one at https://paste.ee/account/api
	 * @param key - your key
	 */
	public void init(String key) {
		if (key == null || key == "") {
			throw new IllegalArgumentException("key must not be empty or null");
		}
		this.key = key;
	}
	
	public void debug() {
		debug = true;
	}
	
	private boolean checkKey() {
		return key == null || key == "";
	}
	
	/**
	 * Logs you in and uses the API as your user account instead of normal application
	 * @param name - you username in lowercase
	 * @param password - your password
	 */
	public void useAsUser(String name, String password) {
		if (usingAsUser) {
			throw new IllegalStateException("Already using the API as user.");
		}
		usingAsUser = true;
		getUserKey(name, password);
	}

	private void getUserKey(String name, String password) {
		UserAuthenticationRequest req = new UserAuthenticationRequest();
		req.setPassword(password);
		req.setUsername(name.toLowerCase());
		Triple<Boolean, Integer, String> response = HttpsUtil.POST(Endpoints.USER_AUTH_ENDPOINT, HeaderUtil.authTokenWithJSONContent(key), gson.toJson(req).getBytes());
		if (debug) {
			System.out.println("Error: " + response.first + " code: " + response.second + " response: " + response.third);
		}
		if (!response.first) {
			System.out.println("Using the Paste.ee API as user: " + name);
		}
		UserAuthenticationResponse responseJson = gson.fromJson(response.third, UserAuthenticationResponse.class);
		if (responseJson.success) {
			this.key = responseJson.key;
		} else {	
			System.err.println("Error occured somewhere");
			if (!responseJson.errors.isEmpty()) {
				for (ErrorObject s : responseJson.errors) {
					System.err.println("Error: " + s.code + ": " + s.message);
				}
			}
		}
	}
	/**
	 * This returns the Response from the API
	 * ONLY WORKS WITH USER ENABLED API 
	 * @see useAsUser()
	 * @return - the response
	 */
	public ListPasteResponse listPastes() {
		if (!usingAsUser) {
			throw new IllegalAccessError("listPages() only works with user enabled API, use useAsUser()");
		}
		if (checkKey()) {
			throw new IllegalStateException("the api instance does not have a key set, use init()");
		}
		Triple<Boolean, Integer, String> response = HttpsUtil.GET(Endpoints.LIST_SUBMIT_PASTE_ENDPOINT, HeaderUtil.authToken(key));
		if (debug) {
			System.out.println("Error: " + response.first + " code: " + response.second + " response: " + response.third);
		}
		if (response.first) {
			BasicResponse br = gson.fromJson(response.third, BasicResponse.class);
			if (!br.errors.isEmpty()) {
				for (ErrorObject s : br.errors) {
					System.err.println("Error: " + s.code + ": " + s.message);
				}
			}
			return null;
		}
		return gson.fromJson(response.third, ListPasteResponse.class);
		
	}
	
	/**
	 * Submits the paste, supports multple paste section
	 * @param encrypted
	 * @param description
	 * @param sections
	 * @return Triple 1. submit succes(true/false) 2. id 3. the full url 2. and 3. will be null if submit was not succesfull
	 * @throws IllegalArgumentException if sections > 10
	 */
	public Triple<Boolean, String, String> submitPaste(boolean encrypted, String description, List<PasteSection> sections) {
		if (sections == null) {
			throw new IllegalArgumentException("sections cannot be null");
		}
		if (sections.size() > 10) {
			throw new IllegalArgumentException("sections cannot be longer then 10");
		}
		if (checkKey()) {
			throw new IllegalStateException("the api instance does not have a key set, use init()");
		}
		SinglePasteRequest req = new SinglePasteRequest();
		if (description != null && description != "") {
			req.setDescription(description);	
		}
		req.setEncrypted(encrypted);
		req.setSections(sections);
		Triple<Boolean, Integer, String> response = HttpsUtil.POST(Endpoints.LIST_SUBMIT_PASTE_ENDPOINT, HeaderUtil.authTokenWithJSONContent(key), gson.toJson(req).getBytes());
		if (debug) {
			System.out.println("Error: " + response.first + " code: " + response.second + " response: " + response.third);
		}
		if (response.first) {
			BasicResponse br = gson.fromJson(response.third, BasicResponse.class);
			if (!br.errors.isEmpty()) {
				for (ErrorObject s : br.errors) {
					System.err.println("Error: " + s.code + ": " + s.message);
				}
			}
			return Triple.create(false, null, null);
		}
		PasteResponse responseJson = gson.fromJson(response.third, PasteResponse.class);
		return Triple.create(true, responseJson.id, responseJson.link);
	}
	
	public void getPaste(String id) {
		throw new UnsupportedOperationException("Not yet implemented, i have not been able to make this work. Open a issue if you know how");
	}
	
	public void deletePaste(String id) {
		throw new UnsupportedOperationException("Not yet implemented, i have not been able to make this work. Open a issue if you know how");
	}
	
	/**
	 * Lists all the syntaxes
	 * @return returns list of syntax objects
	 * @see Syntax
	 */
	public Pair<Boolean, List<Syntax>> getSyntaxes() {
		if (checkKey()) {
			throw new IllegalStateException("the api instance does not have a key set, use init()");
		}
		Triple<Boolean, Integer, String> response = HttpsUtil.GET(Endpoints.LIST_SYNTAXES_ENDPOINT, HeaderUtil.authToken(key));
		if (response.first) {
			BasicResponse br = gson.fromJson(response.third, BasicResponse.class);
			if (!br.errors.isEmpty()) {
				for (ErrorObject s : br.errors) {
					System.err.println("Error: " + s.code + ": " + s.message);
				}
			}
			return Pair.create(false, null);
		}
		ListSyntaxesResponse r = gson.fromJson(response.third, ListSyntaxesResponse.class);
		return Pair.create(true, r.syntaxes);
	}
	
	/**
	 * Gets syntax from the id
	 * @return returns syntax object
	 * @see Syntax
	 */
	public Pair<Boolean, Syntax> getSyntax(String id) {
		if (checkKey()) {
			throw new IllegalStateException("the api instance does not have a key set, use init()");
		}
		Triple<Boolean, Integer, String> response = HttpsUtil.GET(Endpoints.GET_SYNTAX_ENDPOINT.replaceFirst("<id>", id), HeaderUtil.authToken(key));
		if (response.first) {
			BasicResponse br = gson.fromJson(response.third, BasicResponse.class);
			if (!br.errors.isEmpty()) {
				for (ErrorObject s : br.errors) {
					System.err.println("Error: " + s.code + ": " + s.message);
				}
			}
			return Pair.create(false, null);
		}
		GetSyntaxResponse r = gson.fromJson(response.third, GetSyntaxResponse.class);
		return Pair.create(true, r.syntax);
	}
}
