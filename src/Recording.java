import java.util.List;
import com.google.gson.annotations.SerializedName;

public class Recording {
    String title;

    @SerializedName("artist-credit")
    List<ArtistCredit> artistCredit;
}