package org.example;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ExpenseManager {
    private static final String FILE_PATH = "C:\\Users\\user\\IdeaProjects\\JavaProject1\\src\\main\\java\\resourses\\categories.json";
    private List<Expense> expenses;
    private ObjectMapper objectMapper;

    public ExpenseManager() {
        objectMapper = new ObjectMapper();
        expenses = loadExpenses();
    }

    private List<Expense> loadExpenses() {
        try{
            File file = new File(FILE_PATH);
            if(!file.exists()){
                return objectMapper.readValue(file, new TypeReference<List<Expense>>() {});
            }
        } catch(IOException e){
            System.out.println("Error loading file");
        }
        return new ArrayList<>();
    }

    private void  saveExpenses() {
        try{
            objectMapper.writeValue(new File(FILE_PATH), expenses);
        }catch(IOException e){
            System.out.println("Error saving file");
        }
    }
    public void addExpense(Expense expense){
        expenses.add(expense);
        saveExpenses();
    }

    private void updateExpenses(int id, String newDescription, double newAmount, int newCategoryId){
        try {
            for (Expense expense : expenses) {
                if (expense.getId() == id) {
                    expense.setDescription(newDescription);
                    expense.setAmount(newAmount);
                    expense.setCategoryId(newCategoryId);
                    saveExpenses();
                    return;
                }
            }
        }catch (Exception e){
            System.out.println("Error updating expense");
        }
    }
    private void deleteExpenses(int id){
        expenses.removeIf(expense -> expense.getId() == id);
        saveExpenses();
    }

    public List<Expense> getAllExpenses() {
        return expenses;
    }
}
