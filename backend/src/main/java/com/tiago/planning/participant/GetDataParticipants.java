package com.tiago.planning.participant;

import java.util.UUID;

public record GetDataParticipants(UUID id, String name, String email, Boolean isConfirmed) {
}
