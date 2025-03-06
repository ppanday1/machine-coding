package org.example.model;

import lombok.Data;
import org.springframework.data.util.Pair;

import java.util.HashSet;
import java.util.List;

@Data
public class CenterDetail {
    private String name; //primary key for join
    private HashSet<Activity> activities; //considering you can offer different activity of different day in future
    private List<Pair<Integer, Integer>> timings;
}
