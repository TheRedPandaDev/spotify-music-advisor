package advisor.strategy;

import java.net.http.HttpClient;
import java.util.List;

public class StrategyGetFeatured implements Strategy {
    private final List<String> featuredPlaylists = List.of("Mellow Morning",
            "Wake Up and Smell the Coffee",
            "Monday Motivation",
            "Songs to Sing in the Shower"
    );

    @Override
    public List<String> execute(HttpClient httpClient, String resourceAPI, String accessToken) {
        return featuredPlaylists;
    }
}
