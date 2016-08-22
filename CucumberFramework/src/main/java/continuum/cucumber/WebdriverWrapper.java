package continuum.cucumber;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.remote.Augmenter;
import org.testng.Assert;
import org.testng.Reporter;

public class WebdriverWrapper {
	WebDriver driver=null;
	
	public WebdriverWrapper(){
	   driver = DriverFactory.getDriver();
	}
	
	
	public WebDriver getWebdriver(){
		return driver;
	}

	public  WebElement getWebElement(Locator loc)
	{
		 String by=loc.getByType().toLowerCase();
		 String ele=loc.getValue();
		 String key=loc.getKey();
		WebElement webEle=null;
		try{
		if(by.equalsIgnoreCase("id"))
			webEle=driver.findElement(By.id(ele));
		else if(by.equalsIgnoreCase("class"))
			webEle=driver.findElement(By.className(ele));
		else if(by.equalsIgnoreCase("name"))
			webEle=driver.findElement(By.name(ele));
		else if(by.equalsIgnoreCase("link"))
			webEle=driver.findElement(By.linkText(ele));
		else if(by.equalsIgnoreCase("xpath"))
			webEle=driver.findElement(By.xpath(ele));
		else if(by.equalsIgnoreCase("css"))
			webEle=driver.findElement(By.cssSelector(ele));
		else if(by.equalsIgnoreCase("tag"))
			webEle=driver.findElement(By.tagName(ele));
		if(by.toString().equals(null))
			webEle=driver.findElement(By.xpath(ele));
		if(!Utilities.getMavenProperties("browser").equalsIgnoreCase("IE"))
		{
			JavascriptExecutor js = (JavascriptExecutor)driver;
			js.executeScript("arguments[0].setAttribute('style', arguments[1]);",
					webEle, "color: orange; border: 3px solid orange;");
		}
		}catch(Exception e){
			System.out.println("Not able to locate element : "+key +" with locator :" +ele);
			Reporter.log("Not able to locate element : "+key +" with locator :" +ele);
			
		}
		
		return webEle;
}
	
	public By getBy(Locator loc){
		By by=null;
		String locValue= null;
		locValue = loc.getValue();
		
					switch(loc.byType){
					
						case "NAME":
							by = By.name(locValue);
							break;
						case "LINKTEXT":
							by = By.linkText(locValue);
							break;
						case "XPATH":
							by = By.xpath(locValue);
							break;
						case "TAGNAME":
							by = By.tagName(locValue);
							break;
						case "CSS":  case "CSSSELECTOR":
							by = By.cssSelector(locValue);
							break;	
							default:
								by = By.id(locValue);
			
						}
			
				return by;
		}
	

//	public void elementHighlight(Locator loc ) {
//		WebElement element = null;
//		element=getWebElement(loc);
//		if(!(element==null)){
//		for (int i = 0; i < 10; i++) {
//			
//			JavascriptExecutor js = (JavascriptExecutor) driver;
//			js.executeScript(
//					"arguments[0].setAttribute('style', arguments[1]);",
//					element, "color: orange; border: 3px solid orange;");
//			js.executeScript(
//					"arguments[0].setAttribute('style', arguments[1]);",
//					element, "");
//		}
//		}else{
//			Reporter.log( "Highlight Element "+loc.getKey()+" Fail Found Exception while accessing  ");	
//		}
//	}


	public WebElement waitForElementToBeDisplayed(final WebElement element,
			int timeOutPeriod) {
		WebDriverWait webDriverWait = new WebDriverWait(driver, timeOutPeriod);
		webDriverWait.pollingEvery(10, TimeUnit.MICROSECONDS);

		return webDriverWait.until(new ExpectedCondition<WebElement>() {

			public WebElement apply(WebDriver driver) {
				try {
					if (element.isDisplayed())
						return element;
					else
						return null;
				} catch (NoSuchElementException ex) {
					return null;
				} catch (StaleElementReferenceException ex) {
					return null;
				} catch (NullPointerException ex) {

					return null;
				}
			}

		});
	}

