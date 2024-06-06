# Organizational Structure Analysis Tool

## Application Name:
Employee Analyzer

## Purpose:
This application analyzes a company's organizational structure and identifies potential improvements. Specifically, it checks:
1. Managers' salaries relative to their direct subordinates.
2. Reporting line lengths to ensure they are not excessively long.

## How to Use:
1. **Prepare data**: Place the `employees.csv` file containing employees data in the `resources` folder of project. The file must be same structure as shown below.
    ### Example CSV File Format:
        Id,firstName,lastName,salary,managerId
        123,Joe,Doe,60000,
        124,Martin,Chekov,45000,123
        125,Bob,Ronstad,47000,123
        300,Alice,Hasacat,50000,124
        305,Brett,Hardleaf,34000,300

2. **Run the Program**: Execute the application. It will read the `employees.csv` file, process the data in memory, and generate a console report.
3. **Output Report**: The application will output a report:
    - Managers earning less than they should (at least 20% more than their direct subordinates average salary), and by how much less. 
    - Managers earning more than they should (no more than 50% more than their direct subordinates average salary), and by how much more. 
    - Employees with more than 4 managers between them and the CEO, and by how much more.

## Assumptions:
1. **CSV Data**: 
    - The application expects the `employees.csv` file to be located in the `resources` folder. This is a simplification for the test task and not recommended for a production environment.
    - Basic validation is implemented, but not cover all cases as it should be a part of requirements.
    - The file should contain only ONE person without a manager ID, who is assumed to be the CEO.
    - Invalid CSV lines will cause the entire report to fail to maintain data consistency of report.
2. **Salary Range**:
    - It's assumed that no employee earns more than 999,999.
    - All calculations will be performed with high accuracy, to determine even 1 point more or less salary deviation.
3. **Report explanation**:
    - It's decided to use percentage representation of salary deviation from some thresholds in condition of "by how much", as it's more informative in report purposes.
    - About `by how much` shown difference between threshold and real value in percentage - cause it more informative and not described in requirements. For instance: not more than 10%, avg: 100, salary: 111, so result will be 1.0000%.
4. **Logging**:
    - The logger is used for logging errors and other information.
    - The report output is directly sent to the console using common `System.out.println()`.


By following these instructions, you can use the `Organizational Structure Analysis Tool` to evaluate and improve your company's organizational structure effectively.

