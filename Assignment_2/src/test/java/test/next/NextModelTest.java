package test.next;

import io.cucumber.java.After;
import nz.ac.waikato.modeljunit.Action;
import nz.ac.waikato.modeljunit.FsmModel;
import nz.ac.waikato.modeljunit.GreedyTester;
import nz.ac.waikato.modeljunit.StopOnFailureListener;
import nz.ac.waikato.modeljunit.coverage.ActionCoverage;
import nz.ac.waikato.modeljunit.coverage.StateCoverage;
import nz.ac.waikato.modeljunit.coverage.TransitionPairCoverage;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.WebDriver;
import test.pageObject.nextPageObject;

import java.util.ArrayList;
import java.util.Random;

public class NextModelTest implements FsmModel{

    WebDriver driver;
    nextPageObject next;
    private states state = states.HOMEPAGE;
    private String selectedProduct = "";
    private ArrayList<String> cartItems = new ArrayList<>();

    @Before
    public void setUp() throws InterruptedException {
        driver = WebDriverManager.getDriver();
        next = new nextPageObject(driver);
        next.setup();
    }

    @After
    public void tearDown() {
        if (driver != null) {
            WebDriverManager.quitDriver();
        }
        selectedProduct = "";
        cartItems.clear();
    }

    public boolean searchNikesGuard() {
            return getState().equals(states.HOMEPAGE) || getState().equals(states.SEARCH_RESULTS);
    }

    public @Action void searchNikes() {
        String searchQuery = "Nikes";

        //exercise
        next.searchProduct(searchQuery);

        //update state
        state = states.SEARCH_RESULTS;

        //Assert
        Assertions.assertTrue(next.getSearchResultName().contains("Nikes"), "searched word not found in Title");
    }

    public boolean chooseProductGuard() {
            return getState().equals(states.SEARCH_RESULTS);
    }

    public @Action void chooseProduct() {
        //exercise
        if(next.getProductDataIndex().equals("0")) {

            //update variable
            selectedProduct = next.getProductName();

            next.chooseFirstProduct();

            //update state
            state = states.PRODUCT_DETAILS_PAGE;
        }else tearDown();

        //Assert
        Assertions.assertTrue(selectedProduct.contains("Nike Black/White Flex Runner 2 Junior Trainers"), "product name does not match");
    }

    public boolean addToCartGuard() {
            boolean b = false;

            if ((getState().equals(states.PRODUCT_DETAILS_PAGE) || getState().equals(states.PRODUCT_ADDED)) && (selectedProduct.contains("Nike Black/White Flex Runner 2 Junior Trainers"))) {
                b = true;
            }else return b;

            return b;
    }

    public @Action void addToCart() throws InterruptedException {
        //exercise
        next.chooseSize();
        next.addingToBag();

        //update variable
        cartItems.add(selectedProduct);

        //update state
        state = states.PRODUCT_ADDED;

        //Assert
        Assertions.assertTrue(next.getCartPopUp().isDisplayed(), "pop up is not displayed");
        Assertions.assertTrue(!cartItems.isEmpty() && cartItems.get(0).equals(selectedProduct));
    }

    public boolean viewBagGuard() {
        boolean b = false;

        if ((getState().equals(states.PRODUCT_ADDED)) && (!cartItems.isEmpty())) {
            b = true;
        }else return b;

        return b;
    }

    public @Action void viewBag() {
        //exercise
        next.viewBag();

        //update state
        state = states.CART;

        //Assert
        Assertions.assertTrue(next.getCheckoutButton(), "there are no items in the cart");
    }

    public boolean removeItemGuard() {
        try {
            return (getState().equals(states.CART)) && (!next.getEmptyBag().contains("0"));
        } catch (Exception e) {
            System.err.println("Exception in removeItemGuard: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    public @Action void removeItem(){
        //Exercise
        next.removeItem();

        //Update variables
        cartItems.clear();
        selectedProduct = "";

        //Assert
        Assertions.assertEquals("0", next.getEmptyBag(), "bag is not empty");
        Assertions.assertTrue(cartItems.isEmpty() && selectedProduct.isEmpty());
    }

    public boolean continueShoppingMoreGuard() {
        return getState().equals(states.CART);
    }

    public @Action void continueShoppingMore() {
        //Exercise
        //if the cart is empty, and you want to shop for more clothes
        if (cartItems.isEmpty()){
            next.continueShopping();
        }else{
            //if the cart isn't empty, and you want to keep shopping
            next.goShopMore();
        }

        //update state
        state = states.HOMEPAGE;

        Assertions.assertEquals("https://www.next.com.mt/en", next.getURL());
    }

    @Override
    public states getState() {
        return state;
    }

    @Override
    public void reset(boolean b) {
        state = states.HOMEPAGE;
        if (b) {
            tearDown();

            try {
                setUp();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

    //Test runner
    @Test
    public void NextWebsiteTestRunner() {
        final GreedyTester tester = new GreedyTester(this);//Creates a test generator that can generate random walks. A greedy random walk gives preference to transitions that have never been taken before. Once all transitions out of a state have been taken, it behaves the same as a random walk.
        tester.setRandom(new Random()); //Allows for a random path each time the model is run.
        tester.buildGraph(); //Builds a model of our FSM to ensure that the coverage metrics are correct.
        tester.addListener(new StopOnFailureListener()); //This listener forces the test class to stop running as soon as a failure is encountered in the model.
        tester.addListener("verbose"); //This gives you printed statements of the transitions being performed along with the source and destination states.
        tester.addCoverageMetric(new TransitionPairCoverage()); //Records the transition pair coverage i.e. the number of paired transitions traversed during the execution of the test.
        tester.addCoverageMetric(new StateCoverage()); //Records the state coverage i.e. the number of states which have been visited during the execution of the test.
        tester.addCoverageMetric(new ActionCoverage()); //Records the number of @Action methods which have been executed during the execution of the test.
        tester.generate(20); //Generates 20 transitions
        tester.printCoverage(); //Prints the coverage metrics specified above.
    }
}
