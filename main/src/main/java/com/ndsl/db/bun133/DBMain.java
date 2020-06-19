package com.ndsl.db.bun133;

import com.ndsl.db.bun133.db.SingleDBBase;
import com.ndsl.db.bun133.file.DBFile;
import com.ndsl.db.bun133.file.DBFileException;

import java.io.File;
import java.io.IOException;

public class DBMain {
    public static void main(String[] args) throws IOException, DBFileException {
        DBFile file = new DBFile(new File("main\\src\\main\\resources\\test_single_db.ndb"));
        SingleDBBase singleDB = new SingleDBBase(file);
        System.out.println("index 0 is:"+singleDB.get(0));
        singleDB.close();
    }
}
