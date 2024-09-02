package api.test;

import org.testng.annotations.Test;
import org.testng.AssertJUnit;
import org.testng.Assert;
import org.testng.annotations.Test;

import api.endpoints.UserEndPoints;
import api.payload.User;
import api.utilities.DataProviders;
import io.restassured.response.Response;

public class DataDrivenTests {

	User userPayload;


	@Test(priority = 1, dataProvider = "Data", dataProviderClass = DataProviders.class)
	public void testPostUser(String userID, String username,String firstName, String lastName, String email, String password, String phone) {

		User userPayload = new User();
		userPayload.setId(Integer.parseInt(userID));
		userPayload.setUsername(username);
		userPayload.setFirstName(firstName);
		userPayload.setLastName(lastName);
		userPayload.setEmail(email);
		userPayload.setPassword(password);
		userPayload.setPhone(phone);
		
		Response response = UserEndPoints.createUser(userPayload);
		AssertJUnit.assertEquals(response.getStatusCode(), 200);
	}


	@Test(priority = 2, dataProvider = "Usernames", dataProviderClass = DataProviders.class)
	public void testDeleteUserByUsername(String username) {
		Response response = UserEndPoints.deleteUser(username);
		AssertJUnit.assertEquals(response.getStatusCode(), 200);
		
	}
	
	
}

