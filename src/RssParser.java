import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class RssParser {

	List<Post> posts;
	Document dom;

	private class Post {
	
		public String title;
		public String body;
		public String date;
		
		
		public Post(String title,String body,String date) {
			this.title = title;
			this.body = body;
			this.date = date;
		}	

		public String toString() {
			String rep = "";
			rep = rep + "Title:" + this.title + " date:" + this.date;
			return rep;
		}
	}
	
	public RssParser(){
		//create a list to hold the employee objects
		posts = new ArrayList<Post>();
	}

	public static void main(String[] args) {
		
		RssParser myrss = new RssParser();
		
		myrss.readRss("rss.xml");
		myrss.parseRss();
		myrss.printPosts();
	}
	
	public void readRss(String filename) {
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		
		try {
			DocumentBuilder db = dbf.newDocumentBuilder();
			dom = db.parse(filename);
		} catch (IOException e) {
			e.printStackTrace();
		} catch (SAXException e) {
			e.printStackTrace();
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		}
	}
	
	public void parseRss() {
		Element root = dom.getDocumentElement();
		NodeList entries = root.getElementsByTagName("item");
		for (int i = 0; i < entries.getLength(); i++) {
			Element entry = (Element)entries.item(i);
			Post post = createPost(entry);
			posts.add(post);
		}
	}

	public Post createPost(Element entry) {
		String title = getTextInside(entry, "title");
		String date = getTextInside(entry, "pubDate");
		String content = getTextInside(entry, "content:encoded");
		Post post = new Post(title, content, date);
		return post;
	}

	public String getTextInside(Element entry, String tag) {
		String text_value = null;
		NodeList inside = entry.getElementsByTagName(tag);
		if (inside != null && inside.getLength() > 0) {
			text_value = inside.item(0).getFirstChild().getNodeValue(); 
		}
		return text_value;
	}
	
	public void printPosts() {
		for (Post p : posts) {
			System.out.println(p.toString());
			System.out.println(p.body);
		}
	}
	
}
