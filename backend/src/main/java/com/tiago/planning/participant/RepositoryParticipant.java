package com.tiago.planning.participant;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface RepositoryParticipant extends JpaRepository<Participant, UUID> {
    List<Participant> findByTripId(UUID id);
}
