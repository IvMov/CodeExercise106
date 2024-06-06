package epam.exercise.analizer.service.impl;

import epam.exercise.analizer.dto.EmployeeReportDto;
import epam.exercise.analizer.report.ReportCriterion;
import epam.exercise.analizer.service.EmployeesReportPrintService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ConsoleEmployeesReportPrintServiceTest {

    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;
    private final EmployeesReportPrintService service = new ConsoleEmployeesReportPrintService();

    @BeforeEach
    public void setUpStreams() {
        System.setOut(new PrintStream(outContent));
    }

    @AfterEach
    public void restoreStreams() {
        System.setOut(originalOut);
    }

    @Test
    void testPrintReport_whenEmpty_printEmpty() {

        Map<ReportCriterion, List<EmployeeReportDto>> employeesReportDataByCriteria = getTestEmployees();

        service.printReport(employeesReportDataByCriteria);

        String expectedOutput = """
                *** Report of organizational structure of employees ***

                Overpaid employees related to direct subordinates average salary:
                    Employees by this criteria not found.
                
                Underpaid employees related to direct subordinates average salary:
                    Employees by this criteria not found.
                
                Employees with too long reporting line to CEO:
                    Employees by this criteria not found.""";
        assertEquals(expectedOutput, outContent.toString().trim());
    }

    @Test
    void testPrintReport_whenAllOne_printReport() {

        Map<ReportCriterion, List<EmployeeReportDto>> employeesReportDataByCriteria = getTestEmployees();
        employeesReportDataByCriteria.get(ReportCriterion.UNDERPAID).add(new EmployeeReportDto(1L, "Fake", "Fake", ReportCriterion.UNDERPAID, 10.0f));
        employeesReportDataByCriteria.get(ReportCriterion.OVERPAID).add(new EmployeeReportDto(1L, "Fake", "Fake", ReportCriterion.OVERPAID, 10.0f));
        employeesReportDataByCriteria.get(ReportCriterion.TOO_LONG_REPORTING_LINE).add(new EmployeeReportDto(1L, "Fake", "Fake", ReportCriterion.TOO_LONG_REPORTING_LINE, 2.0f));
        service.printReport(employeesReportDataByCriteria);

        String expectedOutput = """
                *** Report of organizational structure of employees ***
                                
                Overpaid employees related to direct subordinates average salary:
                	Employee: id: 1, first name: Fake, last name: Fake, deviation: 10.00000
                	IMPORTANT: Deviation number mean - by how much (in percentage) employee earns more then max salary range threshold is.
                                
                Underpaid employees related to direct subordinates average salary:
                	Employee: id: 1, first name: Fake, last name: Fake, deviation: 10.00000
                	IMPORTANT: Deviation number mean - by how much (in percentage) employee earns less then min salary range threshold is.
                                
                Employees with too long reporting line to CEO:
                	Employee: id: 1, first name: Fake, last name: Fake, deviation: 2.00000
                	IMPORTANT: Deviation number means how many persons more than max persons threshold allowed.""";
        assertEquals(expectedOutput, outContent.toString().trim());

    }

    @Test
    void testPrintReport_whenFewInOne_printReport() {

        Map<ReportCriterion, List<EmployeeReportDto>> employeesReportDataByCriteria = getTestEmployees();
        employeesReportDataByCriteria.get(ReportCriterion.UNDERPAID).add(new EmployeeReportDto(1L, "Fake", "Fake", ReportCriterion.UNDERPAID, 10.0f));
        employeesReportDataByCriteria.get(ReportCriterion.UNDERPAID).add(new EmployeeReportDto(2L, "Fake2", "Fake2", ReportCriterion.OVERPAID, 15.0f));
        service.printReport(employeesReportDataByCriteria);

        String expectedOutput = """
                *** Report of organizational structure of employees ***
                                
                Overpaid employees related to direct subordinates average salary:
                	Employees by this criteria not found.
                                
                Underpaid employees related to direct subordinates average salary:
                	Employee: id: 1, first name: Fake, last name: Fake, deviation: 10.00000
                	Employee: id: 2, first name: Fake2, last name: Fake2, deviation: 15.00000
                	IMPORTANT: Deviation number mean - by how much (in percentage) employee earns less then min salary range threshold is.
                                
                Employees with too long reporting line to CEO:
                	Employees by this criteria not found.""";
        assertEquals(expectedOutput, outContent.toString().trim());

    }

    private Map<ReportCriterion, List<EmployeeReportDto>>  getTestEmployees() {
        Map<ReportCriterion, List<EmployeeReportDto>> reportData = new EnumMap<>(ReportCriterion.class);
        reportData.put(ReportCriterion.OVERPAID, new ArrayList<>());
        reportData.put(ReportCriterion.UNDERPAID, new ArrayList<>());
        reportData.put(ReportCriterion.TOO_LONG_REPORTING_LINE, new ArrayList<>());

        return reportData;
    }
}