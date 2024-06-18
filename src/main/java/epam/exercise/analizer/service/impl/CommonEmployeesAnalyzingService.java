package epam.exercise.analizer.service.impl;

import epam.exercise.analizer.dto.EmployeeInputDto;
import epam.exercise.analizer.dto.EmployeeReportDto;
import epam.exercise.analizer.exception.ReportCreationException;
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

    private static final float UNDERPAID_LIMIT = 0.2f;
    private static final float OVERPAID_LIMIT = 0.5f;
    private static final int REPORTING_LINE_LIMIT = 4;

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
     * @param employees all employees
     * @param employee  subject of analysis
     */
    private void analyzeEmployee(Map<Long, EmployeeInputDto> employees, EmployeeInputDto employee) {
        int managerDistance = calcManagerDistance(employee, employees);
        Map<Long, BigDecimal> averageSubSalariesByManager = calcAvgSubordinatesSalaries(employees);

        if (averageSubSalariesByManager.containsKey(employee.id())) {
            BigDecimal avgSubordinatesSalary = averageSubSalariesByManager.get(employee.id());
            checkIfUnappropriatedSalary(employee, avgSubordinatesSalary);
        }
        checkIfTooLongReportingLine(employee, managerDistance);
    }

    /**
     * Private method to check is salary of subject employee is unappropriate
     *
     * @param employee              subject of analysis
     * @param avgSubordinatesSalary average salary of direct subordinates
     */
    private void checkIfUnappropriatedSalary(EmployeeInputDto employee, BigDecimal avgSubordinatesSalary) {
        BigDecimal minRequiredSalary = avgSubordinatesSalary.add(avgSubordinatesSalary.multiply(BigDecimal.valueOf(UNDERPAID_LIMIT)))
                .setScale(3, RoundingMode.HALF_UP);
        BigDecimal maxRequiredSalary = avgSubordinatesSalary.add(avgSubordinatesSalary.multiply(BigDecimal.valueOf(OVERPAID_LIMIT)))
                .setScale(3, RoundingMode.HALF_UP);
        if (employee.salary().compareTo(minRequiredSalary) < 0) {
            reportDataMap.get(ReportCriterion.UNDERPAID)
                    .add(employeeMapper.mapToReportDto(employee, ReportCriterion.UNDERPAID, minRequiredSalary.subtract(employee.salary()).floatValue()));
        } else if (employee.salary().compareTo(maxRequiredSalary) > 0) {
            reportDataMap.get(ReportCriterion.OVERPAID)
                    .add(employeeMapper.mapToReportDto(employee, ReportCriterion.OVERPAID, employee.salary().subtract(maxRequiredSalary).floatValue()));
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
     * @param employees all employees
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
     * Private method to find length of reporting distance to CEO
     *
     * @param employees all employees
     */
    private int calcManagerDistance(EmployeeInputDto employee, Map<Long, EmployeeInputDto> employees) {
        Long managerId = employee.managerId();
        EmployeeInputDto nextManager;
        int managerDistance = 0;
        int maxRounds = employees.size();

        while (managerId != null) {
            nextManager = employees.get(managerId);
            maxRounds--;
            validateCircleDependencies(employee, maxRounds);

            if (nextManager.managerId() != null) {
                managerDistance++;
            }
            if (knownManagerDistanceByIdMap.containsKey(managerId)) {
                managerDistance += knownManagerDistanceByIdMap.get(managerId);
                break;
            }
            managerId = nextManager.managerId();
        }
        knownManagerDistanceByIdMap.put(employee.id(), managerDistance);

        return managerDistance;
    }

    /**
     * Private method to check circle dependencies if all employees checked but CEO path still not found
     *
     * @param employee  all employees
     * @param maxRounds init value as size of employees collection
     */
    private static void validateCircleDependencies(EmployeeInputDto employee, int maxRounds) {
        if (maxRounds <= 0) {
            throw new ReportCreationException(
                    String.format("Can't find path to CEO for employee id: %d. Please check the input data correctness.",
                            employee.id())
            );
        }
    }
}

