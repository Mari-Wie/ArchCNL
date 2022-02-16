package org.archcnl.domain.input.presets;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.List;
import org.archcnl.domain.input.model.presets.ArchitecturalStyle;
import org.archcnl.domain.input.model.presets.ArchitecturalStyleConfig;
import org.archcnl.domain.input.model.presets.ArchitecturalStyleConfigManager;
import org.archcnl.domain.input.model.presets.ArchitectureRuleConfig;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class ArchitecturalStyleConfigTest {

    private static ArchitecturalStyleConfig config;
    private static ArchitecturalStyle styleUsedInTest =
            ArchitecturalStyle.MICROSERVICE_ARCHITECTURE;

    @BeforeAll
    static void init() {
        try {
            // given + when
            config = new ArchitecturalStyleConfigManager().getConfig(styleUsedInTest);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    @AfterAll
    static void teardown() {
        config = null;
    }

    @Test
    void givenArchitecturalStyleConfig_whenCreatedFromJson_ContainsCorrectRules() {
        List<ArchitectureRuleConfig> rules = config.getRules();

        List<String> knownRules = PresetsTestUtils.getMicroserviceArchitectureRules();
        // then

        // rules have been created
        assertNotNull("List of Rules is null", rules);

        // there should be at least one rule
        assertTrue("There are no rules for the style", rules.size() > 0);

        // Assert that rules from config are contained in the expected known rules
        rules.forEach(
                ruleConfig -> {
                    assertTrue(
                            "Rule from config not within known Rules",
                            knownRules.contains(ruleConfig.getRule()));
                });
    }
}
