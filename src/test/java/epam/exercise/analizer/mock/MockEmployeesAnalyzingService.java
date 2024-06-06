package epam.exercise.analizer.mock;

import epam.exercise.analizer.dto.EmployeeInputDto;
import epam.exercise.analizer.dto.EmployeeReportDto;
import epam.exercise.analizer.report.ReportCriterion;
import epam.exercise.analizer.service.EmployeesAnalyzingService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Mock implementation of {@link MockEmployeesAnalyzingService} as I can't use a Mockito :(
 */
public class MockEmployeesAnalyzingService implements EmployeesAnalyzingService {
    @Override
    public Map<ReportCriterion, List<EmployeeReportDto>> analyzeEmployees(Map<Long, EmployeeInputDto> employees) {
        Map<ReportCriterion, List<EmployeeReportDto>> result = new HashMap<>();
        EmployeeInputDto employee = employees.get(1L);
        EmployeeReportDto reportDto = new EmployeeReportDto(employee.id(),
                employee.firstName(),
                employee.lastName(),
                ReportCriterion.UNDERPAID,
                0.1f);
        result.put(ReportCriterion.UNDERPAID, List.of(reportDto));
        return result;
    }
}
