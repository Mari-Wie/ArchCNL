package org.archcnl.owlify.core;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import org.apache.jena.rdf.model.Model;

/**
 * Abstract base class of input parsers which can be used as a template for new input parsers. It
 * implements the OwlifiyComponent and includes some general behavior of input parsers.
 */
public abstract class AbstractOwlifyComponent implements OwlifyComponent {

    // list of input paths
    private List<Path> sourcePaths;

    /** Constructor. */
    protected AbstractOwlifyComponent() {
        this.sourcePaths = new ArrayList<Path>();
    }

    public void addSourcePath(Path path) {
        sourcePaths.add(path);
    }

    public List<Path> getSourcePaths() {
        return sourcePaths;
    }
}
