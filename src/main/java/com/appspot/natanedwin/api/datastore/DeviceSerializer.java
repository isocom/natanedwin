package com.appspot.natanedwin.api.datastore;

import com.appspot.natanedwin.entity.Device;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import java.io.IOException;

public class DeviceSerializer extends JsonSerializer<Device>{

    @Override
    public void serialize(Device value, JsonGenerator jgen, SerializerProvider provider) throws IOException, JsonProcessingException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    
}
