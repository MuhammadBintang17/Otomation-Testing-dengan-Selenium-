package com.praktikum.testing.otomation.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

/**
 * Page Object untuk halaman Login
 * URL: http://demowebshop.tricentis.com/login
 */
public class LoginPage extends BasePage {

    // Locators menggunakan @FindBy (Page Factory)
    @FindBy(id = "Email")
    private WebElement emailInput;

    @FindBy(id = "Password")
    private WebElement passwordInput;

    @FindBy(css = "input[value='Log in']")
    private WebElement loginButton;

    @FindBy(id = "RememberMe")
    private WebElement rememberMeCheckbox;

    @FindBy(className = "validation-summary-errors")
    private WebElement loginError;

    @FindBy(css = "#Email-error")
    private WebElement emailError;

    @FindBy(linkText = "Log out")
    private WebElement logoutLink;

    @FindBy(linkText = "Log in")
    private WebElement loginLink;

    @FindBy(css = ".header-links .account")
    private WebElement accountEmail;

    // Constructor
    public LoginPage(WebDriver driver) {
        super(driver); // Panggil parent constructor
    }

    // Buka halaman login
    public void goToLoginPage() {
        driver.get("http://demowebshop.tricentis.com/login");
        wait.until(ExpectedConditions.urlContains("/login"));
    }

    // Login dengan email dan password
    public void login(String email, String password) {
        enterText(emailInput, email);
        enterText(passwordInput, password);
        click(loginButton);
    }

    // Login dengan remember me
    public void loginWithRememberMe(String email, String password) {
        enterText(emailInput, email);
        enterText(passwordInput, password);
        click(rememberMeCheckbox); // Centang remember me
        click(loginButton);
    }

    // Cek apakah login berhasil
    public boolean isLoginSuccess() {
        try {
            wait.until(ExpectedConditions.visibilityOf(logoutLink));
            return logoutLink.isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    // Dapatkan pesan error login
    public String getLoginError() {
        try {
            waitForVisible(loginError);
            return getText(loginError);
        } catch (Exception e) {
            return "";
        }
    }

    // Dapatkan pesan error email
    public String getEmailError() {
        try {
            return getText(emailError);
        } catch (Exception e) {
            return "";
        }
    }

    // Logout
    public void logout() {
        try {
            click(logoutLink);
            wait.until(ExpectedConditions.visibilityOf(loginLink));
        } catch (Exception e) {
            System.out.println("Logout error: " + e.getMessage());
        }
    }

    // Cek apakah sudah logout
    public boolean isLogoutSuccess() {
        return isDisplayed(loginLink);
    }

    // Get account email yang login
    public String getAccountEmail() {
        try {
            return getText(accountEmail);
        } catch (Exception e) {
            return "";
        }
    }
}