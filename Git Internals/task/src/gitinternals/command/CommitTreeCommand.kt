package gitinternals.command

import gitinternals.GitRepository
import gitinternals.objects.Blob
import gitinternals.objects.Commit
import gitinternals.objects.Tree

class CommitTreeCommand(
    private val gitRepository: GitRepository
) : Command {
    override fun execute() {
        println("Enter commit-hash:")
        val commitHash = readln().trim()
        val gitObject = gitRepository.getObject(commitHash)

        if (gitObject is Commit) {
            val treeHash = gitObject.getTree()
            visit(treeHash)
        }
    }

    private fun visit(hash: String, parent: String = "") {
        val tree = gitRepository.getObject(hash) as Tree
        val files = tree.files

        files.forEach { (hash, name) ->
            val affix = if (parent.isEmpty()) "" else "$parent/"
            val gitObject = gitRepository.getObject(hash)

            if (gitObject is Blob) println("$affix$name")
            if (gitObject is Tree) visit(hash, name)
        }
    }
}
