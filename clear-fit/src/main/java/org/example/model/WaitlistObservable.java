package org.example.model;

public interface WaitlistObservable {
    void addObserver(WaitlistObserver waitlistObserver);
    WaitlistObserver notifyObserver();
}
