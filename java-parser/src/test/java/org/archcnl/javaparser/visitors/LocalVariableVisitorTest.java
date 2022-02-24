package org.archcnl.javaparser.visitors;

import com.github.javaparser.ast.Modifier;
import com.github.javaparser.ast.body.VariableDeclarator;
import com.github.javaparser.ast.expr.VariableDeclarationExpr;
import com.github.javaparser.ast.type.PrimitiveType;
import java.util.List;
import org.archcnl.owlify.famix.codemodel.LocalVariable;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class LocalVariableVisitorTest {

    private LocalVariableVisitor visitor;
    private VariableDeclarationExpr var;
    private VariableDeclarationExpr var2;
    private VariableDeclarator declarator;
    private VariableDeclarator declarator2;

    @Before
    public void initializeVisitor() {
        visitor = new LocalVariableVisitor("TODO");
        declarator = new VariableDeclarator(PrimitiveType.intType(), "x");
        var = new VariableDeclarationExpr(declarator, Modifier.privateModifier());

        declarator2 = new VariableDeclarator(PrimitiveType.doubleType(), "y");
        var2 =
                new VariableDeclarationExpr(
                        declarator2, Modifier.publicModifier(), Modifier.staticModifier());
    }

    @Test
    public void givenVariables_whenLocalVariableVisitorVisit_thenLocalVariablesFound() {
        // given, when
        visitor.visit(var, null);
        // then
        List<LocalVariable> variables = visitor.getLocalVariables();
        Assert.assertEquals(variables.size(), 1);
        Assert.assertEquals(variables.get(0).getName(), "x");
        Assert.assertEquals(variables.get(0).getType().getSimpleName(), "int");
        Assert.assertEquals(variables.get(0).getModifiers().size(), 1);
        Assert.assertEquals(variables.get(0).getModifiers().get(0).getName(), "private");

        // when
        visitor.visit(var2, null);
        // then
        variables = visitor.getLocalVariables();
        Assert.assertEquals(variables.size(), 2);
        Assert.assertEquals(variables.get(1).getName(), "y");
        Assert.assertEquals(variables.get(1).getType().getSimpleName(), "double");
        Assert.assertEquals(variables.get(1).getModifiers().size(), 2);
        Assert.assertEquals(variables.get(1).getModifiers().get(0).getName(), "public");
        Assert.assertEquals(variables.get(1).getModifiers().get(1).getName(), "static");
    }
}
