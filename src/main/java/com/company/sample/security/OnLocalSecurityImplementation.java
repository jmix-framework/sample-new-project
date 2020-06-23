package com.company.sample.security;

import io.jmix.core.security.AbstractSecurityImplementationCondition;

public class OnLocalSecurityImplementation extends AbstractSecurityImplementationCondition {

    @Override
    protected String getSecurityImplementationName() {
        return "local";
    }

}