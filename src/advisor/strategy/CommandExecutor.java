package advisor.strategy;

import java.util.List;

public class CommandExecutor {
    private Strategy strategy;
    private ConsumerStrategy consumerStrategy;

    public void setStrategy(Strategy strategy) {
        this.strategy = strategy;
    }

    public void setConsumerStrategy(ConsumerStrategy consumerStrategy) {
        this.consumerStrategy = consumerStrategy;
    }

    public List<String> executeStrategy() {
        return strategy.execute();
    }

    public List<String> executeConsumerStrategy(String input) {
        return consumerStrategy.execute(input);
    }
}
