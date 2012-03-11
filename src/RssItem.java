public class RssItem {
			
	public String title;
	public String author;
	public String body;
	public String date;
	public String link;

	public RssItem(String title,String author, String body,String link, String date) {
		this.title = title;
		this.author = author;
		this.body = body;
		this.date = date;
		this.link = link;
	}	
	
	public String toString() {
		String rep = "";
		rep = rep + "Title:" + this.title + " date:" + this.date + "\n" + this.link;
		return rep;
	}
}
