package test1;

import com.google.j2objc.annotations.Weak;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.sql.Driver;
import java.time.Duration;

public class loginTest {
    private WebDriver driver;

    @BeforeClass
    public  void setUp(){
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(15));
        driver.get("https://www.secilstore.com/");

        System.out.println("Sayfa Title: " + driver.getTitle());
        System.out.println("Sayfa URL: " + driver.getCurrentUrl());
    }


    @Test
    public void testValidLogin() throws InterruptedException {
        driver.findElement(By.xpath("//span[@class='md:hidden lg:inline whitespace-nowrap']")).click(); // Giriş Yap
        driver.findElement(By.xpath("//a[@id='login-tab']")).click(); // Login tab
        driver.findElement(By.xpath("//input[@name='email']")).sendKeys("muhammed.gundogdu.test@gmail.com"); // email
        driver.findElement(By.xpath("//input[@name='password']")).sendKeys("Test_2905"); //şifre
        driver.findElement(By.xpath("//input[@name='rememberme']")).click(); //Beni Hatırla
        driver.findElement(By.xpath("//button[@class='btn btn-secil mx-auto w-fit uppercase basis-1/2']")).click(); //Giriş Butonu
        Thread.sleep(5000);

       WebElement profileElement = driver.findElement(By.xpath("//span[@class='md:hidden lg:inline' and text()='Hesabım']"));
       Assert.assertTrue(profileElement.isDisplayed(), "Kullanıcı başarılı giriş yaptı!");
    }


    @Test
    public void testInvalidLogin() throws InterruptedException {
        driver.findElement(By.xpath("//span[@class='md:hidden lg:inline whitespace-nowrap']")).click(); // Giriş Yap
        driver.findElement(By.xpath("//a[@id='login-tab']")).click(); // Login tab
        driver.findElement(By.xpath("//input[@name='email']")).sendKeys("test@gmail.com"); // email
        driver.findElement(By.xpath("//input[@name='password']")).sendKeys("Test_2905"); //şifre
        driver.findElement(By.xpath("//button[@class='btn btn-secil mx-auto w-fit uppercase basis-1/2']")).click(); //Giriş Butonu
        Thread.sleep(5000);

       WebElement errorMessage = driver.findElement(By.xpath("//div[@class='p-1 text-center']"));
       Assert.assertTrue(errorMessage.isDisplayed(), "Hata mesajı görüntülendi!");
    }


    @Test
    public void testEmptyFields() throws InterruptedException {
        driver.findElement(By.xpath("//span[@class='md:hidden lg:inline whitespace-nowrap']")).click(); // Giriş Yap
        driver.findElement(By.xpath("//a[@id='login-tab']")).click(); // Login tab
        driver.findElement(By.xpath("//input[@name='email']")).sendKeys(""); // email
        driver.findElement(By.xpath("//input[@name='password']")).sendKeys(""); //şifre
        driver.findElement(By.xpath("//button[@class='btn btn-secil mx-auto w-fit uppercase basis-1/2']")).click(); //Giriş Butonu
        Thread.sleep(5000);

        WebElement validationMessage = driver.findElement(By.xpath("//span[@class='label-text-alt text-error' and text()='Bu alan zorunludur'][1]"));
        Assert.assertTrue(validationMessage.isDisplayed(), "Boş alanlar için validasyon çalışmıyor!");
    }

    @Test
    public void testForgotPasswordEmail() throws InterruptedException {
        driver.findElement(By.xpath("//span[@class='md:hidden lg:inline whitespace-nowrap']")).click(); // Giriş Yap
        driver.findElement(By.xpath("//a[@id='login-tab']")).click(); // Login tab
        driver.findElement(By.xpath("//label[@class='btn btn-ghost min-h-6 w-fit min-h-6 h-fit p-0' and text()='Şifremi Unuttum']")).click(); //Şifremi unuttum
        driver.findElement(By.xpath("//input[@value='email']")).click(); //E-Mail'e Gönder Seçeneği
        driver.findElement(By.xpath("//input[@placeholder='E-Posta']")).sendKeys("muhammed.gundogdu.test@gmail.com"); // email
        driver.findElement(By.xpath("//button[@class='btn btn-secil mx-auto w-fit uppercase' and text()='Gönder']")).click(); //Şifre Yenileme Butonu
        Thread.sleep(5000);

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement successMessage = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//a[@id='login-tab' and text()='Giriş Yap']")));

        //WebElement successMessage = driver.findElement(By.xpath("//a[@id='login-tab' and text()='Giriş Yap']"));
        //Assert.assertTrue(successMessage.isDisplayed(),"Şifre Yenileme Linki E-Mail Adresinize Gönderildi!");
    }


    @Test
    public void testForgotPasswordFalseEmail() throws InterruptedException {
        driver.findElement(By.xpath("//span[@class='md:hidden lg:inline whitespace-nowrap']")).click(); // Giriş Yap
        driver.findElement(By.xpath("//a[@id='login-tab']")).click(); // Login tab
        driver.findElement(By.xpath("//label[@class='btn btn-ghost min-h-6 w-fit min-h-6 h-fit p-0' and text()='Şifremi Unuttum']")).click(); //Şifremi unuttum
        driver.findElement(By.xpath("//input[@value='email']")).click(); //E-Mail'e Gönder Seçeneği
        driver.findElement(By.xpath("//input[@placeholder='E-Posta']")).sendKeys("test@gmail.com"); // email
        driver.findElement(By.xpath("//button[@class='btn btn-secil mx-auto w-fit uppercase' and text()='Gönder']")).click(); //Şifre Yenileme Butonu
        Thread.sleep(5000);

        WebElement successMessage = driver.findElement(By.xpath("//div[@class='p-1 block text-center' and text()='Kullanıcı bulunamadı']"));
        Assert.assertTrue(successMessage.isDisplayed(),"Bu E-Mail'e ait bir kullanıcı bulunamadı");
    }


    @Test
    public void testForgotPasswordPhone() throws InterruptedException {
        driver.findElement(By.xpath("//span[@class='md:hidden lg:inline whitespace-nowrap']")).click(); // Giriş Yap
        driver.findElement(By.xpath("//a[@id='login-tab']")).click(); // Login tab
        driver.findElement(By.xpath("//label[@class='btn btn-ghost min-h-6 w-fit min-h-6 h-fit p-0' and text()='Şifremi Unuttum']")).click(); //Şifremi unuttum
        driver.findElement(By.xpath("//input[@value='phone']")).click(); //Telefon'a Gönder Seçeneği
        driver.findElement(By.xpath("//input[@placeholder='Telefon']")).sendKeys("5538011753"); // phone
        driver.findElement(By.xpath("//button[@class='btn btn-secil mx-auto w-fit uppercase' and text()='Gönder']")).click(); //Şifre Yenileme Butonu
        Thread.sleep(5000);

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement successMessage = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//a[@id='login-tab' and text()='Giriş Yap']")));

        //WebElement successMessage = driver.findElement(By.xpath("//a[@id='login-tab' and text()='Giriş Yap']"));
        //Assert.assertTrue(successMessage.isDisplayed(),"Şifre Yenileme Linki Telefonunuza Gönderildi!");
    }


    @Test
    public void testForgotPasswordFalsePhone() throws InterruptedException {
        driver.findElement(By.xpath("//span[@class='md:hidden lg:inline whitespace-nowrap']")).click(); // Giriş Yap
        driver.findElement(By.xpath("//a[@id='login-tab']")).click(); // Login tab
        driver.findElement(By.xpath("//label[@class='btn btn-ghost min-h-6 w-fit min-h-6 h-fit p-0' and text()='Şifremi Unuttum']")).click(); //Şifremi unuttum
        driver.findElement(By.xpath("//input[@value='phone']")).click(); //Telefon'a Gönder Seçeneği
        driver.findElement(By.xpath("//input[@placeholder='Telefon']")).sendKeys("5538010000"); // phone
        driver.findElement(By.xpath("//button[@class='btn btn-secil mx-auto w-fit uppercase' and text()='Gönder']")).click(); //Şifre Yenileme Butonu
        Thread.sleep(5000);

        WebElement successMessage = driver.findElement(By.xpath("//div[@class='p-1 block text-center' and text()='Kullanıcı bulunamadı']"));
        Assert.assertTrue(successMessage.isDisplayed(),"Bu Numaraya ait bir kullanıcı bulunamadı");
    }


    @AfterClass
    public void tearDown(){
        driver.quit();
    }

}
