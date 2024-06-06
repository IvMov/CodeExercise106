package epam.exercise.analizer.report;

public enum ReportCriterion {
    OVERPAID("Overpaid employees related to direct subordinates average salary"),
    UNDERPAID("Underpaid employees related to direct subordinates average salary"),
    TOO_LONG_REPORTING_LINE("Employees with too long reporting line to CEO");

    private final String description;

    ReportCriterion(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
