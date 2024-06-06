package epam.exercise.analizer.mock;

import epam.exercise.analizer.dto.EmployeeInputDto;
import epam.exercise.analizer.exception.ReportCreationException;
import epam.exercise.analizer.service.EmployeesFileReaderService;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

/**
 * Mock implementation of {@link MockEmployeesFileReaderService} as I can't use a Mockito :(
 */
public class MockEmployeesFileReaderService implements EmployeesFileReaderService {
    @Override
    public Map<Long, EmployeeInputDto> readEmployeesFile() throws ReportCreationException {
        Map<Long, EmployeeInputDto> employees = new HashMap<>();
        employees.put(1L, new EmployeeInputDto(1L, 2L, "name", "surname", BigDecimal.ONE));

        return employees;
    }
}
