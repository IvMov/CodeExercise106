package epam.exercise.analizer.service.impl;

import epam.exercise.analizer.service.PropertiesService;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class AppPropertiesServiceTest {

    private final PropertiesService service = new AppPropertiesService("test.properties");


    @Test
    void getProperty_propertyExists_expectValue() {
        String value = service.getProperty("test.key1");
        assertEquals("firstValue", value);
    }

    @Test
    void getProperty_propertyExists_expectAnotherValue() {
        String value = service.getProperty("key2");
        assertEquals("secondValue", value);
    }

    @Test
    void getProperty_propertyNotExists_expectNull() {
        String value = service.getProperty("nonexistent.key");
        assertNull(value, "The property value should be null for a nonexistent key");
    }

}