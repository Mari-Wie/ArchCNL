package org.archcnl.owlify.git.ontology;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import org.archcnl.owlify.git.model.GitBranch;
import org.archcnl.owlify.git.model.GitChange;
import org.archcnl.owlify.git.model.GitCommit;
import org.archcnl.owlify.git.model.GitTag;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.ListBranchCommand;
import org.eclipse.jgit.api.LogCommand;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.api.errors.NoHeadException;
import org.eclipse.jgit.diff.DiffEntry;
import org.eclipse.jgit.diff.DiffFormatter;
import org.eclipse.jgit.diff.RawTextComparator;
import org.eclipse.jgit.errors.IncorrectObjectTypeException;
import org.eclipse.jgit.errors.MissingObjectException;
import org.eclipse.jgit.lib.Constants;
import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.lib.Ref;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.jgit.revwalk.RevWalk;
import org.eclipse.jgit.storage.file.FileRepositoryBuilder;
import org.eclipse.jgit.util.io.DisabledOutputStream;

public class GitRepositoryScanner {

    private String path;
    private Map<String, GitCommit> commits = new HashMap<>();

    public GitRepositoryScanner(String path) {
        this.path = path;
    }

    public String getPath() {
        return path;
    }

    public List<GitCommit> findCommits() {
        List<GitCommit> result = new LinkedList<GitCommit>();
        try {
            Repository repository = getRepository();
            ObjectId head = repository.resolve("HEAD");

            RevWalk rw = new RevWalk(repository);
            try (Git git = new Git(repository)) {
                LogCommand logCommand = git.log();
                Iterable<RevCommit> tmp = logCommand.call();

                DiffFormatter df = new DiffFormatter(DisabledOutputStream.INSTANCE);
                df.setRepository(repository);
                df.setDiffComparator(RawTextComparator.DEFAULT);
                df.setDetectRenames(true);

                for (RevCommit commit : tmp) {
                    final Date date = new Date(1000 * (long) commit.getCommitTime());

                    final GitCommit gitCommit = retrieveCommit(ObjectId.toString(commit.getId()));

                    gitCommit.setAuthor(
                            commit.getAuthorIdent().getName()
                                    + " <"
                                    + commit.getAuthorIdent().getEmailAddress()
                                    + ">");
                    gitCommit.setCommitter(
                            commit.getCommitterIdent().getName()
                                    + " <"
                                    + commit.getAuthorIdent().getEmailAddress()
                                    + ">");
                    gitCommit.setDate(date);
                    gitCommit.setMessage(commit.getFullMessage());
                    gitCommit.setShortMessage(commit.getShortMessage());
                    gitCommit.setEncoding(commit.getEncodingName());
                    addCommitParents(rw, df, commit, gitCommit);

                    result.add(gitCommit);
                }

            } catch (NoHeadException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (GitAPIException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } finally {

                rw.close();

                repository.close();
            }
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return result;
    }

    public List<GitBranch> findBranches() throws IOException {

        Repository repository = getRepository();
        List<GitBranch> result = new LinkedList<>();

        try (Git git = new Git(repository)) {
            List<Ref> branches =
                    git.branchList().setListMode(ListBranchCommand.ListMode.ALL).call();
            for (Ref branchRef : branches) {
                GitBranch newBranch =
                        new GitBranch(
                                branchRef.getName(), ObjectId.toString(branchRef.getObjectId()));
                result.add(newBranch);
            }

        } catch (GitAPIException e) {
            throw new IllegalStateException(
                    "Could not read branches from Git repository '" + path + "'", e);
        } finally {
            repository.close();
        }
        return result;
    }

    private void addCommitParents(
            RevWalk rw, DiffFormatter df, RevCommit revCommit, GitCommit gitCommit)
            throws MissingObjectException, IncorrectObjectTypeException, IOException {

        for (int i = 0; i < revCommit.getParentCount(); i++) {
            ObjectId parentId = revCommit.getParent(i).getId();
            RevCommit parent = rw.parseCommit(parentId);
            List<DiffEntry> diffs = df.scan(parent.getTree(), revCommit.getTree());

            for (DiffEntry diff : diffs) {
                final GitChange gitChange =
                        new GitChange(
                                diff.getChangeType().name(), diff.getOldPath(), diff.getNewPath());

                gitCommit.getGitChanges().add(gitChange);
            }
            String parentSha = ObjectId.toString(parentId);
            final GitCommit parentCommit = retrieveCommit(parentSha);
            gitCommit.getParents().add(parentCommit);
        }
    }

    public List<GitTag> findTags() throws IOException {

        Repository repository = getRepository();
        List<GitTag> result = new LinkedList<>();

        try (Git git = new Git(repository)) {
            List<Ref> tags = git.tagList().call();
            for (Ref tagRef : tags) {
                String label = tagRef.getName();
                RevCommit firstCommit = resolveFirstCommitForTag(git, tagRef);
                String objectId = ObjectId.toString(firstCommit);
                GitTag newTag = new GitTag(label, objectId);
                result.add(newTag);
            }
        } catch (GitAPIException e) {
            throw new IllegalStateException(
                    "Could not read tags from Git repository '" + path + "'", e);
        } finally {
            repository.close();
        }

        return result;
    }

    private RevCommit resolveFirstCommitForTag(Git git, Ref tagRef)
            throws MissingObjectException, IncorrectObjectTypeException, NoHeadException,
                    GitAPIException {
        LogCommand log = git.log();
        Ref peeledRef = git.getRepository().peel(tagRef);
        if (peeledRef.getPeeledObjectId() != null) {
            log.add(peeledRef.getPeeledObjectId());
        } else {
            log.add(tagRef.getObjectId());
        }
        Iterable<RevCommit> logs = log.call();
        return logs.iterator().next();
    }

    private GitCommit retrieveCommit(String sha) {
        if (!commits.containsKey(sha)) {
            commits.put(sha, new GitCommit(sha));
        }
        return commits.get(sha);
    }

    Repository getRepository() throws IOException {
        FileRepositoryBuilder builder = new FileRepositoryBuilder();
        Repository repository =
                builder.setGitDir(new File(path))
                        .readEnvironment()
                        .build(); // scan environment GIT_*
        // variables
        return repository;
    }

    GitBranch findHead() throws IOException {
        ObjectId head = getRepository().resolve(Constants.HEAD);
        return new GitBranch(Constants.HEAD, ObjectId.toString(head));
    }
}
