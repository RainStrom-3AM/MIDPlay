package midplay.net;

// ponytail: mock data source — replaces the removed streaming backend.
// No network; canned ASCII sample content so browse/search/favorites UI is demoable.
// JSON shape matches what JsonListResult.parse expects: {"Items":[...],"GetMore":"no"}.
public final class MockData {

  private static final String[] PLAYLIST_NAMES = {"Demo Mix One", "Demo Mix Two", "Demo Mix Three"};
  private static final String[] PLAYLIST_KEYS = {"mock_pl_0", "mock_pl_1", "mock_pl_2"};
  private static final int TRACKS_PER_PLAYLIST = 5;
  private static final String DEMO_ARTIST = "Demo Artist";
  private static final String NO_MORE = "{\"Items\":[],\"GetMore\":\"no\"}";

  public static String hotPlaylists(int page) {
    if (page > 1) {
      return NO_MORE;
    }
    return playlistsJson(allMatch());
  }

  public static String searchPlaylists(String q, int page) {
    if (page > 1) {
      return NO_MORE;
    }
    return playlistsJson(playlistMatch(q));
  }

  public static String searchTracks(String q, int page) {
    if (page > 1) {
      return NO_MORE;
    }
    String needle = lower(q);
    StringBuffer sb = new StringBuffer();
    sb.append("{\"Items\":[");
    int count = 0;
    for (int p = 0; p < PLAYLIST_NAMES.length; p++) {
      for (int t = 0; t < TRACKS_PER_PLAYLIST; t++) {
        String name = trackName(p, t);
        if (needle.length() == 0 || lower(name).indexOf(needle) >= 0) {
          if (count > 0) {
            sb.append(',');
          }
          sb.append(trackItem(p, t));
          count++;
        }
      }
    }
    sb.append("],\"GetMore\":\"no\"}");
    return sb.toString();
  }

  public static String getTracks(String listKey) {
    int idx = playlistIndex(listKey);
    return tracksJson(idx >= 0 ? idx : 0);
  }

  public static String themeColors(String hex) {
    int accent = 0x65558F;
    try {
      accent = Integer.parseInt(hex, 16);
    } catch (Exception e) {
    }
    String colors = "{\"primary\":" + accent + "}";
    return "{\"light\":" + colors + ",\"dark\":" + colors + "}";
  }

  private static String playlistsJson(int matchIdx) {
    StringBuffer sb = new StringBuffer();
    sb.append("{\"Items\":[");
    int count = 0;
    for (int i = 0; i < PLAYLIST_NAMES.length; i++) {
      if (matchIdx < 0 || matchIdx == i) {
        if (count > 0) {
          sb.append(',');
        }
        sb.append(playlistItem(i));
        count++;
      }
    }
    sb.append("],\"GetMore\":\"no\"}");
    return sb.toString();
  }

  private static String tracksJson(int playlistIndex) {
    StringBuffer sb = new StringBuffer();
    sb.append("{\"Items\":[");
    for (int t = 0; t < TRACKS_PER_PLAYLIST; t++) {
      if (t > 0) {
        sb.append(',');
      }
      sb.append(trackItem(playlistIndex, t));
    }
    sb.append("],\"GetMore\":\"no\"}");
    return sb.toString();
  }

  private static String playlistItem(int i) {
    return "{\"ListKey\":\""
        + PLAYLIST_KEYS[i]
        + "\",\"Name\":\""
        + PLAYLIST_NAMES[i]
        + "\",\"Image\":\"\",\"Singer\":\"\"}";
  }

  private static String trackItem(int p, int t) {
    return "{\"Key\":\"mock_t_"
        + p
        + "_"
        + t
        + "\",\"Name\":\""
        + trackName(p, t)
        + "\",\"Url\":\"\",\"Duration\":"
        + (180 + t * 30)
        + ",\"Singer\":\""
        + DEMO_ARTIST
        + "\",\"Image\":\"\"}";
  }

  private static String trackName(int p, int t) {
    return "Demo Track " + ((p * TRACKS_PER_PLAYLIST) + t + 1);
  }

  private static int playlistIndex(String listKey) {
    if (listKey == null) {
      return -1;
    }
    for (int i = 0; i < PLAYLIST_KEYS.length; i++) {
      if (PLAYLIST_KEYS[i].equals(listKey)) {
        return i;
      }
    }
    return -1;
  }

  // -1 means "return all" (no query, no match, or multiple matches); otherwise the single hit.
  private static int playlistMatch(String q) {
    String needle = lower(q);
    if (needle.length() == 0) {
      return allMatch();
    }
    int hit = -1;
    for (int i = 0; i < PLAYLIST_NAMES.length; i++) {
      if (lower(PLAYLIST_NAMES[i]).indexOf(needle) >= 0) {
        if (hit >= 0) {
          return allMatch();
        }
        hit = i;
      }
    }
    return hit;
  }

  private static int allMatch() {
    return -1;
  }

  private static String lower(String s) {
    return s == null ? "" : s.toLowerCase();
  }

  private MockData() {}
}
