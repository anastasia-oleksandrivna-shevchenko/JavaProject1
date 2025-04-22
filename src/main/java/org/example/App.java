package org.example;

import java.util.List;
import java.util.Map;
import java.util.Scanner;

import javax.inject.Inject;

public class App {

    @Inject
    ExpenseManager expenseManager;
    @Inject
    CategoryManager categoryManager;

    public static void main(String[] args) {

        AppComponent appComponent = DaggerAppComponent.create();

        App app = new App();
        appComponent.inject(app);

        app.categoryManager.loadData();
        app.expenseManager.loadData();


        Scanner scanner = new Scanner(System.in);

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
                            viewCategories(app.categoryManager);
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
                            app.expenseManager.addExpense(new Expense(expenseId, expenseDesc, expenseAmount, expenseCategoryId));
                            break;
                        case CATEGORY:
                            System.out.print("Enter category ID: ");
                            int categoryId = scanner.nextInt();
                            scanner.nextLine();
                            System.out.print("Enter category name: ");
                            String categoryName = scanner.nextLine().trim();
                            app.categoryManager.addCategory(new Category(categoryId, categoryName));
                            break;
                    }
                    break;
                case UPDATE:
                    System.out.println("Enter what you want to update: \nCATEGORY,EXPENSE");
                    String inputUpdateName = scanner.nextLine().trim();
                    WorkingWith updateName = WorkingWith.valueOf(inputUpdateName.toUpperCase());
                    switch (updateName) {
                        case EXPENSE:
                            viewExpenses(app.expenseManager);
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
                            app.expenseManager.updateExpenses(updateExpenseId, newDesc, newAmount, newCategoryId);
                            break;
                        case CATEGORY:
                            viewCategories(app.categoryManager);
                            System.out.print("Enter category ID to update: ");
                            int updateCategoryId = scanner.nextInt();
                            scanner.nextLine();
                            System.out.print("Enter new category name: ");
                            String newCategoryName = scanner.nextLine().trim();
                            app.categoryManager.updateCategory(updateCategoryId, newCategoryName);
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
                                viewCategories(app.categoryManager);
                                System.out.print("Enter category ID to view expenses: ");
                                int selectedCategoryId = scanner.nextInt();
                                scanner.nextLine();
                                viewExpensesByCategory(app.expenseManager,  selectedCategoryId);
                            } else {
                                viewExpenses(app.expenseManager);
                            }
                            break;
                        case CATEGORY:
                            viewCategories(app.categoryManager);
                            break;
                    }
                    break;
                case DELETE:
                    System.out.println("Enter what you want to delete: \nCATEGORY,EXPENSE");
                    String inputDeleteName = scanner.nextLine().trim();
                    WorkingWith deleteName = WorkingWith.valueOf(inputDeleteName.toUpperCase());
                    switch (deleteName) {
                        case EXPENSE:
                            viewExpenses(app.expenseManager);
                            System.out.print("Enter expense ID to delete: ");
                            int deleteExpId = scanner.nextInt();
                            scanner.nextLine();
                            app.expenseManager.deleteExpenses(deleteExpId);
                            break;
                        case CATEGORY:
                            viewCategories(app.categoryManager);
                            System.out.print("Enter category ID to delete: ");
                            int deleteCatId = scanner.nextInt();
                            scanner.nextLine();
                            app.categoryManager.deleteCategory(deleteCatId);
                            break;

                    }
                    break;
                case STATISTICS:
                    viewStatistics(app.categoryManager, app.expenseManager);
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




