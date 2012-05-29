import java.util.ArrayList;
import java.util.List;
import java.util.HashMap;

public class RssReader {

	static RssUserInterface ui;
	static HashMap<String, List<RssItem>> feeddata;

	public static void downloadAndAddFeed(String url) {
		RssParser rssparser = new RssParser();
		rssparser.readRss(url);
		List<RssItem> posts = rssparser.doc.getPosts();
		feeddata.put(rssparser.doc.title, posts);
		ui.addFeed(new RssFeed(url, rssparser.doc.title));
	}
	

	public static void main(String[] args) {
		ui = new RssUserInterface();
		Thread ui_thread = new Thread(ui);
		ui_thread.start();
		
		feeddata = new HashMap<String, List<RssItem>>();

		ArrayList<RssFeed> feedlist = RssFeed.getAllFeeds();
		for (RssFeed feed : feedlist) {
			downloadAndAddFeed(feed.getUrl());
		}
		
	}
}

