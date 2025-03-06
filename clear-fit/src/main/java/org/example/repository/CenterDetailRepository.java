package org.example.repository;

import org.example.exception.EntityDoesNotExistException;
import org.example.model.Activity;
import org.example.model.CenterDetail;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Repository;

import java.util.HashSet;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public class CenterDetailRepository {
    private final ConcurrentHashMap<String, CenterDetail> centerDetails; //Center PK to it's daily details

    public CenterDetailRepository() {
        this.centerDetails = new ConcurrentHashMap<>();
    }

    public synchronized void addCenterActivities(String centerName, List<Activity> activities) {
        CenterDetail centerDetail = centerDetails.computeIfAbsent(centerName, k -> new CenterDetail());
        centerDetail.setActivities(new HashSet<>(activities));
        centerDetail.setName(centerName);
        centerDetails.put(centerName, centerDetail);
    }

    public synchronized void addCenterTiming(String centerName, List<Pair<Integer, Integer>> timings) {
        CenterDetail centerDetail = centerDetails.computeIfAbsent(centerName, k -> new CenterDetail());
        centerDetail.setTimings(timings);
        centerDetail.setName(centerName);
        centerDetails.put(centerName, centerDetail);
    }

    public synchronized boolean doesCenterOfferActivity(String name, Activity activity) throws EntityDoesNotExistException {
        if (!centerDetails.containsKey(name)) {
            throw new EntityDoesNotExistException("Center with name " + name + " doesn't exists");
        }
        CenterDetail centerDetail = centerDetails.get(name);
        HashSet<Activity> activities = centerDetail.getActivities();
        return activities.contains(activity);
    }

    public synchronized CenterDetail getCenterDetailByName(String name) throws EntityDoesNotExistException {
        if (!centerDetails.containsKey(name)) {
            throw new EntityDoesNotExistException("Center with name " + name + " doesn't exists");
        }
        return centerDetails.get(name);
    }
}
