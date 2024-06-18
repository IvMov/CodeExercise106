package epam.exercise.analizer.dto;

import epam.exercise.analizer.report.ReportCriterion;

/**
 * Represents a simple output employee report data,
 * contains all necessary data to identify and represent employee in report purposes
 *
 * @param id              the unique id identifier of employee
 * @param firstName       first name of employee
 * @param lastName        last name of employee
 * @param reportCriterion the criterion of type {@link ReportCriterion} - reason why employee was added to report
 * @param criterionValue  the value of deviation from some threshold criterion
 */
public record EmployeeReportDto(Long id, String firstName, String lastName, ReportCriterion reportCriterion,
                                Float criterionValue) {

    @Override
    public String toString() {
        return reportCriterion != ReportCriterion.TOO_LONG_REPORTING_LINE
                ? String.format("Employee: id: %d, first name: %s, last name: %s, deviation: %.2f", id, firstName, lastName, criterionValue)
                : String.format("Employee: id: %d, first name: %s, last name: %s, deviation: %.0f", id, firstName, lastName, criterionValue);
    }
}
