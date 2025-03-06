package org.example.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

@Data
@AllArgsConstructor
@Slf4j
public class User {
    private String name;

    public void update() {
        log.info("User notified");
    }
}
