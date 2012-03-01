import javax.swing.*;
import java.awt.*;
import java.net.*;
import java.util.List;

public class RssReader extends JFrame {
	
	protected JEditorPane postPane;
	
	public RssReader(RssItem post) {
		super("Rss Reader");
		createGUI(post);
	}
	
	protected void createGUI(RssItem post) {
		Container content = getContentPane();
		content.setLayout(new BorderLayout());
		
		postPane = new JEditorPane();
		postPane.setEditable(false);
		postPane.setContentType("text/html");
		content.add(new JScrollPane(postPane), BorderLayout.CENTER);
		
		openURL(post);
		
		setSize(500, 600);
		setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
	}

	protected void openURL(RssItem post) {
		try {
			postPane.setText(post.body);	
		} catch (Exception e) {
			System.out.println("Couldn't Open:" + post.title);
		}
	}
	
	public static void main(String[] args) {
		RssParser myrss = new RssParser();
		myrss.readRss("rss.xml");
		List<RssItem> posts = myrss.doc.getPosts();
		RssItem a_post = posts.get(4);
		new RssReader(a_post).setVisible(true);
	}
}

