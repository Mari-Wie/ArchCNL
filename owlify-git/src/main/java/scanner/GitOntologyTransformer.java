package scanner;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.jena.ontology.Individual;
//import org.eclipse.jgit.lib.Repository;

import core.AbstractOwlifyComponent;
//import core.OwlifyComponent;
import model.GitBranch;
import model.GitChange;
import model.GitCommit;
import model.GitTag;
import ontology.GitOntClassesAndProperties;

public class GitOntologyTransformer extends AbstractOwlifyComponent {

	private GitRepositoryScanner scanner;
	private GitOntClassesAndProperties ontClassesAndProperties;

	private Individual gitRepository;

	private static int changeId = 0;
	private static int fileId;
	private static int committerID = 0;
	private static int authorID = 0;
	private static int commitID;
	private static int branchId;

	private Map<String, Individual> gitFiles;
	private Map<String, Individual> gitCommits;
	private Map<String, Individual> gitAuthors;
	private static int tagId;

	public GitOntologyTransformer() {
		super("./git_result.owl");
		ontClassesAndProperties = new GitOntClassesAndProperties();
		gitFiles = new HashMap<>();
		gitCommits = new HashMap<>();
		gitAuthors = new HashMap<>();
	}
	
	@Override
	public void addSourcePath(String path) {
		scanner = new GitRepositoryScanner(path);
	}

