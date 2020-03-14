package com.greglturnquist.payroll;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class EmployeeTest {

    @Test
    @DisplayName("validate input for firstName - happy case")
    void setFirstName() {
        //Arrange
        Employee employee1 = new Employee("Bilbo", "Baggins", "destroy the ring",
                "ringBearer", "bilbobaggins@gmail.com");

        //Act
        employee1.setFirstName("Frodo");
        String actual = employee1.getFirstName();

        //Assert
        assertEquals("Frodo", actual);
    }

    @Test
    @DisplayName("validate input for firstName - null case")
    void setFirstNameNull() {
        //Arrange
        Employee employee1 = new Employee("Bilbo", "Baggins", "destroy the ring",
                "ringBearer", "bilbobaggins@gmail.com");

        //Act
        try {
            employee1.setFirstName(null);
        }
        //Assert
        catch (IllegalArgumentException description) {
            assertEquals("The firstName is invalid.", description.getMessage());
        }
    }

    @Test
    @DisplayName("validate input for firstName - empty case")
    void setFirstNameEmpty() {
        //Arrange
        Employee employee1 = new Employee("Bilbo", "Baggins", "destroy the ring",
                "ringBearer", "bilbobaggins@gmail.com");

        //Act
        try {
            employee1.setFirstName("");
        }
        //Assert
        catch (IllegalArgumentException description) {
            assertEquals("The firstName is invalid.", description.getMessage());
        }
    }

    @Test
    @DisplayName("validate input for lastName - happy case")
    void setLastName() {
        //Arrange
        Employee employee1 = new Employee("Bilbo", "Baggins", "destroy the ring",
                "ringBearer", "bilbobaggins@gmail.com");

        //Act
        employee1.setLastName("Frodo");
        String actual = employee1.getLastName();

        //Assert
        assertEquals("Frodo", actual);
    }

    @Test
    @DisplayName("validate input for lastName - null case")
    void setLastNameNull() {
        //Arrange
        Employee employee1 = new Employee("Bilbo", "Baggins", "destroy the ring",
                "ringBearer", "bilbobaggins@gmail.com");

        //Act
        try {
            employee1.setLastName(null);
        }
        //Assert
        catch (IllegalArgumentException description) {
            assertEquals("The lastName is invalid.", description.getMessage());
        }
    }

    @Test
    @DisplayName("validate input for lastName - empty case")
    void setLastNameEmpty() {
        //Arrange
        Employee employee1 = new Employee("Bilbo", "Baggins", "destroy the ring",
                "ringBearer", "bilbobaggins@gmail.com");

        //Act
        try {
            employee1.setLastName("");
        }
        //Assert
        catch (IllegalArgumentException description) {
            assertEquals("The lastName is invalid.", description.getMessage());
        }
    }

    @Test
    @DisplayName("validate input for description - happy case")
    void setDescription() {
        //Arrange
        Employee employee1 = new Employee("Bilbo", "Baggins", "destroy the ring",
                "ringBearer", "bilbobaggins@gmail.com");

        //Act
        employee1.setDescription("Frodo");
        String actual = employee1.getDescription();

        //Assert
        assertEquals("Frodo", actual);
    }

    @Test
    @DisplayName("validate input for description - null case")
    void setDescriptionNull() {
        //Arrange
        Employee employee1 = new Employee("Bilbo", "Baggins", "destroy the ring",
                "ringBearer", "bilbobaggins@gmail.com");

        //Act
        try {
            employee1.setDescription(null);
        }
        //Assert
        catch (IllegalArgumentException description) {
            assertEquals("The description is invalid.", description.getMessage());
        }
    }

    @Test
    @DisplayName("validate input for description - empty case")
    void setDescriptionEmpty() {
        //Arrange
        Employee employee1 = new Employee("Bilbo", "Baggins", "destroy the ring",
                "ringBearer", "bilbobaggins@gmail.com");

        //Act
        try {
            employee1.setDescription("");
        }
        //Assert
        catch (IllegalArgumentException description) {
            assertEquals("The description is invalid.", description.getMessage());
        }
    }

    @Test
    @DisplayName("validate input for jobTitle - happy case")
    void setJobTitle() {
        //Arrange
        Employee employee1 = new Employee("Bilbo", "Baggins", "destroy the ring",
                "ringBearer", "bilbobaggins@gmail.com");

        //Act
        employee1.setJobTitle("Frodo");
        String actual = employee1.getJobTitle();

        //Assert
        assertEquals("Frodo", actual);
    }

    @Test
    @DisplayName("validate input for jobTitle - null case")
    void setJobTitleNull() {
        //Arrange
        Employee employee1 = new Employee("Bilbo", "Baggins", "destroy the ring",
                "ringBearer", "bilbobaggins@gmail.com");

        //Act
        try {
            employee1.setJobTitle(null);
        }
        //Assert
        catch (IllegalArgumentException description) {
            assertEquals("The jobTitle is invalid.", description.getMessage());
        }
    }

    @Test
    @DisplayName("validate input for jobTitle - empty case")
    void setJobTitleEmpty() {
        //Arrange
        Employee employee1 = new Employee("Bilbo", "Baggins", "destroy the ring",
                "ringBearer", "bilbobaggins@gmail.com");

        //Act
        try {
            employee1.setJobTitle("");
        }
        //Assert
        catch (IllegalArgumentException description) {
            assertEquals("The jobTitle is invalid.", description.getMessage());
        }
    }

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