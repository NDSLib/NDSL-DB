package com.ndsl.db.bun133.db;

import com.ndsl.db.bun133.file.DBFile;

import java.io.IOException;
import java.util.Map;

public class DoubleDB {
    public DBFile key_file;
    public DBFile value_file;

    public DoubleDB(DBFile key_file, DBFile value_file){
        this.key_file = key_file;
        this.value_file = value_file;
    }

    public int getIndex(String s){
        if(isLocked) throw new IllegalStateException();
        return key_file.getIndex(s);
    }

    public String get(String key){
        if(isLocked) throw new IllegalStateException();
        if(getIndex(key)==-1){
            return "NotFound";
        }
        return value_file.get(getIndex(key));
    }

    public DoubleDB add(String key,String value){
        if(isLocked) throw new IllegalStateException();
        key_file.add(key);
        value_file.add(value);
        return this;
    }

    public DoubleDB addAll(String... datas){
        if(isLocked) throw new IllegalStateException();
        if(datas==null || datas.length % 2 != 0){
             throw new IllegalArgumentException();
        }
        for (int i = 0; i < datas.length; i=+2) {
            add(datas[i],datas[i+1]);
        }
        return this;
    }

    public boolean isLocked=false;

    public DoubleDB close() throws IOException {
        key_file.close();
        value_file.close();
        this.isLocked=true;
        return this;
    }
}
