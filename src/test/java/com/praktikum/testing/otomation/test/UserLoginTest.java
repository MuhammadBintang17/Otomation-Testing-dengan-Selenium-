package com.praktikum.testing.otomation.test;

import com.aventstack.extentreports.Status;
import com.praktikum.testing.otomation.pages.HomePage;
import com.praktikum.testing.otomation.pages.LoginPage;
import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * Test class untuk feature User Login (8 test cases)
 * Mencakup skenario positif, negatif, validasi field, dan session.
 */
public class UserLoginTest extends BaseTest {

    // Ganti credentials ini dengan akun yang SUDAH TERDAFTAR
    private final String VALID_EMAIL = "testuser@example.com";
    private final String VALID_PASSWORD = "Test@123";

    @Test(priority = 1, description = "Test login berhasil dengan credentials valid")
    public void testSuccessfulLogin() {
        test.log(Status.INFO, "Memulai test login berhasil");

        LoginPage loginPage = new LoginPage(driver);
        HomePage homePage = new HomePage(driver);

        // Buka halaman login
        loginPage.goToLoginPage();
        test.log(Status.INFO, "Buka halaman login");

        // Login dengan credentials valid
        loginPage.login(VALID_EMAIL, VALID_PASSWORD);
        test.log(Status.INFO, "Login dengan email: " + VALID_EMAIL);

        // Verifikasi login berhasil
        // Jika gagal disini, berarti akun belum terdaftar atau password salah
        if (!loginPage.isLoginSuccess()) {
            test.log(Status.FAIL, "Login gagal. Pesan error: " + loginPage.getLoginError());
        }

        Assert.assertTrue(loginPage.isLoginSuccess(), "Login harus berhasil (Cek apakah akun sudah diregistrasi?)");
        Assert.assertTrue(homePage.isUserLoggedIn(), "User harus terlihat logged in di homepage");
        test.log(Status.PASS, "Login berhasil - user masuk ke sistem");

        // Logout untuk cleanup agar siap untuk test berikutnya
        loginPage.logout();
        test.log(Status.INFO, "Logout berhasil");
    }

    @Test(priority = 2, description = "Test login gagal dengan credentials invalid")
    public void testLoginWithInvalidCredentials() {
        test.log(Status.INFO, "Memulai test login dengan credentials invalid");

        LoginPage loginPage = new LoginPage(driver);
        loginPage.goToLoginPage();

        // Login dengan credentials asal
        loginPage.login("invalid_email_123@test.com", "WrongPass!");
        test.log(Status.INFO, "Input credentials invalid");

        // Verifikasi error message muncul
        String errorMessage = loginPage.getLoginError();
        Assert.assertFalse(errorMessage.isEmpty(), "Pesan error harus muncul");
        Assert.assertTrue(errorMessage.contains("Login was unsuccessful"), "Pesan error tidak sesuai");

        test.log(Status.PASS, "Login gagal sesuai ekspektasi - Error: " + errorMessage);
    }

    @Test(priority = 3, description = "Test login dengan email kosong")
    public void testLoginWithEmptyEmail() {
        test.log(Status.INFO, "Memulai test login dengan email kosong");

        LoginPage loginPage = new LoginPage(driver);
        loginPage.goToLoginPage();

        // Login hanya dengan password
        loginPage.login("", VALID_PASSWORD);
        test.log(Status.INFO, "Input email kosong");

        // Verifikasi validation error pada field email
        String emailError = loginPage.getEmailError();
        Assert.assertFalse(emailError.isEmpty(), "Error validasi email harus muncul");

        test.log(Status.PASS, "Validasi email kosong berhasil - Error: " + emailError);
    }

    @Test(priority = 4, description = "Test login dengan password kosong")
    public void testLoginWithEmptyPassword() {
        test.log(Status.INFO, "Memulai test login dengan password kosong");

        LoginPage loginPage = new LoginPage(driver);
        loginPage.goToLoginPage();

        // Login hanya dengan email
        loginPage.login(VALID_EMAIL, "");
        test.log(Status.INFO, "Input password kosong");

        // Verifikasi login gagal (tetap di halaman login)
        Assert.assertFalse(loginPage.isLoginSuccess(), "Seharusnya tidak bisa login tanpa password");
        test.log(Status.PASS, "Validasi password kosong berhasil");
    }

    @Test(priority = 5, description = "Test case sensitivity pada email")
    public void testLoginCaseSensitivity() {
        test.log(Status.INFO, "Memulai test case sensitivity (Email UPPERCASE)");

        LoginPage loginPage = new LoginPage(driver);
        loginPage.goToLoginPage();

        // Login dengan email HURUF BESAR (Domain email biasanya case-insensitive)
        loginPage.login(VALID_EMAIL.toUpperCase(), VALID_PASSWORD);
        test.log(Status.INFO, "Login dengan: " + VALID_EMAIL.toUpperCase());

        // Verifikasi login berhasil
        boolean result = loginPage.isLoginSuccess();
        Assert.assertTrue(result, "Login harusnya berhasil walau email uppercase");

        test.log(Status.PASS, "Case sensitivity test berhasil");

        // Logout
        if (result) loginPage.logout();
    }

    @Test(priority = 6, description = "Test remember me functionality")
    public void testRememberMeFunctionality() {
        test.log(Status.INFO, "Memulai test Remember Me");

        LoginPage loginPage = new LoginPage(driver);
        loginPage.goToLoginPage();

        // Login dengan centang Remember Me
        loginPage.loginWithRememberMe(VALID_EMAIL, VALID_PASSWORD);
        test.log(Status.INFO, "Login dengan Remember Me dicentang");

        Assert.assertTrue(loginPage.isLoginSuccess(), "Login harus berhasil");
        test.log(Status.PASS, "Login dengan Remember Me sukses");

        loginPage.logout();
    }

    @Test(priority = 7, description = "Test logout functionality")
    public void testLogoutFunctionality() {
        test.log(Status.INFO, "Memulai test Logout");

        LoginPage loginPage = new LoginPage(driver);
        HomePage homePage = new HomePage(driver);

        // Login dulu
        loginPage.goToLoginPage();
        loginPage.login(VALID_EMAIL, VALID_PASSWORD);

        // Pastikan login sukses dulu
        Assert.assertTrue(loginPage.isLoginSuccess(), "Pre-condition: Login gagal");

        // Lakukan Logout
        loginPage.logout();
        test.log(Status.INFO, "Tombol logout diklik");

        // Verifikasi
        Assert.assertTrue(loginPage.isLogoutSuccess(), "Tombol Login harus muncul kembali");
        Assert.assertFalse(homePage.isUserLoggedIn(), "Link akun tidak boleh terlihat");

        test.log(Status.PASS, "Logout berhasil");
    }

    @Test(priority = 8, description = "Test session persistence setelah login")
    public void testSessionPersistence() {
        test.log(Status.INFO, "Memulai test Session Persistence");

        LoginPage loginPage = new LoginPage(driver);
        HomePage homePage = new HomePage(driver);

        // Login
        loginPage.goToLoginPage();
        loginPage.login(VALID_EMAIL, VALID_PASSWORD);

        // Pindah halaman (Navigasi ke Home)
        homePage.goToHomePage();
        test.log(Status.INFO, "Navigasi ke Homepage");

        // Verifikasi user masih logged in
        Assert.assertTrue(homePage.isUserLoggedIn(), "User harus tetap login setelah pindah halaman");
        test.log(Status.PASS, "Session persistence aman");

        loginPage.logout();
    }
}