Problem Statement: Design a Backend System for ClearFit
Cleartrip is launching ClearFit, a new enterprise application for fitness. Cleartrip is partnering with gyms across Bangalore for its Beta launch. The backend system should support the following functionalities:

Requirements
1. Center Onboarding
   Functionality to onboard centers with details:
   Center Name
   Center Timings
   Workout Variations (e.g., Weights, Cardio, Yoga, Swimming, etc.)
   Each center can have multiple workout variations.
   New workout types may be added in the future.
   2. Defining Workout Slots
      Each center will define workout slots within its operating hours.
      Admin defines these slots daily, and once set, updates are not allowed.
      At any given time, only one workout can be scheduled at a center.
      Each workout slot has fixed seat capacity.
   3. End-User Operations
      Users (customers) can:

(Optional) Register onto the platform (authentication not required).
View workout slot availability for the day:
Filter by workout type (sorted by start time in ascending order).
Filter by workout type and center name (sorted by seats available in ascending order).
Book a workout slot if seats are available.
Cancel a booked workout slot.
(Optional) Notify Me Feature: If a slot is full, users can join a waitlist. If a seat becomes available due to a cancellation, all interested users will be notified (via logs).



Example:
AddCentre(“Koramangala”);
AddCentretimings(“Koramangala”, List<Timings> timings);
// Timings: 6 AM - 9 AM, 6 PM - 9 PM
AddCentreActivities(“Koramangala”, List<String> activities);
// Activities: Weights, Cardio, Yoga, Swimming

AddCentre(“Bellandur”);
AddCentretimings(“Bellandur”, List<Timings> timings);
// Timings: 7 AM - 10 AM, 7 PM - 10 PM
AddCentreActivities(“Bellandur”, List<String> activities);
// Activities: Weights, Cardio, Yoga

addWorkout(“Koramangala”, “Weights”, 6, 7, 100);
addWorkout(“Koramangala”, “Cardio”, 7, 8, 150);
addWorkout(“Koramangala”, “Yoga”, 8, 9, 200);

// Invalid cases:
addWorkout(“Bellandur”, “Weights”, 18, 19, 100); // Not allowed (out of timing)
addWorkout(“Bellandur”, “Swimming”, 19, 20, 100); // Not allowed (workout type not offered)

addWorkout(“Bellandur”, “Weights”, 20, 21, 100);
addWorkout(“Bellandur”, “Cardio”, 19, 20, 20);
addWorkout(“Bellandur”, “Weights”, 21, 22, 100);



register(“Vaibhav”);

// View available slots for Weights
viewWorkoutAvailability(“Weights”);
// Output:
// “Koramangala”, “Weights”, 6, 7, 100
// “Bellandur”, “Weights”, 20, 21, 100
// “Bellandur”, “Weights”, 21, 22, 100

// Book a session
bookSession(“Vaibhav”, “Koramangala”, “Weights”, 6, 7);

// View availability after booking
viewWorkoutAvailability(“Weights”);
// Output:
// “Koramangala”, “Weights”, 6, 7, 99
// “Bellandur”, “Weights”, 20, 21, 100
// “Bellandur”, “Weights”, 21, 22, 100

// Cancel a session
cancelSession(“Vaibhav”, “Koramangala”, “Weights”, 6, 7);

// View availability after cancellation
viewWorkoutAvailability(“Weights”, “Koramangala”);
// Output:
// “Koramangala”, “Weights”, 6, 7, 100


Functional Considerations
Concurrency Handling: Multiple users booking the same slot simultaneously should be handled.
Day-Based Operations: Currently, all operations are for a single day, but design should be extensible for multiple days with minimal changes.
Time Representation: Time can be stored as integers since it's only for a day.
Unique Identifiers: Each entity (center, workout, slot) should have a unique identifier.
Guidelines
Time Limit: 120 mins
Mandatory Programming Language: Java
Driver Program: A main class/test case should be implemented to test the code.
Evaluation Criteria:
Functional correctness & demoable code
Code readability
Proper entity modeling
Modularity & extensibility
Separation of concerns & abstractions
Handling of edge cases
Usage of design patterns (where applicable)
Restrictions:
No external databases (e.g., MySQL) – only in-memory data structures allowed.
No UI required.
API endpoints are not mandatory – methods can be directly invoked from the driver class.