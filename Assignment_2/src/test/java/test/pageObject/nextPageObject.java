package test.pageObject;

import org.openqa.selenium.*;

import java.util.List;

public class nextPageObject {

    WebDriver driver;
    String logoClassName = "header-d5w927";
    String categoryDivID = "plp-seo-heading";
    String productAmountDivClassName = "plp-avvafr";
    String firstProductCssSelector = ".MuiGrid-root.MuiGrid-item.MuiGrid-grid-xs-6.MuiGrid-grid-md-4.MuiGrid-grid-lg-4.MuiGrid-grid-xl-3.plp-14peamn";
    String productTitleClassName = "Title";
    String descriptionLinkText = "Description";
    String searchID = "header-big-screen-search-box";
    String searchSubmitCssSelector = ".MuiButtonBase-root.MuiButton-root.MuiButton-text.MuiButton-textPrimary.MuiButton-sizeMedium.MuiButton-textSizeMedium.header-113x19y";
    String searchResultClassName = "plp-k958he";
    String productH2ClassName = "produc-ivbv8a";
    String chooseSizeLinkText = "Choose Size";
    String sizeChoiceLinkText = "UK 10.5 EU 28";
    String sizeChoiceClassName = "dk_options_inner";
    String addToBagClassName = "AddToBag";
    String cartPopUpClassName = "header-osxxhi";
    String viewBagLinkText = "VIEW/EDIT BAG";
    String quantityID = "dk_container_Qty_1";
    String removeButtonLinkText = "Remove Item";
    String goShopLinkText = "Shop More";
    String continueShoppingLinkText = "Continue Shopping";
    String emptyBagTitleID = "title";
    String emptyBagSpanID = "itemCount";
    String checkoutButtonLinkText = "Go to Checkout";

    public nextPageObject(WebDriver driver) {
        this.driver = driver;
    }
    public void setup() throws InterruptedException {
        driver.get("https://www.next.com.mt/en/women");

        //make sure website is loaded
        Thread.sleep(500);

        //skipping cookies
        driver.switchTo().activeElement().sendKeys(Keys.TAB);
        driver.switchTo().activeElement().sendKeys(Keys.TAB);
        driver.switchTo().activeElement().sendKeys(Keys.ENTER);

    }

    public String getURL() {
        return driver.getCurrentUrl();
    }

    public String getLogoAttribute() {
        return driver.findElement(By.className(logoClassName)).getAttribute("alt");
    }

    public void searchCategory(String category) {
        WebElement categoryLink = driver.findElement(By.linkText(category));
        categoryLink.click();
    }

    public String getCategoryTitle() {
        WebElement headerDiv = driver.findElement(By.id(categoryDivID));
        WebElement headerTitle = headerDiv.findElement(By.tagName("h1"));

        return headerTitle.getText();
    }

    public int getProductAmount() {
        WebElement div = driver.findElement(By.className(productAmountDivClassName));
        WebElement divP = div.findElement(By.tagName("p"));

        //extract value from text + parse to integer
        String pText = divP.getText();
        String extractAmount = pText.replaceAll("[^0-9]", "");

        return Integer.parseInt(extractAmount);
    }

    public void chooseFirstProduct() {
        WebElement divElement = driver.findElement(By.cssSelector(firstProductCssSelector));
        WebElement anchorElement = divElement.findElement(By.tagName("a"));

        anchorElement.click();
    }

    public String getProductDataIndex() {
        WebElement divElement = driver.findElement(By.cssSelector(firstProductCssSelector));

        return divElement.getAttribute("data-index");
    }

    public boolean getProductTitle() {
        WebElement productTitle = driver.findElement(By.className(productTitleClassName));

        return productTitle.isDisplayed();
    }

    public boolean getDescription() {
        WebElement description = driver.findElement(By.linkText(descriptionLinkText));

        return description.isDisplayed();
    }
    public void searchProduct(String search) {
        WebElement searchField = driver.findElement(By.id(searchID));
        searchField.sendKeys(search);

        WebElement submitButton = driver.findElement(By.cssSelector(searchSubmitCssSelector));
        submitButton.submit();
    }
    public String getSearchResultName() {
        WebElement searchedElement = driver.findElement(By.className(searchResultClassName));

        return searchedElement.getText();
    }
    public String getProductName() {
        WebElement productH2 = driver.findElement(By.className(productH2ClassName));
        WebElement productP = productH2.findElement(By.tagName("p"));

        return productP.getText();
    }

    public void chooseSize() {
        //dropdown
        WebElement sizeAnchor = driver.findElement(By.linkText(chooseSizeLinkText));
        sizeAnchor.click();

        //choosing size

        //the driver wasn't finding the dropdown element, so we are using the keyboard keys to bypass
        driver.switchTo().activeElement().sendKeys(Keys.ARROW_DOWN);
        driver.switchTo().activeElement().sendKeys(Keys.ARROW_DOWN);
        driver.switchTo().activeElement().sendKeys(Keys.ARROW_DOWN);
        driver.switchTo().activeElement().sendKeys(Keys.ENTER);
    }

    public void addingToBag() throws InterruptedException {
        Thread.sleep(500);
        WebElement addToBagDiv = driver.findElement(By.className(addToBagClassName));
        WebElement addToBagAnchor = addToBagDiv.findElement(By.tagName("a"));
        addToBagAnchor.click();
    }

    public WebElement getCartPopUp() {
        return driver.findElement(By.className(cartPopUpClassName));
    }

    public void viewBag() {
        WebElement cartPopUp = driver.findElement(By.className(cartPopUpClassName));
        WebElement viewCart = cartPopUp.findElement(By.linkText(viewBagLinkText));
        viewCart.click();
    }

    public boolean getCheckoutButton() {
        WebElement checkoutButton = driver.findElement(By.linkText(checkoutButtonLinkText));

        return checkoutButton.isDisplayed();
    }

    public void removeItem() {
        WebElement removeButton = driver.findElement(By.linkText(removeButtonLinkText));
        removeButton.click();
    }

    public void goShopMore() {
        WebElement goShopButton = driver.findElement(By.linkText(goShopLinkText));
        goShopButton.click();
    }

    public void continueShopping() {
        WebElement continueShoppingButton = driver.findElement(By.linkText(continueShoppingLinkText));
        continueShoppingButton.click();
    }

    public String getEmptyBag() {
        WebElement emptyBagTitle = driver.findElement(By.id(emptyBagTitleID));
        WebElement emptyBagSpan = emptyBagTitle.findElement(By.id(emptyBagSpanID));

        return emptyBagSpan.getText();
    }

}
