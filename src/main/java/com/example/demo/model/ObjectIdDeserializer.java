package com.example.demo.model;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

import java.io.IOException;

import org.bson.types.ObjectId;

public class ObjectIdDeserializer extends JsonDeserializer<ObjectId>  {

    @Override
    public ObjectId deserialize(JsonParser jp, DeserializationContext dc) throws IOException, JsonProcessingException {
        return new ObjectId(jp.readValueAs(String.class));
    }
}