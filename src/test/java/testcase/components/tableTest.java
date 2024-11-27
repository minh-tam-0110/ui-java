package testcase.components;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.microsoft.playwright.Locator;
import model.Customer;
import org.junit.jupiter.api.Test;

import testcase.MasterTest;

import java.util.ArrayList;
import java.util.List;

import static data.TableTestData.CUSTOMER_EXPECTED_DATA;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class tableTest extends MasterTest {
    String [] definedHeaders = {"Name", "Age", "Address", "Tags"};

    @Test
    void verifyTable() throws InterruptedException, JsonProcessingException {
        page.navigate("https://test-with-me-app.vercel.app/learning/web-elements/components/table");
        String tableXpath = "(//div[normalize-space() = 'Table']//following::table)[1]";
        Thread.sleep(3000);
        String tableHeaderXpath = String.format("%s//th", tableXpath);
        List<Locator> headerLocators = page.locator(tableHeaderXpath).all();
        List<String> headerList = new ArrayList<>();
        for (Locator header : headerLocators) {
            headerList.add(header.textContent());
        }
        String rowDataXpath = String.format("%s//tbody//tr",tableXpath);
        List<Customer> actualCustomer = new ArrayList<>();
        String nextButtonXpath = "//div[normalize-space() = 'Table']" +
                                "//following::div[contains(concat(' ',normalize-space(@class),' '),' ant-table-wrapper ')]" +
                                "//li[@title = 'Next Page']";
        boolean isNext = false;
        do {
            List<Locator> rowLocators = page.locator(rowDataXpath).all();
            for (Locator row : rowLocators) {
                Customer customer = new Customer();
                int indexOfName = headerList.indexOf("Name");
                String cellNameLocator = String.format("//td[%s]", indexOfName + 1);
                String name = row.locator(cellNameLocator).textContent();
                customer.setName(name);

                int indexOfAge = headerList.indexOf("Age");
                String cellAgeLocator = String.format("//td[%s]", indexOfAge + 1);
                String age = row.locator(cellAgeLocator).textContent();
                customer.setAge(Integer.parseInt(age));

                int indexOfAdress = headerList.indexOf("Address");
                String cellAdressLocator = String.format("//td[%s]", indexOfAdress + 1);
                String adress = row.locator(cellAdressLocator).textContent();
                customer.setAddress(adress);

                int indexOfTags = headerList.indexOf("Tags");
                String cellTagsLocator = String.format("//td[%s]", indexOfTags + 1);
                String tags = row.locator(cellTagsLocator).textContent();
                customer.setTags(tags);

                actualCustomer.add(customer);
            }
            Locator nextButton = page.locator(nextButtonXpath);
            isNext = !Boolean.parseBoolean(nextButton.getAttribute("aria-disabled"));
            if (isNext) {
                nextButton.click();
            }

        } while (isNext);
        System.out.println();
        ObjectMapper mapper = new ObjectMapper();
        List<Customer> expectedCustomer = mapper.readValue(CUSTOMER_EXPECTED_DATA, new TypeReference<List<Customer>>() {});
        boolean isActualContainAllExpected = actualCustomer.containsAll(expectedCustomer);
        assertTrue(isActualContainAllExpected);
        boolean isExpectedContainAllActual = expectedCustomer.containsAll(actualCustomer);
        assertTrue(isExpectedContainAllActual);
    }
}
