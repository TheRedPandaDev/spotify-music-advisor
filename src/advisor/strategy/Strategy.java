package advisor.strategy;

import java.net.http.HttpClient;
import java.util.List;

public interface Strategy {
    List<String> execute(HttpClient httpClient, String resourceAPI, String accessToken);
}
