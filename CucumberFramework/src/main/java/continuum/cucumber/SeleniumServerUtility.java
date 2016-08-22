package continuum.cucumber;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.simple.JSONObject;

public class SeleniumServerUtility {
	public static String absolutePath=new File("").getAbsolutePath();
	public static String IEDriverLocation=absolutePath+"/drivers/IEDriverServer.exe";
	public static String ChromeDriverLocation=absolutePath+"/drivers/chromedriver.exe";
	public static String serverLocation=absolutePath+"/seleniumserver/selenium-server-standalone-2.53.1.jar";

	
	public static void startServer(){
		String browser=Utilities.getMavenProperties("browser").toLowerCase();
	
		try 
		{
			
	    if(!getSeleniumServerStatus())	
    	{
	       System.out.println("Staring selenium server");

	       Runtime.getRuntime().exec("cmd /c java -jar "+serverLocation+" -Dwebdriver.chrome.driver="+ChromeDriverLocation+" -Dwebdriver.ie.driver="+IEDriverLocation+"  ");
	       	
    	}  
		        Thread.sleep(1000);
		} catch (Exception e) {
			System.out.println("Not able to start selenium server");
			e.printStackTrace();	
		}
		
		
}
		
		private static boolean getSeleniumServerStatus(){
		  boolean status=false;
		  try {

				DefaultHttpClient httpClient = new DefaultHttpClient();
				HttpGet getRequest = new HttpGet("http://localhost:4444/wd/hub/status");
				//getRequest.addHeader("accept", "application/json");

				HttpResponse response = httpClient.execute(getRequest);

				if (response.getStatusLine().getStatusCode() == 200) {

					status=true;
				}
				httpClient.getConnectionManager().shutdown();

			  } catch (ClientProtocolException e) {

			  System.out.println("Selenium server is not started yet");

			  } catch (IOException e) {

				  System.out.println("Selenium server is not started yet");
			  }

			
			 System.out.println("String status:" + status);
			return status;
		}
		
		

		
		private void killSeleniumServer() {
          try{

			Runtime.getRuntime().exec("cmd /c http://localhost:4444/selenium-server/driver/?cmd=shutDownSeleniumServer");

			} catch (IOException e) {
				System.out.println("Not able to kill selenium server");
				e.printStackTrace();
			}
	
     }

}



