package continuum.cucumber.Page;

import java.sql.Connection;
import java.util.List;
import java.util.ListIterator;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Reporter;

import continuum.cucumber.DatabaseUtility;
import continuum.cucumber.Locator;
import continuum.cucumber.Utilities;
import continuum.cucumber.WebdriverWrapper;

public class LoginPage {
	
	 WebdriverWrapper wd=new WebdriverWrapper();

	public Locator emailId= new Locator("Login email id textbox", "user_txt","id");
	public Locator password= new Locator("Login password text box", "user_pass","id");
	public Locator loginBtn= new Locator("Login button", "//button[@value='Submit']//em[contains(.,'Login')]");
	public Locator rememberMeCheckbox=new Locator("Remember me checkbox","//span[@class='cust_checkbox checkbox cust_checkbox_off']");
	
	public void loginToITSPortal(String emaild, String pwd){
	
		
    Reporter.log("Enter login credentials  to ITS portal");
	    wd.waitForElementToBeClickable(emailId,3000);
		wd.clearandSendKeys(emaild, emailId);
		wd.clearandSendKeys(pwd, password);
		wd.changeCheckboxStatus( rememberMeCheckbox,"check");
		wd.clickElement(loginBtn);
		 if(wd.isAlertPresent())
			    wd.acceptAlert();
		
			 wd.clearandSendKeys(emaild, emailId);
			 wd.clearandSendKeys(pwd, password);
   			wd.clickElement(loginBtn);
	  
				
	}

	public void testConnection(){
		final String databaseName=Utilities.getMavenProperties("DBName");
		final String sqlServerURL=Utilities.getMavenProperties("DBServerUrl");
		final String username=Utilities.getMavenProperties("DBUsername");
		final String password=Utilities.getMavenProperties("DBPwd");
		Connection conn =DatabaseUtility.createConnection(databaseName, sqlServerURL, username, password);
	if(conn!=null)
		System.out.println("Database connection made");
	}

}
