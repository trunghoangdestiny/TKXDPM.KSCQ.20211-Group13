package eco.bike.rental.interbank;

import eco.bike.rental.entity.Card;
import lombok.NoArgsConstructor;
import org.json.JSONObject;
import org.springframework.stereotype.Component;

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
import java.util.Locale;

@Component
@NoArgsConstructor
public class PayOrder {
    public JSONObject payOrder(Card card, Float amount, String transactionContent, String typeTransaction) throws NoSuchAlgorithmException, URISyntaxException, IOException, InterruptedException {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        // json to hash md5
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("secretKey", "BiQiHqA+1O4=");

        // transaction in json to hash md5
        JSONObject transaction = new JSONObject();
        transaction.put("command", typeTransaction.toLowerCase(Locale.ROOT));
        transaction.put("cardCode", card.getCardNumber());
        transaction.put("owner", card.getOwner());
        transaction.put("cvvCode", card.getCvvCode());
        transaction.put("dateExpired", card.getExpirationDate());
        transaction.put("transactionContent", transactionContent);
        transaction.put("amount", amount);

        jsonObject.put("transaction", transaction);

        // create message digest to hash json
        MessageDigest messageDigest = MessageDigest.getInstance("MD5");
        messageDigest.update(jsonObject.toString().getBytes(StandardCharsets.UTF_8));

        // get hashcode from json
        String hashCode = DatatypeConverter.printHexBinary(messageDigest.digest());

        // create body request
        JSONObject bodyRequestJson = new JSONObject();
        bodyRequestJson.put("version", "1.0.1");

        transaction.put("createdAt", simpleDateFormat.format(new Date()));

        bodyRequestJson.put("transaction", transaction);
        bodyRequestJson.put("appCode", "AWysZ5cXuUY=");
        bodyRequestJson.put("hashCode", hashCode);

        // execute request
        HttpClient httpClient = HttpClient.newBuilder()
                .build();

        HttpRequest request = HttpRequest.newBuilder()
                .uri(new URI("https://ecopark-system-api.herokuapp.com/api/card/processTransaction"))
                .header("Content-Type", "application/json")
                .method("PATCH", HttpRequest.BodyPublishers.ofString(bodyRequestJson.toString()))
                .build();

        HttpResponse response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

        return new JSONObject(response.body().toString());
    }
}
