package gitinternals

import gitinternals.command.CatFileCommand
import gitinternals.command.CommitTreeCommand
import gitinternals.command.ListBranchesCommand
import gitinternals.command.LogCommand

fun main() {
    println("Enter .git directory location:")
    val directory = readln()
    val gitRepository = GitRepository(directory)

    println("Enter command:")
    val command = when (val commandName = readln().trim()) {
        "list-branches" -> ListBranchesCommand(gitRepository)
        "cat-file" -> CatFileCommand(gitRepository)
        "log" -> LogCommand(gitRepository)
        "commit-tree" -> CommitTreeCommand(gitRepository)
        else -> {
            println("Invalid command: $commandName")
            return
        }
    }

    command.execute()
}
