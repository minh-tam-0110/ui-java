package testcase.components;

import com.microsoft.playwright.Locator;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;
import testcase.MasterTest;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

public class RatingTest extends MasterTest {
    void selectRating(float input, String label){
        boolean isHalfStar = input < Math.ceil(input);
        String firstOrSceond = isHalfStar ? "ant-rate-star-first":"ant-rate-star-second";
        String starXpath = String.format("(//div[normalize-space() = '%s']//following::div[@aria-posinset = '%s']//div[contains(concat(' ',normalize-space(@class),' '),' %s ')])[1]",label, Math.round(Math.ceil(input)) , firstOrSceond);
        String currentFullStarXpath = String.format("//div[normalize-space() = '%s']//following::ul[1]//li[contains(concat(' ',normalize-space(@class),' '),' ant-rate-star-full ')]",label);
        String currentHalfStarXpath = String.format("//div[normalize-space() = '%s']//following::ul[1]//li[contains(concat(' ',normalize-space(@class),' '),' ant-rate-star-half ')]",label);
        float currentRating = page.locator(currentFullStarXpath).count();
        if (page.locator(currentHalfStarXpath).count() != 0){
            currentRating += 0.5f;
        }
        if (currentRating != input){
            Locator startLocator = page.locator(starXpath);
            startLocator.click();
        }
    }

    @ParameterizedTest
    @CsvSource({
            "1, terrible",
            "2, bad",
            "3, normal",
            "4, good",
            "5, wonderful"
    })
        void verifyRatingFullStar(int input, String expectedOutput){
        page.navigate("https://test-with-me-app.vercel.app/learning/web-elements/components/rating");

        selectRating(input, "Rate");

        String currentRatingXpath = "//div[normalize-space() = 'Rate']//following::div[contains(., 'Current rating:')][1]";
        Locator ratingLocator = page.locator(currentRatingXpath);

        assertThat(ratingLocator).hasText(String.format("Current rating: %s", expectedOutput));
    }

    @ParameterizedTest
    @ValueSource(floats = {0.5f, 1f, 1.5f, 2f, 2.5f, 3f, 3.5f, 4f, 4.5f, 5})
    void verifyRatingHaftStar(float input){
        page.navigate("https://test-with-me-app.vercel.app/learning/web-elements/components/rating");
        selectRating(input, "Haft Rate");
        String currentRatingXpath = "//div[normalize-space() = 'Haft Rate']//following::div[contains(., 'Current rating:')][1]";
        Locator ratingLocator = page.locator(currentRatingXpath);
        String expectedValue = String.valueOf(input);
        if (!(input < Math.ceil(input))){
            expectedValue = String.valueOf(Math.round(input));
        }
        assertThat(ratingLocator).hasText(String.format("Current rating: %s", expectedValue));
    }
}