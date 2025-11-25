package com.praktikum.testing.otomation.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

/**
 * CORRECT VERSION - Updated to extend BasePage
 * Page Object untuk Registration Page
 * URL: http://demowebshop.tricentis.com/register
 */
public class RegistrationPage extends BasePage {

    // Correct Locators menggunakan @FindBy annotation
    @FindBy(linkText = "Register")
    private WebElement registerLink;

    @FindBy(id = "gender-male")
    private WebElement genderMaleRadio;

    @FindBy(id = "gender-female")
    private WebElement genderFemaleRadio;

    @FindBy(id = "FirstName")
    private WebElement firstNameField;

    @FindBy(id = "LastName")
    private WebElement lastNameField;

    @FindBy(id = "Email")
    private WebElement emailField;

    @FindBy(id = "Password")
    private WebElement passwordField;

    @FindBy(id = "ConfirmPassword")
    private WebElement confirmPasswordField;

    @FindBy(id = "register-button")
    private WebElement registerButton;

    @FindBy(className = "result")
    private WebElement successMessage;

    @FindBy(css = "div.page-title h1")
    private WebElement pageTitle;

    // Validation messages
    @FindBy(css = "span[for='FirstName']") // Perbaikan selector agar lebih spesifik
    private WebElement firstNameError;

    @FindBy(css = "span[for='LastName']") // Tambahan @FindBy untuk konsistensi
    private WebElement lastNameError;

    @FindBy(css = "span[for='Email']")
    private WebElement emailError;

    @FindBy(css = "span[for='Password']")
    private WebElement passwordError;

    @FindBy(css = "span[for='ConfirmPassword']")
    private WebElement confirmPasswordError;

    // Constructor
    public RegistrationPage(WebDriver driver) {
        super(driver); // Panggil constructor BasePage
    }

    // Method untuk membuka halaman registrasi
    public void navigateToRegisterPage() {
        navigateTo("http://demowebshop.tricentis.com/");
        clickElement(registerLink);
        wait.until(ExpectedConditions.urlContains("/register"));
    }

    // Method untuk memilih gender
    public void selectGender(String gender) {
        if (gender == null || gender.isEmpty()) {
            throw new IllegalArgumentException("Gender cannot be null or empty");
        }

        if (gender.equalsIgnoreCase("Male")) {
            clickElement(genderMaleRadio);
        } else if (gender.equalsIgnoreCase("Female")) {
            clickElement(genderFemaleRadio);
        } else {
            throw new IllegalArgumentException("Gender must be 'Male' or 'Female'");
        }
    }

    // Method untuk mengisi first name
    public void enterFirstName(String firstName) {
        enterText(firstNameField, firstName);
    }

    // Method untuk mengisi last name
    public void enterLastName(String lastName) {
        enterText(lastNameField, lastName);
    }

    // Method untuk mengisi email
    public void enterEmail(String email) {
        enterText(emailField, email);
    }

    // Method untuk mengisi password
    public void enterPassword(String password) {
        enterText(passwordField, password);
    }

    // Method untuk mengisi confirm password
    public void enterConfirmPassword(String confirmPassword) {
        enterText(confirmPasswordField, confirmPassword);
    }

    // Method untuk klik tombol register
    public void clickRegisterButton() {
        clickElement(registerButton);
    }

    // Method untuk mendapatkan pesan sukses
    public String getSuccessMessage() {
        return getElementText(successMessage);
    }

    // Method untuk mendapatkan page title
    public String getPageTitle() {
        return getElementText(pageTitle);
    }

    // Validation error methods
    public String getFirstNameError() {
        try {
            return getElementText(firstNameError);
        } catch (Exception e) {
            return "";
        }
    }

    public String getLastNameError() {
        try {
            return getElementText(lastNameError);
        } catch (Exception e) {
            return "";
        }
    }

    public String getEmailError() {
        try {
            return getElementText(emailError);
        } catch (Exception e) {
            return "";
        }
    }

    public String getPasswordError() {
        try {
            return getElementText(passwordError);
        } catch (Exception e) {
            return "";
        }
    }

    public String getConfirmPasswordError() {
        try {
            return getElementText(confirmPasswordError);
        } catch (Exception e) {
            return "";
        }
    }

    // Method untuk registrasi lengkap
    public void registerUser(String gender, String firstName, String lastName,
                             String email, String password) {
        selectGender(gender);
        enterFirstName(firstName);
        enterLastName(lastName);
        enterEmail(email);
        enterPassword(password);
        enterConfirmPassword(password);
        clickRegisterButton();

        // Wait for either success or error
        try {
            wait.until(ExpectedConditions.or(
                    ExpectedConditions.visibilityOf(successMessage),
                    ExpectedConditions.visibilityOf(emailError),
                    ExpectedConditions.visibilityOf(firstNameError)
            ));
        } catch (Exception e) {
            // Ignore timeout, assertions will handle it
        }
    }

    // Method untuk verifikasi registrasi berhasil
    public boolean isRegistrationSuccessful() {
        return isElementDisplayed(successMessage) &&
                getSuccessMessage().contains("Your registration completed");
    }

    // Method untuk verifikasi ada error validation
    public boolean hasValidationErrors() {
        return !getFirstNameError().isEmpty() ||
                !getEmailError().isEmpty() ||
                !getPasswordError().isEmpty() ||
                !getConfirmPasswordError().isEmpty();
    }
}