package epam.exercise.analizer.service.impl;

import epam.exercise.analizer.mock.MockEmployeesAnalyzingService;
import epam.exercise.analizer.mock.MockEmployeesFileReaderService;
import epam.exercise.analizer.mock.MockEmployeesReportPrintService;
import epam.exercise.analizer.service.EmployeesAnalyzingService;
import epam.exercise.analizer.service.EmployeesFileReaderService;
import epam.exercise.analizer.service.EmployeesReportPrintService;
import epam.exercise.analizer.service.EmployeesReportService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.*;

class CommonEmployeesReportServiceTest {

    private final EmployeesFileReaderService employeesFileReaderService = new MockEmployeesFileReaderService();
    private final EmployeesAnalyzingService employeesAnalyzingService = new MockEmployeesAnalyzingService();
    private final EmployeesReportPrintService employeesReportPrintService = new MockEmployeesReportPrintService();
    private final EmployeesReportService service =
            new CommonEmployeesReportService(employeesFileReaderService, employeesAnalyzingService, employeesReportPrintService);

    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;

    @BeforeEach
    public void setUpStreams() {
        System.setOut(new PrintStream(outContent));
    }

    @AfterEach
    public void restoreStreams() {
        System.setOut(originalOut);
    }

    @Test
    void createEmployeesReport_allServicesWorksConsistantly_resultIsPrinted() {
        service.createEmployeesReport();

        String expectedOutput = "Employee: id: 1, first name: name, last name: surname, deviation: 0.10";
        assertEquals(expectedOutput, outContent.toString().trim());
    }
}