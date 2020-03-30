package pazone.ashot.page;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;

/**
 * Wait for Javascript complete load in the page. If the page includes Angular, your load will be awaited too.
 *
 * @author <a href="guisousa09@hotmail.com">splait</a>
 */
public class JSWait {

	private WebDriver wd;
	private WebDriverWait wait;

	public JSWait(WebDriver driver, int timeoutInSeconds) {
		wd = driver;
		wait = new WebDriverWait(wd, timeoutInSeconds);
	}

	private boolean isJSReady() {
		ExpectedCondition<Boolean> jsLoad = wd -> ((JavascriptExecutor) this.wd)
				.executeScript("return document.readyState").toString().equals("complete");
		try {
			wait.pollingEvery(Duration.ofMillis(100)).until(jsLoad);
			System.out.println("Javascript ready...");
			return true;
		} catch (TimeoutException e) {
			return false;
		}
	}

	public boolean isAngularReady() {
		ExpectedCondition<Boolean> angularDefined = wd -> Boolean
				.valueOf(((JavascriptExecutor) wd).executeScript("return !(window.angular === undefined)").toString());
		try {
			wait.withTimeout(Duration.ofSeconds(5)).pollingEvery(Duration.ofMillis(100)).until(angularDefined);
			return true;
		} catch (TimeoutException angularDefinedTimeout) {
			ExpectedCondition<Boolean> angularInjectorUndefined = driver -> Boolean
					.valueOf(((JavascriptExecutor) driver)
							.executeScript("return !(angular.element(document).injector() === undefined)").toString());
			try {
				wait.withTimeout(Duration.ofSeconds(5)).pollingEvery(Duration.ofMillis(100))
						.until(angularInjectorUndefined);
				return true;
			} catch (TimeoutException angularInjectorTimeout) {
				return waitForAngularLoad();
			}
		}
	}

	private boolean waitForAngularLoad() {
		ExpectedCondition<Boolean> angularLoad = wd -> Boolean.valueOf(((JavascriptExecutor) wd)
				.executeScript("return angular.element(document).injector().get('$http').pendingRequests.length === 0")
				.toString());
		try {
			wait.pollingEvery(Duration.ofMillis(100)).until(angularLoad);
		} catch (TimeoutException e) {
			return false;
		}
		return true;
	}

	public boolean isPageLoaded() {
		return PageUtil.includesAngular(this.wd) ? isJSReady() && isAngularReady() : isJSReady();
	}

	/**
	 * Method to make sure a specific element has loaded on the page
	 *
	 * @param by
	 * @param expected
	 */
	public void waitForElementAreComplete(By by, int expected) {
		ExpectedCondition<Boolean> angularLoad = wd -> {
			int loadingElements = this.wd.findElements(by).size();
			return loadingElements >= expected;
		};
		wait.until(angularLoad);
	}

	/**
	 * Waits for the elements animation to be completed
	 *
	 * @param css
	 */
	public void waitForAnimationToComplete(String css) {
		ExpectedCondition<Boolean> angularLoad = wd -> {
			int loadingElements = this.wd.findElements(By.cssSelector(css)).size();
			return loadingElements == 0;
		};
		wait.until(angularLoad);
	}
}