package eco.bike.rental.interbank.bank;

import org.json.JSONObject;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class BankQuery {
    JSONObject query(String uri, HttpRequest.BodyPublisher bodyPublisher) {
        HttpClient httpClient = HttpClient.newBuilder()
                .build();

        HttpRequest request = null;
        try {
            request = HttpRequest.newBuilder()
                    .uri(new URI(uri))
                    .header("Content-Type", "application/json")
                    .method("PATCH", bodyPublisher)
                    .build();
        } catch (URISyntaxException e) {
            return null;
        }

        HttpResponse response = null;
        try {
            response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
        } catch (IOException e) {
            return null;
        } catch (InterruptedException e) {
            return null;
        }

        return new JSONObject(response.body().toString());
    }
}
