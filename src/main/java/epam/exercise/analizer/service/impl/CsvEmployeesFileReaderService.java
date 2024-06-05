package epam.exercise.analizer.service.impl;

import epam.exercise.analizer.dto.EmployeeInputDto;
import epam.exercise.analizer.exception.ReportCreationException;
import epam.exercise.analizer.service.EmployeesFileReaderService;
import epam.exercise.analizer.service.PropertiesService;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.function.Function;
import java.util.logging.Logger;
import java.util.stream.Collectors;


/**
 * CsvEmployeesFileReaderService used to read and parse csv data, implements {@link EmployeesFileReaderService}
 */
public class CsvEmployeesFileReaderService implements EmployeesFileReaderService {

    private static final Logger LOGGER = Logger.getLogger(CsvEmployeesFileReaderService.class.getName());

    private static final String FILE_NAME_PROPERTY = "app.employees_file_name";
    private static final String COMMA_DELIMITER = ",";

    private static final byte ID_PLACEHOLDER = 0;
    private static final byte FIRST_NAME_PLACEHOLDER = 1;
    private static final byte LAST_NAME_PLACEHOLDER = 2;
    private static final byte SALARY_PLACEHOLDER = 3;
    private static final byte MANAGER_ID_PLACEHOLDER = 4;

    private final PropertiesService propertiesService;


    /**
     * Instantiates a new Csv employees file reader service.
     *
     * @param propertiesService the properties service to retrieve necessary properties values
     */
    public CsvEmployeesFileReaderService(PropertiesService propertiesService) {
        this.propertiesService = propertiesService;
    }


    @Override
    public Map<Long, EmployeeInputDto> readEmployeesFile() throws ReportCreationException {
        String fileName = propertiesService.getProperty(FILE_NAME_PROPERTY);

        try (InputStream inputStream = CsvEmployeesFileReaderService.class.getResourceAsStream("/" + fileName)) {
            validateFile(fileName, inputStream);
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8))) {
                return readCsvToMap(reader);
            }
        } catch (IOException | ReportCreationException e) {
            LOGGER.warning("Failed to read csv file.");
            throw new ReportCreationException(e.getMessage());
        }
    }


    /**
     * Private method to encapsulate all steps of reading csv information
     * from {@link BufferedReader} lines to {@link EmployeeInputDto}
     *
     * @param bufferedReader reader with csv file information
     * @return a map where key is Employee id, and value is {@link EmployeeInputDto}
     */
    private Map<Long, EmployeeInputDto> readCsvToMap(BufferedReader bufferedReader) {
        Map<Long, EmployeeInputDto> employees;
        employees = bufferedReader.lines()
                .skip(1)
                .map(this::parseLineToEmployeeInputDto)
                .collect(Collectors.toMap(EmployeeInputDto::id, Function.identity()));

        return employees;
    }

    /**
     * Simple private validation method ensure that file exists and that file has right extention
     *
     * @param fileName    name of file with extension
     * @param inputStream with file data
     * @throws ReportCreationException in case if validation fails
     */
    private static void validateFile(String fileName, InputStream inputStream) throws ReportCreationException {
        String errorMessage = null;

        if (!"csv".equals(fileName.split("\\.")[1])) {
            errorMessage = String.format("File %s is not .csv format, please use another service or provide correct file.", fileName);
        } else if (inputStream == null) {
            errorMessage = String.format("File %s not found, input stream is null.", fileName);
        }

        if (errorMessage != null) {
            throw new ReportCreationException(errorMessage);
        }
    }

    /**
     * Private method for parsing csv file line to {@link EmployeeInputDto}.
     *
     * @param line line with some delimiter
     * @return {@link EmployeeInputDto} - result of parsing line
     * @throws ReportCreationException in case if parsing fails
     */
    private EmployeeInputDto parseLineToEmployeeInputDto(String line) throws ReportCreationException {
        String[] lineParts = line.split(COMMA_DELIMITER);

        try {
            return new EmployeeInputDto(
                    Long.valueOf(lineParts[ID_PLACEHOLDER]),
                    lineParts.length > MANAGER_ID_PLACEHOLDER ? Long.valueOf(lineParts[MANAGER_ID_PLACEHOLDER]) : null,
                    lineParts.length > FIRST_NAME_PLACEHOLDER ? lineParts[FIRST_NAME_PLACEHOLDER] : null,
                    lineParts.length > LAST_NAME_PLACEHOLDER ? lineParts[LAST_NAME_PLACEHOLDER] : null,
                    lineParts.length > LAST_NAME_PLACEHOLDER ? new BigDecimal(lineParts[SALARY_PLACEHOLDER]) : null
            );
        } catch (NumberFormatException e) {
            throw new ReportCreationException(String.format("Failed to parse line %s, please check your csv file. Report creation interrupted.", line));
        }
    }

}
