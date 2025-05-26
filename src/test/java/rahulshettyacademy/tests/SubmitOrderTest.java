package rahulshettyacademy.tests;

import java.io.IOException;
import java.time.Duration;
import java.util.HashMap;
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
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import rahulshettyacademy.TestComponents.BaseTest;
import rahulshettyacademy.pageobjects.CartPage;
import rahulshettyacademy.pageobjects.CheckoutPage;
import rahulshettyacademy.pageobjects.ConfirmationPage;
import rahulshettyacademy.pageobjects.LandingPage;
import rahulshettyacademy.pageobjects.OrderPage;
import rahulshettyacademy.pageobjects.ProductCatalogue;

public class SubmitOrderTest extends BaseTest{
	String productName = "ZARA COAT 3";
	
//	    @Test
//		public void submitOrder() throws InterruptedException {
	
//	@Test(dataProvider="getData",groups= {"Purchase"})
//	public void submitOrder(String email,String password,String productName ) throws IOException, InterruptedException{
	
	@Test(dataProvider="getData",groups= {"Purchase"})
	public void submitOrder(HashMap<String,String> input) throws IOException, InterruptedException{
		
		// WebDriverManager.chromedriver().setup();
		WebDriver driver = new ChromeDriver();
//		WebDriver driver = new FirefoxDriver();
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
		driver.manage().window().maximize();
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
		LandingPage landingPage = new LandingPage(driver);
		landingPage.goTo();
//		ProductCatalogue productCatalogue=landingPage.loginApplication("rockshashi533@gmail.com","ShashiRaj@123");
//		ProductCatalogue productCatalogue=landingPage.loginApplication(email,password);
		ProductCatalogue productCatalogue = landingPage.loginApplication(input.get("email"), input.get("password"));
		List<WebElement> products = productCatalogue.getProductList();
//		productCatalogue.addProductToCart(productName);
		productCatalogue.addProductToCart(input.get("product"));
		CartPage cartPage = productCatalogue.goToCartPage();
//		Boolean match = cartPage.VerifyProductDisplay(productName);
		Boolean match = cartPage.VerifyProductDisplay(input.get("product"));
		Assert.assertTrue(match);
		CheckoutPage checkoutPage = cartPage.goToCheckout();
		checkoutPage.selectCountry("india");
		JavascriptExecutor js = (JavascriptExecutor) driver;
		
		Thread.sleep(3000);
		js.executeScript("window.scrollBy(0, 500);");
 	    driver.findElement(By.xpath("//a[text()='Place Order ']")).click();
		
		ConfirmationPage confirmationPage = checkoutPage.submitOrder();
		String confirmMessage = confirmationPage.getConfirmationMessage();
		Assert.assertTrue(confirmMessage.equalsIgnoreCase("THANKYOU FOR THE ORDER."));
	}
//It Depends on SubmitOrderTest class Order should place then it will verify orderHistory
	    
//		@Test(dependsOnMethods= {"submitOrder"})
//		public void OrderHistoryTest()
//		{
//			//"ZARA COAT 3";
//			ProductCatalogue productCatalogue = landingPage.loginApplication("rockshashi533@gmail.com","ShashiRaj@123");
//			OrderPage ordersPage = productCatalogue.goToOrdersPage();
//			//It will verify Product (ZARA COAT 3)
//			Assert.assertTrue(ordersPage.VerifyOrderDisplay(productName));
//			
//	}
	
	// Read from Json PurchaseOrder.json username & Pass 
	    @DataProvider
		public Object[][] getData() throws IOException
		{

			
			List<HashMap<String,String>> data = getJsonDataToMap(System.getProperty("user.dir")+"//src//test//java//rahulshettyacademy//data//PurchaseOrder.json");
			return new Object[][]  {{data.get(0)}, {data.get(1) } };
			
		}
//		
		
	 //For this run Purchase.xml
		//Ti Will run Correct
//		 @DataProvider
//		  public Object[][] getData()
//		  {
//		    return new Object[][]  {{"rockshashi533@gmail.com","ShashiRaj@123","ZARA COAT 3"}, {"shetty@gmail.com","Iamking@000","ADIDAS ORIGINAL" } };
//		    
//		  }
		
	    //For this run Purchase.xml
//		 @DataProvider
//		  public Object[][] getData()
//		  {
//		HashMap<String,String> map = new HashMap<String,String>();
//		map.put("email", "rockshashi533@gmail.com");
//		map.put("password", "ShashiRaj@123");
//		map.put("product", "ZARA COAT 3");
//		
//		HashMap<String,String> map1 = new HashMap<String,String>();
//		map1.put("email", "rockshashi533@gmail.com");
//		map1.put("password", "ShashiRaj@123");
//		map1.put("product", "ADIDAS ORIGINAL");
//		return new Object[][] {{map},{map1}} ;
//		  }
}
