public class RssFeed {

	private String url;
	private String name;
	
	public RssFeed(String url) {
		this.url = url;
	}

	public RssFeed(String url, String name) {
		this.url = url;
		this.name = name;
	}
	
	public String getName() {
		return this.name;
	}
	
	public String getUrl() {
		return this.url;
	}

}
