import org.testng.Assert;
import org.testng.annotations.Test;
import org.testng.asserts.Assertion;

import example.StaticProvider;

public class ParallelTestNG02 {

    String name = "test";

   @Test
    public void scenarioTest1(){
        Assert.assertEquals(name, "test");
        System.out.println("Scenario 1");
        System.out.println(Thread.currentThread().getId());
    }
    @Test
    public void scenarioTest2(){
        Assert.assertEquals(name, "test");  
        System.out.println("Scenario 2");  
        System.out.println(Thread.currentThread().getId());
    }

    @Test
    public void scenarioTest3(){
        Assert.assertEquals(name, "test");
        System.out.println("Scenario 3");
        System.out.println(Thread.currentThread().getId());
    }

    @Test(dataProvider = "dataProviderPositive", dataProviderClass = StaticProvider.class)
    public void dataTestScenario(String name, int age){
        System.out.println(" Nama : " + name + " Umur : " + age );
    }


}