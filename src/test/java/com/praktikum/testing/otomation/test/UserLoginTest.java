package com.praktikum.testing.otomation.test;

import com.aventstack.extentreports.Status;
import com.praktikum.testing.otomation.pages.HomePage;
import com.praktikum.testing.otomation.pages.LoginPage;
import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * Test class untuk feature User Login (8 test cases)
 */
public class UserLoginTest extends BaseTest {

    @Test(priority = 1, description = "Test login berhasil dengan credentials valid")
    public void testSuccessfulLogin() {
        test.log(Status.INFO, "Memulai test login berhasil");

        LoginPage loginPage = new LoginPage(driver);
        HomePage homePage = new HomePage(driver);

        // Buka halaman login
        loginPage.goToLoginPage();
        test.log(Status.INFO, "Buka halaman login");

        // Login dengan credentials valid
        // Gunakan akun dummy yang valid atau yang baru diregister
        loginPage.login("testuser@example.com", "Test@123");
        test.log(Status.INFO, "Login dengan email dan password valid");

        // Verifikasi login berhasil
        Assert.assertTrue(loginPage.isLoginSuccess(), "Login harus berhasil");
        Assert.assertTrue(homePage.isUserLoggedIn(), "User harus terlihat logged in");
        test.log(Status.PASS, "Login berhasil - user bisa login");

        // Logout untuk cleanup
        loginPage.logout();
        test.log(Status.INFO, "Logout untuk bersih-bersih");
    }

    @Test(priority = 2, description = "Test login gagal dengan credentials invalid")
    public void testLoginWithInvalidCredentials() {
        test.log(Status.INFO, "Memulai test login dengan credentials invalid");

        LoginPage loginPage = new LoginPage(driver);

        loginPage.goToLoginPage();
        test.log(Status.INFO, "Buka halaman login");

        // Login dengan credentials invalid
        loginPage.login("invalid@example.com", "WrongPassword123");
        test.log(Status.INFO, "Login dengan email dan password salah");

        // Verifikasi error message muncul
        String errorMessage = loginPage.getLoginError();
        Assert.assertFalse(errorMessage.isEmpty(), "Pesan error harus ditampilkan");

        // Pesan error bisa "Login was unsuccessful" atau spesifik lainnya
        Assert.assertTrue(errorMessage.contains("Login was unsuccessful"),
                "Pesan harus tentang login gagal");

        test.log(Status.PASS, "Login gagal - pesan error: " + errorMessage);
    }

    @Test(priority = 3, description = "Test login dengan email kosong")
    public void testLoginWithEmptyEmail() {
        test.log(Status.INFO, "Memulai test login dengan email kosong");

        LoginPage loginPage = new LoginPage(driver);

        loginPage.goToLoginPage();
        test.log(Status.INFO, "Buka halaman login");

        // Login hanya dengan password (email kosong)
        loginPage.login("", "Test@123");
        test.log(Status.INFO, "Login dengan email kosong");

        // Verifikasi validation error
        String emailError = loginPage.getEmailError();
        Assert.assertFalse(emailError.isEmpty(), "Error validasi email harus ditampilkan");
        test.log(Status.PASS, "Validasi berhasil - error: " + emailError);
    }

    @Test(priority = 4, description = "Test login dengan password kosong")
    public void testLoginWithEmptyPassword() {
        test.log(Status.INFO, "Memulai test login dengan password kosong");

        LoginPage loginPage = new LoginPage(driver);

        loginPage.goToLoginPage();
        test.log(Status.INFO, "Buka halaman login");

        // Login hanya dengan email (password kosong)
        loginPage.login("test@example.com", "");
        test.log(Status.INFO, "Login dengan password kosong");

        // Verifikasi login gagal (tetap di halaman login / error message)
        Assert.assertFalse(loginPage.isLoginSuccess(), "Login harus gagal tanpa password");
        test.log(Status.PASS, "Validasi berhasil - login gagal tanpa password");
    }

    @Test(priority = 5, description = "Test case sensitivity pada email")
    public void testLoginCaseSensitivity() {
        test.log(Status.INFO, "Memulai test case sensitivity");

        LoginPage loginPage = new LoginPage(driver);

        loginPage.goToLoginPage();
        test.log(Status.INFO, "Buka halaman login");

        // Test dengan email uppercase (Email biasanya case-insensitive)
        loginPage.login("TESTUSER@EXAMPLE.COM", "Test@123");
        test.log(Status.INFO, "Login dengan email uppercase");

        // Cek hasil (tergantung sistem, bisa berhasil atau gagal)
        // Di DemoWebShop biasanya berhasil karena email case-insensitive
        boolean result = loginPage.isLoginSuccess();
        test.log(Status.INFO, "Hasil login dengan uppercase: " + result);

        // Jika berhasil, logout
        if (result) {
            loginPage.logout();
            test.log(Status.INFO, "Logout setelah test");
        }

        test.log(Status.PASS, "Test case sensitivity selesai");
    }

    @Test(priority = 6, description = "Test remember me functionality")
    public void testRememberMeFunctionality() {
        test.log(Status.INFO, "Memulai test remember me");

        LoginPage loginPage = new LoginPage(driver);

        loginPage.goToLoginPage();
        test.log(Status.INFO, "Buka halaman login");

        // Login dengan remember me
        loginPage.loginWithRememberMe("testuser@example.com", "Test@123");
        test.log(Status.INFO, "Login dengan remember me checked");

        // Verifikasi login berhasil
        Assert.assertTrue(loginPage.isLoginSuccess(), "Login dengan remember me harus berhasil");
        test.log(Status.PASS, "Login dengan remember me berhasil");

        // Logout
        loginPage.logout();
        test.log(Status.INFO, "Logout untuk bersih-bersih");
    }

    @Test(priority = 7, description = "Test logout functionality")
    public void testLogoutFunctionality() {
        test.log(Status.INFO, "Memulai test logout");

        LoginPage loginPage = new LoginPage(driver);
        HomePage homePage = new HomePage(driver);

        // Login dulu
        loginPage.goToLoginPage();
        loginPage.login("testuser@example.com", "Test@123");
        test.log(Status.INFO, "Login terlebih dahulu");

        // Verifikasi sudah login
        Assert.assertTrue(loginPage.isLoginSuccess(), "Harus sudah login sebelum logout");
        test.log(Status.INFO, "Verifikasi login berhasil");

        // Logout
        loginPage.logout();
        test.log(Status.INFO, "Melakukan logout");

        // Verifikasi logout berhasil
        Assert.assertTrue(loginPage.isLogoutSuccess(), "Logout harus berhasil");
        Assert.assertFalse(homePage.isUserLoggedIn(), "User harus terlihat logout");
        test.log(Status.PASS, "Logout berhasil - user bisa logout");
    }

    @Test(priority = 8, description = "Test session persistence setelah login")
    public void testSessionPersistence() {
        test.log(Status.INFO, "Memulai test session persistence");

        LoginPage loginPage = new LoginPage(driver);
        HomePage homePage = new HomePage(driver);

        // Login
        loginPage.goToLoginPage();
        loginPage.login("testuser@example.com", "Test@123");
        test.log(Status.INFO, "Login terlebih dahulu");

        // Buka halaman home (navigasi)
        homePage.goToHomePage();
        test.log(Status.INFO, "Buka halaman home");

        // Verifikasi masih login
        Assert.assertTrue(homePage.isUserLoggedIn(), "User harus tetap login setelah navigasi");
        test.log(Status.PASS, "Session persistence berhasil - user tetap login");

        // Logout
        loginPage.logout();
        test.log(Status.INFO, "Logout untuk bersih-bersih");
    }
}