	public void waitImplicit(int timeOut) {
		driver.manage().timeouts().implicitlyWait(timeOut, TimeUnit.MILLISECONDS);;

	}

	public WebElement waitForElementToBeClickable( final Locator locate,int timeOutPeriod) {

		WebDriverWait webDriverWait = new WebDriverWait(driver, timeOutPeriod);
		webDriverWait.pollingEvery(10, TimeUnit.MICROSECONDS);
		return webDriverWait.until(new ExpectedCondition<WebElement>() {
			public WebElement apply(WebDriver driver) {
				try {
					WebElement element=getWebElement(locate); 
					if (element.isEnabled() && element.isDisplayed())
						return element;
					else
						return null;
				} catch (NoSuchElementException ex) {
					return null;
				} catch (StaleElementReferenceException ex) {
					return null;
				} catch (NullPointerException ex) {
					return null;
				}
			}

		});

	}

	

	

	

	public WebElement waitForElementToBeEnabled(final Locator loc, int timeOutPeriod) {
		WebDriverWait webDriverWait = new WebDriverWait(driver, timeOutPeriod);
		webDriverWait.pollingEvery(10, TimeUnit.MICROSECONDS);
		return webDriverWait.until(new ExpectedCondition<WebElement>() {
			 WebElement element=getWebElement(loc);
			public WebElement apply(WebDriver driver) {
				try {
					
					if (element.isEnabled())
						return element;
					else
						return null;
				} catch (NoSuchElementException ex) {
					return null;
				} catch (StaleElementReferenceException ex) {
					return null;
				} catch (NullPointerException ex) {
					return null;
				}
			}

		});

	}

	



	public WebElement waitForTextToAppearInTextField(
			final Locator loc, int timeOutPeriod) {

		WebDriverWait webDriverWait = new WebDriverWait(driver, timeOutPeriod);
		webDriverWait.pollingEvery(10, TimeUnit.MICROSECONDS);
		return webDriverWait.until(new ExpectedCondition<WebElement>() {
            WebElement webElement=getWebElement(loc);
			public WebElement apply(WebDriver driver) {
				try {
					String text = webElement.getText();
					if (text != null && text.equals("")) {
						return webElement;
					} else
						return null;

				} catch (NoSuchElementException ex) {
					return null;
				} catch (StaleElementReferenceException ex) {
					return null;
				} catch (NullPointerException ex) {
					return null;
				}
			}

		});

	}

	public boolean waitForOptionToBePresentInList(final By by, String value,
			int timeOutPeriod) {

		WebDriverWait webDriverWait = new WebDriverWait(driver, timeOutPeriod);
		webDriverWait.pollingEvery(10, TimeUnit.MICROSECONDS);
		try {
			webDriverWait.until(ExpectedConditions.textToBePresentInElement(
					driver.findElement(by), value));
			return true;
		} catch (TimeoutException e) {
			return false;
		}

		
	}

	

	public void bringElementInView(WebElement element) {
		((JavascriptExecutor) driver).executeScript(
				"arguments[0].scrollIntoView(true);", element);

	}

	

	public void waitForAlert(int timeOutPeriod) {
		WebDriverWait webDriverWait = new WebDriverWait(driver, timeOutPeriod);
		webDriverWait
				.ignoring(NoSuchElementException.class,
						StaleElementReferenceException.class)
				.pollingEvery(10, TimeUnit.MILLISECONDS)
				.until(ExpectedConditions.alertIsPresent());
	}


	public String getAlertMessage(int timeOutPeriod) {
		waitForAlert(timeOutPeriod);
		Alert alert = driver.switchTo().alert();
		String AlertMessage = alert.getText();
		return AlertMessage;
	}

	public String acceptAlert(int timeOutPeriod, String AlertMessage) {
		waitForAlert(timeOutPeriod);
		Alert alert = driver.switchTo().alert();
	
		alert.accept();
		return AlertMessage;
	}

	public String acceptAlert(int timeOutPeriod) {
		for (int checkAttempts = 1; checkAttempts <= 3; checkAttempts++) {
			waitForAlert(timeOutPeriod);
			if (isAlertPresent()) {
				break;
			}
		}
		Alert alert = driver.switchTo().alert();
		String AlertMessage = alert.getText();
	;
		alert.accept();
		return AlertMessage;
	}

