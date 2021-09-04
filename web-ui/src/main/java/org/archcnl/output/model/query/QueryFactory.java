package org.archcnl.output.model.query;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import org.archcnl.output.model.query.attribute.QueryField;
import org.archcnl.output.model.query.attribute.QueryNamespace;
import org.archcnl.output.model.query.attribute.QueryObject;
import org.archcnl.output.model.query.attribute.QueryObjectType;
import org.archcnl.output.model.query.attribute.QueryPredicate;

public class QueryFactory {

    public Query createQuery(Set<String> selectSet, List<List<String>> whereClauseStringList) {
        Set<QueryNamespace> namespaces = temporary_function_get_namespaces();
        Set<QueryField> queryFieldSet =
                selectSet.stream().map(QueryField::new).collect(Collectors.toSet());
        WhereClause whereClause = createWhereClause(whereClauseStringList);
        return new Query(namespaces, new SelectClause(queryFieldSet), whereClause);
    }

    private QueryObjectType getTypeFromString(String objString) {
        return QueryObjectType.FIELD;
    }

    private QueryObject createQueryObject(String objString) {
        return new QueryObject(objString, getTypeFromString(objString));
    }

    private QueryPredicate createQueryPredicate(String praedicateString) {
        String[] key_value_pair = praedicateString.split(":");
        return new QueryPredicate(key_value_pair[0], key_value_pair[1]);
    }

    private QueryField createQuerySubject(String subjectString) {
        return new QueryField(subjectString);
    }

        //TODO relpace get methods with clear indication where which part is in the LIst
    private WhereTriple createWhereTriplet(List<String> subObjPradList) {
        QueryField subject = createQuerySubject(subObjPradList.get(0));
        QueryPredicate predicate = createQueryPredicate(subObjPradList.get(1));
        QueryObject object = createQueryObject(subObjPradList.get(2));
        return new WhereTriple(subject, predicate, object);
    }

    private WhereClause createWhereClause(List<List<String>> whereClauseStringList) {
        WhereClause whereClause = new WhereClause(null);
        whereClauseStringList.stream()
                .forEach(ele -> whereClause.addTriple(createWhereTriplet(ele)));
        return whereClause;
    }

    private Set<QueryNamespace> temporary_function_get_namespaces() {
        Set<QueryNamespace> namespaceSet = new HashSet<QueryNamespace>();

        namespaceSet.add(
                new QueryNamespace("rdf", "<http://www.w3.org/1999/02/22-rdf-syntax-ns#>"));
        namespaceSet.add(new QueryNamespace("owl", "<http://www.w3.org/2002/07/owl#>"));
        namespaceSet.add(new QueryNamespace("rdfs", "<http://www.w3.org/2000/01/rdf-schema#>"));
        namespaceSet.add(new QueryNamespace("xsd", "<http://www.w3.org/2001/XMLSchema#>"));
        namespaceSet.add(
                new QueryNamespace(
                        "conformance",
                        "<http://arch-ont.org/ontologies/architectureconformance#>"));
        namespaceSet.add(
                new QueryNamespace("famix", "<http://arch-ont.org/ontologies/famix.owl#>"));
        namespaceSet.add(
                new QueryNamespace(
                        "architecture", "<http://www.arch-ont.org/ontologies/architecture.owl#>"));

        return namespaceSet;
    }
}
