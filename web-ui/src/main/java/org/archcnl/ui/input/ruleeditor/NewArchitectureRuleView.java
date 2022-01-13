
package org.archcnl.ui.input.ruleeditor;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.checkbox.Checkbox;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
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

    private ComboBox<String> firstCombobox, secondCombobox, thirdCombobox;
    private TextField firstConcept, secondConcept, firstrelation;
    private Checkbox statementCheckbox;
    private VerticalLayout statementLayout;

    public NewArchitectureRuleView(NewArchitectureRulePresenter presenter) {
        this.presenter = presenter;
        getStyle().set("overflow", "auto");
        getStyle().set("border", "1px solid black");
        
        statementLayout = new VerticalLayout();
        saveButton = new Button("Save Rule", e -> saveRule());
        
        add(componentRuleCreator(), statementLayout, textareaRuleCreator(), saveButton);
    }
    
    private HorizontalLayout componentRuleCreator()
    {
        HorizontalLayout componentRuleLayout = new HorizontalLayout();

        List<String> firstStatements = Arrays.asList("Every", "Only", "If", "Nothing", "No",
                "Fact:");
        firstCombobox = new ComboBox<String>("Modifier", firstStatements);
        firstCombobox.setValue("Every");

        firstConcept = new TextField("Concept");
        statementCheckbox = new Checkbox("that...");
        componentRuleLayout.setVerticalComponentAlignment(Alignment.END, statementCheckbox);        

        List<String> secondStatements = Arrays.asList("must", "can-only", "can", "must be");
        secondCombobox = new ComboBox<String>("Modifier", secondStatements);
        secondCombobox.setValue("must");

        firstrelation = new TextField("Relation");
        
        List<String> firstModifier = Arrays.asList("", "a", "an", "anything", "equal-to",
                "equal-to anything", "at-most", "at-least", "exactly", "equal-to at-most",
                "equal-to at-least", "equal-to exactly");
        thirdCombobox = new ComboBox<String>("Modifier", firstModifier);
        thirdCombobox.setValue("a");
        
        secondConcept = new TextField("Concept");
        
        componentRuleLayout.add(firstCombobox, firstConcept, statementCheckbox, secondCombobox,
                firstrelation, thirdCombobox, secondConcept);        
        return componentRuleLayout;
    }
    
    private VerticalLayout textareaRuleCreator()
    {        
        VerticalLayout customeRuleLayout = new VerticalLayout();
        Checkbox activateExpertmode = new Checkbox("Write Architecture Rules");
        activateExpertmode.addClickListener(e->archRuleTextArea.setEnabled(!archRuleTextArea.isEnabled()));
        
        archRuleTextArea = new TextArea(
                "Expert mode (Freestyle)",
                "Every Aggregate must residein a DomainRing");
        archRuleTextArea.setWidthFull();
        archRuleTextArea.setEnabled(false);
        
        customeRuleLayout.add(activateExpertmode, archRuleTextArea);
        return customeRuleLayout;
    }
    
    private void addStatement()
    {
        //HorizontalLayout statementLayout = new HorizontalLayout();
    }

    private void saveRule() {
        if (!archRuleTextArea.isEmpty()) {
            presenter.saveArchitectureRule(archRuleTextArea.getValue());
        } else {
            presenter.saveArchitectureRule(buildRule());
        }
        presenter.returnToRulesView();
    }

    private String buildRule() {
        StringBuilder sBuilder = new StringBuilder();
        sBuilder.append(firstCombobox.getValue() + " ");
        sBuilder.append(firstConcept.getValue() + " ");
        sBuilder.append(secondCombobox.getValue() + " ");
        sBuilder.append(firstrelation.getValue() + " ");
        sBuilder.append(thirdCombobox.getValue() + " ");
        sBuilder.append(secondConcept.getValue() + " ");
        return sBuilder.toString();
    }     
}
