package org.archcnl.common.datatypes;

import java.time.LocalDate;
import org.apache.jena.ontology.OntModelSpec;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;

/**
 * Class for exchanging architecture rules between modules.
 *
 * <p>Value object.
 */
public class ArchitectureRule {
    private final int id;
    private final String cnlSentence;
    private final Model ruleModel;
    private final RuleType type;
    private final LocalDate validFrom;
    private final LocalDate validUntil;

    /**
     * @param id the ID of the rule
     * @param cnlSentence the CNL sentence which is represented by this rule
     * @param type the type of this rule. It corresponds to the way how this rule's CNL sentence can
     *     be deferred from the grammar.
     * @param ruleModel OWL constraint/ontology modelling this rule
     * @param validFrom the date from when on the rule should be validated
     * @param validUntil the date until the rule should be validated
     */
    public ArchitectureRule(
            int id,
            String cnlSentence,
            RuleType type,
            Model ruleModel,
            LocalDate validFrom,
            LocalDate validUntil) {
        this.id = id;
        this.cnlSentence = cnlSentence;
        this.type = type;
        this.ruleModel = ruleModel;
        this.validFrom = validFrom;
        this.validUntil = validUntil;
    }

    public static ArchitectureRule createArchRuleForTests(
            int id,
            String cnlSentence,
            RuleType type,
            String constraintFilePath,
            LocalDate validFrom,
            LocalDate validUntil) {
        Model ruleModel = ModelFactory.createOntologyModel(OntModelSpec.OWL_MEM, null);
        ruleModel.read(constraintFilePath);
        return new ArchitectureRule(id, cnlSentence, type, ruleModel, validFrom, validUntil);
    }

    public static ArchitectureRule createArchRuleForParsing(
            String cnlSentence, LocalDate validFrom, LocalDate validUntil) {
        return new ArchitectureRule(-1, cnlSentence, null, null, validFrom, validUntil);
    }

    /** @return the ID of this rule */
    public Integer getId() {
        return id;
    }

    /** @return the CNL sentence represented by this rule */
    public String getCnlSentence() {
        return cnlSentence;
    }

    /** @return the CNL sentence represented by this rule */
    public LocalDate getValidFrom() {
        return validFrom;
    }

    /** @return the CNL sentence represented by this rule */
    public LocalDate getValidUntil() {
        return validUntil;
    }
    /**
     * @return The type of this rule. It corresponds to the way how this rule's CNL sentence can be
     *     deferred from the grammar.
     */
    public RuleType getType() {
        return type;
    }

    /** @return this rule as an OWL constraint/ontology */
    public Model getRuleModel() {
        Model model = ModelFactory.createOntologyModel(OntModelSpec.OWL_MEM, null);
        model.add(ruleModel);
        return model;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((cnlSentence == null) ? 0 : cnlSentence.hashCode());
        result = prime * result + id;
        result = prime * result + ((type == null) ? 0 : type.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null) return false;
        if (getClass() != obj.getClass()) return false;
        ArchitectureRule other = (ArchitectureRule) obj;
        if (cnlSentence == null) {
            if (other.cnlSentence != null) return false;
        } else if (!cnlSentence.equals(other.cnlSentence)) return false;
        if (ruleModel == null) {
            if (other.ruleModel != null) return false;
        } else if (!ruleModel.isIsomorphicWith(other.ruleModel)) return false;
        if (id != other.id) return false;
        if (type != other.type) return false;
        return true;
    }

    public String toString() {
        String result = "";
        result += "Id         :" + id + "\n";
        result += "Cnl        : " + cnlSentence + "\n";

        if (type != null) {
            result += "Type       : " + type.toString() + "\n";
        }
        return result;
    }
}
