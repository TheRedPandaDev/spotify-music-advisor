package advisor.strategy;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;

public class StrategyGetCategories implements Strategy {
    @Override
    public List<String> execute(HttpClient httpClient, String resourceAPI, String accessToken) {
        List<String> categoriesList = new ArrayList<>();

        HttpRequest httpRequest = HttpRequest.newBuilder()
                .header("Authorization", "Bearer " + accessToken)
                .uri(URI.create(resourceAPI + "/v1/browse/categories"))
                .GET()
                .build();

        HttpResponse<String> httpResponse;

        try {
            httpResponse = httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString());
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
            return null;
        }

        JsonObject responseObject = JsonParser.parseString(httpResponse.body()).getAsJsonObject();

        JsonArray categories;

        if (responseObject.has("categories")) {
            categories = responseObject.get("categories").getAsJsonObject().get("items").getAsJsonArray();
        } else {
            System.out.println(responseObject.get("error").getAsJsonObject().get("message"));
            return null;
        }

        for (JsonElement category : categories) {
            JsonObject categoryObject = category.getAsJsonObject();

            String name = categoryObject.get("name").getAsString();

            categoriesList.add(name);
        }

        return categoriesList;
    }
}
