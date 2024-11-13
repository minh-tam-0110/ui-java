package testcase.elements;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.WaitForSelectorState;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import testcase.MasterTest;

import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class DateTimeTest extends MasterTest {
    String inputGeneric = "//div[@role = 'separator' and normalize-space(.//text()) = '%s']//following::input[1]";
    String expectGeneric = "//div[@role = 'separator' and normalize-space(.//text()) = '%s']//following::div[contains(., '%s') and ./span[contains(concat(' ',normalize-space(@class),' '),' text-rose-500 ')]]";
    String closeforInput = "//div[@role = 'separator' and normalize-space(.//text()) = '%s']//following::span[contains(concat(' ',normalize-space(@class),' '),' ant-picker-clear ')][1]";

    void navigateDateTimePage(){
        page.navigate("https://test-with-me-app.vercel.app/learning/web-elements/elements/date-time");
    }


    @Test
    void checkTimePicker(){
        navigateDateTimePage();

        //khai báo
        String inputTimePickerLabel = "Time Picker";

        Locator inputTimePickerLocator = page.locator(String.format(inputGeneric, inputTimePickerLabel));
        Locator expcectTimePickerLocator = page.locator(String.format(expectGeneric, inputTimePickerLabel));
        Locator closeTimeButtonLocator = page.locator(String.format(closeforInput, inputTimePickerLabel));

//        Action
        //time local format
        LocalTime currentTime = LocalTime.now();
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss");
        String localtime = timeFormatter.format(currentTime);

        //Fill time
        inputTimePickerLocator.fill(localtime);
        page.keyboard().press("Tab");

        //Expect
        assertThat(expcectTimePickerLocator).hasText("Current time: " + localtime);

        //Click button close
        inputTimePickerLocator.hover();
        closeTimeButtonLocator.click();
    }

    @Test
    void buttonNowTimePicker(){
        navigateDateTimePage();
        //khai báo
        String inputTimePickerLabel = "Time Picker";
        String expectTextTimePicker = "Current time:";
        String buttonNowXpath = "//a[contains(concat(' ',normalize-space(@class),' '),' ant-picker-now-btn ') and normalize-space() = 'Now']";

        Locator buttonNowLocator = page.locator(buttonNowXpath);
        Locator inputTimePickerLocator = page.locator(String.format(inputGeneric, inputTimePickerLabel));
        Locator expectinputTimePickerLocator = page.locator(String.format(expectGeneric, inputTimePickerLabel, expectTextTimePicker));

//        //time
//        LocalTime currentTime = LocalTime.now();
//        //Định dạng time
//        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss");
//        String localtime = timeFormatter.format(currentTime);

        //Action
        long startNow = System.currentTimeMillis();
        inputTimePickerLocator.click();
        buttonNowLocator.click();
        long endNow = System.currentTimeMillis();
        page.keyboard().press("Tab");

        //convert
        SimpleDateFormat formatter = new SimpleDateFormat("HH:mm:ss");
        String timeBeforeClickNow = formatter.format(startNow);
        String timeAfterClickNow = formatter.format(endNow);
        String actualyTime = inputTimePickerLocator.getAttribute("value");

        System.out.println("Start: " + timeBeforeClickNow);
        System.out.println("End: " + timeAfterClickNow);
        System.out.println("time actually: " + actualyTime); //time trên web


        //convert form time action to local time
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss");
        LocalTime timeBeforeClickNowConvert = LocalTime.parse(timeBeforeClickNow, timeFormatter);
        LocalTime timeAfterClickNowConvert = LocalTime.parse(timeAfterClickNow, timeFormatter);
        LocalTime actualyTimeConvert = LocalTime.parse(actualyTime, timeFormatter);

        assertThat(expectinputTimePickerLocator).hasText("Current time: " + actualyTime);
        assertTrue(actualyTimeConvert.isAfter(timeBeforeClickNowConvert) && actualyTimeConvert.isBefore(timeAfterClickNowConvert));

    }

    @Test
    void checkInputTimeRangePicker(){
        navigateDateTimePage();

        //Declare
        String inputTimeRangeLabel = "Time Range Picker";
        String expectTextTimeRange = "Current time range:";
        String startTimeRange = "01:00:00";
        String endTimeRange = "23:59:59";
        Locator inputTimeRangeLocator = page.locator(String.format(inputGeneric, inputTimeRangeLabel));
        Locator expectInputTimeRangeLocator = page.locator(String.format(expectGeneric, inputTimeRangeLabel, expectTextTimeRange));

        //Action
        inputTimeRangeLocator.fill(startTimeRange);
        page.keyboard().press("Tab");
        page.keyboard().type(endTimeRange); //con trỏ ở ô input thứ 2
        page.keyboard().press("Tab");

        //Expect
        assertThat(expectInputTimeRangeLocator).hasText(expectTextTimeRange +" "+ startTimeRange + " - " + endTimeRange);

    }

    @ParameterizedTest
    @ValueSource(strings = {"Select date", "Select week", "Select month", "Select quarter", "Select year"})
    void checkInputDatePicker(String placeholderInput){
        navigateDateTimePage();
        //Declare
        String inputDatePickerLabel = "Date Picker";
        String expectTextDatePicker = "Current date:";

            //Xpath
        String commonInputDatePickerXpath = String.format("//div[normalize-space(.//text()) = '%s']//following::input[@placeholder = '%s']",inputDatePickerLabel ,placeholderInput);
        String todayButtonXpath = "//div[contains(@class, 'date')]//following::a[normalize-space() = 'Today']";
        String selectWeekXpath = "//div[contains(@class,'week')]//following::tr[./td[normalize-space() = '9']]";
        String selectMonthXpath = "//div[contains(@class, 'month')]//following::td[normalize-space() = 'Nov']";
        String selectQuarterXpath = "//div[contains(@class, 'quarter')]//following::td[normalize-space() = 'Q3']";
        String selectYearXpath = "//div[contains(@class, 'year')]//following::td[normalize-space() = '2024']";

            //Locator
        Locator commonInputDatePickerLocator = page.locator(commonInputDatePickerXpath);
        Locator expectInputDatePickerLocator = page.locator(String.format(expectGeneric, inputDatePickerLabel, expectTextDatePicker));
//        Locator closeInputButtonDatePickerLocator = page.locator(String.format(closeforInput, inputDatePickerLabel));
        Locator todayButtonLocator = page.locator(todayButtonXpath);
        Locator currentWeekLocator = page.locator(selectWeekXpath);
        Locator currentMonthLocator = page.locator(selectMonthXpath);
        Locator currentQuarterLocator = page.locator(selectQuarterXpath);
        Locator currentYearLocator = page.locator(selectYearXpath);

        //Action
        commonInputDatePickerLocator.click();

        if (todayButtonLocator.isVisible()){
            page.waitForSelector(todayButtonXpath,new Page.WaitForSelectorOptions().setState(WaitForSelectorState.VISIBLE));
            todayButtonLocator.click();
        }
        if (currentWeekLocator.isVisible()){
            currentWeekLocator.hover();
            currentWeekLocator.click();
        }
        if (currentMonthLocator.isVisible()){
            currentMonthLocator.click();
        }
        if (currentQuarterLocator.isVisible()){
            currentQuarterLocator.click();
        }
        if (currentYearLocator.isVisible()){
            currentYearLocator.click();
        }

        //Expect
            //chú ý KHOẢNG CÁCH
        assertThat(expectInputDatePickerLocator).hasText(expectTextDatePicker +" "+ commonInputDatePickerLocator.getAttribute("value"));
//        closeInputButtonDatePickerLocator.hover();
//        closeInputButtonDatePickerLocator.click();

    }

    @Test
    void verifyMultiDatePicker(){
        navigateDateTimePage();
        List<String> dateValues = new ArrayList<>();
        String multipleDateLabel = "Multiple Date Picker";
        String expectMultipleDate = "Current date: ";
        int day = 15;
        String inputMultiple = "//div[@role = 'separator' and normalize-space(.//text()) = 'Multiple Date Picker']//following::div[normalize-space() = 'Select date' and contains(@class, 'picker-multiple')]";
        String selectDayXpath = "//div[contains(@class, 'picker-panel')]//following::td[normalize-space() = '%s']";
        String dateSelectedXpath = "//span[contains(@class, 'selection-item-content')][1]";

        Locator multipleDateLocator = page.locator(inputMultiple);
        Locator expectMultipleDateLocator = page.locator(String.format(expectGeneric, multipleDateLabel, expectMultipleDate));
        Locator dateSelectedLocator = page.locator(dateSelectedXpath);

        multipleDateLocator.click();
        for (int i = 0; i < 3; i++){
            Locator selectDayLocator = page.locator(String.format(selectDayXpath, day));
            selectDayLocator.hover();
            selectDayLocator.click();
            day += 1;


//            dateValues.add(multipleDateLocator.getAttribute("value"));
//            System.out.println(dateValues);
        }
//        multipleDateLocator.click();
//        System.out.println(multipleDateLocator.getAttribute("value"));
//        assertThat(expectMultipleDateLocator).hasText(expectMultipleDate + );

    }
}
