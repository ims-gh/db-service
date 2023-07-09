package com.ims.ordermanagement.services.interfaces;

import com.ims.ordermanagement.models.entities.DbEntity;

public interface DBService {


    default boolean isNotNullOrBlank(String value) {
        return !(value == null || value.isBlank());
    }

    DbEntity findOrThrowError(Object id);


}

