package com.ccc.rit.config;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.ResourceBundle;

public class MessageProvider {

    private static final String MESSAGES_FILE = "messages";
    private static final Properties SECURITY_PROPERTIES = new Properties();

    static {
        try (InputStream input = MessageProvider.class.getClassLoader().getResourceAsStream("messages.properties")) {
            if (input != null) {
                SECURITY_PROPERTIES.load(input);
            }
        } catch (IOException e) {
            throw new IllegalStateException("No se pudo cargar el archivo de mensajes", e);
        }
    }

    private MessageProvider() {
    }

    public static String getMessage(String key) {
        return ResourceBundle.getBundle(MESSAGES_FILE).getString(key);
    }

    public static String getSecurityProperty(String key) {
        return SECURITY_PROPERTIES.getProperty(key);
    }
}
