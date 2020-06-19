package com.ndsl.db.bun133.util;

public class SmartCast {
    public static String toString(Object obj) {
        if(obj instanceof String){
            return ((String)obj);
        }else if(obj instanceof Integer){
            return ((Integer)obj).toString();
        }else if(obj instanceof Long){
            return ((Long)obj).toString();
        }else if(obj instanceof Boolean){
            return ((Boolean)obj).toString();
        }else if(obj instanceof Double){
            return ((Double)obj).toString();
        }else if(obj instanceof Float){
            return ((Float)obj).toString();
        }else if(obj instanceof Character){
            return ((Character)obj).toString();
        }


        System.out.println("Not Mapped! in To String");
        return null;
    }
}
