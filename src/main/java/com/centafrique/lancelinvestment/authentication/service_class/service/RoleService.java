package com.centafrique.lancelinvestment.authentication.service_class.service;


import com.centafrique.lancelinvestment.authentication.entity.Role;

public interface RoleService {

    boolean isRoleExists(String roleName);
    Role addRole(Role role);
    Role getRoleDetails(String roleName);
    void addRoleToUser(Long roleId, String userId);
//    List<Role> getAllRoles();

}
