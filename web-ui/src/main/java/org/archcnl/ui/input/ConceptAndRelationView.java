package org.archcnl.ui.input;

import com.vaadin.flow.component.Unit;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.treegrid.TreeGrid;
import java.util.LinkedList;
import java.util.List;
import org.archcnl.domain.input.datatypes.RulesConceptsAndRelations;
import org.archcnl.domain.input.datatypes.mappings.Concept;
import org.archcnl.domain.input.datatypes.mappings.Relation;

public class ConceptAndRelationView extends VerticalLayout {

    private static final long serialVersionUID = 1L;

    CreateNewLayout createNewConceptLayout = new CreateNewLayout("Concepts", "Create new concept");
    CreateNewLayout createNewRelationLayout =
            new CreateNewLayout("Relations", "Create new relation");
    HorizontalLayout bottomBarLayout = new HorizontalLayout();
    TreeGrid<TreeGridListEntry<Concept>> conceptTreeGrid = new TreeGrid<>();
    TreeGrid<TreeGridListEntry<Relation>> relationTreeGrid = new TreeGrid<>();

    public ConceptAndRelationView() {
        createNewConceptLayout.setHeight(50, Unit.PERCENTAGE);
        createNewRelationLayout.setHeight(50, Unit.PERCENTAGE);

        setUpBottomBar();
        setUpConceptView();
        setUpRelationView();

        add(createNewConceptLayout);
        add(createNewRelationLayout);
        add(bottomBarLayout);
        getStyle().set("border", "1px solid black");

        createNewConceptLayout.add(conceptTreeGrid);
        createNewRelationLayout.add(relationTreeGrid);
    }

    public void setUpBottomBar() {
        final Button saveButton = new Button("Save");
        final Button checkButton = new Button("Check");
        bottomBarLayout.add(saveButton, checkButton);
    }

    public void setUpConceptView() {
        conceptTreeGrid = new TreeGrid<>();

        List<Concept> manager = RulesConceptsAndRelations.getInstance().getConceptManager().getConcepts();

        List<TreeGridListEntry<Concept>> data = new LinkedList<>();
        TreeGridListEntry<Concept> defaultConceptsStub = new TreeGridListEntry<>("Default Concepts", manager);
        data.add(defaultConceptsStub);

        conceptTreeGrid.setItems(data, TreeGridListEntry::getHierarchicalChildren);
        conceptTreeGrid.addComponentHierarchyColumn(
                entry -> {
                    TreeGridListEntryLayout<Concept> entryLayout = new TreeGridListEntryLayout<>(entry);
                    entryLayout.setSizeFull();
                    return entryLayout;
                });
    }

    private void setUpRelationView() {
        relationTreeGrid = new TreeGrid<>();

        List<Relation> manager = RulesConceptsAndRelations.getInstance().getRelationManager().getRelations();

        // no data hierarchy in relations yet
        List<TreeGridListEntry<Relation>> data = new LinkedList<>();
        TreeGridListEntry<Relation> defaultConceptsStub = new TreeGridListEntry<>("Default Relations", manager);
        data.add(defaultConceptsStub);

        relationTreeGrid.setItems(data, TreeGridListEntry::getHierarchicalChildren);
        relationTreeGrid.addComponentHierarchyColumn(
                entry -> {
                    TreeGridListEntryLayout<Relation> entryLayout = new TreeGridListEntryLayout<>(entry);
                    entryLayout.setSizeFull();
                    return entryLayout;
                });
    }
}
