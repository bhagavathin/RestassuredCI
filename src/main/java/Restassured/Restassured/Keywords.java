package Restassured.Restassured;
import static Restassured.Restassured.DriverScript.APP_LOGS;
import static Restassured.Restassured.DriverScript.CONFIG;
import static Restassured.Restassured.DriverScript.OR;
import static Restassured.Restassured.DriverScript.currentTestCaseName;
import static Restassured.Restassured.DriverScript.currentTestDataSetID;
import static Restassured.Restassured.DriverScript.currentTestSuiteXLS;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.FileUtils;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxBinary;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.safari.SafariDriver;

public class Keywords {
	
	public WebDriver driver;
	
	
	public String openBrowser(String object,String data){		
		//APP_LOGS.debug("Opening browser");
		if(data.equals("Mozilla"))
		{
			FirefoxBinary binary = new FirefoxBinary(new File("C:\\Program Files\\Mozilla Firefox\\firefox.exe"));
			driver = new FirefoxDriver(binary,null);
			driver.manage().window().maximize();
			
			
			/*
			 * 
			 * 
				For IE and Firefox:
				
				diver.manage().window().maximize();
				
				For Chrome:
				
				ChromeOptions options = new ChromeOptions();
				options.addArguments("--start-maximized");
				driver = new ChromeDriver( options )
			 * 
			 * 
			 */

		}
		else if(data.equals("IE"))
		{ 
//			System.setProperty("webdriver.ie.driver", "C:\\Program Files\\Internet Explorer\\iexplore.exe");
			System.setProperty("webdriver.ie.driver", "D:\\Selenium Webdriver\\IEDriverServer_Win32_2.45.0\\IEDriverServer.exe");
			driver=new InternetExplorerDriver();
			driver.manage().window().maximize();

		}
					
		else if(data.equals("Chrome"))
		{ 

		    System.setProperty("webdriver.chrome.driver", "D:\\Selenium_Webdriver\\Driver\\chromedriver.exe");
		
		 	//driver=new ChromeDriver();
		 	ChromeOptions options = new ChromeOptions();
			options.addArguments("--start-maximized");
			driver = new ChromeDriver( options );
			
//			File file = new File("D:\\Selenium_Automation_Framework\\Driver\\chromedriver_win32_2.3\\chromedriver.exe");
//		    System.setProperty("webdriver.chrome.driver", file.getAbsolutePath());
		}
		
		
		//long implicitWaitTime=Long.parseLong(CONFIG.getProperty("implicitwait"));
		//driver.manage().timeouts().implicitlyWait(implicitWaitTime, TimeUnit.SECONDS);
		return Constants.KEYWORD_PASS;

	}
	
	public String navigate(String object,String data){		
	//	APP_LOGS.debug("Navigating to URL");
		try{
		driver.navigate().to(data);
		}catch(Exception e){
			return Constants.KEYWORD_FAIL+" -- Not able to navigate";
		}
		return Constants.KEYWORD_PASS;
	}
	
	public String clickLink(String object,String data){
        APP_LOGS.debug("Clicking on link ");
        try{
        driver.findElement(By.xpath(OR.getProperty(object))).click();
        }catch(Exception e){
			return Constants.KEYWORD_FAIL+" -- Not able to click on link"+e.getMessage();
        }
     
		return Constants.KEYWORD_PASS;
	}
	public String clickLink_linkText(String object,String data){
        APP_LOGS.debug("Clicking on link ");
        driver.findElement(By.linkText(OR.getProperty(object))).click();
     
		return Constants.KEYWORD_PASS;
	}
	
	
	
	public  String verifyLinkText(String object,String data){
        APP_LOGS.debug("Verifying link Text");
        try{
        	String actual=driver.findElement(By.xpath(OR.getProperty(object))).getText();
        	String expected=data;
        	
        	if(actual.equals(expected))
        		return Constants.KEYWORD_PASS;
        	else
        		return Constants.KEYWORD_FAIL+" -- Link text not verified";
        	
        }catch(Exception e){
			return Constants.KEYWORD_FAIL+" -- Link text not verified"+e.getMessage();

        }
        
	}
	
	
	public  String clickButton(String object,String data){
        APP_LOGS.debug("Clicking on Button");
        try{
            driver.findElement(By.xpath(OR.getProperty(object))).click();
            }catch(Exception e){
    			return Constants.KEYWORD_FAIL+" -- Not able to click on Button"+e.getMessage();
            }
        
        
		return Constants.KEYWORD_PASS;
	}
	
