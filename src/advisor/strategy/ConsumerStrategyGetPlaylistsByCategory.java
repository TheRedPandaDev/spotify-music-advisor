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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ConsumerStrategyGetPlaylistsByCategory implements ConsumerStrategy {
    @Override
    public List<String> execute(HttpClient httpClient, String resourceAPI, String accessToken, String input) {
        List<String> playlistsList = new ArrayList<>();

        Map<String, String> categoriesMap = new HashMap<>();

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

            String id = categoryObject.get("id").getAsString();

            categoriesMap.putIfAbsent(name, id);
        }

        if (!categoriesMap.containsKey(input)) {
            System.out.println("Unknown category name");
            return null;
        }

        httpRequest = HttpRequest.newBuilder()
                .header("Authorization", "Bearer " + accessToken)
                .uri(URI.create(resourceAPI + "/v1/browse/categories/" + categoriesMap.get(input) + "/playlists"))
                .GET()
                .build();

        try {
            httpResponse = httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString());
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
            return null;
        }

        responseObject = JsonParser.parseString(httpResponse.body()).getAsJsonObject();

        JsonArray playlists;

        if (responseObject.has("playlists")) {
            playlists = responseObject.get("playlists").getAsJsonObject().get("items").getAsJsonArray();
        } else {
            System.out.println(responseObject.get("error").getAsJsonObject().get("message"));
            return null;
        }

        for (JsonElement playlist : playlists) {
            JsonObject playlistObject = playlist.getAsJsonObject();

            String name = playlistObject.get("name").getAsString();

            String url = playlistObject.get("external_urls").getAsJsonObject().get("spotify").getAsString();

            playlistsList.add(name + "\n" + url + "\n");
        }

        return playlistsList;
    }
}
