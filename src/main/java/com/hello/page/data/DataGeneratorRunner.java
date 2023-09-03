package com.hello.page.data;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DataGeneratorRunner implements CommandLineRunner {

    private final DataGenerator dataGenerator;

    public DataGeneratorRunner(DataGenerator dataGenerator) {
        this.dataGenerator = dataGenerator;
    }

    @Override
    public void run(String... args) throws Exception {
        dataGenerator.generateData();
    }
}
