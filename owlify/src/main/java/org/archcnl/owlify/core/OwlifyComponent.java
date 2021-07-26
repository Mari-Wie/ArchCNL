package org.archcnl.owlify.core;

import java.nio.file.Path;
import java.util.List;
import java.util.Map;
import org.apache.jena.rdf.model.Model;

/**
 * Interface for input parsers. Each parser takes some files as input, parses them into an OWL
 * ontology and writes this ontology into an output file.
 */
public interface OwlifyComponent {
    /**
     * This method performs the parsing step.
     * The inputs will be read and the
     * resulting output ontology will be returned.
     */
    public Model transform(List<Path> sourcePaths);

    /**
     * @return Returns a map which contains all OWL namespaces which are provided by the component.
     *     The keys are the abbreviations, and the values are the full URIs of these namespaces.
     */
    public Map<String, String> getProvidedNamespaces();
}