	public  String verifyButtonText(String object,String data){
		APP_LOGS.debug("Verifying the button text");
		try{
		String actual=driver.findElement(By.xpath(OR.getProperty(object))).getText();
    	String expected=data;

    	if(actual.equals(expected))
    		return Constants.KEYWORD_PASS;
    	else
    		return Constants.KEYWORD_FAIL+" -- Button text not verified "+actual+" -- "+expected;
		}catch(Exception e){
			return Constants.KEYWORD_FAIL+" Object not found "+e.getMessage();
		}
		
	}
	
	public  String selectList(String object, String data){
		APP_LOGS.debug("Selecting from list");
		try{
			if(!data.equals(Constants.RANDOM_VALUE)){
			  driver.findElement(By.xpath(OR.getProperty(object))).sendKeys(data);
			}else{
				// logic to find a random value in list
				WebElement droplist= driver.findElement(By.xpath(OR.getProperty(object))); 
				List<WebElement> droplist_cotents = droplist.findElements(By.tagName("option"));
				Random num = new Random();
				int index=num.nextInt(droplist_cotents.size());
				String selectedVal=droplist_cotents.get(index).getText();
				
			  driver.findElement(By.xpath(OR.getProperty(object))).sendKeys(selectedVal);
			}
		}catch(Exception e){
			return Constants.KEYWORD_FAIL +" - Could not select from list. "+ e.getMessage();	

		}
		
		return Constants.KEYWORD_PASS;	
	}
	
	public String verifyAllListElements(String object, String data){
		APP_LOGS.debug("Verifying the selection of the list");
	try{	
		WebElement droplist= driver.findElement(By.xpath(OR.getProperty(object))); 
		List<WebElement> droplist_cotents = droplist.findElements(By.tagName("option"));
		
		// extract the expected values from OR. properties
		String temp=data;
		String allElements[]=temp.split(",");
		// check if size of array == size if list
		if(allElements.length != droplist_cotents.size())
			return Constants.KEYWORD_FAIL +"- size of lists do not match";	
		
		for(int i=0;i<droplist_cotents.size();i++){
			if(!allElements[i].equals(droplist_cotents.get(i).getText())){
					return Constants.KEYWORD_FAIL +"- Element not found - "+allElements[i];
			}
		}
	}catch(Exception e){
		return Constants.KEYWORD_FAIL +" - Could not select from list. "+ e.getMessage();	

	}
		
		
		return Constants.KEYWORD_PASS;	
	}
	
	public  String verifyListSelection(String object,String data){
		APP_LOGS.debug("Verifying all the list elements");
		try{
			String expectedVal=data;
			//System.out.println(driver.findElement(By.xpath(OR.getProperty(object))).getText());
			WebElement droplist= driver.findElement(By.xpath(OR.getProperty(object))); 
			List<WebElement> droplist_cotents = droplist.findElements(By.tagName("option"));
			String actualVal=null;
			for(int i=0;i<droplist_cotents.size();i++){
				String selected_status=droplist_cotents.get(i).getAttribute("selected");
				if(selected_status!=null)
					actualVal = droplist_cotents.get(i).getText();			
				}
			
			if(!actualVal.equals(expectedVal))
				return Constants.KEYWORD_FAIL + "Value not in list - "+expectedVal;

		}catch(Exception e){
			return Constants.KEYWORD_FAIL +" - Could not find list. "+ e.getMessage();	

		}
		return Constants.KEYWORD_PASS;	

	}
	
