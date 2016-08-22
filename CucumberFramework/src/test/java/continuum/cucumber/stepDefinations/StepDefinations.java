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
	
	
	
	@Given("^User can navigate to ITS Portal$")
	public void user_can_navigate_to_ITS_Portal() throws Throwable {
		 homePage.navigateToITSPortal();
		//loginPage.testConnection();
	}

	@When("^Enter Login credentials \"([^\"]*)\" and  \"([^\"]*)\"$")
	public void enter_Login_credentials(String email,String pwd)  {
		loginPage.loginToITSPortal(email,pwd);
	}
	
	/*@When("^Enter Login credentials$")
	public void enter_EmailId_Password()  {
		loginPage.loginToITSPortal("automationBDD@continuum.net","Abc@12345");
	}*/

	@Then("^Verify user is login to ITS portal$")
	public void verify_user_is_login_to_ITS_portal() throws Throwable {
		homePage.verifyLoginToITSPortal();
	}

	@Then("^Dashboard is displayed by default$")
	public void dashboard_is_displayed_by_default() throws Throwable {
	    homePage.verifyDashboardIsDisplayed();
	}

	@Then("^Verify user is able to logout$")
	public void verify_user_is_able_to_logout() throws Throwable {
	    homePage.logoutOfITSPortal();
	}

	@Given("^User can navigate to Quick Access Page$")
	public void user_can_navigate_to_Quick_Access_Page()  {
	   
	}

	@When("^User should go to QuickAccess->Site -> Server$")
	public void user_should_go_to_QuickAccess_Site_Server()  {
	    
	}

	@Then("^Validate total server count$")
	public void validate_total_server_count()  {
	   
	}
	
	@Then("^Update Result to TestRail \"([^\"]*)\"$")
	public void update_Result_to_TestRail(String arg1)  {
	 
	}
	
	
	@Then("^verify banner is available for authorised \"([^\"]*)\"$")
	public void verify_banner_is_available_for_authorised(String roles)  {
		homePage.verifyBanner();
	   
	}

	@Then("^verify if banner is removed from dashboard then banner should not be displayed after relogin$")
	public void verify_if_banner_is_removed_from_dashboard_then_banner_should_not_be_displayed_after_relogin() throws Throwable {
	    
	}
}
