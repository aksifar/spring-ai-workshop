package dev.aksifar.workshop.util;

public class Constants {

    public static final String SLASH = "/";
    public static final String VECTOR_STORE_NAME = "vectorStore.json";
    public static final String BASE_URL = "https://api.weather.gov";

    private Constants() throws IllegalAccessException {
        throw new IllegalAccessException("Utility class, not to be instantiated");
    }
}
