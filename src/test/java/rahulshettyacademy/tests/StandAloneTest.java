package rahulshettyacademy.tests;

import java.time.Duration;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import io.github.bonigarcia.wdm.WebDriverManager;
import rahulshettyacademy.pageobjects.LandingPage;

public class StandAloneTest {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String productName = "ZARA COAT 3";
		WebDriverManager.chromedriver().setup();
		WebDriver driver = new ChromeDriver();
		
		//implicit wait
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
		
		driver.manage().window().maximize();
		
		//opening the web-site
		driver.get("https://rahulshettyacademy.com/client/");
		
		
		LandingPage landingPage = new LandingPage(driver);
		
		//entering details in login page
		driver.findElement(By.id("userEmail")).sendKeys("bhargavi123@gmail.com");
		driver.findElement(By.id("userPassword")).sendKeys("Bhargavi!1");
		driver.findElement(By.id("login")).click();
		
		//explicit wait
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
		
		//waiting for the elements on the webpage to load
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".mb-3")));
		
		//collecting products on the page into a list
		List<WebElement> products = driver.findElements(By.cssSelector(".mb-3"));
		
		//filtering the element we want to add to cart using java streams
		WebElement prod = products.stream().filter(product -> 
		product.findElement(By.cssSelector("b")).getText().equals("ZARA COAT 3")).findFirst().orElse(null);
		
		//clicking on add to cart button on the item we selected
		prod.findElement(By.cssSelector(".card-body button:last-of-type")).click();
		
		
		//using explicit wait object "wait"
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("#toast-container")));
		//ng-animating
		wait.until(ExpectedConditions.invisibilityOf(driver.findElement(By.cssSelector(".ng-animating"))));
		
		//clicking on add to cart button
		driver.findElement(By.cssSelector("[routerlink *= 'cart']")).click();
		//WebElement cart = driver.findElement(By.cssSelector("[routerlink*='cart']"));

		//JavascriptExecutor js = (JavascriptExecutor) driver;

		//js.executeScript("arguments[0].click();", cart);
		
		
		//Verify if the items we added to the cart are present in the cart
		List<WebElement> cartProducts = driver.findElements(By.cssSelector(".cartSection h3"));
		Boolean match = cartProducts.stream().anyMatch(cartProduct -> cartProduct.getText().equalsIgnoreCase(productName));
		Assert.assertTrue(match);
		
		//clicking checkout button
		driver.findElement(By.cssSelector(".totalRow button")).click();
		
		//entering country in checkout page
		Actions a = new Actions(driver);
		a.sendKeys(driver.findElement(By.cssSelector("[placeholder = 'Select Country']")), "india").build().perform();
		wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.cssSelector(".ta-results")));
		
		//clicking on india 
		driver.findElement(By.xpath("(//button[contains(@class,'ta-item')])[2]")).click();
		
		//clicking on place order button
		driver.findElement(By.cssSelector(".action__submit")).click();
		
		//checking for thank you text after order is place
		String confirmMsg = driver.findElement(By.cssSelector(".hero-primary")).getText();
		Assert.assertTrue(confirmMsg.equalsIgnoreCase("THANKYOU FOR THE ORDER."));
		
		driver.close();
		
		

	}

}
