package org.archcnl.kotlinparser.parser;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.archcnl.kotlinparser.parser.FileVisitor.KotlinFileVisitorListener;
import org.archcnl.kotlinparser.visitor.ImportDeclarationVisitor;
import org.archcnl.kotlinparser.visitor.KotlinTypeVisitor;
import org.archcnl.kotlinparser.visitor.NamespaceVisitor;
import org.archcnl.owlify.famix.codemodel.Project;
import org.archcnl.owlify.famix.codemodel.SourceFile;

public class ModelExtractor implements KotlinFileVisitorListener {
    private static final Logger LOG = LogManager.getLogger(ModelExtractor.class);

    private final Project project;
    private final List<Path> sourcePaths;
    private final KtParser parser;

    public ModelExtractor(List<Path> sourcePaths) {
        this.sourcePaths = new ArrayList<>();
        this.sourcePaths.addAll(sourcePaths);
        this.project = new Project();
        this.parser = new KtParser();
    }

    public Project extractCodeModel() {
        visitFilesInPaths();
        return project;
    }

    private void visitFilesInPaths() {
        for (Path sourcePath : sourcePaths) {
            var fileVisitor = new FileVisitor(this);
            try {
                Files.walkFileTree(sourcePath, fileVisitor);
            } catch (IOException e) {
                LOG.error("FileVisitor produced an error: ", e);
                e.printStackTrace();
            }
        }
    }

    @Override
    public void handleKotlinFile(Path path, String content) {
        LOG.info("Parsing kotlin file: " + path.toString());
        var namedFileContext = parser.parse(content);

        var fileContexTree = namedFileContext.getFileContext();

        var namespaceVisitor = new NamespaceVisitor();
        namespaceVisitor.visit(fileContexTree);

        var typeVisitor = new KotlinTypeVisitor(namedFileContext.getRulesNames());
        typeVisitor.visit(fileContexTree);

        if (typeVisitor.getDefinedTypes().isEmpty()) {
            LOG.error(
                    "The following file does not contain a valid Kotlin type: "
                            + path.toAbsolutePath());
            return;
        }

        var importVisitor = new ImportDeclarationVisitor();
        importVisitor.visit(fileContexTree);

        SourceFile sourceFile =
                new SourceFile(
                        path,
                        typeVisitor.getDefinedTypes(),
                        namespaceVisitor.getNamespace(),
                        importVisitor.getImports());
        project.addFile(sourceFile);
    }
}
