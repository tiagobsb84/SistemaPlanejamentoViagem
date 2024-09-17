package com.tiago.planning.trip;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface RepositoryTrip extends JpaRepository<Trip, UUID> {
}
