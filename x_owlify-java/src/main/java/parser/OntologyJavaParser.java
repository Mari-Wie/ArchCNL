package parser;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.filefilter.TrueFileFilter;
import org.apache.commons.io.filefilter.WildcardFileFilter;
import org.apache.jena.ontology.Individual;
import org.apache.jena.ontology.OntModel;
import org.apache.jena.ontology.OntModelSpec;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.github.javaparser.JavaParser;
import com.github.javaparser.ast.CompilationUnit;

import startup.OntologyExtractionConfiguration;
import visitors.ClassVisitor;
import visitors.FieldVisitor;
import visitors.MethodVisitor;
import visitors.PackageVisitor;

public class OntologyJavaParser {

	private static final Logger logger = LogManager.getLogger(OntologyJavaParser.class);

	private String pathToSourceCode;
	private String pathToJavaCodeOntology;
	private String codeOntologyNamespace;
	private OntModel ontoModel;
	private String output;

	public OntologyJavaParser(OntologyExtractionConfiguration config) {

		this.pathToJavaCodeOntology = config.getPathToCodeOntology();
		this.pathToSourceCode = config.getPathToSourceCode();
		this.codeOntologyNamespace = config.getCodeOntologyURI();
		this.output = config.getPathToOutput();

		ontoModel = ModelFactory.createOntologyModel(OntModelSpec.OWL_MEM, null);
//		InputStream input = FileManager.get().open(this.pathToJavaCodeOntology);

		if(config.architectureCodeMappingOntologyIsConfigured() && config.architectureConceptsOntologyIsConfigured()) {
			ontoModel.read(config.getPathToCodeOntology());
			ontoModel.read(config.getWithArchitectureConceptsOntology());
			ontoModel.read(config.getWithArchitectureCodeMappingOntology());
		}
		else {
			ontoModel.read(config.getPathToCodeOntology());			
		}

	}

	public void execute() {
		ClassVisitor classVisitor;
		MethodVisitor methodVisitor;
		FieldVisitor fieldVisitor;
		PackageVisitor packageVisitor;
		Map<CompilationUnit,Individual> unitToIndividualMap = new HashMap<CompilationUnit,Individual>();
		for (File file : FileUtils.listFiles(new File(this.pathToSourceCode),
				new WildcardFileFilter(ProgrammingLanguage.getFileExtensionWildCard(ProgrammingLanguage.JAVA)),
				TrueFileFilter.INSTANCE)) {
			if (file.isFile()) {
				try {
					FileInputStream in = new FileInputStream(file.getAbsolutePath());
					CompilationUnit unit = JavaParser.parse(in);
					classVisitor = new ClassVisitor(this.codeOntologyNamespace, ontoModel, unit.toString());
					unit.accept(classVisitor, null);
//					Optional<Range> range = unit.getRange(); //Lines of code
//					if(range.isPresent()) 
//						{
//							System.out.println(range.get().begin);
//							System.out.println(range.get().end);
//							
//						}
					if (classVisitor.getJavaClassIndividual() != null) // TODO: handle EnumDeclarations,
																		// AnnotationDeclarations and package-info.java
					{
						unitToIndividualMap.put(unit, classVisitor.getJavaClassIndividual());
						methodVisitor = new MethodVisitor(this.codeOntologyNamespace, ontoModel,
								classVisitor.getJavaClassIndividual());
						unit.accept(methodVisitor, null);
						fieldVisitor = new FieldVisitor(this.codeOntologyNamespace, ontoModel,
								classVisitor.getJavaClassIndividual());
						unit.accept(fieldVisitor, null);
						packageVisitor = new PackageVisitor(this.codeOntologyNamespace, ontoModel,
								classVisitor.getJavaClassIndividual());
						unit.accept(packageVisitor, null);
					}
				} catch (FileNotFoundException e) {
					logger.warn("File does not exist: " + file.getAbsolutePath());
				}

			}
		}
		for(CompilationUnit unit: unitToIndividualMap.keySet()) {
			
			unit.getImports().accept(new ImportDeclarationVisitor(this.codeOntologyNamespace, ontoModel, unitToIndividualMap.get(unit)), null);
		}
		
		ExternalLibraryParser parser = new ExternalLibraryParser(ontoModel,codeOntologyNamespace);
		parser.detectExternalLibraries();
		
		try {
			ontoModel.write(new FileOutputStream(new File(output)));
		} catch (FileNotFoundException e) {
			logger.warn("Cannot write file.");
		}
	}

}
