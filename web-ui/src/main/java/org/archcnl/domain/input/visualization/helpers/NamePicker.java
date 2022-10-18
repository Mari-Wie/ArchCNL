package org.archcnl.domain.input.visualization.helpers;

import java.util.Map;
import java.util.Set;
import org.archcnl.domain.common.conceptsandrelations.andtriplets.triplet.Variable;

public class NamePicker {

    private static final String GENERATED_NAME_PREFIX = "GENERATED";
    private static int generatedNamesCounter = 0;

    private NamePicker() {}

    public static Variable pickUniqueVariable(
            Set<Variable> usedVariables,
            Map<Variable, Variable> renamedVariables,
            Variable oldVariable) {
        Variable newVariable = null;
        if (renamedVariables.containsKey(oldVariable)) {
            newVariable = renamedVariables.get(oldVariable);
        } else if (!usedVariables.contains(oldVariable)) {
            newVariable = oldVariable;
            usedVariables.add(newVariable);
            renamedVariables.put(oldVariable, newVariable);
        } else {
            int postfix = 0;
            newVariable = oldVariable;
            while (usedVariables.contains(newVariable)) {
                postfix++;
                newVariable = new Variable(oldVariable.getName() + postfix);
            }
            renamedVariables.put(oldVariable, newVariable);
            usedVariables.add(newVariable);
        }
        return newVariable;
    }

    public static String getNextGeneratedName() {
        generatedNamesCounter++;
        return GENERATED_NAME_PREFIX + generatedNamesCounter;
    }
}
