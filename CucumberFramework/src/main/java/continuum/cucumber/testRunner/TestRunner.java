package continuum.cucumber.testRunner;
import java.io.File;

import org.junit.runner.RunWith;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.testng.Reporter;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import continuum.cucumber.DriverFactory;
import cucumber.api.testng.TestNGCucumberRunner;
import cucumber.api.testng.TestNgReporter;
import cucumber.api.CucumberOptions;
import cucumber.api.Scenario;
import cucumber.api.java.After;
import cucumber.api.java.Before;
import cucumber.api.junit.Cucumber;
import cucumber.api.testng.AbstractTestNGCucumberTests;
import cucumber.api.testng.TestNGCucumberRunner;





@RunWith(Cucumber.class)
@CucumberOptions(
features = "src//test//resources//features//DemoSprint//ITSBanner.feature",
glue="continuum.cucumber.stepDefinations",
plugin = {
"pretty",
"html:test-report/cucumber",
"json:test-report/cucumber.json",
"rerun:target/rerun.txt" },
tags = {"@SmokeTest"}
)
public class TestRunner extends AbstractTestNGCucumberTests
{


}