
package cucumberOptions;
import org.junit.runner.RunWith;
import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;

@RunWith(Cucumber.class)
@CucumberOptions(
		features="src/test/java/features",
		glue = {"stepDefination"},
		dryRun=true,
		publish = true,
		//tags = "@getBookStatus" 
		plugin = { 
		//		"pretty",
		 //       "html:target/cucumber-html-report",
		   //   "json:target/cucumber.json"
		//        "junit:target/cucumber.xml"
		      }
				)
public class TestRunner {
}
