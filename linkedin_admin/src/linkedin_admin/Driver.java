package linkedin_admin;

public class Driver extends login_registration {

	public static void main(String[] args) {
		
			if (registerUser("jul", "dsi", "fasdas@yahoo", "juju", "gonnabehashed")) { // firstname, lastname, email, username, password 
				
					System.out.println("Registration Successful");  //success message, should lead to login in page.
				}
				
			else {	
				System.out.println("Username has been taken, please pick another username"); // error message , user should enter another username 
			}
			
		
	/*	if (loginuser("juju", "gonnabehashed")) {  // username, password 
				
				System.out.println("User Log in Successfull");  //success message, should lead user to page to generate linkedin query requests. 
			}
			else   // values dont exist in DB 
			{
				System.out.println("Incorrect username or password");  //error message
			} */
	
	} 

}
