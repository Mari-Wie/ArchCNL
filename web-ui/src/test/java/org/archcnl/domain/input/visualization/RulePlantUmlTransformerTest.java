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
                "@startuml Every Aggregate must resideIn a DomainRing.\n"
                        + "title Every Aggregate must resideIn a DomainRing.\n"
                        + "folder \"domain\" as domainRingC {\n"
                        + "class \"(\\\\w||\\\\W)*Aggregate\" as aggregateC #RoyalBlue {\n"
                        + "}\n"
                        + "}\n"
                        + "class \"(\\\\w||\\\\W)*Aggregate\" as aggregateW #OrangeRed {\n"
                        + "}\n"
                        + "\n"
                        + "aggregateC -[bold]-> domainRingC\n"
                        + "note on link: resideIn\n"
                        + "note \"DomainRing\" as DomainRing\n"
                        + "DomainRing .. domainRingC\n"
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
                "@startuml Only a ClientScript can use a ClientScript.\n"
                        + "title Only a ClientScript can use a ClientScript.\n"
                        + "folder \"teammates\\\\.client(\\\\w|\\\\W)*\" as package2 {\n"
                        + "class \"?clientScriptW\" as clientScriptW {\n"
                        + "}\n"
                        + "}\n"
                        + "folder \"teammates\\\\.client(\\\\w|\\\\W)*\" as package {\n"
                        + "class \"?clientScript1C\" as clientScript1C {\n"
                        + "}\n"
                        + "}\n"
                        + "folder \"teammates\\\\.client(\\\\w|\\\\W)*\" as package1 {\n"
                        + "class \"?clientScriptC\" as clientScriptC {\n"
                        + "}\n"
                        + "}\n"
                        + "class \"?famixClassW\" as famixClassW {\n"
                        + "}\n"
                        + "clientScript1C -[dashed]-> clientScriptC #line:RoyalBlue;text:RoyalBlue : <<imports>>\n"
                        + "clientScript1C -[bold]-> clientScriptC #line:RoyalBlue;text:RoyalBlue \n"
                        + "note on link: use\n"
                        + "famixClassW -[dashed]-> clientScriptW #line:OrangeRed;text:OrangeRed : <<imports>>\n"
                        + "famixClassW -[bold]-> clientScriptW #line:OrangeRed;text:OrangeRed \n"
                        + "note on link: use\n"
                        + "note \"ClientScript\" as ClientScript2\n"
                        + "ClientScript2 .. clientScriptW\n"
                        + "note \"ClientScript\" as ClientScript\n"
                        + "ClientScript .. clientScript1C\n"
                        + "note \"ClientScript\" as ClientScript1\n"
                        + "ClientScript1 .. clientScriptC\n"
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
                "@startuml Every TestResult can-only bestored TestResultServer.\n"
                        + "title Every TestResult can-only bestored TestResultServer.\n"
                        + "class \"(\\\\w||\\\\W)*TestResultEntity\" as testResultW {\n"
                        + "}\n"
                        + "folder \"app\\\\.coronawarn\\\\.testresult\\\\.?(\\\\w||\\\\W)*\" as testresultpackage {\n"
                        + "class \"?testResultServerC\" as testResultServerC {\n"
                        + "}\n"
                        + "}\n"
                        + "class \"(\\\\w||\\\\W)*TestResultEntity\" as testResultC {\n"
                        + "}\n"
                        + "class \"?famixClassW\" as famixClassW {\n"
                        + "}\n"
                        + "testResultServerC -[dashed]-> testResultC #line:RoyalBlue;text:RoyalBlue : <<imports>>\n"
                        + "testResultC -[bold]-> testResultServerC #line:RoyalBlue;text:RoyalBlue \n"
                        + "note on link: bestored\n"
                        + "famixClassW -[dashed]-> testResultW #line:OrangeRed;text:OrangeRed : <<imports>>\n"
                        + "testResultW -[bold]-> famixClassW #line:OrangeRed;text:OrangeRed \n"
                        + "note on link: bestored\n"
                        + "note \"TestResult\" as TestResult1\n"
                        + "TestResult1 .. testResultW\n"
                        + "note \"TestResultServer\" as TestResultServer\n"
                        + "TestResultServer .. testResultServerC\n"
                        + "note \"TestResult\" as TestResult\n"
                        + "TestResult .. testResultC\n"
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
                "@startuml No Testresultserver can store GUID.\n"
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
                "@startuml Nothing can use a Controller.\n"
                        + "title Nothing can use a Controller.\n"
                        + "class \"?controller\" as controller <<Controller>> {\n"
                        + "}\n"
                        + "annotation \"Controller\" as annoType {\n"
                        + "}\n"
                        + "class \"?nothing\" as nothing {\n"
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
                "@startuml If a LogicClass use a DBType, then it must manage this DBType.\n"
                        + "title If a LogicClass use a DBType, then it must manage this DBType.\n"
                        + "class \"\\\\w*Logic\" as logicClassW #OrangeRed {\n"
                        + "}\n"
                        + "folder \"teammates\\\\.storage\\\\.api(\\\\w|\\\\W)*\" as package1 {\n"
                        + "class \"(\\\\w)+DB\" as dBTypeW {\n"
                        + "}\n"
                        + "}\n"
                        + "class \"\\\\w*Logic\" as logicClassC #RoyalBlue {\n"
                        + "{field} ?att : (\\\\w)+DB\n"
                        + "}\n"
                        + "folder \"teammates\\\\.storage\\\\.api(\\\\w|\\\\W)*\" as package {\n"
                        + "class \"(\\\\w)+DB\" as dBTypeC {\n"
                        + "}\n"
                        + "}\n"
                        + "logicClassC -[dashed]-> dBTypeC: <<imports>>\n"
                        + "logicClassC -[bold]-> dBTypeC\n"
                        + "note on link: use\n"
                        + "logicClassC -[bold]-> dBTypeC\n"
                        + "note on link: manage\n"
                        + "logicClassW -[dashed]-> dBTypeW: <<imports>>\n"
                        + "logicClassW -[bold]-> dBTypeW\n"
                        + "note on link: use\n"
                        + "note \"LogicClass\" as LogicClass1\n"
                        + "LogicClass1 .. logicClassW\n"
                        + "note \"DBType\" as DBType1\n"
                        + "DBType1 .. dBTypeW\n"
                        + "note \"LogicClass\" as LogicClass\n"
                        + "LogicClass .. logicClassC\n"
                        + "note \"DBType\" as DBType\n"
                        + "DBType .. dBTypeC\n"
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
        var rule = new ArchitectureRule("Every FamixClass can imports at-most 10 FamixClass.");

        // when
        PlantUmlTransformer transformer = new PlantUmlTransformer(conceptManager, relationManager);
        String plantUmlCode = transformer.transformToPlantUml(rule);

        // then
        String expectedCode =
                "@startuml Every FamixClass can imports at-most 10 FamixClass.\n"
                        + "title Every FamixClass can imports at-most 10 FamixClass.\n"
                        + "class \"?famixClass1W\" as famixClass1W {\n"
                        + "}\n"
                        + "class \"?famixClass1C\" as famixClass1C {\n"
                        + "}\n"
                        + "class \"?famixClassC\" as famixClassC {\n"
                        + "}\n"
                        + "class \"?famixClassW\" as famixClassW {\n"
                        + "}\n"
                        + "famixClass1C -[dashed]-> \"0..10\" famixClassC #line:RoyalBlue;text:RoyalBlue : <<imports>>\n"
                        + "famixClass1W -[dashed]-> \"11..*\" famixClassW #line:OrangeRed;text:OrangeRed : <<imports>>\n"
                        + "@enduml";
        Assertions.assertEquals(expectedCode, plantUmlCode);
    }

    @Test
    void givenAtLeastRule_whenTransform_thenCorrectPlantUml()
            throws MappingToUmlTranslationFailedException, NoMappingException, NoTripletException,
                    UnrelatedMappingException, ConceptAlreadyExistsException,
                    RelationAlreadyExistsException {
        // given
        CustomConcept subjectConcept = new CustomConcept("Controller", "");
        String subjectMappingString =
                "isController: (?class rdf:type famix:FamixClass)"
                        + " (?class famix:hasName ?name)"
                        + " regex(?name, '.*Controller')"
                        + " -> (?class rdf:type architecture:Controller)";
        ConceptMapping subjectMapping =
                createConceptMapping(subjectMappingString, Collections.emptyList(), subjectConcept);
        subjectConcept.setMapping(subjectMapping);
        conceptManager.addConcept(subjectConcept);

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

        var rule = new ArchitectureRule("Every Controller can have at-least 2 Method.");

        // when
        PlantUmlTransformer transformer = new PlantUmlTransformer(conceptManager, relationManager);
        String plantUmlCode = transformer.transformToPlantUml(rule);

        // then
        String expectedCode =
                "@startuml Every Controller can have at-least 2 Method.\n"
                        + "title Every Controller can have at-least 2 Method.\n"
                        + "class \".*Controller\" as controllerW {\n"
                        + "<color:#OrangeRed> {method} ?methodW()\n"
                        + "}\n"
                        + "class \".*Controller\" as controllerC {\n"
                        + "<color:#RoyalBlue> {method} ?methodC()\n"
                        + "}\n"
                        + "controllerC -[bold]-> \"2..*\" controllerC::methodC #line:RoyalBlue;text:RoyalBlue \n"
                        + "note on link: have\n"
                        + "controllerW -[bold]-> \"0..1\" controllerW::methodW #line:OrangeRed;text:OrangeRed \n"
                        + "note on link: have\n"
                        + "note \"Controller\" as Controller1\n"
                        + "Controller1 .. controllerW\n"
                        + "note \"Controller\" as Controller\n"
                        + "Controller .. controllerC\n"
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
                "@startuml Fact: CWAApp use ExposureNotificationFramework.\n"
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
    void givenIsAFactRelation_whenTransform_thenCorrectPlantUml()
            throws MappingToUmlTranslationFailedException, NoMappingException, NoTripletException,
                    UnrelatedMappingException, ConceptAlreadyExistsException,
                    RelationAlreadyExistsException {
        // given
        CustomConcept subjectConcept = new CustomConcept("Exception", "");
        String subjectMappingString =
                "isException: (?class rdf:type famix:FamixClass)"
                        + " (?class famix:hasName ?name)"
                        + " regex(?name, '.*Exception')"
                        + " -> (?class rdf:type architecture:Exception)";
        ConceptMapping subjectMapping =
                createConceptMapping(subjectMappingString, Collections.emptyList(), subjectConcept);
        subjectConcept.setMapping(subjectMapping);
        conceptManager.addConcept(subjectConcept);

        CustomConcept objectConcept = new CustomConcept("Error", "");
        String objectMappingString =
                "isError: (?error rdf:type famix:FamixClass)"
                        + " -> (?error rdf:type architecture:Error)";
        ConceptMapping objectMapping =
                createConceptMapping(objectMappingString, Collections.emptyList(), objectConcept);
        objectConcept.setMapping(objectMapping);
        conceptManager.addConcept(objectConcept);

        var rule = new ArchitectureRule("Fact: Exception is an Error.");

        // when
        PlantUmlTransformer transformer = new PlantUmlTransformer(conceptManager, relationManager);
        String plantUmlCode = transformer.transformToPlantUml(rule);

        // then
        String expectedCode =
                "@startuml Fact: Exception is an Error.\n"
                        + "title Fact: Exception is an Error.\n"
                        + "class \".*Exception\" as exception {\n"
                        + "}\n"
                        + "class \"?error\" as error {\n"
                        + "}\n"
                        + "exception --|> error: Is-a\n"
                        + "note \"Exception\" as Exception\n"
                        + "Exception .. exception\n"
                        + "note \"Error\" as Error\n"
                        + "Error .. error\n"
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
        String expectedCode =
                "@startuml Only a SpyClass that (isLocatedIn Namespace A) can use a SecretClass that (isLocatedIn Namespace A).\n"
                        + "title Only a SpyClass that (isLocatedIn Namespace A) can use a SecretClass that (isLocatedIn Namespace A).\n"
                        + "\n"
                        + "folder \"? AC\" as  AC {\n"
                        + "class \".*Spy\" as spyClassC {\n"
                        + "}\n"
                        + "class \"?secretClassC\" as secretClassC {\n"
                        + "{method} -<<Create>> ?constructor()\n"
                        + "}\n"
                        + "}\n"
                        + "folder \"? AW\" as  AW {\n"
                        + "class \"?secretClassW\" as secretClassW {\n"
                        + "{method} -<<Create>> ?constructor1()\n"
                        + "}\n"
                        + "}\n"
                        + "\n"
                        + "class \"?famixClassW\" as famixClassW {\n"
                        + "}\n"
                        + "\n"
                        + "spyClassC -[bold]->  AC\n"
                        + "note on link: isLocatedIn\n"
                        + "secretClassC -[bold]->  AC\n"
                        + "note on link: isLocatedIn\n"
                        + "spyClassC -[dashed]-> secretClassC #line:RoyalBlue;text:RoyalBlue : <<imports>>\n"
                        + "spyClassC -[bold]-> secretClassC #line:RoyalBlue;text:RoyalBlue \n"
                        + "note on link: use\n"
                        + "secretClassW -[bold]->  AW\n"
                        + "note on link: isLocatedIn\n"
                        + "famixClassW -[dashed]-> secretClassW #line:OrangeRed;text:OrangeRed : <<imports>>\n"
                        + "famixClassW -[bold]-> secretClassW #line:OrangeRed;text:OrangeRed \n"
                        + "note on link: use\n"
                        + "note \"SpyClass\" as SpyClass\n"
                        + "SpyClass .. spyClassC\n"
                        + "note \"SecretClass\" as SecretClass1\n"
                        + "SecretClass1 .. secretClassW\n"
                        + "note \"SecretClass\" as SecretClass\n"
                        + "SecretClass .. secretClassC\n"
                        + "@enduml";
        Assertions.assertEquals(expectedCode, plantUmlCode);
    }

    @Test
    void givenRuleWithAndInThat_whenTransform_thenCorrectPlantUml()
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
                        "Only a FamixClass that (definesMethod Method and imports an Enum) can use FamixClass.");

        // when
        PlantUmlTransformer transformer = new PlantUmlTransformer(conceptManager, relationManager);
        String plantUmlCode = transformer.transformToPlantUml(rule);

        // then
        String expectedCode =
                "@startuml Only a FamixClass that (definesMethod Method and imports an Enum) can use FamixClass.\n"
                        + "title Only a FamixClass that (definesMethod Method and imports an Enum) can use FamixClass.\n"
                        + "enum \"?enumC\" as enumC {\n"
                        + "}\n"
                        + "class \"?famixClass1C\" as famixClass1C {\n"
                        + "{method} ?methodC()\n"
                        + "}\n"
                        + "class \"?famixClass2W\" as famixClass2W {\n"
                        + "}\n"
                        + "class \"?famixClassC\" as famixClassC {\n"
                        + "}\n"
                        + "class \"?famixClassW\" as famixClassW {\n"
                        + "}\n"
                        + "famixClass1C -[dashed]-> enumC: <<imports>>\n"
                        + "famixClass1C -[dashed]-> famixClassC #line:RoyalBlue;text:RoyalBlue : <<imports>>\n"
                        + "famixClass1C -[bold]-> famixClassC #line:RoyalBlue;text:RoyalBlue \n"
                        + "note on link: use\n"
                        + "famixClass2W -[dashed]-> famixClassW #line:OrangeRed;text:OrangeRed : <<imports>>\n"
                        + "famixClass2W -[bold]-> famixClassW #line:OrangeRed;text:OrangeRed \n"
                        + "note on link: use\n"
                        + "@enduml";
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
        String expectedCode =
                "@startuml Nothing can use anything.\n"
                        + "title Nothing can use anything.\n"
                        + "class \"?nothing\" as nothing {\n"
                        + "}\n"
                        + "class \"?anything\" as anything {\n"
                        + "}\n"
                        + "nothing -[dashed]-> anything #line:OrangeRed;text:OrangeRed : <<imports>>\n"
                        + "nothing -[bold]-> anything #line:OrangeRed;text:OrangeRed \n"
                        + "note on link: use\n"
                        + "@enduml";
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

        var rule = new ArchitectureRule("Nothing can use a Controller or use a MainClass.");

        // when
        PlantUmlTransformer transformer = new PlantUmlTransformer(conceptManager, relationManager);
        String plantUmlCode = transformer.transformToPlantUml(rule);

        // then
        String expectedCode =
                "@startuml Nothing can use a Controller or use a MainClass.\n"
                        + "title Nothing can use a Controller or use a MainClass.\n"
                        + "class \"?nothing1\" as nothing1 {\n"
                        + "}\n"
                        + "class \"?mainClass1\" as mainClass1 {\n"
                        + "{method} main()\n"
                        + "}\n"
                        + "class \".*Controller\" as controller {\n"
                        + "}\n"
                        + "class \"?nothing\" as nothing {\n"
                        + "}\n"
                        + "nothing -[dashed]-> controller #line:OrangeRed;text:OrangeRed : <<imports>>\n"
                        + "nothing -[bold]-> controller #line:OrangeRed;text:OrangeRed \n"
                        + "note on link: use\n"
                        + "nothing1 -[dashed]-> mainClass1 #line:OrangeRed;text:OrangeRed : <<imports>>\n"
                        + "nothing1 -[bold]-> mainClass1 #line:OrangeRed;text:OrangeRed \n"
                        + "note on link: use\n"
                        + "note \"MainClass\" as MainClass\n"
                        + "MainClass .. mainClass1\n"
                        + "note \"Controller\" as Controller\n"
                        + "Controller .. controller\n"
                        + "@enduml";
        Assertions.assertEquals(expectedCode, plantUmlCode);
    }

    @Test
    void givenDoubleOrRule_whenTransform_thenCorrectPlantUml()
            throws MappingToUmlTranslationFailedException, NoMappingException, NoTripletException,
                    UnrelatedMappingException, ConceptAlreadyExistsException,
                    RelationAlreadyExistsException {
        // given
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

        String predicateMappingString =
                "haveMethodMapping: (?class rdf:type famix:FamixClass)"
                        + " (?class famix:definesMethod ?content)"
                        + " -> (?class architecture:haveMethod ?content)";
        RelationMapping predicateMapping =
                createRelationMapping(predicateMappingString, Collections.emptyList());
        CustomRelation predicateRelation =
                new CustomRelation("haveMethod", "", new HashSet<>(), new HashSet<>());
        predicateRelation.setMapping(predicateMapping, conceptManager);
        relationManager.addRelation(predicateRelation);

        String predicate2MappingString =
                "haveFieldMapping: (?class rdf:type famix:FamixClass)"
                        + " (?class famix:definesAttribute ?content)"
                        + " -> (?class architecture:haveField ?content)";
        RelationMapping predicate2Mapping =
                createRelationMapping(predicate2MappingString, Collections.emptyList());
        CustomRelation predicate2Relation =
                new CustomRelation("haveField", "", new HashSet<>(), new HashSet<>());
        predicate2Relation.setMapping(predicate2Mapping, conceptManager);
        relationManager.addRelation(predicate2Relation);

        var rule =
                new ArchitectureRule(
                        "Every Controller can haveMethod exactly 5 Method or haveField at-least 2 Attribute or imports at-most 8 FamixClass.");

        // when
        PlantUmlTransformer transformer = new PlantUmlTransformer(conceptManager, relationManager);
        String plantUmlCode = transformer.transformToPlantUml(rule);

        // then
        String expectedCode =
                "@startuml Every Controller can haveMethod exactly 5 Method or haveField at-least 2 Attribute or imports at-most 8 FamixClass.\n"
                        + "title Every Controller can haveMethod exactly 5 Method or haveField at-least 2 Attribute or imports at-most 8 FamixClass.\n"
                        + "class \".*Controller\" as controllerWWW {\n"
                        + "}\n"
                        + "class \".*Controller\" as controllerW {\n"
                        + "<color:#OrangeRed> {method} ?methodW()\n"
                        + "}\n"
                        + "class \".*Controller\" as controllerC {\n"
                        + "<color:#RoyalBlue> {method} ?methodC()\n"
                        + "}\n"
                        + "class \".*Controller\" as controllerCC {\n"
                        + "<color:#RoyalBlue> {field} ?attributeCC\n"
                        + "}\n"
                        + "class \".*Controller\" as controllerWW {\n"
                        + "<color:#OrangeRed> {field} ?attributeWW\n"
                        + "}\n"
                        + "class \"?famixClassWWW\" as famixClassWWW {\n"
                        + "}\n"
                        + "class \".*Controller\" as controllerCCC {\n"
                        + "}\n"
                        + "class \"?famixClassCCC\" as famixClassCCC {\n"
                        + "}\n"
                        + "controllerC -[bold]-> \"5\" controllerC::methodC #line:RoyalBlue;text:RoyalBlue \n"
                        + "note on link: haveMethod\n"
                        + "controllerW -[bold]-> \"<>5\" controllerW::methodW #line:OrangeRed;text:OrangeRed \n"
                        + "note on link: haveMethod\n"
                        + "controllerCC -[bold]-> \"2..*\" controllerCC::attributeCC #line:RoyalBlue;text:RoyalBlue \n"
                        + "note on link: haveField\n"
                        + "controllerWW -[bold]-> \"0..1\" controllerWW::attributeWW #line:OrangeRed;text:OrangeRed \n"
                        + "note on link: haveField\n"
                        + "controllerCCC -[dashed]-> \"0..8\" famixClassCCC #line:RoyalBlue;text:RoyalBlue : <<imports>>\n"
                        + "controllerWWW -[dashed]-> \"9..*\" famixClassWWW #line:OrangeRed;text:OrangeRed : <<imports>>\n"
                        + "note \"Controller\" as Controller5\n"
                        + "Controller5 .. controllerWWW\n"
                        + "note \"Controller\" as Controller1\n"
                        + "Controller1 .. controllerW\n"
                        + "note \"Controller\" as Controller\n"
                        + "Controller .. controllerC\n"
                        + "note \"Controller\" as Controller2\n"
                        + "Controller2 .. controllerCC\n"
                        + "note \"Controller\" as Controller3\n"
                        + "Controller3 .. controllerWW\n"
                        + "note \"Controller\" as Controller4\n"
                        + "Controller4 .. controllerCCC\n"
                        + "@enduml";
        Assertions.assertEquals(expectedCode, plantUmlCode);
    }

    @Test
    void givenTripleOrRule_whenTransform_thenCorrectPlantUml()
            throws MappingToUmlTranslationFailedException, NoMappingException, NoTripletException,
                    UnrelatedMappingException, ConceptAlreadyExistsException,
                    RelationAlreadyExistsException {
        // given
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

        var rule =
                new ArchitectureRule(
                        "Every Controller must definesMethod Method or definesAttribute Attribute or imports FamixClass or hasAnnotationInstance AnnotationInstance.");

        // when
        PlantUmlTransformer transformer = new PlantUmlTransformer(conceptManager, relationManager);
        String plantUmlCode = transformer.transformToPlantUml(rule);

        // then
        String expectedCode =
                "@startuml Every Controller must definesMethod Method or definesAttribute Attribute or imports FamixClass or hasAnnotationInstance AnnotationInstance.\n"
                        + "title Every Controller must definesMethod Method or definesAttribute Attribute or imports FamixClass or hasAnnotationInstance AnnotationInstance.\n"
                        + "class \".*Controller\" as controllerCCCC <<?annotationInstanceCCCC>> #RoyalBlue {\n"
                        + "}\n"
                        + "class \".*Controller\" as controllerW #OrangeRed {\n"
                        + "}\n"
                        + "class \".*Controller\" as controllerC #RoyalBlue {\n"
                        + "{method} ?methodC()\n"
                        + "}\n"
                        + "class \".*Controller\" as controllerCC #RoyalBlue {\n"
                        + "{field} ?attributeCC\n"
                        + "}\n"
                        + "class \".*Controller\" as controllerCCC #RoyalBlue {\n"
                        + "}\n"
                        + "class \"?famixClassCCC\" as famixClassCCC {\n"
                        + "}\n"
                        + "controllerCCC -[dashed]-> famixClassCCC: <<imports>>\n"
                        + "note \"Controller\" as Controller3\n"
                        + "Controller3 .. controllerCCCC\n"
                        + "note \"Controller\" as Controller4\n"
                        + "Controller4 .. controllerW\n"
                        + "note \"Controller\" as Controller\n"
                        + "Controller .. controllerC\n"
                        + "note \"Controller\" as Controller1\n"
                        + "Controller1 .. controllerCC\n"
                        + "note \"Controller\" as Controller2\n"
                        + "Controller2 .. controllerCCC\n"
                        + "@enduml";
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
        String expectedCode =
                "@startuml Every DataClass must overwrite HashCodeMethod and overwrite EqualsMethod.\n"
                        + "title Every DataClass must overwrite HashCodeMethod and overwrite EqualsMethod.\n"
                        + "folder \".*/data/.*\" as namespace #RoyalBlue {\n"
                        + "class \"?dataClassC\" as dataClassC #RoyalBlue {\n"
                        + "}\n"
                        + "}\n"
                        + "class \"?GENERATED1\" as GENERATED1 {\n"
                        + "{method} hashCode() <<overwrite>>\n"
                        + "}\n"
                        + "class \"?GENERATED2\" as GENERATED2 {\n"
                        + "{method} equals() <<overwrite>>\n"
                        + "}\n"
                        + "annotation \"overwrite\" as type1 {\n"
                        + "}\n"
                        + "annotation \"overwrite\" as type {\n"
                        + "}\n"
                        + "folder \".*/data/.*\" as namespace1 #OrangeRed {\n"
                        + "class \"?dataClassW\" as dataClassW #OrangeRed {\n"
                        + "}\n"
                        + "}\n"
                        + "dataClassC -[bold]-> GENERATED1::hashCode\n"
                        + "note on link: overwrite\n"
                        + "dataClassC -[bold]-> GENERATED2::equals\n"
                        + "note on link: overwrite\n"
                        + "note \"DataClass\" as DataClass\n"
                        + "DataClass .. dataClassC\n"
                        + "note \"HashCodeMethod\" as HashCodeMethod\n"
                        + "HashCodeMethod .. GENERATED1::hashCode\n"
                        + "note \"EqualsMethod\" as EqualsMethod\n"
                        + "EqualsMethod .. GENERATED2::equals\n"
                        + "note \"DataClass\" as DataClass1\n"
                        + "DataClass1 .. dataClassW\n"
                        + "@enduml";
        Assertions.assertEquals(expectedCode, plantUmlCode);
    }

    @Test
    void givenDoubleAndRule_whenTransform_thenCorrectPlantUml()
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

        var rule =
                new ArchitectureRule(
                        "Every Controller can-only use a Controller and use a MainClass and definesMethod Method.");

        // when
        PlantUmlTransformer transformer = new PlantUmlTransformer(conceptManager, relationManager);
        String plantUmlCode = transformer.transformToPlantUml(rule);

        // then
        String expectedCode =
                "@startuml Every Controller can-only use a Controller and use a MainClass and definesMethod Method.\n"
                        + "title Every Controller can-only use a Controller and use a MainClass and definesMethod Method.\n"
                        + "class \"?famixClass1W\" as famixClass1W {\n"
                        + "}\n"
                        + "class \".*Controller\" as controller1W {\n"
                        + "<color:#OrangeRed> {method} ?method1W()\n"
                        + "}\n"
                        + "class \"?mainClassC\" as mainClassC {\n"
                        + "{method} main()\n"
                        + "}\n"
                        + "class \".*Controller\" as controllerC {\n"
                        + "}\n"
                        + "class \".*Controller\" as controller1C {\n"
                        + "<color:#RoyalBlue> {method} ?methodC()\n"
                        + "}\n"
                        + "class \"?famixClassW\" as famixClassW {\n"
                        + "}\n"
                        + "controller1C -[dashed]-> controllerC #line:RoyalBlue;text:RoyalBlue : <<imports>>\n"
                        + "controller1C -[bold]-> controllerC #line:RoyalBlue;text:RoyalBlue \n"
                        + "note on link: use\n"
                        + "controller1C -[dashed]-> mainClassC #line:RoyalBlue;text:RoyalBlue : <<imports>>\n"
                        + "controller1C -[bold]-> mainClassC #line:RoyalBlue;text:RoyalBlue \n"
                        + "note on link: use\n"
                        + "controller1W -[dashed]-> famixClassW #line:OrangeRed;text:OrangeRed : <<imports>>\n"
                        + "controller1W -[bold]-> famixClassW #line:OrangeRed;text:OrangeRed \n"
                        + "note on link: use\n"
                        + "controller1W -[dashed]-> famixClass1W #line:OrangeRed;text:OrangeRed : <<imports>>\n"
                        + "controller1W -[bold]-> famixClass1W #line:OrangeRed;text:OrangeRed \n"
                        + "note on link: use\n"
                        + "note \"Controller\" as Controller2\n"
                        + "Controller2 .. controller1W\n"
                        + "note \"MainClass\" as MainClass\n"
                        + "MainClass .. mainClassC\n"
                        + "note \"Controller\" as Controller1\n"
                        + "Controller1 .. controllerC\n"
                        + "note \"Controller\" as Controller\n"
                        + "Controller .. controller1C\n"
                        + "@enduml";
        Assertions.assertEquals(expectedCode, plantUmlCode);
    }

    @Test
    void givenSubconceptRule_whenTransform_thenCorrectPlantUml()
            throws MappingToUmlTranslationFailedException, NoMappingException, NoTripletException,
                    UnrelatedMappingException, ConceptAlreadyExistsException,
                    RelationAlreadyExistsException {
        // given
        CustomConcept subjectConcept = new CustomConcept("Exception", "");
        String subjectMappingString =
                "isException: (?class rdf:type famix:FamixClass)"
                        + " (?class famix:hasName ?name)"
                        + " regex(?name, '.*Exception')"
                        + " -> (?class rdf:type architecture:Exception)";
        ConceptMapping subjectMapping =
                createConceptMapping(subjectMappingString, Collections.emptyList(), subjectConcept);
        subjectConcept.setMapping(subjectMapping);
        conceptManager.addConcept(subjectConcept);

        String thatPredicateMappingString =
                "isThrownByMapping: (?method rdf:type famix:Method)"
                        + " (?method famix:throwsException ?exception)"
                        + " -> (?exception architecture:isThrownBy ?method)";
        RelationMapping thatPredicateMapping =
                createRelationMapping(thatPredicateMappingString, Collections.emptyList());
        CustomRelation thatPredicateRelation =
                new CustomRelation("isThrownBy", "", new HashSet<>(), new HashSet<>());
        thatPredicateRelation.setMapping(thatPredicateMapping, conceptManager);
        relationManager.addRelation(thatPredicateRelation);

        CustomConcept thatConcept = new CustomConcept("Constructor", "");
        String thatMappingString =
                "isConstructor: (?method rdf:type famix:Method)"
                        + " (?method famix:isConstructor 'true'^^xsd:boolean)"
                        + " -> (?method rdf:type architecture:Constructor)";
        ConceptMapping thatMapping =
                createConceptMapping(thatMappingString, Collections.emptyList(), thatConcept);
        thatConcept.setMapping(thatMapping);
        conceptManager.addConcept(thatConcept);

        CustomConcept objectConcept = new CustomConcept("ConstructorException", "");
        String objectMappingString =
                "isConstructorException: (?exception rdf:type famix:FamixClass)"
                        + " (?exception famix:hasName ?name)"
                        + " regex(?name, '.*ConstructorException')"
                        + " -> (?exception rdf:type architecture:ConstructorException)";
        ConceptMapping objectMapping =
                createConceptMapping(objectMappingString, Collections.emptyList(), objectConcept);
        objectConcept.setMapping(objectMapping);
        conceptManager.addConcept(objectConcept);

        var rule =
                new ArchitectureRule(
                        "Every Exception that (isThrownBy a Constructor) must be a ConstructorException.");

        // when
        PlantUmlTransformer transformer = new PlantUmlTransformer(conceptManager, relationManager);
        String plantUmlCode = transformer.transformToPlantUml(rule);

        // then
        String expectedCode =
                "@startuml Every Exception that (isThrownBy a Constructor) must be a ConstructorException.\n"
                        + "title Every Exception that (isThrownBy a Constructor) must be a ConstructorException.\n"
                        + "class \"?GENERATED1\" as GENERATED1 {\n"
                        + "{method} <<Create>> ?constructorC()\n"
                        + "}\n"
                        + "class \"?GENERATED2\" as GENERATED2 {\n"
                        + "<color:#OrangeRed> {method} <<Create>> ?constructorW()\n"
                        + "}\n"
                        + "class \".*Exception\" as exceptionC {\n"
                        + "}\n"
                        + "class \".*ConstructorException\" as constructorExceptionC {\n"
                        + "}\n"
                        + "class \".*Exception\" as exceptionW #OrangeRed {\n"
                        + "}\n"
                        + "GENERATED1::constructorC -[dashed]-> exceptionC: <<throws>>\n"
                        + "exceptionC -[bold]-> GENERATED1::constructorC\n"
                        + "note on link: isThrownBy\n"
                        + "exceptionC --|> constructorExceptionC #line:RoyalBlue;text:RoyalBlue : Is-a\n"
                        + "GENERATED2::constructorW -[dashed]-> exceptionW #line:OrangeRed;text:OrangeRed : <<throws>>\n"
                        + "exceptionW -[bold]-> GENERATED2::constructorW #line:OrangeRed;text:OrangeRed \n"
                        + "note on link: isThrownBy\n"
                        + "note \"Constructor\" as Constructor\n"
                        + "Constructor .. GENERATED1::constructorC\n"
                        + "note \"Constructor\" as Constructor1\n"
                        + "Constructor1 .. GENERATED2::constructorW\n"
                        + "note \"Exception\" as Exception\n"
                        + "Exception .. exceptionC\n"
                        + "note \"ConstructorException\" as ConstructorException\n"
                        + "ConstructorException .. constructorExceptionC\n"
                        + "note \"Exception\" as Exception1\n"
                        + "Exception1 .. exceptionW\n"
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
