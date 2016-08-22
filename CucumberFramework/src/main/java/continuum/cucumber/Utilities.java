package continuum.cucumber;




import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;

import org.apache.poi.ss.formula.functions.Column;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.junit.runners.model.TestClass;
import org.testng.Reporter;



public class Utilities {
	
	private static HashMap<String,String> validations=new HashMap<String,String>();
	
	public static String  getMavenProperties(String key){
	
	 Properties mavenProps = new Properties();
	 InputStream inStream = TestClass.class.getResourceAsStream("/maven.properties");
	 try {
		mavenProps.load(inStream);
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}

	return mavenProps.getProperty(key);
	}
	
	
	
	public static HashMap<String,String> readValidationsFromExcelFile(String excelFileName,String TestCaseID)  {
		 String excelFilePath = new File("").getAbsolutePath()+"\\src\\test\\resources\\Data\\"+excelFileName;
		 System.out.println("Reading validation points from excel file "+excelFileName);
		try{
	    FileInputStream inputStream = new FileInputStream(new File(excelFilePath));
	    XSSFRow row;
	    XSSFWorkbook workbook = new XSSFWorkbook(inputStream);
	//    System.out.println("workbook created "+workbook);
	    XSSFSheet spreadsheet = workbook.getSheet(TestCaseID);
	      Iterator < Row > rowIterator = spreadsheet.iterator();
	      while (rowIterator.hasNext()) 
	      {
	         row = (XSSFRow) rowIterator.next();
	         Iterator < Cell > cellIterator = row.cellIterator();
	         while ( cellIterator.hasNext()) 
	         {
	            Cell cellKey = row.getCell(0);
	            Cell cellValue=row.getCell(1);
	            //System.out.println("Key :"+getCellValue(cellKey).toString()+"    Value:"+getCellValue(cellValue).toString());
	            validations.put(getCellValue(cellKey).toString(), getCellValue(cellValue).toString());
	            cellKey = cellIterator.next();
	            cellValue = cellIterator.next();
	         } 
	        

	      }
	    
	   
	    
	    workbook.close();
	    inputStream.close();
		 }catch (Exception e)
	    {
	    	System.out.println("Not able to read data from Excelsheet at  "+excelFilePath);
	    	Reporter.log("Not able to read data from Excelsheet at  "+excelFilePath);
	    	e.printStackTrace();
	    }
	    return validations;
	}

	private static Object getCellValue(Cell cell) {
	    switch (cell.getCellType()) {
	    case Cell.CELL_TYPE_STRING:
	        return cell.getStringCellValue();
	 
	    case Cell.CELL_TYPE_BOOLEAN:
	        return cell.getBooleanCellValue();
	 
	    case Cell.CELL_TYPE_NUMERIC:
	        return cell.getNumericCellValue();
	    }
	 
	    return null;
	}
	
	
	public static String getKeyValue( String excelFileName,String TestCaseID,String key){
		if(validations.isEmpty())
		{ validations=readValidationsFromExcelFile(excelFileName,TestCaseID);}
		
		String value= validations.get(key);
		 System.out.println("Key :"+key+"    Value:"+value);
		return value;
		
	}
}

