package pazone.ashot.page;

import java.util.List;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

/**
 * Some utility methods to manipulate the page and its elements
 *
 * @author <a href="guisousa09@hotmail.com">splait</a>
 */
public final class PageUtil {

	private PageUtil() {
	}

	public static boolean includesAngular(WebDriver wd) {
		String angularTags = ".ng-binding, [ng-app], [data-ng-app], [ng-controller], [data-ng-controller], [ng-repeat], [data-ng-repeat]";
		String angularSources = "script[src*=\"angular.js\"], script[src*=\"angular.min.js\"]";
		return (boolean) ((JavascriptExecutor) wd).executeScript(
				"return !!(document.querySelector(arguments[0]) || document.querySelector(arguments[1]));", angularTags,
				angularSources);
	}

	public static boolean elementExists(WebDriver wd, String cssSelector) {
		return ((JavascriptExecutor) wd).executeScript("return document.querySelector(arguments[0])",
				cssSelector) != null;
	}

	public static Boolean isElementVisible(WebDriver wd, WebElement element) {
		return (Boolean) ((JavascriptExecutor) wd).executeScript(
				"return getComputedStyle(arguments[0], null).getPropertyValue('visibility') === 'visible'", element);
	}

	@SuppressWarnings("unchecked")
	public static List<WebElement> getInnerElements(WebDriver wd, WebElement element) {
		return (List<WebElement>) ((JavascriptExecutor) wd).executeScript("return Array.from(arguments[0].children)",
				element);
	}

	public static void setElementVisibilityProperty(WebDriver wd, WebElement element, String visibility) {
		((JavascriptExecutor) wd).executeScript("arguments[0].style.setProperty('visibility', arguments[1])", element,
				visibility);
	}

	public static String getElementVisibilityProperty(WebDriver wd, WebElement element) {
		return (String) ((JavascriptExecutor) wd)
				.executeScript("return getComputedStyle(arguments[0], null).getPropertyValue('visibility')", element);
	}

	public static void setElementPositionProperty(WebDriver wd, WebElement element, String position) {
		((JavascriptExecutor) wd).executeScript("arguments[0].style.setProperty('position', arguments[1])", element,
				position);
	}

	public static String getElementPositionProperty(WebDriver wd, WebElement element) {
		return (String) ((JavascriptExecutor) wd)
				.executeScript("return getComputedStyle(arguments[0], null).getPropertyValue('position')", element);
	}

	@SuppressWarnings("unchecked")
	public static List<WebElement> getAllFixedElements(WebDriver wd) {
		return (List<WebElement>) ((JavascriptExecutor) wd).executeScript(
				"return Array.from(document.body.getElementsByTagName('*')).filter( x => getComputedStyle(x, null).getPropertyValue('position') === 'fixed' && getComputedStyle(x, null).getPropertyValue('visibility') === 'visible')");
	}
}
