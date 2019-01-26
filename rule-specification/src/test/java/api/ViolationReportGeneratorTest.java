package api;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import datatypes.ArchitectureRule;
import datatypes.RuleType;
import impl.violationreportsentences.ConditionalRuleViolationReportSentence;
import impl.violationreportsentences.ExistentialRuleViolationReportSentence;
import impl.violationreportsentences.ViolationReportSentence;

public class ViolationReportGeneratorTest {

	@Test
	public void testCorrectSentenceForRuleTypeIsReturned() {
		ArchitectureRule rule = new ArchitectureRule();
		rule.setType(RuleType.EXISTENTIAL);
		
//		ViolationReportSentence sentence = new ConditionalRuleViolationReportSentence();
//		sentence = sentence.get(rule.getType());
//		
//		assertEquals(sentence.getClass(), ExistentialRuleViolationReportSentence.class);
	}

}
