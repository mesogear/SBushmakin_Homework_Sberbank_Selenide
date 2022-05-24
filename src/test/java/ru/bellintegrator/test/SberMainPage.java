package ru.bellintegrator.test;


import org.openqa.selenium.By;

import static com.codeborne.selenide.Selenide.$;

/** Класс стартовой страницы Сбербанк-АСТ */

public class SberMainPage extends BasePage {

    /**
     * Метод принимает поисковый запрос и вводит его в поисковую строку
     *
     * @author SBushmakin
     * @param searchQuery поисковый запрос
     * @return возвращает объект, вызвавший метод
     */
    public SberMainPage typeSearchQuery(String searchQuery) {
        $(By.xpath("//input[@id='txtUnitedPurchaseSearch']")).sendKeys(searchQuery);
        return this;
    }

    /**
     * Метод нажимает на кнопку поиска
     *
     * @author SBushmakin
     * @return возвращает объект, вызвавший метод
     */
    public SberMainPage clickSearchButton() {
        $(By.xpath("//input[@id='btnUnitedPurchaseSearch']")).click();
        return this;
    }

}
