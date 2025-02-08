package org.example.strategy;

import org.example.model.Slot;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;

@Service
public class DefaultRankingStrategy implements RankingStrategy {

    @Override
    public List<Slot> getRanking(List<Slot> slots) {
        slots.sort(Comparator.comparing(Slot::getLocalTime));
        return slots;
    }
}
