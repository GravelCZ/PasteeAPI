package cz.GravelCZ.PasteeeAPI.Utils.Json;

import java.lang.reflect.Type;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import cz.GravelCZ.PasteeeAPI.Utils.PasteSection;

public class PasteSectionSerializer implements JsonSerializer<PasteSection> {

	@Override
	public JsonElement serialize(PasteSection src, Type typeOfSrc, JsonSerializationContext context) {
		if (src.contents == null || src.contents == "") {
			return null;
		}
		
		JsonObject obj = new JsonObject();
		if (src.name != null && src.name != "") {
			obj.addProperty("name", src.name);
		}
		
		if (src.syntax != null && src.syntax != "") {
			obj.addProperty("syntax", src.syntax);
		}
		
		obj.addProperty("contents", src.contents);
		
		return obj;
	}

}
