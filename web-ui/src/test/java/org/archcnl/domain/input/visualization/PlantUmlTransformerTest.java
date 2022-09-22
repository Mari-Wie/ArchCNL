package org.archcnl.domain.input.visualization;

import java.util.Arrays;
import java.util.HashSet;
import org.archcnl.domain.common.ConceptManager;
import org.archcnl.domain.common.RelationManager;
import org.archcnl.domain.common.conceptsandrelations.Concept;
import org.archcnl.domain.common.conceptsandrelations.CustomConcept;
import org.archcnl.domain.common.conceptsandrelations.CustomRelation;
import org.archcnl.domain.common.conceptsandrelations.JenaBuiltinRelation;
import org.archcnl.domain.common.conceptsandrelations.Relation;
import org.archcnl.domain.common.conceptsandrelations.TypeRelation;
import org.archcnl.domain.common.conceptsandrelations.andtriplets.AndTriplets;
import org.archcnl.domain.common.conceptsandrelations.andtriplets.triplet.BooleanValue;
import org.archcnl.domain.common.conceptsandrelations.andtriplets.triplet.StringValue;
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
    void givenSuperSimpleMapping_whenTransform_thenCorrectPlantUml()
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

    @Test
    void givenSimpleMapping_whenTransform_thenCorrectPlantUml()
            throws MappingToUmlTranslationFailedException {
        // given
        Variable clazz = new Variable("class");
        Relation typeRelation = TypeRelation.getTyperelation();
        Concept famixClass = conceptManager.getConceptByName("FamixClass").get();
        CustomConcept thenConcept = new CustomConcept("ThenConcept", "");
        Relation isInterface = relationManager.getRelationByName("isInterface").get();
        AndTriplets whenTriplets =
                new AndTriplets(
                        Arrays.asList(
                                new Triplet(clazz, typeRelation, famixClass),
                                new Triplet(clazz, isInterface, new BooleanValue(true))));
        Triplet thenTriplet = new Triplet(clazz, typeRelation, thenConcept);

        // when
        PlantUmlTransformer transformer =
                new PlantUmlTransformer(conceptManager, whenTriplets, thenTriplet);
        String plantUmlCode = transformer.transformToPlantUml();

        // then
        String expectedCode =
                "@startuml\n"
                        + "interface \"?class\" as class {\n"
                        + "}\n"
                        + "note \"ThenConcept\" as ThenConcept\n"
                        + "ThenConcept .. class\n"
                        + "@enduml";
        Assertions.assertEquals(expectedCode, plantUmlCode);
    }

    @Test
    void givenRelationMapping_whenTransform_thenCorrectPlantUml()
            throws MappingToUmlTranslationFailedException {
        // given
        Variable namespace = new Variable("namespace");
        Variable clazz = new Variable("class");
        Variable interfaceVar = new Variable("interface");
        Variable attribute = new Variable("attribute");
        Variable method = new Variable("method");
        Variable methodName = new Variable("methodName");
        Relation typeRelation = TypeRelation.getTyperelation();
        Concept famixClass = conceptManager.getConceptByName("FamixClass").get();
        Relation namespaceContains = relationManager.getRelationByName("namespaceContains").get();
        Relation hasName = relationManager.getRelationByName("hasName").get();
        Relation definesMethod = relationManager.getRelationByName("definesMethod").get();
        Relation definesAttribute = relationManager.getRelationByName("definesAttribute").get();
        Relation isInterface = relationManager.getRelationByName("isInterface").get();
        Relation hasModifier = relationManager.getRelationByName("hasModifier").get();
        Relation regex = JenaBuiltinRelation.getRegexRelation();

        AndTriplets whenTriplets =
                new AndTriplets(
                        Arrays.asList(
                                new Triplet(namespace, namespaceContains, clazz),
                                new Triplet(namespace, namespaceContains, interfaceVar),
                                new Triplet(clazz, typeRelation, famixClass),
                                new Triplet(clazz, hasName, new StringValue("ClassName")),
                                new Triplet(clazz, definesAttribute, attribute),
                                new Triplet(attribute, hasModifier, new StringValue("public")),
                                new Triplet(attribute, hasModifier, new StringValue("static")),
                                new Triplet(clazz, definesMethod, method),
                                new Triplet(method, hasName, methodName),
                                new Triplet(methodName, regex, new StringValue(".*main")),
                                new Triplet(method, hasModifier, new StringValue("abstract")),
                                new Triplet(method, hasModifier, new StringValue("private")),
                                new Triplet(interfaceVar, isInterface, new BooleanValue(true)),
                                new Triplet(interfaceVar, hasModifier, new StringValue("public"))));

        CustomRelation weirdRelation =
                new CustomRelation("weirdRelation", "", new HashSet<>(), new HashSet<>());
        Triplet thenTriplet = new Triplet(clazz, weirdRelation, interfaceVar);

        // when
        PlantUmlTransformer transformer =
                new PlantUmlTransformer(conceptManager, whenTriplets, thenTriplet);
        String plantUmlCode = transformer.transformToPlantUml();

        // then
        String expectedCode =
                "@startuml\n"
                        + "folder \"?namespace\" as namespace {\n"
                        + "	class \"ClassName\" as class {\n"
                        + "		{field} {static} +?attribute\n"
                        + "		{method} {abstract} -.*main()\n"
                        + "	}\n"
                        + "	+interface \"?interface\" as interface {\n"
                        + "	}\n"
                        + "}\n"
                        + "class -[bold]-> interface\n"
                        + "note on link: weirdRelation\n"
                        + "@enduml";
        Assertions.assertEquals(expectedCode, plantUmlCode);
    }

    @Test
    void givenSimpleMappingWithConnections_whenTransform_thenCorrectPlantUml()
            throws MappingToUmlTranslationFailedException {
        // given
        Variable file = new Variable("file");
        Variable clazz = new Variable("class");
        Variable clazz2 = new Variable("class2");
        Variable interfaceVar = new Variable("interface");
        Variable inheritance = new Variable("inheritance");
        Relation typeRelation = TypeRelation.getTyperelation();
        Concept artifactFile = conceptManager.getConceptByName("SoftwareArtifactFile").get();
        CustomConcept thenConcept = new CustomConcept("ThenConcept", "");
        Relation isInterface = relationManager.getRelationByName("isInterface").get();
        Relation imports = relationManager.getRelationByName("imports").get();
        Relation containsArtifact = relationManager.getRelationByName("containsArtifact").get();
        Relation hasSubClass = relationManager.getRelationByName("hasSubClass").get();
        Relation hasSuperClass = relationManager.getRelationByName("hasSuperClass").get();

        AndTriplets whenTriplets =
                new AndTriplets(
                        Arrays.asList(
                                new Triplet(file, typeRelation, artifactFile),
                                new Triplet(file, containsArtifact, clazz),
                                new Triplet(clazz, imports, clazz2),
                                new Triplet(file, containsArtifact, interfaceVar),
                                new Triplet(inheritance, hasSubClass, clazz),
                                new Triplet(inheritance, hasSuperClass, interfaceVar),
                                new Triplet(interfaceVar, isInterface, new BooleanValue(true))));
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
                        + "note as file\n"
                        + "	===File\n"
                        + "end note\n"
                        + "class \"?class2\" as class2 {\n"
                        + "}\n"
                        + "interface \"?interface\" as interface {\n"
                        + "}\n"
                        + "file +-- class\n"
                        + "class -[dashed]-> class2: <<imports>>\n"
                        + "file +-- interface\n"
                        + "note \"ThenConcept\" as ThenConcept\n"
                        + "ThenConcept .. class\n"
                        + "@enduml";
        Assertions.assertEquals(expectedCode, plantUmlCode);
    }
}
