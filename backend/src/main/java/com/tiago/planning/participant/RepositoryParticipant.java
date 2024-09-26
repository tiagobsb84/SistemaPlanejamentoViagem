package com.tiago.planning.participant;

import jakarta.servlet.http.Part;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface RepositoryParticipant extends JpaRepository<Participant, UUID> {
}
