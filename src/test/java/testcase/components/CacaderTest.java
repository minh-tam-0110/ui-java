package testcase.components;

import com.microsoft.playwright.Locator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import testcase.MasterTest;

import java.util.List;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

public class CacaderTest extends MasterTest {

    @ParameterizedTest
    @ValueSource(strings = {"Test, With, Me",
                            "Test, With, You",
                            "Tho, Test, UI",
                            "Tho, Test, API"})
    void verifyCascader(String input){
        page.navigate("https://test-with-me-app.vercel.app/learning/web-elements/components/cascader");
        String cascaderXpath = "//div[@role='separator' and normalize-space(.//text())='Cascader']//following::input[contains(concat(' ',normalize-space(@class),' '),' ant-select-selection-search-input ')][1]";
        Locator cascaderLocator = page.locator(cascaderXpath);
        cascaderLocator.click();
        for (String itemString : input.split(",")){
            String itemXpath = String.format("(//li[@role = 'menuitemcheckbox' and .//text()[normalize-space() = '%s']])[last()]", itemString.trim());
            page.locator(itemXpath).click();
        }
        String expectedLabelXpath = "//div[contains(., 'Current value: ') and ./span[contains(concat(' ', normalize-space(@class),' '),' text-rose-500 ')]]";
        assertThat(page.locator(expectedLabelXpath)).hasText(String.format("Current value: %s", input));
    }

    @Test
    void verifyMultipleValueCascader(){
        page.navigate("https://test-with-me-app.vercel.app/learning/web-elements/components/cascader");
        String[] inputs = {"Light > Number 1", "Bamboo > Little > Toy Cards"};
        String cascaderXpath = "//div[@role='separator' and normalize-space(.//text())='Cascader multiple values']//following::input[contains(concat(' ',normalize-space(@class),' '),' ant-select-selection-search-input ')][1]";
        page.locator(cascaderXpath).click();
        for (String input : inputs){
            String[] itemsString = input.split(">");
            for(int i = 0; i < itemsString.length; i++){
                String itemXpath = String.format("(//li[@role = 'menuitemcheckbox' and .//text()[normalize-space() = '%s']])[last()]", itemsString[i].trim());
                String itemCheckboxXpath = String.format("(%s)//span[contains(concat(' ',normalize-space(@class),' '),' ant-cascader-checkbox ')]", itemXpath);
                if (i<itemsString.length-1){
                    page.locator(itemXpath).click();
                }else {
                    page.locator(itemCheckboxXpath).click();
                }
            }
        }
        String expectedLabelXpath = "(//div//span[text()='Cascader multiple values']//following::div[contains(text(),'Current value: ')])[1]";
        assertThat(page.locator(expectedLabelXpath)).hasText("Current value: light, 1bamboo, little, cards");
    }

}
