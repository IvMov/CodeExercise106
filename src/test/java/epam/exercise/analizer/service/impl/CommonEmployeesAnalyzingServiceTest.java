package epam.exercise.analizer.service.impl;

import epam.exercise.analizer.dto.EmployeeInputDto;
import epam.exercise.analizer.dto.EmployeeReportDto;
import epam.exercise.analizer.exception.ReportCreationException;
import epam.exercise.analizer.mapper.EmployeeMapper;
import epam.exercise.analizer.mock.MockEmployeeMapper;
import epam.exercise.analizer.report.ReportCriterion;
import epam.exercise.analizer.service.EmployeesAnalyzingService;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class CommonEmployeesAnalyzingServiceTest {

    private final EmployeeMapper mockEmployeeMapper = new MockEmployeeMapper();
    private final EmployeesAnalyzingService service = new CommonEmployeesAnalyzingService(mockEmployeeMapper);


    @Test
    void analyzeEmployees_WhenAllGood_ExpectEmptyReport() {
        Map<ReportCriterion, List<EmployeeReportDto>> result = service.analyzeEmployees(getTestEmployees());

        assertTrue(result.get(ReportCriterion.OVERPAID).isEmpty());
        assertTrue(result.get(ReportCriterion.UNDERPAID).isEmpty());
        assertTrue(result.get(ReportCriterion.TOO_LONG_REPORTING_LINE).isEmpty());
    }

    @Test
    void analyzeEmployees_WhenOverpaidCeo_ExpectOverpaidContainCeo() {
        Map<Long, EmployeeInputDto> employees = getTestEmployees();
        employees.put(1L, new EmployeeInputDto(1L, null, "name1", "lastName1", BigDecimal.valueOf(150000)));

        Map<ReportCriterion, List<EmployeeReportDto>> result = service.analyzeEmployees(employees);
        List<EmployeeReportDto> overpaidResult = result.get(ReportCriterion.OVERPAID);

        assertFalse(overpaidResult.isEmpty());
        assertEquals(1, overpaidResult.size());
        assertEquals(1L, overpaidResult.getFirst().id());
        assertEquals(1L, overpaidResult.getFirst().id());
        assertEquals(37500f, overpaidResult.getFirst().criterionValue());

        assertTrue(result.get(ReportCriterion.UNDERPAID).isEmpty());
        assertTrue(result.get(ReportCriterion.TOO_LONG_REPORTING_LINE).isEmpty());
    }

    @Test
    void analyzeEmployees_WhenOverpaidCeo1pointOver_ExpectOverpaidContainCeo() {
        Map<Long, EmployeeInputDto> employees = getTestEmployees();
        employees.put(1L, new EmployeeInputDto(1L, null, "name1", "lastName1", BigDecimal.valueOf(112501)));

        Map<ReportCriterion, List<EmployeeReportDto>> result = service.analyzeEmployees(employees);
        List<EmployeeReportDto> overpaidResult = result.get(ReportCriterion.OVERPAID);

        assertFalse(overpaidResult.isEmpty());
        assertEquals(1, overpaidResult.size());
        assertEquals(1L, overpaidResult.getFirst().id());
        assertEquals(1L, overpaidResult.getFirst().id());

        assertTrue(result.get(ReportCriterion.UNDERPAID).isEmpty());
        assertTrue(result.get(ReportCriterion.TOO_LONG_REPORTING_LINE).isEmpty());
    }

    @Test
    void analyzeEmployees_WhenCeoOn50ButNotOverpaid_ExpectEmptyReport() {
        Map<Long, EmployeeInputDto> employees = getTestEmployees();
        employees.put(1L, new EmployeeInputDto(1L, null, "name1", "lastName1", BigDecimal.valueOf(112500)));

        Map<ReportCriterion, List<EmployeeReportDto>> result = service.analyzeEmployees(employees);

        assertTrue(result.get(ReportCriterion.OVERPAID).isEmpty());
        assertTrue(result.get(ReportCriterion.UNDERPAID).isEmpty());
        assertTrue(result.get(ReportCriterion.TOO_LONG_REPORTING_LINE).isEmpty());
    }

    @Test
    void analyzeEmployees_WhenCeoUnderpaidBy1Point_ExpectUnderpaidContainCe() {
        Map<Long, EmployeeInputDto> employees = getTestEmployees();
        employees.put(1L, new EmployeeInputDto(1L, null, "name1", "lastName1", BigDecimal.valueOf(89999)));

        Map<ReportCriterion, List<EmployeeReportDto>> result = service.analyzeEmployees(employees);
        List<EmployeeReportDto> underpaidResult = result.get(ReportCriterion.UNDERPAID);

        assertFalse(underpaidResult.isEmpty());
        assertEquals(1, underpaidResult.size());
        assertEquals(1L, underpaidResult.getFirst().id());
        assertEquals(1L, underpaidResult.getFirst().id());

        assertTrue(result.get(ReportCriterion.OVERPAID).isEmpty());
        assertTrue(result.get(ReportCriterion.TOO_LONG_REPORTING_LINE).isEmpty());
    }

    @Test
    void analyzeEmployees_WhenCeoOn20ButNotUnderpaid_ExpectEmptyReport() {
        Map<Long, EmployeeInputDto> employees = getTestEmployees();
        employees.put(1L, new EmployeeInputDto(1L, null, "name1", "lastName1", BigDecimal.valueOf(90000)));

        Map<ReportCriterion, List<EmployeeReportDto>> result = service.analyzeEmployees(employees);

        assertTrue(result.get(ReportCriterion.OVERPAID).isEmpty());
        assertTrue(result.get(ReportCriterion.UNDERPAID).isEmpty());
        assertTrue(result.get(ReportCriterion.TOO_LONG_REPORTING_LINE).isEmpty());
    }

    @Test
    void analyzeEmployees_WhenUnderpaidCeo_ExpectUnderpaidContainCeo() {
        Map<Long, EmployeeInputDto> employees = getTestEmployees();
        employees.put(1L, new EmployeeInputDto(1L, null, "name1", "lastName1", BigDecimal.valueOf(82500)));

        Map<ReportCriterion, List<EmployeeReportDto>> result = service.analyzeEmployees(employees);
        List<EmployeeReportDto> underpaidResult = result.get(ReportCriterion.UNDERPAID);

        assertFalse(underpaidResult.isEmpty());
        assertEquals(1, underpaidResult.size());
        assertEquals(1L, underpaidResult.getFirst().id());
        assertEquals(1L, underpaidResult.getFirst().id());
        assertEquals(7500.0f, underpaidResult.getFirst().criterionValue());

        assertTrue(result.get(ReportCriterion.OVERPAID).isEmpty());
        assertTrue(result.get(ReportCriterion.TOO_LONG_REPORTING_LINE).isEmpty());
    }

    @Test
    void analyzeEmployees_WhenUnderpaidCeoHaveDirectSubordinates_ExpectUnderpaidContainCeo() {
        Map<Long, EmployeeInputDto> employees = getTestEmployees();
        employees.put(3L, new EmployeeInputDto(3L, 1L, "name3", "lastName3", BigDecimal.valueOf(100000)));

        Map<ReportCriterion, List<EmployeeReportDto>> result = service.analyzeEmployees(employees);
        List<EmployeeReportDto> underpaidResult = result.get(ReportCriterion.UNDERPAID);
        List<EmployeeReportDto> overpaidResult = result.get(ReportCriterion.OVERPAID);

        assertFalse(underpaidResult.isEmpty());
        assertEquals(1, underpaidResult.size());
        assertEquals(1L, underpaidResult.getFirst().id());
        assertEquals(1L, underpaidResult.getFirst().id());
        assertEquals(5000.0f, underpaidResult.getFirst().criterionValue());

        assertFalse(overpaidResult.isEmpty());
        assertEquals(1, overpaidResult.size());
        assertEquals(3L, overpaidResult.getFirst().id());
        assertEquals(3L, overpaidResult.getFirst().id());
        assertEquals(36718.75f, overpaidResult.getFirst().criterionValue());

        assertTrue(result.get(ReportCriterion.TOO_LONG_REPORTING_LINE).isEmpty());
    }

    @Test
    void analyzeEmployees_whenSomeEmployeeHasTooLongReportingLine_TooLongReportingLineContainEmployee() {
        Map<Long, EmployeeInputDto> employees = getTestEmployees();
        employees.put(6L, new EmployeeInputDto(6L, 5L, "name6", "lastName6", BigDecimal.valueOf(25000)));
        employees.put(7L, new EmployeeInputDto(7L, 6L, "name7", "lastName7", BigDecimal.valueOf(20000)));

        Map<ReportCriterion, List<EmployeeReportDto>> result = service.analyzeEmployees(employees);
        List<EmployeeReportDto> tooLongReportingLine = result.get(ReportCriterion.TOO_LONG_REPORTING_LINE);

        assertFalse(tooLongReportingLine.isEmpty());
        assertEquals(1, tooLongReportingLine.size());
        assertEquals(7L, tooLongReportingLine.getFirst().id());
        assertEquals(7L, tooLongReportingLine.getFirst().id());
        assertEquals(1.0f, tooLongReportingLine.getFirst().criterionValue());

        assertTrue(result.get(ReportCriterion.OVERPAID).isEmpty());
        assertTrue(result.get(ReportCriterion.UNDERPAID).isEmpty());
    }

    @Test
    void analyzeEmployees_whenCEONotReachable_ThrowException() {
        String expectedMessage = "Can't find path to CEO for employee id: 6. Please check the input data correctness.";
        Map<Long, EmployeeInputDto> employees = getTestEmployees();
        employees.put(6L, new EmployeeInputDto(6L, 7L, "name6", "lastName6", BigDecimal.valueOf(25000)));
        employees.put(7L, new EmployeeInputDto(7L, 6L, "name7", "lastName7", BigDecimal.valueOf(20000)));

        Throwable exception = assertThrows(ReportCreationException.class, () -> service.analyzeEmployees(employees));

        assertEquals(expectedMessage, exception.getMessage());
    }


    private Map<Long, EmployeeInputDto> getTestEmployees() {
        Map<Long, EmployeeInputDto> employees = new HashMap<>();
        float baseSalary = 100000;
        employees.put(1L, new EmployeeInputDto(1L, null, "name1", "lastName1", BigDecimal.valueOf(baseSalary)));

        for (long i = 2; i <= 5; i++) {
            baseSalary *= 0.75f;
            employees.put(i, new EmployeeInputDto(i, i - 1, "name" + i, "lastName" + i, BigDecimal.valueOf(baseSalary)));
        }

        return employees;
    }
}