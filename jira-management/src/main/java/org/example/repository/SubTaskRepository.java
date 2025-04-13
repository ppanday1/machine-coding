package org.example.repository;

import org.example.model.SubTask;
import org.springframework.stereotype.Repository;

import java.util.concurrent.ConcurrentHashMap;

@Repository
public class SubTaskRepository {

    private final ConcurrentHashMap<String, SubTask> subtasks;

    public SubTaskRepository(ConcurrentHashMap<String, SubTask> subtasks) {
        this.subtasks = subtasks;
    }

    public void addSubtask(SubTask subTask) {
        subtasks.putIfAbsent(subTask.getTitle(), subTask);
    }

    public SubTask getSubTask(String title) {
        return subtasks.get(title);
    }

    public boolean subTaskExist(String title) {
        return subtasks.containsKey(title);
    }

    public void deleteSubTask(String title) {
        subtasks.remove(title);
    }
}
