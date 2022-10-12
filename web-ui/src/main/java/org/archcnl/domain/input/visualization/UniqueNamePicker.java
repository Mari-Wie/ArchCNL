package org.archcnl.domain.input.visualization;

import java.util.Map;
import java.util.Set;
import org.archcnl.domain.common.conceptsandrelations.andtriplets.triplet.Variable;

public class UniqueNamePicker {

    private UniqueNamePicker() {}

    public static Variable pickUniqueVariable(
            Set<Variable> usedVariables,
            Map<Variable, Variable> renamedVariables,
            Variable oldVariable) {
        Variable newVariable = null;
        if (!usedVariables.contains(oldVariable)) {
            newVariable = oldVariable;
            usedVariables.add(newVariable);
            renamedVariables.put(oldVariable, newVariable);
        } else if (renamedVariables.containsKey(oldVariable)) {
            newVariable = renamedVariables.get(oldVariable);
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
}
