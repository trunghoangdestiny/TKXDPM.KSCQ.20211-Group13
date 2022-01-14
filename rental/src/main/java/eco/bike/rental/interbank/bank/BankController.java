package eco.bike.rental.interbank.bank;

import eco.bike.rental.entity.Card;
import org.json.JSONObject;

import javax.xml.bind.DatatypeConverter;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.http.HttpRequest;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class BankController {
    private final String SECRET_KEY = "BiQiHqA+1O4=";
    private final String VERSION = "1.0.1";
    private final String APP_CODE = "AWysZ5cXuUY=";
    private final String URI = "https://ecopark-system-api.herokuapp.com/api/card/processTransaction";

    private final BankQuery bankQuery = new BankQuery();

    public JSONObject payOrder(Card card, Float amount, String transactionContent) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        // json to hash md5
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("secretKey", SECRET_KEY);

        // transaction in json to hash md5
        JSONObject transaction = new JSONObject();
        transaction.put("cardCode", card.getCardNumber());
        transaction.put("owner", card.getOwner());
        transaction.put("cvvCode", card.getCvvCode());
        transaction.put("dateExpired", card.getExpirationDate());
        transaction.put("transactionContent", transactionContent);
        if (amount >= 0) {
            transaction.put("command", "pay");
            transaction.put("amount", amount);
        } else {
            transaction.put("command", "refund");
            transaction.put("amount", amount * (-1));
        }

        jsonObject.put("transaction", transaction);

        // create message digest to hash json
        MessageDigest messageDigest = null;
        try {
            messageDigest = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            return null;
        }
        messageDigest.update(jsonObject.toString().getBytes(StandardCharsets.UTF_8));

        // get hashcode from json
        String hashCode = DatatypeConverter.printHexBinary(messageDigest.digest());

        // create body request
        JSONObject bodyRequestJson = new JSONObject();
        bodyRequestJson.put("version", VERSION);

        transaction.put("createdAt", simpleDateFormat.format(new Date()));

        bodyRequestJson.put("transaction", transaction);
        bodyRequestJson.put("appCode", APP_CODE);
        bodyRequestJson.put("hashCode", hashCode);

        return bankQuery.query(URI, HttpRequest.BodyPublishers.ofString(bodyRequestJson.toString()));
    }
}
