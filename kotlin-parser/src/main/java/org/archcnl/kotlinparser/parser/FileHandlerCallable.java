package org.archcnl.kotlinparser.parser;

import java.nio.file.Path;
import java.util.concurrent.Callable;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.archcnl.kotlinparser.visitor.ImportDeclarationVisitor;
import org.archcnl.kotlinparser.visitor.KotlinTypeVisitor;
import org.archcnl.kotlinparser.visitor.NamespaceVisitor;
import org.archcnl.owlify.famix.codemodel.SourceFile;

public class FileHandlerCallable implements Callable<SourceFile> {
    private static final Logger LOG = LogManager.getLogger(FileHandlerCallable.class);
    private final Path path;
    private final KtParser parser;
    private final String content;

    public FileHandlerCallable(Path path, KtParser parser, String content) {
        this.path = path;
        this.parser = parser;
        this.content = content;
    }

    @Override
    public SourceFile call() {
        LOG.info("Parsing kotlin file: " + path.toString());
        var namedFileContext = parser.parse(content);

        var fileContexTree = namedFileContext.getFileContext();

        var namespaceVisitor = new NamespaceVisitor(namedFileContext.getRulesNames());
        namespaceVisitor.visit(fileContexTree);

        var typeVisitor = new KotlinTypeVisitor(namedFileContext.getRulesNames());
        typeVisitor.visit(fileContexTree);

        if (typeVisitor.getDefinedTypes().isEmpty()) {
            LOG.error(
                    "The following file does not contain a valid Kotlin type: "
                            + path.toAbsolutePath());
        }

        var importVisitor = new ImportDeclarationVisitor(namedFileContext.getRulesNames());
        importVisitor.visit(fileContexTree);

        return new SourceFile(
                path,
                typeVisitor.getDefinedTypes(),
                namespaceVisitor.getNamespace(),
                importVisitor.getImports());
    }
}
