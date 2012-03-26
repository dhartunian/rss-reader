public class RssItem {
			
	private String title;
	private String author;
	private String body;
	private String date;
	private String link;

	public RssItem(String title,String author, String body,String link, String date) {
		this.title = title;
		this.author = author;
		this.body = body;
		this.date = date;
		this.link = link;
	}	
	
	public String getTitle() {
		if (title != null)
			return title;
		else
			return "";
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getAuthor() {
		if (author != null)
			return author;
		else
			return "";
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public String getBody() {
		if (body != null)
			return body;
		else
			return "";
	}

	public void setBody(String body) {
		this.body = body;
	}

	public String getDate() {
		if (date != null)
			return date;
		else 
			return "";
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getLink() {
		if (link != null)
			return link;
		else
			return "";
	}

	public void setLink(String link) {
		this.link = link;
	}

	public String toString() {
		String rep = "";
		rep = rep + "Title:" + this.title + " date:" + this.date + "\n" + this.link;
		return rep;
	}
}
