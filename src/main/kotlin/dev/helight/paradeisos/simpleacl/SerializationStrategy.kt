package dev.helight.paradeisos.simpleacl

enum class SerializationStrategy {

    /**
     * Char List String Serialization
     *
     * r: Read
     * u, w: Update
     * c, w: Create
     * d, w: Delete
     * e, x: Execute
     * s: Special
     */
    CLS,

    /**
     * Binary String Serialization
     */
    Radix2,

    /**
     * Decimal String Serialization
     */
    Radix10,

    /**
     * Hexadecimal String Serialization
     */
    Radix16;

    fun serializeValue(permission: Int): String {
        return when(this) {
            CLS -> AclOperation.serializeCls(*AclOperation.parse(permission).toTypedArray())
            Radix2 -> permission.toString(2)
            Radix10 -> permission.toString(10)
            Radix16 -> permission.toString(16)
        }
    }

    fun deserializeValue(string: String): Int {
        return when(this) {
            CLS -> AclOperation.parseCls(string)
            Radix2 -> string.toInt(2)
            Radix10 -> string.toInt(10)
            Radix16 -> string.toInt(16)
        }
    }
}