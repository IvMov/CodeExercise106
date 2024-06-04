package epam.exercise.analizer.mapper;

import epam.exercise.analizer.dto.EmployeeInputDto;
import epam.exercise.analizer.dto.EmployeeReportDto;
import epam.exercise.analizer.report.ReportCriterion;

import java.math.BigDecimal;

public class EmployeeMapper {

    EmployeeReportDto mapToReportDto(EmployeeInputDto dto, ReportCriterion criterion, BigDecimal criterionValue) {
        return new EmployeeReportDto(
                dto.id(),
                dto.firstName(),
                dto.lastName(),
                criterion,
                criterionValue
        );
    }
}
