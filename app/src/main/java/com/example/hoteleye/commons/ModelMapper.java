package com.example.hoteleye.commons;

import android.view.View;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.lang.reflect.Field;

public class ModelMapper {
    private Map<String, Set<String>> listMapField = new HashMap<>();
    private Map<String, PairValue> listMapValue = new HashMap<>();
    private Map<String, Object> listDefaultValue = new HashMap<>();
    private Map<String, String> listDefaultField = new HashMap<>();
    private Set<String> listLockedMapField = new HashSet<String>();

    public ModelMapper() {
    }

    public void mapFieldName(String fromField, String toField) {
    }

    public void mapFieldName(String fromField, String toField, Object defaultvalue) {
        if (fromField == null || fromField.isEmpty()) {
            return;
        }
        if (toField == null || toField.isEmpty()) {
            return;
        }

        if (!listMapField.containsKey(fromField)) {
            Set<String> set = new HashSet<>();
            set.add(toField);
            this.listMapField.put(fromField, set);
        } else {
            Set<String> set = listMapField.get(fromField);
            set.add(toField);
        }

        //Set default value
        if (!this.listDefaultValue.containsKey(fromField)) {
            // store defaultValue and toField in 2 collections with same key
            this.listDefaultValue.put(fromField, defaultvalue);
            this.listDefaultValue.put(fromField, toField);
        }
    }

//    public void mapFieldValue(String field, String fromValue, String toValue)

    public String buildJsonModel(final Object fromModel, JsonObject refModel) throws Exception {
        Gson gson = new Gson();
        JsonObject model = this.buildJsonModelAsObject(fromModel,refModel);
        return gson.toJson(model);
    }

    public JsonObject buildJsonModelAsObject(final Object fromModel, JsonObject refModel) throws Exception {
        JsonObject jModel = refModel != null ? refModel : new JsonObject();
        if (fromModel == null) {
            return jModel;
        }
        Class<? extends Object> modelClass = fromModel.getClass();
        Field fieldList[] = modelClass.getDeclaredFields();
        for (Field field : fieldList) {
            String fieldName = field.getName();
            field.setAccessible(true);

            Object fromValue = field.get(fromModel);
            Object toValue = fromValue;
            if (this.listLockedMapField.contains(fieldName)) continue;
            //Transform Value
            Object transformValue = this.applyTransformValue(fieldName, fromValue, toValue);

            //Transform Field;
            jModel = this.applyTransformField(jModel, fieldName, transformValue);
        }

        //Transform default value
        jModel = this.applyDefaultValue(jModel);
        return jModel;
    }

    private JsonObject applyDefaultValue(JsonObject jModel) {
        if (jModel == null) return jModel;
        for (Map.Entry<String, Object> entry : this.listDefaultValue.entrySet()) {
            String key = entry.getKey();
            String xKey = this.listDefaultField.containsKey(key) ? this.listDefaultField.get(key) : key;
            Object defaultValue = entry.getValue() != null ? entry.getValue() : null;

            //Update default value for model props
            boolean isPropInModel = false;
            for (Map.Entry<String, JsonElement> entryModel : jModel.entrySet()) {
                String keyModel = entryModel.getKey();
                if (key.equals(keyModel)) {
                    JsonElement modelValue = entryModel.getValue();
                    if (modelValue == null) {
                        jModel = this.setProperty(jModel, xKey, defaultValue);
                    } else if (xKey != key) {
                        jModel = this.setProperty(jModel, xKey, modelValue);
                    }
                    isPropInModel = true;
                    break;
                }
            }
            if (!isPropInModel) {
                //update non model prop
                jModel = this.setProperty(jModel, xKey, defaultValue);
            }
        }
        return jModel;
    }

    private Object applyTransformValue(String fieldName, Object fromValue, Object toValue) {
        Object transformValue = toValue != null ? toValue : null;
        String subKey = fromValue != null ? fromValue.toString().toLowerCase() : "null";
        String key = fieldName + "_" + subKey;

        if (listMapValue.containsKey(key)) {
            PairValue pair = listMapValue.get(key);
            Object xFromValue = pair.getFromValue();
            Object xToValue = pair.getToValue();
            if (fromValue == null && xFromValue == null) {
                toValue = xToValue;
            } else if (fromValue != null && xFromValue != null) {
                if (fromValue.toString().equals(xFromValue.toString())) {
                    toValue = xToValue;
                }
            }
            transformValue = toValue != null ? toValue : null;
        }
        return transformValue;
    }

    private JsonObject applyTransformField(JsonObject jModel, String fieldName, Object transformValue) {
        if (jModel == null) {
            return null;
        }
        Set<String> toNameSet = listMapField.containsKey(fieldName) ? listMapField.get(fieldName) : null;
        if (toNameSet == null) {
            String toName = fieldName;
            jModel = this.setProperty(jModel, toName, transformValue);
            if (!this.listLockedMapField.contains(toName)) {
                this.listLockedMapField.add(toName);
            }
        } else {
            for (String toName : toNameSet) {
                jModel = this.setProperty(jModel, toName, transformValue);
                //Save to resultSet to prevent map again
                if (!this.listLockedMapField.contains(toName)) {
                    this.listLockedMapField.add(toName);
                }
            }
        }
        return jModel;
    }

    public JsonObject setProperty(String field, Object object) {
        JsonObject jModel = new JsonObject();
        return this.setProperty(jModel, field, object);
    }

    public JsonObject setProperty(JsonObject jModel, String field, Object obj) {
        if (jModel == null) {
            return jModel;
        }
        if (obj == null) return jModel;
        if (obj.getClass().equals(JsonArray.class)) {
            jModel.add(field, (JsonArray) obj);
        } else if (obj instanceof List<?>) {
            Gson gson = new GsonBuilder().create();
            JsonArray elementArr = gson.toJsonTree(obj).getAsJsonArray();
            jModel.add(field, elementArr);
        } else if (obj.getClass().equals(JsonObject.class)) {
            jModel.add(field, (JsonObject) obj);
        } else if (obj.getClass().equals(JsonPrimitive.class)) {
            jModel.add(field, (JsonPrimitive) obj);
        } else {
            jModel.addProperty(field, obj.toString());
        }
        return jModel;

    }

    public class PairValue {
        private Object fromValue;
        private Object toValue;

        public PairValue() {
        }

        public Object getFromValue() {
            return fromValue;
        }

        public void setFromValue(Object fromValue) {
            this.fromValue = fromValue;
        }

        public Object getToValue() {
            return toValue;
        }

        public void setToValue(Object toValue) {
            this.toValue = toValue;
        }
    }
}
