import java.util.List;
import java.util.HashMap;
import org.eclipse.swt.*;
import org.eclipse.swt.layout.*;
import org.eclipse.swt.widgets.*;
import org.eclipse.swt.browser.*;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Rectangle;

public class RssReader {
	
	static Browser browser;
	static org.eclipse.swt.widgets.List postlist;
	static org.eclipse.swt.widgets.List feedlist;
	
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
				browser.setText(posts.get(0).body);
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
				browser.setText(post.body);
			}
		}
		
	}	

	public static List<RssItem> getFeedPosts(RssFeed feed) {
		rssparser = new RssParser();
		rssparser.readRss(feed.url);
		List<RssItem> posts = rssparser.doc.getPosts();
		return posts;
	}
	
	public static void main(String[] args) {
		RssFeed rssfeed = new RssFeed("rss.xml", "Topical");
		List<RssItem> posts = getFeedPosts(rssfeed);
		RssItem a_post = posts.get(0);

		RssFeed arsfeed = new RssFeed("ars.xml", "Ars Technica");
		List<RssItem> arsposts = getFeedPosts(arsfeed);

		feeddata = new HashMap<String, List<RssItem>>();
		feeddata.put(rssfeed.title, posts);
		feeddata.put(arsfeed.title, arsposts);
		
		Display display = new Display();
		final Shell shell = new Shell(display);
		shell.setLayout(new FormLayout());
		shell.setText("RSS Reader v0.1");
		
		feedlist = new org.eclipse.swt.widgets.List(shell, SWT.BORDER | SWT.MULTI | SWT.V_SCROLL);
		final FormData feedlist_data = new FormData();
		feedlist_data.left = new FormAttachment(0,0);
		feedlist_data.top = new FormAttachment(0,0);
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
				shell.layout();
			}
		});
		
		
//		final Label title = new Label(shell, SWT.LEFT);
//		title.setText(a_post.title);
////		title.setLayoutData(new GridData(SWT.FILL, SWT.TOP, true, false));
//
//		final Label author = new Label(shell, SWT.LEFT);
//		author.setText(a_post.link);
////		author.setLayoutData(new GridData(SWT.FILL, SWT.TOP, true, false));

		try {
			browser = new Browser(shell, SWT.NONE);
			FormData browser_data = new FormData();
			browser_data.left = new FormAttachment(feedlist);
			browser_data.top = new FormAttachment(0,0);
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
		postlist_data.top = new FormAttachment(feedlist);
		postlist_data.right = new FormAttachment(browser);
		postlist_data.bottom = new FormAttachment(100,0);
		postlist.setLayoutData(postlist_data);
		postlist.addSelectionListener(new PostListSelection());
		for (int i = 0;i < posts.size(); i++){
			postlist.add(posts.get(i).title);			
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

