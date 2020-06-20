package com.ndsl.db.bun133;

import com.ndsl.db.bun133.db.DoubleDB;
import com.ndsl.db.bun133.db.MultiKeyValueDB;
import com.ndsl.db.bun133.db.MultiValueDB;
import com.ndsl.db.bun133.db.SingleDBBase;
import com.ndsl.db.bun133.file.DBFile;
import com.ndsl.db.bun133.file.DBFileException;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;

public class DBMain {
    //MEMO
    //close will flush!!!!
    //DO NOT FORGET!

    public static final String resources="main\\src\\main\\resources";
    public static void main(String[] args) throws IOException, DBFileException {
        DBFile file = new DBFile(new File(resources+"\\test_single_db.ndb"));
        SingleDBBase singleDB = new SingleDBBase(file);
        System.out.println("index 0 is:"+singleDB.get(0));
        singleDB.close();

        //It is going to throw IllegalStateException
        //file.getCollection();
        
        DBFile key_file = new DBFile(new File(resources+"\\double_db_key.ndb"));
        DBFile value_file = new DBFile(new File(resources+"\\double_db_value.ndb"));

        DoubleDB double_db=new DoubleDB(key_file, value_file);
        System.out.println("Value:"+double_db.get("Key1"));
        double_db.add("key2","value2");
        double_db.close();

        DBFile multi_key=new DBFile(new File(resources+"\\multi_db_key.ndb"));
        DBFile multi_value1=new DBFile(new File(resources+"\\multi_db_value1.ndb"));
        DBFile multi_value2=new DBFile(new File(resources+"\\multi_db_value2.ndb"));
        MultiValueDB multiValueDB=new MultiValueDB(multi_key,multi_value1,multi_value2);
        System.out.println("MultiDB:"+ Arrays.toString(multiValueDB.get("key1")));
        multiValueDB.close();

        DBFile multi_key1=new DBFile(new File(resources+"\\multi_db_key1.ndb"));
        DBFile multi_key2=new DBFile(new File(resources+"\\multi_db_key2.ndb"));
        multi_value1=new DBFile(new File(resources+"\\multi_db_value1.ndb"));
        multi_value2=new DBFile(new File(resources+"\\multi_db_value2.ndb"));

        MultiKeyValueDB multiKeyValueDB=new MultiKeyValueDB(Arrays.asList(multi_key1,multi_key2),Arrays.asList(multi_value1,multi_value2));
        System.out.println(Arrays.toString(multiKeyValueDB.getValues("key1")));
        System.out.println(Arrays.toString(multiKeyValueDB.getValues("key2")));
        multiKeyValueDB.close();
    }
}
