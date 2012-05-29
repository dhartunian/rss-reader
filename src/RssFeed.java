import java.util.ArrayList;

import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import com.mongodb.DBObject;
import com.mongodb.DBCursor;

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
	
	public static ArrayList<RssFeed> getAllFeeds() {
		
		ArrayList<RssFeed> feedlist = new ArrayList<RssFeed>();
		RssDB.initConnection();
		DBCollection feeds = RssDB.db.getCollection("feeds");
		DBCursor feeds_cursor = feeds.find();
		DBObject feed;
		while (feeds_cursor.hasNext()) {
			feed = feeds_cursor.next();
			RssFeed newfeed = new RssFeed(feed.get("url").toString());
			feedlist.add(newfeed);
		}
		feeds_cursor.close();
		return feedlist;
	}
	
	public static void addFeed(RssFeed feed) {
		RssDB.initConnection();
		DBCollection feeds = RssDB.db.getCollection("feeds");
		BasicDBObject newfeed = new BasicDBObject();
		newfeed.put("url", feed.getUrl());
		feeds.insert(newfeed);
	}

}
