package org.archcnl.kotlinparser.parser;

import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.PathMatcher;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class FileVisitor extends SimpleFileVisitor<Path> {
	private static final Logger LOG = LogManager.getLogger(FileVisitor.class);
	private final PathMatcher matcher = FileSystems.getDefault().getPathMatcher("glob:*.kt");

	private void parse(Path file, String fileName) {
		try {
			var content = Files.readString(file);
			var parser = new KtParser();
			LOG.debug("Parsing File: {}", fileName);
			parser.parse(content);
		} catch (IOException e) {
			LOG.error("Could not read file {}, exception {}", fileName, e);
			e.printStackTrace();
		}
	}

	private void verifyFileExtension(Path file) {
		var name = file.getFileName();
		if (name != null && matcher.matches(name)) {
			parse(file, name.toString());
		}
	}

	@Override
	public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
		verifyFileExtension(file);
		return FileVisitResult.CONTINUE;
	}

	@Override
	public FileVisitResult visitFileFailed(Path file, IOException exc) throws IOException {
		LOG.error("Error visiting file {}, exception is {}", file, exc);
		return FileVisitResult.CONTINUE;
	}
}
