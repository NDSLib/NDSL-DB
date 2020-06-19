package com.ndsl.db.bun133.db;

import com.ndsl.db.bun133.file.DBFile;
import com.ndsl.db.bun133.file.DBFileException;
import com.ndsl.db.bun133.util.SmartCast;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static com.ndsl.db.bun133.file.DBFile.isDBFile;

public class SingleDBBase {
    public DBFile file;
    public SingleDBBase(DBFile file){
        this.file=file;
        addAll(file.getAll());
    }

    public List<DBObject<String>> DBObjects=new ArrayList<DBObject<String>>();

    public DBObject get(int index){
        if(isLocked) throw new IllegalStateException();
        return DBObjects.get(index);
    }

    public SingleDBBase add(String t){
        if(isLocked) throw new IllegalStateException();
        return add(new DBObject<String>(t));
    }

    public SingleDBBase add(DBObject<String> DBObject){
        if(isLocked) throw new IllegalStateException();
        DBObjects.add(DBObject);
        return this;
    }

    public SingleDBBase addAll(DBObject[] DBObjects){
        for(DBObject DBObject: DBObjects){
            add(DBObject);
        }
        return this;
    }

    public SingleDBBase addAll(List<DBObject> DBObjects){
        for(DBObject DBObject: DBObjects){
            add(DBObject);
        }
        return this;
    }

    public DBFile toFile(String file_path) throws IOException, DBFileException {
        if(isLocked) throw new IllegalStateException();
        File file=new File(file_path);
        if(!file.exists()){
            file.createNewFile();
        }
        if(file.exists()){
            if(isDBFile(file)){
                return new DBFile(file);
            }
        }
        DBFile db_file=new DBFile(file);
        for(DBObject obj:DBObjects){
            db_file.add(SmartCast.toString(obj));
        }
        return db_file;
    }

    public DBFile flush(String file_path) throws IOException, DBFileException {
        if(isLocked) throw new IllegalStateException();
        File file = new File(file_path);
        if(!isDBFile(file)){
            return this.toFile(file_path);
        }else{
            DBFile db_file = new DBFile(file);
            db_file.flush();
            return db_file;
        }
    }

    public boolean isLocked=false;
    public void close() throws IOException {
        file.flush();
        file.close();
        isLocked=true;
    }
}
