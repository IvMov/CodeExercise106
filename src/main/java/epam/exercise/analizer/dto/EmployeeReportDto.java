package epam.exercise.analizer.dto;

import epam.exercise.analizer.report.ReportCriterion;

import java.math.BigDecimal;
import java.util.Objects;

/**
 * Represents a simple output employee report data,
 * contains all necessary data to identify and represent employee in report purposes
 *
 * @param id the unique id identifier of employee
 * @param firstName first name of employee
 * @param lastName last name of employee
 * @param reportCriterion the criterion of type {@link ReportCriterion} - reason why employee was added to report
 * @param criterionValue the value of deviation from some threshold criterion
 */
public record EmployeeReportDto(Long id, String firstName, String lastName, ReportCriterion reportCriterion, BigDecimal criterionValue) {

    @Override
    public String toString() {
        return "Employee {" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", reportCriteriaValue=" + criterionValue +
                '}';
    }
}
