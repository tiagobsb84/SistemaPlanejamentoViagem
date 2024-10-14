package com.tiago.planning.activides;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface ActivityRepository extends JpaRepository<Activity, UUID> {
    List<ActivityData> getAllActivityFromId(UUID id);
}
