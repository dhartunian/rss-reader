import java.net.UnknownHostException;
import java.util.ArrayList;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.Mongo;

public class RssDBMongo implements RssDB{
	public static Mongo m;
	public static DB db;
	
	public ArrayList<RssFeed> getAllFeeds() {
		ArrayList<RssFeed> feedlist = new ArrayList<RssFeed>();
		initConnection();
		DBCollection feeds = db.getCollection("feeds");
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
	
	public void addFeed(RssFeed feed) {
		initConnection();
		DBCollection feeds = db.getCollection("feeds");
		BasicDBObject newfeed = new BasicDBObject();
		newfeed.put("url", feed.getUrl());
		feeds.insert(newfeed);
	}
	
	public RssDBMongo() {
		initConnection();
	}
	
	private static void initConnection() {
		try {		
			if (m == null) {
				m = new Mongo("localhost");
			}
			if (db == null) {
				db = m.getDB("rssreader");				
			}
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
	}
	
}
