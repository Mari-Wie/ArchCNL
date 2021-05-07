package org.archcnl.owlify.famix.parser;

import com.github.javaparser.StaticJavaParser;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.symbolsolver.JavaSymbolSolver;
import com.github.javaparser.symbolsolver.resolution.typesolvers.CombinedTypeSolver;
import com.github.javaparser.symbolsolver.resolution.typesolvers.JavaParserTypeSolver;
import com.github.javaparser.symbolsolver.resolution.typesolvers.ReflectionTypeSolver;
import java.io.File;
import java.io.FileNotFoundException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.filefilter.TrueFileFilter;
import org.apache.commons.io.filefilter.WildcardFileFilter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.archcnl.owlify.famix.codemodel.Project;
import org.archcnl.owlify.famix.codemodel.SourceFile;
import org.archcnl.owlify.famix.exceptions.FileIsNotAJavaClassException;
import org.archcnl.owlify.famix.visitors.ImportDeclarationVisitor;
import org.archcnl.owlify.famix.visitors.JavaTypeVisitor;
import org.archcnl.owlify.famix.visitors.NamespaceVisitor;

public class ModelExtractor {

    private static final Logger LOG = LogManager.getLogger(ModelExtractor.class);

    private Project project;
    private List<Path> sourcePaths;

    public ModelExtractor(List<Path> sourcePaths) {
        this.sourcePaths = new ArrayList<>(sourcePaths);
    }

    public Project extractCodeModel() {
        setup();
        visitJavaFiles();
        return project;
    }

    private void setup() {
        CombinedTypeSolver combinedTypeSolver = new CombinedTypeSolver();
        combinedTypeSolver.add(new ReflectionTypeSolver());
        for (Path path : sourcePaths) {
            LOG.debug("Adding Java source path: " + path);
            combinedTypeSolver.add(new JavaParserTypeSolver(path));
        }

        JavaSymbolSolver symbolSolver = new JavaSymbolSolver(combinedTypeSolver);
        StaticJavaParser.getConfiguration().setSymbolResolver(symbolSolver);

        project = new Project();
    }

    private void visitFile(Path path, CompilationUnit unit) {
        JavaTypeVisitor typeVisitor = new JavaTypeVisitor();

        unit.getTypes().forEach(type -> type.accept(typeVisitor, null));

        //        unit.accept(typeVisitor, null);

        if (typeVisitor.getDefinedTypes().isEmpty()) {
            LOG.error(
                    "The following file does not contain a valid Java type: "
                            + path.toAbsolutePath());
            return;
        }

        NamespaceVisitor namespaceVisitor = new NamespaceVisitor();
        ImportDeclarationVisitor importVisitor = new ImportDeclarationVisitor();

        unit.accept(namespaceVisitor, null);
        unit.accept(importVisitor, null);

        project.addFile(
                new SourceFile(
                        path,
                        typeVisitor.getDefinedTypes(),
                        namespaceVisitor.getNamespace(),
                        importVisitor.getImports()));
    }

    private void visitJavaFiles() {
        for (Path path : sourcePaths) {
            for (File file : getJavaFilesAtPath(path)) {
                try {
                    visitFile(
                            file.toPath(),
                            CompilationUnitFactory.getFromPath(file.getAbsolutePath()));

                } catch (FileIsNotAJavaClassException e) {
                    LOG.error(
                            "The following file is not a Java file. Skipping it ... : "
                                    + file.getAbsolutePath());
                    e.printStackTrace();
                } catch (FileNotFoundException e) {
                    LOG.error(
                            "The following file cannot be accessed. Skipping it ... : "
                                    + file.getAbsolutePath());
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * Returns all Java Files located under the specified path. Only files with the suffix ".java"
     * will be returned.
     */
    private Collection<File> getJavaFilesAtPath(Path path) {
        return FileUtils.listFiles(
                path.toFile(),
                new WildcardFileFilter(
                        ProgrammingLanguage.getFileExtensionWildCard(ProgrammingLanguage.JAVA)),
                TrueFileFilter.INSTANCE);
    }
}
