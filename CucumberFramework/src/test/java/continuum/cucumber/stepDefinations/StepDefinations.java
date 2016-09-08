package continuum.cucumber.stepDefinations;


import static org.testng.AssertJUnit.assertEquals;

import java.util.List;

import org.openqa.selenium.WebDriver;
import org.testng.Reporter;

import continuum.cucumber.DatabaseUtility;
import continuum.cucumber.DriverFactory;
import continuum.cucumber.Utilities;
import continuum.cucumber.Page.LoginPage;
import continuum.cucumber.Page.PageFactory;
import cucumber.api.DataTable;
import cucumber.api.PendingException;
import cucumber.api.Scenario;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import cucumber.api.java.en.Given;

public class StepDefinations extends PageFactory {
		
	@Given("^User can navigate to ITS Portal for \"([^\"]*)\"$")
	 public void user_can_navigate_to_ITS_Portal(String env)  {
		 Reporter.log("Verifying user is navigated to ITS portal");
	  if(Utilities.getMavenProperties("Environment").equalsIgnoreCase(env)){
	   loginPage.openApplication();
	   homePage.navigateToITSPortal();
	  }
	  else
	  {
	   System.out.println("******WRONG ENVIRONMENT*********");
	   throw new PendingException();
	  }
	 }
	
	

	@When("^Enter Login credentials \"([^\"]*)\" and  \"([^\"]*)\"$")
	public void enter_Login_credentials(String email,String pwd)  {
		 Reporter.log("Logging to ITS portal");
		loginPage.openApplication();
		loginPage.loginToITSPortal(email,pwd);
	}
	
	
	
	
	@Then("^Verify user is login to ITS portal$")
	public void verify_user_is_login_to_ITS_portal() throws Throwable {
		homePage.verifyLoginToITSPortal();
	}

	

	@Then("^Verify user is able to logout$")
	public void verify_user_is_able_to_logout() throws Throwable {
		System.out.println("Step defination for Logging out of ITS portal");
	    homePage.logoutOfITSPortal();
	}

	
	
	
	@Then("^Verify banner is available for authorised \"([^\"]*)\"$")
	public void verify_banner_is_available_for_authorised(String roles) throws Throwable  {
		 Reporter.log("Verifying banner available for authorised user");
		homePage.verifyDashboardBanner(roles);
	   
	}

	@Then("^Verify banner can be deleted for \"([^\"]*)\"$")
	public void verify_banner_can_be_deleted(String roles) throws Throwable {
		 Reporter.log("Verifying deletion of banner");
	    // Write code here that turns the phrase above into concrete actions
	    homePage.deleteBanner(roles);
	}

	@Then("^Verify banner is not present \"([^\"]*)\"$")
	public void verify_banner_is_not_present(String roles) throws Throwable {
		 Reporter.log("Verifying banner  is available on ITS portal");
	    // Write code here that turns the phrase above into concrete actions
		homePage.verifyBannerNotPresent(roles);
	 
}
	
}
