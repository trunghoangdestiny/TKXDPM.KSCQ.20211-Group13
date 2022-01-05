package eco.bike.rental.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class OrderInfoDTO {
    private String owner;
    private String cardNumber;
    private String issuingBank;
    private String expirationDate;
    private String cvvCode;

    private String transactionDescription;

    private String rentingBikeCode;
    private Long rentingBikeParkingId;
}
