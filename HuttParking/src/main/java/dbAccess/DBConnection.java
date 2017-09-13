package dbAccess;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class DBConnection{
	private Connection myConn;
	TimeRemaining time;
	
	public DBConnection() throws Exception {

		String dbUrl = "jdbc:mysql://localhost:3306/user_login";
		String user = "root";
		String password = "";

		Class.forName("com.mysql.jdbc.Dricer");
		myConn = DriverManager.getConnection(dbUrl, user, password);
		System.out.println("initialising database. . .");
		Statement myStmt = myConn.createStatement();
		time = new TimeRemaining();
	}

	public boolean searchUser (String plate, String password) throws SQLException {
		PreparedStatement myStmt = null;
		ResultSet myRS = null;

		try {
			myStmt = myConn.prepareStatement("SELECT * FROM account WHERE liscensePlate =? and password =?");
			myStmt.setString(1, plate);
			myStmt.setString(2, password);
			myRS = myStmt.executeQuery();

			if(myRS.next()) {
				return true;
			}
			else {
				return false;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			myConn.close();
		}
		return false;
	}

	public boolean checkTicketed(String plate) throws SQLException {
		PreparedStatement myStmt = null;
		ResultSet myRS = null;

		try {
			myStmt = myConn.prepareStatement("SELECT ticketed FROM account WHERE liscensePlate =?");
			myStmt.setString(1, plate);
			myRS = myStmt.executeQuery();

			if(myRS.next()) {
				if(myRS.getInt(0) == 1)
				{
					return true;
				}
				else
				{
					return false;
				}	
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			myConn.close();
		}
		return false;
	}

	public String getTime(int carpark) throws ParseException, SQLException {
		PreparedStatement myStmt = null;
		ResultSet myRS = null;

		try {
			myStmt = myConn.prepareStatement("SELECT endTime FROM carpark WHERE idcarpark =?");
			myStmt.setInt(1, carpark);
			myRS = myStmt.executeQuery();

			if(myRS.next()) {
				String endTime = myRS.getString(0);
				int totalMin = time.calcDiff(endTime);
				String result = time.timeDiff(totalMin);
				return result;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			myConn.close();
		}		
		return null;
	}
	
	/*public boolean addTime(String parkNum, String min) {
		PreparedStatement myStmt = null;
		ResultSet myRS = null;
		String start;
		String end;

		try {
			myStmt = myConn.prepareStatement("SELECT * FROM account WHERE liscensePlate =? and password =?");
			myStmt.setString(1, start);
			myStmt.setString(2, end);
			myRS = myStmt.executeQuery();

			if(myRS.next()) {
				return true;
			}
			else {
				return false;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			myConn.close();
		}
		return false;
	}*/
}
