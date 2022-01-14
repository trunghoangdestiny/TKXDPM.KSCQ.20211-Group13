package eco.bike.rental.interbank;

import eco.bike.rental.entity.Card;
import eco.bike.rental.interbank.bank.BankController;
import org.json.JSONObject;

public class BankImpl implements IBank<JSONObject> {
    private final BankController bankController = new BankController();

    @Override
    public JSONObject payOrder(Card card, Float amount, String transactionContent) {
        return bankController.payOrder(card, amount, transactionContent);
    }
}
