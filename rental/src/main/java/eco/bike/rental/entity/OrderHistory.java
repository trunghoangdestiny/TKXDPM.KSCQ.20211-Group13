package eco.bike.rental.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class OrderHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private Date startedAt;
    private Date stoppedAt;
    private Date currentRentedTime;

    private Boolean isDone;
    private Boolean isSuccess;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "card_id")
    private Card card;

    private Long bikeId;
    private Long bikeParkingStartId;
    private Long bikeParkingStopId;
}
