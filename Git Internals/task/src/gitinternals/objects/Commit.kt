package gitinternals.objects

import gitinternals.model.PersonInfo

class Commit(private val hash: String, data: ByteArray) : GitObject() {
    private var tree: String = ""
    private var parents: List<String> = emptyList()
    private var author: PersonInfo? = null
    private var committer: PersonInfo? = null
    private var message: String = ""

    init {
        parse(data)
    }

    fun getParents(): List<String> = parents
    fun getCommitter(): PersonInfo? = committer
    fun getMessage(): String = message
    fun getHash(): String = hash
    fun getTree(): String = tree

    override fun parse(data: ByteArray) {
        val content = data.copyOfRange(data.indexOf(0) + 1, data.size).decodeToString()
        val lines = content.split("\n").filter { it.isNotBlank() }
        var messageStartIndex = 0

        for ((index, line) in lines.withIndex()) {
            when {
                line.startsWith("tree ") -> tree = line.substringAfter("tree ")
                line.startsWith("parent ") -> {
                    val parentHashes = lines.filter { it.startsWith("parent ") }
                        .map { it.substringAfter("parent ") }
                    parents = parentHashes
                }

                line.startsWith("author ") -> author = PersonInfo.parse(line.substringAfter("author "))
                line.startsWith("committer ") -> {
                    committer = PersonInfo.parse(line.substringAfter("committer "))
                    messageStartIndex = index + 1
                    break
                }
            }
        }

        if (messageStartIndex < lines.size) {
            message = lines.subList(messageStartIndex, lines.size).joinToString("\n")
        }
    }

    override fun toString(): String {
        val sb = StringBuilder()
        sb.appendLine("*COMMIT*")
        sb.appendLine("tree: $tree")

        if (parents.isNotEmpty()) {
            sb.appendLine("parents: ${parents.joinToString(" | ")}")
        }

        author?.let {
            sb.append("author: ${it.name} ${it.email} original ")
            sb.appendLine("timestamp: ${it.formatTimestamp()}")
        }

        committer?.let {
            sb.append("committer: ${it.name} ${it.email} commit ")
            sb.appendLine("timestamp: ${it.formatTimestamp()}")
        }

        sb.appendLine("commit message:")
        sb.append(message)

        return sb.toString()
    }
}
