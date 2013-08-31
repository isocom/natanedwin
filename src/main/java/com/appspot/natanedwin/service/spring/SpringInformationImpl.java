/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.appspot.natanedwin.service.spring;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 *
 * @author prokob01
 */
@Service
public class SpringInformationImpl implements SpringInformation {

    @Value("${natanedwin.version}")
    private String version;
    @Value("${natanedwin.appname}")
    private String applicationName;

    @Override
    public String getVersion() {
        return version;
    }

    @Override
    public String getApplicationName() {
        return applicationName;
    }

    public static void main(String... args) {
        SpringInformation bean = SpringContext.INSTANCE.getApplicationContext().getBean(SpringInformation.class);
        System.out.println(bean.getVersion());
    }

    @Override
    public String getSystemInformation() {
        StringBuilder sb = new StringBuilder();
        sb.append("The total amount of memory in the Java virtual machine: ");
        sb.append(Runtime.getRuntime().totalMemory() / (1024 * 1024)).append("Mb\n");
        sb.append("The amount of free memory in the Java Virtual Machine: ");
        sb.append(Runtime.getRuntime().freeMemory() / (1024 * 1024)).append("Mb\n");
        sb.append("The maximum amount of memory that the Java virtual machine will attempt to use: ");
        sb.append(Runtime.getRuntime().maxMemory() / (1024 * 1024)).append("Mb");
        return sb.toString();
    }
}
