package cz.GravelCZ.PasteeeAPI.Responses;

import java.util.List;

public class ListPasteResponse {

	private int total;
	private int per_page;
	private int current_page;
	private int last_page;
	private String next_page_url;
	private String prev_page_url;
	private int from;
	private int to;

	private List<Paste> data;

	public int getTotal() {
		return total;
	}

	public int getPerPage() {
		return per_page;
	}

	public int getCurrentPage() {
		return current_page;
	}

	public int getLastPage() {
		return last_page;
	}

	public String getNextPageUrl() {
		return next_page_url;
	}

	public String getPrevPageUrl() {
		return prev_page_url;
	}

	public int getFrom() {
		return from;
	}

	public int getTo() {
		return to;
	}

	public List<Paste> getData() {
		return data;
	}

	public static class Paste {

		private String id;
		private String description;
		private int views;
		private String created_at;
		private List<Section> sections;

		public String getId() {
			return id;
		}

		public String getDescription() {
			return description;
		}

		public int getViews() {
			return views;
		}

		public String getCreatedAt() {
			return created_at;
		}

		public List<Section> getSections() {
			return sections;
		}

		public static class Section {

			private String string;
			private String syntax;
			private String contents;

			public String getString() {
				return string;
			}

			public String getSyntax() {
				return syntax;
			}

			public String getContents() {
				return contents;
			}

		}

	}

}
