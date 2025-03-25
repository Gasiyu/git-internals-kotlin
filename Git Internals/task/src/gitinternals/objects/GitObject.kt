package gitinternals.objects

abstract class GitObject {

    abstract fun parse(data: ByteArray)
    abstract override fun toString(): String

    companion object {
        fun create(hash: String, data: ByteArray): GitObject {
            return when (val objectType = String(data.copyOfRange(0, data.indexOf(' '.code.toByte())))) {
                "blob" -> Blob(data)
                "commit" -> Commit(hash, data)
                "tree" -> Tree(data)
                else -> throw IllegalArgumentException("Unknown object type: $objectType")
            }
        }
    }
}