	public String dismissAlert(int timeOutPeriod) {
		waitForAlert(timeOutPeriod);
		Alert alert = driver.switchTo().alert();
		String alertMessage = alert.getText();

		alert.dismiss();
		return alertMessage;
	}

	public void acceptAlert() {
		
			try {
				waitForAlert(20);
				driver.switchTo().alert().accept();
			} catch (Exception e) {

			
		}
	}

	public String getAlertMessage() {
		String alertMessage = new String("");
		try {
			waitForAlert(5);
		
			alertMessage = driver.switchTo().alert().getText();
			
		} catch (Exception e) {

		}
		return alertMessage;
	}

	public boolean isAlertPresentForScreenshot() {
		boolean isAlertPresent = false;
		try {
			driver.switchTo().alert();
			isAlertPresent = false;
		} catch (Exception e) {

		}
		return isAlertPresent;
	}

	public boolean isAlertPresent() {
		boolean isAlertPresent = false;
		try {
			waitForAlert(3);
			driver.switchTo().alert();
			isAlertPresent = true;
		} catch (Exception e) {

		}
		return isAlertPresent;
	}

	public boolean isAlertWithSpecifiedMessagePresent(String errorMessage) {
		boolean isAlertPresent = false;
		try {
			// waitForAlert(3);
			isAlertPresent = driver.switchTo().alert().getText()
					.contains(errorMessage);
		} catch (Exception e) {

		}
		return isAlertPresent;
	}

	public boolean isAlertNotPresent() {
		boolean isAlertNotPresent = false;
		try {
			driver.switchTo().alert();
			isAlertNotPresent = false;
		} catch (Exception e) {
			isAlertNotPresent = true;
		}
		return isAlertNotPresent;
	}

	public void waitFor(int waitTime) {

		try {
			Thread.sleep(waitTime);
		} catch (InterruptedException e) {

		}
	}



	public String getTextValue(String strElementName, Locator loc){
		
		String strTextValue=null;
		try{
			WebElement element = null;
			element=getWebElement(loc);	

			strTextValue=element.getText();
			Reporter.log("Get Value from " +strElementName + "Value stored is "+strTextValue);
			
		}catch(Exception e){
			Reporter.log("<B><I><font size='4' color='Blue'>"+"Failed to Fetch Text Value"+loc.getKey()+ "</font></I></B>");
			Assert.fail("Found Exception while storing value " +e.getMessage());
		}
		return strTextValue;	
	}
	
	
	public void verifyTextValue(String strElementName, Locator loc,String expectedValue){
		
		String strTextValue=null;
		try{
			
			
			WebElement element = null;
			element=getWebElement(loc);	
			
			strTextValue=element.getText();
			if(strTextValue.contains(",")){
				strTextValue=strTextValue.replace(",", "");
			}
			if(expectedValue.contains(",")){
				expectedValue=expectedValue.replace(",", "");
			}
			if(strTextValue.equalsIgnoreCase(expectedValue)){
				Reporter.log("Verify Text value of " +strElementName+" Expected value is "+expectedValue+" Pass Actual value  is "+strTextValue);
				System.out.println("Verify Text value of " +strElementName+" Expected value is "+expectedValue+" Pass Actual value  is "+strTextValue);
			}
			else{
				Reporter.log("Verify Text value of "+strElementName+" Expected value is "+expectedValue+" Fail Actual value  is "+strTextValue);
				System.out.println("Verify Text value of "+strElementName+" Expected value is "+expectedValue+" Fail Actual value  is "+strTextValue);
			}
			
		}catch(Exception e){
			Reporter.log("<B><I><font size='4' color='Blue'>"+"Verify Text value of " +strElementName+" Expected value is "+expectedValue+"</font></I></B>");
			Assert.fail("Verify Text value of " +strElementName+" Expected value is "+expectedValue+" Fail Found Exception while comparing "+e.getMessage());


		}
			
	}
	
	
	
	
	public void sendKeys( String key,Locator loc){
		
		try{
			
			getWebElement(loc).sendKeys(key);

		}catch(Exception e){
		
			Reporter.log( "Send key command "+key+" Fail Found Exception while sending keys"+e.getMessage());
			
		}
		
	}
	
