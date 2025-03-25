package gitinternals.objects

class Blob(data: ByteArray) : GitObject() {
    private var content: String = ""

    init {
        parse(data)
    }

    override fun parse(data: ByteArray) {
        this.content = data.copyOfRange(data.indexOf(0) + 1, data.size).decodeToString()
    }

    override fun toString(): String {
        return "*BLOB*\n$content"
    }
}
