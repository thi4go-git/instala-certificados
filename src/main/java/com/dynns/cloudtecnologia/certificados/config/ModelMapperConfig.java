package com.dynns.cloudtecnologia.certificados.config;

import org.modelmapper.ModelMapper;

public class ModelMapperConfig {

    private ModelMapperConfig() {
    }

    public static ModelMapper getModelMapper() {
        return new ModelMapper();
    }
}
