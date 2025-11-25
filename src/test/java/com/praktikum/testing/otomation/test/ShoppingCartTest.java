package com.praktikum.testing.otomation.test;

import com.aventstack.extentreports.Status;
import com.praktikum.testing.otomation.pages.CartPage;
import com.praktikum.testing.otomation.pages.HomePage;
import com.praktikum.testing.otomation.pages.LoginPage;
import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * Test class untuk feature Shopping Cart (10 test cases)
 */
public class ShoppingCartTest extends BaseTest {

    @Test(priority = 1, description = "Test menambahkan single product ke cart")
    public void testAddSingleProductToCart() {
        test.log(Status.INFO, "Memulai test tambah single product ke cart");

        HomePage homePage = new HomePage(driver);
        CartPage cartPage = new CartPage(driver);

        homePage.goToHomePage();
        test.log(Status.INFO, "Buka halaman home");

        // Tambah produk pertama ke cart
        homePage.addToCart(0);
        test.log(Status.INFO, "Tambah produk pertama ke cart");

        // Verifikasi cart quantity bertambah
        String cartQty = homePage.getCartItemCount();
        Assert.assertNotEquals(cartQty, "0", "Cart quantity harus bertambah");
        test.log(Status.INFO, "Cart quantity: " + cartQty);

        // Buka cart untuk verifikasi
        homePage.goToCart();
        test.log(Status.INFO, "Buka halaman cart");

        // Verifikasi produk ada di cart
        int itemCount = cartPage.getItemCount();
        Assert.assertTrue(itemCount > 0, "Harus ada produk di cart");
        test.log(Status.PASS, "Single product berhasil ditambah - itemCount: " + itemCount);

        // Bersih-bersih
        cartPage.removeItem(0);
        test.log(Status.INFO, "Bersih-bersih - hapus item dari cart");
    }

    @Test(priority = 2, description = "Test menambahkan multiple products ke cart")
    public void testAddMultipleProductsToCart() {
        test.log(Status.INFO, "Memulai test tambah multiple products ke cart");

        HomePage homePage = new HomePage(driver);
        CartPage cartPage = new CartPage(driver);

        homePage.goToHomePage();
        test.log(Status.INFO, "Buka halaman home");

        // Tambah dua produk ke cart
        homePage.addToCart(0);
        test.log(Status.INFO, "Tambah produk pertama ke cart");

        homePage.addToCart(1);
        test.log(Status.INFO, "Tambah produk kedua ke cart");

        // Buka cart
        homePage.goToCart();
        test.log(Status.INFO, "Buka halaman cart");

        // Verifikasi ada multiple produk
        int itemCount = cartPage.getItemCount();
        Assert.assertTrue(itemCount >= 2, "Harus ada minimal 2 produk di cart");
        test.log(Status.PASS, "Multiple products berhasil ditambah - itemCount: " + itemCount);

        // Bersih-bersih
        for (int i = 0; i < itemCount; i++) {
            cartPage.removeItem(0);
        }
        test.log(Status.INFO, "Bersih-bersih - hapus semua item dari cart");
    }

    @Test(priority = 3, description = "Test update product quantity di cart")
    public void testUpdateProductQuantity() {
        test.log(Status.INFO, "Memulai test update quantity di cart");

        HomePage homePage = new HomePage(driver);
        CartPage cartPage = new CartPage(driver);

        homePage.goToHomePage();
        test.log(Status.INFO, "Buka halaman home");

        // Tambah produk ke cart
        homePage.addToCart(0);
        test.log(Status.INFO, "Tambah produk ke cart");

        // Buka cart
        homePage.goToCart();
        test.log(Status.INFO, "Buka halaman cart");

        // Update quantity menjadi 3
        cartPage.updateQuantity(0, 3);
        test.log(Status.INFO, "Update quantity menjadi 3");

        // Verifikasi cart terupdate
        String total = cartPage.getTotal();
        Assert.assertNotNull(total, "Cart total harus terupdate");
        test.log(Status.PASS, "Quantity berhasil diupdate - total: " + total);

        // Bersih-bersih
        cartPage.removeItem(0);
        test.log(Status.INFO, "Bersih-bersih - hapus item dari cart");
    }

