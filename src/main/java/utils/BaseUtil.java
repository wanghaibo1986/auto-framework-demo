package utils;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.internal.FindsByClassName;
import org.openqa.selenium.internal.FindsByCssSelector;
import org.openqa.selenium.internal.FindsById;
import org.openqa.selenium.internal.FindsByLinkText;
import org.openqa.selenium.internal.FindsByName;
import org.openqa.selenium.internal.FindsByTagName;
import org.openqa.selenium.internal.FindsByXPath;
import org.openqa.selenium.support.ui.Select;

import java.io.Serializable;
import java.util.List;

/**

 */
public class BaseUtil extends By {
	
  private static WebDriver driver;
  
  public static WebDriver setDriver(WebDriver driver) {
	  return BaseUtil.driver = driver;
  }
  
  public static WebDriver getDriver() {
	  return driver;
  }
  
  public WebElement findElement() {
	  return getDriver().findElement(this);
  }

  public List<WebElement> findElements() {
	  return getDriver().findElements(this);
  }

	public void click() {
		findElement().click();
	}

	public void input(String value) {
		findElement().clear();
		findElement().sendKeys(value);
	}
	
	public void check() {
		WebElement ele = findElement();
		if(!ele.isSelected()) {
			ele.click();
		}
	}
	
	public void uncheck() {
		WebElement ele = findElement();
		if(ele.isSelected()) {
			ele.click();
		}
	}
	
	public String getAttribute(String attr){
		return findElement().getAttribute(attr);
	}
	
	public String getText() {
		return findElement().getText();
	}
	
	public Boolean isDisplayed() {
		if (findElements().size()==0) {
			return false;
		} else {
			return findElement().isDisplayed();
		}
	}
  
	public Select selectByVisibleText(String text) {
		WebElement ele = findElement();
		Select sele = new Select(ele);
		sele.selectByVisibleText(text);
		return sele;
	}
	
	// blow code copied from By, but changed "By" to "BaseUtil" and implemented abstract methods
  
  /**
   * @param id The value of the "id" attribute to search for
   * @return a By which locates elements by the value of the "id" attribute.
   */
  public static BaseUtil id(final String id) {
    if (id == null)
      throw new IllegalArgumentException(
          "Cannot find elements with a null id attribute.");

    return new ById(id);
  }

  /**
   * @param linkText The exact text to match against
   * @return a By which locates A elements by the exact text it displays
   */
  public static BaseUtil linkText(final String linkText) {
    if (linkText == null)
      throw new IllegalArgumentException(
          "Cannot find elements when link text is null.");

    return new ByLinkText(linkText);
  }

  /**
   * @param linkText The text to match against
   * @return a By which locates A elements that contain the given link text
   */
  public static BaseUtil partialLinkText(final String linkText) {
    if (linkText == null)
      throw new IllegalArgumentException(
          "Cannot find elements when link text is null.");

    return new ByPartialLinkText(linkText);
  }

  /**
   * @param name The value of the "name" attribute to search for
   * @return a By which locates elements by the value of the "name" attribute.
   */
  public static BaseUtil name(final String name) {
    if (name == null)
      throw new IllegalArgumentException(
          "Cannot find elements when name text is null.");

    return new ByName(name);
  }

  /**
   * @param name The element's tagName
   * @return a By which locates elements by their tag name
   */
  public static BaseUtil tagName(final String name) {
    if (name == null)
      throw new IllegalArgumentException(
          "Cannot find elements when name tag name is null.");

    return new ByTagName(name);
  }

  /**
   * @param xpathExpression The xpath to use
   * @return a By which locates elements via XPath
   */
  public static BaseUtil xpath(final String xpathExpression) {
    if (xpathExpression == null)
      throw new IllegalArgumentException(
          "Cannot find elements when the XPath expression is null.");

    return new ByXPath(xpathExpression);
  }

  /**
   * Finds elements based on the value of the "class" attribute. If an element has many classes then
   * this will match against each of them. For example if the value is "one two onone", then the
   * following "className"s will match: "one" and "two"
   * 
   * @param className The value of the "class" attribute to search for
   * @return a By which locates elements by the value of the "class" attribute.
   */
  public static BaseUtil className(final String className) {
    if (className == null)
      throw new IllegalArgumentException(
          "Cannot find elements when the class name expression is null.");

    return new ByClassName(className);
  }

