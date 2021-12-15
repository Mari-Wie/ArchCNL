package org.archcnl.stardogwrapper.impl;

import com.complexible.stardog.ContextSets;
import com.complexible.stardog.api.Connection;
import com.complexible.stardog.api.ConnectionConfiguration;
import com.complexible.stardog.icv.Constraint;
import com.complexible.stardog.icv.api.ICVConnection;
import com.complexible.stardog.protocols.http.client.BaseHttpClient.HttpClientException;
import com.complexible.stardog.reasoning.Proof;
import com.complexible.stardog.reasoning.ProofType;
import com.complexible.stardog.reasoning.ProofWriter;
import com.stardog.stark.Statement;
import com.stardog.stark.Values;
import com.stardog.stark.io.RDFFormats;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.StringWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import org.apache.jena.rdf.model.Model;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.archcnl.common.datatypes.ConstraintViolation.ConstraintViolationBuilder;
import org.archcnl.stardogwrapper.api.ConstraintViolationsResultSet;
import org.archcnl.stardogwrapper.api.StardogDatabaseAPI;
import org.archcnl.stardogwrapper.api.StardogICVAPI;
import org.archcnl.stardogwrapper.api.exceptions.DBAccessException;

public class StardogICVAPIImpl implements StardogICVAPI {

    private static int id = 0;

    private static final Logger LOG = LogManager.getLogger(StardogICVAPI.class);

    private StardogDatabaseAPI db;

    /**
     * Constructor.
     *
     * @param database The database to use.
     */
    public StardogICVAPIImpl(StardogDatabaseAPI database) {
        db = database;
    }

    @Override
    public void addIntegrityConstraint(Model constraintModel) throws DBAccessException {
        LOG.debug("Adding contraints to database");
        try (Connection aConn = establishConnection()) {
            ICVConnection aValidator = aConn.as(ICVConnection.class);

            aConn.begin();
            Writer writer = new StringWriter();

            constraintModel.write(writer);

            InputStream reader = new ByteArrayInputStream(writer.toString().getBytes());
            aValidator.addConstraints().format(RDFFormats.RDFXML).stream(reader);

            aConn.commit();
            
        } catch (HttpClientException e) {
            LOG.error("Error while adding constraints to the database: ", e);
            throw new DBAccessException("Adding integrity constraints failed.", e);
        }
    }

    @Override
    public void removeIntegrityConstraints() {
        LOG.debug("Removing all constraints from the database");
        try (Connection aConn = establishConnection()) {
            ICVConnection aValidator = aConn.as(ICVConnection.class);

            aValidator.begin();
            aValidator.clearConstraints();
            aValidator.commit();
        }
    }

    @Override
    public List<ConstraintViolationsResultSet> explainViolationsForContext(String context) {

        LOG.trace("Explaining violations for context: " + context);

        List<ConstraintViolationsResultSet> result = new ArrayList<>();

        try (Connection aConn = establishConnection()) {

            ICVConnection aValidator = aConn.as(ICVConnection.class);

            Set<Constraint> constraints = aValidator.getConstraints();

            Collection<com.stardog.stark.IRI> selectedContext = new ArrayList<>();
            selectedContext.add(Values.iri(context));

            LOG.trace("is valid: " + aValidator.isValid(ContextSets.DEFAULT_ONLY));

            for (Constraint constraint : constraints) {
                Iterable<Proof> proofs =
                        aValidator
                                .explain(constraint)
                                .activeGraphs(selectedContext)
                                .countLimit(600)
                                .proofs();
                for (Proof p : proofs) {
                    LOG.debug(ProofWriter.toString(p));
                }
                result.add(storeViolations(id, constraint, proofs));
                id++;
            }
        }
        return result;
    }

    private Connection establishConnection() {
        return ConnectionConfiguration.to(db.getDatabaseName())
                .server(db.getServer())
                .reasoning(false)
                .credentials(db.getUserName(), db.getPassword())
                .connect();
    }

    private ConstraintViolationsResultSet storeViolations(
            int id, Constraint constraint, Iterable<Proof> proofs) {
        ConstraintViolationsResultSet result = new ConstraintViolationsResultSet();
        for (Proof proof : proofs) {
            Iterable<Statement> asserted = proof.getStatements(ProofType.ASSERTED);
            ConstraintViolationBuilder builder = new ConstraintViolationBuilder();

            for (Statement statement : asserted) {
                builder.addViolation(
                        statement.subject().toString(),
                        statement.predicate().toString(),
                        statement.object().toString());
            }
            Iterable<Statement> notInferred = proof.getStatements(ProofType.NOT_INFERRED);
            for (Statement statement : notInferred) {
                builder.addNotInferredStatement(
                        statement.subject().toString(),
                        statement.predicate().toString(),
                        statement.object().toString());
            }

            LOG.debug("Proof: " + proof.toString());
            result.addViolation(builder.build());
        }
        return result;
    }
}
