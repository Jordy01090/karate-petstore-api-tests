package runner;
import com.intuit.karate.junit5.Karate;

public class PetStoreRunner {

    @Karate.Test
    Karate petstore() {
        // Si tu feature está en src/test/resources/features/petstore.feature
        return Karate.run("classpath:features/petstore.feature");
    }
}