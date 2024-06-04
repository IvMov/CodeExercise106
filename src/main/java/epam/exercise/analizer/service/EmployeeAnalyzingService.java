package epam.exercise.analizer.service;

import epam.exercise.analizer.dto.EmployeeReportDto;
import epam.exercise.analizer.dto.EmployeeInputDto;
import epam.exercise.analizer.report.ReportCriterion;

import java.util.List;
import java.util.Map;


public interface EmployeeAnalyzingService {

    Map<ReportCriterion, List<EmployeeReportDto>> analyzeEmployeesByCriteria(Map<Long, EmployeeInputDto> employees, List<ReportCriterion> criteria);
}
