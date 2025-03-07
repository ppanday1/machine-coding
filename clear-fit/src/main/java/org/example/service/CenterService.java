package org.example.service;

import lombok.extern.slf4j.Slf4j;
import org.example.exception.TimingForCenterNotDefinedException;
import org.example.model.Activity;
import org.example.model.Center;
import org.example.model.CenterDetail;
import org.example.model.Slot;
import org.example.repository.CenterDetailRepository;
import org.example.repository.CenterRepository;
import org.example.repository.SlotRepository;
import org.example.sortingstrategy.SlotSortingStrategy;
import org.example.sortingstrategy.SortingParam;
import org.example.sortingstrategy.SortingStrategyFactory;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class CenterService {
    private final CenterDetailRepository centerDetailRepository;
    private final CenterRepository centerRepository;
    private final SlotRepository slotRepository;
    private final SortingStrategyFactory sortingStrategyFactory;

    public CenterService(CenterDetailRepository centerDetailRepository,
                         CenterRepository centerRepository,
                         SlotRepository slotRepository,
                         SortingStrategyFactory sortingStrategyFactory) {
        this.centerDetailRepository = centerDetailRepository;
        this.centerRepository = centerRepository;
        this.slotRepository = slotRepository;
        this.sortingStrategyFactory = sortingStrategyFactory;
    }

    public synchronized void addCenter(String name) {
        try {
            Center center = new Center(name);
            centerRepository.saveCenter(center);
        } catch (Exception e) {
            log.error("Error occurred while trying to add center ", e);
        }
    }

    public synchronized void addCenterTiming(String name, List<Pair<Integer, Integer>> timings) {
        if (!checkForConflict(timings)) {
            log.error("Timing conflict found in the input");
            return;
        }
        centerDetailRepository.addCenterTiming(name, timings);
    }

    public synchronized void addActivity(String name, List<String> workouts) {
        List<Activity> activities = new ArrayList<>();
        for (String workoutType : workouts) {
            activities.add(Activity.valueOf(workoutType));
        }
        centerDetailRepository.addCenterActivities(name, activities);
    }

    public synchronized void addCenterWorkout(String name, String activityType, int startTime, int endTime, int slotsAvailable) {
        try {
            Activity activity = Activity.valueOf(activityType);
            // check if center exists
            Center center = centerRepository.getCenterByName(name);

            //check if center offers the activity
            if (!centerDetailRepository.doesCenterOfferActivity(name, activity)) {
                log.error("Center " + name + " doesn't offer activity: " + activityType);
                return;
            }

            //check if time is not outside of center timing
            CenterDetail centerDetail = centerDetailRepository.getCenterDetailByName(name);
            List<Pair<Integer, Integer>> timings = centerDetail.getTimings();
            if (timings == null) {
                throw new TimingForCenterNotDefinedException(" timing for the center is not defined for center " + name);
            }
            if (!isTimingWithinRange(startTime, endTime, timings)) {
                log.error("Specified time is not within center operation time");
                return;
            }

            //update slots only if it is not conflicting with other slot
            List<Slot> slotsForCenter = slotRepository.getAllSlotForCenter(name);
            if (conflictForSlot(slotsForCenter, startTime, endTime)) {
                log.error("Slots are conflicting with existing one");
                return;
            }

            //update slot
            Slot slot = new Slot(name, startTime, endTime, slotsAvailable, activity);
            slotRepository.saveSlot(slot);
        } catch (Exception e) {
            log.error("Exception occurred while trying to update the workout slots", e);
        }
    }

    public synchronized void viewWorkoutAvailability(String workoutType) {
        Activity activity = Activity.valueOf(workoutType);
        List<Slot> slots = slotRepository.getSlotByActivity(activity);
        SlotSortingStrategy slotSortingStrategy = sortingStrategyFactory.getSortingStrategy(SortingParam.TIME);
        List<Slot> sortedSlots = slotSortingStrategy.sort(slots);
        log.info("Available slots are {}", sortedSlots);
    }

    public synchronized void viewWorkoutAvailability(String centerName, String workoutType) {
        Activity activity = Activity.valueOf(workoutType);
        List<Slot> slots = slotRepository.getSlotByCenterAndWorkout(centerName, activity);
        SlotSortingStrategy slotSortingStrategy = sortingStrategyFactory.getSortingStrategy(SortingParam.AVAILABLE_SLOT);
        List<Slot> sortedSlots = slotSortingStrategy.sort(slots);
        log.info("Available slots are {}", sortedSlots);
    }

    private boolean checkForConflict(List<Pair<Integer, Integer>> timings) {
        return true;
    }

    private boolean isTimingWithinRange(int startTime, int endTime, List<Pair<Integer, Integer>> timings) {
        return timings.stream()
                .anyMatch(timing -> timing.getFirst() <= startTime && timing.getSecond() >= endTime);
    }

    private boolean conflictForSlot(List<Slot> slots, int startTime, int endTime) {
        return slots.stream()
                .anyMatch(slot -> slot.getStartTime() < endTime && slot.getEndTime() > startTime);
    }
}
