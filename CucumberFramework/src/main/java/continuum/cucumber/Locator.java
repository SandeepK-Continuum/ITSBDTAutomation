package continuum.cucumber;

import org.openqa.selenium.By;



public class Locator {
	  
		 String key;
		String value;
		String byType;

		public Locator(String name, String locator,String byType) {
			this.key=name;
			this.value=locator;
			this.byType=byType;
		}
		public Locator(String name, String locator) {
			this.key=name;
			this.value=locator;
			this.byType="xpath";
		}
		 


		public String getKey() {
			return key;
		}

		public String getValue() {
			
			return value;
		}

		public String getByType() {
			return byType.toLowerCase();
		}
		
		public Locator runtimeLocator(int j)
		{
			String locatorValue=value.replace("?",String.valueOf(j));
		    Locator loc=new Locator(this.key,locatorValue,this.byType);	
			return loc;
		}
				
		
}
