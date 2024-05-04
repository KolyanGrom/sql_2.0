package ru.netology.test;


import org.junit.jupiter.api.*;
import ru.netology.data.DataHelper;
import ru.netology.data.SQLHelper;
import ru.netology.page.LoginPage;

import static com.codeborne.selenide.Selenide.open;


public class AuthTest {
    LoginPage loginPage;

    @AfterEach
    void tearDown() {
        SQLHelper.cleanAuthCodes();
    }

    @BeforeEach
    void setUp() {
        loginPage = open("http://localhost:9999", LoginPage.class);

    }

    @AfterAll
    static void cleanUp() {
        SQLHelper.cleanDatabase();
    }

    @Test
    @DisplayName("Should successfuly login to dashboard wiht exist login and password from sut test data")
    void shouldSuccessfulLogin() {
        var userData = DataHelper.getUserDataWinTestData();
        var verificationPage = loginPage.validLogin(userData);
        verificationPage.verifyVerificationPageVisibility();
        var verificationCode = SQLHelper.getVerificationCode();
        verificationPage.validVerify(verificationCode);
    }

    @Test
    @DisplayName("Should get error notification if user is not exit in base")
    void shouldGetErrorRandomUserNotAddingToBase() {
        var userData = DataHelper.generateRandomUser();
        loginPage.validLogin(userData);
        loginPage.verifyErrorNotification("Ошибка! \nНеверно указан логин или пароль");
    }

    @Test
    @DisplayName("Should get error notification if ligin whith exist in base and active user and random verification code")
    void shouldGetErrorNotificationIfLoginWihtExistsUserAndRandomVerificationCode() {
        var userData = DataHelper.getUserDataWinTestData();
        var verificationPage = loginPage.validLogin(userData);
        verificationPage.verifyVerificationPageVisibility();
        var verificationCode = DataHelper.generateRandomVerificationCode();
        verificationPage.verify(verificationCode.getCode());
        verificationPage.verifyErrorNotification("Ошибка! \nНеверно указан код! Попробуйте ещё раз.");
    }
}