package org.archcnl.owlify.famix.ontology;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import org.archcnl.owlify.famix.codemodel.ClassInterfaceEnum;
import org.archcnl.owlify.famix.codemodel.DefinedType;
import org.archcnl.owlify.famix.codemodel.SourceFile;

public class DefinedTypeExtractor {

    private Set<String> userDefinedTypes;

    public DefinedTypeExtractor(List<SourceFile> sourceFiles) {
        userDefinedTypes = new HashSet<>();
        setUserDefinedTypes(sourceFiles);
    }

    public boolean isUserDefinedType(String fullyQualifiedTypeName) {
        return userDefinedTypes.contains(fullyQualifiedTypeName);
    }

    private void setUserDefinedTypes(List<SourceFile> files) {
        userDefinedTypes =
                files.stream()
                        .flatMap(file -> extractDefinedTypeNames(file.getDefinedType()).stream())
                        .collect(Collectors.toSet());
    }

    private List<String> extractDefinedTypeNames(DefinedType type) {
        List<String> result = new ArrayList<>();
        result.add(type.getName());

        if (type instanceof ClassInterfaceEnum) {
            List<DefinedType> nested = ((ClassInterfaceEnum) type).getNestedTypes();

            result.addAll(
                    nested.stream()
                            .map(this::extractDefinedTypeNames)
                            .flatMap(List::stream)
                            .collect(Collectors.toList()));
        }

        return result;
    }
}
