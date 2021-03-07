Title of application:     Appointment Scheduler
Application Version/Date: 2.0 2020/11

Purpose:      To make a scheduling GUI application that reads and writes to and from a database.
Author:       Frank Morales
Contact Info: (325)227-3083
              fmora26@wgu.edu


DEVELOPMENT ENVIRONMENT INFORMATION
IDE used:   IntelliJ IDEA 2020.2.3 (Community Edition)
JDK:        Java SE 11.0.9
JavaFX-SDK: javafx-sdk-11.0.2

INSTRUCTIONS ON HOW TO USE THE PROGRAM:
Login Page: When the program first starts, it will initiate an information dialog box indicating whether
            or not an appointment is coming up in 15 minutes. After pressing OK, please proceed to enter
            the correct credentials, username and password, to be let into the program. Everytime someone
            attempts to log in, the program writes to a text file in the src/files folder the time and date
            of login attempt, as well as if it was successful or unsuccessful. The text file is called
            login_attempts.txt.
Main Page:  Once logged in, the main page will display a table of Customers and another table of Appointments.
            The Add buttons take you to a form where you can create a new customer or appointment. The Update
            buttons let you make any changes to a particular customer or appointment. The Delete buttons lets
            you delete a customer or appointment from the database and after confirming your decision in the
            Confirmation Dialog Box, that customer or appointment will be removed from the table and the
            database.
Reports:    The reports button takes you to the Reports Form, where you can see 3 different reports based on
            the radio button selection at the top.

Report 1:   Reports the total number of customer appointments by type and month.

Report 2:   Shows a schedule for each contact in your organization that includes appointment ID, title, type
            and description, start date and time, end date and time, and customer ID.

Report 3:   Display all the past appointments.