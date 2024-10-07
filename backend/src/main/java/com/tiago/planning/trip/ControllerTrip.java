package com.tiago.planning.trip;

import com.tiago.planning.participant.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RequestMapping(value = "/trips")
@RestController
public class ControllerTrip {

    @Autowired
    private RepositoryTrip repositoryTrip;

    @Autowired
    private ParticipantService participantService;

    @PostMapping
    public ResponseEntity<TripCreateResponse> createTrip(@RequestBody TripRequestLoad obj) {
        Trip newTrip = new Trip(obj);

        this.repositoryTrip.save(newTrip);

        this.participantService.registerParticipantToTrip(obj.email_to_invite(), newTrip);

        return ResponseEntity.ok(new TripCreateResponse(newTrip.getId()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Trip> getTripDetails(@PathVariable UUID id) {
        Optional<Trip> trip = repositoryTrip.findById(id);

        return trip.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Trip> updateTrip(@PathVariable UUID id, @RequestBody TripRequestLoad obj) {
        Optional<Trip> trip = repositoryTrip.findById(id);

        if(trip.isPresent()) {
            Trip rowTrip = trip.get();
            rowTrip.setDestination(obj.destination());
            rowTrip.setStartAs(LocalDateTime.parse(obj.start_as(), DateTimeFormatter.ISO_DATE_TIME));
            rowTrip.setEndAs(LocalDateTime.parse(obj.end_as(), DateTimeFormatter.ISO_DATE_TIME));

            this.repositoryTrip.save(rowTrip);

            return ResponseEntity.ok(rowTrip);
        }

        return ResponseEntity.notFound().build();
    }

    @GetMapping("/{id}/confirm")
    public ResponseEntity<Trip> confirmTrip(@PathVariable UUID id) {
        Optional<Trip> trip = this.repositoryTrip.findById(id);

        if(trip.isPresent()) {
            Trip rowTrip = trip.get();
            rowTrip.setIsConfirmed(true);

            this.repositoryTrip.save(rowTrip);
            this.participantService.confirmedEmailToParticipants(id);

            return ResponseEntity.ok(rowTrip);
        }

        return ResponseEntity.notFound().build();
    }

    @PostMapping("/{id}/invite")
    public ResponseEntity<ParticipantCreateResponse> inviteParticipant(@PathVariable UUID id, @RequestBody ParticipantRequestLoad obj) {
        Optional<Trip> trip = this.repositoryTrip.findById(id);

        if (trip.isPresent()) {
            Trip rowTrip = trip.get();

            ParticipantCreateResponse participantResponse = this.participantService.registerParticipantToEvent(obj.email(), rowTrip);

            if (rowTrip.getIsConfirmed()) this.participantService.confirmedEmailToParticipant(obj.email());

            return ResponseEntity.ok(participantResponse);
        }

        return ResponseEntity.notFound().build();
    }

    @GetMapping("/{id}/participants")
    public ResponseEntity<List<GetDataParticipants>> getAllParticipantTrip(@PathVariable UUID id) {
        List<GetDataParticipants> listaParticipants = this.participantService.getAllParticipantsTrips(id);

        return  ResponseEntity.ok(listaParticipants);
    }
}
