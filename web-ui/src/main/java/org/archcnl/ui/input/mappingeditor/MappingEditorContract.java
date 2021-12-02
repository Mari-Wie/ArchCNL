package org.archcnl.ui.input.mappingeditor;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;
import org.archcnl.domain.common.ObjectType;
import org.archcnl.domain.common.Variable;
import org.archcnl.domain.input.exceptions.InvalidVariableNameException;
import org.archcnl.ui.input.InputContract;
import org.archcnl.ui.input.mappingeditor.exceptions.SubjectOrObjectNotDefinedException;

public interface MappingEditorContract {

    public interface View {

        public void deleteAndTripletsView(AndTripletsEditorContract.View andTripletsView);

        public List<AndTripletsEditorPresenter> getAndTripletsPresenters();

        public void updateNameField(String newName);

        public void updateNameFieldInThenTriplet(String newName);

        public void showNameFieldErrorMessage(String message);

        public String getDescription();

        public void updateDescription(String description);

        public Variable getThenTripletSubject()
                throws SubjectOrObjectNotDefinedException, InvalidVariableNameException;

        public Optional<ObjectType> getThenTripletObject()
                throws SubjectOrObjectNotDefinedException, InvalidVariableNameException;

        public ObjectType getSelectedObjectTypeInThenTriplet();

        public void showThenSubjectErrorMessage(String message);

        public void showThenSubjectOrObjectErrorMessage(String message);

        public void setSubjectInThenTriplet(Variable subject);

        public void setObjectInThenTriplet(ObjectType object);

        public int getIndexOf(AndTripletsEditorContract.View andTripletsView);

        public void addAndTripletsViewAtIndex(int index, AndTripletsEditorView andTripletsView);

        public void addAndTripletsView(AndTripletsEditorView andTripletsView);

        public void clearContent();
    }

    public interface Presenter<T extends View> extends Serializable {

        public void setView(T view);

        public void nameHasChanged(String newName);

        public VariableManager getVariableManager();

        public void addNewAndTripletsViewAfter(AndTripletsEditorContract.View andTripletsView);

        public void deleteAndTripletsView(AndTripletsEditorContract.View andTripletsView);

        public int numberOfAndTriplets();

        public void doneButtonClicked(InputContract.Remote inputRemote);

        public void lastAndTripletsDeleted();

        public void showFirstAndTripletsView();

        public void descriptionHasChanged(String value);
    }
}
