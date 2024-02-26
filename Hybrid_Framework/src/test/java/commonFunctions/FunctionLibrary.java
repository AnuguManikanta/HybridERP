package commonFunctions;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.Duration;
import java.util.Properties;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.Reporter;

public class FunctionLibrary {
	public static Properties conpro;
	public static WebDriver driver;
	//method for launch browser
	public static WebDriver startBrowser()throws Throwable {
		conpro = new Properties();
		//load properties file
		conpro.load(new FileInputStream("./PropertyFile/Environment.properties"));
		if(conpro.getProperty("Browser").equalsIgnoreCase("chrome")) {
			driver = new ChromeDriver();
			driver.manage().window().maximize();
		}
		else if(conpro.getProperty("Browser").equalsIgnoreCase("firefox")) {
			driver = new FirefoxDriver();
		}
		else {
			Reporter.log("Browser value is not matching",true);
		}
		return driver;
		
	}
	//method for launching Url
	public static void openUrl() {
		driver.get(conpro.getProperty("Url"));
	}
	//method for wait for element
	public static void waitForElement(String Locater_Type,String Locater_Value,String Test_Data) {
		WebDriverWait mywait = new WebDriverWait(driver, Duration.ofSeconds(Integer.parseInt(Test_Data)));
		if(Locater_Type.equalsIgnoreCase("xpath")) {
			mywait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(Locater_Value)));
		}
		if(Locater_Type.equalsIgnoreCase("id")) {
			mywait.until(ExpectedConditions.visibilityOfElementLocated(By.id(Locater_Value)));
		}
		if(Locater_Type.equalsIgnoreCase("name")) {
			mywait.until(ExpectedConditions.visibilityOfElementLocated(By.name(Locater_Value)));
		}
			
		
	}
	//method for type action used to perform action in text boxes
	public static void typeAction(String Locater_Type,String Locater_Value,String Test_Data) {
		if(Locater_Type.equalsIgnoreCase("xpath")) {
			driver.findElement(By.xpath(Locater_Value)).clear();
			driver.findElement(By.xpath(Locater_Value)).sendKeys(Test_Data);
		}
		if(Locater_Type.equalsIgnoreCase("id")) {
			driver.findElement(By.id(Locater_Value)).clear();
			driver.findElement(By.id(Locater_Value)).sendKeys(Test_Data);
		}
		if(Locater_Type.equalsIgnoreCase("name")) {
			driver.findElement(By.name(Locater_Value)).clear();
			driver.findElement(By.name(Locater_Value)).sendKeys(Test_Data);
		}
	}
	//method for click action to perform on buttons.images,links,radiobuttion and checkboxes
	public static void clickAction(String Locater_Type,String Locater_Value) {
		if(Locater_Type.equalsIgnoreCase("xpath")) {
			driver.findElement(By.xpath(Locater_Value)).click();
		}
		if(Locater_Type.equalsIgnoreCase("name")) {
			driver.findElement(By.name(Locater_Value)).click();
		}
		if(Locater_Type.equalsIgnoreCase("id")) {
			driver.findElement(By.id(Locater_Value)).sendKeys(Keys.ENTER);
		}
	}
	//method for validation title
	public static void validateTitle(String expected_title) {
		String actual_title = driver.getTitle();
		try {
			Assert.assertEquals(expected_title, actual_title,"title is not matching");
			
		} catch (Throwable t) {
			System.out.println(t.getMessage());
		}
	}
	//method for close browser
	public static void closeBrowser() {
		driver.quit();
	}
	//method for listboxes
	public static void dropDownAction(String Locater_Type,String Locater_Value,String Test_Data) {
		if(Locater_Type.equalsIgnoreCase("xpath")) {
			int value = Integer.parseInt(Test_Data);
			Select element = new Select(driver.findElement(By.xpath(Locater_Value)));
			element.selectByIndex(value);
		}
		if(Locater_Type.equalsIgnoreCase("name")) {
			int value = Integer.parseInt(Test_Data);
			Select element = new Select(driver.findElement(By.name(Locater_Value)));
			element.selectByIndex(value);
		}
		if(Locater_Type.equalsIgnoreCase("id")) {
			int value = Integer.parseInt(Test_Data);
			Select element = new Select(driver.findElement(By.id(Locater_Value)));
			element.selectByIndex(value);
		}
		
	}
	//method for capturing stock number into note pad
	public static void captureStockNum(String Locater_Type,String Locater_Value) throws Throwable {
		String StockNum = "";
		if(Locater_Type.equalsIgnoreCase("xpath")) {
			StockNum = driver.findElement(By.xpath(Locater_Value)).getAttribute("value");
		}
		if(Locater_Type.equalsIgnoreCase("name")) {
			StockNum = driver.findElement(By.name(Locater_Value)).getAttribute("value");
		}
		if(Locater_Type.equalsIgnoreCase("id")) {
			StockNum = driver.findElement(By.id(Locater_Value)).getAttribute("value");
		}
		FileWriter fw = new FileWriter("./CaptureData/stocknumber.txt");
		BufferedWriter bw = new BufferedWriter(fw);
		bw.write(StockNum);
		bw.flush();
		bw.close();
	}
	//method for stock table
	public static void stockTable() throws Throwable {
		FileReader fr = new FileReader("./CaptureData/stocknumber.txt");
		BufferedReader br = new BufferedReader(fr);
		String exp_data = br.readLine();
		if(!driver.findElement(By.xpath(conpro.getProperty("search-textbox"))).isDisplayed()) {
			driver.findElement(By.xpath(conpro.getProperty("search-panel"))).click();
			driver.findElement(By.xpath(conpro.getProperty("search-textbox"))).sendKeys(exp_data);
			driver.findElement(By.xpath(conpro.getProperty("search-button"))).click();
			Thread.sleep(4000);
			String act_data = driver.findElement(By.xpath("//table[@class='table ewTable']/tbody/tr[1]/td[8]/div/span/span")).getText();
			Reporter.log(exp_data+"    "+act_data,true);
			try {
				Assert.assertEquals(exp_data, act_data,"stock number not matching");
			}catch(AssertionError a) {
				System.out.println(a.getMessage());
			}
			}
	}
		//method for capture supplier number
		public static void captureSup(String Locater_Type,String Locater_Value)throws Throwable {
			String SupplierNum = "";
			if(Locater_Type.equalsIgnoreCase("xpath")) {
				SupplierNum = driver.findElement(By.xpath(Locater_Value)).getAttribute("value");
			}
			if(Locater_Type.equalsIgnoreCase("id")) {
				SupplierNum = driver.findElement(By.id(Locater_Value)).getAttribute("value");
			}
			if(Locater_Type.equalsIgnoreCase("name")) {
				SupplierNum = driver.findElement(By.name(Locater_Value)).getAttribute("value");
			}
			FileWriter fw = new FileWriter("./CaptureData/suppliernumber.txt");
			BufferedWriter bw = new BufferedWriter(fw);
			bw.write(SupplierNum);
			bw.flush();
			bw.close();
			
		}
		//method for supplier table
		public static void supplierTable() throws Throwable {
			FileReader fr = new FileReader("./CaptureData/suppliernumber.txt");
			BufferedReader br = new BufferedReader(fr);
			String exp_data = br.readLine();
			if(!driver.findElement(By.xpath(conpro.getProperty("search-textbox"))).isDisplayed()) {
				driver.findElement(By.xpath(conpro.getProperty("search-panel"))).click();
				driver.findElement(By.xpath(conpro.getProperty("search-textbox"))).sendKeys(exp_data);
				driver.findElement(By.xpath(conpro.getProperty("search-button"))).click();
				Thread.sleep(4000);
				String act_data = driver.findElement(By.xpath("//table[@class='table ewTable']/tbody/tr[1]/td[8]/div/span/span")).getText();
				Reporter.log(exp_data+"    "+act_data,true);
				try {
					Assert.assertEquals(exp_data, act_data,"supplier number not matching");
				}catch(AssertionError a) {
					System.out.println(a.getMessage());
		}
}
			
		}
		//method for capture customer number
				public static void capturecus(String Locater_Type,String Locater_Value)throws Throwable {
					String CustomerNum = "";
					if(Locater_Type.equalsIgnoreCase("xpath")) {
						CustomerNum = driver.findElement(By.xpath(Locater_Value)).getAttribute("value");
					}
					if(Locater_Type.equalsIgnoreCase("id")) {
						CustomerNum = driver.findElement(By.id(Locater_Value)).getAttribute("value");
					}
					if(Locater_Type.equalsIgnoreCase("name")) {
						CustomerNum = driver.findElement(By.name(Locater_Value)).getAttribute("value");
					}
					FileWriter fw = new FileWriter("./CaptureData/customernumber.txt");
					BufferedWriter bw = new BufferedWriter(fw);
					bw.write(CustomerNum);
					bw.flush();
					bw.close();
					
				}
				//method for customer table
				public static void customerTable() throws Throwable {
					FileReader fr = new FileReader("./CaptureData/customernumber.txt");
					BufferedReader br = new BufferedReader(fr);
					String exp_data = br.readLine();
					if(!driver.findElement(By.xpath(conpro.getProperty("search-textbox"))).isDisplayed()) {
						driver.findElement(By.xpath(conpro.getProperty("search-panel"))).click();
						driver.findElement(By.xpath(conpro.getProperty("search-textbox"))).sendKeys(exp_data);
						driver.findElement(By.xpath(conpro.getProperty("search-button"))).click();
						Thread.sleep(4000);
						String act_data = driver.findElement(By.xpath("//table[@class='table ewTable']/tbody/tr[1]/td[8]/div/span/span")).getText();
						Reporter.log(exp_data+"    "+act_data,true);
						try {
							Assert.assertEquals(exp_data, act_data,"customer number not matching");
						}catch(AssertionError a) {
							System.out.println(a.getMessage());
				}
		}
					
				}
}
				
