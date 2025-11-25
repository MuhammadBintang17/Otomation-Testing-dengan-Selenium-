package com.praktikum.testing.otomation.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

/**
 * Page Object untuk halaman Detail Produk
 */
public class ProductPage extends BasePage {

    // Locators
    @FindBy(className = "product-name")
    private WebElement productName;

    @FindBy(className = "product-price")
    private WebElement productPrice;

    @FindBy(className = "short-description")
    private WebElement productDescription;

    @FindBy(className = "picture")
    private WebElement productImage;

    // Perhatikan: ID ini mungkin berubah tergantung produknya (misal: add-to-cart-button-72)
    // Sesuaikan dengan ID spesifik produk yang Anda test jika perlu.
    @FindBy(id = "add-to-cart-button-72")
    private WebElement addToCartButton;

    @FindBy(id = "product_enteredQuantity_72")
    private WebElement quantityInput;

    @FindBy(className = "content")
    private WebElement notificationMessage;

    @FindBy(linkText = "shopping cart")
    private WebElement cartLink;

    // Constructor
    public ProductPage(WebDriver driver) {
        super(driver);
    }

    // Dapatkan nama produk
    public String getName() {
        waitForVisible(productName);
        return getText(productName);
    }

    // Dapatkan harga produk
    public String getPrice() {
        waitForVisible(productPrice);
        return getText(productPrice);
    }

    // Dapatkan deskripsi produk
    public String getDescription() {
        waitForVisible(productDescription);
        return getText(productDescription);
    }

    // Cek apakah gambar produk ditampilkan
    public boolean isImageDisplayed() {
        return isDisplayed(productImage);
    }

    // Tambah ke cart
    public void addToCart() {
        click(addToCartButton);
    }

    // Set quantity
    public void setQuantity(int quantity) {
        enterText(quantityInput, String.valueOf(quantity));
    }

    // Dapatkan pesan notifikasi
    public String getNotification() {
        try {
            waitForVisible(notificationMessage);
            return getText(notificationMessage); // Typo 'gotToxt' diperbaiki
        } catch (Exception e) {
            return "";
        }
    }

    // Klik link shopping cart
    public void goToCart() {
        try {
            click(cartLink);
        } catch (Exception e) {
            System.out.println("Cart link not found: " + e.getMessage());
        }
    }

    // Cek apakah produk berhasil ditambahkan ke cart
    public boolean isAddedToCart() {
        try {
            String message = getNotification();
            return message.contains("added to your shopping cart") ||
                    message.contains("The product has been added to your");
        } catch (Exception e) {
            return false;
        }
    }
}