  /**
   * Finds elements via the driver's underlying W3 Selector engine. If the browser does not
   * implement the Selector API, a best effort is made to emulate the API. In this case, we strive
   * for at least CSS2 support, but offer no guarantees.
   */
  public static BaseUtil cssSelector(final String selector) {
    if (selector == null)
      throw new IllegalArgumentException(
          "Cannot find elements when the selector is null");

    return new ByCssSelector(selector);

  }

  /**
   * Find a single element.
   * 
   * @param context A context to use to find the element
   * @return The WebElement that matches the selector
   */
  public WebElement findElement(SearchContext context) {
    List<WebElement> allElements = findElements(context);
    if (allElements == null || allElements.isEmpty())
      throw new NoSuchElementException("Cannot locate an element using "
          + toString());
    return allElements.get(0);
  }

  /**
   * Find many elements.
   * 
   * @param context A context to use to find the element
   * @return A list of WebElements matching the selector
   */
  public List<WebElement> findElements(SearchContext context) {
	  List<WebElement> allElements = findElements(context);
	    if (allElements == null || allElements.isEmpty())
	      throw new NoSuchElementException("Cannot locate an element using "
	          + toString());
	    return allElements;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o)
      return true;
    if (o == null || getClass() != o.getClass())
      return false;

    By by = (By) o;

