package org.archcnl.ui.input.newMappingEditor;

import com.vaadin.flow.component.ComponentEvent;
	import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;

public class VariableUpdateRequest  extends ComponentEvent<ComboBox<String>> {
	    private static final long serialVersionUID = 1L;
	    HorizontalLayout variableContainter;
	    String selectedVariable;

	    public VariableUpdateRequest(ComboBox<String>  source, boolean fromClient, HorizontalLayout variableContainer,String selectedVariable) {
	        super(source, fromClient);
	        this.variableContainter = variableContainer;
	        this.selectedVariable = selectedVariable;
	        System.out.println("Variable UpdateRequest send");
	    }
	    public HorizontalLayout  getVariableContainer() {return variableContainter;}
	    public String getSelectedVariable() {return selectedVariable;}
	}
