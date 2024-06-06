package epam.exercise.analizer.mock;

import epam.exercise.analizer.service.PropertiesService;

import java.util.HashMap;
import java.util.Map;


/**
 * Mock implementation of PropertiesService as I can't use a Mockito :(
 */
public class MockPropertiesService implements PropertiesService {
    private final Map<String, String> properties = new HashMap<>();


    public MockPropertiesService() {
        properties.put("app.employees_file_name", "test.csv");
    }


    public void setProperty(String key, String value) {
        properties.put(key, value);
    }

    @Override
    public String getProperty(String key) {
        return properties.get(key);
    }
}
