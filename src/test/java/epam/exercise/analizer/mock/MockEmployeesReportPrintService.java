package epam.exercise.analizer.mock;

import epam.exercise.analizer.dto.EmployeeReportDto;
import epam.exercise.analizer.report.ReportCriterion;
import epam.exercise.analizer.service.EmployeesReportPrintService;

import java.util.List;
import java.util.Map;

/**
 * Mock implementation of {@link EmployeesReportPrintService} as I can't use a Mockito :(
 */
public class MockEmployeesReportPrintService implements EmployeesReportPrintService {

    @Override
    public void printReport(Map<ReportCriterion, List<EmployeeReportDto>> employeesReportDataByCriteria) {
        employeesReportDataByCriteria.get(ReportCriterion.UNDERPAID).forEach(System.out::println);
    }
}
