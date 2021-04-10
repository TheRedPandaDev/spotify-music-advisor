package advisor.strategy;

import java.util.List;

public class StrategyGetCategories implements Strategy {
    private final List<String> categories = List.of("Top Lists",
            "Pop",
            "Mood",
            "Latin"
    );

    @Override
    public List<String> execute() {
        return categories;
    }
}
