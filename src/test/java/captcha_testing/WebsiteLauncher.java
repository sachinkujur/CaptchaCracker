package captcha_testing;

import io.github.bonigarcia.wdm.WebDriverManager;
import net.sourceforge.tess4j.ITesseract;
import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.TesseractException;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import ru.yandex.qatools.ashot.AShot;
import ru.yandex.qatools.ashot.Screenshot;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class WebsiteLauncher {
    public static void main(String[] args) throws IOException, TesseractException {
        WebDriverManager.chromedriver().setup();
        WebDriver driver = new ChromeDriver();
        driver.get("https://traceability.apeda.gov.in/Organic/TraceNet/ErrorPage/authorizationfailed.html?msg=Thread%252520was%252520being%252520aborted.");
        driver.manage().window().maximize();

        driver.findElement(By.xpath("//*[@id=\"myform\"]/table/tbody/tr[4]/td[2]/a[3]")).click();
        ArrayList<String> newTb = new ArrayList<String>(driver.getWindowHandles());
        //switch to new tab
        driver.switchTo().window(newTb.get(1));
        Screenshot screenshot = new AShot().takeScreenshot(driver, driver.findElement(By.xpath("//*[@id=\"ctl00_ContentPlaceHolder1_chapthca123\"]")));
        ImageIO.write(screenshot.getImage(), "PNG", new File("C:\\Users\\user\\OneDrive - MoreYeahs IT Technologies Pvt Ltd\\Pictures\\div_element.png"));

        ITesseract image = new Tesseract();
        image.setDatapath("C:\\Users\\user\\IdeaProjects\\CaptchaCracker\\src\\tesseract");
        image.setLanguage("eng");
        String str1 = image.doOCR(new File("C:\\Users\\user\\OneDrive - MoreYeahs IT Technologies Pvt Ltd\\Pictures\\div_element.png"));
        System.out.println(str1);

//        driver.findElement(By.xpath("//*[@id=\"verify\"]")).sendKeys(str1);
//        driver.quit();
    }
}