	public  String selectRadio(String object, String data){
		APP_LOGS.debug("Selecting a radio button");
		try{
			String temp[]=object.split(Constants.DATA_SPLIT);
			driver.findElement(By.xpath(OR.getProperty(temp[0])+data+OR.getProperty(temp[1]))).click();
		}catch(Exception e){
			return Constants.KEYWORD_FAIL +"- Not able to find radio button";	

		}
		
		return Constants.KEYWORD_PASS;	

	}
	
	public  String verifyRadioSelected(String object, String data){
		APP_LOGS.debug("Verify Radio Selected");
		try{
			String temp[]=object.split(Constants.DATA_SPLIT);
			String checked=driver.findElement(By.xpath(OR.getProperty(temp[0])+data+OR.getProperty(temp[1]))).getAttribute("checked");
			if(checked==null)
				return Constants.KEYWORD_FAIL+"- Radio not selected";	

				
		}catch(Exception e){
			return Constants.KEYWORD_FAIL +"- Not able to find radio button";	

		}
		
		return Constants.KEYWORD_PASS;	

	}
	
	
	public  String checkCheckBox(String object,String data){
		APP_LOGS.debug("Checking checkbox");
		try{
			// true or null
			String checked=driver.findElement(By.xpath(OR.getProperty(object))).getAttribute("checked");
			if(checked==null)// checkbox is unchecked
				driver.findElement(By.xpath(OR.getProperty(object))).click();
		}catch(Exception e){
			return Constants.KEYWORD_FAIL+" - Could not find checkbox";
		}
		return Constants.KEYWORD_PASS;
		
	}
	
	public String unCheckCheckBox(String object,String data){
		APP_LOGS.debug("Unchecking checkBox");
		try{
			String checked=driver.findElement(By.xpath(OR.getProperty(object))).getAttribute("checked");
			if(checked!=null)
				driver.findElement(By.xpath(OR.getProperty(object))).click();
		}catch(Exception e){
			return Constants.KEYWORD_FAIL+" - Could not find checkbox";
		}
		return Constants.KEYWORD_PASS;
		
	}
	
	
	public  String verifyCheckBoxSelected(String object,String data){
		APP_LOGS.debug("Verifying checkbox selected");
		try{
			String checked=driver.findElement(By.xpath(OR.getProperty(object))).getAttribute("checked");
			if(checked!=null)
				return Constants.KEYWORD_PASS;
			else
				return Constants.KEYWORD_FAIL + " - Not selected";
			
		}catch(Exception e){
			return Constants.KEYWORD_FAIL+" - Could not find checkbox";

		}
		
		
	}
	
	
	public String verifyText(String object, String data){
		APP_LOGS.debug("Verifying the text");
		try{
			String actual=driver.findElement(By.xpath(OR.getProperty(object))).getText();
	    	String expected=data;

	    	if(actual.equals(expected))
	    		return Constants.KEYWORD_PASS;
	    	else
	    		return Constants.KEYWORD_FAIL+" -- text not verified "+actual+" -- "+expected;
			}catch(Exception e){
				return Constants.KEYWORD_FAIL+" Object not found "+e.getMessage();
			}
		
	}
	
	public  String writeInInput(String object,String data){
		APP_LOGS.debug("Writing in text box");
		
		try{
			driver.findElement(By.xpath(OR.getProperty(object))).sendKeys(data);
		}catch(Exception e){
			return Constants.KEYWORD_FAIL+" Unable to write "+e.getMessage();

		}
		return Constants.KEYWORD_PASS;
		
	}
	
	public  String verifyTextinInput(String object,String data){
       APP_LOGS.debug("Verifying the text in input box");
       try{
			String actual = driver.findElement(By.xpath(OR.getProperty(object))).getAttribute("value");
			String expected=data;

			if(actual.equals(expected)){
				return Constants.KEYWORD_PASS;
			}else{
				return Constants.KEYWORD_FAIL+" Not matching ";
			}
			
		}catch(Exception e){
			return Constants.KEYWORD_FAIL+" Unable to find input box "+e.getMessage();

		}
	}
	
	public  String clickImage(){
	       APP_LOGS.debug("Clicking the image");
			
			return Constants.KEYWORD_PASS;
	}
	
