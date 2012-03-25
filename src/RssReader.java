import java.util.List;
import java.util.HashMap;
import org.eclipse.swt.*;
import org.eclipse.swt.layout.*;
import org.eclipse.swt.widgets.*;
import org.eclipse.swt.browser.*;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.FontData;
import org.eclipse.swt.graphics.Rectangle;

public class RssReader {
	
	static Browser browser;
	static org.eclipse.swt.widgets.List postlist;
	static org.eclipse.swt.widgets.List feedlist;
	static Label posttitle;
	static Label postauthor;
	static Link posturl;
	
	static HashMap<String, List<RssItem>> feeddata;
	static List<RssFeed> feeds;
	static RssParser rssparser;
	
	private static class FeedListSelection implements SelectionListener {
		public void widgetDefaultSelected(SelectionEvent arg0) {
			return;
		}
		
		public void widgetSelected(SelectionEvent arg0) {
			if (feedlist.getSelectionIndex() >= 0) {
				String feedname = feedlist.getItem(feedlist.getSelectionIndex());
				List<RssItem> posts = feeddata.get(feedname);
				postlist.removeAll();
				for (int i = 0;i < posts.size(); i++){
					postlist.add(posts.get(i).title);			
				}
				updatePostArea(posts.get(0));
			}
		}
	}	
	
	private static class PostListSelection implements SelectionListener {
		public void widgetDefaultSelected(SelectionEvent arg0) {
			return;
		}

		public void widgetSelected(SelectionEvent arg0) {
			if (feedlist.getSelectionIndex() >= 0) {
				String feedname = feedlist.getItem(feedlist.getSelectionIndex());
				RssItem post = feeddata.get(feedname).get(postlist.getSelectionIndex());
				updatePostArea(post);
			}
		}
		
	}
	
	
	private static class addFeed implements SelectionListener {
		public void widgetDefaultSelected(SelectionEvent arg0) {
			return;
		}

		public void widgetSelected(SelectionEvent arg0) {
			
		}
	}

	private static void updatePostArea(RssItem post) {
		posttitle.setText(post.title);
		postauthor.setText(post.author);
		posturl.setText(post.link);
		browser.setText(post.body);
	}
	
	public static List<RssItem> getFeedPosts(RssFeed feed) {
		rssparser = new RssParser();
		rssparser.readRss(feed.url);
		List<RssItem> posts = rssparser.doc.getPosts();
		return posts;
	}
	
	public static void main(String[] args) {
		RssFeed rssfeed = new RssFeed("http://topicaltopical.com/feed", "Topical");
		List<RssItem> posts = getFeedPosts(rssfeed);
//		RssItem a_post = posts.get(0);

		RssFeed arsfeed = new RssFeed("http://feeds.arstechnica.com/arstechnica/index?format=xml", "Ars Technica");
		List<RssItem> arsposts = getFeedPosts(arsfeed);
		RssItem a_post = arsposts.get(0);

		feeddata = new HashMap<String, List<RssItem>>();
		feeddata.put(rssfeed.title, posts);
		feeddata.put(arsfeed.title, arsposts);
		
		Display display = new Display();
		final Shell shell = new Shell(display);
		FormLayout shelllayout = new FormLayout();
		shelllayout.marginWidth = 4;
		shelllayout.marginHeight = 4;
		shell.setLayout(shelllayout);
		shell.setText("RSS Reader v0.1");
		
		Button addfeed = new Button(shell, SWT.PUSH | SWT.TOP);
		//addfeed.addSelectionListener(new addFeedListener());
		final FormData addfeed_data = new FormData();
		addfeed_data.left = new FormAttachment(0,0);
		addfeed_data.top = new FormAttachment(0,0);
		addfeed_data.width = shell.getClientArea().width / 4;
		addfeed_data.height = 40;
		addfeed.setLayoutData(addfeed_data);
		addfeed.setText("+ Feed");
		
		feedlist = new org.eclipse.swt.widgets.List(shell, SWT.BORDER | SWT.MULTI | SWT.V_SCROLL);
		final FormData feedlist_data = new FormData();
		feedlist_data.left = new FormAttachment(0,0);
		feedlist_data.top = new FormAttachment(addfeed,4);
		feedlist_data.width = shell.getClientArea().width / 4;
		feedlist_data.height = shell.getClientArea().height / 3;
		feedlist.setLayoutData(feedlist_data);
		feedlist.add("Topical");
		feedlist.add("Ars Technica");
		feedlist.addSelectionListener(new FeedListSelection());


		shell.addListener(SWT.Resize, new Listener() {
			public void handleEvent (Event e) {
				Rectangle area = shell.getClientArea();
				feedlist_data.width = area.width / 4;
				feedlist_data.height = area.height / 3;
				addfeed_data.width = shell.getClientArea().width / 4;				
				shell.layout();
			}
		});
		
		posttitle = new Label(shell, SWT.LEFT);
		posttitle.setText(a_post.title);
		final FormData posttitle_data = new FormData();
		posttitle_data.left = new FormAttachment(feedlist,4);
		posttitle_data.top = new FormAttachment(0,0);
		posttitle_data.right = new FormAttachment(100,0);
		posttitle.setLayoutData(posttitle_data);
		posttitle.setFont(new Font(display, new FontData("",16,SWT.BOLD)));

		postauthor = new Label(shell, SWT.LEFT);
		postauthor.setText(a_post.author);
		final FormData postauthor_data = new FormData();
		postauthor_data.left = new FormAttachment(feedlist,4);
		postauthor_data.top = new FormAttachment(posttitle,4);
		postauthor_data.right = new FormAttachment(100,0);
		postauthor.setLayoutData(postauthor_data);

		posturl = new Link(shell, SWT.LEFT);
		posturl.setText(a_post.link);
		final FormData posturl_data = new FormData();
		posturl_data.left = new FormAttachment(feedlist,4);
		posturl_data.top = new FormAttachment(postauthor,4);
		posturl_data.right = new FormAttachment(100,0);
		posturl.setLayoutData(posturl_data);
		
		try {
			browser = new Browser(shell, SWT.NONE);
			FormData browser_data = new FormData();
			browser_data.left = new FormAttachment(feedlist, 4);
			browser_data.top = new FormAttachment(posturl,4);
			browser_data.right = new FormAttachment(100,0);
			browser_data.bottom = new FormAttachment(100,0);
			browser.setLayoutData(browser_data);
			
		} catch (SWTError e) {
			System.out.println(e.getMessage());
			display.dispose();
			return;
		}

		postlist = new org.eclipse.swt.widgets.List(shell, SWT.BORDER | SWT.MULTI | SWT.V_SCROLL);
		FormData postlist_data = new FormData();
		postlist_data.left = new FormAttachment(0,0);
		postlist_data.top = new FormAttachment(feedlist, 4);
		postlist_data.right = new FormAttachment(browser, -4);
		postlist_data.bottom = new FormAttachment(100,0);
		postlist.setLayoutData(postlist_data);
		postlist.addSelectionListener(new PostListSelection());
		for (int i = 0;i < arsposts.size(); i++){
			postlist.add(arsposts.get(i).title);			
		}

		browser.setText(a_post.body);
		feedlist.setSelection(0);
		postlist.setSelection(0);

//		shell.pack();
		shell.open();

		while (!shell.isDisposed()) {
			if (!display.readAndDispatch())
				display.sleep();
		}
		
		display.dispose();
	}
}

