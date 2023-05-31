package com.example.desafiodescartes.util;

import java.text.MessageFormat;
import java.util.Locale;
import java.util.ResourceBundle;

import org.springframework.context.support.MessageSourceResourceBundle;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.stereotype.Component;

@Component
public class MessageBundle {

    private static MessageBundle instance = null;

    private final ResourceBundle resourceBundle;

    protected MessageBundle() {
        ResourceBundleMessageSource messageSource = new ResourceBundleMessageSource();
        messageSource.setBasename("messages");
        resourceBundle = new MessageSourceResourceBundle(messageSource, Locale.getDefault());
    }

    public static MessageBundle getInstance() {
        if (instance == null) {
            synchronized (MessageBundle.class) {
                if (instance == null)
                    instance = new MessageBundle();
            }
        }
        return instance;
    }

    public String getString(String key) {
        return resourceBundle.getString(key);
    }

    public String getString(String key, Object... params) {
        return MessageFormat.format(getString(key), params);
    }

}

