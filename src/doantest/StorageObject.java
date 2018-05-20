/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package doantest;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 *
 * @author Hieu Nguyen
 */
public class StorageObject extends JSONObject {
    private JSONObject object;
    
    public StorageObject() {
        object = new JSONObject();
    }

    public StorageObject(JSONObject obj) {
        object = new JSONObject(obj, JSONObject.getNames(obj));
    }
    
    /**
     * Kiểm tra xem trong JSONArray có chứa JSONObject không
     * @param arr
     * @param obj
     * @return 
     */
    public static boolean arrayContainsObject(JSONArray arr, JSONObject obj) {
        int length = arr.length();
        for(int i = 0; i < length; i ++) {
            if(obj.toString().equals(arr.get(i).toString())) {
                return true;
            }
        }
        return false;
    }
    public int indexOf(JSONArray arr, JSONObject obj) {
        int length = arr.length();
        for(int i = 0; i < length; i ++) {
            if(obj.toString().equals(arr.get(i).toString())) {
                return i;
            }
        }
        return -1;
    }

    public JSONObject getObject() {
        return object;
    }

    public void setObject(JSONObject obj) {
        object = new JSONObject(obj, JSONObject.getNames(obj));
    }

    public void reset() {
        object = new JSONObject();
    }
    
    public void putObjectToArray(String nameOfArray, JSONObject obj, boolean isReplaceable) {
        if(object.isNull(nameOfArray)) {
            object.put(nameOfArray, new JSONArray().put(obj));

        } 
        else {
            int index = indexOf(object.getJSONArray(nameOfArray), obj);
            if(index == -1) {
                object.getJSONArray(nameOfArray).put(obj);
            }
            else {
                if(isReplaceable) {
                    object.getJSONArray(nameOfArray).remove(index);
                    object.getJSONArray(nameOfArray).put(obj);
                }
            }
        }
    }
    
    public void removeObjectFromArray(String nameOfArray, JSONObject obj) {
        int index = indexOf(object.getJSONArray(nameOfArray), obj);
        if(!object.isNull(nameOfArray) && hasPutObjectToArray(nameOfArray, obj)) {
            object.getJSONArray(nameOfArray).remove(index);
        }
    }
    
    public boolean hasPutObjectToArray(String nameOfArray, JSONObject obj) {
        if(object.isNull(nameOfArray)) {
            return false;
        } else {
            if(!arrayContainsObject(object.getJSONArray(nameOfArray), obj)) {
                return false;
            }
        }
        return true;
    }
    
    public void putKeyValueToObject(String nameOfObject, String key, String value, boolean isReplaceable) {
        if(object.isNull(nameOfObject)) {
            object.put(nameOfObject, new JSONObject().put(key, value));
        }
        else if(object.getJSONObject(nameOfObject).isNull(key)) {
            object.getJSONObject(nameOfObject).put(key, value);
        }
        else if (!object.getJSONObject(nameOfObject).get(key).toString().equals(value)) {
            if(isReplaceable) {
                object.getJSONObject(nameOfObject).remove(key);
                object.getJSONObject(nameOfObject).put(key, value);
            }
        }
        else {
        }
    }
    
    public void putKeyValueToArray(String nameOfArray, String key, String value, boolean repeat) {
        if(object.isNull(nameOfArray)) {
            object.put(nameOfArray, new JSONObject().put(key, new JSONArray().put(value)));

        } else {
            if(object.getJSONObject(nameOfArray).has(key)) {
                if(!object.getJSONObject(nameOfArray).getJSONArray(key).toString().contains(value)) {
                    object.getJSONObject(nameOfArray).getJSONArray(key).put(value);
                }
                else if(repeat) {
                    object.getJSONObject(nameOfArray).getJSONArray(key).put(value);
                }
            }
            else {
                object.getJSONObject(nameOfArray).put(key, new JSONArray().put(value));
            }
        }
    }
    
    public void addArrayToArray(String source, String target) {
        if(!object.isNull(source)) {
//            System.out.println("SOURCE: " + object.getJSONArray(source));
//            System.out.println("TARGET: " + object.getJSONArray(target));
            if(!object.isNull(target)) {
                for(Object element: object.getJSONArray(source)) {
                    object.getJSONArray(target).put(element);
                }
                
                int length = object.getJSONArray(source).length();
                for (int i = length - 1; i >= 0; i--) {
                    object.getJSONArray(source).remove(i);
                }
            }
            else {
                object.put(target, object.getJSONArray(source));
                
                int length = object.getJSONArray(source).length();
                for (int i = length - 1; i >= 0; i--) {
                    object.getJSONArray(source).remove(i);
                }
            }
        }
//        System.out.println("SOURCE AFTER ADDING: " + object.getJSONArray(source));
//        System.out.println("TARGET AFTER ADDING: " + object.getJSONArray(target));
    }
}