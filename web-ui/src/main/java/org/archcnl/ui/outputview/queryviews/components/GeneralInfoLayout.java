package org.archcnl.ui.outputview.queryviews.components;

import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.formlayout.FormLayout.ResponsiveStep;
import com.vaadin.flow.component.formlayout.FormLayout.ResponsiveStep.LabelsPosition;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;

public class GeneralInfoLayout extends VerticalLayout {

    private static final long serialVersionUID = 1L;

    Label layoutLabel = new Label("Statistical Information");
    FormLayout formLayout = new FormLayout();
    Label numberOfViolationsValue = new Label("-1");
    Label numberOfPackagesValue = new Label("-1");
    Label numberOfRelationshipsValue = new Label("-1");
    Label numberOfTypesValue = new Label("-1");

    public GeneralInfoLayout() {
        formLayout.setResponsiveSteps(new ResponsiveStep("0", 2, LabelsPosition.ASIDE));
        final String formItemCssClass = "general-info-box--item-lable";
        final Label violationsNumber = new Label("Number of Violations");
        violationsNumber.addClassName(formItemCssClass);
        final Label packagesNumber = new Label("Number of Packages");
        packagesNumber.addClassName(formItemCssClass);
        final Label typesNumber = new Label("Number of Types");
        typesNumber.addClassName(formItemCssClass);
        final Label relationshipsNumber = new Label("Number of Relationships");
        relationshipsNumber.addClassName(formItemCssClass);
        formLayout.addFormItem(numberOfViolationsValue, violationsNumber);
        formLayout.addFormItem(numberOfPackagesValue, packagesNumber);
        formLayout.addFormItem(numberOfTypesValue, typesNumber);
        formLayout.addFormItem(numberOfRelationshipsValue, relationshipsNumber);
        formLayout.setClassName("general-info-box");
        layoutLabel.setClassName("label-title");
        add(layoutLabel, formLayout);
    }

    public void update(
            String nrOfViolations,
            String nrOfPackages,
            String nrOfRelationships,
            String nrOfTypes) {
        numberOfViolationsValue.setText(nrOfViolations);
        numberOfPackagesValue.setText(nrOfPackages);
        numberOfRelationshipsValue.setText(nrOfRelationships);
        numberOfTypesValue.setText(nrOfTypes);
    }
}
