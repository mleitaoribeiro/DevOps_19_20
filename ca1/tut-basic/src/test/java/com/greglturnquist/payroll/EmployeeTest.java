package com.greglturnquist.payroll;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class EmployeeTest {

    @Test
    @DisplayName("validate input for email - happy case")
    void setEmail() {
        //Arrange
        Employee employee1 = new Employee("Bilbo", "Baggins", "destroy the ring",
                "ringBearer", "bilbobaggins@gmail.com");

        //Act
        employee1.setEmail("bilbobaggins1@gmail.com");
        String actual = employee1.getEmail();

        //Assert
        assertEquals("bilbobaggins1@gmail.com", actual);

    }

    @Test
    @DisplayName("validate input for email - null email")
    void setEmailNull() {
        //Arrange
        Employee employee1 = new Employee("Bilbo", "Baggins", "destroy the ring",
                "ringBearer", "bilbobaggins@gmail.com");

        //Act
        try {
            employee1.setEmail(null);
        }

        //Assert
        catch (IllegalArgumentException description) {
            assertEquals("The email is invalid.", description.getMessage());
        }
    }

    @Test
    @DisplayName("validate input for email - empty email")
    void setEmailEmpty() {
        //Arrange
        Employee employee1 = new Employee("Bilbo", "Baggins", "destroy the ring",
                "ringBearer", "bilbobaggins@gmail.com");

        //Act
        try {
            employee1.setEmail("");
        }

        //Assert
        catch (IllegalArgumentException description) {
            assertEquals("The email is invalid.", description.getMessage());
        }
    }

}