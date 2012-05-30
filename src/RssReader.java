public class RssReader {

	static RssUserInterface ui;

	public static void main(String[] args) {
		ui = new RssUserInterface();
		Thread ui_thread = new Thread(ui);
		ui_thread.start();
	}
	
}