package org.archcnl.application.service;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Optional;
import java.util.Properties;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.archcnl.application.exceptions.PropertyNotFoundException;

/** Helper class to work with property and config files. */
public class ConfigAppService {

    private static final Logger LOG = LogManager.getLogger(ConfigAppService.class);

    private static final String PROPERTY_FILE = "db.properties";
    private static final String DB_NAME = "databaseName";
    private static final String DB_USER_NAME = "username";
    private static final String DB_PASSWORD = "password";
    private static final String DB_URL = "server";

    private ConfigAppService() {
        // only static
    }

    /** @return DB Name from property file */
    public static String getDbName() throws PropertyNotFoundException {
        return ConfigAppService.checkIfPropertyPresent(
                ConfigAppService.getDbPropertyByName(ConfigAppService.DB_NAME),
                ConfigAppService.DB_NAME);
    }

    /** @return DB name from property file */
    public static String getDbUsername() throws PropertyNotFoundException {
        return ConfigAppService.checkIfPropertyPresent(
                ConfigAppService.getDbPropertyByName(ConfigAppService.DB_USER_NAME),
                ConfigAppService.DB_USER_NAME);
    }

    /** @return DB password from property file */
    public static String getDbPassword() throws PropertyNotFoundException {
        return ConfigAppService.checkIfPropertyPresent(
                ConfigAppService.getDbPropertyByName(ConfigAppService.DB_PASSWORD),
                ConfigAppService.DB_PASSWORD);
    }

    /** @return DB URL from property file */
    public static String getDbUrl() throws PropertyNotFoundException {
        return ConfigAppService.checkIfPropertyPresent(
                ConfigAppService.getDbPropertyByName(ConfigAppService.DB_URL),
                ConfigAppService.DB_URL);
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
                ConfigAppService.class
                        .getClassLoader()
                        .getResourceAsStream(ConfigAppService.PROPERTY_FILE)) {
            properties.load(fis);
            return Optional.ofNullable(properties.getProperty(name));
        } catch (final FileNotFoundException e) {
            ConfigAppService.LOG.error("DB property file not found.", e);
        } catch (final IOException ex) {
            ConfigAppService.LOG.error("DB property not found.", ex);
        }
        return Optional.empty();
    }
}
