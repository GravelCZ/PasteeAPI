package cz.GravelCZ.PasteeeAPI.Utils;

public class PasteSection {

	public String name, syntax, contents;

	/**
	 * used only for GSON DO NOT USE unless you are going to use the set methods afterwards
	 */
	public PasteSection() {}
	
	/**
	 * 
	 * @param name - the name of the paste
	 * @param syntax - the syntax to use, use null for autodetect
	 * @param contents - the content, cannot be empty or null
	 */
	public PasteSection(String name, String syntax, String contents) {
		this.name = name;
		if (syntax != null) {
			this.syntax = syntax;	
		} else {
			this.syntax = "autodetect";
		}
		if (contents == null || contents == "") {
			throw new IllegalArgumentException("contents cannot be null or empty");
		}
		this.contents = contents;
	}
	
	public void setName(String name) {
		this.name = name;
	}

	public void setSyntax(String syntax) {
		this.syntax = syntax;
	}

	public void setContents(String contents) {
		this.contents = contents;
	}

}
