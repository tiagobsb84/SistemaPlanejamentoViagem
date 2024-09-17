package com.tiago.planning.trip;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Table(name = "trips")
@Entity
public class Trip {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @Column(name = "destination", nullable = false)
    private String destination;

    @Column(name = "start_as", nullable = false)
    private LocalDateTime startAs;

    @Column(name = "end_as", nullable = false)
    private LocalDateTime endAs;

    @Column(name = "is_confirmed", nullable = false)
    private Boolean isConfirmed;

    @Column(name = "owner_name", nullable = false)
    private String ownerName;

    @Column(name = "owner_email", nullable = false)
    private String ownerEmail;

    public Trip(TripRequestLoad obj) {
        this.destination = obj.destination();
        this.startAs = LocalDateTime.parse(obj.start_as(), DateTimeFormatter.ISO_DATE_TIME);
        this.endAs = LocalDateTime.parse(obj.end_as(), DateTimeFormatter.ISO_DATE_TIME);
        this.isConfirmed = false;
        this.ownerName = obj.owner_name();
        this.ownerEmail = obj.owner_email();
    }
}
