package org.example.repository;

import org.example.model.Comment;
import org.springframework.stereotype.Repository;

import java.util.concurrent.ConcurrentHashMap;

@Repository
public class CommentRepository {
    //TO:DO
    private ConcurrentHashMap<String, Comment> comments;

    public CommentRepository() {
        this.comments = new ConcurrentHashMap<>();
    }

//    public void
}
