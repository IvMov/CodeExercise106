package epam.exercise.analizer.service.impl;

import epam.exercise.analizer.dto.EmployeeInputDto;
import epam.exercise.analizer.dto.EmployeeReportDto;
import epam.exercise.analizer.report.ReportCriterion;
import epam.exercise.analizer.service.EmployeesAnalyzingService;
import epam.exercise.analizer.service.EmployeesReportService;
import epam.exercise.analizer.service.EmployeesFileReaderService;
import epam.exercise.analizer.service.EmployeesReportPrintService;

import java.util.List;
import java.util.Map;

/**
 * The implementation of {@link EmployeesReportService}
 */
public class CommonEmployeesReportService implements EmployeesReportService {

    private final EmployeesFileReaderService employeesFileReaderService;
    private final EmployeesAnalyzingService employeesAnalyzingService;
    private final EmployeesReportPrintService employeesReportPrintService;

    /**
     * Instantiates a new CommonEmployeesReportService.
     *
     * @param employeesFileReaderService  the employees file reader service
     * @param employeesAnalyzingService   the employees analyzing service
     * @param employeesReportPrintService the employees report print service
     */
    public CommonEmployeesReportService(EmployeesFileReaderService employeesFileReaderService,
                                        EmployeesAnalyzingService employeesAnalyzingService,
                                        EmployeesReportPrintService employeesReportPrintService) {
        this.employeesFileReaderService = employeesFileReaderService;
        this.employeesAnalyzingService = employeesAnalyzingService;
        this.employeesReportPrintService = employeesReportPrintService;
    }

    public void createEmployeesReport() {
        Map<Long, EmployeeInputDto> employees = employeesFileReaderService.readEmployeesFile();
        Map<ReportCriterion, List<EmployeeReportDto>> reportData = employeesAnalyzingService.analyzeEmployees(employees);
        employeesReportPrintService.printReport(reportData);
    }
}
