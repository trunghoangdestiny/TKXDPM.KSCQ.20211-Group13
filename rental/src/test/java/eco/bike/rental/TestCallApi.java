package eco.bike.rental;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.JSONException;
import org.json.JSONObject;

import javax.xml.bind.DatatypeConverter;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class TestCallApi {
    public static void main(String[] args) throws JSONException, NoSuchAlgorithmException, URISyntaxException, IOException, InterruptedException {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("secretKey", "BiQiHqA+1O4=");

        JSONObject transaction = new JSONObject();
        transaction.put("command", "pay");
        transaction.put("cardCode", "kscq1_group13_2021");
        transaction.put("owner", "Group 13");
        transaction.put("cvvCode", "402");
        transaction.put("dateExpired", "1125");
        transaction.put("transactionContent", "Pay to rent bike");
        transaction.put("amount", 1);

        jsonObject.put("transaction", transaction);


        MessageDigest messageDigest = MessageDigest.getInstance("MD5");
        messageDigest.update(jsonObject.toString().getBytes(StandardCharsets.UTF_8));

        //get hashcode from json
        String hashCode = DatatypeConverter.printHexBinary(messageDigest.digest());

        JSONObject bodyRequestJson = new JSONObject();
        bodyRequestJson.put("version", "1.0.1");

        transaction.put("createdAt", simpleDateFormat.format(new Date()));

        bodyRequestJson.put("transaction", transaction);
        bodyRequestJson.put("appCode", "AWysZ5cXuUY=");
        bodyRequestJson.put("hashCode", hashCode);

        // body request to reset balance
        JSONObject bodyResetBalance = new JSONObject();
        bodyResetBalance.put("cardCode", "kscq1_group13_2021");
        bodyResetBalance.put("owner", "Group 13");
        bodyResetBalance.put("cvvCode", "402");
        bodyResetBalance.put("dateExpired", "1125");

//        HttpRequest request = HttpRequest.newBuilder()
//                .uri(new URI("https://ecopark-system-api.herokuapp.com/api/card/processTransaction"))
//                .header("Content-Type", "application/json")
//                .method("PATCH", HttpRequest.BodyPublishers.ofString(bodyRequestJson.toString()))
//                .build();

        HttpRequest request = HttpRequest.newBuilder()
                .uri(new URI("https://ecopark-system-api.herokuapp.com/api/card/reset-balance"))
                .header("Content-Type", "application/json")
                .method("PATCH", HttpRequest.BodyPublishers.ofString(bodyResetBalance.toString()))
                .build();

        HttpClient httpClient = HttpClient.newBuilder()
                .build();

        HttpResponse response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
        System.out.println(response.statusCode());
        System.out.println(new JSONObject(response.body().toString()));
    }
}
