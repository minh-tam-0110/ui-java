package data;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class FormTestData {
    public static String formValidationData = """
            [
              {
                "Full Name": {
                  "input": "",
                  "expected" : "Please input your full name!"
                },
                "Email": {
                  "input": "",
                  "expected" : "Please input your email!"
                },
                "Phone Number": {
                  "input": "",
                  "expected" : "Please input your phone number!"
                },
                "Date of Birth": {
                  "input": "",
                  "expected" : "Please select your date of birth!You must be at least 18 years old!"
                },
                "Address": {
                  "input": "",
                  "expected" : "Please input your address!"
                }
              },
            
              {
                "Full Name": {
                  "input": "",
                  "expected" : "Please input your full name!"
                },
                "Email": {
                  "input": "abc.com",
                  "expected" : "The input is not valid E-mail!"
                },
                "Phone Number": {
                  "input": "123465789",
                  "expected" : "Phone number must be 10 digits!"
                },
                "Date of Birth": {
                  "input": "2024-12-03",
                  "expected" : "You must be at least 18 years old!"
                },
                "Address": {
                  "input": "",
                  "expected" : "Please input your address!"
                }
              },
            
              {
                "Full Name": {
                  "input": "",
                  "expected" : "Please input your full name!"
                },
                "Email": {
                  "input": "abc.com",
                  "expected" : "The input is not valid E-mail!"
                },
                "Phone Number": {
                  "input": "11123465789",
                  "expected" : "Phone number must be 10 digits!"
                },
                "Date of Birth": {
                  "input": "asdfdfsdfa",
                  "expected" : "Please select your date of birth!You must be at least 18 years old!"
                },
                "Address": {
                  "input": "",
                  "expected" : "Please input your address!"
                }
              }
            ]
            """;
    public static String formValidationDataSuccess = String.format("""
            [
                {
                    "Full Name": "Minh Tâm",
                    "Email": "abc@gmail.com",
                    "Phone Number": "0123465789",
                    "Date of Birth": "2000-12-03",
                    "Address": "aaaa"
                },               
                {
                    "Full Name": "Minh Tâm",
                    "Email": "abc@gmail.com",
                    "Phone Number": "0123465789",
                    "Date of Birth": "%s",
                    "Address": "aaaa"
                }
              
            ]
            """, getValueBoudaryBirth());
    private static String getValueBoudaryBirth(){
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate today = LocalDate.now();
        String validBoudaryBirth = dateTimeFormatter.format(today.minusYears(18));
        return validBoudaryBirth;
    }
}
