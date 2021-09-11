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
    TreeGrid<ListEntry<Concept>> conceptTreeGrid = new TreeGrid<>();
    TreeGrid<ListEntry<Relation>> relationTreeGrid = new TreeGrid<>();

    public ConceptAndRelationView() {
        createNewConceptLayout.setHeight(50, Unit.PERCENTAGE);
        createNewRelationLayout.setHeight(50, Unit.PERCENTAGE);

        setUpBottomBar();
        setUpConceptView();
        setUpRelationView();

        add(createNewConceptLayout);
        add(createNewRelationLayout);
        
        /**
        TreeGrid<Relation> relations = new TreeGrid<>();
        RulesConceptsAndRelations car = RulesConceptsAndRelations.getInstance();
        List<Relation> a = car.getRelationManager().getRelations();
        relations.setItems(a);
        relations.addHierarchyColumn(Relation::toStringRepresentation);
        
        relations.addExpandListener(event -> System.out.println(event.getItems().size()));
        relations.addItemDoubleClickListener(event -> System.out.println(event.getItem().toString()));
        relations.addCollapseListener(event -> System.out.println(event.getItems().size()));
        createNewRelationLayout.add(relations);
        */
        
        //ListEntry entry = new ListEntry(null);
        //createNewRelationLayout.add(entry);
        
        createNewConceptLayout.add(conceptTreeGrid);
        createNewRelationLayout.add(relationTreeGrid);
        
        add(bottomBarLayout);
        getStyle().set("border", "1px solid black");
        
    }

	public void setUpBottomBar() {
        final Button saveButton = new Button("Save");
        final Button checkButton = new Button("Check");
        bottomBarLayout.add(saveButton, checkButton);
    }
    
	public void setUpConceptView() {
		conceptTreeGrid = new TreeGrid<>();
		conceptTreeGrid.setWidthFull();
		RulesConceptsAndRelations car = RulesConceptsAndRelations.getInstance();
		List<Concept> manager = car.getConceptManager().getConcepts();
		List<ListEntry<Concept>> data = new LinkedList<>();
		ListEntry<Concept> defaultConceptsStub = new ListEntry<>("Default Concepts", manager);
		data.add(defaultConceptsStub);
		
		
		conceptTreeGrid.setItems(data, ListEntry::getHierarchicalChildren);
		conceptTreeGrid.addComponentHierarchyColumn(entry -> {
			ListEntryLayout<Concept> entryLayout = new ListEntryLayout<>(entry);
			entryLayout.setSizeFull();
			entryLayout.setPadding(false);
			entryLayout.setSpacing(false);
			//entry.setMinWidth("500px");
			return entryLayout;
		});
		conceptTreeGrid.setWidthFull();
		conceptTreeGrid.addExpandListener(event -> System.out.println(event.getItems().size()));
		conceptTreeGrid.addItemDoubleClickListener(event -> System.out.println(event.getItem().toString()));
		conceptTreeGrid.addCollapseListener(event -> System.out.println(event.getItems().size()));
	}
	
	private void setUpRelationView() {
        relationTreeGrid = new TreeGrid<>();
        relationTreeGrid.setWidthFull();
        RulesConceptsAndRelations car = RulesConceptsAndRelations.getInstance();
        List<Relation> manager = car.getRelationManager().getRelations();
        List<ListEntry<Relation>> data = new LinkedList<>();
        ListEntry<Relation> defaultConceptsStub = new ListEntry<>("Default Relations", manager);
        data.add(defaultConceptsStub);
        
    	
        relationTreeGrid.setItems(data, ListEntry::getHierarchicalChildren);
        relationTreeGrid.addComponentHierarchyColumn(entry -> {
    		ListEntryLayout<Relation> entryLayout = new ListEntryLayout<>(entry);
    		entryLayout.setSizeFull();
    		entryLayout.setPadding(false);
    		entryLayout.setSpacing(false);
    		//entry.setMinWidth("500px");
    		return entryLayout;
    	});
        relationTreeGrid.setWidthFull();
        relationTreeGrid.addExpandListener(event -> System.out.println(event.getItems().size()));
        relationTreeGrid.addItemDoubleClickListener(event -> System.out.println(event.getItem().toString()));
        relationTreeGrid.addCollapseListener(event -> System.out.println(event.getItems().size()));
    }
		
}