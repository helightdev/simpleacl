## Simple Acl
### Description
SimpleACL is a small library useful for integrating more complex
access control rules into new application or role based platforms like
firebase auth. The acl is also intended to be embeddable into JWTs or
certificates.

### Format
SimpleACL defines a bunch of possible formats for declaring permission paths
and primarily permission values. Tho they always represent following pattern:
 ``<path>:<value>``. Values are to be used as followed:
```markdown
| CLS Char | Bit Mask | HTTP Methods | Description                                                    |
|----------|----------|--------------|----------------------------------------------------------------|
| r        | 100000   | GET          | Permission for retrieving information                          |
| u, w     | 010000   | PATCH, PUT   | Permission updating already existing entries                   |
| c, w     | 001000   | POST, PUT    | Permission for creating an entry / datastructure               |
| d, w     | 000100   | DELETE       | Permission for deleting an entry or running a delete operation |
| e, x     | 000010   | POST         | Permission for triggering an operation or job                  |
| s        | 000001   | Any          | Special placeholder permission with variable use               |
```
The permission can be defined as
- **CLS** (Char List String)  
This format is in usage similar to unix file permissions.  
Example: ``users.joe:rw``
- **Radix2** (Binary)  
Defines the value in binary. Easy to write by combining the bit masks.  
Example: ``users.joe:111100``
- **Radix10** (Decimal)  
Defines the value in an int32 decimal using only lowermost bytes
- **Radix16** (Hexadecimal)  
Defines the value in as a hex int32 string using only lowermost bytes

### Wildcards
The permission path supports the usage of wildcards using the char ``*``.
Wildcards are only usable as full path part replacements.
I.e. ``users.helight.pictures.*`` is valid where ``users.joe.*-picture`` is not.
When defining a permission for ``root.*`` the applied selector doesn't include
``root``
