package com.tiago.planning.trip;

import com.tiago.planning.participant.ParticipantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping(value = "/trips")
@RestController
public class ControllerTrip {

    @Autowired
    private RepositoryTrip repositoryTrip;

    @Autowired
    private ParticipantService participantService;

    @PostMapping
    public ResponseEntity<String> createTrip(@RequestBody TripRequestLoad obj) {
        Trip newTrip = new Trip(obj);

        this.repositoryTrip.save(newTrip);

        this.participantService.registerParticipantToTrip(obj.email_to_invite(), newTrip.getId());

        return ResponseEntity.ok("sucess!");
    }
}