	@Override
	public void transform() {
		addRepository();
		addCommitsToRepository(scanner.findCommits());
		try {
			addBranchesToRepository(scanner.findBranches());
			addTagsToRepository(scanner.findTags());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
//		addAuthorsToRepository();

		ontClassesAndProperties.writeOntology(super.getResultPath());
	}

	private void addTagsToRepository(List<GitTag> tags) {

		for (GitTag tag : tags) {
			Individual tagIndividual = ontClassesAndProperties.createIndividual(
					ontClassesAndProperties.getOntologyNamespace() + "Tag_" + tagId++,
					ontClassesAndProperties.gitTagClass());
			gitRepository.addProperty(ontClassesAndProperties.hasTagProperty(), tagIndividual);
			String label = tag.getLabel();
			label = label.replaceFirst("refs/tags/", "");
			String sha = tag.getCommitSha();

			tagIndividual.addLiteral(ontClassesAndProperties.hasLabelProperty(), label);
			tagIndividual.addProperty(ontClassesAndProperties.atCommitProperty(), gitCommits.get(sha));
		}

	}

	private void addBranchesToRepository(List<GitBranch> branches) {
		for (GitBranch branch : branches) {
			Individual branchIndividual = ontClassesAndProperties.createIndividual(
					ontClassesAndProperties.getOntologyNamespace() + "Branch_" + branchId++,
					ontClassesAndProperties.gitBranchClass());
			gitRepository.addProperty(ontClassesAndProperties.hasBranchProperty(), branchIndividual);
			String name = branch.getName();
			String sha = branch.getCommitSha();
			Individual commit = gitCommits.get(sha);

			branchIndividual.addLiteral(ontClassesAndProperties.hasNameProperty(), name.replaceFirst("refs/", ""));
			branchIndividual.addProperty(ontClassesAndProperties.hasHeadProperty(), commit);

		}
	}

	private void addCommitsToRepository(List<GitCommit> findCommits) {
		for (GitCommit gitCommit : findCommits) {
			Individual commitIndividual = ontClassesAndProperties.createIndividual(
					ontClassesAndProperties.getOntologyNamespace() + "Commit_" + commitID,
					ontClassesAndProperties.gitCommitClass());
			commitID++;
			gitRepository.addProperty(ontClassesAndProperties.hasCommitProperty(), commitIndividual);

			// Set commit properties
			Individual author = gitAuthors.get(gitCommit.getAuthor());
			if (author == null) {
				author = ontClassesAndProperties.createIndividual(
						ontClassesAndProperties.getOntologyNamespace() + "Author_" + authorID++,
						ontClassesAndProperties.gitAuthor());
//				gitAuthor.setName(author.substring(0, author.indexOf("<")).trim());
//
//                gitAuthor.setEmail(author.substring(author.indexOf("<")+1, author.indexOf(">")).trim());
				author.addLiteral(ontClassesAndProperties.hasNameProperty(), gitCommit.getAuthor().substring(0, gitCommit.getAuthor().indexOf("<")).trim());
				author.addLiteral(ontClassesAndProperties.hasEmailAdressProperty(), gitCommit.getAuthor().substring(gitCommit.getAuthor().indexOf("<")+1, gitCommit.getAuthor().indexOf(">")).trim());
				commitIndividual.addProperty(ontClassesAndProperties.hasAuthorProperty(), author);
				author.addProperty(ontClassesAndProperties.performsCommitProperty(), commitIndividual);
				gitRepository.addProperty(ontClassesAndProperties.hasAuthorProperty(), author);
				gitAuthors.put(gitCommit.getAuthor(), author);
			}
			
			author = gitAuthors.get(gitCommit.getAuthor());
			
			Individual committer = ontClassesAndProperties.createIndividual(
					ontClassesAndProperties.getOntologyNamespace() + "Committer_" + committerID++,
					ontClassesAndProperties.gitCommitter());
			committer.addLiteral(ontClassesAndProperties.hasNameProperty(), gitCommit.getCommitter());
			commitIndividual.addProperty(ontClassesAndProperties.hasCommitterProperty(), committer);

			commitIndividual.addLiteral(ontClassesAndProperties.hasSHAProperty(), gitCommit.getSha());
			commitIndividual.addLiteral(ontClassesAndProperties.hasMessageProperty(), gitCommit.getMessage());
			commitIndividual.addLiteral(ontClassesAndProperties.hasShortMessageProperty(), gitCommit.getShortMessage());

			commitIndividual.addLiteral(ontClassesAndProperties.hasCommitDateProperty(),
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
				commitIndividual.addProperty(ontClassesAndProperties.hasParentProperty(), parentIndividual);
			}
		}
	}

	private void addCommitFiles(GitCommit gitCommit, Individual commitIndividual) {
		for (GitChange gitChange : gitCommit.getGitChanges()) {
			// Git change individual
			Individual change = ontClassesAndProperties.createIndividual(
					ontClassesAndProperties.getOntologyNamespace() + "Change_" + changeId++,
					ontClassesAndProperties.gitChangeClass());
			// set modification kind for git change
			change.addProperty(ontClassesAndProperties.hasModificationKindProperty(),
					ontClassesAndProperties.getModificationKindIndividual(gitChange.getModificationKind()));
			// file under version control, file individual

			addGitFileToCommit(gitChange, change, gitCommit, commitIndividual);
		}
	}

	private void addGitFileToCommit(GitChange gitChange, Individual change, GitCommit gitCommit,
			Individual commitIndividual) {

		Individual file = getOrCreateFile(gitChange);

		if (gitChange.getModificationKind().equals("A")) { // add change
			file.addLiteral(ontClassesAndProperties.isCreatedOnProperty(), gitCommit.getDate().toString());
			change.addProperty(ontClassesAndProperties.createsFileProperty(), file);
		} else if (gitChange.getModificationKind().equals("C")) {
			Individual oldFile = gitFiles.get(gitChange.getOldPath());
			Individual newFile = gitFiles.get(gitChange.getNewPath());

			newFile.addProperty(ontClassesAndProperties.isNewCopyOfProperty(), oldFile);
			change.addProperty(ontClassesAndProperties.copiesFileProperty(), oldFile);
			change.addProperty(ontClassesAndProperties.createsFileProperty(), newFile);
			change.addLiteral(ontClassesAndProperties.copiedOnProperty(), gitCommit.getDate().toString());
		} else if (gitChange.getModificationKind().equals("D")) {
			file.addLiteral(ontClassesAndProperties.isDeletedOnProperty(), gitCommit.getDate().toString());
			change.addProperty(ontClassesAndProperties.deletesFileProperty(), file);
		} else if (gitChange.getModificationKind().equals("R")) {
			Individual oldFile = gitFiles.get(gitChange.getOldPath());
			Individual newFile = gitFiles.get(gitChange.getNewPath());

			oldFile.addProperty(ontClassesAndProperties.isRenamedToProperty(), newFile);

			change.addProperty(ontClassesAndProperties.renamesFileProperty(), oldFile);
			change.addProperty(ontClassesAndProperties.deletesFileProperty(), oldFile);
			change.addProperty(ontClassesAndProperties.createsFileProperty(), newFile);
			change.addProperty(ontClassesAndProperties.renamedOnProperty(), gitCommit.getDate().toString());
		} else if (gitChange.getModificationKind().equals("U")) {
			file.addLiteral(ontClassesAndProperties.lastModificationOnProperty(), gitCommit.getDate().toString());
			change.addProperty(ontClassesAndProperties.updatesFileProperty(), file);
		}

		change.addProperty(ontClassesAndProperties.modifiesFileProperty(), file);
	}

	private Individual getOrCreateFile(GitChange change) {

		if (gitFiles.get(change.getRelativePath()) == null) {
			Individual file = ontClassesAndProperties.createIndividual(
					ontClassesAndProperties.getOntologyNamespace() + "File_" + fileId++,
					ontClassesAndProperties.fileClass());
			file.addLiteral(ontClassesAndProperties.hasRelativePathProperty(), change.getRelativePath());
			gitFiles.put(change.getRelativePath(), file);
		}

		return gitFiles.get(change.getRelativePath());
	}

	private void addRepository() {
		try {
			GitBranch head = scanner.findHead();

			gitRepository = ontClassesAndProperties.createIndividual(
					ontClassesAndProperties.getOntologyNamespace() + "GitRepository_",
					ontClassesAndProperties.gitRepositoryClass());

			// Head of Repository
			Individual iHead = ontClassesAndProperties.createIndividual(
					ontClassesAndProperties.getOntologyNamespace() + "HEAD", ontClassesAndProperties.gitBranchClass());
			iHead.addLiteral(ontClassesAndProperties.hasNameProperty(), head.getName());
			iHead.addLiteral(ontClassesAndProperties.hasSHAProperty(), head.getCommitSha());
			// add Head to repository
			gitRepository.addProperty(ontClassesAndProperties.hasHeadProperty(), iHead);

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		GitOntologyTransformer transformer = new GitOntologyTransformer();
		transformer.addSourcePath("C:\\Users\\sandr\\Desktop\\test_repos\\cnl-toolchain\\.git");
		transformer.transform();
	}

	@Override
	public Map<String, String> getProvidedNamespaces() {
		// TODO Auto-generated method stub
		HashMap<String, String> res = new HashMap<>();
		res.put("git", ontClassesAndProperties.getOntologyNamespace());
		res.put("main", ontClassesAndProperties.getMainNamespace());
		res.put("history", ontClassesAndProperties.getHistoryNamespace());
		
		return res;
	}

	
}
