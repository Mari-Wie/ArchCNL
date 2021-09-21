package org.archcnl.ui.input.mappingeditor.triplet;

import java.io.Serializable;

public interface TripletContract {

    public interface View {

        public void setAddButtonVisible(boolean visible);
    }

    public interface Presenter<T extends View> extends Serializable {

        public void setView(T view);

        public void setSubjectPresenter(SubjectPresenter subjectPresenter);

        public void setPredicatePresenter(PredicatePresenter predicatePresenter);

        public void setObjectPresenter(ObjectPresenter objectPresenter);

        public void mouseEnter();

        public void mouseLeave();
    }
}
