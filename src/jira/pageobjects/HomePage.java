package jira.pageobjects;

import jira.common.CommonActions;

import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;

public class HomePage extends AbstractPage {

	public HomePage(WebDriver idriver) {
		super(idriver);
	}

	public By lnkCreate = By.id("create_link");
	public By dlgCreateIssue = By.id("create-issue-dialog");
	public By txtProjectName = By.id("project-field");
	public By lnkLoginPage = By.xpath("//a[@class='aui-nav-link login-link']");
	public By txtIssueType = By.id("issuetype-field");
	public By txtSummary = By.id("summary");
	public By txtPriority = By.id("priority-field");
	public By btnCreateIssue = By.id("create-issue-submit");
	public By msgGlobal = By.className("global-msg");
	public By txtQuickSearch = By.id("quickSearchInput");

	public String strGlobalMsg;

	public enum IssueTypes {
		New_Feature("New Feature"), Bug("Bug"), Improvement("Improvement"), Support_Request("Support Request"), Task("Task"), Third_party_issue(
				"Third-party issue");

		private String issue;

		private IssueTypes(String s) {
			issue = s;
		}

		public String getText() {
			return this.issue;
		}

		public boolean equalsName(String otherName) {
			return (otherName == null) ? false : issue.equals(otherName);
		}

		public String toString() {
			return issue;
		}
	}

	public enum Priority {
		Minor("Minor"), Blocker("Blocker"), Major("Major"), Critical("Critical"), Trivial("Trivial");

		private String issue;

		private Priority(String s) {
			issue = s;
		}

		public String getText() {
			return this.issue;
		}

		public boolean equalsName(String otherName) {
			return (otherName == null) ? false : issue.equals(otherName);
		}

		public String toString() {
			return issue;
		}
	}

	public LoginPage navigateToLoginPage() {
		try {
			// Click on Login button
			idriver.findElement(lnkLoginPage).click();
			CommonActions.getInstance().waitForElementExist(idriver, By.id("login-submit"), 10);
		} catch (Exception ex) {
			ex.printStackTrace();
			CommonActions.getInstance().handleError(idriver);
		}
		return new LoginPage(idriver);
	}

	public HomePage createIssue(String strProjectName, IssueTypes issueType, String strSummary, Priority priority) {
		try {
			idriver.findElement(lnkCreate).click();

			// Wait for Element dialog display
			CommonActions.getInstance().waitForElementExist(idriver, dlgCreateIssue, 20);

			// Enter information of issue
			if (strProjectName != null) {
				CommonActions.getInstance().waitForElementExist(idriver, txtProjectName, 20);
				CommonActions.getInstance().waitForElementClickable(idriver, txtProjectName, 10);
				CommonActions.getInstance().enterTextboxValue(idriver.findElement(txtProjectName), strProjectName);
			}

			if (issueType != null) {
				CommonActions.getInstance().waitForElementExist(idriver, txtIssueType, 20);
				CommonActions.getInstance().waitForElementClickable(idriver, txtIssueType, 10);
				CommonActions.getInstance().enterTextboxValue(idriver.findElement(txtIssueType), issueType.toString());
			}

			if (strSummary != null) {
				CommonActions.getInstance().waitForElementExist(idriver, txtSummary, 20);
				CommonActions.getInstance().waitForElementClickable(idriver, txtSummary, 10);
				CommonActions.getInstance().enterTextboxValue(idriver.findElement(txtSummary), strSummary);
			}

			if (priority != null) {
				CommonActions.getInstance().waitForElementExist(idriver, txtPriority, 20);
				CommonActions.getInstance().waitForElementClickable(idriver, txtPriority, 10);
				CommonActions.getInstance().enterTextboxValue(idriver.findElement(txtPriority), priority.toString());
			}

			// Click on Create button
			idriver.findElement(btnCreateIssue).click();
			CommonActions.getInstance().waitForElementNotExist(idriver, dlgCreateIssue, 60);
			CommonActions.getInstance().waitForElementExist(idriver, msgGlobal, 20);

			// Get Global Message text
			strGlobalMsg = idriver.findElement(msgGlobal).getText();
		} catch (Exception ex) {
			ex.printStackTrace();
			CommonActions.getInstance().handleError(idriver);
		}
		return new HomePage(idriver);
	}

	public IssuePage openIssue(String strIssueID) {
		try {
			CommonActions.getInstance().waitForElementExist(idriver, txtQuickSearch, 20);

			// Enter information of issue
			CommonActions.getInstance().enterTextboxValue(idriver.findElement(txtQuickSearch), strIssueID);
			idriver.findElement(txtQuickSearch).sendKeys(Keys.ENTER);

			// Wait for issue load
			CommonActions.getInstance().waitForTitleContain(idriver, strIssueID, 30);
		} catch (Exception ex) {
			ex.printStackTrace();
			CommonActions.getInstance().handleError(idriver);
		}
		return new IssuePage(idriver);
	}

	public IssuePage checkIssueCreated(String strSummary) {
		try {
			Assert.assertTrue("Cannot create Issue", strGlobalMsg.contains(strSummary + " has been successfully created"));
			System.out.println("Passed - New Issue was created");
		} catch (Throwable ex) {
			ex.printStackTrace();
			CommonActions.getInstance().handleError(idriver);
		}
		return new IssuePage(idriver);
	}
}
