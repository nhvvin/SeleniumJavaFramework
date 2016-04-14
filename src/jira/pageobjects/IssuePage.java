package jira.pageobjects;

import jira.common.CommonActions;

import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class IssuePage extends HomePage {

	public IssuePage(WebDriver idriver) {
		super(idriver);
	}

	public By btnEditIssueSummary = By.xpath("//h1[@id='summary-val']/span[1]");
	public By btnEditIssueType = By.xpath("//span[@id='type-val']/span[1]");
	public By btnEditIssuePriority = By.xpath("//span[@id='priority-val']/span[1]");
	public By btnSubmit = By.xpath("//button[@class='aui-button submit']");
	public By lnkIssueID = By.xpath("//a[@id='key-val']");
	public By spnIssueType = By.xpath("//span[@id='type-val']");
	public By hdIssueSummary = By.xpath("//h1[@id='summary-val']");

	public IssuePage editIssue(String strIssueID, String strProjectName, IssueTypes issueType, String strSummary) {
		try {
			// Edit Issue type
			if (issueType != null) {
				idriver.findElement(btnEditIssueType).click();
				CommonActions.getInstance().waitForElementExist(idriver, txtIssueType, 10);
				CommonActions.getInstance().enterTextboxValue(idriver.findElement(txtIssueType), issueType.toString());
				CommonActions.getInstance().waitForElementExist(idriver, btnSubmit, 10);
				idriver.findElement(btnSubmit).click();
				CommonActions.getInstance().waitForElementExist(idriver, btnEditIssueType, 10);
			}

			// Edit Issue summary
			if (strSummary != null) {
				idriver.findElement(btnEditIssueSummary).click();
				CommonActions.getInstance().enterTextboxValue(idriver.findElement(txtSummary), strSummary);
				CommonActions.getInstance().waitForElementExist(idriver, btnSubmit, 10);
				idriver.findElement(btnSubmit).click();
				CommonActions.getInstance().waitForElementExist(idriver, btnEditIssueSummary, 10);
			}

		} catch (Exception ex) {
			ex.printStackTrace();
			CommonActions.getInstance().handleError(idriver);
		}
		return new IssuePage(idriver);
	}

	public IssuePage checkIssueOpened(String strIssueIDExpected) {
		try {
			String strIssueIDActual = idriver.findElement(lnkIssueID).getText();
			Assert.assertEquals(strIssueIDExpected, strIssueIDActual);
			System.out.println("Passed - Expected:" + strIssueIDExpected + " - Actual:" + strIssueIDActual);
		} catch (Throwable ex) {
			ex.printStackTrace();
			CommonActions.getInstance().handleError(idriver);
		}
		return new IssuePage(idriver);
	}
	
	public IssuePage checkIssueUpdated(IssueTypes issueType, String strSummary) {
		try {
			if (issueType != null){
				Assert.assertEquals(issueType.toString(), idriver.findElement(spnIssueType).getText());
			}
			
			if (strSummary != null){
				Assert.assertEquals(strSummary, idriver.findElement(hdIssueSummary).getText());
			}
			System.out.println("Passed - Issue was updated");
		} catch (Throwable ex) {
			ex.printStackTrace();
			CommonActions.getInstance().handleError(idriver);
		}
		return new IssuePage(idriver);
	}
}
