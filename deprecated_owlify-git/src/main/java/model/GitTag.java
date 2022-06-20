package model;

public class GitTag {

	private String label;
	private String commitSha;

	public GitTag(String label, String commitSha) {

		this.label = label;

		this.commitSha = commitSha;

	}

	public String getLabel() {

		return label;

	}

	public String getCommitSha() {

		return commitSha;

	}

}
