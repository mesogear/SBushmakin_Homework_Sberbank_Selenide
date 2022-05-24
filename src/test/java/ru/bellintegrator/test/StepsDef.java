package ru.bellintegrator.test;

import cucumber.api.java.ru.*;

import static com.codeborne.selenide.Selenide.open;
import static java.lang.Integer.parseInt;

public class StepsDef extends Steps {

    static SberMainPage mainPage;
    static SberSearchResultsPage searchPage;

    @Пусть("пользователь запускает Google Chrome")
    public void пользователь_запускает_Google_Chrome() {
        открытьХром();
    }

    @Пусть("открывает сайт {string}")
    public void открывает_сайт(String url) {
        open(url);
    }

    @Затем("вводит в поле поиска {string}")
    public void вводит_в_поле_поиска(String searchQuery) {
        mainPage = new SberMainPage();
        mainPage.typeSearchQuery(searchQuery);
    }

    @Затем("нажимает поиск")
    public void нажимает_поиск() {
        mainPage.clickSearchButton();
    }

    @Тогда("задаем количество результатов поиска на страницу {string}")
    public void задаемКоличествоРезультатовПоискаНаСтраницу(String resultsPerPage) {
        searchPage = new SberSearchResultsPage();
        searchPage.setResultsPerPage(parseInt(resultsPerPage));
    }

    @Также("проверяем первые {string} результатов, смотрим название, цену и номер")
    public void проверяемПервыеРезультатовСмотримНазваниеЦенуИНомерПервыхРезультатовСЦенойВыше(String allResultsAmount) {
        searchPage.setPagesAmount(parseInt(allResultsAmount), searchPage.getResultsPerPageAmount())
                .setTenders(searchPage.getPagesAmount());
    }

    @Тогда("выводим в Allure и консоль название, цену и номер первых {string} результатов с ценой выше {string} и типом {string}")
    public void выводим_информацию_в_Allure_и_консоль(String filteredResultsAmount, String price, String type) {
        searchPage.getFilteredTenders(parseInt(filteredResultsAmount), parseInt(price), type);
    }

    @Тогда("закрываем Google Chrome")
    public void закрываем_Google_Chrome() {
    }

}
