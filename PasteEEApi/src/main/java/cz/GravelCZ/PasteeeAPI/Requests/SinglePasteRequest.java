package cz.GravelCZ.PasteeeAPI.Requests;

import java.util.List;

import cz.GravelCZ.PasteeeAPI.Utils.PasteSection;

public class SinglePasteRequest {

	public boolean encrypted;
	public String description;
	public List<PasteSection> sections;

	public void setEncrypted(boolean encrypted) {
		this.encrypted = encrypted;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public void setSections(List<PasteSection> sections) {
		this.sections = sections;
	}

}
