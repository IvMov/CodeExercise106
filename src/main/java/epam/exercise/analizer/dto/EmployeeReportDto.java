package epam.exercise.analizer.dto;

import epam.exercise.analizer.entity.Employee;
import epam.exercise.analizer.report.ReportCriterion;

import java.math.BigDecimal;
import java.util.Objects;

/**
 * Represents a Data Transfer Object (DTO) of an {@link Employee}.
 * This class is immutable and thread-safe.
 */
//TODO: REFACTOR to record?
public final class EmployeeReportDto {
    private final Long id;
    private final String firstName;
    private final String lastName;
    private final BigDecimal criterionValue;

    public EmployeeReportDto(Long id, String firstName, String lastName, BigDecimal criterionValue) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.criterionValue = criterionValue;
    }

    public Long getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public BigDecimal getCriterionValue() {
        return criterionValue;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        EmployeeReportDto that = (EmployeeReportDto) o;

        if (!id.equals(that.id)) return false;
        if (!Objects.equals(firstName, that.firstName)) return false;

        return Objects.equals(lastName, that.lastName);
    }

    @Override
    public int hashCode() {
        int result = id.hashCode();
        result = 31 * result + (firstName != null ? firstName.hashCode() : 0);
        result = 31 * result + (lastName != null ? lastName.hashCode() : 0);

        return result;
    }

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
