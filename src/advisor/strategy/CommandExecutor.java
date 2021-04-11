package advisor.strategy;

import java.net.http.HttpClient;
import java.util.List;

public class CommandExecutor {
    private Strategy strategy;
    private ConsumerStrategy consumerStrategy;
    private final String resourceAPI;
    private final HttpClient httpClient;
    private String accessToken;

    public CommandExecutor(String resourceAPI, HttpClient httpClient) {
        this.resourceAPI = resourceAPI;
        this.httpClient = httpClient;
        accessToken = "";
    }

    public void setStrategy(Strategy strategy) {
        this.strategy = strategy;
    }

    public void setConsumerStrategy(ConsumerStrategy consumerStrategy) {
        this.consumerStrategy = consumerStrategy;
    }

    public List<String> executeStrategy() {
        return strategy.execute(httpClient, resourceAPI, accessToken);
    }

    public List<String> executeConsumerStrategy(String input) {
        return consumerStrategy.execute(resourceAPI, input);
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }
}
