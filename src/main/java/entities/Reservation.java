package entities;


import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

import java.util.Date;

@Entity
public class Reservation {

    @Id
    int ReservationId;

    Date CheckInDate;

    Date CheckOutDate;

    float TotalPrice;

    @ManyToOne
    @JoinColumn(name = "room_id", nullable = false)
    private Room RoomId;

}
