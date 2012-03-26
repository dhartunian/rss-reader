import java.util.List;
import java.util.ArrayList;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

public class RssDocument {

	private Document dom;
	private List<RssItem> posts;
	public String title;

	public RssDocument(Document dom) {
		this.dom = dom;
		this.posts = new ArrayList<RssItem>();
		parseRss();
	}
	
	public void parseRss() {
		Element root = dom.getDocumentElement();
		NodeList titlenode = root.getElementsByTagName("title");
		title = titlenode.item(0).getTextContent();
		NodeList entries = root.getElementsByTagName("item");
		for (int i = 0; i < entries.getLength(); i++) {
			Element entry = (Element)entries.item(i);
			RssItem post = createPost(entry);
			posts.add(post);
		}
	}

	public RssItem createPost(Element entry) {
		String title = getTextInside(entry, "title");
		String date = getTextInside(entry, "pubDate");
		String content = "";
		if (getTextInside(entry, "description") != null) {
			content = getTextInside(entry, "description");
		}
		if (getTextInside(entry, "content:encoded") != null) { 
			content = getTextInside(entry, "content:encoded");
		}
		String link = getTextInside(entry, "link");
		String author = null;
		if (this.supportsDC()) {
			author = getTextInside(entry, "dc:creator");
		} else {
			author = getTextInside(entry, "author");
		}
		RssItem post = new RssItem(title, author, content, link, date);
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
		System.out.println(getTitle());
		System.out.println("-----------------------");
		for (RssItem p : posts) {
			System.out.println(p.toString());
			System.out.println(p.getBody());
			System.out.println("");
			System.out.println("");
			System.out.println("");
		}
	}	
	
	public String getTitle() {
		Element root = dom.getDocumentElement();
		NodeList channel = root.getElementsByTagName("channel");		
		String title = getTextInside((Element)channel.item(0), "title");
		return title;
	}
	
	private NodeList getNodeListByName(String name) {
		Element root = dom.getDocumentElement();
		NodeList nl = root.getElementsByTagName(name);
		if (nl != null && nl.getLength() > 0) return nl;
		else return null;
	}
	
	private NodeList getRDF() {
		return getNodeListByName("rdf");
	}
	
	private NodeList getRSS() {
		return getNodeListByName("rss");
	}
	
	public boolean supportsDC() {
		return supportsAttr("xmlns:dc");
	}
	
	private boolean supportsAttr(String attribute){
		Element root = dom.getDocumentElement();
		String attr = root.getAttribute(attribute);
		if (attr.isEmpty())
			return false;
		else 
			return true;		
	}
	
	public boolean supportsContent() {
		return supportsAttr("xmlns:content");
	}
	
	
	public List<RssItem> getPosts() {
		return posts;
	}

}
