package org.vaadin.example.queryview;

import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.formlayout.FormLayout.ResponsiveStep;
import com.vaadin.flow.component.formlayout.FormLayout.ResponsiveStep.LabelsPosition;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;

class GeneralInfoLayout extends HorizontalLayout {
    VerticalLayout mainLayout = new VerticalLayout();
    Label layoutLabel = new Label("Statistical Information");
    FormLayout formLayout = new FormLayout();
    Label numberOfViolationsValue = new Label("-1");
    Label numberOfPackagesValue = new Label("-1");
    Label numberOfRelationshipsValue = new Label("-1");
    Label numberOfTypesValue = new Label("-1");

    public GeneralInfoLayout() {
        formLayout.setResponsiveSteps(new ResponsiveStep("0", 2, LabelsPosition.ASIDE));
        formLayout.addFormItem(numberOfViolationsValue, "Number of Violations");
        formLayout.addFormItem(numberOfPackagesValue, "Number of Packages");
        formLayout.addFormItem(numberOfTypesValue, "Number of Types");
        formLayout.addFormItem(numberOfRelationshipsValue, "Number of Relationships");
        formLayout.getStyle().set("border", "1px solid black");
        mainLayout.addAndExpand(layoutLabel, formLayout);
        addAndExpand(mainLayout);
    }

    public void updateInfo(int numVio, int numPackages, int numTypes, int numRelationships) {
        numberOfViolationsValue.setText(String.valueOf(numVio));
        numberOfPackagesValue.setText(String.valueOf(numPackages));
        numberOfTypesValue.setText(String.valueOf(numTypes));
        numberOfRelationshipsValue.setText(String.valueOf(numRelationships));
    }
}
