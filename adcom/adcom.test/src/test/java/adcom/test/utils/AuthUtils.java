package adcom.test.utils;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class AuthUtils {
	
	public static void login(WebDriver driver,String baseUrl,String username ,String password){
		driver.get(baseUrl + "/adlogin.client/#/login");
		driver.findElement(By.id("username")).clear();
		driver.findElement(By.id("username")).sendKeys(username);
		RandomMilisec.doWait(Long.valueOf(1000));
		driver.findElement(By.id("password")).clear();
		driver.findElement(By.id("password")).sendKeys(password);
		RandomMilisec.doWait(Long.valueOf(1000));
		driver.findElement(By.id("login")).click();
	}
	
	
	public static void logout(WebDriver driver){
		RandomMilisec.doWait(Long.valueOf(300));
		driver.findElement(By.id("logout")).click();
	}

}
