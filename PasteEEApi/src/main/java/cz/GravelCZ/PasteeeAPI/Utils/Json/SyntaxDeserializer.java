package cz.GravelCZ.PasteeeAPI.Utils.Json;

import java.lang.reflect.Type;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

import cz.GravelCZ.PasteeeAPI.Utils.Syntax;

public class SyntaxDeserializer implements JsonDeserializer<Syntax> {

	public Syntax deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
		JsonObject obj = json.getAsJsonObject();
		Syntax s = new Syntax();
		s.id = obj.get("id").getAsString();
		s.shortName = obj.get("short").getAsString();
		s.name = obj.get("name").getAsString();
		return s;
	}
	
}
