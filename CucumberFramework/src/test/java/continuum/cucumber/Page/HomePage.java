package continuum.cucumber.Page;

import org.testng.Reporter;

import continuum.cucumber.Locator;
import continuum.cucumber.Utilities;
import continuum.cucumber.WebdriverWrapper;

public class HomePage {

	
	WebdriverWrapper wd=new WebdriverWrapper();
	
	public Locator welcomeMsg= new Locator("Welcome message on login page","//nav[@role='navigation']//span[contains(.,'Welcome')]"); 
	public Locator logutBtn=new Locator("Logout Btn","//a[@href='/QADashB/ContinuumLogin/ContinuumLogout']");
	
     public void navigateToITSPortal(){
    	//	System.out.println("hp driver id:"+ wd.driver);
    		wd.waitImplicit(5000);
    	 wd.verifyCurrentUrl(Utilities.getMavenProperties("browser"));
    	 Reporter.log("User is navigated to ITS portal");
		
	}
	
	public void verifyLoginToITSPortal(){
		wd.verifyElementPresent(welcomeMsg);
		
	}

	public void verifyDashboardIsDisplayed() {
		// TODO Auto-generated method stub
		
	}

	public void logoutOfITSPortal() {
		
		wd.mouseHover(welcomeMsg);
		wd.clickElement(logutBtn);
	}

	public void verifyBanner() {
		// TODO Auto-generated method stub
		
	}

}
