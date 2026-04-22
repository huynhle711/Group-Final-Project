import java.net.URL;
import java.util.Random;
import java.util.Scanner;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class SongGenerator {

    public static String fetchJSON(String url) {
        URL url1;
        Scanner scan;

        try {
            url1 = new URL(url);
            scan = new Scanner(url1.openStream());
        } catch (Exception e) {
            return "{ \"count\": 0, \"recordings\": [] }";
        }

        StringBuilder data = new StringBuilder();

        while (scan.hasNextLine()) {
            data.append(scan.nextLine());
        }

        scan.close();
        return data.toString();
    }

    public static String[] getSong(String genre) {
        Random random = new Random();

        genre = genre.replaceAll("\\s", "-");

        String pageURL = "https://musicbrainz.org/ws/2/recording?query=tag:" + genre + "&limit=1&fmt=json";
        String pageJSON = fetchJSON(pageURL);

        JsonObject countObj = JsonParser.parseString(pageJSON).getAsJsonObject();
        int pages = countObj.get("count").getAsInt();

        if (pages == 0) {
            return new String[]{"No song found", "No artist found"};
        }

        int max = Math.min(pages, 10000);
        int randomPage = random.nextInt(max);

        String url = "https://musicbrainz.org/ws/2/recording?query=tag:" + genre + "&limit=1&offset=" + randomPage + "&fmt=json";
        String data = fetchJSON(url);

        JsonObject obj = JsonParser.parseString(data).getAsJsonObject();

        if (!obj.has("recordings") || obj.getAsJsonArray("recordings").size() == 0) {
            return new String[]{"No song found", "No artist found"};
        }

        JsonObject song = obj.getAsJsonArray("recordings")
                .get(0)
                .getAsJsonObject();

        String title = song.get("title").getAsString();

        String artist = song.getAsJsonArray("artist-credit")
                .get(0)
                .getAsJsonObject()
                .get("name")
                .getAsString();

        return new String[]{title, artist};
    }
}