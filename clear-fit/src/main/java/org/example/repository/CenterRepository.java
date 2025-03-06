package org.example.repository;

import org.example.exception.EntityAlreadyFoundException;
import org.example.exception.EntityDoesNotExistException;
import org.example.model.Center;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public class CenterRepository {
    private final ConcurrentHashMap<String, Center> centers;

    public CenterRepository() {
        this.centers = new ConcurrentHashMap<>();
    }

    public void saveCenter(Center center) throws EntityAlreadyFoundException {
        if (centers.containsKey(center.getName())) {
            throw new EntityAlreadyFoundException("Center with name " + center.getName() + " already exists");
        }
        centers.put(center.getName(), center);
    }

    public Center getCenterByName(String centerName) throws EntityDoesNotExistException {
        if (!centers.containsKey(centerName)) {
            throw new EntityDoesNotExistException("Center with name " + centerName + " doesn't exists");
        }
        return centers.get(centerName);
    }

    public boolean doesCenterExist(String centerName){
        return centers.containsKey(centerName);
    }

    public List<String> getAllCenterNames(){
        return new ArrayList<>(centers.keySet());
    }
}
