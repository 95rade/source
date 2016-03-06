package swip.ch15pageflow.v2.pages;


import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import swip.ch15pageflow.domain.*;
import swip.ch15pageflow.locators.ClassName;
import swip.ch15pageflow.v2.framework.Browser;
import swip.ch15pageflow.v2.framework.BrowserRunner;

import javax.inject.Inject;
import java.time.Month;

import static org.junit.Assert.assertEquals;
import static swip.ch15pageflow.locators.ClassName.ORDER_NUMBER;
import static swip.ch15pageflow.locators.Id.ERROR_MESSAGE;
import static swip.ch15pageflow.locators.Id.ORDER_NUMBER_ID;

@RunWith(BrowserRunner.class)
public class BookstoreShoppingIT {

    public static final String EXPECTED_ERROR_MESSAGE =
        "The cardNumber must be between 19 and 19 characters long";
    public static final String EXPECTED_ORDER_NUMBER = "#00008.";

    @Inject
    private Browser browser;
    private ShoppingCartPage cartPage;

    private Address billingAddress = new Address(
        "1111 Mountain Dr",
        "14052014",
        "Edison",
        "08820",
        UnitedStates.New_Jersey,
        Countries.United_States,
        "Sanjay",
        "Rao");
    private CreditCard creditCard = new CreditCard(
        CreditCardType.MasterCard,
        "4111-1111-1111-1111",
        "123",
        Month.DECEMBER, 2020);

    private CreditCard invalidCreditCard = new CreditCard(
        CreditCardType.MasterCard,
        "4111-1111-1111",
        "123",
        Month.DECEMBER, 2020);

    private OtherInformation otherInformation = new OtherInformation(
        "no code",
        "joe@email.com",
        true,
        true,
        MailingOption.WEEKLY_NEWSLETTER,
        "no comments"
    );

    @Before
    public void addToCartAndSetSomeInformation() {
        BookstoreHomepage homePage = new BookstoreHomepage(browser);   //<1>
        homePage.searchBook("Selenium WebDriver in Practice");         //<2>

        BookPage bookPage = new BookPage(browser);                    //<3>
        bookPage.addToCart();                                         //<4>
        bookPage.gotoCart();                                          //<5>

        cartPage = new ShoppingCartPage(browser);                     //<6>
        cartPage.setBillingAddress(billingAddress);                   //<7>
        cartPage.setOtherInformation(otherInformation);               //<8>
    }

    @Test
    public void invalidCreditCard() {
        cartPage.setCreditCard(invalidCreditCard);          //<9>
        cartPage.continues();                               //<10>

        assertEquals(EXPECTED_ERROR_MESSAGE, browser.getText(ERROR_MESSAGE));  //<11>
    }

    @Test
    public void purchaseSuccessful() {
        cartPage.setCreditCard(creditCard);
        cartPage.continues();

        assertEquals(EXPECTED_ORDER_NUMBER, cartPage.getOrderNumber());  //<1>
    }

}

