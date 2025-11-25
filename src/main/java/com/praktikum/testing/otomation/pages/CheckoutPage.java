package com.praktikum.testing.otomation.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;

/**
 * Page Object untuk halaman Checkout
 */
public class CheckoutPage extends BasePage {

    // Locators
    @FindBy(id = "BillingNewAddress_FirstName")
    private WebElement firstNameInput;

    @FindBy(id = "BillingNewAddress_LastName")
    private WebElement lastNameInput;

    @FindBy(id = "BillingNewAddress_Email")
    private WebElement emailInput;

    @FindBy(id = "BillingNewAddress_CountryId")
    private WebElement countryDropdown;

    @FindBy(id = "BillingNewAddress_City")
    private WebElement cityInput;

    @FindBy(id = "BillingNewAddress_Address1")
    private WebElement addressInput;

    @FindBy(id = "BillingNewAddress_ZipPostalCode")
    private WebElement zipInput;

    @FindBy(id = "BillingNewAddress_PhoneNumber")
    private WebElement phoneInput;

    @FindBy(css = "input[onclick='Billing.save()']")
    private WebElement continueBillingButton;

    @FindBy(css = "input[onclick='ConfirmOrder.save()']")
    private WebElement confirmOrderButton;

    @FindBy(className = "order-completed")
    private WebElement orderCompleteMessage;

    @FindBy(className = "order-number")
    private WebElement orderNumber;

    @FindBy(className = "field-validation-error")
    private WebElement validationError;

    // Constructor
    public CheckoutPage(WebDriver driver) {
        super(driver);
    }

    // Isi alamat billing
    public void fillBillingAddress(String firstName, String lastName, String email,
                                   String country, String city, String address,
                                   String zip, String phone) {
        enterText(firstNameInput, firstName);
        enterText(lastNameInput, lastName);
        enterText(emailInput, email);
        selectCountry(country);
        enterText(cityInput, city);
        enterText(addressInput, address);
        enterText(zipInput, zip);
        enterText(phoneInput, phone);
    }

    // Pilih negara dari dropdown
    private void selectCountry(String country) {
        Select countrySelect = new Select(countryDropdown);
        countrySelect.selectByVisibleText(country);
    }

    // Continue billing
    public void continueBilling() {
        click(continueBillingButton);
        // Tunggu sampai tombol confirm muncul/clickable
        wait.until(ExpectedConditions.elementToBeClickable(confirmOrderButton));
    }

    // Confirm order
    public void confirmOrder() {
        click(confirmOrderButton);
        // Tunggu pesan sukses muncul
        wait.until(ExpectedConditions.visibilityOf(orderCompleteMessage));
    }

    // Dapatkan pesan order complete
    public String getOrderCompleteMessage() {
        waitForVisible(orderCompleteMessage);
        return getText(orderCompleteMessage);
    }

    // Dapatkan nomor order
    public String getOrderNumber() {
        return getText(orderNumber);
    }

    // Cek apakah order berhasil
    public boolean isOrderSuccess() {
        try {
            String message = getOrderCompleteMessage();
            return message.contains("Your order has been successfully processed");
        } catch (Exception e) {
            return false;
        }
    }

    // Dapatkan pesan error
    public String getError() {
        try {
            return getText(validationError);
        } catch (Exception e) {
            return "";
        }
    }
}