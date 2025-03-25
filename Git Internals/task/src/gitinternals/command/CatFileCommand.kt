package gitinternals.command

import gitinternals.GitRepository
import java.io.FileNotFoundException

class CatFileCommand(
    private val gitRepository: GitRepository
) : Command {
    override fun execute() {
        println("Enter git object hash:")
        val objectHash = readln()

        try {
            val gitObject = gitRepository.getObject(objectHash)
            println(gitObject)
        } catch (e: FileNotFoundException) {
            println("File not found: $e")
        } catch (e: Exception) {
            println("Something went wrong: ${e.message}")
        }
    }
}
