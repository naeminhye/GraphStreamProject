/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package doantest;

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

    public JSONObject getObject() {
        return object;
    }

    public void setObject(JSONObject obj) {
        object = new JSONObject(obj, JSONObject.getNames(obj));
    }

    public void reset() {
        object = new JSONObject();
    }
}