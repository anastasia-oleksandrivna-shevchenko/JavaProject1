package org.example;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CategoryManager {
    private static final String FILE_PATH = "C:\\Users\\user\\IdeaProjects\\JavaProject1\\src\\main\\java\\resourses\\categories.json";
    private List<Category> categories;
    private ObjectMapper objectMapper;

    public CategoryManager() {
        objectMapper = new ObjectMapper();
        categories = loadCategories();
    }

    private List<Category> loadCategories() {
        try
        {
            File file = new File(FILE_PATH);
            if(file.exists()){
                return objectMapper.readValue(file, new TypeReference<List<Category>>() {});
                }
        }catch (IOException e) {
            System.out.println("Error loading file");
        }
        return new ArrayList<>();
    }

    private void saveCategories() {
        try{
            objectMapper.writeValue(new File(FILE_PATH), categories);
        } catch(IOException e) {
            System.out.println("Error saving file");
        }
    }

    private void addCategory(Category category) {
        categories.add(category);
        saveCategories();
    }

    private void updateCategory (int id, String newName){
        try{
            for(Category category : categories){
                if(category.getId() == id){
                    category.setName(newName);
                    saveCategories();
                    return;
                }
            }
        } catch(Exception e){
            System.out.println("Error updating file");
        }
    }

    private void deleteCategory(int id){
        categories.removeIf(c -> c.getId() == id);
        saveCategories();
    }
}
