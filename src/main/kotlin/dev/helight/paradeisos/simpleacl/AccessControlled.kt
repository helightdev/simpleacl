package dev.helight.paradeisos.simpleacl

interface AccessControlled {

    fun isAccessibleBy(acl: ACL): Boolean

}