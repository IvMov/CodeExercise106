package epam.exercise.analizer.service.impl;

import epam.exercise.analizer.dto.EmployeeInputDto;
import epam.exercise.analizer.dto.EmployeeReportDto;
import epam.exercise.analizer.mapper.EmployeeMapper;
import epam.exercise.analizer.report.ReportCriterion;
import epam.exercise.analizer.service.EmployeesAnalyzingService;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;
import java.util.stream.Collectors;

/**
 * The CommonEmployeesAnalyzingService implementation of {@link EmployeesAnalyzingService}
 * Contain methods to analyze employees by {@link ReportCriterion}
 */
public class CommonEmployeesAnalyzingService implements EmployeesAnalyzingService {

    private static final float UNDERPAID_LIMIT = 20.0f;
    private static final float OVERPAID_LIMIT = 50.0f;
    private static final int REPORTING_LINE_LIMIT = 4;
    private static final int SALARY_DIVISION_SCALE = 5;

    private final EmployeeMapper employeeMapper;
    private final Map<Long, Integer> knownManagerDistanceByIdMap;
    private final Map<ReportCriterion, List<EmployeeReportDto>> reportDataMap;

    /**
     * Instantiates a new CommonEmployeesAnalyzingService
     *
     * @param employeeMapper the employee mapper
     */
    public CommonEmployeesAnalyzingService(EmployeeMapper employeeMapper) {
        this.employeeMapper = employeeMapper;
        this.knownManagerDistanceByIdMap = new HashMap<>();
        this.reportDataMap = prepareBlankReportDataMap();
    }

    @Override
    public Map<ReportCriterion, List<EmployeeReportDto>> analyzeEmployees(Map<Long, EmployeeInputDto> employees) {
        employees.values()
                .forEach(employee -> analyzeEmployee(employees, employee));

        return reportDataMap;
    }

    /**
     * Private method to analyze to prepare employee data for analysis
     *
     * @param employees          all employees
     * @param employee           subject of analysis
     */
    private void analyzeEmployee(Map<Long, EmployeeInputDto> employees, EmployeeInputDto employee) {
        int managerDistance = calcManagerDistance(employee, employees);
        Map<Long, BigDecimal> averageSubSalariesByManager = calcAvgSubordinatesSalaries(employees);

        if (averageSubSalariesByManager.containsKey(employee.id())) {
            BigDecimal avgSubordinatesSalary = averageSubSalariesByManager.get(employee.id());
            Float salaryDifferencePercentage = employee.salary()
                    .divide(avgSubordinatesSalary, SALARY_DIVISION_SCALE, RoundingMode.HALF_UP)
                    .subtract(BigDecimal.ONE) //subtract 1 (100%)
                    .multiply(BigDecimal.valueOf(100)) //100%
                    .floatValue();

            checkIfUnappropriatedSalary(employee, salaryDifferencePercentage);
        }
        checkIfTooLongReportingLine(employee, managerDistance);
    }

    /**
     * Private method to check is salary of subject employee is unappropriate
     *
     * @param employee                   subject of analysis
     * @param salaryDifferencePercentage percentage of how much employee salary is more that AVERAGE salary of his direct subordinates
     */
    private void checkIfUnappropriatedSalary(EmployeeInputDto employee, Float salaryDifferencePercentage) {
        if (salaryDifferencePercentage < UNDERPAID_LIMIT) {
            reportDataMap.get(ReportCriterion.UNDERPAID)
                    .add(employeeMapper.mapToReportDto(employee, ReportCriterion.UNDERPAID, salaryDifferencePercentage - UNDERPAID_LIMIT));
        } else if (salaryDifferencePercentage > OVERPAID_LIMIT) {
            reportDataMap.get(ReportCriterion.OVERPAID)
                    .add(employeeMapper.mapToReportDto(employee, ReportCriterion.OVERPAID, salaryDifferencePercentage - OVERPAID_LIMIT));
        }
    }

    /**
     * Private method to check is reporting line of employee is too long
     *
     * @param employee        subject of analysis
     * @param managerDistance current length of reporting line for employee
     */
    private void checkIfTooLongReportingLine(EmployeeInputDto employee, int managerDistance) {
        if (managerDistance > REPORTING_LINE_LIMIT) {
            reportDataMap.get(ReportCriterion.TOO_LONG_REPORTING_LINE)
                    .add(employeeMapper.mapToReportDto(employee, ReportCriterion.TOO_LONG_REPORTING_LINE, managerDistance - (float) REPORTING_LINE_LIMIT));
        }
    }

    /**
     * Private method to prepare report data
     *
     * @return report with report criteria as keys and empty lists as values
     */
    private Map<ReportCriterion, List<EmployeeReportDto>> prepareBlankReportDataMap() {
        Map<ReportCriterion, List<EmployeeReportDto>> reportData = new EnumMap<>(ReportCriterion.class);
        reportData.put(ReportCriterion.OVERPAID, new ArrayList<>());
        reportData.put(ReportCriterion.UNDERPAID, new ArrayList<>());
        reportData.put(ReportCriterion.TOO_LONG_REPORTING_LINE, new ArrayList<>());

        return reportData;
    }

    /**
     * Private method for calculating an average subordinates salary
     *
     * @param employees        all employees
     */
    private Map<Long, BigDecimal> calcAvgSubordinatesSalaries(Map<Long, EmployeeInputDto> employees) {
        return employees.values().stream()
                .collect(Collectors.groupingBy(employee -> employee.managerId() == null ? -1L : employee.managerId(),
                        Collectors.collectingAndThen(Collectors.averagingDouble(employee -> employee.salary().doubleValue()),
                                BigDecimal::valueOf
                        )
                ));
    }

    /**
     * Private method length of reporting distance to CEO
     *
     * @param employees        all employees
     */
    private int calcManagerDistance(EmployeeInputDto employee, Map<Long, EmployeeInputDto> employees) {
        Long managerId = employee.managerId();
        int managerDistance = 0;
        int maxRounds = employees.size();

        while (managerId != null || maxRounds <= 0) {
            maxRounds--;
            managerDistance++;
            if (knownManagerDistanceByIdMap.containsKey(managerId)) {
                managerDistance += knownManagerDistanceByIdMap.get(managerId);
                break;
            }
            managerId = employees.get(managerId).managerId();
        }
        knownManagerDistanceByIdMap.put(employee.id(), managerDistance);

        return managerDistance;
    }
}

