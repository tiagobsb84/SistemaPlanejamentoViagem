package com.tiago.planning.participant;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class ParticipantService {

    public void registerParticipantToTrip(List<String> participantsToInvite, UUID tripId){}

    public void confirmedEmailToParticipant(UUID tripId){}
}
