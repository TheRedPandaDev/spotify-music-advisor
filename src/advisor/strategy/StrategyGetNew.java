package advisor.strategy;

import java.util.List;

public class StrategyGetNew implements Strategy {
    private final List<String> newReleases = List.of("Mountains [Sia, Diplo, Labrinth]",
            "Runaway [Lil Peep]",
            "The Greatest Show [Panic! At The Disco]",
            "All Out Life [Slipknot]"
    );

    @Override
    public List<String> execute() {
        return newReleases;
    }
}