    @Test(priority = 4, description = "Test remove product dari cart")
    public void testRemoveProductFromCart() {
        test.log(Status.INFO, "Memulai test remove product dari cart");

        HomePage homePage = new HomePage(driver);
        CartPage cartPage = new CartPage(driver);

        homePage.goToHomePage();
        test.log(Status.INFO, "Buka halaman home");

        // Tambah produk ke cart
        homePage.addToCart(0);
        test.log(Status.INFO, "Tambah produk ke cart");

        // Buka cart
        homePage.goToCart();
        test.log(Status.INFO, "Buka halaman cart");

        // Verifikasi produk ada sebelum remove
        int beforeRemove = cartPage.getItemCount();
        Assert.assertTrue(beforeRemove > 0, "Harus ada produk sebelum remove");
        test.log(Status.INFO, "Jumlah item sebelum remove: " + beforeRemove);

        // Remove produk
        cartPage.removeItem(0);
        test.log(Status.INFO, "Remove produk dari cart");

        // Verifikasi cart kosong
        boolean isEmpty = cartPage.isEmpty();
        Assert.assertTrue(isEmpty, "Cart harus kosong setelah remove");
        test.log(Status.PASS, "Remove berhasil - cart kosong: " + isEmpty);
    }

    @Test(priority = 5, description = "Test cart total calculation")
    public void testCartTotalCalculation() {
        test.log(Status.INFO, "Memulai test cart total calculation");

        HomePage homePage = new HomePage(driver);
        CartPage cartPage = new CartPage(driver);

        homePage.goToHomePage();
        test.log(Status.INFO, "Buka halaman home");

        // Tambah produk ke cart
        homePage.addToCart(0);
        test.log(Status.INFO, "Tambah produk ke cart");

        // Buka cart
        homePage.goToCart();
        test.log(Status.INFO, "Buka halaman cart");

        // Dapatkan total cart
        String total = cartPage.getTotal();
        Assert.assertNotNull(total, "Cart total harus ditampilkan");
        Assert.assertFalse(total.isEmpty(), "Cart total tidak boleh kosong");
        test.log(Status.PASS, "Cart total calculation berhasil - total: " + total);

        // Bersih-bersih
        cartPage.removeItem(0);
        test.log(Status.INFO, "Bersih-bersih - hapus item dari cart");
    }

    @Test(priority = 6, description = "Test continue shopping functionality")
    public void testContinueShoppingFunctionality() {
        test.log(Status.INFO, "Memulai test continue shopping");

        HomePage homePage = new HomePage(driver);
        CartPage cartPage = new CartPage(driver);

        homePage.goToHomePage();
        test.log(Status.INFO, "Buka halaman home");

        // Tambah produk ke cart
        homePage.addToCart(0);
        test.log(Status.INFO, "Tambah produk ke cart");

        // Buka cart
        homePage.goToCart();
        test.log(Status.INFO, "Buka halaman cart");

        // Klik continue shopping
        cartPage.continueShopping();
        test.log(Status.INFO, "Klik continue shopping");

        // Verifikasi kembali ke home page
        Assert.assertTrue(driver.getCurrentUrl().contains("demowebshop"), "Harus kembali ke home page");
        test.log(Status.PASS, "Continue shopping berhasil - kembali ke home page");

        // Bersih-bersih
        homePage.goToCart();
        cartPage.removeItem(0);
        test.log(Status.INFO, "Bersih-bersih - hapus item dari cart");
    }

