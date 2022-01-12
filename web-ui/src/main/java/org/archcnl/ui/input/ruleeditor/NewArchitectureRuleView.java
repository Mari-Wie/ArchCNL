package org.archcnl.ui.input.ruleeditor;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.textfield.TextField;

import java.util.Arrays;
import java.util.List;

import org.archcnl.ui.input.RulesOrMappingEditorView;

public class NewArchitectureRuleView extends RulesOrMappingEditorView
        implements ArchitectureRulesContract.View {

    private static final long serialVersionUID = -2966045554441002128L;
    private Button saveButton;
    private TextArea archRuleTextArea;
    private NewArchitectureRulePresenter presenter;
    
    private ComboBox<String> firstCombobox, secondCombobox;
    private TextField firstConcept, secondConcept, firstrelation;

    public NewArchitectureRuleView(NewArchitectureRulePresenter presenter) {
        this.presenter = presenter;
        getStyle().set("overflow", "auto");
        getStyle().set("border", "1px solid black");

        saveButton = new Button("Save Rule", e -> saveRule());
        archRuleTextArea =
                new TextArea(
                        "Expert mode (Freestyle)",
                        "Every Aggregate must residein a DomainRing");
        archRuleTextArea.setWidthFull();
        
        HorizontalLayout newRuleCreation = new HorizontalLayout();
                
        List<String> firstStatements = Arrays.asList("Every","Only","If","Nothing","No","Fact:");    
        firstCombobox = new ComboBox<String>("Modifier", firstStatements);
        firstCombobox.setValue("Every");
                
        firstConcept = new TextField("Concept");
        
        TextField aModifier = new TextField("Optional");
        aModifier.setEnabled(false);
        aModifier.setWidth("55px");
        aModifier.setValue("a/an");
        
        TextField aModifier2 = new TextField("Optional");
        aModifier2.setEnabled(false);
        aModifier2.setWidth("55px");
        aModifier2.setValue("a/an");
        
        secondConcept = new TextField("Concept");
        
        List<String> secondStatements = Arrays.asList("must","can-only","can","must be");    
        secondCombobox = new ComboBox<String>("Modifier", secondStatements);
        secondCombobox.setValue("must");
        
        firstrelation = new TextField("Relation");
    
        newRuleCreation.add(firstCombobox, aModifier, firstConcept, secondCombobox, firstrelation, aModifier2, secondConcept);
        add(newRuleCreation, archRuleTextArea);
        add(saveButton);
    }

    private void saveRule() {
        if (!archRuleTextArea.isEmpty()) {
            presenter.saveArchitectureRule(archRuleTextArea.getValue());
        }
        else
        {
            presenter.saveArchitectureRule(buildRule());           
        }
        presenter.returnToRulesView();
    }
    
    private String buildRule()
    {
        StringBuilder sBuilder = new StringBuilder();
        sBuilder.append(firstCombobox.getValue() + " ");
        sBuilder.append(firstConcept.getValue() + " ");
        sBuilder.append(secondCombobox.getValue() + " ");
        sBuilder.append(firstrelation.getValue() + " ");
        sBuilder.append(secondConcept.getValue() + " ");
        return sBuilder.toString();
    }
}
