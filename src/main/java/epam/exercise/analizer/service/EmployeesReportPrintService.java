package epam.exercise.analizer.service;

import epam.exercise.analizer.dto.EmployeeReportDto;
import epam.exercise.analizer.report.ReportCriterion;

import java.util.List;
import java.util.Map;

/**
 * The interface EmployeesReportPrintService print report using some input data.
 */
public interface EmployeesReportPrintService {

    /**
     * Print report.
     *
     * @param employeesReportDataByCriteria the employees report data by criteria
     */
    void printReport(Map<ReportCriterion, List<EmployeeReportDto>> employeesReportDataByCriteria);

}