    @Test(priority = 7, description = "Test empty cart scenario")
    public void testEmptyCartScenario() {
        test.log(Status.INFO, "Memulai test empty cart scenario");

        CartPage cartPage = new CartPage(driver);

        // Buka cart page langsung
        cartPage.goToCartPage();
        test.log(Status.INFO, "Buka halaman cart langsung");

        // Verifikasi cart kosong
        boolean isEmpty = cartPage.isEmpty();
        Assert.assertTrue(isEmpty, "Cart baru harus kosong");
        test.log(Status.PASS, "Empty cart scenario berhasil - cart kosong: " + isEmpty);
    }

    @Test(priority = 8, description = "Test cart persistence setelah login")
    public void testCartPersistenceAfterLogin() {
        test.log(Status.INFO, "Memulai test cart persistence setelah login");

        HomePage homePage = new HomePage(driver);
        LoginPage loginPage = new LoginPage(driver);
        CartPage cartPage = new CartPage(driver);

        // Tambah produk sebelum login
        homePage.goToHomePage();
        homePage.addToCart(0);
        test.log(Status.INFO, "Tambah produk ke cart sebelum login");

        // Login
        loginPage.goToLoginPage();
        loginPage.login("testuser@example.com", "Test@123");
        test.log(Status.INFO, "Login ke akun");

        // Verifikasi cart items tetap ada
        homePage.goToHomePage();
        String cartQty = homePage.getCartItemCount();
        Assert.assertNotEquals(cartQty, "0", "Cart items harus tetap ada setelah login");
        test.log(Status.PASS, "Cart persistence berhasil - quantity: " + cartQty);

        // Bersih-bersih
        homePage.goToCart();
        cartPage.removeItem(0);
        loginPage.logout();
        test.log(Status.INFO, "Bersih-bersih - hapus item dan logout");
    }

    @Test(priority = 9, description = "Test maximum quantity validation")
    public void testMaximumQuantityValidation() {
        test.log(Status.INFO, "Memulai test maximum quantity validation");

        HomePage homePage = new HomePage(driver);
        CartPage cartPage = new CartPage(driver);

        homePage.goToHomePage();
        test.log(Status.INFO, "Buka halaman home");

        // Tambah produk ke cart
        homePage.addToCart(0);
        test.log(Status.INFO, "Tambah produk ke cart");

        // Buka cart
        homePage.goToCart();
        test.log(Status.INFO, "Buka halaman cart");

        // Coba set quantity sangat tinggi
        cartPage.updateQuantity(0, 9999);
        test.log(Status.INFO, "Coba set quantity ke 9999");

        // Verifikasi system handle tanpa error
        String total = cartPage.getTotal();
        Assert.assertNotNull(total, "System harus handle high quantity tanpa error");
        test.log(Status.PASS, "Maximum quantity test berhasil - total: " + total);

        // Bersih-bersih
        cartPage.removeItem(0);
        test.log(Status.INFO, "Bersih-bersih - hapus item dari cart");
    }

    @Test(priority = 10, description = "Test cart icon update")
    public void testCartIconUpdate() {
        test.log(Status.INFO, "Memulai test cart icon update");

        HomePage homePage = new HomePage(driver);
        CartPage cartPage = new CartPage(driver);

        homePage.goToHomePage();
        test.log(Status.INFO, "Buka halaman home");

        // Dapatkan quantity awal
        String initialQty = homePage.getCartItemCount();
        test.log(Status.INFO, "Cart quantity awal: " + initialQty);

        // Tambah produk ke cart
        homePage.addToCart(0);
        test.log(Status.INFO, "Tambah produk ke cart");

        // Verifikasi cart icon terupdate
        String updatedQty = homePage.getCartItemCount();
        Assert.assertNotEquals(updatedQty, initialQty, "Cart icon harus terupdate");
        test.log(Status.PASS, "Cart icon update berhasil - dari " + initialQty + " ke " + updatedQty);

        // Bersih-bersih
        homePage.goToCart();
        cartPage.removeItem(0);
        test.log(Status.INFO, "Bersih-bersih - hapus item dari cart");
    }
}