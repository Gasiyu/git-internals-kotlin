# Git Internals Explorer

A command-line tool for exploring Git internals, written in Kotlin. This program allows you to examine the internal structure of a Git repository, including branches, commits, trees, and blobs.


Git Internals Explorer provides a way to understand how Git works internally by exposing the underlying data structures. It reads directly from the `.git` directory of a repository and provides commands to:

- List all branches
- View Git objects (blobs, commits, trees)
- Display commit history
- Show the file structure of a commit


1. Clone this repository
2. Build the project using Gradle:
   ```
   ./gradlew build
   ```
3. Run the program:
   ```
   ./gradlew run
   ```

The greater-than symbol followed by a space > represents the user input. Note that it's not part of the input.


Lists all branches in the repository, with the active branch marked with an asterisk (*).

Example:
```
Enter .git directory location:
> /home/gasiyu/Projects/git-internals/.git
Enter command:
> list-branches
  feature1
  feature2
* master
```


Displays the contents of a Git object by its hash.

Example:
```
Enter .git directory location:
> /home/gasiyu/Projects/git-internals/.git
Enter command:
> cat-file
Enter git object hash:
> dcec4e51e2ce4a46a6206d0d4ab33fa99d8b1ab5
*COMMIT*
tree: d128f76a96c56ac4373717d3fbba4fa5875ca68f
parents: 5ad3239e54ba7c533d9f215a13ac82d14197cd8f | d2c5bedbb2c46945fd84f2ad209a7d4ee047f7f9
author: John jdoe@email.com original timestamp: 2021-12-11 22:49:02 -03:00
committer: John jdoe@email.com commit timestamp: 2021-12-11 22:49:02 -03:00
commit message:
awsome hello
```


Displays the commit history for a specified branch.

Example:
```
Enter .git directory location:
> /home/gasiyu/Projects/git-internals/.git
Enter command:
> log
Enter branch name:
> main
Commit: dcec4e51e2ce4a46a6206d0d4ab33fa99d8b1ab5
John jdoe@email.com commit timestamp: 2021-12-11 22:49:02 -03:00
awsome hello

Commit: d2c5bedbb2c46945fd84f2ad209a7d4ee047f7f9 (merged)
Gasiyu gasiyu@email.com commit timestamp: 2021-12-11 22:43:54 -03:00
hello of the champions

Commit: 5ad3239e54ba7c533d9f215a13ac82d14197cd8f
John jdoe@email.com commit timestamp: 2021-12-11 22:46:28 -03:00
maybe hello

Commit: 31cddcbd00e715688cd127ad20c2846f9ed98223
John jdoe@email.com commit timestamp: 2021-12-11 22:31:36 -03:00
simple hello
```


Displays the file structure of a commit.

Example:
```
Enter .git directory location:
> /home/gasiyu/Projects/git-internals/.git
Enter command:
> commit-tree
Enter commit-hash:
> fd362f3f305819d17b4359444aa83e17e7d6924a
README.md
src/main.kt
src/utils/helper.kt
```


Git Internals Explorer reads directly from the `.git` directory of a repository:

- Branches are stored in `.git/refs/heads/`
- The active branch is referenced in `.git/HEAD`
- Git objects (blobs, commits, trees) are stored in `.git/objects/`

The program decompresses and parses these objects to display their contents in a human-readable format.


Contributions are welcome! Please feel free to submit a Pull Request.


This project is open source and available under the [MIT License](LICENSE).
