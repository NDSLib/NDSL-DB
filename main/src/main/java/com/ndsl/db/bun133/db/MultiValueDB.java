package com.ndsl.db.bun133.db;

import com.ndsl.db.bun133.file.DBFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MultiValueDB {
    public DBFile key_file;
    public DBFile[] values_file;

    public MultiValueDB(DBFile key_file, DBFile... values_file){
        this.key_file=key_file;
        this.values_file=values_file;
    }

    public String[] get(String key) {
        if(isLocked) throw new IllegalStateException();
        List<String> objects=new ArrayList<String>();
        int index=key_file.getIndex(key);
        for(DBFile value_file:values_file){
            objects.add(value_file.get(index));
        }
        return objects.toArray(new String[0]);
    }

    public MultiValueDB add(String key,String... value){
        if(isLocked) throw new IllegalStateException();
        if(value.length>values_file.length){
            throw new IllegalArgumentException();
        }

        key_file.add(key);
        for (int i = 0; i < value.length; i++) {
            values_file[i].add(value[i]);
        }
        return this;
    }

    public MultiValueDB addAll(String[] keys,String[][] values){
        if(isLocked) throw new IllegalStateException();
        if(keys.length!=values.length) throw new IllegalArgumentException();
        for (int i = 0; i < keys.length; i++) {
            add(keys[i],values[i]);
        }
        return this;
    }

    public Map<String,String[]> getCollection(){
        if(isLocked) throw new IllegalStateException();
        Map<String,String[]> map=new HashMap<String,String[]>();
        for(String key:key_file.getCollection()){
            map.put(key,get(key));
        }
        return map;
    }

    public MultiValueDB flush() throws IOException {
        if(isLocked) throw new IllegalStateException();
        key_file.flush();
        for(DBFile file:values_file){
            file.flush();
        }
        return this;
    }

    public boolean isLocked=false;
    public MultiValueDB close() throws IOException {
        if(isLocked) throw new IllegalStateException();
        flush();
        key_file.close();
        for(DBFile file:values_file){
            file.close();
        }
        isLocked=true;
        return this;
    }
}
