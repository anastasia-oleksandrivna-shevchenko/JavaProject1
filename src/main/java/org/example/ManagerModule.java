package org.example;

import dagger.Module;
import dagger.Provides;

@Module
public class ManagerModule {

    @Provides
    public CategoryManager provideCategoryManager(JsonDataSource jsonDataSource) {
        return new CategoryManager(jsonDataSource);
    }

    @Provides
    public ExpenseManager provideExpenseManager(JsonDataSource jsonDataSource) {
        return new ExpenseManager(jsonDataSource);
    }
}
