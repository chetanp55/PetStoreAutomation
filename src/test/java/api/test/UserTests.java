package api.test;

import org.testng.annotations.Test;
import org.testng.annotations.BeforeMethod;
import org.testng.AssertJUnit;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.github.javafaker.Faker;

import api.endpoints.UserEndPoints;
import api.payload.User;
import io.restassured.response.Response;

public class UserTests {

	Faker faker;
	User userPayload;

	@BeforeClass
	public void setUp() {
		faker = new Faker();
		userPayload = new User();

		userPayload.setId(faker.idNumber().hashCode());
		userPayload.setUsername(faker.name().username());
		userPayload.setFirstName(faker.name().firstName());
		userPayload.setLastName(faker.name().lastName());
		userPayload.setEmail(faker.internet().emailAddress());
		userPayload.setPassword(faker.internet().password());
		userPayload.setPhone(faker.phoneNumber().cellPhone());
	}


	@Test(priority = 1)
	public void testPostUser() {
		Response response = UserEndPoints.createUser(userPayload);
		response.then().log().all();

		AssertJUnit.assertEquals(response.getStatusCode(), 200);
	}

	@Test(priority = 2)
	public void testGetUserByUserName() {
		Response response = UserEndPoints.readUser(this.userPayload.getUsername());
		response.then().log().all();

		AssertJUnit.assertEquals(200,response.getStatusCode());
	}

	@Test(priority = 3)
	public void testUpdateUser() {
		//update data using payload
		userPayload.setFirstName(faker.name().firstName());
		userPayload.setLastName(faker.name().lastName());
		userPayload.setEmail(faker.internet().safeEmailAddress());

		Response response = UserEndPoints.updateUser(userPayload, this.userPayload.getUsername());
		response.then().log().all();
		response.then().log().body().statusCode(200);	// Assertion way 1
		AssertJUnit.assertEquals(response.getStatusCode(), 200);		// Assertion way 2

		//Checking data after update		
		Response responseAfterUpdate = UserEndPoints.readUser(this.userPayload.getUsername());
		AssertJUnit.assertEquals(response.getStatusCode(), 200);
	}

	
	@Test(priority = 4)
	public void testDeleteUserByUserName() {
		Response response = UserEndPoints.deleteUser(this.userPayload.getUsername());
		response.then().log().all();

		AssertJUnit.assertEquals(response.getStatusCode(), 200);
	}

}
