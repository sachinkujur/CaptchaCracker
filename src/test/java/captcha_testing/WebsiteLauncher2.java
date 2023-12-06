package captcha_testing;

import io.github.bonigarcia.wdm.WebDriverManager;
import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.TesseractException;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

public class WebsiteLauncher2 {
    public static void main(String[] args) {
        WebDriverManager.chromedriver().setup();
        WebDriver driver = new ChromeDriver();
        driver.get("https://pgsindia-ncof.gov.in/PGSI/login/");
        driver.manage().window().maximize();

        WebElement element = driver.findElement(By.xpath("//*[@id=\"cpatchaImg\"]"));
        File screenshotFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
        byte[] screenshotBytes = null;
        try {
            screenshotBytes = Files.readAllBytes(screenshotFile.toPath());
        } catch (IOException e) {
            e.printStackTrace();
        }

        BufferedImage fullImg = null;
        try {
            fullImg = ImageIO.read(new ByteArrayInputStream(screenshotBytes));
        } catch (IOException e) {
            e.printStackTrace();
        }

        Point point = element.getLocation();
        int eleWidth = element.getSize().getWidth();
        int eleHeight = element.getSize().getHeight();
        BufferedImage eleScreenshot = fullImg.getSubimage(point.getX(), point.getY(), eleWidth, eleHeight);
        try {
            ImageIO.write(eleScreenshot, "png", new File("screenshot.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        Tesseract tesseract = new Tesseract();
        try {
            tesseract.setDatapath("C:\\Users\\user\\IdeaProjects\\CaptchaCracker\\src\\tesseract");
            File imageFile = new File("C:\\Users\\user\\IdeaProjects\\CaptchaCracker\\screenshot.png");
            String result = tesseract.doOCR(imageFile);
            System.out.println(result);
        } catch (TesseractException e) {
            System.err.println(e.getMessage());
        }
        driver.quit();
    }
}