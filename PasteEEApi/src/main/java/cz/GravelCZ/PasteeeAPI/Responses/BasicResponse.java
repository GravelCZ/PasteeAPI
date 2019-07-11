package cz.GravelCZ.PasteeeAPI.Responses;

import java.util.List;

public class BasicResponse {

	public boolean success;
	public List<ErrorObject> errors;
	
	public static class ErrorObject {
		
		public int code;
		public String message;
		
	}
	
}
