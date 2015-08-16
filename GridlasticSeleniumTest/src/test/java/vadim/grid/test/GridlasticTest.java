package vadim.grid.test;

import java.net.URL;
import java.util.Arrays;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.Platform;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class GridlasticTest {

    private static final String VIDEO_URL = null;
    private RemoteWebDriver driver;

    @BeforeMethod(alwaysRun = true)
    public void beforeMethod() throws Exception {

		// Example test environment. NOTE: Gridlastic auto scaling requires all
        // these 3 environment variables in each request.
        String platform_name = "linux";
        String browser_name = "chrome";
        String browser_version = ""; // for Chrome leave empty

        // optional video recording
        String record_video = "True";

        DesiredCapabilities capabilities = new DesiredCapabilities();
        if (platform_name.equalsIgnoreCase("win7")) {
            capabilities.setPlatform(Platform.VISTA);
        }
        if (platform_name.equalsIgnoreCase("win8")) {
            capabilities.setPlatform(Platform.WIN8);
        }
        if (platform_name.equalsIgnoreCase("win8_1")) {
            capabilities.setPlatform(Platform.WIN8_1);
        }
        if (platform_name.equalsIgnoreCase("linux")) {
            capabilities.setPlatform(Platform.LINUX);
        }
        capabilities.setBrowserName(browser_name);
        capabilities.setVersion(browser_version);

        // video record
        if (record_video.equalsIgnoreCase("True")) {
            capabilities.setCapability("video", "True");
        } else {
            capabilities.setCapability("video", "False");
        }

        if (browser_name.equalsIgnoreCase("chrome")) {
            ChromeOptions options = new ChromeOptions();
            options.addArguments(Arrays.asList("--start-maximized"));
            capabilities.setCapability(ChromeOptions.CAPABILITY, options);
        }

        driver = new RemoteWebDriver(
                new URL("http://kf33e6s2lwGG7vXiuddROI73FzPfJ2qU:7jOcg74stAYqWLzgPCYtaWnGl2MVU2FR@BAGIKTEST.gridlastic.com:80/wd/hub"),
                capabilities);
        driver.manage().timeouts().implicitlyWait(60, TimeUnit.SECONDS);
        driver.manage().window().maximize();

        if (record_video.equalsIgnoreCase("True")) {
            System.out
                    .println("Test Video: " + VIDEO_URL + ((RemoteWebDriver) driver).getSessionId());
        }
    }

    @Test(enabled = true)
    public void submitTestForm() {
        
        String name = "Vadim Oksentiuk";
        
        // Go to submit form
        driver.get("https://docs.google.com/forms/d/18nq9YuC0E8p2JOONkqZ5IAMIdP1eytiEDV8hJn_spHk/viewform");

        // Find the input elements by its ids
        WebElement textInput = driver.findElement( By.xpath("//*[@id='entry_785445797']") );
        textInput.sendKeys( name );
        
        if( !driver.findElement( By.xpath("//*[@id='group_396363777_2']") ).isSelected() ) 
            driver.findElement( By.xpath("//*[@id='group_396363777_2']") ).click();
        
        if( !driver.findElement( By.xpath("//*[@id='group_396363777_4']") ).isSelected() ) 
            driver.findElement( By.xpath("//*[@id='group_396363777_4']") ).click();
            
        if( !driver.findElement( By.xpath("//*[@id='group_277070397_3']") ).isSelected() ) 
            driver.findElement( By.xpath("//*[@id='group_396363777_4']") ).click();
            
        driver.findElement( By.xpath("//*[@id='ss-submit']") ).click();
        
        System.out.println( "Result: " + driver.getTitle() );
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown() throws Exception {
        driver.quit();
    }

}
