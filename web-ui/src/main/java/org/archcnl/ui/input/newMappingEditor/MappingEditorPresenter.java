package org.archcnl.ui.input.newMappingEditor;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.Tag;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;
import org.archcnl.domain.common.BooleanValue;
import org.archcnl.domain.common.Relation;
import org.archcnl.domain.common.StringValue;
import org.archcnl.domain.common.TypeRelation;
import org.archcnl.domain.input.exceptions.RelationDoesNotExistException;
import org.archcnl.domain.input.model.RulesConceptsAndRelations;
import org.archcnl.ui.input.newMappingEditor.events.PredicateListUpdateRequest;
import org.archcnl.ui.input.newMappingEditor.events.PredicateSelectionEvent;
import org.archcnl.ui.input.newMappingEditor.events.VariableUpdateRequest;

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
        view.addListener(PredicateSelectionEvent.class, e -> handlePredicateSelectionEvent(e));
        view.addListener(VariableUpdateRequest.class, e -> handleVariableUpdateRequest(e));
        view.addListener(
                PredicateListUpdateRequest.class, e -> handlePredicateListUpdateRequest(e));
    }

    public void updateVariableSelectionOptions(Relation relation, ComboBox<String> target) {
        List<String> items = new ArrayList<String>();
        boolean stringsAllowed = relation.canRelateToObjectType(new StringValue(""));
        boolean booleanAllowed = relation.canRelateToObjectType(new BooleanValue(false));

        items.add("Variable");
        if (stringsAllowed) {
            items.add("String");
        }
        if (booleanAllowed) {
            items.add("Boolean");
        }
        target.setItems(items);
        target.setValue("Variable");
    }

    public void handlePredicateListUpdateRequest(PredicateListUpdateRequest e) {
        System.out.println("PredicateListUpdateRequest received");
        e.getSource().setItems(getRelationNameList());
    }

    public void handlePredicateSelectionEvent(PredicateSelectionEvent e) {
        System.out.println("PredicateSelectionEvent Event received");
        Relation relation = null;
        String newValue = e.getSelectedPredicate();
        if (newValue != null) {
            System.out.println(newValue);
            try {
                relation =
                        RulesConceptsAndRelations.getInstance()
                                .getRelationManager()
                                .getRelationByName(newValue);
            } catch (RelationDoesNotExistException | NoSuchElementException ex) {
                System.out.println("Critical Error: Selected predicate does not exist");
                return;
            }
            if (relation instanceof TypeRelation) {
                // TODO see old code (something something switch to concept view)
            } else {
                updateVariableSelectionOptions(relation, e.getVariableTypeSelection());
            }
        }
    }

    public void updateVariableComboBox(
            ComboBox<String> target, List<String> items, String newItem) {
        items.add(newItem);
        target.setItems(items);
        target.setValue(newItem);
    }

    public void handleVariableUpdateRequest(VariableUpdateRequest e) {
        System.out.println("received VariableUpdateRequest");

        String newVariable = e.getSelectedVariable().trim();
        ComboBox<String> source = e.getSource();
        VariableList variableListComponent = e.getVariableContainer();
        String noSpacesRegex = "^[^\\s]+$";
        List<String> variableList = variableListComponent.collect();

        source.setInvalid(false);
        if (!newVariable.matches(noSpacesRegex)) {
            source.setInvalid(true);
            source.setErrorMessage("Spaces");
        } else if (!variableList.contains(newVariable)) {
            updateVariableComboBox(source, variableList, newVariable);
            variableListComponent.addVariable(newVariable);
        }
    }

    public List<String> getRelationNameList() {
        return RulesConceptsAndRelations.getInstance().getRelationManager().getRelations().stream()
                .map(Relation::getName)
                .collect(Collectors.toList());
    }

    public VerticalLayout getView() {
        return view;
    }

    List<String> getConceptNameList() {
        List<String> testItems = Arrays.asList("concept1", "concept2", "concept3");
        // return new ArrayList<String>();
        return testItems;
    }
}
