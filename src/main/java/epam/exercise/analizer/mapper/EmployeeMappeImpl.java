package epam.exercise.analizer.mapper;

import epam.exercise.analizer.dto.EmployeeInputDto;
import epam.exercise.analizer.dto.EmployeeReportDto;
import epam.exercise.analizer.report.ReportCriterion;

public class EmployeeMappeImpl implements EmployeeMapper {

    @Override
    public EmployeeReportDto mapToReportDto(EmployeeInputDto dto, ReportCriterion criterion, Float criterionValue) {
        return new EmployeeReportDto(
                dto.id(),
                dto.firstName(),
                dto.lastName(),
                criterion,
                criterionValue
        );
    }
}
