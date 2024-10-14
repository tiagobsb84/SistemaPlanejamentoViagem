package com.tiago.planning.activides;

import com.tiago.planning.trip.Trip;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ActivityService {

    @Autowired
    private ActivityRepository repository;

    public ActivityResponse registerActivity(ActivityRequestLoad obj, Trip trip) {
        Activity activity = new Activity(obj.title(), obj.occurs_at(), trip);

        this.repository.save(activity);

        return new ActivityResponse(activity.getId());
    }
}
