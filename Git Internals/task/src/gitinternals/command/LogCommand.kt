package gitinternals.command

import gitinternals.GitRepository
import gitinternals.objects.Commit
import java.io.FileNotFoundException

class LogCommand(
    private val gitRepository: GitRepository
) : Command {
    override fun execute() {
        println("Enter branch name:")
        val branchName = readln().trim()

        try {
            val headCommitHash = gitRepository.getBranchCommitHash(branchName)
            visit(headCommitHash)
        } catch (e: FileNotFoundException) {
            println(e.message)
        } catch (e: Exception) {
            println("Something went wrong: ${e.message}")
        }
    }

    private fun visit(commitHash: String, visitParent: Boolean = true, isMerged: Boolean = false) {
        val gitObject = gitRepository.getObject(commitHash)

        if (gitObject is Commit) {
            printCommit(gitObject, isMerged)

            val parents = gitObject.getParents()
            if (parents.isNotEmpty() && visitParent) {
                if (parents.size > 1) {
                    visit(parents.last(), visitParent = false, isMerged = true)
                }
                visit(parents.first())
            }
        }
    }

    private fun printCommit(commit: Commit, isMerged: Boolean) {
        val suffix = if (isMerged) " (merged)" else ""

        println("Commit: ${commit.getHash()}$suffix")
        commit.getCommitter()?.let { committer ->
            println("${committer.name} ${committer.email} commit timestamp: ${committer.formatTimestamp()}")
        }
        println(commit.getMessage())
        println()
    }
}
