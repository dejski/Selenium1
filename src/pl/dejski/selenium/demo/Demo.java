package pl.dejski.selenium.demo;

import java.util.List;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

public class Demo {
	
	public static boolean isOkienko(WebDriver driver) {
		try 
	    { 
	        driver.switchTo().alert(); 
	        return true; 
	    }   // try 
	    catch (Exception Ex) 
	    { 
	        return false; 
	    }
		
	}

	public static void main(String[] args) throws InterruptedException {
		//FIREFOX
		System.setProperty("webdriver.gecko.driver",
				"c:\\PRACA\\_SELENIUM\\geckodriver.exe");
		WebDriver driver = new FirefoxDriver();
//		driver.get("http://UserName:Password@172.25.117.100/eBKW");
		//driver.get("http://UserName:Password@172.25.117.100/eBKW/O2/PrzygotowanieProjektuWpisu/PrzygotowanieProjektuWpisu.aspx");
		driver.get("http://172.25.117.100/eBKW/O2/PrzygotowanieProjektuWpisu/PrzygotowanieProjektuWpisu.aspx");
		
		
		//CHROME
//		 System.setProperty("webdriver.chrome.driver",
//		 "c:\\PRACA\\_SELENIUM\\chromedriver.exe");
//		 WebDriver driver = new ChromeDriver();
//		 driver.get("http://UserName:Password@172.25.117.100/eBKW");
		//IE
		// System.setProperty("webdriver.ie.driver",
		// "c:\\PRACA\\_SELENIUM\\IEDriverServer.exe");
		// InternetExplorerDriver driver = new InternetExplorerDriver();
		// driver.get("http://google.com");

		//Thread.sleep(200);

		driver.switchTo().alert()
				.sendKeys("NKW\\DIRS0001" + Keys.TAB + "Ekw.20170822");
		driver.switchTo().alert().accept();

		Thread.sleep(2000);
//		driver.findElement(By.id("Szukaj_acWKWWniosekSrc")).sendKeys("DIRS");
		driver.findElement(By.id("Szukaj_NrWnio")).sendKeys("0000132");
		driver.findElement(By.id("Szukaj_RokWnio")).sendKeys("18");
		
		Thread.sleep(3000);
//		// KLIKNIECIE SZUKAJ
		driver.findElement(By.id("Szukaj_Szukaj")).click();

		Thread.sleep(3000);
//		// KLIKNIECIE POBIERZ
		driver.findElement(By.id("bPobierz_KW")).click();
		
		//POTWIERDZENIE KOMUNIKATU
		Thread.sleep(3000);
		if (isOkienko(driver)) {
			driver.switchTo().alert().accept();
		}
		// driver.manage().window().maximize();
			
		//ODSWIEZ DO CZASU ZMIANY STATUSU
		Thread.sleep(3000);
		int index = 0;
		while(index<100){
			Thread.sleep(10000);
			driver.findElement(By.id("bOdswiez")).click();
			index++;
			System.out.println("ODSWIEZENIE " + index);
			WebElement baseTable2 = driver.findElement(By.id("KsiegiGrid"));
			//System.out.println(baseTable2.getText());
			if (baseTable2.getText().contains("PH - DANE POBRANE")) {
				break;
			}
		}
		System.out.println("ZMIANA STATUSU!");
//		Thread.sleep(10000);
		if (isOkienko(driver)) {
			driver.switchTo().alert().accept();
		}
		
		//ZWOLNIJ KW
		//Thread.sleep(3000);
//		driver.findElement(By.id("bZwolnij_KW")).click();
		
		//POWROT
//		Thread.sleep(3000);
		driver.findElement(By.id("Menu")).click();
		Thread.sleep(3000);
//		// KLIKNIECIE KONIEC
		List<WebElement> linksListKoniec = driver.findElements(By.xpath(".//a[contains(@href,'Koniec')]"));
		for (WebElement webElement : linksListKoniec){
		         webElement.click();
		         //System.out.println(webElement);
		}
		
		// PrzygotowanieProjektuWpisu.aspx
		//driver.get("http://172.25.117.100/eBKW/O2/PrzygotowanieProjektuWpisu/PrzygotowanieProjektuWpisu.aspx");
		
		

		Thread.sleep(5000);
		driver.close();		
		
	
	}
	
	
}
