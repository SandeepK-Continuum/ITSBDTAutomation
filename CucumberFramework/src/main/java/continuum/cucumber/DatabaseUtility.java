package continuum.cucumber;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.testng.Reporter;



public class DatabaseUtility {

	
	public static final String DRIVER = "com.microsoft.sqlserver.jdbc.SQLServerDriver";

			
	public void resultUpdateToDataBase(String testMethodName,String testResultStatus, 
			String[] string_Array,String executionTime,String timeStamp,
				String errorMessage,String screenShotPath) throws Throwable {
		
		Connection dataBaseConnection = null;
		Statement stmt = null;
		String databaseName=Utilities.getMavenProperties("DBName");
		String sqlServerURL=Utilities.getMavenProperties("DBServerUrl");
		String username=Utilities.getMavenProperties("DBUsername");
		String password=Utilities.getMavenProperties("DBPwd");
		try{
			dataBaseConnection = createConnection(databaseName,sqlServerURL, username, password);
			System.out.println("Database is Connected");
			stmt = dataBaseConnection.createStatement();
			
			String insertQuery = "INSERT INTO TestData (Scenario, UserSet, Branch, Product, Currencies, "
					+ "Denoms,Quantity,Execution_Flag,Sanity_Flag,Status,"
					+ "ScenarioExecutionTime,TimeStamp,ErrorMessage,ScreenShotPath) VALUES('"+string_Array[0]+"','"+string_Array[1]+"','"+string_Array[2]+"','"+string_Array[3]
							+"','"+string_Array[4]+"','"+string_Array[5]+"','"+string_Array[6]+"','"+string_Array[7]
									+"','"+string_Array[8]+"','','','"+timeStamp+"','"+errorMessage+"','"+screenShotPath+"')";
			
			int insertQueryCount = stmt.executeUpdate(insertQuery);
			
			String updateQuery = "update TestData set Status='"+testResultStatus+"',ScenarioExecutionTime ='"+executionTime+" Minutes' where Scenario='"+string_Array[0]+"' "
        			+ "and UserSet ='"+string_Array[1]+"' and Branch ='"+string_Array[2]+"' and Product ='"+string_Array[3]+"' and Currencies ='"+
        			string_Array[4]+"' and Execution_Flag='"+
        			string_Array[7]+"' and Sanity_Flag='"+string_Array[8]+"' and TimeStamp='"+timeStamp+"'";
			
			int updateQueryCount = stmt.executeUpdate(updateQuery);
	        System.out.println("Updated queries Successfully: ");
			
		}
		catch(Exception e){
			System.out.println("Failed to connect to database"+ e.getMessage());
		}
	finally {
		try{
			
			if(stmt != null) stmt.close();
			if(dataBaseConnection != null) dataBaseConnection.close();
		}
		catch(Exception e){
			
		}
		
	}
	}

	
	public static Connection createConnection(String databaseName, String sqlServerURL, String username, String password) {
		
		Connection conn = null;
		try {
			Class.forName(DRIVER);
			if (!sqlServerURL.startsWith("jdbc:")) {
				sqlServerURL = "jdbc:sqlserver://" + sqlServerURL;
			}
			String connectionUrl = sqlServerURL + ";databaseName=" + databaseName +";user=" + username +";password="+ password;
			Reporter.log("SQL server connection: " + connectionUrl);
			System.out.println("SQL server connection: " + connectionUrl);
			conn = DriverManager.getConnection(connectionUrl);
		}
		catch (SQLException e) {	
			e.printStackTrace();
		} 
		catch (ClassNotFoundException e) {	
			e.printStackTrace();
		} 
		return conn;
	}
		
	
	public static void closeConnection(Connection conn) {
		if (conn != null) {
			try {
				conn.close();
			} catch (SQLException e) {	
				e.printStackTrace();
			}
		}
	}
	
	
	public static ResultSet runQuery(Connection conn, String query) {
		ResultSet resultSet = null;
		if (conn != null) {
			try {
				Reporter.log("SQL query to execute: " + query);
				Statement st = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
				resultSet = st.executeQuery(query);
			}
			catch (SQLException e) {	
				e.printStackTrace();
			} 
		}
		return resultSet;
	}
	
	
	public static String executeScalar(String databaseName,
			String sqlServerURL, String username, String password, String query) {

		String result = null;

		Connection conn = createConnection(databaseName, sqlServerURL,
				username, password);
		ResultSet resultSet = runQuery(conn, query);

		try {
			resultSet.beforeFirst();
			while (resultSet.next()) {
				result = resultSet.getString(1);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			closeConnection(conn);
		}

		return result;
	}

	
	public static String executQuery(Connection conn, String query) {
		String result = null;
		ResultSet resultSet = null;
		if (conn != null) {
			try {
				resultSet = runQuery(conn, query);
				if (resultSet != null) {
					resultSet.beforeFirst();
					while (resultSet.next()) {
						result = resultSet.getString(1);
					}
				}
			} catch (SQLException e) {	
				e.printStackTrace();
			} finally {
				if (resultSet != null) {
					try {
						resultSet.close();
					} catch(SQLException ex) {}
				}
			}
		}
		return result;
	}

	
	public static List<String> getListByQuery(String databaseName,	String sqlServerURL,
			String username, String password, String query, String column) {

		List<String> result = new ArrayList<>();

		Connection conn = null;
		ResultSet resultSet = null;

		try {
			conn = createConnection(databaseName, sqlServerURL,	username, password);
			resultSet = runQuery(conn, query);
			resultSet.beforeFirst();
			while (resultSet.next()) {
				result.add(resultSet.getString(column));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			closeConnection(conn);
		}

		return result;
	}


	public static void runUpdateQuery(Connection conn, String query) {

		Statement stmt = null;
		try {
			stmt = conn.createStatement();
			stmt.executeUpdate(query );
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			try {
				stmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}


	

}
