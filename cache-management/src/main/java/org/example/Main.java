package org.example;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.time.LocalDateTime;
import java.time.Year;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.util.Locale;

@SpringBootApplication
public class Main {
    public static void main(String[] args) {

        System.out.println("Hello world!");
        SpringApplication.run(Main.class, args);
        EvictionPolicy<String> evictionPolicy = new LruEvictionPolicy<>();
        DataStore<String, String> dataStore = new InMemoryDataStore<>();
        Cache<String, String> cache = new Cache<>(dataStore, evictionPolicy, 3);
        System.out.println(cache.get("key1"));
        cache.put("key1","value1");
        cache.put("key2","value2");
        cache.put("key3","value3");
        cache.put("key4","value4");
        System.out.println(cache.get("key1"));
        System.out.println(cache.get("key2"));
        cache.put("key1","value1");
        System.out.println(cache.get("key2"));
        System.out.println(cache.get("key3"));
    }
}