package epam.exercise.analizer.service.impl;

import epam.exercise.analizer.dto.EmployeeReportDto;
import epam.exercise.analizer.entity.Employee;
import epam.exercise.analizer.report.ReportCriterion;
import epam.exercise.analizer.service.EmployeeAnalyzingService;

import java.util.List;
import java.util.Map;

public class CommonEmployeeAnalyzingService implements EmployeeAnalyzingService {

    @Override
    public Map<ReportCriterion, List<EmployeeReportDto>> analyzeEmployeesByCriteria(Map<Long, Employee> employees, List<ReportCriterion> criteria) {
//        TODO: implement logic
        return null;
    }
}
