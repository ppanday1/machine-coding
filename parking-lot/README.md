Question:

Design a Parking Lot management System:
1. Multiple entry exit points
2. Multiple floor with multiple types of spots
3. User enter and gets a ticket along with assigned spot
4. User exits and gets payment details
5. Admin can enable disable spot/floors for parking
6. Different strategies for parking like nearest available

Optionals:
1. VIP Zones-> Customer priority
2. advance bookings



Core Functionality
1. Create a parking lot.
2. Add floors to the parking lot.
3. Add parking slots to any floor.
4. Multiple entry and exit gates.
5. Vehicles enter, get assigned a ticket with a parking slot.
6. Vehicles exit, make payment, and free the slot.
7. Display available slots per floor for a specific vehicle type.
8. Display all occupied slots per floor for a specific vehicle type.

Vehicle Details
1. Every vehicle has a type, registration number, and color.
2. Supported vehicle types: Car, Bike, Truck.

Parking Slot Rules
1. Each slot type can park only a specific type of vehicle.
2. First available slot allocation is based on:
3. Same type as the vehicle.
4. Lowest possible floor.
5. Lowest slot number on that floor.
6. Slot numbering is serial per floor.


Floor allocation:
1. 1st slot on each floor → Truck
2. Next 2 slots → Bikes
3. Remaining slots → Cars

Additional Features
1. Multiple entry/exit points with independent processing.
2. Admin controls: Enable/disable parking floors or slots.
3. Ticketing Service: Centralized system to track active tickets.
4. Different Parking Strategies:
5. Nearest to Gate
6. Lowest Floor First

Payment System:
Calculates parking fare at exit.
Optional Enhancements
VIP Parking → Priority for specific users.
Advance Booking → Reserve a parking slot before arrival.