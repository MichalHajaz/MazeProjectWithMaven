import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;
import org.junit.runner.RunWith;


    @RunWith(Cucumber.class)
    @CucumberOptions(plugin = {"pretty"}, features = "/Users/mhajaz/MazeProjectWithMaven/src/test/java/CucumberTest/IntermediatePlayer.feature")
    public class RunCucumberTest {
    }



