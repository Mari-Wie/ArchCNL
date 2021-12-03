package org.archcnl.ui.input.newMappingEditor;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.Tag;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Tag("Test")
public class MappingEditorPresenter extends Component {
    MappingEditorView view;
    ArrayList<String> variables;

    public MappingEditorPresenter() {
        createGui();
    }

    void updateModel() {}

    void createGui() {
        view = new MappingEditorView();
        view.addListener(PredicateSelectionEvent.class, e ->  handlePredicateSelectionEvent(e));
        view.addListener(VariableUpdateRequest.class, e ->  handleVariableUpdateRequest(e));
    }

   public void handlePredicateSelectionEvent(PredicateSelectionEvent e) {
		e.getSource().setItems(getConceptNameList());
   }

   
   public void handleVariableUpdateRequest(VariableUpdateRequest e) {
	   System.out.println("received VariableUpdateRequest");
	  e.getVariableContainer().add(new TextField(e.getSelectedVariable()));
   }
   
   
    public VerticalLayout getView() {
        return view;
    }

    List<String> getConceptNameList() {
        List<String> testItems = Arrays.asList("concept1", "concept2", "concept3");
        //return new ArrayList<String>();
    	return  testItems;
    }
}
