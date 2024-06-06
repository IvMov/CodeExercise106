package epam.exercise.analizer.mock;

import epam.exercise.analizer.dto.EmployeeInputDto;
import epam.exercise.analizer.dto.EmployeeReportDto;
import epam.exercise.analizer.mapper.EmployeeMapper;
import epam.exercise.analizer.report.ReportCriterion;

/**
 * Mock implementation of {@link EmployeeMapper} as I can't use a Mockito :(
 */
public class MockEmployeeMapper implements EmployeeMapper {

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
