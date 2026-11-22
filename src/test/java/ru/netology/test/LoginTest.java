package ru.netology.test;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.netology.api.UserApi;
import ru.netology.data.DataGenerator;
import ru.netology.data.RegistrationDto;
import ru.netology.page.LoginPage;

import static com.codeborne.selenide.Selenide.open;

class LoginTest {

    private LoginPage loginPage;

    @BeforeEach
    void setUp() {
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

        String wrongPassword = DataGenerator.generateWrongPassword(user.getPassword());
        loginPage.login(user.getLogin(), wrongPassword);
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