package org.example.strategy;

import org.example.model.Slot;

import java.util.List;

public interface RankingStrategy {
    List<Slot> getRanking(List<Slot> slots);
}
