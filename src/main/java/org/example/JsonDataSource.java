package org.example;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class JsonDataSource {
    private ObjectMapper objectMapper;


    JsonDataSource(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    public <T> List<T> loadData(String FILE_PATH, TypeReference<List<T>> typeReference) {
        try {
            File file = new File(FILE_PATH);
            if (file.exists()) {
                return objectMapper.readValue(file, typeReference);
            }
        } catch (IOException e) {
            System.out.println("Error loading file");
        }
        return new ArrayList<>();
    }
    public <T> void saveData(String FILE_PATH, List<T> data) {
        try {
            objectMapper.writeValue(new File(FILE_PATH), data);
        } catch (IOException e) {
            System.out.println("Error saving file");
        }
    }
}
