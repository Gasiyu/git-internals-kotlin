package gitinternals

import gitinternals.objects.GitObject
import java.io.File
import java.io.FileInputStream
import java.io.FileNotFoundException
import java.util.zip.InflaterInputStream

class GitRepository(private val path: String) {

    fun listBranches(): List<String> {
        val branches = File("$path/refs/heads").list()?.toList() ?: emptyList()
        return branches.sorted()
    }

    fun getActiveBranch(): String {
        val head = File("$path/HEAD").readText()
        return head.substringAfter("refs/heads/").trim()
    }

    fun getBranchCommitHash(branchName: String): String {
        val branchFile = File("$path/refs/heads/$branchName")
        if (!branchFile.exists()) throw FileNotFoundException("Branch not found: $branchName")
        return branchFile.readText().substringAfter("refs/heads/").trim()
    }

    fun getObject(hash: String): GitObject {
        val file = FileInputStream("$path/objects/${hash.take(2)}/${hash.drop(2)}")
        return GitObject.create(hash, InflaterInputStream(file).readAllBytes())
    }
}
