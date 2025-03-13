package org.example;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CategoryManager {
    private final String FILE_PATH;
    private List<Category> categories;
    private final JsonDataSource jsonDataSource;

    public CategoryManager(String FILE_PATH,JsonDataSource jsonDataSource,  List<Category> categories) {
        this.FILE_PATH = FILE_PATH;
        this.jsonDataSource = jsonDataSource;
        this.categories = categories;
    }

    private void saveCategories() {
        jsonDataSource.saveData(FILE_PATH, categories);
    }

    private boolean isCategoryIdExists(int id) {
        for (Category category : categories) {
            if (category.getId() == id) {
                return true;
            }
        }
        return false;
    }

    public void addCategory(Category category) {
        if (isCategoryIdExists(category.getId())) {
            System.out.println("Category with this ID already exists.");
        } else {
            categories.add(category);
            saveCategories();
        }
    }

    public void updateCategory (int id, String newName){
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

    public void deleteCategory(int id){
        categories.removeIf(c -> c.getId() == id);
        saveCategories();
    }
    public List<Category> getAllCategories() {
        return categories;
    }
}
