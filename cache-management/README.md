Question:
https://leetcode.com/discuss/interview-experience/6366344/Flipkart-or-SDE-3-or-Banglore-or-December-2024-Reject

Description:
Food ordering system
Description:
Implement an online food ordering system. Below are the expected features of the system.


Features:


This system has tie-ups with restaurants, each with a menu with all the items at their prices.
Each restaurant has a maximum processing capacity(food preparation and dispatch) of items at any given time. It will not accept any further item requests until items in processing are completed.
Each restaurant takes some time to prepare and dispatch food. Once the item is fulfilled the system gets the notification of it which adds back to the processing capacity of that restaurant.
One or multiple restaurants can be selected based on the restaurant selection strategy.
Order is accepted from customers only if all the items can be fulfilled by one or more restaurants.
Requirements:


Onboard new restaurant with its menu and item processing capacity. The menu should be reflected in the food ordering system.
A restaurant should be able to change its menu.
Customers should be able to place an order by giving items. We can assume that each item will be of only 1 quantity.
Restaurants should be selected based on the lowest price offered by the restaurant for that item.
The system should be able to keep track of all items served by each restaurant, and the system should be aware of the remaining capacity of each restaurant at a given time.
Once the order is fulfilled by the restaurant, the capacity should be replenished for the given restaurant.
Test-cases:
Below are the sample driver commands that can be demonstrated. This is for the understanding purpose, it does not reflect exact function calls.


add_restaurant("A2B", [Idly for 40Rs, Vada for 30Rs, Paper Plain Dosa for 50Rs], 4)
add_restaurant("Rasaganga", [Idly for 45Rs, Set Dosa for 60Rs, Poori for 25Rs], 6)
add_restaurant("Eat Fit", [Idly 30Rs, Vada for 40Rs], 2)
order(["Idly", "Poori"])
output: Order Id#1: Ordered from "Eat Fit" & "Rasaganga"
order(["Idly", "Vada"])
output: Order Id#2 : Ordered from "Eat Fit" & "A2B"
Print system stats:
output (Restaurant name and current processing power):
A2B: 3
Rasaganga: 5
Eat Fit: 0
order(["Idly"])
output: Ordered from "A2B"   ("Eat Fit" was not selected since there was no further capacity left)
fulfilled_item_for_restaurant("Order Id#1")
Print system stats:
output (Restaurant name and current processing power):
A2B: 2
Rasaganga: 6
Eat Fit: 1
fulfilled_item_for_restaurant("#2")
change_menu("Eat Fit", [Idly 60Rs, Vada for 40Rs], 2)
order(["Idly"])
output: Ordered from "A2B"