package com.tiago.planning.participant;

import com.tiago.planning.trip.Trip;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class ParticipantService {

    @Autowired
    private RepositoryParticipant repositoryParticipant;

    public void registerParticipantToTrip(List<String> participantsToInvite, Trip trip){
        List<Participant> participants = participantsToInvite.stream().map(email -> new Participant(email, trip)).toList();

        this.repositoryParticipant.saveAll(participants);

        System.out.println(participants.get(0).getId());
    }

    public void confirmedEmailToParticipant(UUID tripId){}
}
