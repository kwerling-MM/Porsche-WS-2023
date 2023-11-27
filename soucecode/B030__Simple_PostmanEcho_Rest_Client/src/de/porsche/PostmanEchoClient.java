package de.porsche;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class PostmanEchoClient {

    public PostmanEchoClient() {
        HttpClient client = HttpClient.newHttpClient();
        try {
            peGet(client);
            peGetWithParameter(client);
            peMyIP(client);
            pePost(client);
        } catch (Exception ex) {
            System.err.println(ex.getMessage());
            ex.printStackTrace();
        }
    }

    void printResult( String methodName, HttpResponse<String> response) {
        System.out.println(methodName + ": " + response.body());
        System.out.println(methodName + " status code: " + response.statusCode());
        System.out.println();
    }

     void peGet(HttpClient client) throws URISyntaxException, IOException, InterruptedException {
        HttpRequest request = HttpRequest.newBuilder()
                .uri( new URI("https://postman-echo.com/get") )
                .GET()
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        printResult("peGet", response);
    }

     void  peGetWithParameter(HttpClient client) throws URISyntaxException, IOException, InterruptedException {
        HttpRequest request = HttpRequest.newBuilder()
                .uri( new URI("https://postman-echo.com/get?Eins=1") )
                .GET()
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        printResult("peGetWithParameter", response);
    }

     void  peMyIP(HttpClient client) throws URISyntaxException, IOException, InterruptedException {
        HttpRequest request = HttpRequest.newBuilder()
                .uri( new URI("https://postman-echo.com/ip") )
                .GET()
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        printResult("peMyIP", response);
    }

     void pePost(HttpClient client) throws URISyntaxException, IOException, InterruptedException {
        HttpRequest request = HttpRequest.newBuilder()
                .uri( new URI("https://postman-echo.com/post") )
                .headers("Content-Type", "text/plain;charset=UTF-8")
                .POST(HttpRequest.BodyPublishers
                        .ofString("Greeting to the Postman Web Service."))
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        printResult("pePost", response);
     }

}