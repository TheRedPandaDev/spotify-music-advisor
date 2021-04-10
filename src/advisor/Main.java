package advisor;

import advisor.strategy.*;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.*;

public class Main {
    private static final CommandExecutor commandExecutor = new CommandExecutor();



    public static void main(String[] args) {
        String accessPoint = "https://accounts.spotify.com";
        if (args.length == 2) {
            accessPoint = args[1];
        }
        String header = "";
        List<String> output = null;
        boolean authorized = false;


        Scanner scanner = new Scanner(System.in);
        String input = scanner.nextLine();

        while (!"exit".equals(input)) {
            if (!authorized) {
                if ("auth".equals(input)) {
                    try {
                        authorized = authorize(accessPoint);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    if (authorized) {
                        System.out.println("---SUCCESS---");
                    } else {
                        System.out.println("---AUTHORIZATION FAILED---");
                    }
                } else {
                    System.out.println("Please, provide access for application.");
                }

                input = scanner.nextLine();
                continue;
            }
            String[] inputArr = input.split(" ");
            if (inputArr.length == 1) {
                switch (input) {
                    case "new":
                        commandExecutor.setStrategy(new StrategyGetNew());
                        header = "---NEW RELEASES---";
                        break;
                    case "featured":
                        commandExecutor.setStrategy(new StrategyGetFeatured());
                        header = "---FEATURED---";
                        break;
                    case "categories":
                        commandExecutor.setStrategy(new StrategyGetCategories());
                        header = "---CATEGORIES---";
                        break;
                }
                output = commandExecutor.executeStrategy();
            } else if (inputArr.length == 2) {
                if ("playlists".equals(inputArr[0])) {
                    commandExecutor.setConsumerStrategy(new ConsumerStrategyGetPlaylistsByCategory());
                    header = "--" + inputArr[1] + " PLAYLISTS---";
                }
                output = commandExecutor.executeConsumerStrategy(inputArr[1]);
            }

            System.out.println(header);
            if (output != null) {
                output.forEach(System.out::println);
            }

            input = scanner.nextLine();
        }

        System.out.println("---GOODBYE!---");
    }

    public static boolean authorize(String accessPoint) throws IOException {

        String clientId = "a7b9a4757fea480d90f85db711027c0a";
        String redirectURI = "http://localhost:8888/";
        String authLink = accessPoint +
                "/authorize?client_id=" +
                clientId +
                "&redirect_uri=" +
                redirectURI +
                "&response_type=code";

        System.out.println("use this link to request the access code:");
        System.out.println(authLink);

        HttpServer httpServer = HttpServer.create();
        httpServer.bind(new InetSocketAddress(8888), 0);

        final String[] code = new String[1];

        httpServer.createContext("/",
                httpExchange -> {
                    Map<String, String> queryParams = queryToMap(httpExchange.getRequestURI().getQuery());

                    if (queryParams.entrySet().size() != 0 &&
                            queryParams.get("code") != null &&
                            !queryParams.get("code").equals("")) {
                        code[0] = queryParams.get("code");
                        String OK = "Got the code. Return back to your program.";
                        httpExchange.sendResponseHeaders(200, OK.length());
                        httpExchange.getResponseBody().write(OK.getBytes());
                    } else {
                        String badRequest = "Authorization code not found. Try again.";
                        httpExchange.sendResponseHeaders(400, badRequest.length());
                        httpExchange.getResponseBody().write(badRequest.getBytes());
                    }

                    httpExchange.close();
                });

        httpServer.start();
        System.out.println("waiting for code...");

        while (code[0] == null || code[0].equals("")) {
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
                return false;
            }
        }

        System.out.println("code received");
        httpServer.stop(10);

        System.out.println("making http request for access_token...");

        HttpClient httpClient = HttpClient.newBuilder().build();
        HttpRequest httpRequest = HttpRequest.newBuilder()
                .header("Content-Type", "application/x-www-form-urlencoded")
                .uri(URI.create(accessPoint + "/api/token"))
                .POST(HttpRequest.BodyPublishers.ofString("grant_type=authorization_code" +
                        "&code=" + code[0] +
                        "&redirect_uri=" + redirectURI +
                        "&client_id=" +
                        "&client_secret="
                ))
                .build();

        HttpResponse<String> httpResponse;

        try {
            httpResponse = httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString());
        } catch (InterruptedException e) {
            e.printStackTrace();
            return false;
        }

        System.out.println("response:\n" + httpResponse.body());

        return true;
    }

    public static Map<String, String> queryToMap(String query) {
        Map<String, String> result = new HashMap<>();
        if (query == null) return result;

        for (String param : query.split("&")) {
            String[] entry = param.split("=");
            if (entry.length > 1) {
                result.put(entry[0], entry[1]);
            } else {
                result.put(entry[0], "");
            }
        }

        return result;
    }
}
