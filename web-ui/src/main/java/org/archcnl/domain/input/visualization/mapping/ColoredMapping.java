package org.archcnl.domain.input.visualization.mapping;

import java.util.List;
import java.util.stream.Collectors;
import org.archcnl.domain.input.model.mappings.Mapping;
import org.archcnl.domain.input.visualization.exceptions.MappingToUmlTranslationFailedException;

public class ColoredMapping {

    private List<ColoredVariant> variants;
    private ColoredTriplet thenTriplet;
    private String name;

    public ColoredMapping(List<ColoredVariant> variants, ColoredTriplet thenTriplet, String name) {
        this.variants = variants;
        this.thenTriplet = thenTriplet;
        this.name = name;
    }

    public void setVariants(List<ColoredVariant> variants) {
        this.variants = variants;
    }

    public List<ColoredVariant> getVariants() {
        return variants;
    }

    public ColoredTriplet getThenTriplet() {
        return thenTriplet;
    }

    public String getName() {
        return name;
    }

    public static ColoredMapping fromMapping(Mapping mapping)
            throws MappingToUmlTranslationFailedException {
        if (mapping.getWhenTriplets().isEmpty()
                || mapping.getWhenTriplets().get(0).getTriplets().isEmpty()) {
            throw new MappingToUmlTranslationFailedException("Body of mapping is empty");
        }
        String name = mapping.getMappingNameRepresentation();
        ColoredTriplet thenTriplet = new ColoredTriplet(mapping.getThenTriplet());
        List<ColoredVariant> variants =
                mapping.getWhenTriplets().stream()
                        .map(ColoredVariant::fromAndTriplets)
                        .collect(Collectors.toList());
        return new ColoredMapping(variants, thenTriplet, name);
    }
}
