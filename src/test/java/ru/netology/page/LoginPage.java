package ru.netology.page;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;

import java.time.Duration;

import static com.codeborne.selenide.Selenide.$;

public class LoginPage {

    private final SelenideElement loginInput = $("[data-test-id='login'] input");
    private final SelenideElement passwordInput = $("[data-test-id='password'] input");
    private final SelenideElement loginButton = $("[data-test-id='action-login']");
    private final SelenideElement errorNotification = $("[data-test-id='error-notification']");
    private final SelenideElement dashboardHeading = $("h2.heading");

    public void login(String login, String password) {
        loginInput.setValue(login);
        passwordInput.setValue(password);
        loginButton.click();
    }

    public void waitForDashboard() {
        dashboardHeading
                .shouldBe(Condition.visible, Duration.ofSeconds(15))
                .shouldHave(Condition.text(expectedText), Duration.ofSeconds(15));
    }

    public void waitForError(String expectedText) {
        errorNotification
                .shouldBe(Condition.visible, Duration.ofSeconds(15))
                .shouldHave(Condition.text(expectedText), Duration.ofSeconds(15));
    }
}