package com.appspot.natanedwin.service.spring;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 *
 * @author Bart Prokop
 */
@Service
public class SpringTestService {

    @Value("${service.spring.testValue}")
    private String testValue;

    public String getTestValue() {
        return testValue;
    }

}
