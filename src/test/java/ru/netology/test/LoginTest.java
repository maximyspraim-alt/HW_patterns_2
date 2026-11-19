package ru.netology.test;

import com.codeborne.selenide.Configuration;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.chrome.ChromeOptions;
import ru.netology.api.UserApi;
import ru.netology.data.DataGenerator;
import ru.netology.data.RegistrationDto;
import ru.netology.page.LoginPage;

import java.util.HashMap;
import java.util.Map;

import static com.codeborne.selenide.Selenide.open;

class LoginTest {

    private LoginPage loginPage;

    @BeforeEach
    void setUp() {
        ChromeOptions options = new ChromeOptions();
        Map<String, Object> prefs = new HashMap<>();
        prefs.put("profile.password_manager_leak_detection", false);
        prefs.put("credentials_enable_service", false);
        prefs.put("profile.password_manager_enabled", false);
        options.setExperimentalOption("prefs", prefs);
        Configuration.browserCapabilities = options;

        open("http://localhost:9999");
        loginPage = new LoginPage();
    }

    @Test
    void shouldLoginWithActiveUser() {
        RegistrationDto user = DataGenerator.generateActiveUser();
        UserApi.createUser(user);

        loginPage.login(user.getLogin(), user.getPassword());
        loginPage.waitForDashboard();
    }

    @Test
    void shouldNotLoginWithBlockedUser() {
        RegistrationDto user = DataGenerator.generateBlockedUser();
        UserApi.createUser(user);

        loginPage.login(user.getLogin(), user.getPassword());
        loginPage.waitForError("Ошибка! Пользователь заблокирован");
    }

    @Test
    void shouldNotLoginWithWrongPassword() {
        RegistrationDto user = DataGenerator.generateActiveUser();
        UserApi.createUser(user);

        loginPage.login(user.getLogin(), "wrong_password");
        loginPage.waitForError("Ошибка! Неверно указан логин или пароль");
    }

    @Test
    void shouldNotLoginWithNonExistentUser() {
        String login = DataGenerator.generateLogin();
        String password = DataGenerator.generatePassword();

        loginPage.login(login, password);
        loginPage.waitForError("Ошибка! Неверно указан логин или пароль");
    }
}