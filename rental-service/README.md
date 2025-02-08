Question:
https://leetcode.com/discuss/interview-experience/1400137/flipkart-machine-coding-sde-3-bangalore-august-2021-reject

Description:
Flipkart is starting a new vehicle rental service called FlipKar. In this service, we will rent different kinds of vehicles such as cars and bikes.


Features:


Rental service has multiple branches throughout the city.
Each branch has a limited number of different kinds of vehicles.
Each vehicle can be booked with a predefined price per unit time slot.
For simplicity, the current pricing model does not support dynamic pricing
or update on prices based on seasonality.
Each vehicle can be booked in multiples of 1 hour time slot.
All bookings should be made before the start time of particular booking.
Requirements:


Onboard a new branch with available vehicles.
Onboard new vehicle(s) of existing type to a particular branch.
Rent vehicle for a time slot and a vehicle type
(lowest price as the default choice of selection of vehicle, this should be extendable to any other strategy).
While booking a vehicle if availability is not there, then it should fallback to another available branch,
which is derived based on the vehicle selection strategy.
A system view should be made available, such as currently blocked vehicles,
available vehicles of all the branches.
Bonus:


While booking a vehicle, if 2 branches are providing vehicle at the same price,
give priority to the nearest branch.
Other Notes:


Do not use any database or NoSQL store, use in-memory data-structure for now.
Do not create any UI for the application.
Write a driver class for demo purpose. Which will execute all the commands at one place in the code and have test cases.
Please prioritize code compilation, execution and completion.
Work on the expected output first and then add good-to-have features of your own.
Expectations:


Make sure that you have working and demonstrable code.
Make sure that code is functionally correct.
Make sure concurrent requests are handled appropriately.
Code should be modular and readable.
Separation of concern should be addressed.
Code should easily accommodate new requirements with minimal changes.
Code should be easily testable. Demo with Unit Tests
Test cases:


•	add_branch(‘koramangala’, [“1 suv for Rs.12 per hour”, “3 sedan for Rs.10 per hour”, “3 bikes for Rs.20 per hour”]);
•	add_branch(‘jayanagar’, [“3 sedan for Rs.11 per hour”, “3 bikes for Rs.30 per hour”, “4 hatchback for Rs.8 per hour”]);
•	add_branch(‘malleshwaram’, [“1 suv for Rs.11 per hour”, “10 bikes for Rs.3 per hour” , “3 sedan for Rs.10 per hour”]);
•	add_vehicle(‘koramangala’,  “1 sedan”); //add 1 sedan to koramangala
•	rent_vehicle(‘suv’, 20th Feb 10:00 PM, 20th Feb 12:00 PM); // should book from malleshwaram.
•	rent_vehicle(‘suv’, 20th Feb 10:00 PM, 20th Feb 12:00 PM); // should book from koramangala.
•	rent_vehicle(‘suv’, 20th Feb 10:00 PM, 20th Feb 12:00 PM); //Should fail saying no vehicle.
•	print_system_view_for_time_slot(20th Feb 11:00 PM, 20th Feb 12:00 PM);

o	Output:
	‘Koramangala’:
	All “suv” are booked.
	“sedan” is available for Rs.10
	“bike” is available for Rs.20
	‘Jayanagar’:
	“sedan” is available for Rs.11
	“bike” is available for Rs.30
	“hatchback” is available for Rs.8
	‘‘Malleshwaram’’:
	All “suv” are booked.
	“bike” is available for Rs.3
	“sedan” is available for Rs.10