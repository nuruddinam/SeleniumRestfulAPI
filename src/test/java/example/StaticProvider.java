package example;

import org.testng.annotations.DataProvider;

public class StaticProvider {
     @DataProvider(name="dataProviderPositive")
        public Object[][] dataTestPositiveCase(){
            return new Object[][]{
                {"Rudi", 10},
                {"Sari", 20},
                {"Joko", 25}
            };
        }

    @DataProvider(name="dataProviderNegative")
        public Object[][] dataTestNegativeCase(){
            return new Object[][]{
                {"Rudi", 10},
                {"Sari", 20},
                {"Joko", 25}
            };
        }
}
