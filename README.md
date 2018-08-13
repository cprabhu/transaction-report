# transaction-report
An application that creates a CSV report of the transactions based on certain rules

## Prerequisites
Below is the list of things needed to be installed and configured before trying to run the application
- JDK 1.8.*
- Maven 4.0
- Lombok plugin on your IDE (if you are importing project on your IDE).

## Installing the project
- Unzip the project zip and put it in your local workspace
- Import it as an existing Maven project on your favorite IDE (Eclipse or Intellij)

## Running the application
To generate the report, just run the spring-boot application via console(command line) using below command

mvn spring-boot:run

OR 

Run/Debug the TransactionReportApplication as Spring-Boot app on your IDE (Eclipse or IntelliJ)

Note: If you want to rerun the application on console, kill the application manually and follow the same steps to run it again.

### Running the tests
When you clean build the project, maven will ensure the tests are run properly. It can be done via the command 

mvn clean package

Also, as part of mvn spring-boot:run, the tests will run.


## Verifying the results
The Output.csv (with required headers and data) is generated under the output folder in the project (root folder).

Also, the log file is generated in the root folder of the project which is timestamped for easy identification which will look something like log-YYYY-mm-DDTHHmmSS.log
