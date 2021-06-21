package org.archcnl.javaparser.visitors;

import static org.junit.Assert.*;

import java.util.List;

import org.archcnl.owlify.famix.codemodel.LocalVariable;
import org.junit.Before;
import org.junit.Test;

import com.github.javaparser.ast.Modifier;
import com.github.javaparser.ast.body.VariableDeclarator;
import com.github.javaparser.ast.expr.VariableDeclarationExpr;
import com.github.javaparser.ast.type.PrimitiveType;

public class LocalVariableVisitorTest {

	private LocalVariableVisitor visitor;
	private VariableDeclarationExpr var;	
	private VariableDeclarationExpr var2;	
	private VariableDeclarator declarator;
	private VariableDeclarator declarator2;
	
	@Before
    public void initializeVisitor() {
        visitor = new LocalVariableVisitor();           
        declarator = new VariableDeclarator(PrimitiveType.intType(), "x");
        var = new VariableDeclarationExpr(declarator, Modifier.privateModifier());
        		
        declarator2 = new VariableDeclarator(PrimitiveType.doubleType(), "y");
        var2 = new VariableDeclarationExpr(declarator2, Modifier.publicModifier(), Modifier.staticModifier());
    }
	
	
	@Test
	public void testVariableExtraction() {		
		visitor.visit(var, null);
		List<LocalVariable> variables = visitor.getLocalVariables();
		
		assertEquals(variables.size(), 1);		
		assertEquals(variables.get(0).getName(), "x");
		assertEquals(variables.get(0).getType().getSimpleName(), "int");
		
		assertEquals(variables.get(0).getModifiers().size(), 1);
		assertEquals(variables.get(0).getModifiers().get(0).getName(), "private");
		
		visitor.visit(var2, null);
		variables = visitor.getLocalVariables();
		
		assertEquals(variables.size(), 2);		
		assertEquals(variables.get(1).getName(), "y");
		assertEquals(variables.get(1).getType().getSimpleName(), "double");
		
		assertEquals(variables.get(1).getModifiers().size(), 2);
		assertEquals(variables.get(1).getModifiers().get(0).getName(), "public");
		assertEquals(variables.get(1).getModifiers().get(1).getName(), "static");
	}
	
}
