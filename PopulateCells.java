package extra06nam;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;



public class PopulateCells {
	
	static ArrayList<String> list = null;
	static WebDriver drive = null;
	static String fileName = "";
	
	public static void main(String[] args) throws InterruptedException {
		populateSet();
		bootUp();
		populateNameTxt();
	}


	private static void populateSet() {
		list = new ArrayList<String>();
		File file = new File(fileName);
		try (Scanner scan = new Scanner(file)) {
			while (scan.hasNextLine()) {
				list.add(scan.nextLine());
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	private static void bootUp() throws InterruptedException {
		String os = System.getProperty("os.name").toLowerCase();
		if (os.contains("mac")) {
			System.setProperty("webdriver.chrome.driver",
					System.getProperty("user.dir") + "/src/extra03catering/chromedriver");
		} else {
			System.setProperty("webdriver.chrome.driver",
					System.getProperty("user.dir") + "\\src\\extra03catering\\chromedriver.exe");
		}
		ChromeOptions options = new ChromeOptions();
	    options.addArguments("--headless", "--disable-gpu", "--window-size=1920,1200","--ignore-certificate-errors", "--silent");
	    
		drive = new ChromeDriver(options);
		drive.get("https://www.uniprot.org/");
	}
	
	private static void populateNameTxt() throws InterruptedException {
		try (BufferedWriter write = new BufferedWriter(new FileWriter("src/extra06nam/mass.txt", true))) 
		{
			int i = 1;
			for (String s : list) 
			{
				drive.get("https://www.uniprot.org/uniprot/" + s);
				write.write(	drive.findElement(By.xpath("//*[@id=\"sequences-section\"]/div[1]/div[2]/div[2]/span[2]")).getText());
				write.newLine();
				write.flush();
				System.out.println(i + " :proteinID: " + s + " complete.");
				i++;
			}		
		}	
		catch (IOException e) {
			e.printStackTrace();
		}	
		Thread.sleep(2000);
		System.out.println("DONE!");

	}
	
}
