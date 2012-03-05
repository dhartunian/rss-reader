import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.List;
import org.eclipse.swt.browser.*;

public class FeedList {

	private Browser browser;
	private List list;
	private PostList postlist;
	private java.util.List<RssFeed> feeds;

	private class FeedListSelection implements SelectionListener {

		@Override
		public void widgetDefaultSelected(SelectionEvent arg0) {
			return;
		}

		@Override
		public void widgetSelected(SelectionEvent arg0) {
			
		}
		
	}
	
	public FeedList(Composite parent, int style, Browser browser, 
					java.util.List<RssFeed> feeds, PostList postlist) {
		this.browser = browser;
		this.list = new List(parent, style);
		this.postlist = postlist;
		this.feeds = feeds;
		FeedListSelection listener = new FeedListSelection();
		this.list.addSelectionListener(listener);
	}

	public void setLayoutData(GridData gd) {
		this.list.setLayoutData(gd);
	}
	
	public void add(String name) {
		this.list.add(name);
	}

}
