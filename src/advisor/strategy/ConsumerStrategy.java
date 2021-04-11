package advisor.strategy;

import java.util.List;

public interface ConsumerStrategy {
    List<String> execute(String resourceAPI, String input);
}
