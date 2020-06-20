package com.ndsl.db.bun133.file;

import com.ndsl.db.bun133.db.DBObject;

import java.io.*;
import java.sql.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class DBFile {
    public static boolean isDBFile(String name){
        return name.endsWith(".ndb") || name.endsWith(".NDB");
    }

    public static boolean isDBFile(File file){
        return file.exists() && isDBFile(file.getName());
    }

    public File file;

    public DBFile(File file) throws DBFileException, IOException {
        if(!isDBFile(file)){
            throw new DBFileException(file);
        }
        this.file=file;
        init();
    }
    public BufferedReader FileReader;
    public BufferedWriter FileWriter;
    public List<String> Strings=new ArrayList();

    private void init() throws IOException {
        if(isClosed) throw new IllegalStateException();
        FileReader=new BufferedReader(new FileReader(file));
        Strings.addAll(Arrays.asList(readAll(FileReader)));
        FileReader.close();
        FileWriter=new BufferedWriter(new FileWriter(file));
        System.out.println("DataSize:"+Strings.size());
    }

    public String[] getCollection() {
        if(isClosed) throw new IllegalStateException();
        return Strings.toArray(new String[0]);
    }

    public DBFile add(String s){
        if(isClosed) throw new IllegalStateException();
        if(!isExists(s)) Strings.add(s);
        return this;
    }

    public DBFile flush() throws IOException {
        if(isClosed) throw new IllegalStateException();
        return flush(file);
    }

    @SuppressWarnings("ResultOfMethodCallIgnored")
    public DBFile flush(File file) throws IOException {
        if(isClosed) throw new IllegalStateException();
        String path = file.getAbsolutePath();
        file.delete();
        file=new File(path);
        file.createNewFile();
        FileWriter=new BufferedWriter(new FileWriter(file));
        writeAll(Arrays.asList(getCollection()),FileWriter);
        return this;
    }

    private void writeAll(List<String> lines,BufferedWriter writer) throws IOException {
        if(isClosed) throw new IllegalStateException();
        for (int i = 0; i < lines.size(); i++) {
            writer.write(lines.get(i));
            if (!(i+1<lines.size())){
                //Final Line
                break;
            }else{
                writer.newLine();
            }
        }
    }

    public boolean isClosed=false;

    public DBFile close() throws IOException {
        if(isClosed) throw new IllegalStateException();
        flush();
        FileReader.close();
        FileWriter.close();
        isClosed=true;
        return this;
    }

    public String[] readAll(BufferedReader reader) throws IOException {
        String line="";
        List<String> lines=new ArrayList<String>();
        while((line=reader.readLine())!=null){
            System.out.println("ReadData:"+line);
            lines.add(line);
        }
        System.out.println("DataSize:"+lines.size());
        return lines.toArray(new String[0]);
    }

    public DBObject[] getAll(){
        List<DBObject> objects = new ArrayList<>();
        for(String s:Strings){
            objects.add(new DBObject(s));
        }
        return objects.toArray(new DBObject[0]);
    }

    public boolean contain(String s){
        return Strings.contains(s);
    }

    public int getIndex(String s){
        if(!contain(s)) return -1;
        String[] collection = getCollection();
        for (int i = 0; i < collection.length; i++) {
            if(collection[i].equals(s)) return i;
        }
        return -1;
    }

    public String get(int index) {
        if(index>this.Strings.size()){
            return "Out of Length";
        }
        return this.Strings.get(index);
    }

    public boolean isExists(String s){
        if(isClosed) throw new IllegalStateException();
        return Strings.contains(s);
    }
}
