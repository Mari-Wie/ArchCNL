package org.archcnl.owlify.git.ontology;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import org.apache.jena.ontology.Individual;
import org.apache.jena.rdf.model.Model;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.archcnl.owlify.core.MainOntology;
import org.archcnl.owlify.core.OwlifyComponent;
import org.archcnl.owlify.git.model.GitBranch;
import org.archcnl.owlify.git.model.GitChange;
import org.archcnl.owlify.git.model.GitCommit;
import org.archcnl.owlify.git.model.GitTag;

public class GitOntologyTransformer implements OwlifyComponent {
    private static final Logger LOG = LogManager.getLogger(GitOntologyTransformer.class);

    private GitRepositoryScanner scanner;
    private GitOntology ontClassesAndProperties;

    private Individual gitRepository;

    private static int versionId = 0;
    private static int committerID = 0;
    private static int authorID = 0;
    private static int commitID;
    private static int branchId;
    private Map<String, Date> gitFileMaxDates;

    private Map<String, Individual> gitCommits;
    private Map<String, Individual> gitAuthors;
    private static int tagId;

    public GitOntologyTransformer() {
        gitCommits = new HashMap<>();
        gitAuthors = new HashMap<>();
        gitFileMaxDates = new HashMap<>();
    }

    @Override
    public Model transform(List<Path> sourcePaths) {
        sourcePaths = scanForGitFile(sourcePaths);
        InputStream gitOntology = getClass().getResourceAsStream("/ontologies/git.owl");
        InputStream historyOntology = getClass().getResourceAsStream("/ontologies/history.owl");
        InputStream mainOntology = getClass().getResourceAsStream("/ontologies/main.owl");
        this.ontClassesAndProperties = new GitOntology(gitOntology, historyOntology, mainOntology);
        for (Path sourcePath : sourcePaths) {
            this.scanner = new GitRepositoryScanner(sourcePath.toString());
            try {
                addRepository();
                addCommitsToRepository(scanner.findCommits());
                addBranchesToRepository(scanner.findBranches());
                addTagsToRepository(scanner.findTags());
                createFilesBasedOnMaxDates();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        return ontClassesAndProperties.getFinalModel();
    }

    private void addTagsToRepository(List<GitTag> tags) {

        for (GitTag tag : tags) {
            Individual tagIndividual =
                    ontClassesAndProperties.createIndividual(
                            ontClassesAndProperties.getOntologyNamespace() + "Tag_" + tagId++,
                            ontClassesAndProperties.gitTagClass());
            gitRepository.addProperty(ontClassesAndProperties.hasTagProperty(), tagIndividual);
            String label = tag.getLabel();
            label = label.replaceFirst("refs/tags/", "");
            String sha = tag.getCommitSha();

            tagIndividual.addLiteral(ontClassesAndProperties.hasLabelProperty(), label);
            tagIndividual.addProperty(
                    ontClassesAndProperties.atCommitProperty(), gitCommits.get(sha));
        }
    }

    private void addBranchesToRepository(List<GitBranch> branches) {
        for (GitBranch branch : branches) {
            Individual branchIndividual =
                    ontClassesAndProperties.createIndividual(
                            ontClassesAndProperties.getOntologyNamespace() + "Branch_" + branchId++,
                            ontClassesAndProperties.gitBranchClass());
            gitRepository.addProperty(
                    ontClassesAndProperties.hasBranchProperty(), branchIndividual);
            String name = branch.getName();
            String sha = branch.getCommitSha();
            Individual commit = gitCommits.get(sha);
            if (commit != null) {
                branchIndividual.addLiteral(
                        ontClassesAndProperties.hasNameProperty(), name.replaceFirst("refs/", ""));
                branchIndividual.addProperty(ontClassesAndProperties.hasHeadProperty(), commit);
            }
        }
    }

    private void addCommitsToRepository(List<GitCommit> findCommits) {
        for (GitCommit gitCommit : findCommits) {
            Individual commitIndividual =
                    ontClassesAndProperties.createIndividual(
                            ontClassesAndProperties.getOntologyNamespace() + "Commit_" + commitID,
                            ontClassesAndProperties.gitCommitClass());
            commitID++;
            gitRepository.addProperty(
                    ontClassesAndProperties.hasCommitProperty(), commitIndividual);

            // Set commit properties
            Individual author = gitAuthors.get(gitCommit.getAuthor());
            if (author == null) {
                author =
                        ontClassesAndProperties.createIndividual(
                                ontClassesAndProperties.getOntologyNamespace()
                                        + "Author_"
                                        + authorID++,
                                ontClassesAndProperties.gitAuthor());
                author.addLiteral(
                        ontClassesAndProperties.hasNameProperty(),
                        gitCommit
                                .getAuthor()
                                .substring(0, gitCommit.getAuthor().indexOf("<"))
                                .trim());
                author.addLiteral(
                        ontClassesAndProperties.hasEmailAdressProperty(),
                        gitCommit
                                .getAuthor()
                                .substring(
                                        gitCommit.getAuthor().indexOf("<") + 1,
                                        gitCommit.getAuthor().indexOf(">"))
                                .trim());
                commitIndividual.addProperty(ontClassesAndProperties.hasAuthorProperty(), author);
                author.addProperty(
                        ontClassesAndProperties.performsCommitProperty(), commitIndividual);
                gitRepository.addProperty(ontClassesAndProperties.hasAuthorProperty(), author);
                gitAuthors.put(gitCommit.getAuthor(), author);
            }

            author = gitAuthors.get(gitCommit.getAuthor());

            Individual committer =
                    ontClassesAndProperties.createIndividual(
                            ontClassesAndProperties.getOntologyNamespace()
                                    + "Committer_"
                                    + committerID++,
                            ontClassesAndProperties.gitCommitter());
            committer.addLiteral(
                    ontClassesAndProperties.hasNameProperty(), gitCommit.getCommitter());
            commitIndividual.addProperty(ontClassesAndProperties.hasCommitterProperty(), committer);

            commitIndividual.addLiteral(
                    ontClassesAndProperties.hasSHAProperty(), gitCommit.getSha());
            commitIndividual.addLiteral(
                    ontClassesAndProperties.hasMessageProperty(), gitCommit.getMessage());
            commitIndividual.addLiteral(
                    ontClassesAndProperties.hasShortMessageProperty(), gitCommit.getShortMessage());

            commitIndividual.addLiteral(
                    ontClassesAndProperties.hasCommitDateProperty(),
                    gitCommit.getDate().toString());

            addCommitFiles(gitCommit, commitIndividual);

            gitCommits.put(gitCommit.getSha(), commitIndividual);
        }

        for (GitCommit gitCommit : findCommits) {
            addCommitParents(gitCommit.getParents(), gitCommits.get(gitCommit.getSha()));
        }
    }

    private void addCommitParents(List<GitCommit> parents, Individual commitIndividual) {
        for (GitCommit parent : parents) {
            String sha = parent.getSha();
            Individual parentIndividual = gitCommits.get(sha);
            if (parentIndividual == null) {
                System.out.println("Parent not found...");
            } else {
                commitIndividual.addProperty(
                        ontClassesAndProperties.hasParentProperty(), parentIndividual);
            }
        }
    }

    private void addCommitFiles(GitCommit gitCommit, Individual commitIndividual) {
        for (GitChange gitChange : gitCommit.getGitChanges()) {
            addGitFileToCommit(gitChange, gitCommit, commitIndividual);
        }
    }

    private void addGitFileToCommit(
            GitChange gitChange, GitCommit gitCommit, Individual commitIndividual) {

        compareChangeDateToMaxDate(gitChange, gitCommit);
    }

    private void createFilesBasedOnMaxDates() {
        for (Entry<String, Date> entry : gitFileMaxDates.entrySet()) {
            Individual file =
                    MainOntology.MainOntClasses.SoftwareArtifactFile.create(
                            ontClassesAndProperties.mainModel().getOntology(), entry.getKey());
            file.addLiteral(
                    ontClassesAndProperties.lastModificationOnProperty(),
                    entry.getValue().toString());
            Individual historyVersion =
                    ontClassesAndProperties.createIndividual(
                            ontClassesAndProperties.getOntologyNamespace()
                                    + "version_"
                                    + versionId++,
                            ontClassesAndProperties.historyVersion());

            historyVersion.addLiteral(ontClassesAndProperties.hasVersionNumberProperty(), 1);

            file.addProperty(ontClassesAndProperties.hasVersionProperty(), historyVersion);
        }
    }

    private void compareChangeDateToMaxDate(GitChange gitChange, GitCommit gitCommit) {

        if (gitFileMaxDates.get(transformPathForFamixPath(gitChange.getRelativePath())) == null) {
            if (gitChange.getRelativePath().contains("src/")) {
                gitFileMaxDates.put(
                        transformPathForFamixPath(gitChange.getRelativePath()),
                        gitCommit.getDate());
            }
        } else if (gitFileMaxDates
                .get(transformPathForFamixPath(gitChange.getRelativePath()))
                .before(gitCommit.getDate())) {
            gitFileMaxDates.put(
                    transformPathForFamixPath(gitChange.getRelativePath()), gitCommit.getDate());
        }
    }

    private String transformPathForFamixPath(String path) {

        return Paths.get(scanner.getPath()).getParent() + "\\" + path.replace("/", "\\");
    }

    private void addRepository() {
        try {
            GitBranch head = scanner.findHead();

            gitRepository =
                    ontClassesAndProperties.createIndividual(
                            ontClassesAndProperties.getOntologyNamespace() + "GitRepository_",
                            ontClassesAndProperties.gitRepositoryClass());

            // Head of Repository
            Individual iHead =
                    ontClassesAndProperties.createIndividual(
                            ontClassesAndProperties.getOntologyNamespace() + "HEAD",
                            ontClassesAndProperties.gitBranchClass());
            iHead.addLiteral(ontClassesAndProperties.hasNameProperty(), head.getName());
            iHead.addLiteral(ontClassesAndProperties.hasSHAProperty(), head.getCommitSha());
            // add Head to repository
            gitRepository.addProperty(ontClassesAndProperties.hasHeadProperty(), iHead);

        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    private List<Path> scanForGitFile(List<Path> sourcePaths) {
        List<Path> result = new ArrayList<>();
        for (Path path : sourcePaths) {
            boolean gitFileExists = false;
            while (!gitFileExists && path != null) {
                Path gitPath = Paths.get(path + "\\.git");
                if (gitPath.toFile().exists()) {
                    gitFileExists = true;
                    result.add(gitPath);
                }
                path = path.getParent();
            }
        }
        if (result.isEmpty()) {
            LOG.warn("Unable to parse Git File from given paths. Will not create git ontology.");
        }
        return result;
    }

    @Override
    public Map<String, String> getProvidedNamespaces() {
        HashMap<String, String> res = new HashMap<>();
        res.put("git", GitOntology.GIT_PREFIX);
        res.put("main", GitOntology.MAIN_PREFIX);
        res.put("history", GitOntology.HISTORY_PREFIX);

        return res;
    }
}
