package epam.exercise.analizer.service.impl;

import epam.exercise.analizer.exception.ReportCreationException;
import epam.exercise.analizer.service.PropertiesService;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Simple implementation of {@link PropertiesService} for reading properties.
 */
public class AppPropertiesService implements PropertiesService {

    private final Properties properties = new Properties();

    /**
     * Instantiates a new AppPropertiesService, depends on property file.
     *
     * @param propertyFileName the name of property file
     */
    public AppPropertiesService(String propertyFileName) {

        try (InputStream inputStream = getClass().getClassLoader().getResourceAsStream(propertyFileName)) {
            if (inputStream == null) {
                throw new IOException(String.format("Property file not found: %s.", propertyFileName));
            }
            properties.load(inputStream);
        } catch (IOException e) {
            throw new ReportCreationException("Failed to load application. Failed to read properties file.");
        }

    }

    @Override
    public String getProperty(String key) {
        return properties.getProperty(key);
    }
}
