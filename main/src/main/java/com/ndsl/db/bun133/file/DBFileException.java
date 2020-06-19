package com.ndsl.db.bun133.file;

import java.io.File;

public class DBFileException extends Throwable {
    public DBFileException(File file) {
        System.out.println(file.getAbsolutePath()+" is Not NDBFile.");
    }
}
