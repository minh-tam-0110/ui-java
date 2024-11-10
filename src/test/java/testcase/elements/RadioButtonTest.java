package testcase.elements;

import com.microsoft.playwright.Locator;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;


public class RadioButtonTest extends MasterTest {
    String commonRadioButtonXpath = "//div[contains(@class, 'radio-group')][%s]//label[normalize-space(.//text()) = '%s']";
    String expectCommonRadioButtonXpath = "//div[@role = 'separator' and normalize-space(.//text()) = 'Radio button']//following::div[contains(., 'Value:') and ./span[contains(concat(' ',normalize-space(@class),' '),' text-rose-500 ')]][%s]";

    void navigatePageRadio(){
        page.navigate("https://test-with-me-app.vercel.app/learning/web-elements/elements/radio");
    }

    void actionRadioButton(String index, String defaultRadiobuttonLabel){
        Locator checkRadioButtonLocator = page.locator(String.format(commonRadioButtonXpath, index, defaultRadiobuttonLabel));
        checkRadioButtonLocator.click();
    }
    void resultRadioButton(String index, String defaultRadiobuttonLabel){
        Locator expectRadioButtonLocator = page.locator(String.format(expectCommonRadioButtonXpath, index));
        assertThat(expectRadioButtonLocator).hasText("Value: " + defaultRadiobuttonLabel);
    }

    @ParameterizedTest
    @ValueSource(strings = {"Apple","Pear","Orange"})
    void defaultRadioButton(String defaultRadiobuttonLabel){
        navigatePageRadio();
        String indexdefaultRadioButton = "1";

        actionRadioButton(indexdefaultRadioButton,defaultRadiobuttonLabel);
        resultRadioButton(indexdefaultRadioButton,defaultRadiobuttonLabel);

    }

    @ParameterizedTest
    @ValueSource(strings = {"Apple","Pear","Orange"})
    void customRadioButton(String defaultRadiobuttonLabel){
        navigatePageRadio();
        //khai báo
        String index = "2";

        actionRadioButton(index,defaultRadiobuttonLabel);
        resultRadioButton(index,defaultRadiobuttonLabel);

    }

}