	public  String verifyFileName(){
	       APP_LOGS.debug("Verifying inage filename");
			
			return Constants.KEYWORD_PASS;
	}
	
	
	
	
	public  String verifyTitle(String object, String data){
	       APP_LOGS.debug("Verifying title");
	       try{
	    	   String actualTitle= driver.getTitle();
	    	   String expectedTitle=data;
	    	   if(actualTitle.equals(expectedTitle))
		    		return Constants.KEYWORD_PASS;
		    	else
		    		return Constants.KEYWORD_FAIL+" -- Title not verified "+expectedTitle+" -- "+actualTitle;
			   }catch(Exception e){
					return Constants.KEYWORD_FAIL+" Error in retrieving title";
			   }		
	}
	
	public String exist(String object,String data){
	       APP_LOGS.debug("Checking existance of element");
	       try{
	    	   driver.findElement(By.xpath(OR.getProperty(object)));
			   }catch(Exception e){
					return Constants.KEYWORD_FAIL+" Object doest not exist";
			  }
	       
	       
			return Constants.KEYWORD_PASS;
	}
	
	public  String click(String object,String data){
	       APP_LOGS.debug("Clicking on any element");
	       try{
	    	   driver.findElement(By.xpath(OR.getProperty(object))).click();
			   }catch(Exception e){
					return Constants.KEYWORD_FAIL+" Not able to click";
			  }
			return Constants.KEYWORD_PASS;
	}
	
	public  String synchronize(String object,String data){
		APP_LOGS.debug("Waiting for page to load");
		((JavascriptExecutor) driver).executeScript(
        		"function pageloadingtime()"+
        				"{"+
        				"return 'Page has completely loaded'"+
        				"}"+
        		"return (window.onload=pageloadingtime());");
        
		return Constants.KEYWORD_PASS;
	}
	
	public  String waitForElementVisibility(String object,String data){
		APP_LOGS.debug("Waiting for an element to be visible");
		int start=0;
		int time=(int)Double.parseDouble(data);
		try{
		 while(time == start){
			if(driver.findElements(By.xpath(OR.getProperty(object))).size() == 0){
				Thread.sleep(1000L);
				start++;
			}else{
				break;
			}
		 }
		}catch(Exception e){
			return Constants.KEYWORD_FAIL+"Unable to close browser. Check if its open"+e.getMessage();
		}
		return Constants.KEYWORD_PASS;
	}
	
	public  String closeBroswer(String object, String data){
		APP_LOGS.debug("Closing the browser");
		try{
			driver.quit();
		}catch(Exception e){
			return Constants.KEYWORD_FAIL+"Unable to close browser. Check if its open"+e.getMessage();
		}
		return Constants.KEYWORD_PASS;

	}
	
	public  String VerifyImage(String object, String data)
	{
			 
		 WebElement value1= driver.findElement(By.xpath(data));
		 System.out.println(value1.getText());
		 return value1.getText();
	}
	
	public  String VerifyWebTable(String object, String data)
	{
		 WebElement value=driver.findElement(By.xpath(data));
		 System.out.println(value.getText());
		 return value.getText();
	}	 
		
		//WebElement mytable =driver.findElement(By.xpath(data));
//		List<WebElement> rows_table = mytable.findElements(By.tagName("tr"));
//		 int rows_count = rows_table.size();
//		 System.out.println("Number of rows  "+rows_count);
//         //Loop will execute till the last row of table.
//        for (int row=0; row<rows_count; row++){
//         //To locate columns(cells) of that specific row.
//         List<WebElement> Columns_row = rows_table.get(row).findElements(By.tagName("td"));
//         //To calculate no of columns(cells) In that specific row.
//         int columns_count = Columns_row.size();
//         System.out.println("Number of cells In Row "+row+" are "+columns_count);
//         
//         //Loop will execute till the last cell of that specific row.
//         for (int column=0; column<columns_count; column++){
//          //To retrieve text from that specific cell.
//          String celtext = Columns_row.get(column).getText();
//          System.out.println("Cell Value Of row number "+row+" and column number "+column+" Is "+celtext);
//         }
//         System.out.println("--------------------------------------------------");
//        }  
     //   return Constants.KEYWORD_PASS;
      


	
	public String pause(String object, String data) throws NumberFormatException, InterruptedException{
		long time = (long)Double.parseDouble(object);
		Thread.sleep(time*1000L);
		return Constants.KEYWORD_PASS;
	}
	
	
	/************************APPLICATION SPECIFIC KEYWORDS********************************/
	
