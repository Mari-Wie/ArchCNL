package model;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

public class GitCommit {
	
	private String author;
	private String committer;
	private Date date;
	private String message;
	private String shortMessage;
	private String encoding;
	private String sha;
	
	private final List<GitCommit> parents;
	private final List<GitChange> changes;
	
	public GitCommit(String sha) {
		this.sha = sha;
		this.parents = new LinkedList<GitCommit>();
		this.changes = new LinkedList<GitChange>();
	}
	
	public String getAuthor() {
		return author;
	}
	public void setAuthor(String author) {
		this.author = author;
	}
	public String getCommitter() {
		return committer;
	}
	public void setCommitter(String committer) {
		this.committer = committer;
	}
	
	//TODO better: use Datetime
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public String getShortMessage() {
		return shortMessage;
	}
	public void setShortMessage(String shortMessage) {
		this.shortMessage = shortMessage;
	}
	public String getEncoding() {
		return encoding;
	}
	public void setEncoding(String encoding) {
		this.encoding = encoding;
	}

	public List<GitChange> getGitChanges() {
		return this.changes;
	}
	
	public List<GitCommit> getParents() {
		return parents;
	}

	public String getSha() {
		return sha;
	}

}
