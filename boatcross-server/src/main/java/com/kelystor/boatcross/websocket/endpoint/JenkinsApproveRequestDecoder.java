package com.kelystor.boatcross.websocket.endpoint;

import com.fasterxml.jackson.databind.ObjectMapper;

import javax.websocket.DecodeException;
import javax.websocket.Decoder;
import javax.websocket.EndpointConfig;
import java.io.IOException;

public class JenkinsApproveRequestDecoder implements Decoder.Text<JenkinsApproveRequest> {
    private static final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public JenkinsApproveRequest decode(String s) throws DecodeException {
        try {
            return objectMapper.readValue(s, JenkinsApproveRequest.class);
        } catch (IOException e) {
            return null;
        }
    }

    @Override
    public boolean willDecode(String s) {
        return true;
    }

    @Override
    public void init(EndpointConfig endpointConfig) {

    }

    @Override
    public void destroy() {

    }
}
