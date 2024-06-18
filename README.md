# Organizational Structure Analysis Tool

## Application Name:
Employee Analyzer

## Purpose:
This application analyzes a company's organizational structure and identifies potential improvements. Specifically, it checks:
1. Managers' salaries relative to their direct subordinates.
2. Reporting line lengths to ensure they are not excessively long (there must be not more than N managers between employee and CEO).

## How to Use:
TODO: add description where filename located
1. **Prepare data**: Place the `*.csv` (by default `employees.csv`) file containing employees data in the `resources` folder of the project. 
You can create subfolders for file path. The file must be same structure as shown below. 
Some requirements for `*.csv`:
   - The file should contain only ONE person without a manager ID, who is assumed to be the CEO.
   - The file should contain no empty lines
   - Salaries can't be negative numbers
   - Employee can't have its own id as managerId
   - All employees have reachable path to CEO
      ### Example CSV File Format:
          Id,firstName,lastName,salary,managerId
          123,Joe,Doe,60000,
          124,Martin,Chekov,45000,123
          125,Bob,Ronstad,47000,123
          300,Alice,Hasacat,50000,124
          305,Brett,Hardleaf,34000,300
2. **Configure application**: By default application use configuration when file placed in root of `resources` and file named as `employees.csv`, 
you can change this behavior by modifying `application.properties`.
   ### Default configuration of `application.properties`:
       app.employees_file_path = / 
       app.employees_file_name = employees.csv
   ***Example:*** *If file placed in subfolder and have path like `resources/subfolder/report_data.csv`, then please adjust `application.properties`.*
   ### Adjusted configuration `application.properties`:
       app.employees_file_path = subfolder/
       app.employees_file_name = report_data.csv
3. **Run the Program**: Execute the application. It will read the `*.csv` file, process the data in memory, and generate a console report.
4. **Output Report**: The application will output a report or will print error message with reason of fail:
    - Managers earning less than they should (at least 20% more than their direct subordinates average salary), and by how much less. 
    - Managers earning more than they should (no more than 50% more than their direct subordinates average salary), and by how much more. 
    - Employees with more than 4 managers between them and the CEO, and by how much more.

## Assumptions:
1. **CSV Data**: 
    - The application expects the `*.csv` file to be located in the `resources` folder or subfolders. 
This is a simplification for the test task and not recommended for a production environment. 
Assumption used because no direct instruction regarding file location was placed in task (for instance `\Users\%USERNAME%\SomeReportFolder`, etc).
    - Basic validation is implemented, but **not cover all cases as it should be a part of requirements related to business requirements**.
    - Invalid CSV lines will cause the entire report to fail to maintain data consistency of report.
2. **Logging**:
    - The logger is used for logging errors and other information.
    - The report output is directly sent to the console using common `System.out.println()`.


By following these instructions, you can use the `Organizational Structure Analysis Tool` to evaluate and improve your company's organizational structure effectively.

