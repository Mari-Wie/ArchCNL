package org.archcnl.service;

import org.archcnl.application.exceptions.PropertyNotFoundException;
import org.archcnl.application.service.ConfigAppService;
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
    public void givenValidDbConfigFile_whenCallGetDbUserName_thenReturnDbUserName()
            throws PropertyNotFoundException {
        // given
        final String expectedValue = "admin";
        // when
        final String dbUserName = ConfigAppService.getDbUsername();
        // then
        Assert.assertEquals(expectedValue, dbUserName);
    }

    @Test
    public void givenValidDbConfigFile_whenCallGetDbContext_thenReturnDbContext()
            throws PropertyNotFoundException {
        // given
        final String expectedValue = "http://graphs.org/archcnl_it_db/1.0";
        // when
        final String dbContext = ConfigAppService.getDbContext();
        // then
        Assert.assertEquals(expectedValue, dbContext);
    }

    @Test
    public void givenValidDbConfigFile_whenCallGetDbRuleFile_thenReturnDbRuleFile()
            throws PropertyNotFoundException {
        // given
        final String expectedValue = "temp/GeneratedRuleFile.adoc";
        // when
        final String dbContext = ConfigAppService.getDbRuleFile();
        // then
        Assert.assertEquals(expectedValue, dbContext);
    }
}
