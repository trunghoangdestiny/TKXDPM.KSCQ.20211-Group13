package eco.bike.rental.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class OrderHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String startedAt;
    private String stoppedAt;
    private String currentRentedTime;
    private Long currentPrice;

    private Boolean isDone;
    private Boolean isSuccess;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "card_id")
    private Card card;

    private String bikeCode;
    private Long bikeParkingStartId;
    private Long bikeParkingStopId;
}
