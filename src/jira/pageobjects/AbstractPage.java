package jira.pageobjects;

import org.openqa.selenium.WebDriver;

public class AbstractPage {

	protected WebDriver idriver;

	public AbstractPage(WebDriver idriver) {
		this.idriver = idriver;
	}

	public WebDriver getDriver() {
		return idriver;
	}

	public HomePage navigateToHomePage() {
		idriver.navigate().to("https://jira.atlassian.com/browse/TST");
		return new HomePage(idriver);
	}
	
	public LoginPage navigateToLoginPage() {
		idriver.navigate().to("https://id.atlassian.com/login");
		return new LoginPage(idriver);
	}

}
