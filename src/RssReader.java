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
//		
//		final Composite c = new Composite(shell, SWT.NONE);
//		
//		GridLayout layout = new GridLayout();
//		layout.numColumns = 2;
//		c.setLayout(layout);
		
		Browser browser;
		
		try {
			browser = new Browser(shell, SWT.NONE);
			browser.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		} catch (SWTError e) {
			System.out.println(e.getMessage());
			display.dispose();
			return;
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

