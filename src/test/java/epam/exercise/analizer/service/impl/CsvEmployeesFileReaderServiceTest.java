package epam.exercise.analizer.service.impl;

import epam.exercise.analizer.dto.EmployeeInputDto;
import epam.exercise.analizer.exception.ReportCreationException;
import epam.exercise.analizer.helper.TestPropertiesService;
import epam.exercise.analizer.service.EmployeesFileReaderService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class CsvEmployeesFileReaderServiceTest {

    private TestPropertiesService mockPropertiesService = new TestPropertiesService();
    private EmployeesFileReaderService employeesFileReaderService;

    @BeforeAll
    public void setUp() {
        employeesFileReaderService = new CsvEmployeesFileReaderService(mockPropertiesService);
    }

    @Test
    void readEmployeesFile_fileValidAndExist_expectResults() {
        mockPropertiesService.setProperty("app.employees_file_name", "test.csv");

        Map<Long, EmployeeInputDto> result = employeesFileReaderService.readEmployeesFile();

        assertNotNull(result);
        assertEquals("Hasacat", result.get((long) 300).lastName());
        assertNull(result.get((long) 123).managerId());
        assertEquals(5, result.size());
    }

    @Test
    void readEmployeesFile_fileNotValid_expectExceptionThrown() {
        String expectedMessage = "File test.yaml is not .csv format, please use another service or provide correct file.";
        mockPropertiesService.setProperty("app.employees_file_name", "test.yaml");

        Throwable exception = assertThrows(ReportCreationException.class, () -> employeesFileReaderService.readEmployeesFile());

        assertEquals(expectedMessage, exception.getMessage());
    }

    @Test
    void readEmployeesFile_fileNotExist_expectExceptionThrown() {
        String expectedMessage = "File nonexist.csv not found, input stream is null.";
        mockPropertiesService.setProperty("app.employees_file_name", "nonexist.csv");

        Throwable exception = assertThrows(ReportCreationException.class, () -> employeesFileReaderService.readEmployeesFile());

        assertEquals(expectedMessage, exception.getMessage());
    }

    @Test
    void readEmployeesFile_fileBroken_expectExceptionThrown() {
        String expectedMessage = "Failed to parse line 124,Martin,Chekov,45000,123d, please check your csv file. Report creation interrupted.";
        mockPropertiesService.setProperty("app.employees_file_name", "test_broken_data.csv");

        Throwable exception = assertThrows(ReportCreationException.class, () -> employeesFileReaderService.readEmployeesFile());

        assertEquals(expectedMessage, exception.getMessage());
    }
}