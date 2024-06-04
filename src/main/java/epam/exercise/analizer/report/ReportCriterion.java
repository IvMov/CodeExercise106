package epam.exercise.analizer.report;

public enum ReportCriterion {
    OVER_PAID("Overpaid employee"),
    UNDER_PAID("Underpaid employee"),
    TOO_LONG_REPORTING_LINE("Employee with too long reporting line");

    private final String description;

    ReportCriterion(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
