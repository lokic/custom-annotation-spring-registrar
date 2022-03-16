package com.github.lokic.custom.registrar.service;

import com.github.lokic.custom.registrar.annotiation.TestObjectService;

@TestObjectService
public class UserObjectService {

    public String getName(Long uid) {
        return "UserObjectService:ok";
    }
}
