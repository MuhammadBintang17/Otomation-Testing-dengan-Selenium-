package com.praktikum.testing.otomation.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.util.List;

/**
 * Page Object untuk halaman Home
 * URL: http://demowebshop.tricentis.com/
 */
public class HomePage extends BasePage {

    // Locators
    @FindBy(className = "header-logo")
    private WebElement logo;

    @FindBy(id = "small-searchterms")
    private WebElement searchBox;

    @FindBy(css = "input[value='Search']")
    private WebElement searchButton;

    @FindBy(className = "product-item")
    private List<WebElement> products;

    @FindBy(css = ".product-box-add-to-cart-button")
    private List<WebElement> addToCartButtons;

    @FindBy(id = "topcartlink")
    private WebElement cartLink;

    @FindBy(className = "cart-qty")
    private WebElement cartQuantity;

    @FindBy(linkText = "Log in")
    private WebElement loginLink;

    @FindBy(linkText = "Register")
    private WebElement registerLink;

    @FindBy(className = "ico-account")
    private WebElement accountLink;

    @FindBy(className = "ico-logout")
    private WebElement logoutLink;

    @FindBy(className = "product-title")
    private List<WebElement> productTitles;

    @FindBy(className = "result")
    private WebElement searchResult;

    // Constructor
    public HomePage(WebDriver driver) {
        super(driver);
    }

    // Buka halaman home
    public void goToHomePage() {
        driver.get("http://demowebshop.tricentis.com/");
        wait.until(ExpectedConditions.visibilityOf(logo));
    }

    // Search produk
    public void search(String keyword) {
        enterText(searchBox, keyword);
        click(searchButton);
    }

    // Tambah produk ke cart berdasarkan index
    public void addToCart(int productIndex) {
        if (productIndex >= 0 && productIndex < addToCartButtons.size()) {
            click(addToCartButtons.get(productIndex));
        }
    }

    // Klik cart
    public void goToCart() {
        click(cartLink);
    }

    // Dapatkan jumlah item di cart
    public String getCartItemCount() {
        try {
            return getText(cartQuantity);
        } catch (Exception e) {
            return "0";
        }
    }

    // Cek apakah user sudah login
    public boolean isUserLoggedIn() {
        return isDisplayed(accountLink);
    }

    // Dapatkan jumlah hasil search
    public int getSearchResultCount() {
        wait.until(ExpectedConditions.visibilityOfAllElements(products));
        return products.size();
    }

    // Dapatkan judul produk
    public String getProductTitle(int index) {
        if (index >= 0 && index < productTitles.size()) {
            return getText(productTitles.get(index));
        }
        return "";
    }

    // Klik produk berdasarkan index
    public void clickProduct(int index) {
        if (index >= 0 && index < productTitles.size()) {
            click(productTitles.get(index));
        }
    }

    // Dapatkan pesan hasil search
    public String getSearchMessage() {
        try {
            return getText(searchResult);
        } catch (Exception e) {
            return "";
        }
    }
}