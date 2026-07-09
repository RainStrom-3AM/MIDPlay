package midplay.net;

import javax.microedition.lcdui.Image;

public class SpriteSheetOperation extends NetworkOperation {

  private final String[] urls;
  private final int cellW;
  private final int cellH;
  private final int cols;
  private final Listener listener;

  public SpriteSheetOperation(String[] urls, int cellW, int cellH, int cols, Listener listener) {
    this.urls = urls;
    this.cellW = cellW;
    this.cellH = cellH;
    this.cols = cols;
    this.listener = listener;
  }

  protected void execute() {
    // ponytail: mock mode — no sprite server; thumbnails are not fetched.
    listener.onError(new Exception("mock: no network"));
  }

  protected void handleError(Exception e) {
    listener.onError(e);
  }

  public interface Listener {
    void onSheet(Image[] cells);

    void onError(Exception e);
  }
}
