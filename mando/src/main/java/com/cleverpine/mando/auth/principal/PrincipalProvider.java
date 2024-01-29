package com.cleverpine.mando.auth.principal;

import com.cleverpine.viravaspringhelper.core.ViravaPrincipalProvider;
import org.springframework.stereotype.Component;

@Component
public class PrincipalProvider extends ViravaPrincipalProvider<UserPrincipal> {

    protected PrincipalProvider() {
        super(UserPrincipal.class);
    }

    @Override
    public UserPrincipal provideCustomPrincipalInfo(String username) {
        // TODO
        return null;
    }
}
