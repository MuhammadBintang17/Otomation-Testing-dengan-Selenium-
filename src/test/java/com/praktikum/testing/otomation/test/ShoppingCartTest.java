package com.praktikum.testing.otomation.test;

import com.aventstack.extentreports.Status;
import com.praktikum.testing.otomation.pages.CartPage;
import com.praktikum.testing.otomation.pages.HomePage;
import com.praktikum.testing.otomation.pages.LoginPage;
import com.praktikum.testing.otomation.pages.ProductPage; // Pastikan import ini ada
import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * Test class untuk feature Shopping Cart (10 test cases)
 * Updated: Menggunakan addProductSafe dan clearCart untuk stabilitas
 */
public class ShoppingCartTest extends BaseTest {

    // Helper method: Menambahkan produk dengan aman (menghindari Gift Card)
    private void addProductSafe(HomePage homePage, int productIndex) {
        homePage.goToHomePage();
        // Klik judul produk untuk masuk ke detail page (lebih stabil)
        homePage.clickProduct(productIndex);

        ProductPage productPage = new ProductPage(driver);
        productPage.addToCart();

        // Tunggu notifikasi sukses muncul agar tidak race condition
        if (productPage.isAddedToCart()) {
            test.log(Status.INFO, "Produk berhasil ditambahkan (notifikasi muncul)");
        } else {
            // Fallback wait jika notifikasi terlewat
            try { Thread.sleep(1500); } catch (InterruptedException e) {}
        }

        homePage.goToHomePage();
    }

    // Helper method: Membersihkan cart sampai kosong
    private void clearCart(CartPage cartPage) {
        cartPage.goToCartPage();
        int maxAttempts = 10;
        while (cartPage.getItemCount() > 0 && maxAttempts > 0) {
            cartPage.removeItem(0);
            maxAttempts--;
        }
        test.log(Status.INFO, "Cart dibersihkan");
    }

    @Test(priority = 1, description = "Test menambahkan single product ke cart")
    public void testAddSingleProductToCart() {
        test.log(Status.INFO, "Memulai test tambah single product ke cart");
        HomePage homePage = new HomePage(driver);
        CartPage cartPage = new CartPage(driver);

        // Gunakan Index 1 (Laptop) bukan 0 (Gift Card)
        addProductSafe(homePage, 1);
        test.log(Status.INFO, "Tambah produk (Laptop) ke cart");

        // Verifikasi cart quantity di header bertambah
        String cartQty = homePage.getCartItemCount();
        Assert.assertNotEquals(cartQty, "0", "Cart quantity header harus bertambah");
        test.log(Status.INFO, "Cart quantity header: " + cartQty);

        // Verifikasi di dalam halaman cart
        homePage.goToCart();
        int itemCount = cartPage.getItemCount();
        Assert.assertTrue(itemCount > 0, "Harus ada produk di halaman cart");

        test.log(Status.PASS, "Single product berhasil ditambah - Total item: " + itemCount);

        clearCart(cartPage);
    }

    @Test(priority = 2, description = "Test menambahkan multiple products ke cart")
    public void testAddMultipleProductsToCart() {
        test.log(Status.INFO, "Memulai test tambah multiple products ke cart");
        HomePage homePage = new HomePage(driver);
        CartPage cartPage = new CartPage(driver);

        // Tambah Produk 1 (Laptop)
        addProductSafe(homePage, 1);

        // Tambah Produk 2 (Build PC - Index 2)
        addProductSafe(homePage, 2);

        homePage.goToCart();
        int itemCount = cartPage.getItemCount();

        Assert.assertTrue(itemCount >= 2, "Harus ada minimal 2 produk di cart");
        test.log(Status.PASS, "Multiple products berhasil ditambah - Total item: " + itemCount);

        clearCart(cartPage);
    }

    @Test(priority = 3, description = "Test update product quantity di cart")
    public void testUpdateProductQuantity() {
        test.log(Status.INFO, "Memulai test update quantity di cart");
        HomePage homePage = new HomePage(driver);
        CartPage cartPage = new CartPage(driver);

        addProductSafe(homePage, 1);
        homePage.goToCart();

        // Pastikan cart tidak kosong sebelum update
        if (cartPage.getItemCount() > 0) {
            cartPage.updateQuantity(0, 3);
            test.log(Status.INFO, "Update quantity menjadi 3");

            String total = cartPage.getTotal();
            Assert.assertNotNull(total, "Cart total harus terupdate");
            test.log(Status.PASS, "Quantity berhasil diupdate - total: " + total);
        } else {
            Assert.fail("Cart kosong, gagal melakukan update quantity");
        }

        clearCart(cartPage);
    }

