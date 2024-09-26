package com.tiago.planning.participant;

import jakarta.servlet.http.Part;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping(value = "/participants")
public class ControllerParticipant {

    @Autowired
    private RepositoryParticipant repositoryParticipant;

    @PostMapping("/{id}/confirm")
    public ResponseEntity<Participant> confirmParticipant(@PathVariable UUID id, @RequestBody ParticipantRequestLoad obj){
        Optional<Participant> participant = this.repositoryParticipant.findById(id);

        if(participant.isPresent()) {
            Participant rowParticipant = participant.get();
            rowParticipant.setIsConfirmed(true);
            rowParticipant.setName(obj.name());

            this.repositoryParticipant.save(rowParticipant);

            return ResponseEntity.ok(rowParticipant);
        }
        
        return ResponseEntity.notFound().build();
    }
}
