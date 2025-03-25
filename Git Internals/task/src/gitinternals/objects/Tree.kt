package gitinternals.objects

class Tree(data: ByteArray) : GitObject() {
    private var content: MutableList<String> = mutableListOf()
    val files: MutableList<Pair<String, String>> = mutableListOf()

    init {
        parse(data)
    }

    @OptIn(ExperimentalStdlibApi::class)
    override fun parse(data: ByteArray) {
        val treeData = data.copyOfRange(data.indexOf(0) + 1, data.size)

        var index = 0
        while (index < treeData.size) {
            val spaceIndex = findByteFrom(treeData, ' '.code.toByte(), index)
            if (spaceIndex == -1) break

            val permission = String(treeData.copyOfRange(index, spaceIndex))

            val nullIndex = findByteFrom(treeData, 0, spaceIndex + 1)
            if (nullIndex == -1) break

            val fileName = String(treeData.copyOfRange(spaceIndex + 1, nullIndex))

            val sha1hash = treeData.copyOfRange(nullIndex + 1, nullIndex + 21)
            val sha1hex = sha1hash.toHexString()

            files.add(sha1hex to fileName)
            content.add("$permission $sha1hex $fileName")
            index = nullIndex + 21
        }
    }

    private fun findByteFrom(array: ByteArray, byte: Byte, fromIndex: Int): Int {
        for (i in fromIndex until array.size) {
            if (array[i] == byte) {
                return i
            }
        }
        return -1
    }

    override fun toString(): String {
        return "*TREE*\n${content.joinToString("\n")}"
    }
}
