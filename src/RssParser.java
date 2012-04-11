import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.apache.commons.io.IOUtils;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class RssParser {

	RssDocument doc;
	
	public RssParser(){
	}

	public static void main(String[] args) {
		
		RssParser myrss = new RssParser();
		myrss.readRss("rss.xml");
		myrss.doc.printPosts();
//		System.out.println(myrss.doc.supportsDC());
	}
	
	public void readRss(String filename) {
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		
		try {
			DocumentBuilder db = dbf.newDocumentBuilder();
			String rss_data = Download.getCleanFeedData(filename);
			if (!rss_data.isEmpty()) {
				Document dom = db.parse(IOUtils.toInputStream(rss_data));
				doc = new RssDocument(dom);
			}
		} catch (IOException e) {
			e.printStackTrace();
		} catch (SAXException e) {
			e.printStackTrace();
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		}
	}
	

	
}
