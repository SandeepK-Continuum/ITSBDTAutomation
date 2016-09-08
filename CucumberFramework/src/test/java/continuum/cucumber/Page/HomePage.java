package continuum.cucumber.Page;

import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.Reporter;



import continuum.cucumber.Locator;
import continuum.cucumber.Utilities;
import continuum.cucumber.WebdriverWrapper;

public class HomePage {

	
	WebdriverWrapper wd=new WebdriverWrapper();
	
	public Locator welcomeMsg= new Locator("Welcome message on login page","//nav[@role='navigation']//span[contains(.,'Welcome')]"); 
	public Locator logutBtn=new Locator("Logout Btn","//a[@href='/QADashB/ContinuumLogin/ContinuumLogout']");
	public Locator verifyBanner= new Locator("Check Banner ","//div[@class='col-lg-12 col-md-12 col-sm-12 col-xs-12']/div");
	public Locator dropMenu=new Locator("Welcome drop menu","//a[@class='dropmenuselect']");
	public Locator deleteBanner= new Locator ("Delete Banner","//button[@class='close btnBannerClose']"); 
	 
     public void navigateToITSPortal(){
    	//	System.out.println("hp driver id:"+ wd.driver);
    		wd.waitImplicit(1000);
    	 wd.verifyCurrentUrl(Utilities.getMavenProperties("browser"));
    	 Reporter.log("User is navigated to ITS portal");
    	 System.out.println("User is navigated to ITS portal");
		
	}
	
	public void verifyLoginToITSPortal(){
		wd.verifyElementPresent(welcomeMsg);
		
	}

	public void verifyDashboardIsDisplayed() {
		// TODO Auto-generated method stub
		
	}

	public void logoutOfITSPortal() {
		
		System.out.println("Logging out of ITS portal");
		wd.waitFor(1000);
		wd.waitForElementToBeClickable(welcomeMsg,2000);
		wd.mouseHover(welcomeMsg);
		wd.waitForElementToBeClickable(logutBtn, 2000);
		wd.clickElement(logutBtn);
        wd.getWebdriver().manage().deleteAllCookies();
      
	}

	
		 public void verifyDashboardBanner(String roles) {
			 System.out.println("Verifying banner of ITS portal");
			  wd.waitFor(2000);
			  if(roles.equalsIgnoreCase("Super User")|| roles.equalsIgnoreCase("Manager") || roles.equalsIgnoreCase("Technician"))
			   {
			     
			      boolean actual=wd.findElementPresent(verifyBanner);
				  Assert.assertEquals(actual,true);
				  Reporter.log("Banner is  present for "+ roles);
	  
			   
			  }
			   else 
			   {
				   verifyBannerNotPresent(roles);
				 
			   }
		
	}
		 public void verifyBannerNotPresent(String roles)
		 {
			  Assert.assertEquals(wd.verifyElementNotPresent(verifyBanner),true);
			   Reporter.log("Banner is not present for "+ roles);  
		 }

		
		 
		 public void deleteBanner(String roles) {
			wd.waitFor(1000);
			wd.clickElement(deleteBanner);
			wd.waitFor(1000);
						
		}

}
