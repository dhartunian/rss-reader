import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.List;
import org.eclipse.swt.browser.*;

public class PostList {

	private Browser browser;
	private List list;
	private java.util.List<RssItem> posts;

	private class PostListSelection implements SelectionListener {

		@Override
		public void widgetDefaultSelected(SelectionEvent arg0) {
			return;
		}

		@Override
		public void widgetSelected(SelectionEvent arg0) {
			browser.setText(posts.get(list.getSelectionIndices()[0]).body);
		}
		
	}
	
	public PostList(Composite parent, int style, Browser browser, java.util.List<RssItem> posts) {
		this.browser = browser;
		this.list = new List(parent, style);
		this.posts = posts;		
		PostListSelection listener = new PostListSelection();
		this.list.addSelectionListener(listener);
	}

	public void setLayoutData(GridData gd) {
		this.list.setLayoutData(gd);
	}
	
	public void add(String name) {
		this.list.add(name);
	}

}
