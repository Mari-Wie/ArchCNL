package model;

public class GitBranch {

	private String name;
	private String commitSha;

	public GitBranch(String name, String commitSha) {
		this.name = name;
		this.commitSha = commitSha;
	}

	public String getName() {
		return name;
	}

	public String getCommitSha() {
		return commitSha;
	}

}
