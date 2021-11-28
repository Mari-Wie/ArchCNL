package org.archcnl.output.model.query;

import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.Set;
import org.archcnl.domain.common.Variable;
import org.archcnl.domain.input.exceptions.InvalidVariableNameException;
import org.archcnl.domain.output.model.query.SelectClause;
import org.junit.Assert;
import org.junit.Test;

public class SelectClauseTest {

    @Test
    public void givenSelectClause_whenCallAsFormattedString_thenReturnFormattedString()
            throws InvalidVariableNameException {
        // given
        final Variable variable1 = new Variable("name");
        final Variable variable2 = new Variable("cnl");
        final Variable variable3 = new Variable("a");
        final Variable variable4 = new Variable("b");
        final Set<Variable> objects =
                new LinkedHashSet<>(Arrays.asList(variable1, variable2, variable3, variable4));
        final SelectClause selectClause = new SelectClause(objects);

        final String expectedString = "SELECT ?name ?cnl ?a ?b ";

        // when
        final String selectClauseAsString = selectClause.transformToGui();

        // then
        Assert.assertEquals(expectedString, selectClauseAsString);
    }

    @Test
    public void givenSimpleSelectClause_whenCallAsFormattedString_thenReturnFormattedString()
            throws InvalidVariableNameException {
        // given
        final Variable variable1 = new Variable("name");
        final Set<Variable> objects = new LinkedHashSet<>(Arrays.asList(variable1));
        final SelectClause selectClause = new SelectClause(objects);

        final String expectedString = "SELECT ?name ";

        // when
        final String selectClauseAsString = selectClause.transformToGui();

        // then
        Assert.assertEquals(expectedString, selectClauseAsString);
    }
}
