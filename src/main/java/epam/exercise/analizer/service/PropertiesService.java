package epam.exercise.analizer.service;

/**
 * Interface representing a service for retrieving properties.
 */
public interface PropertiesService {

    /**
     * Retrieves the value of the property associated with the specified key.
     *
     * @param key the key of the property to retrieve
     * @return the value of the property, or {@code null} if the property is not found
     */
    String getProperty(String key);
}
