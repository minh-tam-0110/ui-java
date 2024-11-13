package testcase.elements;

import com.microsoft.playwright.*;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import testcase.MasterTest;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;


public class InputTest extends MasterTest {
    String inputText = "Hello World";
    String inputXpathOrigin = "//div[@role = 'separator' and normalize-space(.//text()) = '%s']//following::input[1]";
    String inputExpectXpathOrigin = "//div[@role = 'separator'  and normalize-space(.//text()) = '%s']" +
            "//following::input[1]" +
            "//following::div[contains(., 'Value:') and ./span[contains(concat(' ',normalize-space(@class),' '),' text-rose-500 ')]][1]";


    void navigatePage(){
        page.navigate("https://test-with-me-app.vercel.app/learning/web-elements/elements/input");
    }

    @ParameterizedTest
    @ValueSource(strings = {"Normal Input", "Password Box", "Text Area"})
    void checkValueCommonInput(String inputTextLabel) {
        //Declare
        Locator inputCommonlLocator = page.locator(String.format(inputXpathOrigin, inputTextLabel));
        Locator expectedCommonLocator = page.locator(String.format(inputExpectXpathOrigin, inputTextLabel));

        //Step
        navigatePage();
        inputCommonlLocator.fill(inputText);

        //Expect
        assertThat(expectedCommonLocator).hasText(String.format("Value: %s", inputText));
    }

    @Test
    void checkValueInputNumberDefault(){
        //Declare
        String inputNumberLabel = "Input Number";
        String inputnumberDefaultValue = "Value: 3";


        String decreaseValueXpath = "//span[@aria-label = 'Decrease Value']";

        Locator decreaseValueLocator = page.locator(decreaseValueXpath);

        Locator inputNumberLocator = page.locator(String.format(inputXpathOrigin, inputNumberLabel));
        Locator inputExpectNumberLocator = page.locator(String.format(inputExpectXpathOrigin, inputNumberLabel));

        //Step
        navigatePage();

        //Expect value compare vs default value
        assertThat(inputExpectNumberLocator).hasText(inputnumberDefaultValue);

        // Increase value
//        inputNumberLocator.click();
//        increaseValueLocator.hover();
//        increaseValueLocator.click();
//        assertThat(inputExpectNumberLocator).hasText("Value: 8");
    }

    @Test
    void checkIncreaseInputNumber() {
        //Declare
        String InputNumberLabel = "Input Number";
        int currentValue = 3;
        String increseValueXpath = "//span[@aria-label = 'Increase Value']";
        String decreaseValueXpath = "//span[@aria-label = 'Decrease Value']";

        Locator inputNumberLocator = page.locator(String.format(inputXpathOrigin, InputNumberLabel));
        Locator expectInputNumberLocator = page.locator(String.format(inputExpectXpathOrigin, InputNumberLabel));
        Locator increaseValueLocator = page.locator(increseValueXpath);
        Locator decreaseValueLocator = page.locator(decreaseValueXpath);

        //Action
        navigatePage();
        for (int i=0; i < 5; i++){
            inputNumberLocator.click();
            increaseValueLocator.hover();
            increaseValueLocator.click();
            currentValue += 5;
            assertThat(expectInputNumberLocator).hasText("Value: " + currentValue);
        }
        if (currentValue > 3){
            for (int i=0; i < 5; i++){
                inputNumberLocator.click();
                decreaseValueLocator.hover();
                decreaseValueLocator.click();
                currentValue -= 5;
                assertThat(expectInputNumberLocator).hasText("Value: " + currentValue);
            }
        }

        //Expect
        assertThat(expectInputNumberLocator).hasText("Value: " + currentValue);
    }

    @Test
    void otpInput(){
        navigatePage();
        String otpInputLabel = "OTP Box";
        String fillValue = "543678";
        String inputOtpXpath = "//div[normalize-space() = 'OTP Box' and @role = 'separator']//following::input[position() <= 6]";
        Locator inputOtpLocator = page.locator(inputOtpXpath);
        Locator expectInputOtpLocator = page.locator(String.format(inputExpectXpathOrigin, otpInputLabel));

        for (int i = 0; i < fillValue.length(); i++){
            inputOtpLocator.nth(i).fill(String.valueOf(fillValue.charAt(i)));
        }
        assertThat(expectInputOtpLocator).hasText("Value: " + fillValue);
    }
}
