import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class RssReader {

	static RssUserInterface ui;
	static RssDB db;
	static HashMap<String, List<RssItem>> feeddata;

	public static void downloadAndAddFeed(String url) {
		RssParser rssparser = new RssParser();
		rssparser.readRss(url);
		List<RssItem> posts = rssparser.doc.getPosts();
		
		RssFeed feed = new RssFeed(url, rssparser.doc.title);
		feeddata.put(feed.getName(), posts);
	}	
	
	public static void main(String[] args) {
		db = new RssDBMongo();
		feeddata = new HashMap<String, List<RssItem>>();

		RssDB db = new RssDBMongo();
		
		ArrayList<RssFeed> feedlist = db.getAllFeeds();
		for (RssFeed feed : feedlist) {
			downloadAndAddFeed(feed.getUrl());
		}				

		ui = new RssUserInterface(feeddata);
		
		Thread ui_thread = new Thread(ui);
		ui_thread.start();
		
	}
	
}