	public String getDriverTitle( ){
		String strTextValue=null;
		
		try{
		
			strTextValue=driver.getTitle();
			Reporter.log( "Get Browser Title"+strTextValue+ " Pass Value stored is "+strTextValue);
			
		}catch(Exception e){
			Reporter.log("<B><I><font size='4' color='Blue'>"+"Get Browser Title"+strTextValue+"Fail Found Exception while storing driver title "+"</font></I></B>");
			Assert.fail( "Get Browser Title"+strTextValue+"Fail Found Exception while storing driver title "+e.getMessage());
			
		
		}
		return strTextValue;	
	}

	
	
	public void clearandSendKeys(String textTobeEntered,Locator loc){
		try{
			if(textTobeEntered == null || textTobeEntered == ""||textTobeEntered.equalsIgnoreCase("NA")){
				
				return;
			}

			
			WebElement element = null;
			element=getWebElement(loc);
			 Thread.sleep(2000);
			element.clear();
			 Thread.sleep(1000);
			
			 element.sendKeys(textTobeEntered);
			
			
			//System.out.println("Enter Value in " +loc.getKey()+" text field "+textTobeEntered+" Pass Value "+textTobeEntered+ " entered successfully");
			Reporter.log( "Enter Value in " +loc.getKey()+" text field "+textTobeEntered+" Pass Value "+textTobeEntered+ " entered successfully");	
		}catch(Exception e){
			Reporter.log("<B><I><font size='4' color='Blue'>"+"Enter Value in " +loc.getKey()+" text field "+textTobeEntered+" Fail Found Exception "+e.getMessage()+"</font></I></B>");
		//	System.out.println("Enter Value in " +loc.getKey()+" text field "+textTobeEntered+" Fail Found Exception  "+e.getMessage());
		}
		
	}
	

	public String getWindowId(RemoteWebDriver driver){
		
		String existingWindowID=null;
		
			try{
			existingWindowID = driver.getWindowHandle();
			}catch(Exception e){
				Reporter.log("<B><I><font size='4' color='Blue'>"+"Get current  window id Fail Found Exception while getting window id"+e.getMessage()+"</font></I></B>");
				
			}
		
			return existingWindowID;
			
		}

	public void switchToNewlyOpenedWindow(Set<String>  winIDs,RemoteWebDriver driver){
		
		try{
			Set<String> afterWindow = driver.getWindowHandles();
			//remove all the handles from before the popup window appears
			afterWindow.removeAll(winIDs);
			//there should be only one window handle left
			if(afterWindow.size() == 1) {
			         driver.switchTo().window((String)afterWindow.toArray()[0]);
			        
			         Thread.sleep(1000);
			          Reporter.log("New Window Title : " + driver.getTitle());
			         Reporter.log("New Window Url : " + driver.getCurrentUrl());
			}
			
			
			Reporter.log( "Switch to newly opened window Pass Successfully switched to new window");
			
		}catch(Exception e){
			Reporter.log("<B><I><font size='4' color='Blue'>"+"Switch to newly opened window Fail Found Exception while switching to new window "+e.getMessage()+"</font></I></B>");
			
		}
		
	}
	
	public String getAttributeValue(String strElementName, Locator loc, String attribute){
		String strTextValue=null;
		
		try{
			
			WebElement element = null;
			element=getWebElement(loc);
			
			strTextValue=element.getAttribute(attribute);
			Reporter.log( "Get "+attribute+" from " +strElementName+" "+strTextValue+" Pass Value stored is "+strTextValue);
			
		}catch(Exception e){
			Reporter.log("<B><I><font size='4' color='Blue'>"+"Get "+attribute+" from " +strElementName+" "+strTextValue+" Fail Found Exception while storing value "+e.getMessage()+"</font></I></B>");
			
		  
		}
		return strTextValue;	
	}
	
