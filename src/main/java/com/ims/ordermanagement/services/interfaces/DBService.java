package com.ims.ordermanagement.services.interfaces;

import com.ims.ordermanagement.models.entities.DbEntity;

public interface DBService {


    default boolean isNotNullOrEmptyOrBlank(String value) {
        return !(value == null || value.isEmpty() || value.isBlank());
    }

    DbEntity findOrThrowError(Object id);


}

