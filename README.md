# NDSL-DB
This is simple DB.

# For Using
## DBFile
```Java
DBFile file=new DBFile(new File("%path"));
```
Init for DBFile.
DBFile extension is ".ndb" or ".NDB"

## SingleDB
```Java
SingleDBBase SingleDB=new SingleDBBase(DBfile);
```

and Using for getting of index 1

```Java
SingleDB.get(1);
```

and Finelly,
**You have to Close!**
```Java
SigleDB.close();
```

## DoubleDB
DoubleDB has one key,one value
```Java
DoubleDB doubleDB=new DoubleDB(DB_Key_File,DB_Value_File);
```

and using for getting value linked key:"key1"

```Java
doubleDB.get("key1");
```

and Close
```Java
doubleDB.close();
```
## MultiValueDB
MultiValueDB has one key and many values
```Java
MultiValueDB multiValueDB=new MultiValueDB(DB_Key_File,DB_Value_File...);
```

and using for getting values linked key:"key1"
```Java
multiValueDB.get("key1");
```

and Close
```Java
multiValueDB.close();
```
## MultiKeyValueDB
MultiKeyValueDB has many keys and many values
```Java
MultiKeyValueDB multiKeyValueDB=new MultiKeyValueDB(Arrays.asList(DB_Key_File...),Arrays.asList(DB_Value_File...));
```

and using for getting values linked keys:"key1","key2"
```Java
multiKeyValueDB.get("key1");
multiKeyValueDB.get("key2");
// It's Same! :)
```

and Close
```
multiKeyValueDB.close();
```
