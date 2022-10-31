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

    @Test
    void givenIfRule_whenTransform_thenCorrectPlantUml()
            throws MappingToUmlTranslationFailedException, NoMappingException, NoTripletException,
                    UnrelatedMappingException, ConceptAlreadyExistsException,
                    RelationAlreadyExistsException {
        // given
        CustomConcept subjectConcept = new CustomConcept("LogicClass", "");
        String subjectMappingString =
                "isLogicClass: (?class rdf:type famix:FamixClass)"
                        + " (?class famix:hasFullQualifiedName ?name)"
                        + " regex(?name, '\\\\w*Logic')"
                        + " -> (?class rdf:type architecture:LogicClass)";
        ConceptMapping subjectMapping =
                createConceptMapping(subjectMappingString, Collections.emptyList(), subjectConcept);
        subjectConcept.setMapping(subjectMapping);
        conceptManager.addConcept(subjectConcept);

        CustomConcept objectConcept = new CustomConcept("DBType", "");
        String objectMappingString =
                "isDBType: (?class rdf:type famix:FamixClass)"
                        + " (?class famix:hasFullQualifiedName ?className)"
                        + " regex(?className, '(\\\\w)+DB')"
                        + " (?package rdf:type famix:Namespace)"
                        + " (?package famix:hasName ?name)"
                        + " regex(?name, 'teammates\\\\.storage\\\\.api(\\\\w|\\\\W)*')"
                        + " (?package famix:namespaceContains ?class)"
                        + " -> (?class rdf:type architecture:DBType)";
        ConceptMapping objectMapping =
                createConceptMapping(objectMappingString, Collections.emptyList(), objectConcept);
        objectConcept.setMapping(objectMapping);
        conceptManager.addConcept(objectConcept);

        String predicateMappingString =
                "useMapping: (?class rdf:type architecture:LogicClass)"
                        + " (?class2 rdf:type architecture:DBType)"
                        + " (?class famix:imports ?class2)"
                        + " -> (?class architecture:use ?class2)";
        RelationMapping predicateMapping =
                createRelationMapping(predicateMappingString, Collections.emptyList());
        CustomRelation predicateRelation =
                new CustomRelation("use", "", new HashSet<>(), new HashSet<>());
        predicateRelation.setMapping(predicateMapping, conceptManager);
        relationManager.addRelation(predicateRelation);

        String secondPredicateMappingString =
                "manageMapping: (?class rdf:type architecture:LogicClass)"
                        + " (?class2 rdf:type architecture:DBType)"
                        + " (?class famix:definesAttribute ?att)"
                        + " (?att famix:hasDeclaredType ?class2)"
                        + " -> (?class architecture:manage ?class2)";
        RelationMapping secondPredicateMapping =
                createRelationMapping(secondPredicateMappingString, Collections.emptyList());
        CustomRelation secondPredicateRelation =
                new CustomRelation("manage", "", new HashSet<>(), new HashSet<>());
        secondPredicateRelation.setMapping(secondPredicateMapping, conceptManager);
        relationManager.addRelation(secondPredicateRelation);

        var rule =
                new ArchitectureRule(
                        "If a LogicClass use a DBType, then it must manage this DBType.");

        // when
        PlantUmlTransformer transformer = new PlantUmlTransformer(conceptManager, relationManager);
        String plantUmlCode = transformer.transformToPlantUml(rule);

        // then
        String expectedCode =
                "@startuml\n"
                        + "title If a LogicClass use a DBType, then it must manage this DBType.\n"
                        + "class \"\\\\w*Logic\" as logicClassW #OrangeRed {\n"
                        + "}\n"
                        + "folder \"teammates\\\\.storage\\\\.api(\\\\w|\\\\W)*\" as package {\n"
                        + "class \"(\\\\w)+DB\" as dBType {\n"
                        + "}\n"
                        + "}\n"
                        + "class \"\\\\w*Logic\" as logicClassC #RoyalBlue {\n"
                        + "{field} ?att : (\\\\w)+DB\n"
                        + "}\n"
                        + "logicClassW -[dashed]-> dBType: <<imports>>\n"
                        + "logicClassW -[bold]-> dBType\n"
                        + "note on link: use\n"
                        + "logicClassC -[dashed]-> dBType: <<imports>>\n"
                        + "logicClassC -[bold]-> dBType\n"
                        + "note on link: use\n"
                        + "logicClassC -[bold]-> dBType\n"
                        + "note on link: manage\n"
                        + "note \"LogicClass\" as LogicClass1\n"
                        + "LogicClass1 .. logicClassW\n"
                        + "note \"DBType\" as DBType\n"
                        + "DBType .. dBType\n"
                        + "note \"LogicClass\" as LogicClass\n"
                        + "LogicClass .. logicClassC\n"
                        + "@enduml";
        Assertions.assertEquals(expectedCode, plantUmlCode);
    }

    @Test
    void givenIfRuleWithDifferentObjects_whenTransform_thenThrowException()
            throws MappingToUmlTranslationFailedException, NoMappingException, NoTripletException,
                    UnrelatedMappingException, ConceptAlreadyExistsException,
                    RelationAlreadyExistsException {
        // given

        var rule =
                new ArchitectureRule("If a LogicClass use a DBType, then it must manage this ABC.");

        // when
        PlantUmlTransformer transformer = new PlantUmlTransformer(conceptManager, relationManager);

        // then
        Assertions.assertThrows(
                MappingToUmlTranslationFailedException.class,
                () -> transformer.transformToPlantUml(rule));
    }

    @Test
    void givenAtMostRule_whenTransform_thenCorrectPlantUml()
            throws MappingToUmlTranslationFailedException, NoMappingException, NoTripletException,
                    UnrelatedMappingException, ConceptAlreadyExistsException,
                    RelationAlreadyExistsException {
        // given
        String predicateMappingString =
                "haveMapping: (?class rdf:type famix:FamixClass)"
                        + " (?method rdf:type famix:Method)"
                        + " (?class famix:definesMethod ?method)"
                        + " -> (?class architecture:have ?method)";
        RelationMapping predicateMapping =
                createRelationMapping(predicateMappingString, Collections.emptyList());
        CustomRelation predicateRelation =
                new CustomRelation("have", "", new HashSet<>(), new HashSet<>());
        predicateRelation.setMapping(predicateMapping, conceptManager);
        relationManager.addRelation(predicateRelation);

        var rule = new ArchitectureRule("Every FamixClass can imports at-most 10 FamixClass.");

        // when
        PlantUmlTransformer transformer = new PlantUmlTransformer(conceptManager, relationManager);
        String plantUmlCode = transformer.transformToPlantUml(rule);

        // then
        String expectedCode =
                "@startuml\n"
                        + "title Every FamixClass can imports at-most 10 FamixClass.\n"
                        + "class \"?famixClass1\" as famixClass1 {\n"
                        + "}\n"
                        + "class \"?famixClassC\" as famixClassC #RoyalBlue {\n"
                        + "}\n"
                        + "class \"?famixClassW\" as famixClassW #OrangeRed {\n"
                        + "}\n"
                        + "famixClassC -[dashed]-> \"..10\" famixClass1: <<imports>>\n"
                        + "famixClassW -[dashed]-> \"11..\" famixClass1: <<imports>>\n"
                        + "@enduml";
        Assertions.assertEquals(expectedCode, plantUmlCode);
    }

    @Test
    void givenFactRelation_whenTransform_thenCorrectPlantUml()
            throws MappingToUmlTranslationFailedException, NoMappingException, NoTripletException,
                    UnrelatedMappingException, ConceptAlreadyExistsException,
                    RelationAlreadyExistsException {
        // given
        CustomConcept subjectConcept = new CustomConcept("CWAApp", "");
        String subjectMappingString =
                "isCWAApp: (?class rdf:type famix:FamixClass)"
                        + " (?cwaapppackage rdf:type famix:Namespace)"
                        + " (?cwaapppackage famix:hasName ?cwaapppackagename)"
                        + " regex(?cwaapppackagename, 'de\\\\.rki\\\\.coronawarnapp\\\\.?(\\\\w||\\\\W)*')"
                        + " (?cwaapppackage famix:namespaceContains ?class)"
                        + " -> (?class rdf:type architecture:CWAApp)";
        ConceptMapping subjectMapping =
                createConceptMapping(subjectMappingString, Collections.emptyList(), subjectConcept);
        subjectConcept.setMapping(subjectMapping);
        conceptManager.addConcept(subjectConcept);

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

        CustomConcept objectConcept = new CustomConcept("ExposureNotificationFramework", "");
        String objectMappingString =
                "isExposureNotificationFramework: (?class rdf:type famix:FamixClass)"
                        + " (?enfpackage rdf:type famix:Namespace)"
                        + " (?enfpackage famix:hasName ?enfpackagename)"
                        + " regex(?enfpackagename, 'com\\\\.google\\\\.android\\\\.gms\\\\.nearby\\\\.exposurenotification\\\\.?(\\\\w||\\\\W)*')"
                        + " (?enfpackage famix:namespaceContains ?class)"
                        + " -> (?class rdf:type architecture:ExposureNotificationFramework)";
        ConceptMapping objectMapping =
                createConceptMapping(objectMappingString, Collections.emptyList(), objectConcept);
        objectConcept.setMapping(objectMapping);
        conceptManager.addConcept(objectConcept);

        var rule = new ArchitectureRule("Fact: CWAApp use ExposureNotificationFramework.");

        // when
        PlantUmlTransformer transformer = new PlantUmlTransformer(conceptManager, relationManager);
        String plantUmlCode = transformer.transformToPlantUml(rule);

        // then
        String expectedCode =
                "@startuml\n"
                        + "title Fact: CWAApp use ExposureNotificationFramework.\n"
                        + "folder \"com\\\\.google\\\\.android\\\\.gms\\\\.nearby\\\\.exposurenotification\\\\.?(\\\\w||\\\\W)*\" as enfpackage {\n"
                        + "class \"?exposureNotificationFramework\" as exposureNotificationFramework {\n"
                        + "}\n"
                        + "}\n"
                        + "folder \"de\\\\.rki\\\\.coronawarnapp\\\\.?(\\\\w||\\\\W)*\" as cwaapppackage {\n"
                        + "class \"?cWAApp\" as cWAApp {\n"
                        + "}\n"
                        + "}\n"
                        + "cWAApp -[dashed]-> exposureNotificationFramework: <<imports>>\n"
                        + "cWAApp -[bold]-> exposureNotificationFramework\n"
                        + "note on link: use\n"
                        + "note \"ExposureNotificationFramework\" as ExposureNotificationFramework\n"
                        + "ExposureNotificationFramework .. exposureNotificationFramework\n"
                        + "note \"CWAApp\" as CWAApp\n"
                        + "CWAApp .. cWAApp\n"
                        + "@enduml";
        Assertions.assertEquals(expectedCode, plantUmlCode);
    }

    @Test
    void givenRuleWithThatVariable_whenTransform_thenCorrectPlantUml()
            throws MappingToUmlTranslationFailedException, NoMappingException, NoTripletException,
                    UnrelatedMappingException, ConceptAlreadyExistsException,
                    RelationAlreadyExistsException {
        // given
        CustomConcept subjectConcept = new CustomConcept("SpyClass", "");
        String subjectMappingString =
                "isCWAApp: (?class rdf:type famix:FamixClass)"
                        + " (?class famix:hasName ?name)"
                        + " regex(?name, '.*Spy')"
                        + " -> (?class rdf:type architecture:SpyClass)";
        ConceptMapping subjectMapping =
                createConceptMapping(subjectMappingString, Collections.emptyList(), subjectConcept);
        subjectConcept.setMapping(subjectMapping);
        conceptManager.addConcept(subjectConcept);

        String thatMappingString =
                "isLocatedInMapping: (?class rdf:type famix:FamixClass)"
                        + " (?package rdf:type famix:Namespace)"
                        + " (?package famix:namespaceContains ?class)"
                        + " -> (?class architecture:isLocatedIn ?package)";
        RelationMapping thatMapping =
                createRelationMapping(thatMappingString, Collections.emptyList());
        CustomRelation thatRelation =
                new CustomRelation("isLocatedIn", "", new HashSet<>(), new HashSet<>());
        thatRelation.setMapping(thatMapping, conceptManager);
        relationManager.addRelation(thatRelation);

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

        CustomConcept objectConcept = new CustomConcept("SecretClass", "");
        String objectMappingString =
                "isSecretClass: (?class rdf:type famix:FamixClass)"
                        + " (?class famix:definesMethod ?constructor)"
                        + " (?constructor famix:isConstructor 'true'^^xsd:boolean)"
                        + " (?constructor famix:hasModifier 'private')"
                        + " -> (?class rdf:type architecture:SecretClass)";
        ConceptMapping objectMapping =
                createConceptMapping(objectMappingString, Collections.emptyList(), objectConcept);
        objectConcept.setMapping(objectMapping);
        conceptManager.addConcept(objectConcept);

        var rule =
                new ArchitectureRule(
                        "Only a SpyClass that (isLocatedIn Namespace A) can use a SecretClass that (isLocatedIn Namespace A).");

        // when
        PlantUmlTransformer transformer = new PlantUmlTransformer(conceptManager, relationManager);
        String plantUmlCode = transformer.transformToPlantUml(rule);

        // then
        String expectedCode = "";
        Assertions.assertEquals(expectedCode, plantUmlCode);
    }

    @Test
    void givenNothingAnythingRule_whenTransform_thenCorrectPlantUml()
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

        var rule = new ArchitectureRule("Nothing can use anything.");

        // when
        PlantUmlTransformer transformer = new PlantUmlTransformer(conceptManager, relationManager);
        String plantUmlCode = transformer.transformToPlantUml(rule);

        // then
        String expectedCode = "";
        Assertions.assertEquals(expectedCode, plantUmlCode);
    }

    @Test
    void givenOrRule_whenTransform_thenCorrectPlantUml()
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
                        + " (?class famix:hasName ?name)"
                        + " regex(?name, '.*Controller')"
                        + " -> (?class rdf:type architecture:Controller)";
        ConceptMapping objectMapping =
                createConceptMapping(objectMappingString, Collections.emptyList(), objectConcept);
        objectConcept.setMapping(objectMapping);
        conceptManager.addConcept(objectConcept);

        CustomConcept object2Concept = new CustomConcept("MainClass", "");
        String object2MappingString =
                "isMainClass: (?class rdf:type famix:FamixClass)"
                        + " (?class famix:definesMethod ?method)"
                        + " (?method famix:hasName 'main')"
                        + " -> (?class rdf:type architecture:MainClass)";
        ConceptMapping object2Mapping =
                createConceptMapping(object2MappingString, Collections.emptyList(), object2Concept);
        object2Concept.setMapping(object2Mapping);
        conceptManager.addConcept(object2Concept);

        var rule = new ArchitectureRule("Nothing can use a Controller or a MainClass.");

        // when
        PlantUmlTransformer transformer = new PlantUmlTransformer(conceptManager, relationManager);
        String plantUmlCode = transformer.transformToPlantUml(rule);

        // then
        String expectedCode = "";
        Assertions.assertEquals(expectedCode, plantUmlCode);
    }

    @Test
    void givenAndRule_whenTransform_thenCorrectPlantUml()
            throws MappingToUmlTranslationFailedException, NoMappingException, NoTripletException,
                    UnrelatedMappingException, ConceptAlreadyExistsException,
                    RelationAlreadyExistsException {
        // given
        CustomConcept subjectConcept = new CustomConcept("DataClass", "");
        String subjectMappingString =
                "isCWAApp: (?class rdf:type famix:FamixClass)"
                        + " (?namespace famix:namespaceContains ?class)"
                        + " (?namespace famix:hasName ?name)"
                        + " regex(?name, '.*/data/.*')"
                        + " -> (?class rdf:type architecture:DataClass)";
        ConceptMapping subjectMapping =
                createConceptMapping(subjectMappingString, Collections.emptyList(), subjectConcept);
        subjectConcept.setMapping(subjectMapping);
        conceptManager.addConcept(subjectConcept);

        String predicateMappingString =
                "overwriteMapping: (?class rdf:type famix:FamixClass)"
                        + " (?method rdf:type famix:Method)"
                        + " (?method famix:hasAnnotationInstance ?instance)"
                        + " (?instance famix:hasAnnotationType ?type)"
                        + " (?type famix:hasName 'overwrite')"
                        + " -> (?class architecture:overwrite ?method)";
        RelationMapping predicateMapping =
                createRelationMapping(predicateMappingString, Collections.emptyList());
        CustomRelation predicateRelation =
                new CustomRelation("overwrite", "", new HashSet<>(), new HashSet<>());
        predicateRelation.setMapping(predicateMapping, conceptManager);
        relationManager.addRelation(predicateRelation);

        CustomConcept objectConcept = new CustomConcept("HashCodeMethod", "");
        String objectMappingString =
                "isHashCodeMethod: (?method rdf:type famix:Method)"
                        + " (?method famix:hasName 'hashCode')"
                        + " -> (?method rdf:type architecture:HashCodeMethod)";
        ConceptMapping objectMapping =
                createConceptMapping(objectMappingString, Collections.emptyList(), objectConcept);
        objectConcept.setMapping(objectMapping);
        conceptManager.addConcept(objectConcept);

        CustomConcept object2Concept = new CustomConcept("EqualsMethod", "");
        String object2MappingString =
                "isEqualsMethod: (?method rdf:type famix:Method)"
                        + " (?method famix:hasName 'equals')"
                        + " -> (?method rdf:type architecture:EqualsMethod)";
        ConceptMapping object2Mapping =
                createConceptMapping(object2MappingString, Collections.emptyList(), object2Concept);
        object2Concept.setMapping(object2Mapping);
        conceptManager.addConcept(object2Concept);

        var rule =
                new ArchitectureRule(
                        "Every DataClass must overwrite HashCodeMethod and overwrite EqualsMethod.");

        // when
        PlantUmlTransformer transformer = new PlantUmlTransformer(conceptManager, relationManager);
        String plantUmlCode = transformer.transformToPlantUml(rule);

        // then
        String expectedCode = "";
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
