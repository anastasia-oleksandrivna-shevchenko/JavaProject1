package org.example;
import com.fasterxml.jackson.databind.ObjectMapper;
import dagger.Module;
import dagger.Provides;
import jakarta.inject.Singleton;

@Module
public class JsonDataSourceModule {

    @Provides
    @Singleton
    public ObjectMapper provideObjectMapper() {
        return new ObjectMapper();
    }
    @Provides
    @Singleton
    public JsonDataSource provideJsonDataSource(ObjectMapper objectMapper) {
        return new JsonDataSource(objectMapper);
    }
}
