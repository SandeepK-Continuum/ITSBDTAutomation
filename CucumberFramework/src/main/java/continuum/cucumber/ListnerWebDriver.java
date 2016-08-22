package continuum.cucumber;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.io.FileUtils;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.IInvokedMethod;
import org.testng.IInvokedMethodListener;
import org.testng.ITestResult;
import org.testng.Reporter;

import continuum.cucumber.testRunner.TestRunner;

	public class ListnerWebDriver implements IInvokedMethodListener {
		public static String testClassName;
		public static String testMethodName;
		public static String resultParameter[],testResultStatus,timeStamp,imagePath;
		public static String errorMessage,screenShotPath;
		
		
		  public static String filePath = new File("").getAbsolutePath()+"\\Screenshots";
			
		    static RemoteWebDriver driver=null;
		    
	    @Override
	    public void beforeInvocation(IInvokedMethod method, ITestResult testResult) {
	    	SeleniumServerUtility.startServer();
	    	if (method.isTestMethod()) {
	        	 driver=WebDriverInitialization.createInstance(driver);
	     	 
	     	        driver.get(Utilities.getMavenProperties("applicationUrl"));
	     	       driver.manage().deleteAllCookies();
	     	     	driver.manage().window().maximize();
	     	      
	     	    DriverFactory.setWebDriver(driver);
	        }
	    }
	 
	    @Override
	    public void afterInvocation(IInvokedMethod method, ITestResult result) {
	    //	String scenarioName=Hooks.getScenario().getName();
	    	String testMethodName = result.getInstanceName().toString().trim();
			String executionTime=String.valueOf(result.getStartMillis()-result.getEndMillis());
		
			String screenShotName = testMethodName + ".jpg";
	    	if(result.isSuccess())
	    		{Reporter.log("*****  " + result.getName() + " test has Passed *****");}
	    	else 
	    	{
	    			
	    		
	    			takeScreenShot(screenShotName, testMethodName);
	    	}
	    	        if (method.isTestMethod()) {
	    	        //	String testMethodName = result.getName().toString().trim();
	    	        	int resultStatus = result.getStatus();
	    	        	if(resultStatus==1){
	    	        		testResultStatus = "Passed";
	    	        	}
	    	        	else
	    	        	{
	    	        		testResultStatus = "Failed";
	    	        	Throwable testError = result.getThrowable();
	    		    		errorMessage = testError.getMessage();
	    		    		
	    		    		int errMessageLength = errorMessage.length();
	    		    		if(errMessageLength>255){
	    		    			errorMessage = errorMessage.substring(0, 100);
	    		    		}
	    		    		else
	    		    		{
	    		    			errorMessage = testError.getMessage();
	    		    		}
	    		    		screenShotPath = imagePath;
	    	        	}
	    	        	
	    	        	Object[] resultParameter = result.getParameters();
	    	        	String String_Array[]=new String[resultParameter.length];
	    	        	
	    	        	for (int i=0;i<String_Array.length;i++) 
	    	        		String_Array[i]=resultParameter[i].toString();
	    	        	
	    	        	WebDriver driver = DriverFactory.getDriver();
		            	
		            	
		            	String dbFlag =Utilities.getMavenProperties("dbFlag");
		            	
		            	if(dbFlag.equalsIgnoreCase("true"))
		            	{
			            	DatabaseUtility updateResultToDB = new DatabaseUtility();
			        		try {
								updateResultToDB.resultUpdateToDataBase(testMethodName,testResultStatus,String_Array,executionTime,timeStamp,errorMessage,screenShotPath);
								
							} catch (Throwable e) {
								
								e.printStackTrace();
							}
		            	}
		            	driver.close();
		                driver.quit();
		     
		              }
	    	     //   ReportMail.sendMail();
	    			
}
	    
	    		

				public static void takeScreenShot(String screenShotName, String testName) {
//	    			String jenkins = Utilities.getConfigValues("jenkins");
//	    			if(jenkins.equalsIgnoreCase("true"))
//	    			{
//	    				String filepath="\\\\145.224.219.188\\Jenkins\\jobs\\"+testClassName+"\\workspace\\Screenshots\\";
		    			try {
		    				File file = new File(filePath);
		    				if (!file.exists()) {
		    					System.out.println("File created " + file);
		    					file.mkdir();
		    				}

		    				File screenshotFile = ((TakesScreenshot) DriverFactory.getDriver()).getScreenshotAs(OutputType.FILE);
		    				File targetFile = new File(filePath, screenShotName);
		    				FileUtils.copyFile(screenshotFile, targetFile);

		    				//return screenShotName;
		    				imagePath = "file:///"+ filePath+"\\"+ screenShotName;
		    				
		    				Reporter.log("Screenshot can be found : " + imagePath);
		    				
		    				Reporter.log("<br> <a target =\"_blank\"href=\""+imagePath+"\">"+
		    				"<img src=\""+imagePath+"\"alt=\"screenshot Not available\"height=\"400\"width=\"400\"></a>");
		    				
		    				
		    			} catch (Exception e) {
		    				System.out.println("An exception occured while taking screenshot " + e);
		    				
		    			}
	    				
	    			
	    		}
	    		public static String getTestName(String testName) {
	    			String[] reqTestClassname = testName.split("\\.");
	    			int i = reqTestClassname.length - 1;
	    			System.out.println("Required Test Name : " + reqTestClassname[i]);
	    			return reqTestClassname[i];
	    		}
	    		
	}		