# transaction-report
An application that creates a CSV report of the transactions based on certain rules

## Installing the project
- Unzip the project zip and put it in your local workspace
- Import it as an existing Maven project on your favorite IDE (Eclipse or Intellij)

## Running the application
To generate the report, just run the spring-boot application via command line using below command

mvn spring-boot:run

OR 

Run/Debug the TransactionReportApplication as Spring-Boot app on your IDE (Eclipse or IntelliJ)

## Verifying the results
The Output.csv (with required headers and data) is generated under the output folder in the project (root folder).

Also, the log file is generated in the root folder of the project which is timestamped for easy identification which will look something like log-YYYY-mm-DDTHHmmSS.log
