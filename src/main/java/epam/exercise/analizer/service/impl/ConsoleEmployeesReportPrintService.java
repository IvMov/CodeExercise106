package epam.exercise.analizer.service.impl;

import epam.exercise.analizer.dto.EmployeeReportDto;
import epam.exercise.analizer.report.ReportCriterion;
import epam.exercise.analizer.service.EmployeesReportPrintService;

import java.util.List;
import java.util.Map;

/**
 * The ConsoleEmployeesReportPrintService implementation of {@link EmployeesReportPrintService}.
 * Used to print report to the console.
 */
public class ConsoleEmployeesReportPrintService implements EmployeesReportPrintService {

    private static final String REPORT_HEADER = "\n*** Report of organizational structure of employees ***\n";

    @Override
    public void printReport(Map<ReportCriterion, List<EmployeeReportDto>> employeesReportDataByCriteria) {
        StringBuilder stringBuilder = buildReportBody(employeesReportDataByCriteria);
        System.out.println(stringBuilder);
    }

    /**
     * Private method to fulfill stringBuilder with report body
     *
     * @param employeesReportDataByCriteria content to create report body
     * @return ready stringBuilder with report body
     */
    private StringBuilder buildReportBody(Map<ReportCriterion, List<EmployeeReportDto>> employeesReportDataByCriteria) {
        StringBuilder stringBuilder = new StringBuilder(REPORT_HEADER);

        employeesReportDataByCriteria.keySet().forEach(criterion -> {
            List<EmployeeReportDto> employees = employeesReportDataByCriteria.get(criterion);
            appendPrimaryLine(stringBuilder, criterion.getDescription());

            if (employees.isEmpty()) {
                appendAsSecondaryLine(stringBuilder, "Employees by this criteria not found.");
            } else {
                employees.forEach(employee ->
                        appendAsSecondaryLine(stringBuilder, employee.toString()));
                appendAsSecondaryLine(stringBuilder, getCriterionDeviationExplanation(criterion));
            }

        });

        return stringBuilder;
    }

    /**
     * Private method encapsulated appending of primary (0 tabs) line
     *
     * @param stringBuilder report body holder
     * @param content       content to be appended to stringBuilder
     */
    private static void appendPrimaryLine(StringBuilder stringBuilder, String content) {
        stringBuilder.append("\n")
                .append(content)
                .append(":\n");
    }

    /**
     * Private method encapsulated appending of secondary (1 tabs) line
     *
     * @param stringBuilder report body holder
     * @param content       content to be appended to stringBuilder
     */
    private void appendAsSecondaryLine(StringBuilder stringBuilder, String content) {
        stringBuilder.append("\t")
                .append(content)
                .append("\n");
    }

    /**
     * Private method to clarify some additional information depending on {@link ReportCriterion}
     *
     * @param criterion report criterion
     * @return string with criterion deviation explanation
     */
    private String getCriterionDeviationExplanation(ReportCriterion criterion) {
        switch (criterion) {
            case TOO_LONG_REPORTING_LINE -> {
                return "IMPORTANT: Deviation number means - by how many managers more than max managers allowed between them and the CEO.";
            }
            case UNDERPAID -> {
                return "IMPORTANT: Deviation number means - by how much employee earns less then min salary range threshold is.";
            }
            case OVERPAID -> {
                return "IMPORTANT: Deviation number means - by how much employee earns more then max salary range threshold is.";
            }
            default -> {
                return "Unknown report criterion.\n";
            }
        }
    }

}
