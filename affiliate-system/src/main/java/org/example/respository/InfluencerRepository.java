package org.example.respository;

import org.example.exception.ElementAlreadyExistException;
import org.example.exception.ElementDoesNotExistException;
import org.example.models.Influencer;
import org.springframework.stereotype.Repository;

import java.util.concurrent.ConcurrentHashMap;

@Repository
public class InfluencerRepository {
    private final ConcurrentHashMap<String, Influencer> influencers;

    public InfluencerRepository() {
        influencers = new ConcurrentHashMap<>();
    }

    public synchronized void saveInfluencer(Influencer influencer) throws ElementAlreadyExistException {
        if (influencers.containsKey(influencer.getName())) {
            throw new ElementAlreadyExistException("Influencer already " + influencer.getName() + " exists");
        }
        influencers.put(influencer.getName(), influencer);
    }

    public synchronized Influencer getInfluencerByName(String name) throws ElementDoesNotExistException {
        if (!influencers.containsKey(name)) {
            throw new ElementDoesNotExistException("Influencer " + name + " does not exists");
        }
        return influencers.get(name);
    }
}
