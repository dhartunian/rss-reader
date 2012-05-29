import java.net.UnknownHostException;

import com.mongodb.DB;
import com.mongodb.Mongo;


public class RssDB {
	public static Mongo m;
	public static DB db;
	
	public static void initConnection() {
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
