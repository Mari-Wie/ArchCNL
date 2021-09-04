package org.archcnl.service;

import org.archcnl.commons.exceptions.PropertyNotFoundException;
import org.archcnl.commons.service.ConfigService;
import org.junit.Assert;
import org.junit.Test;

public class ConfigServiceTest {

    @Test
    public void givenValidDbConfigFile_whenCallGetDbName_thenReturnDbName()
            throws PropertyNotFoundException {
        // given
        final String expectedValue = "archcnl_it_db";
        // when
        final String dbName = ConfigService.getDbName();
        // then
        Assert.assertEquals(expectedValue, dbName);
    }

    @Test
    public void givenValidDbConfigFile_whenCallGetDbUrl_thenReturnDbUrl()
            throws PropertyNotFoundException {
        // given
        final String expectedValue = "http://localhost:5820";
        // when
        final String dbUrl = ConfigService.getDbUrl();
        // then
        Assert.assertEquals(expectedValue, dbUrl);
    }

    @Test
    public void givenValidDbConfigFile_whenCallGetDbPassword_thenReturnDbPass()
            throws PropertyNotFoundException {
        // given
        final String expectedValue = "admin";
        // when
        final String dbPass = ConfigService.getDbPassword();
        // then
        Assert.assertEquals(expectedValue, dbPass);
    }

    @Test
    public void givenValidDbConfigFile_whenCallGetDbUserName_thenReturnDbPass()
            throws PropertyNotFoundException {
        // given
        final String expectedValue = "admin";
        // when
        final String dbUserName = ConfigService.getDbUsername();
        // then
        Assert.assertEquals(expectedValue, dbUserName);
    }
}
