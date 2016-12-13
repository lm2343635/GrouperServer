# Grouper's API Document
This is the REST API document of Grouper Web service, which is a group finance manager application using multiple untrusted servers.

1. Group
====
(1)`group/register`
   
   - Register a new group in this untrusted server.
   - method: POST
   - param: 
      - id(String): group id
      - name(String): group name
   - return:
      - masterkey(String): master key for group creator
   - error:
      - ErrorGroupExsit(1011): Group id is exist in this server.
      - ErrorGroupRegister(1012): Register group error.

(2)`group/info`
   
   - Get group information.
   - method: GET
   - header:
      - key(String): master key of this group or access key of group member
   - return:
      - group(Object): group information
         - id(String): group id
         - name(String): group name
         - members(int): number of group members
         - createDate(long)
         - oid(String): user id from facebook of group owner
   - error:
      - ErrorKeyWrong(903): Cannot get group info, master key or access key is wrong.

2. User
====
(1)`user/add`

   - Add a new user in this untrusted server.
   - method: POST
   - header:
      - key(String): master key
   - param: 
      - uid(String) : user id from facebook
      - name(String)
      - email(String)
      - gender(String)
      - pictureUrl(String): the picture url of user's avatar
      - owner(boolean): this user is group owner or not
   - return:
      - accesskey(String): access key for this new user
   -  error:
      - ErrorMasterKey(901): Master key is wrong.
      - ErrorAddUser(2011): Add user internel error.

(2)`user/list`

   - Get all users' information of a group.
   - method: GET
   - header:
      - key(String): master key of this group or access key of group member
   - return:
      - users(List<UserBean>): user list of this group
         - id(String): user id from facebook
         - name(String)
         - email(String)
         - gender(String)
         - url(String): the picture url of user's avatar
         - gid(String): group id of this user's group
   -  error:
      - ErrorAccessKey(903): Master key or access key is wrong.
      
3. Transfer
====
(1)`transfer/put`

   - Put a share to transfer table
   - method: POST
   - header:
      - key(String): access key of group member
   - param:
      - share(String): the content of a share
      - receiver(String): user id from facebook of the receiver, it's empty if send to all
   - return:
      - success(boolean)
   -  error:
      - ErrorAccessKey(902): Access key is wrong.
      - ErrorNoReceiverFound(3011): Cannot find receiver in this group by this user id.
      - ErrorPutShare(3012): Put share internel error.

(2)`transfer/list`

   - Put a share to transfer table
   - method: POST
   - header:
      - key(String): access key of group member
   - return:
      - shares(List<String>): id of shares
   -  error:
      - ErrorAccessKey(902): Access key is wrong.

(2)`transfer/get`

   - Get a share list by access key, only get share for this user or all users.
   - method: GET
   - header:
      - key(String): access key of group member
   - param:
      - share(String): the content of a share
   - return:
      - success(TransferBean): 
         - id(String): share id
         - share(String): share content
         - savetime(Date)
         - sender(String): user id of sender
         - receiver(String): user id of receiver
   -  error:
      - ErrorAccessKey(902): Access key is wrong.
      - ErrorNoShareFound(3031): Cannot find share by this id.
      - ErrorShareNoPrivilege(3032): You do not have privilege to access this share.