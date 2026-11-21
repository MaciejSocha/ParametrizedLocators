package io.github.maciejsocha.factory;

import io.github.maciejsocha.annotation.ParamFindBy;
import io.github.maciejsocha.locator.ParametrizedLocator;
import org.openqa.selenium.WebDriver;

import java.lang.reflect.Field;

public class ParametrizedLocatorFactory {
    public static void initElements(WebDriver driver, Object page) {
        for (Field field : page.getClass().getDeclaredFields()) {
            ParamFindBy annotation = field.getAnnotation(ParamFindBy.class);

            if (annotation == null) continue;

            ParametrizedLocator locator = null;
            if (!"".equals(annotation.xpath())) locator = new ParametrizedLocator(driver, annotation.xpath());
            if (!"".equals(annotation.id())) locator = new ParametrizedLocator(driver, annotation.id());

            if (locator == null) throw new NullPointerException();

            try {
                field.setAccessible(true);
                field.set(page, locator);
            } catch (IllegalAccessException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
