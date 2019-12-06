# Ticket Amature

The Ticket Amature application is a desktop event finding application that utilizies the TicketMaster API to locate events across the U.S. and locally.
It is the class project of four individuals enrolled at Champlain College:
Christopher Dierolf,
Tom Much,
Neil Hart, and
Stephen Greybeal
<br>
Users of the Ticket Amature application can create an account, log in, search for events by keywords and/or postal code, select seats, faux purchase events (no actual credit card ransactions actually occur), and have the "tickets" sent to their email. Because utilizing the TicketMaster API's Venue features is well beyond the scope of this project, "Venues" are randomly rendered. <br>
After purchase, the events are now listed on the app's landing page where the user can quickly view the details of their purchase and resend the tickets to their email address. The email server used is a simple gmail account open for access from the app.

## Getting Started

Ticket Amature utilizes JavaFX 11 and Java 8 with a MSSQL backend.
<br>

1. Download or Clone the repository
2. Utilize the provided MSSQL Server build script
3. Create a resource file called "TicketManager.properties" in a folder called "resources" in the src file.
4. Fill in the following data in the TicketManager.properties file:<br>
   dbIpaddress=<br>
   dbUsername=<br>
   dbPassword=<br>
   dbName=<br>
   dbPort=<br>
   sendEmailAddress=<br>
   sendEmailPassword=<br>
   apiKey=<br>
   apiBaseUrl=<br>
5. Run the app!

For database systems other than MSSQL, utilize the data dictionary and ERD.

### Prerequisites

JavaFX 11.0.1
Java 8.0.191
MSSQL Server

### Dependencies

1. commons-logging-1.2.jar
2. gson-2.8.5.jar
3. java-json.jar
4. java-mail-1.4.4.jar
5. log4j-1.2.17.jar
6. mssql-jdbc-6.4.0.jre8.jar
7. commons-dbcp2-2.5.0.jar
8. commons-pool2-2.6.0.jar
