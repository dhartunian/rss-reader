public class RssItem {
			
	public String title;
	public String body;
	public String date;
	public String link;

	public RssItem(String title,String body,String link, String date) {
		this.title = title;
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
