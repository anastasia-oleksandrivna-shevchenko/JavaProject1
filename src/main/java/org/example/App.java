package org.example;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class App {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        ObjectMapper objectMapper = new ObjectMapper();
        JsonDataSource jsonDataSource = JsonDataSource.getInstance(objectMapper);

        CategoryManager categoryManager = new CategoryManager(jsonDataSource);
        ExpenseManager expenseManager = new ExpenseManager(jsonDataSource);

        categoryManager.loadData();
        expenseManager.loadData();

        while (true) {
            System.out.println("Menu:");
            System.out.println("Choose what you want to do:");
            System.out.println("Enter: ADD, DELETE, VIEW, UPDATE, STATISTICS, EXIT ");
            String input = scanner.nextLine().trim();
            Menu choice;
            try {
                choice = Menu.valueOf(input.toUpperCase());
            }catch (IllegalArgumentException e) {
                System.out.println("Invalid option. Please try again.");
                continue;
            }
            switch (choice) {
                case ADD:
                    System.out.println("Enter what you want to add: \nCATEGORY,EXPENSE");
                    String inputName = scanner.nextLine().trim();
                    WorkingWith name = WorkingWith.valueOf(inputName.toUpperCase());
                    switch (name) {
                        case EXPENSE:
                            viewCategories(categoryManager);
                            System.out.print("Enter expense ID: ");
                            int expenseId = scanner.nextInt();
                            scanner.nextLine();
                            System.out.print("Enter expense description: ");
                            String expenseDesc = scanner.nextLine().trim();
                            System.out.print("Enter expense amount: ");
                            double expenseAmount = scanner.nextDouble();
                            scanner.nextLine();
                            System.out.print("Enter category ID for the expense: ");
                            int expenseCategoryId = scanner.nextInt();
                            scanner.nextLine();
                            expenseManager.addExpense(new Expense(expenseId, expenseDesc, expenseAmount, expenseCategoryId));
                            break;
                        case CATEGORY:
                            System.out.print("Enter category ID: ");
                            int categoryId = scanner.nextInt();
                            scanner.nextLine();
                            System.out.print("Enter category name: ");
                            String categoryName = scanner.nextLine().trim();
                            categoryManager.addCategory(new Category(categoryId, categoryName));
                            break;
                    }
                    break;
                case UPDATE:
                    System.out.println("Enter what you want to update: \nCATEGORY,EXPENSE");
                    String inputUpdateName = scanner.nextLine().trim();
                    WorkingWith updateName = WorkingWith.valueOf(inputUpdateName.toUpperCase());
                    switch (updateName) {
                        case EXPENSE:
                            viewExpenses(expenseManager);
                            System.out.print("Enter expense ID to update: ");
                            int updateExpenseId = scanner.nextInt();
                            scanner.nextLine();
                            System.out.print("Enter new description: ");
                            String newDesc = scanner.nextLine().trim();
                            System.out.print("Enter new amount: ");
                            double newAmount = scanner.nextDouble();
                            scanner.nextLine();
                            System.out.print("Enter new category ID: ");
                            int newCategoryId = scanner.nextInt();
                            scanner.nextLine();
                            expenseManager.updateExpenses(updateExpenseId, newDesc, newAmount, newCategoryId);
                            break;
                        case CATEGORY:
                            viewCategories(categoryManager);
                            System.out.print("Enter category ID to update: ");
                            int updateCategoryId = scanner.nextInt();
                            scanner.nextLine();
                            System.out.print("Enter new category name: ");
                            String newCategoryName = scanner.nextLine().trim();
                            categoryManager.updateCategory(updateCategoryId, newCategoryName);
                            break;

                    }
                    break;
                case VIEW:
                    System.out.println("Enter what you want to view: \nCATEGORY,EXPENSE");
                    String inputViewName = scanner.nextLine().trim();
                    WorkingWith viewName = WorkingWith.valueOf(inputViewName.toUpperCase());
                    switch (viewName) {
                        case EXPENSE:
                            System.out.println("Do you wanna view expenses by category? (yes/no)");
                            String answer = scanner.nextLine().trim();
                            if (answer.equalsIgnoreCase("yes")) {
                                viewCategories(categoryManager);
                                System.out.print("Enter category ID to view expenses: ");
                                int selectedCategoryId = scanner.nextInt();
                                scanner.nextLine();
                                viewExpensesByCategory(expenseManager,  selectedCategoryId);
                            } else {
                                viewExpenses(expenseManager);
                            }
                            break;
                        case CATEGORY:
                            viewCategories(categoryManager);
                            break;
                    }
                    break;
                case DELETE:
                    System.out.println("Enter what you want to delete: \nCATEGORY,EXPENSE");
                    String inputDeleteName = scanner.nextLine().trim();
                    WorkingWith deleteName = WorkingWith.valueOf(inputDeleteName.toUpperCase());
                    switch (deleteName) {
                        case EXPENSE:
                            viewExpenses(expenseManager);
                            System.out.print("Enter expense ID to delete: ");
                            int deleteExpId = scanner.nextInt();
                            scanner.nextLine();
                            expenseManager.deleteExpenses(deleteExpId);
                            break;
                        case CATEGORY:
                            viewCategories(categoryManager);
                            System.out.print("Enter category ID to delete: ");
                            int deleteCatId = scanner.nextInt();
                            scanner.nextLine();
                            categoryManager.deleteCategory(deleteCatId);
                            break;

                    }
                    break;
                case STATISTICS:
                    viewStatistics(categoryManager, expenseManager);
                    break;
                case EXIT:
                    System.out.println("Exiting...");
                    return;
                default:
                    System.out.println("Invalid option. Please try again.");
            }


        }
    }

    public static void viewStatistics(CategoryManager categoryManager, ExpenseManager expenseManager) {
        List<Category> allCategories = categoryManager.getAllCategories();
        List<Map.Entry<Category, Double>> statistics = expenseManager.getCategoryStatistics(allCategories);
        System.out.println("Statistics (Categories sorted by total expenses):");
        for (Map.Entry<Category, Double> entry : statistics) {
            System.out.println("Category: " + entry.getKey().getName() + " | Total expenses: " + entry.getValue());
        }
    }
    public static void viewExpenses(ExpenseManager expenseManager){
        System.out.println("Expenses: ");
        for (Expense expense : expenseManager.getAllExpenses()) {
            System.out.println(expense);
        }
    }
    public static void viewCategories(CategoryManager categoryManager) {
        System.out.println("Categories: ");
        for (Category category : categoryManager.getAllCategories()) {
            System.out.println(category);
        }
    }
    public static void viewExpensesByCategory(ExpenseManager expenseManager, int selectedCategoryId) {

        List<Expense> expensesByCategory = expenseManager.getExpensesByCategory(selectedCategoryId);
        if (expensesByCategory.isEmpty()) {
            System.out.println("No expenses found for this category.");
        } else {
            System.out.println("Expenses in this category:");
            for (Expense expense : expensesByCategory) {
                System.out.println(expense);
            }
        }
        double total = expenseManager.getTotalExpensesByCategory(selectedCategoryId);
        System.out.println("Total expenses for this category: " + total);
    }
}




