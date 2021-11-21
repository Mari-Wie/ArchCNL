package org.archcnl.output.model.query;

import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.Set;
import org.archcnl.domain.output.model.query.SelectClause;
import org.archcnl.domain.output.model.query.attribute.QueryField;
import org.junit.Assert;
import org.junit.Test;

public class SelectClauseTest {

    @Test
    public void givenSelectClause_whenCallAsFormattedString_thenReturnFormattedString() {
        // given
        final QueryField field1 = new QueryField("name");
        final QueryField field2 = new QueryField("cnl");
        final QueryField field3 = new QueryField("a");
        final QueryField field4 = new QueryField("b");
        final Set<QueryField> objects =
                new LinkedHashSet<>(Arrays.asList(field1, field2, field3, field4));
        final SelectClause selectClause = new SelectClause(objects);

        final String expectedString = "SELECT ?name ?cnl ?a ?b ";

        // when
        final String selectClauseAsString = selectClause.transformToGui();

        // then
        Assert.assertEquals(expectedString, selectClauseAsString);
    }

    @Test
    public void givenSimpleSelectClause_whenCallAsFormattedString_thenReturnFormattedString() {
        // given
        final QueryField field1 = new QueryField("name");
        final Set<QueryField> objects = new LinkedHashSet<>(Arrays.asList(field1));
        final SelectClause selectClause = new SelectClause(objects);

        final String expectedString = "SELECT ?name ";

        // when
        final String selectClauseAsString = selectClause.transformToGui();

        // then
        Assert.assertEquals(expectedString, selectClauseAsString);
    }
}
