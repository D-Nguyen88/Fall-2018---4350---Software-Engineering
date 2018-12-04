package linkedin_adminRMIServer;


import java.io.File;
import java.io.IOException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;



@SuppressWarnings("serial")
public class admin_server extends UnicastRemoteObject implements adminServerinterface {
	 Logger logger = LoggerFactory.getLogger("logger");
	 

	public admin_server() throws RemoteException{
		
	}
	
	String key = "";
	static String XMLfilePath="";
	
	//connects to database 
		public Connection getDBConnection(){  

			Connection con = null;
			try {
				Class.forName("com.mysql.cj.jdbc.Driver");
				con = DriverManager.getConnection("jdbc:mysql://linkedin.cctcghvmt1au.us-east-2.rds.amazonaws.com/LinkedInDB","csc4350root","LinkedIn2daDB!" ); //value changes if DB changed
				logger.info("Database Connected");
			} catch (Exception ex) {
				logger.error("Database Connection Failed", ex);
			}

			return con;
		}


		// checks if username exists, if it doesnt it will register the user.
		public boolean registerUser(String fname, String lname,String email, String uname, String pass) {  
			logger.info("Registration method invoked");
			PreparedStatement ps;
			ResultSet rs;
			boolean registered=false;  // checking if username exists in DB
			String Insertquery = "INSERT INTO `UserInfo`(`firstName`, `lastName`, `email`, `username`, `password`) VALUES (?,?,?,?,?)"; //query to insert method parameters
			String Userquery = "SELECT * FROM `UserInfo` WHERE `username` =?";  // query to check if username exists 

			try {
				logger.info("Connecting to database to register user");
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
				logger.error("Error registering user", ex);
			}
			return registered;
		}


		//checks if username and password exists in database for login screen 
		public  boolean loginuser(String uname, String pass) {   
			logger.info("Login Method invoked");
			PreparedStatement ps;
			ResultSet rs;
			boolean loggedin= false;
			String query = "SELECT * FROM `UserInfo` WHERE `username` =? AND `password` =?";
			try {
				ps = getDBConnection().prepareStatement(query);
				pass = passwordHash(pass); //converts user enter password to hash to compare whats in DB 
				ps.setString(1, uname);
				ps.setString(2, pass);
				rs = ps.executeQuery();
				if(rs.next())
				{
					int id= rs.getInt("userId");
					String ids = Integer.toString(id);
					setKey(ids);
					loggedin= true;
					return loggedin;
				}
			}catch (SQLException | NoSuchAlgorithmException ex) {
				logger.error("Error with login method", ex);
			}
			return loggedin;
		}

		public void setKey(String key) {
			this.key= key;
		}
		public String getKey() {
			return key;
		}
		

		
		//hashes password for encryption 
		public  String passwordHash(String pass) throws NoSuchAlgorithmException { 

			MessageDigest md = MessageDigest.getInstance("SHA-256"); //using sha-256 encryption 
			md.update(pass.getBytes());
			byte[] b = md.digest();
			StringBuffer hashed = new StringBuffer();
			for(byte b1 : b){
				hashed.append(Integer.toHexString(b1 & 0xff).toString());
			}
			logger.info("Password Hashed Successfully");
			return hashed.toString();
		}
		
		
		// polling (Takes the queryID, xsl file path , and directory of CSV file (where it should go)
		public boolean pollComplete(String queryId, String CSV) throws TransformerException, ParserConfigurationException, SAXException, IOException {
			logger.info("Polling method invoked");
			int resultStatus=0;
			boolean pollingComplete= false;
			PreparedStatement ps;
			ResultSet rs;
			try {
				logger.info("Connecting to database for polling");
				String query1 = "SELECT * FROM QueryResults WHERE queryId =?";
				ps = getDBConnection().prepareStatement(query1);
				ps.setString(1, queryId);
			while (resultStatus!=1) {
				
				rs=ps.executeQuery();
				rs.first();
				resultStatus= rs.getInt("isReady");
				logger.info("Polling", resultStatus);
				Thread.sleep(5000);  // waits 5 seconds before polling again 
			}
			logger.info("XML file ready from backend, read for CSV conversion");
			// gets the path of the xml file generated by backend 
			rs=ps.executeQuery();
			rs.first();
			String path = rs.getString("resultsFilepath");  
			setXMLFilepath(path);
			logger.info("XML file path is ", returnXMLFilepath() );
			// changes isReady status back to 0 for future query runs 
			String changeIsready = "UPDATE QueryResults SET isReady = ? WHERE queryId = ?"; 
			ps = getDBConnection().prepareStatement(changeIsready);
			ps.setString(2, queryId);
			ps.setString(1, "0");
			ps.executeUpdate();
			logger.info("Setting query status back to 0 for future reruns");
			} catch (SQLException | InterruptedException ex) {
				logger.error("Error with getting XML file path ", ex );
			}
			//  xml conversion method 
			
			logger.info("Converting xml to CSV");
			try {
			File stylesheet = new File("src/res/xml2csv.xsl"); //transformation sheet
		    File xmlSource = new File(returnXMLFilepath()); //input file
		    DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		    DocumentBuilder builder = factory.newDocumentBuilder();
		    Document document = builder.parse(xmlSource);
		    StreamSource stylesource = new StreamSource(stylesheet);
		    Transformer transformer = TransformerFactory.newInstance()
		            .newTransformer(stylesource);
		    Source source = new DOMSource(document);
		    Result outputTarget = new StreamResult(new File(CSV+"/"+queryId+".csv")); //output file
		    transformer.transform(source, outputTarget);
			}catch (Exception e) {
				logger.info("XML to CSV failed" , e);
			}
			logger.info("Polling Complete");
			pollingComplete = true;
			return pollingComplete;
			
		}
		
		public  void setXMLFilepath(String path) {
			XMLfilePath= path;
		}
		
		public  String returnXMLFilepath() {
			return XMLfilePath;
		}
		
}
