package dev.helight.paradeisos.simpleacl

interface AclSubject {

    val acl: Iterable<AclEntry>

}