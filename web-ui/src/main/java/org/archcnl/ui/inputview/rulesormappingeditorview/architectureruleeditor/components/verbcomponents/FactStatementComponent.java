package org.archcnl.ui.inputview.rulesormappingeditorview.architectureruleeditor.components.verbcomponents;

import com.vaadin.flow.component.ComponentEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.shared.Registration;

import java.util.Arrays;
import org.archcnl.ui.common.andtriplets.triplet.ConceptSelectionComponent;
import org.archcnl.ui.common.andtriplets.triplet.PredicateSelectionComponent;
import org.archcnl.ui.common.andtriplets.triplet.events.ConceptListUpdateRequestedEvent;
import org.archcnl.ui.common.andtriplets.triplet.events.RelationListUpdateRequestedEvent;
import org.archcnl.ui.inputview.rulesormappingeditorview.architectureruleeditor.components.RuleComponentInterface;
import org.archcnl.ui.inputview.rulesormappingeditorview.architectureruleeditor.components.textfieldwidgets.ConceptTextfieldWidget;

public class FactStatementComponent extends VerticalLayout implements RuleComponentInterface {

    private static final long serialVersionUID = 1L;
    private HorizontalLayout componentRuleLayout;
    private ComboBox<String> one_firstModifier, three_secondModifier;
    private ConceptSelectionComponent twoA_firstVariable_Concept;
    private ConceptSelectionComponent fourA_thirdVariable;
    private ConceptTextfieldWidget fourB_thirdVariable;    
    private PredicateSelectionComponent twoB_firstVariable_Relation;
    private boolean isInUpperBranch = false, conceptRequired = true;

    public FactStatementComponent() {
        this.setMargin(false);
        this.setPadding(false);

        initializeLayout();
    }

    private void initializeLayout() {
        componentRuleLayout = new HorizontalLayout();
        
        one_firstModifier = new ComboBox<>("Modifier", Arrays.asList("is a", "is an", " "));
        one_firstModifier.setValue(" ");
        one_firstModifier.addValueChangeListener(
                e -> {
                    updateUI();
                });

        twoA_firstVariable_Concept = createConceptSelectionComponent();
        twoB_firstVariable_Relation = createRelationSelectionComponent();

        three_secondModifier = new ComboBox<>("Modifier", Arrays.asList("equal-to", " "));
        three_secondModifier.setValue("equal-to");
        three_secondModifier.addValueChangeListener(
                e -> {
                    updateUI();
                });

        fourA_thirdVariable = createConceptSelectionComponent();
        fourB_thirdVariable = createTextfieldWidget();
        
        componentRuleLayout.add(
                one_firstModifier,
                twoB_firstVariable_Relation,
                three_secondModifier,
                fourA_thirdVariable);
        add(componentRuleLayout);
        updateUI();
    }
    
    private ConceptSelectionComponent createConceptSelectionComponent()
    {
    	ConceptSelectionComponent conceptVariable = new ConceptSelectionComponent();
    	conceptVariable.addListener(ConceptListUpdateRequestedEvent.class, this::fireEvent);
    	conceptVariable.setPlaceholder("Concept");
    	conceptVariable.setLabel("Concept");
        return conceptVariable;
    }
    
    private PredicateSelectionComponent createRelationSelectionComponent()
    {
    	PredicateSelectionComponent conceptVariable = new PredicateSelectionComponent();
    	conceptVariable.addListener(RelationListUpdateRequestedEvent.class, this::fireEvent);
    	conceptVariable.setPlaceholder("Relation");
    	conceptVariable.setLabel("Relation");
        return conceptVariable;
    }
    
    private ConceptTextfieldWidget createTextfieldWidget()
    {
    	ConceptTextfieldWidget freeTextVariable = new ConceptTextfieldWidget();
    	freeTextVariable.setPlaceholder("+/- [0-9] / String");
    	freeTextVariable.setLabel("Integer or String");
    	return freeTextVariable;
    }

    private void updateUI() {
        componentRuleLayout.removeAll();

        if (!one_firstModifier.getValue().equals(" ")) {
            isInUpperBranch = true;
            componentRuleLayout.add(one_firstModifier, twoA_firstVariable_Concept);
            return;
        }

        isInUpperBranch = false;
        componentRuleLayout.add(
                one_firstModifier,
                twoB_firstVariable_Relation,
                three_secondModifier);

        if (three_secondModifier.getValue().equals("equal-to")) {
        	componentRuleLayout.add(fourB_thirdVariable);
        	conceptRequired = false;
            return;
        }   
        componentRuleLayout.add(fourA_thirdVariable);
        conceptRequired = true;
    }

    @Override
    public String getRuleString() {
        StringBuilder sBuilder = new StringBuilder();
        sBuilder.append(one_firstModifier.getValue() + " ");
        if (isInUpperBranch) {
            sBuilder.append(twoA_firstVariable_Concept.getValue() + " ");
        } else {
            sBuilder.append(twoB_firstVariable_Relation.getValue() + " ");
            sBuilder.append(three_secondModifier.getValue() + " ");
            if(conceptRequired)
            {
            	sBuilder.append(fourA_thirdVariable.getValue());
            	return sBuilder.toString();
            }
            sBuilder.append(fourB_thirdVariable.getValue());
        }
        return sBuilder.toString();
    }
    
    @Override
    public <T extends ComponentEvent<?>> Registration addListener(
            final Class<T> eventType, final ComponentEventListener<T> listener) {
        return getEventBus().addListener(eventType, listener);
    }
}
