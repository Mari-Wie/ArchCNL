# Guideline for Contributing 

Welcome and thanks for contributing! 

This document contains guidelines for contributing to ArchCNL. They are a guidance to follow according to your own judgement, not rules to obey in all circumstances. 
Changes to this document can be proposed at any time (via a pull request).  

## Table of Contents

- [General Remarks](#general-remarks)
- [Opening Issues](#opening-issues)
- [Closing Issues](#closing-issues)
- [Pull Requests](#pull-requests)
- [Documentation](#documentation)
- [Community](#community)

## General Remarks

- The [Wiki](https://github.com/Mari-Wie/ArchCNL/wiki) contains useful information. Questions like how to start ArchCNL, how to build it, what the different packages do, etc. are answered there. Naming conventions and design decisions are also documented there. 
- The master should *always* be runnable. Work in branches and create a pull request when you are done.
- When you want to work on something, assign yourself to the corresponding issue. If someone else is already working on it, ask them whether it is OK when you help out. If no existing issue covers your problem or feature, open a new one and assign yourself to it. This allows everyone to keep track of who is working on what. Wiki pages can be edited without opening  an issue. 

## Opening Issues

Note: Try to kepp issues fine-grained and small. This leads to fewer changes in the corresponding pull requests and thus less merge conflicts. Large issues can be implemented by several smaller sub-issues as long as each increment results in a runnable software (basic idea for CI/CD).

1. Make sure that no exisiting issue covers the same problem.
2. Find a brief yet meaningful title.
3. Further describe the problem in the description. Other people should be able to understand
  - what the problem is,
  - why it is relevant, 
  - which parts of the project (code or documentation, when code is affected which module?), and 
  - whether there are a proposed solution, helpful links, etc.
4. If you want to work on the issue yourself *right now*, assign yourself to it.  
5. Add useful labels. These labels are in use as of 2021-03-04:
  
  | Label | Description |
  | ----- | ----------- |
  | bachelor thesis | This issue is suitable for a bachelor thesis* |
  | bug | Something is not working as specified |
  | case study/research | Proposal for further research* |
  | dissertation | This issue is suitable for an PHD student* |
  | documentation | Something must be documented properly |
  | duplicate | This issue addresses the same problem as another one |
  | enhancement | An opportunity to improve ArchCNL by adding a **new** feature |
  | master project | This issue is suitable for a master project* |
  | master thesis | This issue is suitable for a master thesis* |
  | project management | The issue addresses an organizational problem |
  | restructuring/improvement | An **existing** feature should be improved (in quality)** |
  
  \*: ArchCNL is currently being developed by the SWK working group of the department of informatics from the university of Hamburg, Germany. These labels are used for internal organization. Please do not use or change them if you are not a member of SWK.   
  \*\*: Refactorings, reengineerings, and optimisations are typical examples.
  

## Closing Issues

Issues can be closed when

- the problem is solved, e.g. when an associated pull request solving the problem has benn merged into master, when the wiki has been updated, etc.
- the issue is a duplicate of another issue or concerns a nonexistent problem. Communicate (preferably in the comment section) with the person who opened the issue before doing so to verify that the problem is indeed duplicate/nonexistent.  
- the problem is considered not (realistically) solveable after thorough examination. Write the reasons into the issue's comment section. The issue should only be closed with the approbal of at least one other contributor.     

## Pull Requests

- Work in a feature branch. Your branch should have a concise, unique, and meaningful name. Examples are `feature/feature-name`, `bugfix/bug-description`, and `refactor/some-class-or-module`.
- When you work is done (see below), push your branch (if you have not done so already) and create a pull request.
- Add a meaningful title and brief description. When your branch closes the issue number *X*, write `Closes #X` into the description. This tells Github to close the issue when the pull request is merged. If the issue is already describing the problem, the pull request does not have to contain a redundant description.  
- When enough other contributors are around (e.g. because a master project is currently running *hint* *hint*), let someone else review your changes. Incorporate your reviewer's feedback and inform him/her when you are done. The pull request is merged by the reviewer.   

### Definition of Done

Pull requests are only eligable for merging when

- the addressed issue is solved by the code,
- no bugs are introduced,
- the pipeline is "green", i.e. all tests are passing,
- new code is covered by tests in a reasonable way (100% code coverage is not necessary, but important parts should be tested),
- no significant decrease in code quality is introduced ("leave the code a bit cleaner than you found it"),
- the documentation is updated when necessary. The wiki should only be updated once the pull request is merged.

### Review Process

Reviewers have to check whether the definition of done is complied with. Do not look only at functional correctness but also at other code quality attributes. These questions can be used as a guidance:

- Is the documentation up-to-date? 
- Have new classes, methods, variables, etc. meaningful names? 
- When an existing class/method/... was modified without changing its name, is the old name still fitting? 
- Are the tests reasonable (and present)?
- Are new code smells introduced?
- Are common pitfalls (e.g. null pointers, missing files, ...) present?
- Is error handling present?
- Are the naming conventions, logging conventions, ... (see wiki) complied with? 
- ...

## Documentation

ArchCNL's documentation is split three-fold:

1. Java and XML comments in the code, ontologies, pom files, log4j configuration files, etc. These comments should point out important things directly where it matters. (Simplified) example:
```Java
public void someMethod(int importantParameter) {
  // PoorlyWrittenLibrary has problems with integers, but work fine with longs
  // Thus, the cast is necessary
  long someValue = objectFromLibrary.poorlyWrittenMethod((long) importantParameter);
}
```
2. Javadoc comments. They are used to document the interfaces of Java interfaces, classes, methods, etc. Again a simplified example:
```Java
/**
 *
 */
public interface FancyParser {
  /**
   * Parses the given fancy file, extracts rules and returns a list of the identified rules. 
   *
   * @param pathToFile Absolute path to the file to parse. The file must a written in the rule language FancyLang.
   * @return The list of FancyLang rules defined in the input file. 
   * @throws FileNotFoundException when the given input file cannot be accessed
   * @throws HorribleException when the file does not contain valid FancyLang rules.
   */
  public List<Rule> parseRuleFile(String pathToFile);
}
```
3. The wiki. The wiki contains a user guide and a developer guide. Everything related to starting, building, and using ArchCNL goes into the user guide. Everything related to design and architecture, code conventions, and other "high-level" documentation intended for developers goes into the developer guide.

## Community

- Please be a decent human being and behave yourself (stay polite, friendly, etc, do **not** be racist, sexist, something-ist)
