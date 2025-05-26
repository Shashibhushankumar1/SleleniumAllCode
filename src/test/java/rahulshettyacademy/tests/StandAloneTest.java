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

import rahulshettyacademy.pageobjects.LandingPage;

public class StandAloneTest {
	public static void main(String[] args) throws Exception {
		String productName = "ZARA COAT 3";

		// WebDriverManager.chromedriver().setup();
		WebDriver driver = new ChromeDriver();
//		WebDriver driver = new FirefoxDriver();
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
		driver.manage().window().maximize();
		driver.get("https://rahulshettyacademy.com/client");
		LandingPage landingPage = new LandingPage(driver);
		driver.findElement(By.id("userEmail")).sendKeys("rockshashi533@gmail.com");
		driver.findElement(By.id("userPassword")).sendKeys("ShashiRaj@123");
		driver.findElement(By.id("login")).click();
//		driver.navigate().refresh();
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));

		wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".mb-3")));

		// <div _ngcontent-hav-c34="" class="col-lg-4 col-md-6 col-sm-10 offset-md-0
		// offset-sm-1 mb-3 ng-star-inserted"> Add To Cart</button>
		// Here .mb-3 Taken
		List<WebElement> products = driver.findElements(By.cssSelector(".mb-3"));
		// Product Name finding below ZARA COAT 3
		// How Many ZARA COAT 3 Name get Return First with help findFirst()
		WebElement prod = products.stream()
				.filter(product -> product.findElement(By.cssSelector("b")).getText().equals(productName)).findFirst()
				.orElse(null);
		// <div _ngcontent-hav-c34="" class="card-body" xpath="1"> Add To
		// Cart</button></div>
		// <button _ngcontent-hav-c34="" class="btn w-10 rounded" style="float: right;">
		// Add To Cart</button>
		prod.findElement(By.cssSelector(".card-body button:last-of-type")).click();

		// <div id="toast-container" class="toast-bottom-right toast-container"></div>
		// Wait Until toast message show add to card
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("#toast-container")));
		// ng-animating For Loading Icon
		wait.until(ExpectedConditions.invisibilityOf(driver.findElement(By.cssSelector(".ng-animating"))));
		// For Cart button after added product
		// <button _ngcontent-hav-c33="" routerlink="/dashboard/cart"
		// class="btn"></label></button>
		// routerlink="/dashboard/cart" Partial Text='cart'
		driver.findElement(By.cssSelector("[routerlink*='cart']")).click();
		// <div _ngcontent-bbs-c36="" class="cartSection">ZARA COAT 3</h3></div>
		// <h3 _ngcontent-bbs-c36="">ZARA COAT 3</h3>
		// anyMatch() = That Product will match then
		// filter() =Complete match
		List<WebElement> cartProducts = driver.findElements(By.cssSelector(".cartSection h3"));
		Boolean match = cartProducts.stream()
				.anyMatch(cartProduct -> cartProduct.getText().equalsIgnoreCase(productName));
		Assert.assertTrue(match);
		// <li _ngcontent-bbs-c36="" class="totalRow">Checkout</button></li>
		// <button _ngcontent-bbs-c36="" type="button" class="btn
		// btn-primary">Checkout</button>
		// Checkout Button
		driver.findElement(By.cssSelector(".totalRow button")).click();
		// <input _ngcontent-bbs-c35="" placeholder="Select Country">
		Actions a = new Actions(driver);
		a.sendKeys(driver.findElement(By.cssSelector("[placeholder='Select Country']")), "india").build().perform();
		// <section _ngcontent-bbs-c32="" class="ta-results list-group
		// ng-star-inserted"></div><!----></section>
		// it will wait until pop will come
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".ta-results")));
		// Regular Expression
		// <button _ngcontent-bbs-c32="" type="button" class="ta-item list-group-item
		// ng-star-inserted"><!----></button>
		driver.findElement(By.xpath("(//button[contains(@class,'ta-item')])[2]")).click();
		// placae the order
		Thread.sleep(3000);
		//a[text()='Place Order ']
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("window.scrollBy(0, 500);");
 	    driver.findElement(By.xpath("//a[text()='Place Order ']")).click();
//		driver.findElement(By.cssSelector(".action__submit")).click();

		// <h1 _ngcontent-bbs-c39="" class="hero-primary" >Thankyou for the order. </h1>s
		String confirmMessage = driver.findElement(By.cssSelector(".hero-primary")).getText();
		Assert.assertTrue(confirmMessage.equalsIgnoreCase("THANKYOU FOR THE ORDER."));
//		driver.close();
	}

}
