package org.archcnl.domain.input.visualization;

import java.util.Arrays;
import org.archcnl.domain.common.ConceptManager;
import org.archcnl.domain.common.RelationManager;
import org.archcnl.domain.common.conceptsandrelations.Concept;
import org.archcnl.domain.common.conceptsandrelations.CustomConcept;
import org.archcnl.domain.common.conceptsandrelations.Relation;
import org.archcnl.domain.common.conceptsandrelations.TypeRelation;
import org.archcnl.domain.common.conceptsandrelations.andtriplets.AndTriplets;
import org.archcnl.domain.common.conceptsandrelations.andtriplets.triplet.Triplet;
import org.archcnl.domain.common.conceptsandrelations.andtriplets.triplet.Variable;
import org.archcnl.domain.common.exceptions.ConceptDoesNotExistException;
import org.archcnl.domain.input.visualization.exceptions.MappingToUmlTranslationFailedException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class PlantUmlTransformerTest {

    private ConceptManager conceptManager;
    private RelationManager relationManager;

    @BeforeEach
    private void setup() throws ConceptDoesNotExistException {
        conceptManager = new ConceptManager();
        relationManager = new RelationManager(conceptManager);
    }

    @Test
    void givenSimpleMapping_whenTransform_thenCorrectPlantUml()
            throws MappingToUmlTranslationFailedException {
        // given
        Variable clazz = new Variable("class");
        Relation typeRelation = TypeRelation.getTyperelation();
        Concept famixClass = conceptManager.getConceptByName("FamixClass").get();
        CustomConcept thenConcept = new CustomConcept("ThenConcept", "");
        AndTriplets whenTriplets =
                new AndTriplets(Arrays.asList(new Triplet(clazz, typeRelation, famixClass)));
        Triplet thenTriplet = new Triplet(clazz, typeRelation, thenConcept);

        // when
        PlantUmlTransformer transformer =
                new PlantUmlTransformer(conceptManager, whenTriplets, thenTriplet);
        String plantUmlCode = transformer.transformToPlantUml();

        // then
        String expectedCode =
                "@startuml\n"
                        + "class \"?class\" as class {\n"
                        + "}\n"
                        + "note \"ThenConcept\" as ThenConcept\n"
                        + "ThenConcept .. class\n"
                        + "@enduml";
        Assertions.assertEquals(expectedCode, plantUmlCode);
    }
}
