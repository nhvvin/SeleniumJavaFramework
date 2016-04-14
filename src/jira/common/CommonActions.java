package jira.common;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class CommonActions {

	public static CommonActions instance = null;

	public CommonActions() {
	}

	public static CommonActions getInstance() {
		if (instance == null) {
			instance = new CommonActions();
		}
		return instance;
	}

	public void enterTextboxValue(WebElement webElement, String strValue) {
		try {
			webElement.clear();
			webElement.sendKeys(strValue);
			webElement.sendKeys(Keys.TAB);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	public void waitForElementExist(WebDriver idriver, By webElementProperty, int timeOut) {
		(new WebDriverWait(idriver, timeOut)).until(ExpectedConditions.presenceOfElementLocated(webElementProperty));
	}

	public void waitForElementNotExist(WebDriver idriver, By webElementProperty, int timeOut) {
		(new WebDriverWait(idriver, timeOut)).until(ExpectedConditions.invisibilityOfElementLocated(webElementProperty));
	}

	public void waitForElementClickable(WebDriver idriver, By webElementProperty, int timeOut) {
		(new WebDriverWait(idriver, timeOut)).until(ExpectedConditions.elementToBeClickable(webElementProperty));
	}

	public void waitForTitleContain(WebDriver idriver, String strTitle, int timeOut) {
		(new WebDriverWait(idriver, timeOut)).until(ExpectedConditions.titleContains(strTitle));
	}

	public void handleError(WebDriver idriver) {
		try {
			String scrFolder = System.getenv("HOMEDRIVE") + "/temp/"
					+ new SimpleDateFormat("yyyy_MM_dd").format(Calendar.getInstance().getTime()).toString();
			new File(scrFolder).mkdir();
			File scrFile = ((TakesScreenshot) idriver).getScreenshotAs(OutputType.FILE);
			FileUtils.copyFile(scrFile,
					new File(scrFolder + "/Error-" + new SimpleDateFormat("HH-mm-ss").format(Calendar.getInstance().getTime()).toString()
							+ ".png"));
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
}
