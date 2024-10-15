package com.tiago.planning.trip;

import com.tiago.planning.activity.ActivityData;
import com.tiago.planning.activity.ActivityRequestLoad;
import com.tiago.planning.activity.ActivityResponse;
import com.tiago.planning.activity.ActivityService;
import com.tiago.planning.link.*;
import com.tiago.planning.participant.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RequestMapping(value = "/trips")
@RestController
public class TripController {

    @Autowired
    private RepositoryTrip repositoryTrip;

    @Autowired
    private ParticipantService participantService;

    @Autowired
    private ActivityService activityService;

    @Autowired
    private LinkService linkService;

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

    @PostMapping("/{id}/activities")
    public ResponseEntity<ActivityResponse> registerActivity(@PathVariable UUID id, @RequestBody ActivityRequestLoad obj) {
        Optional<Trip> trip = this.repositoryTrip.findById(id);

        if(trip.isPresent()) {
            Trip rowTrip = trip.get();

            ActivityResponse activityResponse = this.activityService.registerActivity(obj, rowTrip);

            return ResponseEntity.ok(activityResponse);
        }

        return ResponseEntity.notFound().build();
    }

    @GetMapping("/{id}/activities")
    public ResponseEntity<List<ActivityData>> getAllActivity(@PathVariable UUID id) {
        List<ActivityData> activityData = this.activityService.getAllActivityFromId(id);

        return ResponseEntity.ok(activityData);
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

    // Links
    @PostMapping("/{id}/links")
    public ResponseEntity<LinkResponse> registerLinks(@PathVariable UUID id, @RequestBody LinkResponseLoad obj) {
        Optional<Trip> trip = this.repositoryTrip.findById(id);

        if(trip.isPresent()) {
            Trip rowTrip = trip.get();

            LinkResponse linkResponse = this.linkService.registerLink(obj, rowTrip);

            return ResponseEntity.ok(linkResponse);
        }

        return ResponseEntity.notFound().build();
    }

    @GetMapping("/{id}/links")
    public ResponseEntity<List<LinkData>> getAllLinks(@PathVariable UUID id) {
        List<LinkData> linkData = this.linkService.getAllLinks(id);

        return ResponseEntity.ok(linkData);
    }
}
