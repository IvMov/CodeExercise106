package epam.exercise.analizer.service;

import epam.exercise.analizer.dto.EmployeeInputDto;
import epam.exercise.analizer.exception.ReportCreationException;

import java.util.Map;

/**
 * The interface EmployeesFileReaderService for reading files.
 */
public interface EmployeesFileReaderService {

    /**
     * Method for reading file and to parsing it to {@link EmployeeInputDto}
     *
     * @return a map where key is Employee id, and the value is {@link EmployeeInputDto}
     * @throws ReportCreationException in case if failed to read or parse csv file
     */
    Map<Long, EmployeeInputDto> readEmployeesFile() throws ReportCreationException;
}
