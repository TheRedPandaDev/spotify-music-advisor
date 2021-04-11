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

public class StrategyGetNew implements Strategy {
    @Override
    public List<String> execute(HttpClient httpClient, String resourceAPI, String accessToken) {
        List<String> newReleases = new ArrayList<>();

        HttpRequest httpRequest = HttpRequest.newBuilder()
                .header("Authorization", "Bearer " + accessToken)
                .uri(URI.create(resourceAPI + "/v1/browse/new-releases"))
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

        JsonArray albums;

        if (responseObject.has("albums")) {
            albums = responseObject.get("albums").getAsJsonObject().get("items").getAsJsonArray();
        } else {
            System.out.println(responseObject.get("error").getAsJsonObject().get("message"));
            return null;
        }

        for (JsonElement album : albums) {
            JsonObject albumObject = album.getAsJsonObject();

            String name = albumObject.get("name").getAsString();

            JsonArray artistArray = albumObject.get("artists").getAsJsonArray();
            StringBuilder artistsNamesSB = new StringBuilder("[");
            artistsNamesSB.append(artistArray.get(0).getAsJsonObject().get("name"));
            for (int i = 1; i < artistArray.size(); i++) {
                artistsNamesSB.append(", ").append(artistArray.get(i).getAsJsonObject().get("name"));
            }
            artistsNamesSB.append("]");
            String artistsNames = artistsNamesSB.toString();

            String url = albumObject.get("external_urls").getAsJsonObject().get("spotify").getAsString();

            newReleases.add(name + "\n" + artistsNames + "\n" + url + "\n");
        }

        return newReleases;
    }
}
