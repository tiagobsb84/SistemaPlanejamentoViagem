package com.tiago.planning.participant;

import com.tiago.planning.trip.Trip;
import com.tiago.planning.trip.TripCreateResponse;
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

    public ParticipantCreateResponse registerParticipantToEvent(String email, Trip trip) {
        Participant newParticipant = new Participant(email, trip);
        this.repositoryParticipant.save(newParticipant);

        return new ParticipantCreateResponse(newParticipant.getId());
    }

    public void confirmedEmailToParticipants(UUID id){}

    public void confirmedEmailToParticipant(String email){}

    public List<GetDataParticipants> getAllParticipantsTrips(UUID id) {
        return this.repositoryParticipant.findByTripId(id).stream().map(participant -> new GetDataParticipants(
                participant.getId(), participant.getName(), participant.getEmail(), participant.getIsConfirmed())).toList();
    }
}
