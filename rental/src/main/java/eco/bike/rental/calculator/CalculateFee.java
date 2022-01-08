package eco.bike.rental.calculator;

public class CalculateFee {
    private ICalculateFee calculateFee;

    public CalculateFee(ICalculateFee calculateFee) {
        this.calculateFee = calculateFee;
    }

    public long calculateFee(long time){
        return calculateFee.calculateFee(time);
    }
}
