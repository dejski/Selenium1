package pl.dejski.selenium.duzeKW;

import java.io.FileInputStream;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
//import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
//import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

public class LadujDuzaKW {

	public static boolean isOkienko(WebDriver driver) {
		try {
			driver.switchTo().alert();
			return true;
		} // try
		catch (Exception Ex) {
			return false;
		}
	}

	public static void main(String[] args) throws InterruptedException,
			IOException {

		DateTimeFormatter dtf = DateTimeFormatter
				.ofPattern("yyyy/MM/dd HH:mm:ss");
		LocalDateTime dStart = LocalDateTime.now();
		long startTime;
	    long endTime;

		Properties prop = new Properties();
		InputStream input = null;
		int lPowtorzen = 0;
		startTime = System.currentTimeMillis();

		try {
			input = new FileInputStream("config.properties");
			prop.load(input);
			System.out.println("START: " + dtf.format(dStart) + "Properties:" + prop.getProperty("nrKW") + "|"
					+ prop.getProperty("rokKW") + "|"
					+ prop.getProperty("login"));

			while (lPowtorzen < Integer
					.parseInt(prop.getProperty("lPowtorzen"))) {
				lPowtorzen++;
				System.out.println("POWTORZENIE " + lPowtorzen);

				// input = new FileInputStream(args[0]);

				// FIREFOX
				System.setProperty("webdriver.gecko.driver",
						prop.getProperty("geckodriver"));
				WebDriver driver = new FirefoxDriver();
				// driver.get("http://UserName:Password@172.25.117.100/eBKW");
				// driver.get("http://UserName:Password@172.25.117.100/eBKW/O2/PrzygotowanieProjektuWpisu/PrzygotowanieProjektuWpisu.aspx");
				driver.get(prop.getProperty("url")
						+ "/eBKW/O2/PrzygotowanieProjektuWpisu/PrzygotowanieProjektuWpisu.aspx");

				// CHROME
				// System.setProperty("webdriver.chrome.driver",
				// "c:\\PRACA\\_SELENIUM\\chromedriver.exe");
				// WebDriver driver = new ChromeDriver();
				// driver.get("http://UserName:Password@172.25.117.100/eBKW");
				// IE
				// System.setProperty("webdriver.ie.driver",
				// "c:\\PRACA\\_SELENIUM\\IEDriverServer.exe");
				// InternetExplorerDriver driver = new InternetExplorerDriver();
				// driver.get("http://google.com");
				Thread.sleep(2000);

				driver.switchTo()
						.alert()
						.sendKeys(
								prop.getProperty("login") + Keys.TAB
										+ prop.getProperty("password"));
				driver.switchTo().alert().accept();

				Thread.sleep(3000);
				// driver.findElement(By.id("Szukaj_acWKWWniosekSrc")).sendKeys("DIRS");
				driver.findElement(By.id("Szukaj_NrWnio")).sendKeys(
						prop.getProperty("nrKW"));
				driver.findElement(By.id("Szukaj_RokWnio")).sendKeys(
						prop.getProperty("rokKW"));

				Thread.sleep(1000);
				// // KLIKNIECIE SZUKAJ
				driver.findElement(By.id("Szukaj_Szukaj")).click();

				Thread.sleep(1000);
				// // KLIKNIECIE POBIERZ
				driver.findElement(By.id("bPobierz_KW")).click();

				// POTWIERDZENIE KOMUNIKATU
				Thread.sleep(3000);
				if (isOkienko(driver)) {
					driver.switchTo().alert().accept();
				}
				// driver.manage().window().maximize();

				// ODSWIEZ DO CZASU ZMIANY STATUSU
				Thread.sleep(3000);
				int index = 0;
				while (true) {
					Thread.sleep(10000);
					driver.findElement(By.id("bOdswiez")).click();
					index++;
					System.out.println("ODSWIEZENIE " + index);
					WebElement baseTable2 = driver.findElement(By
							.id("KsiegiGrid"));
					System.out.println(baseTable2.getText());
					if (baseTable2.getText().contains("PH - DANE POBRANE")) {
						break;
					}
				}
				System.out.println("ZMIANA STATUSU!");
				// Thread.sleep(10000);
				if (isOkienko(driver)) {
					driver.switchTo().alert().accept();
				}

				// ZWOLNIJ KW
				// Thread.sleep(3000);
				// driver.findElement(By.id("bZwolnij_KW")).click();

				// POWROT
				// Thread.sleep(3000);
				driver.findElement(By.id("Menu")).click();
				Thread.sleep(1000);
				// // KLIKNIECIE KONIEC
				List<WebElement> linksListKoniec = driver.findElements(By
						.xpath(".//a[contains(@href,'Koniec')]"));
				for (WebElement webElement : linksListKoniec) {
					webElement.click();
					// System.out.println(webElement);
				}

				// PrzygotowanieProjektuWpisu.aspx
				// driver.get("http://172.25.117.100/eBKW/O2/PrzygotowanieProjektuWpisu/PrzygotowanieProjektuWpisu.aspx");

				Thread.sleep(1000);
				driver.close();

			}

		} catch (IOException ex) {
			ex.printStackTrace();
		} finally {
			if (input != null) {
				try {
					input.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		
		LocalDateTime dEnd = LocalDateTime.now();
		endTime = System.currentTimeMillis();
		long czasTrwania = (endTime - startTime)/1000;
		System.out.println("KONIEC: " + dtf.format(dEnd)+ " CZAS TRWANIA [sek]: " +czasTrwania);
	}
}