    @Test(priority = 4, description = "Test remove product dari cart")
    public void testRemoveProductFromCart() {
        test.log(Status.INFO, "Memulai test remove product dari cart");
        HomePage homePage = new HomePage(driver);
        CartPage cartPage = new CartPage(driver);

        addProductSafe(homePage, 1);
        homePage.goToCart();

        int beforeRemove = cartPage.getItemCount();
        Assert.assertTrue(beforeRemove > 0, "Harus ada produk sebelum remove");
        test.log(Status.INFO, "Jumlah item sebelum remove: " + beforeRemove);

        // Remove produk
        cartPage.removeItem(0);
        test.log(Status.INFO, "Remove produk dari cart");

        boolean isEmpty = cartPage.isEmpty();
        Assert.assertTrue(isEmpty, "Cart harus kosong setelah remove");
        test.log(Status.PASS, "Remove berhasil - cart kosong");
    }

    @Test(priority = 5, description = "Test cart total calculation")
    public void testCartTotalCalculation() {
        test.log(Status.INFO, "Memulai test cart total calculation");
        HomePage homePage = new HomePage(driver);
        CartPage cartPage = new CartPage(driver);

        addProductSafe(homePage, 1);
        homePage.goToCart();

        String total = cartPage.getTotal();
        Assert.assertNotNull(total, "Cart total harus ditampilkan");
        Assert.assertFalse(total.isEmpty(), "Cart total tidak boleh kosong");

        test.log(Status.PASS, "Cart total calculation berhasil - total: " + total);

        clearCart(cartPage);
    }

    @Test(priority = 6, description = "Test continue shopping functionality")
    public void testContinueShoppingFunctionality() {
        test.log(Status.INFO, "Memulai test continue shopping");
        HomePage homePage = new HomePage(driver);
        CartPage cartPage = new CartPage(driver);

        addProductSafe(homePage, 1);
        homePage.goToCart();

        cartPage.continueShopping();
        test.log(Status.INFO, "Klik continue shopping");

        Assert.assertTrue(driver.getCurrentUrl().contains("demowebshop"), "Harus kembali ke home page");
        test.log(Status.PASS, "Continue shopping berhasil");

        clearCart(cartPage);
    }

    @Test(priority = 7, description = "Test empty cart scenario")
    public void testEmptyCartScenario() {
        test.log(Status.INFO, "Memulai test empty cart scenario");
        CartPage cartPage = new CartPage(driver);

        // Pastikan cart bersih dulu
        clearCart(cartPage);

        // Buka cart page lagi
        cartPage.goToCartPage();

        boolean isEmpty = cartPage.isEmpty();
        Assert.assertTrue(isEmpty, "Cart baru harus kosong");
        test.log(Status.PASS, "Empty cart scenario berhasil");
    }

    @Test(priority = 8, description = "Test cart persistence setelah login")
    public void testCartPersistenceAfterLogin() {
        test.log(Status.INFO, "Memulai test cart persistence setelah login");
        HomePage homePage = new HomePage(driver);
        LoginPage loginPage = new LoginPage(driver);
        CartPage cartPage = new CartPage(driver);

        // 1. Tambah produk saat kondisi Guest (belum login)
        addProductSafe(homePage, 1);
        test.log(Status.INFO, "Tambah produk ke cart sebelum login");

        // 2. Login
        loginPage.goToLoginPage();
        // Gunakan akun dummy valid
        loginPage.login("testuser@example.com", "Test@123");
        test.log(Status.INFO, "Login ke akun");

        // 3. Verifikasi cart tetap ada di header
        homePage.goToHomePage();
        String cartQty = homePage.getCartItemCount();
        Assert.assertNotEquals(cartQty, "0", "Cart items harus tetap ada setelah login");
        test.log(Status.PASS, "Cart persistence berhasil - quantity: " + cartQty);

        // Cleanup
        clearCart(cartPage);
        loginPage.logout();
    }

    @Test(priority = 9, description = "Test maximum quantity validation")
    public void testMaximumQuantityValidation() {
        test.log(Status.INFO, "Memulai test maximum quantity validation");
        HomePage homePage = new HomePage(driver);
        CartPage cartPage = new CartPage(driver);

        addProductSafe(homePage, 1);
        homePage.goToCart();

        if (cartPage.getItemCount() > 0) {
            // Coba set quantity sangat tinggi
            cartPage.updateQuantity(0, 9999);
            test.log(Status.INFO, "Set quantity ke 9999");

            // Verifikasi sistem handle tanpa crash (Total masih muncul)
            String total = cartPage.getTotal();
            Assert.assertNotNull(total, "System harus handle high quantity tanpa error");
            test.log(Status.PASS, "Maximum quantity test berhasil");
        } else {
            Assert.fail("Cart kosong");
        }

        clearCart(cartPage);
    }

    @Test(priority = 10, description = "Test cart icon update")
    public void testCartIconUpdate() {
        test.log(Status.INFO, "Memulai test cart icon update");
        HomePage homePage = new HomePage(driver);
        CartPage cartPage = new CartPage(driver);

        homePage.goToHomePage();
        String initialQty = homePage.getCartItemCount();
        test.log(Status.INFO, "Quantity awal: " + initialQty);

        addProductSafe(homePage, 1);

        String updatedQty = homePage.getCartItemCount();
        Assert.assertNotEquals(updatedQty, initialQty, "Cart icon header harus terupdate");
        test.log(Status.PASS, "Cart icon update berhasil");

        clearCart(cartPage);
    }
}