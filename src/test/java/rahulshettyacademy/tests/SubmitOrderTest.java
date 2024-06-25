package rahulshettyacademy.tests;

import java.io.File;
import java.io.IOException;
import java.time.Duration;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import io.github.bonigarcia.wdm.WebDriverManager;
import rahulshettyacademy.TestComponents.BaseTest;
import rahulshettyacademy.pageobjects.CartPage;
import rahulshettyacademy.pageobjects.CheckOutPage;
import rahulshettyacademy.pageobjects.ConfirmationPage;
import rahulshettyacademy.pageobjects.LandingPage;
import rahulshettyacademy.pageobjects.OrderPage;
import rahulshettyacademy.pageobjects.ProductCatalogue;

public class SubmitOrderTest extends BaseTest{

	String productName = "ZARA COAT 3";
	@Test(dataProvider = "getData", groups= {"Purchase"})
	public void  submitOrder(HashMap<String,String> input) throws IOException, InterruptedException
	{
		
		ProductCatalogue productCatalogue = landingPage.loginApplication(input.get("email"), input.get("password"));
		List<WebElement> products = productCatalogue.getProductList();
		productCatalogue.addProductToCart(input.get("product"));
		CartPage cartPage = productCatalogue.goToCartPage();
		Boolean match = cartPage.verifyProductDisplay(input.get("product"));
		Assert.assertTrue(match);
		CheckOutPage checkoutPage = cartPage.goToCheckout();
		checkoutPage.SelectCountry("india");
		ConfirmationPage confirmationPage = checkoutPage.submitOrder();
		String confirmMsg = confirmationPage.getConfirmationMsg();
		Assert.assertTrue(confirmMsg.equalsIgnoreCase("THANKYOU FOR THE ORDER."));

	}
	
	//To Verify ZARA COAT 3 is displaying in orders page
	
	@Test(dependsOnMethods = {"submitOrder"})
	public void OrderHistoryTest()
	{
		ProductCatalogue productCatalogue = landingPage.loginApplication("bhargavi123@gmail.com", "Bhargavi!1");
		OrderPage orderPage = productCatalogue.goToOrderPage();
		Assert.assertTrue(orderPage.verifyOrdertDisplay(productName));

	}
	

	
	@DataProvider
	public Object[][] getData() throws IOException
	{
		
		
		List<HashMap<String,String>> data = getJsonDataToMap(System.getProperty("user.dir")+"\\src\\test\\java\\rahulshettyacademy\\data\\PurchaseOrder.json");
		return new Object[][] { {data.get(0)}, {data.get(1)} };
	}
	

	//return new Object[][] {{"bhargavi123@gmail.com","Bhargavi!1","ZARA COAT 3"},{"anshika@gmail.com","Iamking@000","ADIDAS ORIGINAL"}}
	
	/*HashMap<String,String> map = new HashMap<String,String>();
	map.put("email", "bhargavi123@gmail.com");
	map.put("password", "Bhargavi!1");
	map.put("product", "ZARA COAT 3");
	
	HashMap<String,String> map1 = new HashMap<String,String>();
	map1.put("email", "anshika@gmail.com");
	map1.put("password", "Iamking@000");
	map1.put("product", "ADIDAS ORIGINAL");*/
//C:\Users\jbhar\eclipse-workspace\SeleniumFrameworkDesign\src\test\java\rahulshettyacademy\data\PurchaseOrder.json
}
