package epam.exercise.analizer;

import epam.exercise.analizer.mapper.EmployeeMappeImpl;
import epam.exercise.analizer.mapper.EmployeeMapper;
import epam.exercise.analizer.service.*;
import epam.exercise.analizer.service.impl.*;

/**
 * The EmployeeAnalyzer application allow to get custom employees report from csv file.
 * More specific information in readme.md file.
 */
public class EmployeeAnalyzer {
    private static final String PROPERTIES_FILE_NAME = "application.properties";

    /**
     * The entry point of application.
     */
    public static void main(String[] args) {
        PropertiesService propertiesService = new AppPropertiesService(PROPERTIES_FILE_NAME);
        EmployeesFileReaderService employeesFileReaderService = new CsvEmployeesFileReaderService(propertiesService);
        EmployeeMapper employeeMapper = new EmployeeMappeImpl();
        EmployeesAnalyzingService employeesAnalyzingService = new CommonEmployeesAnalyzingService(employeeMapper);
        EmployeesReportPrintService employeesReportPrintService = new ConsoleEmployeesReportPrintService();
        EmployeesReportService employeesReportService = new CommonEmployeesReportService(employeesFileReaderService, employeesAnalyzingService, employeesReportPrintService);

        employeesReportService.createEmployeesReport();
    }
}
