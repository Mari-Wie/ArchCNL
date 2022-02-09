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
    private static final String DB_NAME = "DB_NAME";
    private static final String DB_USER = "DB_USER";
    private static final String DB_PASSWORD = "DB_PASSWORD";
    private static final String DB_HOST = "DB_HOST";
    private static final String DB_PROTOCOL = "DB_PROTOCOL";
    private static final String DB_PORT = "DB_PORT";
    private static final String DB_CONTEXT = "DB_CONTEXT";
    private static final String DB_RULEFILE = "DB_RULEFILE";
    private static final String APP_RUNTIME_ENV = "RUNTIME_ENV";
    private static final String PROD_STAGE = "DOCKER";

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
                ConfigAppService.getDbPropertyByName(ConfigAppService.DB_USER),
                ConfigAppService.DB_USER);
    }

    /** @return DB password from property file */
    public static String getDbPassword() throws PropertyNotFoundException {
        return ConfigAppService.checkIfPropertyPresent(
                ConfigAppService.getDbPropertyByName(ConfigAppService.DB_PASSWORD),
                ConfigAppService.DB_PASSWORD);
    }

    /** @return DB context from property file */
    public static String getDbContext() throws PropertyNotFoundException {
        return ConfigAppService.checkIfPropertyPresent(
                ConfigAppService.getDbPropertyByName(ConfigAppService.DB_CONTEXT),
                ConfigAppService.DB_CONTEXT);
    }

    /** @return DB rule file path from property file */
    public static String getDbRuleFile() throws PropertyNotFoundException {
        return ConfigAppService.checkIfPropertyPresent(
                ConfigAppService.getDbPropertyByName(ConfigAppService.DB_RULEFILE),
                ConfigAppService.DB_RULEFILE);
    }

    /** @return DB URL from property file */
    public static String getDbUrl() throws PropertyNotFoundException {
        return ConfigAppService.checkIfPropertyPresent(
                ConfigAppService.getFullDbUrl(), ConfigAppService.DB_HOST);
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

    private static Optional<String> getFullDbUrl() {
        final Optional<String> protocolOpt =
                ConfigAppService.getDbPropertyByName(ConfigAppService.DB_PROTOCOL);
        final Optional<String> hostOpt =
                ConfigAppService.getDbPropertyByName(ConfigAppService.DB_HOST);
        final Optional<String> portOpt =
                ConfigAppService.getDbPropertyByName(ConfigAppService.DB_PORT);
        return protocolOpt.isPresent() && hostOpt.isPresent() && portOpt.isPresent()
                ? Optional.of(protocolOpt.get() + "://" + hostOpt.get() + ":" + portOpt.get())
                : Optional.empty();
    }

    private static Optional<String> getDbPropertyByName(final String name) {
        final Optional<String> stageOpt =
                Optional.ofNullable(System.getenv(ConfigAppService.APP_RUNTIME_ENV));
        final boolean isDockerContainer =
                stageOpt.isPresent() && stageOpt.get().equals(ConfigAppService.PROD_STAGE);
        ConfigAppService.LOG.info(
                "Property '{}' will be read from '{}'", name, isDockerContainer ? "ENV" : "FILE");
        return isDockerContainer
                ? ConfigAppService.getDbPropertyByNameFromEnv(name)
                : ConfigAppService.getDbPropertyByNameFromFile(name);
    }

    private static Optional<String> getDbPropertyByNameFromFile(final String name) {
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

    private static Optional<String> getDbPropertyByNameFromEnv(final String name) {
        ConfigAppService.LOG.info("Property '{}' will be read from ENV", name);
        return Optional.ofNullable(System.getenv(name));
    }
}
