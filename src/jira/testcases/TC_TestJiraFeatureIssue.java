package jira.testcases;

import jira.pageobjects.*;
import jira.pageobjects.HomePage.IssueTypes;

import org.junit.*;
import org.junit.rules.*;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.*;

public class TC_TestJiraFeatureIssue {

	WebDriver idriver;

	// Identify parameter for test cases below
	private String strUsername = "nhv.vin@gmail.com";
	private String strPassword = "p@ssw0rd";
	private String strProjectName = "A Test Project (TST)";
	private String strSummary = "This is just testing create issue function";

	public String strIssueID = "TST-60614";

	@Rule
	public final TestName testName = new TestName();

	@Before
	public void launchFireFox() {
		idriver = new FirefoxDriver();
		idriver.manage().deleteAllCookies();

		// This use to modify wait time for the browser
		// idriver.manage().timeouts().implicitlyWait(60,
		// java.util.concurrent.TimeUnit.SECONDS);
	}

	@After
	public void cleanUp() {
		idriver.manage().deleteAllCookies();
		idriver.quit();
		String className = this.getClass().getSimpleName();
		String methodName = this.testName.getMethodName();
		System.err.println("Finished test " + className + "." + methodName + "()");
	}

	@Test
	public void TC_001_Verify_New_issues_can_be_created() {
		HomePage runHomePage = new HomePage(idriver);
		runHomePage.navigateToHomePage();
		LoginPage runLoginPage = runHomePage.navigateToLoginPage();

		runHomePage = runLoginPage.loginToProject(strUsername, strPassword);

		runHomePage.createIssue(strProjectName, IssueTypes.New_Feature, strSummary, null);
		runHomePage.checkIssueCreated(strSummary);
	}

	@Test
	public void TC_002_Verify_Existing_issues_can_be_updated() {
		HomePage runHomePage = new HomePage(idriver);
		runHomePage.navigateToHomePage();
		LoginPage runLoginPage = runHomePage.navigateToLoginPage();

		runHomePage = runLoginPage.loginToProject(strUsername, strPassword);

		IssuePage runIssuePage = runHomePage.openIssue(strIssueID);
		runIssuePage.editIssue(strIssueID, strProjectName, IssueTypes.Bug, strSummary + " Edited");
		runIssuePage.checkIssueUpdated(IssueTypes.Bug, strSummary + " Edited");
	}

	@Test
	public void TC_003_Verify_Existing_issues_can_be_found_via_JIRA_search() {
		HomePage runHomePage = new HomePage(idriver);
		runHomePage.navigateToHomePage();
		LoginPage runLoginPage = runHomePage.navigateToLoginPage();

		runHomePage = runLoginPage.loginToProject(strUsername, strPassword);

		IssuePage runIssuePage = runHomePage.openIssue(strIssueID);
		runIssuePage.checkIssueOpened(strIssueID);
	}
}
