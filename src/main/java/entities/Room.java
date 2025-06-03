package entities;

import jakarta.persistence.*;

import java.util.List;

public class Room {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long Room;

    Boolean Availability;

    @OneToMany(mappedBy = "room", cascade = CascadeType.ALL)
    private List<Reservation> reservations;

}
