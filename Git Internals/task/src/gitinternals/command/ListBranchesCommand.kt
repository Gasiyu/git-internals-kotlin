package gitinternals.command

import gitinternals.GitRepository

class ListBranchesCommand(
    private val gitRepository: GitRepository
) : Command {
    override fun execute() {
        val branches = gitRepository.listBranches()
        val activeBranch = gitRepository.getActiveBranch()

        branches.forEach { branch ->
            if (branch == activeBranch) {
                println("* $branch")
            } else {
                println("  $branch")
            }
        }
    }
}
