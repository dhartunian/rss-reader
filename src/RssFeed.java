import java.net.UnknownHostException;
import java.util.ArrayList;
import com.mongodb.Mongo;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBObject;
import com.mongodb.DBCursor;

public class RssFeed {

	public String url;
	
	public RssFeed(String url) {
		this.url = url;
	}
	
	public static ArrayList<RssFeed> getFeedsFromDb() {
		ArrayList<RssFeed> feedlist = new ArrayList<RssFeed>();
		try {
			Mongo m = new Mongo("localhost");
			DB db = m.getDB("rssreader");
			DBCollection feeds = db.getCollection("feeds");
			DBCursor feeds_cursor = feeds.find();
			DBObject feed;
			while (feeds_cursor.hasNext()) {
				feed = feeds_cursor.next();
				RssFeed newfeed = new RssFeed(feed.get("url").toString());
				feedlist.add(newfeed);
			}
			feeds_cursor.close();
			m.close();
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
		return feedlist;
	}

}
