import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import org.apache.commons.io.IOUtils;

public class Download {

	public static String getCleanFeedData(String url) {
		try {
			InputStream in = new URL(url).openStream();
			try {
				String rss_data = IOUtils.toString(in);
				rss_data = rss_data.replace('\u000c', ' ');
				rss_data = rss_data.replace('\u000b', ' ');
				rss_data = rss_data.replace('\u000e', ' ');
				rss_data = rss_data.replace('\u000f', ' ');
				return rss_data;
			} finally {
				IOUtils.closeQuietly(in);
			}
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return "";
	}
}
