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

    // PERBAIKAN: Menggunakan CSS Selector yang lebih umum (starts-with)
    // Ini akan cocok dengan 'add-to-cart-button-72', 'add-to-cart-button-31', dll.
    @FindBy(css = "input[id^='add-to-cart-button-']")
    private WebElement addToCartButton;

    // PERBAIKAN: Sama seperti tombol add to cart, input quantity ID-nya dinamis
    @FindBy(css = "input[id^='product_enteredQuantity_']")
    private WebElement quantityInput;

    @FindBy(css = "#bar-notification .content")
    private WebElement notificationMessage;

    @FindBy(css = "#bar-notification .close")
    private WebElement closeNotification;

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
        // Scroll ke tombol add to cart jika perlu (opsional, kadang tertutup)
        // ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", addToCartButton);
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
            return getText(notificationMessage);
        } catch (Exception e) {
            return "";
        }
    }

    // Klik link shopping cart
    public void goToCart() {
        try {
            // Tunggu notifikasi hilang dulu agar tidak menutupi tombol cart (opsional)
            // Atau langsung klik link cart di header
            click(cartLink);
        } catch (Exception e) {
            System.out.println("Cart link not found: " + e.getMessage());
        }
    }

    // Cek apakah produk berhasil ditambahkan ke cart
    public boolean isAddedToCart() {
        try {
            // Tunggu sampai bar notifikasi muncul
            wait.until(ExpectedConditions.visibilityOf(notificationMessage));
            String message = getText(notificationMessage);

            // Tutup notifikasi agar tidak menghalangi elemen lain
            try {
                if (isDisplayed(closeNotification)) {
                    click(closeNotification);
                    // Tunggu sebentar sampai animasi tutup selesai
                    Thread.sleep(500);
                }
            } catch (Exception ex) {
                // Ignore jika gagal tutup
            }

            return message.contains("added to your shopping cart") ||
                    message.contains("The product has been added to your");
        } catch (Exception e) {
            return false;
        }
    }
}