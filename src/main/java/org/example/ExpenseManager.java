package org.example;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ExpenseManager {
    private final String FILE_PATH;
    private List<Expense> expenses;
    private final JsonDataSource jsonDataSource;

    public ExpenseManager(String FILE_PATH ,JsonDataSource jsonDataSource, List<Expense> expenses) {
        this.FILE_PATH = FILE_PATH;
        this.jsonDataSource = jsonDataSource;
        this.expenses = expenses;
    }

    private void  saveExpenses() {
        jsonDataSource.saveData(FILE_PATH, expenses);
    }

    private boolean isExpenseIdExists(int id) {
        for (Expense expense : expenses) {
            if (expense.getId() == id) {
                return true;
            }
        }
        return false;
    }

    public void addExpense(Expense expense){
        if (isExpenseIdExists(expense.getId())) {
            System.out.println("Expense with this ID already exists.");
        } else {
            expenses.add(expense);
            saveExpenses();
        }
    }

    public void updateExpenses(int id, String newDescription, double newAmount, int newCategoryId){
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
    public void deleteExpenses(int id){
        expenses.removeIf(expense -> expense.getId() == id);
        saveExpenses();
    }

    public List<Expense> getExpensesByCategory(int categoryId) {
        List<Expense> filteredExpenses = new ArrayList<>();
        for (Expense expense : expenses) {
            if (expense.getCategoryId() == categoryId) {
                filteredExpenses.add(expense);
            }
        }
        return filteredExpenses;
    }

    public double getTotalExpensesByCategory(int categoryId) {
        double total = 0;
        for (Expense expense : expenses) {
            if (expense.getCategoryId() == categoryId) {
                total += expense.getAmount();
            }
        }
        return total;
    }

    public List<Map.Entry<Category, Double>> getCategoryStatistics(List<Category> categories) {
        Map<Category, Double> categoryTotalMap = new HashMap<>();

        for (Category category : categories) {
            double total = getTotalExpensesByCategory(category.getId());
            categoryTotalMap.put(category, total);
        }

        List<Map.Entry<Category, Double>> sortedCategories = new ArrayList<>(categoryTotalMap.entrySet());
        sortedCategories.sort((entry1, entry2) -> Double.compare(entry2.getValue(), entry1.getValue()));

        return sortedCategories;
    }

    public List<Expense> getAllExpenses() {
        return expenses;
    }

}
