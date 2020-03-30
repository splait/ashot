package pazone.ashot.page;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;

/**
 * Some expected conditions to web pages
 *
 * @author <a href="guisousa09@hotmail.com">splait</a>
 */
public final class PageExpectedConditions {

	private PageExpectedConditions() {
	}

	public static ExpectedCondition<Boolean> visibilityOfElement(WebElement element) {

		return new ExpectedCondition<Boolean>() {
			@Override
			public Boolean apply(WebDriver wd) {
				return PageUtil.isElementVisible(wd, element);
			}

			@Override
			public String toString() {
				return "visibility of " + element;
			}
		};
	}

	public static ExpectedCondition<Boolean> invisibilityOfElement(WebElement element) {

		return new ExpectedCondition<Boolean>() {
			@Override
			public Boolean apply(WebDriver wd) {
				return !PageUtil.isElementVisible(wd, element);
			}

			@Override
			public String toString() {
				return "invisibility of " + element;
			}
		};
	}

	/**
	 * Waits for the page load. Default timeout is 20 seconds
	 * 
	 * @return <b>true</b> if the page loaded completely
	 */
	public static ExpectedCondition<Boolean> pageLoads() {

		return new ExpectedCondition<Boolean>() {
			@Override
			public Boolean apply(WebDriver webDriver) {
				JSWait wait = new JSWait(webDriver, 20);
				return wait.isPageLoaded();
			}
		};
	}

	/**
	 * Waits for the page load.
	 * @param timeoutInSeconds Maximum waiting time for page loading
	 * @return <b>true</b> if the page loaded completely
	 */
	public static ExpectedCondition<Boolean> pageLoads(int timeoutInSeconds) {

		return new ExpectedCondition<Boolean>() {
			@Override
			public Boolean apply(WebDriver webDriver) {
				JSWait wait = new JSWait(webDriver, timeoutInSeconds);
				return wait.isPageLoaded();
			}
		};
	}
}
