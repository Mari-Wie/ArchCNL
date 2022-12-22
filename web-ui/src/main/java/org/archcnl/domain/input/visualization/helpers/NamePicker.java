package org.archcnl.domain.input.visualization.helpers;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import org.archcnl.domain.common.conceptsandrelations.andtriplets.triplet.Variable;

public class NamePicker {

    private static final String GENERATED_NAME_PREFIX = "GENERATED";
    private static int generatedNamesCounter = 0;
    private static Map<String, Integer> conceptAppearences = new HashMap<>();

    private NamePicker() {}

    public static Variable pickUniqueVariable(
            Set<Variable> usedVariables,
            Map<Variable, Variable> renamedVariables,
            Variable oldVariable) {
        Variable newVariable = null;
        if (renamedVariables.containsKey(oldVariable)) {
            // when variable has already been used at this level
            newVariable = renamedVariables.get(oldVariable);
            return newVariable;
        }
        if (!usedVariables.contains(oldVariable)) {
            // when this variable is unused
            usedVariables.add(oldVariable);
            renamedVariables.put(oldVariable, oldVariable);
            return oldVariable;
        }
        // if variable has already been used elsewhere
        newVariable = findUnusedVariable(usedVariables, oldVariable);
        renamedVariables.put(oldVariable, newVariable);
        usedVariables.add(newVariable);
        return newVariable;
    }

    public static Variable pickUniqueVariableName(
            String variableName, Set<Variable> usedVariables) {
        String name = NamePicker.getStringWithFirstLetterInLowerCase(variableName);
        Variable nameVariable = new Variable(name);
        return NamePicker.pickUniqueVariable(usedVariables, new HashMap<>(), nameVariable);
    }

    public static String getUniqueConceptVariantSuffix(String conceptName) {
        if (!conceptAppearences.containsKey(conceptName)) {
            conceptAppearences.put(conceptName, 1);
            return "";
        } else {
            Integer count = conceptAppearences.get(conceptName);
            conceptAppearences.put(conceptName, count + 1);
            return "_" + count;
        }
    }

    public static String getNextGeneratedName() {
        generatedNamesCounter++;
        return GENERATED_NAME_PREFIX + generatedNamesCounter;
    }

    public static void reset() {
        generatedNamesCounter = 0;
        conceptAppearences = new HashMap<>();
    }

    public static String getStringWithFirstLetterInLowerCase(String str) {
        return str.substring(0, 1).toLowerCase() + str.substring(1);
    }

    private static Variable findUnusedVariable(Set<Variable> usedVariables, Variable oldVariable) {
        Variable newVariable = oldVariable;
        int postfix = 0;
        while (usedVariables.contains(newVariable)) {
            postfix++;
            newVariable = new Variable(oldVariable.getName() + postfix);
        }
        return newVariable;
    }
}
