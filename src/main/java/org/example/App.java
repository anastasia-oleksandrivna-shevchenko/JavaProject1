package org.example;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class App 
{

    public static void main( String[] args )
    {
        Scanner scanner = new Scanner(System.in);
        ExpenseManager expenseManager = new ExpenseManager();
        CategoryManager categoryManager = new CategoryManager();

        while(true){

            System.out.println("\nMenu:");
            System.out.println("1. Add category");
            System.out.println("2. View categories");
            System.out.println("3. Add expense");
            System.out.println("4. View expenses");
            System.out.println("5. Update category");
            System.out.println("6. Delete category");
            System.out.println("7. Update expense");
            System.out.println("8. Delete expense");
            System.out.println("9. View Expenses by Category");
            System.out.println("10. Statistics");
            System.out.println("11. Exit");
            System.out.print("Choose an option: ");
            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    System.out.print("Enter category ID: ");
                    int catId = scanner.nextInt();
                    scanner.nextLine();
                    System.out.print("Enter category name: ");
                    String catName = scanner.nextLine();
                    categoryManager.addCategory(new Category(catId, catName));
                    break;
                case 2:
                    System.out.println("Categories: ");
                    for (Category category : categoryManager.getAllCategories()) {
                        System.out.println(category);
                    }
                    break;
                case 3:
                    System.out.print("Enter expense ID: ");
                    int expId = scanner.nextInt();
                    scanner.nextLine();
                    System.out.print("Enter expense description: ");
                    String expDesc = scanner.nextLine();
                    System.out.print("Enter expense amount: ");
                    double expAmount = scanner.nextDouble();
                    System.out.print("Enter category ID for the expense: ");
                    int expCatId = scanner.nextInt();
                    expenseManager.addExpense(new Expense(expId, expDesc, expAmount, expCatId));
                    break;
                case 4:
                    System.out.println("Expenses: ");
                    for (Expense expense : expenseManager.getAllExpenses()) {
                        System.out.println(expense);
                    }
                    break;
                case 5:
                    System.out.print("Enter category ID to update: ");
                    int updateCatId = scanner.nextInt();
                    scanner.nextLine();
                    System.out.print("Enter new category name: ");
                    String newCatName = scanner.nextLine();
                    categoryManager.updateCategory(updateCatId, newCatName);
                    break;
                case 6:
                    System.out.print("Enter category ID to delete: ");
                    int deleteCatId = scanner.nextInt();
                    categoryManager.deleteCategory(deleteCatId);
                    break;
                case 7:
                    System.out.print("Enter expense ID to update: ");
                    int updateExpId = scanner.nextInt();
                    scanner.nextLine();
                    System.out.print("Enter new description: ");
                    String newDesc = scanner.nextLine();
                    System.out.print("Enter new amount: ");
                    double newAmount = scanner.nextDouble();
                    System.out.print("Enter new category ID: ");
                    int newCategoryId = scanner.nextInt();
                    expenseManager.updateExpenses(updateExpId, newDesc, newAmount, newCategoryId);
                    break;
                case 8:
                    System.out.print("Enter expense ID to delete: ");
                    int deleteExpId = scanner.nextInt();
                    expenseManager.deleteExpenses(deleteExpId);
                    break;
                case 9:
                    System.out.print("Enter category ID to view expenses: ");
                    int selectedCategoryId = scanner.nextInt();
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
                    break;
                case 10:
                    List<Category> allCategories = categoryManager.getAllCategories();
                    List<Map.Entry<Category, Double>> statistics = expenseManager.getCategoryStatistics(allCategories);
                    System.out.println("Statistics (Categories sorted by total expenses):");
                    for (Map.Entry<Category, Double> entry : statistics) {
                        System.out.println("Category: " + entry.getKey().getName() + " | Total expenses: " + entry.getValue());
                    }
                    break;
                case 11:
                    System.out.println("Exiting...");
                    return;
                default:
                    System.out.println("Invalid option. Please try again.");
            }
        }
    }
}
