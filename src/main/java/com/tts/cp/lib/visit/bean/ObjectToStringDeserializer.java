package com.tts.cp.lib.visit.bean;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;

@Slf4j
public class ObjectToStringDeserializer extends StdDeserializer {


    public ObjectToStringDeserializer() {
        this(null);
    }
    protected ObjectToStringDeserializer(
            Class<?> vc) {
        super(vc);
    }

    @Override
    public String deserialize(JsonParser p, DeserializationContext ctxt) throws IOException, JsonProcessingException {
        JsonNode node = p.getCodec().readTree(p);
        return node.toString();
    }
}

