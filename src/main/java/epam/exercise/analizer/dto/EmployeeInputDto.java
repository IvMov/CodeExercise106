package epam.exercise.analizer.dto;

import java.math.BigDecimal;

/**
 * Represents a simple employee input raw data.
 *
 * @param id the unique id identifier of employee
 * @param managerId the id of direct manager of instance employee
 * @param firstName first name of employee
 * @param lastName last name of employee
 * @param salary salary of employee
 */
public record EmployeeInputDto(Long id, Long managerId, String firstName, String lastName, BigDecimal salary) {

}
