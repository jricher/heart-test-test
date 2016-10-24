package io.bspk.testframework.strawman;

import java.awt.BorderLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.machinepublishers.jbrowserdriver.JBrowserDriver;

public class Selenium2Example  {
    public static void main(String[] args) throws Exception {
        // Create a new instance of the Firefox driver
        // Notice that the remainder of the code relies on the interface, 
        // not the implementation.
        final WebDriver driver = new JBrowserDriver();

        // And now use this to visit Google
        driver.get("https://reddit.com/");
        // Alternatively the same thing can be done like this
        // driver.navigate().to("http://www.google.com");

        // Find the text input element by its name
//        WebElement element = driver.findElement(By.name("q"));

        // Enter something to search for
//        element.sendKeys("Cheese!");

        // Now submit the form. WebDriver will find the form for us from the element
//        element.submit();

        driver.manage().window().maximize();
        
        JFrame frame = new JFrame("Screenshot");
        
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        // Check the title of the page
        System.out.println("Page title is: " + driver.getTitle());
        
        File scrFile = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
        
        System.out.println("Wrote screenshot to: " + scrFile.getAbsolutePath());

//        FileUtils.copyFile(scrFile, new File("/tmp/screenshot.png"));
        
        
        final JLabel label = new JLabel(new ImageIcon(ImageIO.read(scrFile)));
        
        frame.getContentPane().add(label, BorderLayout.CENTER);
        
        frame.pack();
        
        frame.show();

        label.addMouseListener(new MouseAdapter() {

			/* (non-Javadoc)
			 * @see java.awt.event.MouseAdapter#mouseClicked(java.awt.event.MouseEvent)
			 */
			@Override
			public void mouseClicked(MouseEvent e) {
				System.out.println("Clicked at " + e.getX() + "," + e.getY());
				
		        Actions builder = new Actions(driver);
		        builder.moveByOffset(e.getX(), e.getY());
		        builder.click();
		        builder.moveByOffset(-e.getX(), -e.getY());
		        
		        builder.build().perform();
		        
		        try {
					Thread.sleep(500);
				} catch (InterruptedException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
		        
		        System.out.println("Page title is: " + driver.getTitle());
		        
		        File scrFile = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
		        
		        System.out.println("Wrote screenshot to: " + scrFile.getAbsolutePath());

		        try {
					label.setIcon(new ImageIcon(ImageIO.read(scrFile)));
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
		        
			}
        	
		});
        
        
        //Close the browser
        //driver.quit();
    }
}
