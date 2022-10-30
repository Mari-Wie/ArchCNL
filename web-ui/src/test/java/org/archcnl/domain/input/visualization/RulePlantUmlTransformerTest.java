package org.archcnl.domain.input.visualization;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import org.archcnl.domain.common.ConceptManager;
import org.archcnl.domain.common.RelationManager;
import org.archcnl.domain.common.conceptsandrelations.CustomConcept;
import org.archcnl.domain.common.conceptsandrelations.CustomRelation;
import org.archcnl.domain.common.conceptsandrelations.andtriplets.AndTriplets;
import org.archcnl.domain.common.exceptions.ConceptAlreadyExistsException;
import org.archcnl.domain.common.exceptions.ConceptDoesNotExistException;
import org.archcnl.domain.common.exceptions.RelationAlreadyExistsException;
import org.archcnl.domain.common.exceptions.UnrelatedMappingException;
import org.archcnl.domain.common.io.importhelper.MappingParser;
import org.archcnl.domain.common.io.importhelper.exceptions.NoMappingException;
import org.archcnl.domain.common.io.importhelper.exceptions.NoTripletException;
import org.archcnl.domain.input.model.architecturerules.ArchitectureRule;
import org.archcnl.domain.input.model.mappings.ConceptMapping;
import org.archcnl.domain.input.model.mappings.RelationMapping;
import org.archcnl.domain.input.visualization.exceptions.MappingToUmlTranslationFailedException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RulePlantUmlTransformerTest {

    private ConceptManager conceptManager;
    private RelationManager relationManager;

    @BeforeEach
    private void setup() throws ConceptDoesNotExistException {
        conceptManager = new ConceptManager();
        relationManager = new RelationManager(conceptManager);
    }

    @Test
    void givenEveryAggregateRule_whenTransform_thenCorrectPlantUml()
            throws MappingToUmlTranslationFailedException, NoMappingException, NoTripletException,
                    UnrelatedMappingException, ConceptAlreadyExistsException,
                    RelationAlreadyExistsException {
        // given
        CustomConcept aggregateConcept = new CustomConcept("Aggregate", "");
        String aggregateMappingString =
                "isAggregate: (?class rdf:type famix:FamixClass)"
                        + " (?class famix:hasName ?name)"
                        + " regex(?name, '(\\\\w||\\\\W)*Aggregate')"
                        + " -> (?class rdf:type architecture:Aggregate)";
        ConceptMapping aggregateMapping =
                createConceptMapping(
                        aggregateMappingString, Collections.emptyList(), aggregateConcept);
        aggregateConcept.setMapping(aggregateMapping);
        conceptManager.addConcept(aggregateConcept);

        String resideInMappingString =
                "resideInMapping: (?class rdf:type famix:FamixClass)"
                        + " (?package rdf:type famix:Namespace)"
                        + " (?package famix:namespaceContains ?class)"
                        + " -> (?class architecture:resideIn ?package)";
        RelationMapping resideInMapping =
                createRelationMapping(resideInMappingString, Collections.emptyList());
        CustomRelation resideInRelation =
                new CustomRelation("resideIn", "", new HashSet<>(), new HashSet<>());
        resideInRelation.setMapping(resideInMapping, conceptManager);
        relationManager.addRelation(resideInRelation);

        CustomConcept domainRingConcept = new CustomConcept("DomainRing", "");
        String domainRingMappingString =
                "isDomainRing: (?package rdf:type famix:Namespace)"
                        + " (?package famix:hasName ?name)"
                        + " regex(?name, 'domain')"
                        + " -> (?package rdf:type architecture:DomainRing)";
        ConceptMapping domainRingMapping =
                createConceptMapping(
                        domainRingMappingString, Collections.emptyList(), domainRingConcept);
        domainRingConcept.setMapping(domainRingMapping);
        conceptManager.addConcept(domainRingConcept);

        var rule = new ArchitectureRule("Every Aggregate must resideIn a DomainRing.");

        // when
        PlantUmlTransformer transformer = new PlantUmlTransformer(conceptManager, relationManager);
        String plantUmlCode = transformer.transformToPlantUml(rule);

        // then
        String expectedCode =
                "@startuml\n"
                        + "title Every Aggregate must resideIn a DomainRing.\n"
                        + "folder \"domain\" as domainRing {\n"
                        + "class \"(\\\\w||\\\\W)*Aggregate\" as aggregateC #RoyalBlue {\n"
                        + "}\n"
                        + "}\n"
                        + "class \"(\\\\w||\\\\W)*Aggregate\" as aggregateW #OrangeRed {\n"
                        + "}\n"
                        + "\n"
                        + "aggregateC -[bold]-> domainRing\n"
                        + "note on link: resideIn\n"
                        + "note \"DomainRing\" as DomainRing\n"
                        + "DomainRing .. domainRing\n"
                        + "note \"Aggregate\" as Aggregate1\n"
                        + "Aggregate1 .. aggregateW\n"
                        + "note \"Aggregate\" as Aggregate\n"
                        + "Aggregate .. aggregateC\n"
                        + "@enduml";
        Assertions.assertEquals(expectedCode, plantUmlCode);
    }

    @Test
    void givenOnlyUserRule_whenTransform_thenCorrectPlantUml()
            throws MappingToUmlTranslationFailedException, NoMappingException, NoTripletException,
                    UnrelatedMappingException, ConceptAlreadyExistsException,
                    RelationAlreadyExistsException {
        // given
        CustomConcept subjectConcept = new CustomConcept("ClientScript", "");
        String subjectMappingString =
                "isClientScript: (?class rdf:type famix:FamixClass)"
                        + " (?package rdf:type famix:Namespace)"
                        + " (?package famix:hasName ?name)"
                        + " regex(?name, 'teammates\\\\.client(\\\\w|\\\\W)*')"
                        + " (?package famix:namespaceContains ?class)"
                        + " -> (?class rdf:type architecture:ClientScript)";
        ConceptMapping subjectMapping =
                createConceptMapping(subjectMappingString, Collections.emptyList(), subjectConcept);
        subjectConcept.setMapping(subjectMapping);
        conceptManager.addConcept(subjectConcept);

        String predicateMappingString =
                "useMapping: (?class rdf:type famix:FamixClass)"
                        + " (?class2 rdf:type architecture:ClientScript)"
                        + " (?class famix:imports ?class2)"
                        + " -> (?class architecture:use ?class2)";
        RelationMapping predicateMapping =
                createRelationMapping(predicateMappingString, Collections.emptyList());
        CustomRelation predicateRelation =
                new CustomRelation("use", "", new HashSet<>(), new HashSet<>());
        predicateRelation.setMapping(predicateMapping, conceptManager);
        relationManager.addRelation(predicateRelation);

        var rule = new ArchitectureRule("Only a ClientScript can use a ClientScript.");

        // when
        PlantUmlTransformer transformer = new PlantUmlTransformer(conceptManager, relationManager);
        String plantUmlCode = transformer.transformToPlantUml(rule);

        // then
        String expectedCode =
                "@startuml\n"
                        + "title Only a ClientScript can use a ClientScript.\n"
                        + "class \"?famixClass\" as famixClass {\n"
                        + "}\n"
                        + "folder \"teammates\\\\.client(\\\\w|\\\\W)*\" as package {\n"
                        + "class \"?clientScript\" as clientScript {\n"
                        + "}\n"
                        + "}\n"
                        + "folder \"teammates\\\\.client(\\\\w|\\\\W)*\" as package1 {\n"
                        + "class \"?clientScript1\" as clientScript1 {\n"
                        + "}\n"
                        + "}\n"
                        + "clientScript -[dashed]-> clientScript1 #line:RoyalBlue;text:RoyalBlue : <<imports>>\n"
                        + "clientScript -[bold]-> clientScript1 #line:RoyalBlue;text:RoyalBlue \n"
                        + "note on link: use\n"
                        + "famixClass -[dashed]-> clientScript1 #line:OrangeRed;text:OrangeRed : <<imports>>\n"
                        + "famixClass -[bold]-> clientScript1 #line:OrangeRed;text:OrangeRed \n"
                        + "note on link: use\n"
                        + "note \"ClientScript\" as ClientScript\n"
                        + "ClientScript .. clientScript\n"
                        + "note \"ClientScript\" as ClientScript1\n"
                        + "ClientScript1 .. clientScript1\n"
                        + "@enduml";
        Assertions.assertEquals(expectedCode, plantUmlCode);
    }

    @Test
    void givenEveryTestResultCanOnlyRule_whenTransform_thenCorrectPlantUml()
            throws MappingToUmlTranslationFailedException, NoMappingException, NoTripletException,
                    UnrelatedMappingException, ConceptAlreadyExistsException,
                    RelationAlreadyExistsException {
        // given
        CustomConcept subjectConcept = new CustomConcept("TestResult", "");
        String subjectMappingString =
                "isTestResult: (?class rdf:type famix:FamixClass)"
                        + " (?class famix:hasName ?name)"
                        + " regex(?name, '(\\\\w||\\\\W)*TestResultEntity')"
                        + " -> (?class rdf:type architecture:TestResult)";
        ConceptMapping subjectMapping =
                createConceptMapping(subjectMappingString, Collections.emptyList(), subjectConcept);
        subjectConcept.setMapping(subjectMapping);
        conceptManager.addConcept(subjectConcept);

        CustomConcept repositoryConcept = new CustomConcept("Repository", "");
        String repositoryMappingString =
                "isRepository: (?class rdf:type famix:FamixClass)"
                        + " (?class famix:hasName ?name)"
                        + " regex(?name, '(\\\\w||\\\\W)*Repository')"
                        + " -> (?class rdf:type architecture:Repository)";
        ConceptMapping repositoryMapping =
                createConceptMapping(
                        repositoryMappingString, Collections.emptyList(), repositoryConcept);
        repositoryConcept.setMapping(repositoryMapping);
        conceptManager.addConcept(repositoryConcept);

        String predicateMappingString =
                "bestoredMapping: (?repositoryClass rdf:type architecture:Repository)"
                        + " (?repositoryClass famix:imports ?entityClass)"
                        + " (?entityClass famix:isExternal 'false'^^xsd:boolean)"
                        + " -> (?entityClass architecture:bestored ?repositoryClass)";
        RelationMapping predicateMapping =
                createRelationMapping(predicateMappingString, Collections.emptyList());
        CustomRelation predicateRelation =
                new CustomRelation("bestored", "", new HashSet<>(), new HashSet<>());
        predicateRelation.setMapping(predicateMapping, conceptManager);
        relationManager.addRelation(predicateRelation);

        CustomConcept objectConcept = new CustomConcept("TestResultServer", "");
        String objectMappingString =
                "isTestresultserver: (?class rdf:type famix:FamixClass)"
                        + " (?testresultpackage rdf:type famix:Namespace)"
                        + " (?testresultpackage famix:hasName ?testresultpackagename)"
                        + " regex(?testresultpackagename, 'app\\\\.coronawarn\\\\.testresult\\\\.?(\\\\w||\\\\W)*')"
                        + " (?testresultpackage famix:namespaceContains ?class)"
                        + " -> (?class rdf:type architecture:Testresultserver)";
        ConceptMapping objectMapping =
                createConceptMapping(objectMappingString, Collections.emptyList(), objectConcept);
        objectConcept.setMapping(objectMapping);
        conceptManager.addConcept(objectConcept);

        var rule = new ArchitectureRule("Every TestResult can-only bestored TestResultServer.");

        // when
        PlantUmlTransformer transformer = new PlantUmlTransformer(conceptManager, relationManager);
        String plantUmlCode = transformer.transformToPlantUml(rule);

        // then
        String expectedCode =
                "@startuml\n"
                        + "title Every TestResult can-only bestored TestResultServer.\n"
                        + "class \"?famixClass\" as famixClass {\n"
                        + "}\n"
                        + "folder \"app\\\\.coronawarn\\\\.testresult\\\\.?(\\\\w||\\\\W)*\" as testresultpackage {\n"
                        + "class \"?testResultServer\" as testResultServer {\n"
                        + "}\n"
                        + "}\n"
                        + "class \"(\\\\w||\\\\W)*TestResultEntity\" as testResult {\n"
                        + "}\n"
                        + "testResultServer -[dashed]-> testResult #line:RoyalBlue;text:RoyalBlue : <<imports>>\n"
                        + "testResult -[bold]-> testResultServer #line:RoyalBlue;text:RoyalBlue \n"
                        + "note on link: bestored\n"
                        + "famixClass -[dashed]-> testResult #line:OrangeRed;text:OrangeRed : <<imports>>\n"
                        + "testResult -[bold]-> famixClass #line:OrangeRed;text:OrangeRed \n"
                        + "note on link: bestored\n"
                        + "note \"TestResultServer\" as TestResultServer\n"
                        + "TestResultServer .. testResultServer\n"
                        + "note \"TestResult\" as TestResult\n"
                        + "TestResult .. testResult\n"
                        + "@enduml";
        Assertions.assertEquals(expectedCode, plantUmlCode);
    }

    @Test
    void givenNoTestResultServerRule_whenTransform_thenCorrectPlantUml()
            throws MappingToUmlTranslationFailedException, NoMappingException, NoTripletException,
                    UnrelatedMappingException, ConceptAlreadyExistsException,
                    RelationAlreadyExistsException {
        // given
        CustomConcept subjectConcept = new CustomConcept("Testresultserver", "");
        String subjectMappingString =
                "isTestresultserver: (?class rdf:type famix:FamixClass)"
                        + " (?testresultpackage rdf:type famix:Namespace)"
                        + " (?testresultpackage famix:hasName ?testresultpackagename)"
                        + " regex(?testresultpackagename, 'app\\\\.coronawarn\\\\.testresult\\\\.?(\\\\w||\\\\W)*')"
                        + " (?testresultpackage famix:namespaceContains ?class)"
                        + " -> (?class rdf:type architecture:Testresultserver)";
        ConceptMapping subjectMapping =
                createConceptMapping(subjectMappingString, Collections.emptyList(), subjectConcept);
        subjectConcept.setMapping(subjectMapping);
        conceptManager.addConcept(subjectConcept);

        CustomConcept repositoryConcept = new CustomConcept("Repository", "");
        String repositoryMappingString =
                "isRepository: (?class rdf:type famix:FamixClass)"
                        + " (?class famix:hasName ?name)"
                        + " regex(?name, '(\\\\w||\\\\W)*Repository')"
                        + " -> (?class rdf:type architecture:Repository)";
        ConceptMapping repositoryMapping =
                createConceptMapping(
                        repositoryMappingString, Collections.emptyList(), repositoryConcept);
        repositoryConcept.setMapping(repositoryMapping);
        conceptManager.addConcept(repositoryConcept);

        String predicateMappingString =
                "storeMapping: (?repositoryClass rdf:type architecture:Repository)"
                        + " (?repositoryClass famix:imports ?entityClass)"
                        + " (?entityClass famix:isExternal 'false'^^xsd:boolean)"
                        + " -> (?repositoryClass architecture:store ?entityClass)";
        RelationMapping predicateMapping =
                createRelationMapping(predicateMappingString, Collections.emptyList());
        CustomRelation predicateRelation =
                new CustomRelation("store", "", new HashSet<>(), new HashSet<>());
        predicateRelation.setMapping(predicateMapping, conceptManager);
        relationManager.addRelation(predicateRelation);

        CustomConcept objectConcept = new CustomConcept("GUID", "");
        String objectMappingString =
                "isGUID: (?class rdf:type famix:FamixClass)"
                        + " (?class2 rdf:type famix:FamixClass)"
                        + " (?class2 famix:hasFullQualifiedName 'java.util.UUID')"
                        + " (?class famix:imports ?class2)"
                        + " -> (?class rdf:type architecture:GUID)";
        ConceptMapping objectMapping =
                createConceptMapping(objectMappingString, Collections.emptyList(), objectConcept);
        objectConcept.setMapping(objectMapping);
        conceptManager.addConcept(objectConcept);

        var rule = new ArchitectureRule("No Testresultserver can store GUID.");

        // when
        PlantUmlTransformer transformer = new PlantUmlTransformer(conceptManager, relationManager);
        String plantUmlCode = transformer.transformToPlantUml(rule);

        // then
        String expectedCode =
                "@startuml\n"
                        + "title No Testresultserver can store GUID.\n"
                        + "class \"java.util.UUID\" as class2 {\n"
                        + "}\n"
                        + "class \"?gUID\" as gUID {\n"
                        + "}\n"
                        + "gUID -[dashed]-> class2: <<imports>>\n"
                        + "folder \"app\\\\.coronawarn\\\\.testresult\\\\.?(\\\\w||\\\\W)*\" as testresultpackage {\n"
                        + "class \"?testresultserver\" as testresultserver {\n"
                        + "}\n"
                        + "}\n"
                        + "testresultserver -[dashed]-> gUID #line:OrangeRed;text:OrangeRed : <<imports>>\n"
                        + "testresultserver -[bold]-> gUID #line:OrangeRed;text:OrangeRed \n"
                        + "note on link: store\n"
                        + "note \"GUID\" as GUID\n"
                        + "GUID .. gUID\n"
                        + "note \"Testresultserver\" as Testresultserver\n"
                        + "Testresultserver .. testresultserver\n"
                        + "@enduml";
        Assertions.assertEquals(expectedCode, plantUmlCode);
    }

    @Test
    void givenNothingRule_whenTransform_thenCorrectPlantUml()
            throws MappingToUmlTranslationFailedException, NoMappingException, NoTripletException,
                    UnrelatedMappingException, ConceptAlreadyExistsException,
                    RelationAlreadyExistsException {
        // given

        String predicateMappingString =
                "useMapping: (?class rdf:type famix:FamixClass)"
                        + " (?class2 rdf:type famix:FamixClass)"
                        + " (?class famix:imports ?class2)"
                        + " -> (?class architecture:use ?class2)";
        RelationMapping predicateMapping =
                createRelationMapping(predicateMappingString, Collections.emptyList());
        CustomRelation predicateRelation =
                new CustomRelation("use", "", new HashSet<>(), new HashSet<>());
        predicateRelation.setMapping(predicateMapping, conceptManager);
        relationManager.addRelation(predicateRelation);

        CustomConcept objectConcept = new CustomConcept("Controller", "");
        String objectMappingString =
                "isController: (?class rdf:type famix:FamixClass)"
                        + " (?class famix:hasAnnotationInstance ?instance)"
                        + " (?instance famix:hasAnnotationType ?annoType)"
                        + " (?annoType famix:hasName 'Controller')"
                        + " -> (?class rdf:type architecture:Controller)";
        ConceptMapping objectMapping =
                createConceptMapping(objectMappingString, Collections.emptyList(), objectConcept);
        objectConcept.setMapping(objectMapping);
        conceptManager.addConcept(objectConcept);

        var rule = new ArchitectureRule("Nothing can use a Controller.");

        // when
        PlantUmlTransformer transformer = new PlantUmlTransformer(conceptManager, relationManager);
        String plantUmlCode = transformer.transformToPlantUml(rule);

        // then
        String expectedCode =
                "@startuml\n"
                        + "title Nothing can use a Controller.\n"
                        + "class \"?controller\" as controller <<Controller>> {\n"
                        + "}\n"
                        + "annotation \"Controller\" as annoType {\n"
                        + "}\n"
                        + "class \"?nothing\" as nothing #OrangeRed {\n"
                        + "}\n"
                        + "nothing -[dashed]-> controller #line:OrangeRed;text:OrangeRed : <<imports>>\n"
                        + "nothing -[bold]-> controller #line:OrangeRed;text:OrangeRed \n"
                        + "note on link: use\n"
                        + "note \"Controller\" as Controller\n"
                        + "Controller .. controller\n"
                        + "@enduml";
        Assertions.assertEquals(expectedCode, plantUmlCode);
    }

    private ConceptMapping createConceptMapping(
            String mappingString, List<String> additionalWhens, CustomConcept thisConcept)
            throws NoTripletException, NoMappingException, UnrelatedMappingException {
        ConceptMapping mapping =
                MappingParser.parseMapping(
                        mappingString, thisConcept, relationManager, conceptManager);
        for (String additionalWhenString : additionalWhens) {
            AndTriplets additionalWhen =
                    MappingParser.parseWhenPart(
                            additionalWhenString, relationManager, conceptManager);
            mapping.addAndTriplets(additionalWhen);
        }

        // To enable wrapper trick in PlantUmlTransformer
        CustomConcept concept = (CustomConcept) mapping.getThenTriplet().getObject();
        concept.setMapping(mapping);
        return mapping;
    }

    private RelationMapping createRelationMapping(
            String mappingString, List<String> additionalWhens)
            throws NoTripletException, NoMappingException, UnrelatedMappingException {
        RelationMapping mapping =
                MappingParser.parseMapping(mappingString, relationManager, conceptManager);
        for (String additionalWhenString : additionalWhens) {
            AndTriplets additionalWhen =
                    MappingParser.parseWhenPart(
                            additionalWhenString, relationManager, conceptManager);
            mapping.addAndTriplets(additionalWhen);
        }

        // To enable wrapper trick in PlantUmlTransformer
        CustomRelation relation = (CustomRelation) mapping.getThenTriplet().getPredicate();
        relation.setMapping(mapping, conceptManager);
        return mapping;
    }
}
