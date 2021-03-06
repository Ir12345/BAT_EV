package core;

import org.testng.annotations.Test;
import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;
import java.io.*;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.logging.*;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.*;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.htmlunit.*;
import org.openqa.selenium.safari.SafariDriver;
import org.openqa.selenium.safari.SafariOptions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.*;
import org.testng.annotations.*;
import java.lang.reflect.Method;
 
public class ElementsValidationTest implements ITest {
       static WebDriver driver;
       static final String baseUrl = "http://alex.academy/exercises/signup/v1/";
       
       //String csvFile = "./src/resources/test_data/csv/bat/elements_validation_chrome.csv";
       //String csvFile = "./src/resources/test_data/csv/bat/elements_validation_firefox.csv";
       //String csvFile = "./src/resources/test_data/csv/bat/elements_validation_safari.csv";
       String csvFile = "./src/resources/test_data/csv/bat/elements_validation.csv";
       private String test_name = "";  
       public String getTestName() {return test_name;}
       private void setTestName(String a) {test_name = a;}
       
       
       @BeforeMethod(alwaysRun = true)
       public void bm(Method method, Object[] parameters) {
              setTestName(method.getName());
              Override a = method.getAnnotation(Override.class);
              String testCaseId = (String) parameters[a.id()];
              setTestName(testCaseId);}
       @DataProvider(name = "dp")
       public Iterator<String[]> a2d() throws InterruptedException, IOException {
              String cvsLine = "";
              String[] a = null;
              ArrayList<String[]> al = new ArrayList<>();
              BufferedReader br = new BufferedReader(new FileReader(csvFile));
              while ((cvsLine = br.readLine()) != null) {
                     a = cvsLine.split(";");
                     al.add(a);}
              br.close();
              return al.iterator();}
	@Override
	@Test(dataProvider = "dp")
       public void test(String tc_id, String url, String element_id, String element_size, String element_location) {
 
              getDriver("safari", url);
              assertThat(isPresent(element_id, driver), equalTo(true));
              //assertThat(size(element_id, driver), equalTo(element_size));
              assertThat(sizeOffSet(element_id, element_size, 50, 50, driver), equalTo(true));
              //assertThat(location(element_id, driver), equalTo(element_location));}
              assertThat(locationOffSet(element_id, element_location, 50, 50, driver), equalTo(true));}
       
              @AfterMethod
              public void am() {driver.quit();}
              
              
 
	public static void getDriver(String browser, String url) {
					Logger logger = Logger.getLogger("");
					logger.setLevel(Level.OFF);
              if (browser.equalsIgnoreCase("chrome")) {
                     System.setProperty("webdriver.chrome.driver", "./src/resources/webdrivers/mac/chromedriver");
                     System.setProperty("webdriver.chrome.silentOutput", "true");
                     ChromeOptions option = new ChromeOptions();
                     option.addArguments("-start-fullscreen");
                     driver = new ChromeDriver(option);}
             else if (browser.equalsIgnoreCase("firefox")) {
                     System.setProperty("webdriver.gecko.driver", "./src/resources/webdrivers/mac/geckodriver");
                     driver = new FirefoxDriver();
                     driver.manage().window().maximize();
                     driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);}
             else if (browser.equalsIgnoreCase("safari")) {
            	 	SafariOptions options = new SafariOptions();
            	 	options.setUseCleanSession(true);
            	 	options.setPort(55555);
            	 	driver = new SafariDriver(options);
            	 	driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
            	 	driver.manage().window().maximize();}
               else {driver = new HtmlUnitDriver();
                    ((HtmlUnitDriver) driver).setJavascriptEnabled(true);
                     driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);}
              driver.get(baseUrl + url);}
 
	public static boolean isPresent(String element_id, WebDriver wd) {
              driver = wd;
              if (driver.findElements(By.id(element_id)).size() > 0) {return true;}
             else {return false;}}
  
	public static String size(String element_id, WebDriver wd) {
        driver = wd;
        String n = null;
        if (!driver.findElements(By.id(element_id)).isEmpty()) {
             String s = driver.findElement(By.id(element_id)).getSize().toString(); return s;}
       else {return n;}}
	
	public static boolean sizeOffSet(String element_id, String element_size, int w_offset, int h_offset, WebDriver wd) {
		driver = wd;
	if (!driver.findElements(By.id(element_id)).isEmpty()) {
			
            int w_act = driver.findElement(By.id(element_id)).getSize().getWidth(); 
            int h_act = driver.findElement(By.id(element_id)).getSize().getHeight();
            
            int w_exp = Integer.parseInt(element_size.replaceAll("\\s","")
            										 .replaceAll("\\(","")
            										 .replaceAll("\\)","")
            										 .split(",")[0]);
            										 
            int h_exp = Integer.parseInt(element_size.replaceAll("\\s","")
                    								 .replaceAll("\\(","")
                    								 .replaceAll("\\)","")
                    								 .split(",")[1]);
    if (
            	(w_act >= w_exp - w_offset && w_act <= w_exp + w_offset) &&
            	(h_act >= h_exp - h_offset && h_act <= h_exp + h_offset)
            		)
            {return true;}
    else {return false;}}      
           
    else {return false;}}
		
  
	public static String location(String element_id, WebDriver wd) {
              driver = wd;
              String n = null;
              if (!driver.findElements(By.id(element_id)).isEmpty()) {
                  String l = driver.findElement(By.id(element_id)).getLocation().toString(); return l;}
             else {return n;}}
	
	public static boolean locationOffSet(String element_id, String element_location, int x_offset, int y_offset, WebDriver wd) {
		driver = wd;
	if (!driver.findElements(By.id(element_id)).isEmpty()) {
			
            int x_act = driver.findElement(By.id(element_id)).getLocation().getX(); 
            int y_act = driver.findElement(By.id(element_id)).getLocation().getY();
            
            int x_exp = Integer.parseInt(element_location.replaceAll("\\s","")
            										 .replaceAll("\\(","")
            										 .replaceAll("\\)","")
            										 .split(",")[0]);
            										 
            int y_exp = Integer.parseInt(element_location.replaceAll("\\s","")
                    								 .replaceAll("\\(","")
                    								 .replaceAll("\\)","")
                    								 .split(",")[1]);
    if (
            	(x_act >= x_exp - x_offset && x_act <= x_exp + x_offset) &&
            	(y_act >= y_exp - y_offset && y_act <= y_exp + y_offset)
            		)
            {return true;}
    else {return false;}}      
           
    else {return false;}}
}
 