package org.archcnl.commons.service;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Optional;
import java.util.Properties;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.archcnl.commons.exceptions.PropertyNotFoundException;

/** Helper class to work with property and config files. */
public class ConfigService {

    private static final Logger LOG = LogManager.getLogger(ConfigService.class);

    private static final String PROPERTY_FILE = "db.properties";
    private static final String DB_NAME = "databaseName";
    private static final String DB_USER_NAME = "username";
    private static final String DB_PASSWORD = "password";
    private static final String DB_URL = "server";

    private ConfigService() {
        // only static
    }

    /** @return DB Name from property file */
    public static String getDbName() throws PropertyNotFoundException {
        return ConfigService.checkIfPropertyPresent(
                ConfigService.getDbPropertyByName(ConfigService.DB_NAME), ConfigService.DB_NAME);
    }

    /** @return DB name from property file */
    public static String getDbUsername() throws PropertyNotFoundException {
        return ConfigService.checkIfPropertyPresent(
                ConfigService.getDbPropertyByName(ConfigService.DB_USER_NAME),
                ConfigService.DB_USER_NAME);
    }

    /** @return DB password from property file */
    public static String getDbPassword() throws PropertyNotFoundException {
        return ConfigService.checkIfPropertyPresent(
                ConfigService.getDbPropertyByName(ConfigService.DB_PASSWORD),
                ConfigService.DB_PASSWORD);
    }

    /** @return DB URL from property file */
    public static String getDbUrl() throws PropertyNotFoundException {
        return ConfigService.checkIfPropertyPresent(
                ConfigService.getDbPropertyByName(ConfigService.DB_URL), ConfigService.DB_URL);
    }

    private static String checkIfPropertyPresent(
            final Optional<String> optProperty, final String propertyName)
            throws PropertyNotFoundException {
        if (optProperty.isPresent()) {
            return optProperty.get();
        } else {
            throw new PropertyNotFoundException(propertyName);
        }
    }

    private static Optional<String> getDbPropertyByName(final String name) {
        final Properties properties = new Properties();
        try (final InputStream fis =
                ConfigService.class
                        .getClassLoader()
                        .getResourceAsStream(ConfigService.PROPERTY_FILE)) {
            properties.load(fis);
            return Optional.ofNullable(properties.getProperty(name));
        } catch (final FileNotFoundException e) {
            ConfigService.LOG.error("DB property file not found.", e);
        } catch (final IOException ex) {
            ConfigService.LOG.error("DB property not found.", ex);
        }
        return Optional.empty();
    }
}
