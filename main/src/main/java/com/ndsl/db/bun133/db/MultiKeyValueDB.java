package com.ndsl.db.bun133.db;

import com.ndsl.db.bun133.file.DBFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MultiKeyValueDB {
    public List<DBFile> key_files;
    public List<DBFile> value_files;

    public MultiKeyValueDB(List<DBFile> key_file,List<DBFile> value_file){
        key_files=new ArrayList<>(key_file);
        value_files=new ArrayList<>(value_file);
    }

    public int getIndex(String s){
        if(isLocked) throw new IllegalStateException();
        for(DBFile file:key_files){
            if(file.getIndex(s)!=-1){
                return file.getIndex(s);
            }
        }
        return -1;
    }

    public String[] getValues(String s){
        if(isLocked) throw new IllegalStateException();
        return getValues(getIndex(s));
    }

    public String[] getValues(int index){
        if(isLocked) throw new IllegalStateException();
        if(index < 0) throw new IllegalArgumentException();
        List<String> list=new ArrayList<String>();
        for(DBFile file:value_files){
            list.add(file.get(index));
        }
        return list.toArray(new String[0]);
    }

    public MultiKeyValueDB add(List<String> keys,List<String> values){
        if(isLocked) throw new IllegalStateException();
        if(key_files.size()!=keys.size()||value_files.size()!=values.size()){
            throw new IllegalArgumentException();
        }
        for (int i = 0; i < keys.size(); i++) {
            key_files.get(i).add(keys.get(i));
        }
        for (int i = 0; i < values.size(); i++) {
            value_files.get(i).add(values.get(i));
        }
        return this;
    }

    public MultiKeyValueDB addAll(List<List<String>> keys,List<List<String>>values){
        if(isLocked) throw new IllegalStateException();
        if(keys.size()!=values.size()){
            throw new IllegalArgumentException();
        }

        for (int i = 0; i < keys.size(); i++) {
            add(keys.get(i), values.get(i));
        }
        return this;
    }

    public MultiKeyValueDB flush() throws IOException {
        if(isLocked) throw new IllegalStateException();
        for(DBFile file:key_files){
            file.flush();
        }
        for(DBFile file:key_files){
            file.flush();
        }
        return this;
    }

    public boolean isLocked=false;
    public MultiKeyValueDB close() throws IOException {
        if(isLocked) throw new IllegalStateException();
        for(DBFile file:key_files){
            file.close();
        }
        for(DBFile file:value_files){
            file.close();
        }
        isLocked=true;
        return this;
    }
}
