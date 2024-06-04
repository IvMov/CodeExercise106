package epam.exercise.analizer.service;

import epam.exercise.analizer.dto.EmployeeReportDto;
import epam.exercise.analizer.report.ReportCriterion;

import java.util.List;
import java.util.Map;

public interface PrintReportService {

    void printReport(Map<ReportCriterion, List<EmployeeReportDto>> employeesByCriteria);

}