	public void changeCheckboxStatus(Locator loc, String statuReq){
				
	
				WebElement element = null;
				try{
				
				
				element=getWebElement(loc);
				if(statuReq.equalsIgnoreCase("uncheck") && element.isSelected()){
					element.click();	
				}
				if(statuReq.equalsIgnoreCase("check") && !element.isSelected()){
					element.click();
				}
				Reporter.log( "Change "+loc.getKey()+" checkbox status to "+statuReq);
		}catch(Exception e1){
			Reporter.log("<B><I><font size='4' color='Blue'>"+"Change "+loc.getKey()+" checkbox status"+statuReq+" Fail Found Exception "+e1.getMessage()+"</font></I></B>");
			
		}
	}
	
	
	public void  verifyAlertandClose(String elementName, String expectedValue,String operation){
			
		String alertText= null;
		
				try{
					
				Alert alert = driver.switchTo().alert();
			
			      alertText = alert.getText();
			     if (operation.equalsIgnoreCase("accept")) {
			       alert.accept();
			     } else {
			       alert.dismiss();
			     }
		if(alertText.equalsIgnoreCase(expectedValue)){
			Reporter.log( "Verify Alert "+elementName+" Expected value is "+expectedValue+" Pass Actual value  is "+alertText);	
		}
		else{
			Reporter.log( "Verify Alert Text "+elementName +" Expected value is "+expectedValue+" Fail Actual value  is "+alertText);
		}
		
	}catch(Exception e){
		Reporter.log("<B><I><font size='4' color='Blue'>"+"Verify Alert Text  Expected value is "+expectedValue+" Fail Found Exception while comparing "+e.getMessage()+"</font></I></B>");
		
	}
	}

		
	public boolean getCheckboxStatus(String strElementname,Locator loc){
		boolean status= false;
		
		WebElement element = null;
		
		try{
		
		
		element=getWebElement(loc);
		status= element.isSelected();
		Reporter.log( "Verify checkbox status "+strElementname+" Pass Checkbox is " + status);
		}catch(Exception e1){
			Reporter.log("<B><I><font size='4' color='Blue'>"+"Verify checkbox status "+strElementname+" Fail Found Exception " + status+"</font></I></B>");

		  }
		
		return status;
		}

