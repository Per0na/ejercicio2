package es.per0na.vincle.components;

import es.per0na.vincle.data.seed.SeedDataGenerator;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class SeedDataInitializer implements CommandLineRunner {

    private final SeedDataGenerator seedDataGenerator;

    public SeedDataInitializer(SeedDataGenerator seedDataGenerator) {
        this.seedDataGenerator = seedDataGenerator;
    }

    @Override
    public void run(String... args) {
        if (args.length > 0 && "--seed".equals(args[0])) {
            //seedDataGenerator.generateSeedData(args);
            System.out.println("Seed data generation is disabled");
            System.exit(0);
        }
    }
}

