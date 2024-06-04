package epam.exercise.analizer.service;

import epam.exercise.analizer.entity.Employee;

import java.util.Map;

public interface FileReaderService {

    Map<Long, Employee> readEmployeesFile(String path);
}
