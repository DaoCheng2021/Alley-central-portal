package com.tts.cp.lib.common;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;

/**
 * @author Alley zhao created on 2021/8/17.
 */
public class DateTimeJsonDeserialize extends JsonDeserializer {

    private SimpleDateFormat format = new SimpleDateFormat("yyy-MM-dd HH:mm:ss");

    @Override
    public Object deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JsonProcessingException {
        try {
            return format.parse(jsonParser.getText());
        } catch (ParseException e) {
            return null;
        }
    }
}
