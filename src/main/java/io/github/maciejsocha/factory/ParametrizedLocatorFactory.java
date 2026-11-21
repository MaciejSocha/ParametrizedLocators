package io.github.maciejsocha.factory;

import io.github.maciejsocha.annotation.ParamFindBy;
import io.github.maciejsocha.locator.ParametrizedLocator;
import org.jspecify.annotations.NonNull;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.How;

import java.lang.reflect.Field;

public class ParametrizedLocatorFactory {
    public static void initElements(WebDriver driver, Object page) {
        for (Field field : page.getClass().getDeclaredFields()) {
            ParamFindBy annotation = field.getAnnotation(ParamFindBy.class);

            if (annotation == null) continue;

            ParametrizedLocator locator = getLocator(driver, annotation);

            try {
                field.setAccessible(true);
                field.set(page, locator);
            } catch (IllegalAccessException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private static @NonNull ParametrizedLocator getLocator(WebDriver driver, ParamFindBy annotation) {
        ParametrizedLocator locator = null;
        if (!"".equals(annotation.id())) locator = new ParametrizedLocator(driver, annotation.id(), How.ID);
        if (!"".equals(annotation.name())) locator = new ParametrizedLocator(driver, annotation.name(), How.NAME);
        if (!"".equals(annotation.className())) locator = new ParametrizedLocator(driver, annotation.className(), How.CLASS_NAME);
        if (!"".equals(annotation.css())) locator = new ParametrizedLocator(driver, annotation.css(), How.CSS);
        if (!"".equals(annotation.tagName())) locator = new ParametrizedLocator(driver, annotation.tagName(), How.TAG_NAME);
        if (!"".equals(annotation.linkText())) locator = new ParametrizedLocator(driver, annotation.linkText(), How.LINK_TEXT);
        if (!"".equals(annotation.partialLinkText())) locator = new ParametrizedLocator(driver, annotation.partialLinkText(), How.PARTIAL_LINK_TEXT);
        if (!"".equals(annotation.xpath())) locator = new ParametrizedLocator(driver, annotation.xpath(), How.XPATH);

        if (locator == null) throw new NullPointerException();
        return locator;
    }
}
