import java.util.ArrayList;


public interface RssDB {

	public ArrayList<RssFeed> getAllFeeds();
	public void addFeed(RssFeed feed);
//	public void addPost(RssFeed feed, RssItem post);
	
	
}
