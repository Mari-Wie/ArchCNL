package asciidocparser;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.FileUtils;
import org.architecture.cnl.ide.CNL2OWLGenerator;
import org.asciidoctor.Asciidoctor;
import org.asciidoctor.ast.Block;
import org.asciidoctor.ast.Document;
import org.asciidoctor.ast.StructuralNode;

import datatypes.ArchitectureRule;
import datatypes.ArchitectureRules;

public class AsciiDocArc42Parser
{

    private final static String TMP_MAPPING_FILE_NAME = "./mapping.txt";
    private final static String EXTENSION = ".architecture";
    private final static String OWL_EXTENSION = ".owl";
    private final static String PREFIX = "tmp";

    private final static String javaOntologyNamespace = "@prefix java: <http://arch-ont.org/ontologies/javacodeontology.owl#>\n";
    private final static String famixNamespace = "@prefix famix: <http://arch-ont.org/ontologies/famix.owl#>\n";
    private final static String mavenOntologyNamespace = "@prefix maven: <http://arch-ont.org/ontologies/maven.owl#>\n";
    private final static String mainOntologyNamespace = "@prefix main: <http://arch-ont.org/ontologies/main.owl#>\n";
    private final static String osgiOntologyNamespace = "@prefix osgi: <http://arch-ont.org/ontologies/osgi.owl#>\n";
    private final static String historyOntologyNamespace = "@prefix git: <http://www.arch-ont.org/ontologies/git.owl#>\n";
    private final static String architectureOntologyNamespace = "@prefix architecture: <http://www.arch-ont.org/ontologies/architecture.owl#>\n\n";

    private final static String ONTOLOGY_PREFIXES_FOR_MAPPING = famixNamespace
            + javaOntologyNamespace + mavenOntologyNamespace
            + mainOntologyNamespace + osgiOntologyNamespace
            + historyOntologyNamespace + architectureOntologyNamespace;

    private static int id = 0;

    private List<String> ontologyPaths;

    public AsciiDocArc42Parser()
    {
        ontologyPaths = new ArrayList<String>();
    }

    public void parseRulesFromDocumentation(String path)
    {

        Asciidoctor ascii = Asciidoctor.Factory.create();
        CNL2OWLGenerator generator = new CNL2OWLGenerator();

        File file = new File(path);
        HashMap<String, Object> hashmap = new HashMap<String, Object>();
        Document doc = ascii.loadFile(file, hashmap);
        Map<Object, Object> selector = new HashMap<Object, Object>();
        selector.put("role", "rule");
        //		selector.put("skip", "false");
        List<StructuralNode> result = doc.findBy(selector);
        int id_for_file = 0;
        String ontologyPath = "";
        for (StructuralNode structuralNode : result)
        {
            Block b = (Block) structuralNode;
            List<String> lines = b.lines();
            for (String line : lines)
            {
                File f = new File(PREFIX + "_" + id_for_file + EXTENSION);

                ArchitectureRule rule = new ArchitectureRule();
                rule.setId(id);
                rule.setCnlSentence(line);
                ArchitectureRules rules = ArchitectureRules.getInstance();
                rules.addRule(rule, id);
                id++;
                try
                {
                    ontologyPath = "./architecture" + id_for_file
                            + OWL_EXTENSION;
                    ontologyPaths.add(ontologyPath);
                    FileUtils.writeStringToFile(f, line + "\n", (Charset) null,
                            true);
                    rules.addRuleWithPathToConstraint(rule, id_for_file,
                            "./architecture" + id_for_file + OWL_EXTENSION);
                    generator.transformCNLFile(
                            PREFIX + "_" + id_for_file + EXTENSION);
                    System.out.println("transformed");

                    f.delete();
                }
                catch (IOException e)
                {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                id_for_file++;
            }
        }

    }

    public void parseMappingRulesFromDocumentation(String path)
    {
        Asciidoctor ascii = Asciidoctor.Factory.create();
        Document doc = ascii.loadFile(new File(path),
                new HashMap<String, Object>());
        Map<Object, Object> selector = new HashMap<Object, Object>();
        selector.put("role", "mapping");

        List<StructuralNode> result = doc.findBy(selector);
        File f = new File(TMP_MAPPING_FILE_NAME);
        if (f.exists())
        {
            f.delete();
            try
            {
                f.createNewFile();
            }
            catch (IOException e)
            {
                System.out.println("Can't create file for mapping.");
            }
        }
        String allMappingRules = "";
        for (StructuralNode structuralNode : result)
        {

            String tmp = "";
            Block b = (Block) structuralNode;
            List<String> lines = b.lines();
            for (String line : lines)
            {
                tmp += line;

                // TODO forward to JENA reasoner

            }
            System.out.println("[" + tmp + "]");
            allMappingRules += "[" + tmp + "]" + "\n";

        }
        try
        {
            FileUtils.writeStringToFile(f,
                    ONTOLOGY_PREFIXES_FOR_MAPPING + allMappingRules,
                    (Charset) null, true);
        }
        catch (IOException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        // f.delete();

    }

    public List<String> getOntologyPaths()
    {
        return ontologyPaths;
    }

    public String getMappingFilePath()
    {
        return TMP_MAPPING_FILE_NAME;
    }

}
