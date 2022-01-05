package eco.bike.rental.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class BackingInfoDTO {
    private String owner;
    private String cardNumber;
    private String issuingBank;
    private String expirationDate;
    private String cvvCode;

    private String transactionDescription;

    private Long orderId;
    private Long amount;
    private String codeBike;
    private Long bikeParkingId;
}
