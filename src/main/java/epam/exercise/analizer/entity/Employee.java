package epam.exercise.analizer.entity;

import java.math.BigDecimal;
import java.util.Objects;

/**
 * Represents a simple employee entity with its custom basic information.
 * This class is immutable and thread-safe.
 */
//TODO: REFACTOR to record?
public class Employee {

    private final Long id;
    private final Long managerId;
    private final String firstName;
    private final String lastName;
    private final BigDecimal salary;

    public Employee(Long id, Long managerId, String firstName, String lastName, BigDecimal salary) {
        this.id = id;
        this.managerId = managerId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.salary = salary;
    }

    public Long getId() {
        return id;
    }

    public Long getManagerId() {
        return managerId;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public BigDecimal getSalary() {
        return salary;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Employee employee = (Employee) o;

        if (!id.equals(employee.id)) return false;
        if (!Objects.equals(managerId, employee.managerId)) return false;
        if (!Objects.equals(firstName, employee.firstName)) return false;
        if (!Objects.equals(lastName, employee.lastName)) return false;

        return Objects.equals(salary, employee.salary);
    }

    @Override
    public int hashCode() {
        int result = id.hashCode();
        result = 31 * result + (managerId != null ? managerId.hashCode() : 0);
        result = 31 * result + (firstName != null ? firstName.hashCode() : 0);
        result = 31 * result + (lastName != null ? lastName.hashCode() : 0);
        result = 31 * result + (salary != null ? salary.hashCode() : 0);

        return result;
    }


}
