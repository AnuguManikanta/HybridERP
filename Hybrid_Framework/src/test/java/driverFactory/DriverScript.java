package driverFactory;

import org.openqa.selenium.WebDriver;
import org.testng.annotations.Test;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

import commonFunctions.FunctionLibrary;
import utilities.ExcelFileUtil;

public class DriverScript {
	public static WebDriver driver;
	String inputpath = "./FileInput/Data Engine.xlsx";
	String outputpath = "./FileOutput/HybirdResults.xlsx";
	ExtentReports reports;
	ExtentTest logger;
	@Test
	public void startTest() throws Throwable {
		String modulestatus = "";
		//create object for excelfileutil class
		ExcelFileUtil xl = new ExcelFileUtil(inputpath);
		String testcases = "MasterTestCase";
		//iterate all rows testcases sheet
		for(int i=1;i<=xl.rowCount(testcases);i++) {
			if(xl.getCellData(testcases, i, 2).equalsIgnoreCase("Y")) {
				//read all testcase or corresponding sheets
				String TCModule = xl.getCellData(testcases, i, 1);
				//define path of html
				reports = new ExtentReports("./target/Reports/"+TCModule+".html");
				logger = reports.startTest(TCModule);
				//iterate all rows in TCModule sheet
				for(int j=1;j<=xl.rowCount(TCModule);j++) {
					//read all cells from TCModule 
					String Description = xl.getCellData(TCModule, j, 0);
					String Object_Type = xl.getCellData(TCModule, j, 1);
					String Locator_Type = xl.getCellData(TCModule, j, 2);
					String Locator_Value = xl.getCellData(TCModule, j, 3);
					String Test_Data = xl.getCellData(TCModule, j, 4);
					try {
						if(Object_Type.equalsIgnoreCase("startBrowser")) {
						FunctionLibrary.startBrowser();
							logger.log(LogStatus.INFO, Description);
						}
						if(Object_Type.equalsIgnoreCase("openUrl")) {
							 FunctionLibrary.openUrl();
							 logger.log(LogStatus.INFO, Description);
							
						}
						if(Object_Type.equalsIgnoreCase("waitForElement")) {
							FunctionLibrary.waitForElement(Locator_Type, Locator_Value, Test_Data);
							logger.log(LogStatus.INFO, Description);
						}
						if(Object_Type.equalsIgnoreCase("typeAction")) {
							FunctionLibrary.typeAction(Locator_Type, Locator_Value, Test_Data);
							logger.log(LogStatus.INFO, Description);
							
						}
						if(Object_Type.equalsIgnoreCase("clickAction")) {
							FunctionLibrary.clickAction(Locator_Type,Locator_Value);
							logger.log(LogStatus.INFO, Description);
						}
						if(Object_Type.equalsIgnoreCase("validateTitle")) {
							FunctionLibrary.validateTitle(Test_Data);
							logger.log(LogStatus.INFO, Description);
							
						}
						if(Object_Type.equalsIgnoreCase("closeBrowser")) {
							FunctionLibrary.closeBrowser();
							logger.log(LogStatus.INFO, Description);
						}
						if(Object_Type.equalsIgnoreCase("dropDownAction")) {
							FunctionLibrary.dropDownAction(Locator_Type, Locator_Value, Test_Data);
							logger.log(LogStatus.INFO, Description);
							
						}
						if(Object_Type.equalsIgnoreCase("captureStockNum")) {
							FunctionLibrary.captureStockNum(Locator_Value, Test_Data);
							logger.log(LogStatus.INFO, Description);
						}
							if(Object_Type.equalsIgnoreCase("stockTable")) {
								FunctionLibrary.stockTable();
								logger.log(LogStatus.INFO, Description);
							}
							if(Object_Type.equalsIgnoreCase("captureSup")) {
								FunctionLibrary.captureSup(Locator_Type, Locator_Value);
								logger.log(LogStatus.INFO, Description);
							}
							if(Object_Type.equalsIgnoreCase("supplierTable")) {
								FunctionLibrary.supplierTable();
								logger.log(LogStatus.INFO, Description);
								
							}
							if(Object_Type.equalsIgnoreCase("capturecus")) {
								FunctionLibrary.capturecus(Locator_Type, Locator_Value);
								logger.log(LogStatus.INFO, Description);
							}
							if(Object_Type.equalsIgnoreCase("customerTable")) {
								FunctionLibrary.customerTable();
								logger.log(LogStatus.INFO, Description);
							}
							
						
						//write as pass into status cell in TCModule
						xl.setCellData(TCModule, j, 5, "pass",outputpath);
						logger.log(LogStatus.PASS, Description);
						modulestatus = "True";
						
						
					}catch(Exception e) {
						System.out.println(e.getMessage());
						//write as fail into status cell in TCModule
						xl.setCellData(TCModule, j, 5, "fail", outputpath);
						logger.log(LogStatus.FAIL, Description);
						modulestatus = "False";
						
					}
					if(modulestatus.equalsIgnoreCase("true")) {
						//write as pass into testcases sheet
						xl.setCellData(testcases, i, 3, "pass", outputpath);
					}
					else {
						//write as fail into testcases sheet
						xl.setCellData(testcases, i, 3, "fail", outputpath);
					}
					reports.endTest(logger);
					reports.flush();
				}
			}else {
				//write as blocked into status cell for flag N
				xl.setCellData(testcases, i, 3, "Blocked", outputpath);
			}
		}
	}
			
	

}
