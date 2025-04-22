package org.example;

import dagger.Component;

@Component(modules = {JsonDataSourceModule.class, ManagerModule.class})
public interface AppComponent {
    void inject(App app);
}
