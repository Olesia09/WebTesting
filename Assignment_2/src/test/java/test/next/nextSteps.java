package test.next;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.WebDriver;
import test.pageObject.nextPageObject;

public class nextSteps {

    WebDriver driver;

    nextPageObject next;

    @Before
    public void setUp() {
        driver = WebDriverManager.getDriver();
        next = new nextPageObject(driver);
    }

    @After
    public void tearDown() {
        if (driver != null) {
            WebDriverManager.quitDriver();
        }
    }

    @Given("I am a user of the website")
    public void iAmAUserOfTheWebsite() throws InterruptedException {
        next = new nextPageObject(driver);
        next.setup();
    }

    @When("I visit the Next website")
    public void iVisitTheSportsDirectWebsite() {
        Assertions.assertEquals("https://www.next.com.mt/en/women",next.getURL(), "incorrect URL");
        Assertions.assertEquals("Next Icon",next.getLogoAttribute(),"logo alt is incorrect");

    }

    @And("I click on the {} category")
    public void iClickOnTheCategory(String category) {
        next.searchCategory(category);
    }

    @Then("I should be taken to {} category")
    public void iShouldBeTakenToCategory(String category) {
        Assertions.assertTrue(next.getCategoryTitle().contains(category), "category not found in Title");
    }

    @And("the category should show at least {} products")
    public void theCategoryShouldShowAtLeastProducts(int amount) {
        Assertions.assertTrue(next.getProductAmount() >= amount, "Amount is not greater than or equal to " + amount);
    }

    @When("I click on the first product in the results")
    public void iClickOnTheFirstProductInTheResults() {
        //if product is first, click on it, else exit.
        if(next.getProductDataIndex().equals("0")) {
            next.chooseFirstProduct();
        }else tearDown();
    }

    @Then("I should be taken to the details page for that product")
    public void iShouldBeTakenToTheDetailsPageForThatProduct() {
        Assertions.assertTrue(next.getProductTitle(), "Product title cannot be found");
        Assertions.assertTrue(next.getDescription(), "Product description cannot be found");
    }

    @When("I search for a product using the term {string}")
    public void iSearchForAProductUsingTheTerm(String product) {
        next.searchProduct(product);
    }

    @Then("I should see the search results")
    public void iShouldSeeTheSearchResults() {
        Assertions.assertTrue(next.getSearchResultName().contains("Nikes"), "searched word not found in Title");
    }

    @And("there should be at least {int} products in the search results")
    public void thereShouldBeAtLeastProductsInTheSearchResults(int amount) {
        Assertions.assertTrue(next.getProductAmount() >= amount, "Amount is not greater than or equal to " + amount);
    }
}