	public String validateLogin(String object, String data){
	// object of the current test XLS
	// name of my current test case
		System.out.println("xxxxxxxxxxxxxxxxxxxxx");
        String data_flag=currentTestSuiteXLS.getCellData(currentTestCaseName, "Data_correctness",currentTestDataSetID );
		while(driver.findElements(By.xpath(OR.getProperty("image_login_process"))).size() !=0 ){
			try{
				String visiblity=driver.findElement(By.xpath(OR.getProperty("image_login_process"))).getAttribute("style");
			    System.out.println("System Processing request - "+ visiblity);
			    if(visiblity.indexOf("hidden") != -1){
			    	// error message on screen
			    	// YOUR WORK
//			    	String actualErrMsg=driver.findElement(By.xpath(OR.getProperty("error_login"))).getText();
			    	//String expected=OR;
			    	if(data_flag.equals(Constants.POSITIVE_DATA))
			    	 return Constants.KEYWORD_FAIL;
			    	else
			    	 return Constants.KEYWORD_PASS;
			    }
			
			}catch(Exception e){
				
			}
		}
		
		// check for page title
		if(data_flag.equals(Constants.POSITIVE_DATA))
	    	 return Constants.KEYWORD_PASS;
	    else
	         return Constants.KEYWORD_FAIL;
	}
	
	
	
	
	public  String verifyLaptops(String object, String data){
        APP_LOGS.debug("Verifying the laptops in app");
		// brand
        String brand=currentTestSuiteXLS.getCellData(currentTestCaseName, "Brand", currentTestDataSetID).toLowerCase();
        for(int i=1 ; i<=4;i++){
        	String text = driver.findElement(By.xpath(OR.getProperty("laptop_name_link_start")+i+OR.getProperty("laptop_name_link_end"))).getText().toLowerCase();
        	if(text.indexOf(brand) == -1){
        		return Constants.KEYWORD_FAIL+" Brand not there in - "+text;
        	}
        	
        }
        
        
		return Constants.KEYWORD_PASS;
	}
	
	
	public String verifySearchResults(String object,String data){
        APP_LOGS.debug("Verifying the Search Results");
        try{
        	data=data.toLowerCase();
        	for(int i=3;i<=5;i++){
        		String text=driver.findElement(By.xpath(OR.getProperty("search_result_heading_start")+i+OR.getProperty("search_result_heading_end"))).getText().toLowerCase();
        		if(text.indexOf(data) == -1){
        			return Constants.KEYWORD_FAIL+" Got the text - "+text;
        		}
        	}
        	
        }catch(Exception e){
			return Constants.KEYWORD_FAIL+"Error -->"+e.getMessage();
		}
        
		return Constants.KEYWORD_PASS;


	}
	
	
	// not a keyword
	
	public void captureScreenshot(String filename, String keyword_execution_result) throws IOException{
		
		System.out.println("Capture Screen Shot step.......");
		// take screen shots
		if(CONFIG.getProperty("screenshot_everystep").equals("Y")){
			// capturescreen
			
			File scrFile = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
		    FileUtils.copyFile(scrFile, new File(System.getProperty("user.dir") +"//screenshots//"+filename+".jpg"));
			
		}else if (keyword_execution_result.startsWith(Constants.KEYWORD_FAIL) && CONFIG.getProperty("screenshot_error").equals("Y") ){
		// capture screenshot
			File scrFile = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
		    FileUtils.copyFile(scrFile, new File(System.getProperty("user.dir") +"//screenshots//"+filename+".jpg"));
		}
	}
	
	
		public String AutoSuggestControl(String object,String data){
	        APP_LOGS.debug("Auto suggest value");
	        driver.findElement(By.xpath(OR.getProperty(object))).click();
			return Constants.KEYWORD_PASS;
		}
}
