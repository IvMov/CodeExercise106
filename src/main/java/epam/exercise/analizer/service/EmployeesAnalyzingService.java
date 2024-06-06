package epam.exercise.analizer.service;

import epam.exercise.analizer.dto.EmployeeReportDto;
import epam.exercise.analizer.dto.EmployeeInputDto;
import epam.exercise.analizer.report.ReportCriterion;

import java.util.List;
import java.util.Map;


/**
 * The interface EmployeesAnalyzingService.
 */
public interface EmployeesAnalyzingService {

    /**
     * Analyze employees map.
     *
     * @param employees the employees
     * @return the map
     */
    Map<ReportCriterion, List<EmployeeReportDto>> analyzeEmployees(Map<Long, EmployeeInputDto> employees);
}
