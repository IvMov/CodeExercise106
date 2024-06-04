package epam.exercise.analizer.service;

import epam.exercise.analizer.dto.EmployeeReportDto;
import epam.exercise.analizer.entity.Employee;
import epam.exercise.analizer.report.ReportCriterion;

import java.util.List;
import java.util.Map;


public interface EmployeeAnalyzingService {

    Map<ReportCriterion, List<EmployeeReportDto>> analyzeEmployeesByCriteria(Map<Long, Employee> employees, List<ReportCriterion> criteria);
}
