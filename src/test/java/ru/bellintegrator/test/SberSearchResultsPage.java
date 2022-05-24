package ru.bellintegrator.test;

import com.codeborne.selenide.Condition;
import io.qameta.allure.Allure;
import org.openqa.selenium.By;

import java.util.ArrayList;
import java.util.List;

import static com.codeborne.selenide.Selenide.*;
import static java.lang.Double.parseDouble;

/** Класс страницы Сбербанк-АСТ с результатами поиска */

public class SberSearchResultsPage extends BasePage {

    /** Количество страниц поиска */
    protected int pagesAmount;

    /** Количество результатов поиска на странице */
    protected int optionResultsPerPage;

    /** Список названий заказов */
    protected List<String> names = new ArrayList<>();

    /** Список номеров заказов */
    protected List<String> numbers = new ArrayList<>();

    /** Список стоимости заказов */
    protected List<String> prices = new ArrayList<>();

    /** Список типов заказов */
    protected List<String> types = new ArrayList<>();

    /**
     * Метод устанавливает количество результатов поиска на странице
     *
     * @author SBushmakin
     * @param amount устанавливаемое количество результатов поиска
     * @return возвращает объект, вызвавший метод
     */
    public SberSearchResultsPage setResultsPerPage(int amount) {
        $(By.xpath("//select[@id='headerPagerSelect']")).click();
        $(By.xpath("//select[@id='headerPagerSelect']/option[contains( . , '" + amount + "')]")).click();
        this.optionResultsPerPage = amount;
        return this;
    }

    /**
     * Метод возвращает количество результатов поиска на странице
     *
     * @author SBushmakin
     * @return возвращает количество результатов на странице
     */
    public int getResultsPerPageAmount() {
        return optionResultsPerPage;
    }

    /**
     * Метод принимает значение с количеством результатов, которые необходимо
     * проверить, значение с количеством результатов на странице и
     * находит число страниц
     *
     * @author SBushmakin
     * @param allResults количество результатов для проверки
     * @param resultsPerPage количество результатов на странице
     * @return возвращает объект, вызвавший метод
     */
    public SberSearchResultsPage setPagesAmount(int allResults, int resultsPerPage) {
        this.pagesAmount = allResults / resultsPerPage;
        return this;
    }

    /**
     * Метод принимает количество страниц для проверки и заполняет списки с
     * данными результатов поиска
     *
     * @param pagesAmount количество страниц для проверки
     * @return возвращает объект, вызвавший метод
     * @author SBushmakin
     */
    public SberSearchResultsPage setTenders(int pagesAmount) {
        for (int i = 0; i < pagesAmount; i++) {
            $(By.xpath("//div[@id='loading']")).shouldBe(Condition.disappear);
            names.addAll($$(By.xpath("//div[@class='es-el-type-name']")).texts());
            numbers.addAll($$(By.xpath("//span[@class='es-el-code-term']")).texts());
            types.addAll($$(By.xpath("//span[@class='es-el-source-term']")).texts());
            prices.addAll($$(By.xpath("//span[@class='es-el-amount']")).texts());
            $(By.xpath("//div[@id='footerPager']//span[@id='pageButton']/span[text()='>']")).click();
        }
        return this;
    }

    /**
     * Метод принимает количество заказов, которые нужно вывести, их тип и миниальную стоимость,
     * затем выводит название, номер и цену подходящих заказов в консоль, Allure-отчет
     * и добавляет в Allure-отчет json c найденными данными
     *
     * @param filteredResultsAmount количество заказов для вывода
     * @param price минимальная стоимость
     * @param type тип заказа
     * @return возвращает объект, вызвавший метод
     * @author SBushmakin
     */
    public SberSearchResultsPage getFilteredTenders(int filteredResultsAmount, int price, String type) {
        StringBuilder formattedResult = new StringBuilder();
        for (int i = 0; i < prices.size(); i++) {
            if (!prices.get(i).isEmpty() && (parseDouble(prices.get(i).replace(" ", "")) > price)
            && types.get(i).equals(type)) {
                String filteredResult = "Название: " + names.get(i) + " Номер: " + numbers.get(i) + " Цена: " + prices.get(i).replace(" ", "");
                System.out.println(filteredResult);
                Allure.addAttachment("Информация о заказе", filteredResult);
                formattedResult.append("{\"Название\": \"").append(names.get(i)).append("\",\n\"Номер\": \"").append(numbers.get(i)).append("\",\n\"Цена\": ").append(prices.get(i).replace(" ", "")).append("}\n");
                filteredResultsAmount--;
                if (filteredResultsAmount == 0) {
                    break;
                }
            }
        }
        Allure.addAttachment("Информация о заказах в формате json", "application/json", formattedResult.toString());
        return this;
    }

    /**
     * Метод возвращает количество страниц, которые надо проверить
     *
     * @author SBushmakin
     * @return возвращает количество страниц, которые надо проверить
     */
    public int getPagesAmount() {
        return pagesAmount;
    }

}