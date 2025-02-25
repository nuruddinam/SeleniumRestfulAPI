import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import org.testng.asserts.Assertion;

import example.StaticProvider;

public class TestNG {

    String name = "test";

    @BeforeClass
    public void setUpClass(){
        System.out.println("Ini untuk setup class" );
    }

    @BeforeMethod
    public void setUp(){
        System.out.println("Before Method");
    }

   @Test
    public void scenarioTest1(){
        Assert.assertEquals(name, "test");
        System.out.println("Scenario 1");
    }
    @Test
    public void scenarioTest2(){
        Assert.assertEquals(name, "test");  
        System.out.println("Scenario 2");  
    }

    @Test
    public void scenarioTest3(){
        Assert.assertEquals(name, "test");
        System.out.println("Scenario 3");
    }

    @Test(dataProvider = "dataProviderPositive", dataProviderClass = StaticProvider.class)
    public void dataTestScenario(String name, int age){
        System.out.println(" Nama : " + name + " Umur : " + age );
    }


    @AfterMethod
    public void afterUp(){
        System.out.println("After Method");
    }

    @AfterClass
    public void setUpAfterClass(){
        System.out.println("Ini adalah setup after class");
    }

    // @DataProvider(name="dataProviderPositive")
    //     public Object[][] dataTestPositiveCase(){
    //         return new Object[][]{
    //             {"Rudi", 10},
    //             {"Sari", 20},
    //             {"Joko", 25}
    //         };
    //     }

    // @DataProvider(name="dataProviderNegative")
    //     public Object[][] dataTestNegativeCase(){
    //         return new Object[][]{
    //             {"Rudi", 10},
    //             {"Sari", 20},
    //             {"Joko", 25}
    //         };
    //     }


        }

