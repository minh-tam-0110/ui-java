package testcase.components;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.options.BoundingBox;
import org.junit.jupiter.api.Test;
import testcase.MasterTest;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

public class SliderTest extends MasterTest {
    @Test
    void verifySlider(){
        page.navigate("https://test-with-me-app.vercel.app/learning/web-elements/components/slider");
        String sliderXpath = "//div[normalize-space() = 'Slider']//following::div[contains(concat(' ',normalize-space(@class),' '),' ant-slider-rail ')]";
        Locator sliderLocator = page.locator(sliderXpath);
        double input = 0.28;
        double sliderLenght = sliderLocator.boundingBox().width * input;
        sliderLocator.hover();
        page.mouse().move(sliderLocator.boundingBox().x, sliderLocator.boundingBox().y);
        page.mouse().down();
        page.mouse().move(sliderLocator.boundingBox().x + sliderLenght, sliderLocator.boundingBox().y);
        page.mouse().up();
        String expectedLabelXpath = "//div[contains(text(), 'Current Value:') and ./span[contains(concat(' ',normalize-space(@class),' '),' text-rose-500 ')]]";
        assertThat(page.locator(expectedLabelXpath)).hasText("Current Value: 28");
    }
}
