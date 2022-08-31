package com.ims.dbservice.services;

public interface DBService {

    default boolean isNotNullOrEmptyOrBlank(String value) {
        return !(value == null || value.isEmpty() || value.isBlank());
    }
}
