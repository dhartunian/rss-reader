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
	static Text newfeed;
	
	static HashMap<String, List<RssItem>> feeddata;
	static RssParser rssparser;

	static Display display;
	static Shell shell;
	
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
					postlist.add(posts.get(i).getTitle());			
				}
				updatePostArea(posts.get(0));
				postlist.select(0);
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
	
	
	private static class addFeedClick implements SelectionListener {
		public void widgetDefaultSelected(SelectionEvent arg0) {
			return;
		}

		public void widgetSelected(SelectionEvent arg0) {
			downloadAndAddFeed(newfeed.getText());
		}
	}

	private static void updatePostArea(RssItem post) {
		posttitle.setText(post.getTitle());
		postauthor.setText(post.getAuthor());
		posturl.setText(post.getLink());
		browser.setText(post.getBody());
	}
	
	public static void downloadAndAddFeed(String url) {
		rssparser = new RssParser();
		rssparser.readRss(url);
		List<RssItem> posts = rssparser.doc.getPosts();
		feeddata.put(rssparser.doc.title, posts);
		feedlist.add(rssparser.doc.title);
	}
	
	public static void createInterface() {
		newfeed = new Text(shell, SWT.SINGLE);
		final FormData newfeed_data = new FormData();
		newfeed_data.left = new FormAttachment(0,0);
		newfeed_data.top = new FormAttachment(0,0);
//		newfeed_data.right = new FormAttachment(posttitle,0);
		newfeed_data.width = shell.getClientArea().width / 4;
		newfeed.setLayoutData(newfeed_data);
		
		Button addfeed = new Button(shell, SWT.PUSH | SWT.TOP);
		addfeed.addSelectionListener(new addFeedClick());
		final FormData addfeed_data = new FormData();
		addfeed_data.left = new FormAttachment(0,0);
		addfeed_data.top = new FormAttachment(newfeed,4);
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
		final FormData posttitle_data = new FormData();
		posttitle_data.left = new FormAttachment(feedlist,4);
		posttitle_data.top = new FormAttachment(0,0);
		posttitle_data.right = new FormAttachment(100,0);
		posttitle.setLayoutData(posttitle_data);
		posttitle.setFont(new Font(display, new FontData("",16,SWT.BOLD)));

		postauthor = new Label(shell, SWT.LEFT);
		final FormData postauthor_data = new FormData();
		postauthor_data.left = new FormAttachment(feedlist,4);
		postauthor_data.top = new FormAttachment(posttitle,4);
		postauthor_data.right = new FormAttachment(100,0);
		postauthor.setLayoutData(postauthor_data);

		posturl = new Link(shell, SWT.LEFT);
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
		
	}
	
	public static void main(String[] args) {
		feeddata = new HashMap<String, List<RssItem>>();
		
		display = new Display();
		shell = new Shell(display);
		FormLayout shelllayout = new FormLayout();
		shelllayout.marginWidth = 4;
		shelllayout.marginHeight = 4;
		shell.setLayout(shelllayout);
		shell.setText("RSS Reader v0.1");
		
		createInterface();

		downloadAndAddFeed("http://topicaltopical.com/feed");
		downloadAndAddFeed("http://feeds.arstechnica.com/arstechnica/index?format=xml");
		
		shell.open();

		while (!shell.isDisposed()) {
			if (!display.readAndDispatch())
				display.sleep();
		}
		
		display.dispose();
		
	}
}

