package com.tiago.planning.activity;

import com.tiago.planning.trip.Trip;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class ActivityService {

    @Autowired
    private ActivityRepository repository;

    public ActivityResponse registerActivity(ActivityRequestLoad obj, Trip trip) {
        Activity activity = new Activity(obj.title(), obj.occurs_at(), trip);

        this.repository.save(activity);

        return new ActivityResponse(activity.getId());
    }

    public List<ActivityData> getAllActivityFromId(UUID id) {
        return this.repository.findById(id).stream().map(activity -> new ActivityData(
                activity.getId(), activity.getTitle(), activity.getOccursAt()
        )).toList();
    }
}
