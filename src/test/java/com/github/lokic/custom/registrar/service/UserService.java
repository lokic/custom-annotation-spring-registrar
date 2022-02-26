package com.github.lokic.custom.registrar.service;

import com.github.lokic.custom.registrar.annotiation.TestService;

@TestService
public interface UserService {

    String getName(Long uid);
}
