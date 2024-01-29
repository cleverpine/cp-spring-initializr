package com.cleverpine.mando.auth.roles;

import com.cleverpine.viravaspringhelper.core.BaseRole;
import com.cleverpine.viravaspringhelper.dto.Permission;
import com.cleverpine.viravaspringhelper.dto.Scope;

import java.util.List;

public enum Roles implements BaseRole {
    USER("view-profile", List.of(Permission.of(Resources.TEMPLATE, Scope.READ))),
    ADMIN("manage-account", List.of(Permission.of(Resources.TEMPLATE, Scope.CRUD)));

    private final List<Permission> permissionList;
    private final String roleName;

    Roles(String roleName, List<Permission> permissionList) {
        this.roleName = roleName;
        this.permissionList = permissionList;
    }

    @Override
    public String getRoleName() {
        return roleName;
    }

    @Override
    public List<Permission> getPermissionList() {
        return List.copyOf(permissionList);
    }
}
