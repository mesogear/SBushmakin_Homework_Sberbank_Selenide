package ru.bellintegrator.test;

import com.codeborne.selenide.Configuration;

public class Steps {

    /**
     * Метод открывает браузер Хром на полный размер окна и устанавливает ожидание
     * @author SBushmakin
     */
    public void открытьХром(){
        Configuration.timeout = 6000;
        Configuration.browser = "chrome";
        Configuration.browserSize = "1280x720";
    }
}