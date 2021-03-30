package ru.netology.web;

import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selectors.*;
import static com.codeborne.selenide.Selenide.*;

class DeliveryOrderTest {
    LocalDate today = LocalDate.now();
    LocalDate newDate = today.plusDays(7);
    DateTimeFormatter format = DateTimeFormatter.ofPattern("dd.MM.yyyy");
    int daysInCurrentMonth = today.lengthOfMonth();
    int day = today.getDayOfMonth();
    int rest = 0;
    String str = "";

    @Test
    void shouldFillingFieldCity() {
        open("http://localhost:9999");
        $("[data-test-id='city'] input").setValue("Мо");
        $$(".menu-item__control").find(exactText("Москва")).click();
        $("[data-test-id='date'] .input__control").doubleClick().sendKeys(format.format(newDate));
        $("[data-test-id='name'] input").setValue("Николай Римский-Корсаков");
        $("[data-test-id='phone'] input").setValue("+12345678901");
        $("[data-test-id='agreement']").click();
        $$(".button").find(exactText("Забронировать")).click();
        $(byText("Успешно!")).shouldBe(visible, Duration.ofMillis(15000));
    }

    @Test
    void shouldFillingFieldDate() {
        open("http://localhost:9999");
        $("[data-test-id='city'] input").setValue("Мо");
        $$(".menu-item__control").find(exactText("Москва")).click();
        $("button.icon-button").click();
        if ((daysInCurrentMonth - day) >= 7) {
            rest = day + 7;
            str = Integer.toString(rest);
            $$(".calendar__day").find(exactText(str)).click();
        } else if (((daysInCurrentMonth - day) < 7) && ((daysInCurrentMonth - day) > 3)) {
            rest = 7 - (daysInCurrentMonth - day);
            str = Integer.toString(rest);
            $(".calendar__arrow_direction_right[data-step='1']").click();
            $$(".calendar__day").find(exactText(str)).click();
        } else if ((daysInCurrentMonth - day) <= 3) {
            rest = 7 - (daysInCurrentMonth - day);
            str = Integer.toString(rest);
            $$(".calendar__day").find(exactText(str)).click();
        }
        $("[data-test-id='name'] input").setValue("Николай Римский-Корсаков");
        $("[data-test-id='phone'] input").setValue("+12345678901");
        $("[data-test-id='agreement']").click();
        $$(".button").find(exactText("Забронировать")).click();
        $(byText("Успешно!")).shouldBe(visible, Duration.ofMillis(15000));
    }
}

