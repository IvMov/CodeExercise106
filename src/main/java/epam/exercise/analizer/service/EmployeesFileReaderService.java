package epam.exercise.analizer.service;

import epam.exercise.analizer.dto.EmployeeInputDto;

import java.util.Map;

public interface EmployeesFileReaderService {

    Map<Long, EmployeeInputDto> readEmployeesFile(String path);
}
