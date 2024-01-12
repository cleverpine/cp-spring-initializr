package com.cleverpine.{applicationName}.auth.roles;


import com.cleverpine.viravaspringhelper.core.BaseResource;

import java.util.List;

public enum Resources implements BaseResource {
    TEMPLATE;

    @Override
    public String resource() {
        return toString();
    }

    @Override
    public List<BaseResource> getFullResourceList() {
        return List.of(TEMPLATE);
    }
}
