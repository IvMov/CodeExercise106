package epam.exercise.analizer.service.impl;

import epam.exercise.analizer.service.PropertiesService;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.logging.Logger;

/**
 * Simple implementation of {@link PropertiesService} for reading properties.
 */
public class AppPropertiesService implements PropertiesService {

    private static final Logger LOGGER = Logger.getLogger(AppPropertiesService.class.getName());
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
            LOGGER.info("Property file loaded.");
        } catch (IOException e) {
            LOGGER.warning(e.getMessage());
        }

    }

    @Override
    public String getProperty(String key) {
        return properties.getProperty(key);
    }
}
