package org.archcnl.owlify.famix.parser;

//
// public class FamixOntologyTransformer extends AbstractOwlifyComponent {
//
//    private static final Logger LOG = LogManager.getLogger(FamixOntologyTransformer.class);
//
//    private FamixOntology ontology;
//    private GeneralSoftwareArtifactOntology mainOntology;
//    private Map<String, Individual> unitToIndividualMap;
//
//    private List<SourceFile> sourceFileModels;
//
//    /** @param resultPath - Path to the file in which the results will be stored. */
//    public FamixOntologyTransformer(String resultPath) {
//        super(resultPath);
//        LOG.debug("Reading resource ontologies ...");
//        InputStream famixOntologyInputStream =
//                getClass().getResourceAsStream("/ontologies/famix.owl");
//        ontology = new FamixOntology(famixOntologyInputStream);
//        InputStream mainOntologyInputStream =
//                getClass().getResourceAsStream("/ontologies/main.owl");
//        mainOntology = new GeneralSoftwareArtifactOntology(mainOntologyInputStream);
//
//        unitToIndividualMap = new HashMap<>();
//
//        sourceFileModels = new ArrayList<>();
//    }
//
//    public void transform() {
//        LOG.trace("Starting transform");
//
//        CombinedTypeSolver combinedTypeSolver = new CombinedTypeSolver();
//        combinedTypeSolver.add(new ReflectionTypeSolver());
//        List<Path> sourcePaths = super.getSourcePaths();
//        for (Path path : sourcePaths) {
//            LOG.debug("Adding Java source path: " + path);
//            combinedTypeSolver.add(new JavaParserTypeSolver(path));
//        }
//
//        JavaSymbolSolver symbolSolver = new JavaSymbolSolver(combinedTypeSolver);
//        StaticJavaParser.getConfiguration().setSymbolResolver(symbolSolver);
//
//        resolveAllTypes(sourcePaths);
//        parseOtherElements(sourcePaths);
//
//        ontology.add(mainOntology.getOntology());
//        LOG.debug("Writing code model to the file: " + super.getResultPath());
//        ontology.save(super.getResultPath());
//    }
//
//    private void parseOtherElements(List<Path> sourcePaths) {
//        LOG.trace("Starting parseOtherElements ...");
//        visitJavaFiles(sourcePaths, (filePath, unit) -> {
//            LOG.debug("Parsing code file: " + filePath);
//
//            Individual currentUnit = unitToIndividualMap.get(filePath);
//
//            // some calls are commented to increase performance
//            // however, this decreases the number of architecture
//            // violations that can be found
//            unit.accept(new InheritanceVisitor(ontology, currentUnit), null); // instantiated only
// here
//            unit.accept(new JavaFieldVisitor(ontology, currentUnit), null); // instantiated only
// here
//            unit.accept(new ConstructorDeclarationVisitor(ontology, currentUnit), null); //
// instantiated only here
//            unit.accept(
//                    new MethodDeclarationVisitor(ontology, currentUnit), // instantiated only here
//                    null);
//            unit.accept(
//                    new NamespaceVisitor( // instantiated only here
//                            ontology,
//                            currentUnit),
//                    null);
//            unit.accept(
//                    new NormalAnnotationExpressionVisitor(
//                            ontology, currentUnit),
//                    null);
//            unit.accept(
//                    new MarkerAnnotationExpressionVisitor(
//                            ontology, currentUnit),
//                    null);
//            unit.accept(
//                    new SingleMemberAnnotationExpressionVisitor(
//                            ontology, currentUnit),
//                    null);
//            //              unit.accept(new AccessVisitor(ontology,currentUnitIndividual), null);
//            unit.accept(
//                    new ImportDeclarationVisitor(ontology, currentUnit), // instantiated only here
//                    null);
//
//        });
//    }
//
//    private void resolveAllTypes(List<Path> sourcePaths) {
//        visitJavaFiles(sourcePaths, (filePath, unit) -> {
//            JavaTypeVisitor visitor = new JavaTypeVisitor(ontology);
//            unit.accept(visitor, null);
//
//            if (visitor.getFamixTypeIndividual() == null) {
//                LOG.error("The following file does not contain a valid Java type: " + filePath);
//                return;
//            }
//
//            mainOntology.getSoftwareArtifactFileIndividual(filePath,
// visitor.getFamixTypeIndividual());
//
//            unitToIndividualMap.put(filePath, visitor.getFamixTypeIndividual());
//        });
//    }
//
//    @Override
//    public Map<String, String> getProvidedNamespaces() {
//        HashMap<String, String> res = new HashMap<>();
//        res.put("famix", ontology.getOntologyNamespace());
//        return res;
//    }
//
//    /**
//     * Executes the specified lambda on all Java source files located under the given source
// paths.
//     * @param sourcePaths A list of paths to directories containing the files to process.
//     * @param visitor A lambda expression which is executed on each file.
//     */
//    private void visitJavaFiles(List<Path> sourcePaths, JavaFileVisitor visitor) {
//        for (Path path : sourcePaths) {
//            for (File file : getJavaFilesAtPath(path)) {
//                try {
//                    visitor.visitFile(file.getAbsolutePath(),
// CompilationUnitFactory.getFromPath(file.getAbsolutePath()));
//
//                } catch (FileIsNotAJavaClassException e) {
//                    LOG.error("The following file is not a Java file. Skipping it ... : " +
// file.getAbsolutePath());
//                    e.printStackTrace();
//                } catch (FileNotFoundException e) {
//                    LOG.error("The following file cannot be accessed. Skipping it ... : " +
// file.getAbsolutePath());
//                    e.printStackTrace();
//                }
//            }
//        }
//    }
//
//    /**
//     * Returns all Java Files located under the specified path.
//     * Only files with the suffix ".java" will be returned.
//     */
//    private Collection<File> getJavaFilesAtPath(Path path) {
//        return FileUtils.listFiles(
//                path.toFile(),
//                new WildcardFileFilter(
//                        ProgrammingLanguage.getFileExtensionWildCard(
//                                ProgrammingLanguage.JAVA)),
//                TrueFileFilter.INSTANCE);
//    }
//
//    private static interface JavaFileVisitor {
//        /**
//         * Used as a parameter for accepting lambda expressions. Implementations are supposed to
//         * process the given Java file/compilation unit.
//         *
//         * @param filePath The path of the file being processed.
//         * @param unit The CompilationUnit corresponding to the file being processed.
//         */
//        public void visitFile(String filePath, CompilationUnit unit);
//    }
//
// }