    return toString().equals(by.toString());
  }

  @Override
  public int hashCode() {
    return toString().hashCode();
  }

  @Override
  public String toString() {
    // A stub to prevent endless recursion in hashCode()
    return "[unknown locator]";
  }

  public static class ById extends BaseUtil implements Serializable {

    private static final long serialVersionUID = 5341968046120372169L;

    private final String id;

    public ById(String id) {
      this.id = id;
    }

    @Override
    public List<WebElement> findElements(SearchContext context) {
      if (context instanceof FindsById)
        return ((FindsById) context).findElementsById(id);
      return ((FindsByXPath) context).findElementsByXPath(".//*[@id = '" + id
          + "']");
    }

    @Override
    public WebElement findElement(SearchContext context) {
      if (context instanceof FindsById)
        return ((FindsById) context).findElementById(id);
      return ((FindsByXPath) context).findElementByXPath(".//*[@id = '" + id
          + "']");
    }

    @Override
    public String toString() {
      return "By.id: " + id;
    }
  }

  public static class ByLinkText extends BaseUtil implements Serializable {

    private static final long serialVersionUID = 1967414585359739708L;

    private final String linkText;

    public ByLinkText(String linkText) {
      this.linkText = linkText;
    }

    @Override
    public List<WebElement> findElements(SearchContext context) {
      return ((FindsByLinkText) context).findElementsByLinkText(linkText);
    }

    @Override
    public WebElement findElement(SearchContext context) {
      return ((FindsByLinkText) context).findElementByLinkText(linkText);
    }

    @Override
    public String toString() {
      return "By.linkText: " + linkText;
    }
  }

  public static class ByPartialLinkText extends BaseUtil implements Serializable {

    private static final long serialVersionUID = 1163955344140679054L;

    private final String linkText;

    public ByPartialLinkText(String linkText) {
      this.linkText = linkText;
    }

    @Override
    public List<WebElement> findElements(SearchContext context) {
      return ((FindsByLinkText) context)
          .findElementsByPartialLinkText(linkText);
    }

    @Override
    public WebElement findElement(SearchContext context) {
      return ((FindsByLinkText) context).findElementByPartialLinkText(linkText);
    }

    @Override
    public String toString() {
      return "By.partialLinkText: " + linkText;
    }
  }

  public static class ByName extends BaseUtil implements Serializable {

    private static final long serialVersionUID = 376317282960469555L;

    private final String name;

    public ByName(String name) {
      this.name = name;
    }

    @Override
    public List<WebElement> findElements(SearchContext context) {
      if (context instanceof FindsByName)
        return ((FindsByName) context).findElementsByName(name);
      return ((FindsByXPath) context).findElementsByXPath(".//*[@name = '"
          + name + "']");
    }

    @Override
    public WebElement findElement(SearchContext context) {
      if (context instanceof FindsByName)
        return ((FindsByName) context).findElementByName(name);
      return ((FindsByXPath) context).findElementByXPath(".//*[@name = '"
          + name + "']");
    }

    @Override
    public String toString() {
      return "By.name: " + name;
    }
  }

  public static class ByTagName extends BaseUtil implements Serializable {

    private static final long serialVersionUID = 4699295846984948351L;

    private final String name;

    public ByTagName(String name) {
      this.name = name;
    }

    @Override
    public List<WebElement> findElements(SearchContext context) {
      if (context instanceof FindsByTagName)
        return ((FindsByTagName) context).findElementsByTagName(name);
      return ((FindsByXPath) context).findElementsByXPath(".//" + name);
    }

    @Override
    public WebElement findElement(SearchContext context) {
      if (context instanceof FindsByTagName)
        return ((FindsByTagName) context).findElementByTagName(name);
      return ((FindsByXPath) context).findElementByXPath(".//" + name);
    }

    @Override
    public String toString() {
      return "By.tagName: " + name;
    }
  }

  public static class ByXPath extends BaseUtil implements Serializable {

    private static final long serialVersionUID = -6727228887685051584L;

    private final String xpathExpression;

    public ByXPath(String xpathExpression) {
      this.xpathExpression = xpathExpression;
    }

    @Override
    public List<WebElement> findElements(SearchContext context) {
      return ((FindsByXPath) context).findElementsByXPath(xpathExpression);
    }

    @Override
    public WebElement findElement(SearchContext context) {
      return ((FindsByXPath) context).findElementByXPath(xpathExpression);
    }

    @Override
    public String toString() {
      return "By.xpath: " + xpathExpression;
    }
  }

  public static class ByClassName extends BaseUtil implements Serializable {

    private static final long serialVersionUID = -8737882849130394673L;

    private final String className;

    public ByClassName(String className) {
      this.className = className;
    }

    @Override
    public List<WebElement> findElements(SearchContext context) {
      if (context instanceof FindsByClassName)
        return ((FindsByClassName) context).findElementsByClassName(className);
      return ((FindsByXPath) context).findElementsByXPath(".//*["
          + containingWord("class", className) + "]");
    }

    @Override
    public WebElement findElement(SearchContext context) {
      if (context instanceof FindsByClassName)
        return ((FindsByClassName) context).findElementByClassName(className);
      return ((FindsByXPath) context).findElementByXPath(".//*["
          + containingWord("class", className) + "]");
    }

    /**
     * Generates a partial xpath expression that matches an element whose specified attribute
     * contains the given CSS word. So to match &lt;div class='foo bar'&gt; you would say "//div[" +
     * containingWord("class", "foo") + "]".
     * 
     * @param attribute name
     * @param word name
     * @return XPath fragment
     */
    private String containingWord(String attribute, String word) {
      return "contains(concat(' ',normalize-space(@" + attribute + "),' '),' "
          + word + " ')";
    }

    @Override
    public String toString() {
      return "By.className: " + className;
    }
  }

  public static class ByCssSelector extends BaseUtil implements Serializable {

    private static final long serialVersionUID = -3910258723099459239L;

    private final String selector;

    public ByCssSelector(String selector) {
      this.selector = selector;
    }

    @Override
    public WebElement findElement(SearchContext context) {
      if (context instanceof FindsByCssSelector) {
        return ((FindsByCssSelector) context)
            .findElementByCssSelector(selector);
      }

      throw new WebDriverException(
          "Driver does not support finding an element by selector: " + selector);
    }

    @Override
    public List<WebElement> findElements(SearchContext context) {
      if (context instanceof FindsByCssSelector) {
        return ((FindsByCssSelector) context)
            .findElementsByCssSelector(selector);
      }

      throw new WebDriverException(
          "Driver does not support finding elements by selector: " + selector);
    }

    @Override
    public String toString() {
      return "By.cssSelector: " + selector;
    }
  }
}
