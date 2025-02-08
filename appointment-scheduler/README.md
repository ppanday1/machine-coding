https://leetcode.com/discuss/interview-experience/5650840/Flipkart-or-SDE-2-or-Bengaluru-or-July-2024-or-Offer/

You are required to build an application that lets patients connect to doctors and book appointments. The day is divided into time slots of 30 mins each, starting from 9 am to 9 pm. Doctors can login to the portal and declare their availability for the given day in terms of slots. Patients can login and book appointments/ cancel existing appointments.
For simplicity you can assume that the doctors’ availability is declared for that particular day only.

Functionalities required:
A new doctor should be able to register, and mention his/her speciality among (Cardiologist, Dermatologist, Orthopedic, General Physician)
A doctor should be able to declare his/her availability in each slot for the day. For example, the slots will be of 30 mins like 9am-9.30am, 9.30am-10am..
Patients should be able to login, and search available slots based on speciality.  
The slots should be displayed in a ranked fashion. Default ranking strategy should be to rank by start time. But we should be able to plugin more strategies like Doctor’s rating etc in future.
Patients should be able to book appointments with a doctor for an available slot.A patient can book multiple appointments in a day.  A patient cannot book two appointments with two different doctors in the same time slot.
Patients can also cancel an appointment, in which case that slot becomes available for someone else to book.
Build a waitlist feature:
If the patient wishes to book a slot for a particular doctor that is already booked, then add this patient to the waitlist. If the patient with whom the appointment is booked originally, cancels the appointment, then the first in the waitlist gets the appointment.
A patient/doctor should be able to view his/her booked appointments for the day.  
Doctors can’t provide overlapping slots.
Implementing login feature is optional
Patient registration is not mandatory
Name of Doctor and patient are their identifiers