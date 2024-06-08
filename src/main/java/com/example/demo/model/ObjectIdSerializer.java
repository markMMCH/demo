package com.example.demo.model;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;

import org.bson.types.ObjectId;

public class ObjectIdSerializer extends JsonSerializer<ObjectId> {

    @Override
    public void serialize(ObjectId id, JsonGenerator jg, SerializerProvider sp) throws IOException, JsonProcessingException {
        jg.writeString(id.toString());
    }
}