package testcase.elements;

import com.microsoft.playwright.Locator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

public class DateTimeTest extends MasterTest{
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
        String expectTextTimePicker = "Current time:";

        Locator inputTimePickerLocator = page.locator(String.format(inputGeneric, inputTimePickerLabel));
        Locator expcectTimePickerLocator = page.locator(String.format(expectGeneric, inputTimePickerLabel, expectTextTimePicker));
        Locator closeTimeButtonLocator = page.locator(String.format(closeforInput, inputTimePickerLabel));

        //Fill time
        inputTimePickerLocator.fill("12:00:00");
        page.keyboard().press("Tab");

        //Expect
        assertThat(expcectTimePickerLocator).hasText("Current time: 12:00:00");

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
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss");
//        String localtime = timeFormatter.format(currentTime);

        //Act
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
        System.out.println("time actualy: " + actualyTime); //time trên web


        //convert form time action to local time
        LocalTime timeBeforeClickNowConvert = LocalTime.parse(timeBeforeClickNow, timeFormatter);
        LocalTime timeAfterClickNowConvert = LocalTime.parse(timeAfterClickNow, timeFormatter);
        LocalTime actualyTimeConvert = LocalTime.parse(actualyTime, timeFormatter);


        if(actualyTimeConvert.isAfter(timeBeforeClickNowConvert)){
            if(actualyTimeConvert.isBefore(timeAfterClickNowConvert) || actualyTimeConvert.equals(timeAfterClickNowConvert)){
                assertThat(expectinputTimePickerLocator).hasText("Current time: " + actualyTime);
            }
        }
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
        assertThat(expectInputTimeRangeLocator).hasText("Current time range: " + startTimeRange + " - " + endTimeRange);

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
        Locator closeInputButtonDatePickerLocator = page.locator(String.format(closeforInput, inputDatePickerLabel));
        Locator todayButtonLocator = page.locator(todayButtonXpath);
        Locator currentWeekLocator = page.locator(selectWeekXpath);
        Locator currentMonthLocator = page.locator(selectMonthXpath);
        Locator currentQuarterLocator = page.locator(selectQuarterXpath);
        Locator currentYearLocator = page.locator(selectYearXpath);

        //Action
        commonInputDatePickerLocator.click();
        if (todayButtonLocator.isVisible()){
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
        closeInputButtonDatePickerLocator.hover();
        closeInputButtonDatePickerLocator.click();

    }
}
