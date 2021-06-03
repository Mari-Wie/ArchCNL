package org.archcnl.kotlinparser.parser;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.archcnl.kotlinparser.parser.FileVisitor.KotlinFileVisitorListener;
import org.archcnl.owlify.famix.codemodel.Project;
import org.archcnl.owlify.famix.codemodel.SourceFile;

public class ModelExtractor implements KotlinFileVisitorListener {
    private static final Logger LOG = LogManager.getLogger(ModelExtractor.class);

    private final Project project;
    private final List<Path> sourcePaths;
    private final ExecutorService executor;
    private final List<Future<SourceFile>> resultList;

    public ModelExtractor(List<Path> sourcePaths) {
        this.sourcePaths = new ArrayList<>();
        this.sourcePaths.addAll(sourcePaths);
        this.project = new Project();
        this.executor = Executors.newWorkStealingPool();
        this.resultList = new ArrayList<>();
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
        for (var result : this.resultList) {
            try {
                project.addFile(result.get());
            } catch (InterruptedException e) {
                LOG.error("FileHandler was interrupted", e);
            } catch (ExecutionException e) {
                LOG.error("Excpetion while execution of FileHandler", e);
            }
        }
    }

    @Override
    public void handleKotlinFile(Path path, String content) {
        var fileHandler = new FileHandlerCallable(path, new KtParser(), content);
        var futureResult = executor.submit(fileHandler);
        resultList.add(futureResult);
    }
}
