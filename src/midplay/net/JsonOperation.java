package midplay.net;

import midplay.model.JsonListResult;
import midplay.model.Playlists;
import midplay.model.Tracks;

public class JsonOperation extends NetworkOperation {

  private static final int KIND_HOT_PLAYLISTS = 0;
  private static final int KIND_SEARCH_PLAYLISTS = 1;
  private static final int KIND_SEARCH_TRACKS = 2;
  private static final int KIND_GET_TRACKS = 3;

  private final int kind;
  private final String param;
  private final int page;
  private final JsonListResult result;
  private final JsonListListener listener;

  private JsonOperation(
      int kind, String param, int page, JsonListResult result, JsonListListener listener) {
    this.kind = kind;
    this.param = param;
    this.page = page;
    this.result = result;
    this.listener = listener;
  }

  public static JsonOperation searchTracks(String keyword, int page, JsonListListener listener) {
    return new JsonOperation(KIND_SEARCH_TRACKS, keyword, page, new Tracks(), listener);
  }

  public static JsonOperation searchTracks(String keyword, JsonListListener listener) {
    return searchTracks(keyword, 1, listener);
  }

  public static JsonOperation getTracks(String listKey, JsonListListener listener) {
    return new JsonOperation(KIND_GET_TRACKS, listKey, 1, new Tracks(), listener);
  }

  public static JsonOperation searchPlaylists(
      String keyword, String type, int page, JsonListListener listener) {
    return new JsonOperation(KIND_SEARCH_PLAYLISTS, keyword, page, new Playlists(), listener);
  }

  public static JsonOperation searchPlaylists(
      String keyword, String type, JsonListListener listener) {
    return searchPlaylists(keyword, type, 1, listener);
  }

  public static JsonOperation getHotPlaylists(int page, JsonListListener listener) {
    return new JsonOperation(KIND_HOT_PLAYLISTS, null, page, new Playlists(), listener);
  }

  public static JsonOperation getHotPlaylists(JsonListListener listener) {
    return getHotPlaylists(1, listener);
  }

  protected void execute() {
    String json;
    switch (kind) {
      case KIND_HOT_PLAYLISTS:
        json = MockData.hotPlaylists(page);
        break;
      case KIND_SEARCH_PLAYLISTS:
        json = MockData.searchPlaylists(param, page);
        break;
      case KIND_SEARCH_TRACKS:
        json = MockData.searchTracks(param, page);
        break;
      default:
        json = MockData.getTracks(param);
        break;
    }
    onResponse(json);
  }

  protected void processResponse(String response) {
    result.parse(response);
    if (result.size() == 0) {
      listener.onNoData();
    } else {
      listener.onDataReceived(result);
    }
  }

  protected void handleNoData() {
    listener.onNoData();
  }

  protected void handleError(Exception e) {
    listener.onError(e);
  }

  public interface JsonListListener {
    void onDataReceived(JsonListResult result);

    void onNoData();

    void onError(Exception e);
  }
}