	public void verifyAttributeValueContains(String strElementName, Locator loc, String expectedValue,String attribute,RemoteWebDriver driver){
		
		String strTextValue=null;
		try{
			
			WebElement element = null;
			element=getWebElement(loc);
			
			strTextValue=element.getAttribute(attribute);
			if(strTextValue.contains(",")){
				strTextValue=strTextValue.replace(",", "");
			}
			if(expectedValue.contains(",")){
				expectedValue=expectedValue.replace(",", "");
			}
			if(strTextValue.contains(expectedValue)){
				Reporter.log( "Verify "+attribute+" of " +strElementName+" Expected : "+expectedValue+" should contain "+strTextValue+" Pass Actual value  is "+strTextValue);	
			}
			else{
				Reporter.log( "Verify "+attribute+" of " +strElementName+" Expected : "+expectedValue+" should contain "+strTextValue+" Fail Actual value  is "+strTextValue);
			}
			
		}catch(Exception e){
			Reporter.log("<B><I><font size='4' color='Blue'>"+"Verify "+attribute+" of " +strElementName+" Expected : "+expectedValue+" should contain "+strTextValue+" Fail Found Exception "+e.getMessage()+"</font></I></B>");
			
		}
		
	}
		
	
	public void clickElement(Locator loc){
//		
//		
			WebElement element = null;
			element=getWebElement(loc);
			if(element.isDisplayed())
			{
				element.click();
				Reporter.log("Clicking on  "+loc.getKey());
				try {
					Thread.sleep(2000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}
			else
			{
				System.out.println(loc.getKey()+" Element is not displayed");
			    Reporter.log(loc.getKey()+" Element is not displayed");
			}
	}
	
	
	public void clickWebListElement(Locator loc,String identification){
		
		try{
			
			String[] identity=identification.split(":");
			List<WebElement> lst= getWebList(loc);
			WebElement element = null;
			if(identity[0].equalsIgnoreCase("index")){
				element=lst.get(Integer.parseInt(identity[1]));
			}else if(identity[0].equalsIgnoreCase("text")){
				for(int i=0; i<lst.size();i++){
					try{
						if(lst.get(i).getText().equalsIgnoreCase(identity[1])) element=lst.get(i);
					}catch(Exception e ){
						e.printStackTrace();
					}
				}
			}
			
		if(!(element== null)) element.click();	
			Reporter.log( "Clicked on " +loc.getKey()+" Successfully");	
		}catch(Exception e){
			Reporter.log("<B><I><font size='4' color='Blue'>"+"Clicked on " +loc.getKey()+" Failed Found Exception "+e.getMessage()+"</font></I></B>");
			
		}
			
	}
		
	public void doubleClick(String strElementName, Locator loc){
		
		try{
		
			Actions doubleClickAction = new Actions(driver); 
			
			
			WebElement element = null;
			element=getWebElement(loc);
			
			doubleClickAction.moveToElement(element).doubleClick().build().perform();
			Reporter.log( "Double Click " +strElementName+" Pass Clicked "+strElementName+" Successfully");	
		}catch(Exception e){
			Reporter.log("<B><I><font size='4' color='Blue'>"+"Double Click " +strElementName+" Fail Found Exception "+e.getMessage()+"</font></I></B>");
		//	executionFlag=false;
		}
			
	}
	
	public void selectValueFromDropDown(String strElementName, Locator loc,String valueTobeSelected){
		if(valueTobeSelected == null || valueTobeSelected == ""||valueTobeSelected.equalsIgnoreCase("NA")){
			return;
		}
		try{
			
			
			WebElement element = null;
			element=getWebElement(loc);
			System.out.println("Selecting "+valueTobeSelected);
			Select webCheckBox= new Select(element);
			if(valueTobeSelected.equalsIgnoreCase("EMPTY")){
				webCheckBox.selectByVisibleText("");
			}else{
				webCheckBox.selectByVisibleText(valueTobeSelected);
			}
			
			Reporter.log( "Select value from " +strElementName+" "+valueTobeSelected+" Pass Selected "+valueTobeSelected+" Successfully");	
		}catch(Exception e){
			Reporter.log("<B><I><font size='4' color='Blue'>"+"Select value from " +strElementName+" "+valueTobeSelected+" Fail  Found Exception while selecting value "+e.getMessage()+"</font></I></B>");	
			
		}
			
	}
		
	public void selectValueByIndex(String strElementName, Locator loc,int index ){
		String valueSelected="";
		try{
			
			
			WebElement element = null;
			element=getWebElement(loc);
			Select webCheckBox= new Select(element);
			webCheckBox.selectByIndex(index);
			valueSelected=webCheckBox.getFirstSelectedOption().getText();
			Reporter.log( "Select value from " +strElementName+" "+valueSelected+" Pass Selected "+valueSelected+" Successfully");	
		}catch(Exception e){
			Reporter.log("<B><I><font size='4' color='Blue'>"+"Select value from " +strElementName+" "+valueSelected+" Fail  Found Exception while selecting value "+e.getMessage()+"</font></I></B>");	
			
		}
			
	}
			
	public void selectByValue(String strElementName, Locator loc,String valueTobeSelected){
		if(valueTobeSelected == null || valueTobeSelected == ""||valueTobeSelected.equalsIgnoreCase("NA")){
			return;
		}
		try{
			
			
			WebElement element = null;
			element=getWebElement(loc);
			System.out.println("Selecting by value "+valueTobeSelected);
			Select webCheckBox= new Select(element);
			if(valueTobeSelected.equalsIgnoreCase("EMPTY")){
				webCheckBox.selectByValue("");
			}else{
				webCheckBox.selectByValue(valueTobeSelected);
			}
			
			Reporter.log( "Select value from " +strElementName+" "+valueTobeSelected+" Pass Selected "+valueTobeSelected+" Successfully");	
		}catch(Exception e){
			Reporter.log("<B><I><font size='4' color='Blue'>"+"Select value from " +strElementName+" "+valueTobeSelected+" Fail  Found Exception while selecting value "+e.getMessage()+"</font></I></B>");	
			
		}
			
	}
	
	
	public void verifySelectedValue(String strElementName, Locator loc,String expectedValue){
		
		try{
			WebElement element = null;
			element=getWebElement(loc);
			Select webSelectList= new Select(element);
			String item = webSelectList.getFirstSelectedOption().getText();
				if (item.trim().equals(expectedValue.trim()))
				{
				Reporter.log( "Verify Selected List value from " +strElementName+" "+ expectedValue+" Pass Selected value is"+expectedValue);
				}
				else
				{
				Reporter.log( "Verify Selected List value from " +strElementName+" "+expectedValue+" Fail Selected value "+item+" does not match "+expectedValue);
				}
		}catch(Exception e){
		Reporter.log("<B><I><font size='4' color='Blue'>"+"Verify Selected List value from  " +strElementName+" "+expectedValue+"Fail Found Exception while verifying selected element "+e.getMessage()+"</font></I></B>");
		 
		}
	}
	
	public String getSelectedValue(String strElementName, Locator loc){
	String item =null;
	
		try{
			WebElement element = null;
			element=getWebElement(loc);
			Select webSelectList= new Select(element);
			item = webSelectList.getFirstSelectedOption().getText();
			Reporter.log( "Get Selected List value from " +strElementName+" "+strElementName+" Pass Selected value is "+item);
		}catch(Exception e){
		Reporter.log("<B><I><font size='4' color='Blue'>"+"Get Selected List value from " +strElementName+" "+strElementName+" Done Found Exception while verifying selected element "+e.getMessage()+"</font></I></B>");
		}
	
	return item;
	}

			
	public void selectValueFromTable(String strElementName, Locator loc,String valueTobeSelected){
		if(valueTobeSelected == null || valueTobeSelected == ""||valueTobeSelected.equalsIgnoreCase("NA")){
			return;
		}
		try{

			

			WebElement element = null;
			element=getWebElement(loc);
			List<WebElement> options1 = element.findElements(By.tagName("option"));
			boolean isPresent = false;
			for(WebElement option : options1){
				if(option.getText().equals(valueTobeSelected)){
					element.click();
					option.click();
					isPresent = true;
					break;
				}
			}
			if(isPresent){
				Reporter.log( "Select value from " +strElementName+ " Listbox "+ valueTobeSelected+" Pass "+valueTobeSelected+" option is selected from the dropdown");
			}else{
				Reporter.log( "Select value from " +strElementName+ " Listbox "+ valueTobeSelected+ " Fail "+valueTobeSelected+" option is not selected from the dropdown");
			}
				
		}catch(Exception e){
			Reporter.log("<B><I><font size='4' color='Blue'>"+"Select value from " +strElementName+" "+valueTobeSelected+" Fail Found Exception while selecting value "+e.getMessage()+"</font></I></B>");	
		
		}
			
	}
	
	
	
	
	
	
	
	
	public List<WebElement> getWebList(Locator loc ){
		
		List<WebElement> lst = null;
		String[] objProperties= null;
	//	objProperties = identifier.split("_", 2);
		
				try{
					By by=getBy(loc);
					lst=driver.findElements(by);
					} catch(Exception elenotFound){
						Reporter.log("<B><I><font size='4' color='Blue'>"+"Get Web List "+objProperties[1]+" Fail Found Exception while accessing element "+elenotFound.getMessage()+"</font></I></B>");
					}
				
				return lst;
			}
	
public void verifyElementPresent(Locator loc){
		
		boolean exists = false;
		//CreateResult result = new CreateResult();
		
		for(int interval = 0; interval < 30;interval++)
		{
			if(getWebElement(loc).isDisplayed())
			    {
				 exists=true;
				 break;
			    }
		}
			
		
		
	}


public void verifyCurrentUrl(String url) {
//	 Assert.assertEquals(url,driver.getTitle());
		
}


public void mouseHover(Locator loc) {
	WebElement hoverElement=getWebElement(loc);
	Actions builder = new Actions(driver);
	builder.moveToElement(hoverElement).perform();
	
	
}



	
	
	
	
 




}
