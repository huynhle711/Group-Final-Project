import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Random;
import java.util.Scanner;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class SongGenerator {

    public static String fetchJSON(String url) {
        try {
            URL url1 = new URL(url);
            Scanner scan = new Scanner(url1.openStream());

            StringBuilder data = new StringBuilder();

            while (scan.hasNextLine()) {
                data.append(scan.nextLine());
            }

            scan.close();
            return data.toString();

        } catch (Exception e) {
            return "{ \"count\": 0, \"recordings\": [] }";
        }
    }

    public static String[] getSongsByArtist(String artist) {
        Random random = new Random();
        String[] songs = new String[5];

        artist = URLEncoder.encode(artist, StandardCharsets.UTF_8);

        String countURL = "https://musicbrainz.org/ws/2/recording?query=artist:"
                + artist + "&limit=1&fmt=json";

        String countJSON = fetchJSON(countURL);

        JsonObject countObj = JsonParser.parseString(countJSON).getAsJsonObject();
        int pages = countObj.get("count").getAsInt();

        if (pages == 0) {
            return new String[]{
                    "No song found",
                    "No song found",
                    "No song found",
                    "No song found",
                    "No song found"
            };
        }

        int max = Math.min(pages, 1000);

        for (int i = 0; i < 5; i++) {
            int randomPage = random.nextInt(max);

            String url = "https://musicbrainz.org/ws/2/recording?query=artist:"
                    + artist + "&limit=1&offset=" + randomPage + "&fmt=json";

            String data = fetchJSON(url);
            JsonObject obj = JsonParser.parseString(data).getAsJsonObject();

            if (!obj.has("recordings") || obj.getAsJsonArray("recordings").size() == 0) {
                songs[i] = "No song found";
            } else {
                JsonObject song = obj.getAsJsonArray("recordings")
                        .get(0)
                        .getAsJsonObject();

                String title = song.get("title").getAsString();
                String album = "Unknown Album";

                if (song.has("releases") && song.getAsJsonArray("releases").size() > 0) {
                    JsonArray releases = song.getAsJsonArray("releases");
                    album = releases.get(0)
                            .getAsJsonObject()
                            .get("title")
                            .getAsString();
                }

                String artistName = song.getAsJsonArray("artist-credit")
                        .get(0)
                        .getAsJsonObject()
                        .get("name")
                        .getAsString();

                songs[i] = "🎵 " + title + " | " + album + " | " + artistName;
            }
        }

        return songs;
    }
}