package com.tiago.planning.trip;

import java.util.List;

public record TripRequestLoad(String destination, String start_as, String end_as, List<String> email_to_invite, String owner_name, String owner_email) {
}
