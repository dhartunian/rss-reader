import java.util.List;
import org.eclipse.swt.*;
import org.eclipse.swt.layout.*;
import org.eclipse.swt.widgets.*;
import org.eclipse.swt.browser.*;

public class RssReader {
	
	public static void main(String[] args) {
		RssParser myrss = new RssParser();
		myrss.readRss("rss.xml");
		List<RssItem> posts = myrss.doc.getPosts();
		RssItem a_post = posts.get(4);

		Display display = new Display();
		Shell shell = new Shell(display);
		shell.setLayout(new GridLayout(2,false));
		shell.setText("RSS Reader v0.1");
		
		Browser browser;		

		final org.eclipse.swt.widgets.List feed_list = new org.eclipse.swt.widgets.List(shell, SWT.BORDER | SWT.MULTI | SWT.V_SCROLL);
		feed_list.setLayoutData(new GridData(SWT.FILL, SWT.TOP, false, true, 1, 3));
		feed_list.add("Topical");
		feed_list.add("3quarksdaily");
		feed_list.add("Brain Pickings");
		feed_list.add("Futility Closet");
		feed_list.add("Infovore");

		final Label title = new Label(shell, SWT.LEFT);
		title.setText(a_post.title);
		title.setLayoutData(new GridData(SWT.FILL, SWT.TOP, true, false));

		final Label author = new Label(shell, SWT.LEFT);
		author.setText(a_post.link);
		author.setLayoutData(new GridData(SWT.FILL, SWT.TOP, true, false));

		try {
			browser = new Browser(shell, SWT.NONE);
			browser.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 2));
		} catch (SWTError e) {
			System.out.println(e.getMessage());
			display.dispose();
			return;
		}

		final PostList post_list = new PostList(shell, SWT.BORDER | SWT.MULTI | SWT.V_SCROLL, browser, posts);
		post_list.setLayoutData(new GridData(SWT.FILL, SWT.TOP, false, true));
		for (int i = 0;i < posts.size(); i++){
			post_list.add(posts.get(i).title);			
		}

		browser.setText(a_post.body);
		shell.open();
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch())
				display.sleep();
		}
		display.dispose();
	}
}

