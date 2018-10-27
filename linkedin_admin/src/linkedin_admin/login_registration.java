package linkedin_admin;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.SQLException;

public class login_registration {

	
	//connects to database 
	public static Connection getDBConnection(){  

		Connection con = null;
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			con = DriverManager.getConnection("jdbc:mysql://localhost:3306/localDb","root","adminuser" ); //value changes if DB changed
		} catch (Exception ex) {
			System.out.println("Error connection to Database" + ex);
		}

		return con;
	}


	// checks if username exists, if it doesnt it will register the user.
	public static boolean registerUser(String fname, String lname,String email, String uname, String pass) {  
		PreparedStatement ps;
		ResultSet rs;
		boolean registered=false;  // checking if username exists in DB
		String Insertquery = "INSERT INTO `Users`(`firstName`, `lastName`, `email`, `userName`, `password`) VALUES (?,?,?,?,?)"; //query to insert method parameters
		String Userquery = "SELECT * FROM `Users` WHERE `userName` =?";  // query to check if username exists 

		try {

			ps = getDBConnection().prepareStatement(Userquery);
			ps.setString(1, uname);
			rs = ps.executeQuery();
			if(rs.next()) //goes through all usernames in DB to see if an exact one is found 
			{
				registered = false;  //username exists cannot register, user has to type another username  
				return registered;
			}
			else  //username doesn't exist and user will be registered into Db 
			{
				ps = getDBConnection().prepareStatement(Insertquery);
				pass = passwordHash(pass);  //converts user entered pass to hash to store in DB
				ps.setString(1, fname);
				ps.setString(2, lname);
				ps.setString(3, email);
				ps.setString(4, uname);
				ps.setString(5, pass);
				if(ps.executeUpdate() > 0) {  
					registered = true;	            }
			}
		} catch (SQLException | NoSuchAlgorithmException ex) {
			System.out.println("Error " + ex);
		}
		return registered;
	}


	//checks if username and password exists in database for login screen 
	public static boolean loginuser(String uname, String pass) {   
		PreparedStatement ps;
		ResultSet rs;
		boolean loggedin= false;
		String query = "SELECT * FROM `Users` WHERE `userName` =? AND `password` =?";
		try {
			ps = getDBConnection().prepareStatement(query);
			pass = passwordHash(pass); //converts user enter password to hash to compare whats in DB 
			ps.setString(1, uname);
			ps.setString(2, pass);
			rs = ps.executeQuery();
			if(rs.next())
			{
				loggedin= true;
				return loggedin;
			}
		}catch (SQLException | NoSuchAlgorithmException ex) {
			System.out.println("Error " + ex);
		}
		return loggedin;
	}

	
	//hashes password for encryption 
	public static String passwordHash(String pass) throws NoSuchAlgorithmException { 

		MessageDigest md = MessageDigest.getInstance("SHA-256"); //using sha-256 encryption 
		md.update(pass.getBytes());
		byte[] b = md.digest();
		StringBuffer hashed = new StringBuffer();
		for(byte b1 : b){
			hashed.append(Integer.toHexString(b1 & 0xff).toString());
		}
		return hashed.toString();
	}



}







