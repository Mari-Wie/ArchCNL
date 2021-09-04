package org.archcnl.output.model.query;

import org.archcnl.domain.output.model.query.WhereTriple;
import org.archcnl.domain.output.model.query.attribute.QueryField;
import org.archcnl.domain.output.model.query.attribute.QueryObject;
import org.archcnl.domain.output.model.query.attribute.QueryObjectType;
import org.archcnl.domain.output.model.query.attribute.QueryPredicate;
import org.junit.Assert;
import org.junit.Test;

public class WhereTripleTest {

    @Test
    public void givenWhereTripleWithField_whenCallAsFormattedString_thenReturnFormattedString() {
        // given
        final WhereTriple triple =
                new WhereTriple(
                        new QueryField("aggregate"),
                        new QueryPredicate("famix", "hasName"),
                        new QueryObject("name", QueryObjectType.FIELD));

        final String expectedString = "?aggregate famix:hasName ?name";

        // when
        final String tripleAsString = triple.asFormattedString();

        // then
        Assert.assertEquals(expectedString, tripleAsString);
    }

    @Test
    public void
            givenWhereTripleWithPrimitiveValueString_whenCallAsFormattedString_thenReturnFormattedString() {
        // given
        final WhereTriple triple =
                new WhereTriple(
                        new QueryField("aggregate"),
                        new QueryPredicate("xsd", "hasName"),
                        new QueryObject("Some string value", QueryObjectType.PRIMITIVE_VALUE));

        final String expectedString = "?aggregate xsd:hasName \"Some string value\"^^xsd:string";

        // when
        final String tripleAsString = triple.asFormattedString();

        // then
        Assert.assertEquals(expectedString, tripleAsString);
    }

    @Test
    public void
            givenWhereTripleWithPrimitiveValueInt_whenCallAsFormattedString_thenReturnFormattedString() {
        // given
        final WhereTriple triple =
                new WhereTriple(
                        new QueryField("aggregate"),
                        new QueryPredicate("owl", "hasName"),
                        new QueryObject("-1", QueryObjectType.PRIMITIVE_VALUE));

        final String expectedString = "?aggregate owl:hasName \"-1\"^^xsd:integer";

        // when
        final String tripleAsString = triple.asFormattedString();

        // then
        Assert.assertEquals(expectedString, tripleAsString);
    }

    @Test
    public void
            givenWhereTripleWithPrimitiveValueDouble_whenCallAsFormattedString_thenReturnFormattedString() {
        // given
        final WhereTriple triple =
                new WhereTriple(
                        new QueryField("aggregate"),
                        new QueryPredicate("xsd", "hasName"),
                        new QueryObject("-1.0", QueryObjectType.PRIMITIVE_VALUE));

        final String expectedString = "?aggregate xsd:hasName \"-1.0\"^^xsd:double";

        // when
        final String tripleAsString = triple.asFormattedString();

        // then
        Assert.assertEquals(expectedString, tripleAsString);
    }

    @Test
    public void
            givenWhereTripleWithPrimitiveValueBoolean_whenCallAsFormattedString_thenReturnFormattedString() {
        // given
        final WhereTriple triple =
                new WhereTriple(
                        new QueryField("aggregate"),
                        new QueryPredicate("xsd", "hasName"),
                        new QueryObject("true", QueryObjectType.PRIMITIVE_VALUE));

        final String expectedString = "?aggregate xsd:hasName \"true\"^^xsd:boolean";

        // when
        final String tripleAsString = triple.asFormattedString();

        // then
        Assert.assertEquals(expectedString, tripleAsString);
    }

    @Test
    public void
            givenWhereTripleWithPrimitiveValueDateTime_whenCallAsFormattedString_thenReturnFormattedString() {
        // given
        final WhereTriple triple =
                new WhereTriple(
                        new QueryField("aggregate"),
                        new QueryPredicate("xsd", "hasName"),
                        new QueryObject("2020-08-28T21:12:00", QueryObjectType.PRIMITIVE_VALUE));

        final String expectedString =
                "?aggregate xsd:hasName \"2020-08-28T21:12:00\"^^xsd:dateTime";

        // when
        final String tripleAsString = triple.asFormattedString();

        // then
        Assert.assertEquals(expectedString, tripleAsString);
    }

    @Test
    public void givenWhereTripleWithProperty_whenCallAsFormattedString_thenReturnFormattedString() {
        // given
        final WhereTriple triple =
                new WhereTriple(
                        new QueryField("aggregate"),
                        new QueryPredicate("xsd", "hasName"),
                        new QueryObject("conformance:ArchitectureRule", QueryObjectType.PROPERTY));

        final String expectedString = "?aggregate xsd:hasName conformance:ArchitectureRule";

        // when
        final String tripleAsString = triple.asFormattedString();

        // then
        Assert.assertEquals(expectedString, tripleAsString);
    }
}
