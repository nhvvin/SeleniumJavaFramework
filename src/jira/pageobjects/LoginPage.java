package jira.pageobjects;

import jira.common.CommonActions;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class LoginPage extends AbstractPage {

	public LoginPage(WebDriver idriver) {
		super(idriver);
	}

	public By txtUserName = By.id("username");
	public By txtPassword = By.id("password");
	public By btnLogin = By.id("login-submit");

	public HomePage loginToProject(String strUsername, String strPassword) {
		try {
			CommonActions.getInstance().enterTextboxValue(idriver.findElement(txtUserName), strUsername);
			CommonActions.getInstance().enterTextboxValue(idriver.findElement(txtPassword), strPassword);

			idriver.findElement(btnLogin).click();
		} catch (Exception ex) {
			ex.printStackTrace();
			CommonActions.getInstance().handleError(idriver);
		}
		return new HomePage(idriver);
	}
}
