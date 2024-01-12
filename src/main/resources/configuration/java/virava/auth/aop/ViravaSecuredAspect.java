package com.cleverpine.{applicationName}.auth.aop;

import com.cleverpine.{applicationName}.auth.ViravaSecured;
import com.cleverpine.{applicationName}.auth.principal.PrincipalProvider;
import com.cleverpine.viravaspringhelper.aop.BaseViravaSecuredAspect;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class ViravaSecuredAspect extends BaseViravaSecuredAspect {

    public ViravaSecuredAspect(PrincipalProvider principalProvider) {
        super(principalProvider);
    }

    @Before("@annotation(viravaSecured)")
    public void secure(JoinPoint joinPoint, ViravaSecured viravaSecured) {
        authorize(joinPoint, viravaSecured.resource(), viravaSecured.scope());
    }
}