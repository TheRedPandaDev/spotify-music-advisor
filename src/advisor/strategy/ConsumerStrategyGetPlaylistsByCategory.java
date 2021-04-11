package advisor.strategy;

import java.util.Collections;
import java.util.List;
import java.util.Map;

public class ConsumerStrategyGetPlaylistsByCategory implements ConsumerStrategy {
    private final Map<String, List<String>> playlists = Collections.singletonMap("Mood",
            List.of("Walk Like A Badass",
                    "Rage Beats",
                    "Arab Mood Booster",
                    "Sunday Stroll"));

    @Override
    public List<String> execute(String resourceAPI, String input) {
        return playlists.get(input);
    }
}
