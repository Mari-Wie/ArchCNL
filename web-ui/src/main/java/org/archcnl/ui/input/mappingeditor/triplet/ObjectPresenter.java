package org.archcnl.ui.input.mappingeditor.triplet;

import java.util.List;
import java.util.stream.Collectors;
import org.archcnl.domain.input.exceptions.ConceptDoesNotExistException;
import org.archcnl.domain.input.model.RulesConceptsAndRelations;
import org.archcnl.domain.input.model.mappings.Concept;
import org.archcnl.ui.input.mappingeditor.exceptions.ConceptNotDefinedException;
import org.archcnl.ui.input.mappingeditor.triplet.ObjectContract.View;

public class ObjectPresenter implements ObjectContract.Presenter<View> {

    private static final long serialVersionUID = -6274011448119690642L;
    private View view;

    @Override
    public void setView(View view) {
        this.view = view;
    }

    @Override
    public List<String> getConceptNames() {
        return RulesConceptsAndRelations.getInstance().getConceptManager().getConcepts().stream()
                .map(Concept::getName)
                .collect(Collectors.toList());
    }

    public Concept getObject() throws ConceptDoesNotExistException, ConceptNotDefinedException {
        String conceptName = view.getSelectedItem().orElseThrow(ConceptNotDefinedException::new);
        return RulesConceptsAndRelations.getInstance()
                .getConceptManager()
                .getConceptByName(conceptName);
    }
}
