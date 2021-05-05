package it.polimi.ingsw.model.gameboard;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;

import java.lang.reflect.Type;

/*
 *This class is an interface adapter for interface Producible and it is used for deserializing json objects.
 */

public class InterfaceAdapter<T extends Producible> implements JsonDeserializer<T> {

    private static final String CLASSNAME = "className";

    @Override
    public T deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
        final JsonObject jsonObject = jsonElement.getAsJsonObject();
        final JsonPrimitive prim = (JsonPrimitive) jsonObject.get(CLASSNAME);
        final String className = prim.getAsString();
        final Class<T> clazz = getClassInstance(className);
        return jsonDeserializationContext.deserialize(jsonObject, clazz);
    }

    @SuppressWarnings("unchecked")
    public Class<T> getClassInstance(String className) {
        try {
            return (Class<T>) Class.forName(className);
        } catch (ClassNotFoundException e) {
            throw new JsonParseException(e.getMessage());
        }
    }


}