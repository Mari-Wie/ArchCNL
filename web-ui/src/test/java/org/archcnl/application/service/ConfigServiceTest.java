package org.archcnl.application.service;

import org.archcnl.application.exceptions.PropertyNotFoundException;
import org.junit.Assert;
import org.junit.Test;

public class ConfigServiceTest {

    @Test
    public void givenValidDbConfigFile_whenCallGetDbName_thenReturnDbName()
            throws PropertyNotFoundException {
        // given
        final String expectedValue = "archcnl_it_db";
        // when
        final String dbName = ConfigAppService.getDbName();
        // then
        Assert.assertEquals(expectedValue, dbName);
    }

    @Test
    public void givenValidDbConfigFile_whenCallGetDbUrl_thenReturnDbUrl()
            throws PropertyNotFoundException {
        // given
        final String expectedValue = "http://localhost:5820";
        // when
        final String dbUrl = ConfigAppService.getDbUrl();
        // then
        Assert.assertEquals(expectedValue, dbUrl);
    }

    @Test
    public void givenValidDbConfigFile_whenCallGetDbPassword_thenReturnDbPass()
            throws PropertyNotFoundException {
        // given
        final String expectedValue = "admin";
        // when
        final String dbPass = ConfigAppService.getDbPassword();
        // then
        Assert.assertEquals(expectedValue, dbPass);
    }

    @Test
    public void givenValidDbConfigFile_whenCallGetDbUserName_thenReturnDbPass()
            throws PropertyNotFoundException {
        // given
        final String expectedValue = "admin";
        // when
        final String dbUserName = ConfigAppService.getDbUsername();
        // then
        Assert.assertEquals(expectedValue, dbUserName);
    }
}
