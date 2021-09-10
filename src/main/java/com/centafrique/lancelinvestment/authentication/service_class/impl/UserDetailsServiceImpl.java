package com.centafrique.lancelinvestment.authentication.service_class.impl;


import com.centafrique.lancelinvestment.authentication.entity.Role;
import com.centafrique.lancelinvestment.authentication.entity.UserDetails;
import com.centafrique.lancelinvestment.authentication.helper_class.Results;
import com.centafrique.lancelinvestment.authentication.repository.RoleRepository;
import com.centafrique.lancelinvestment.authentication.repository.UserDetailsRepository;
import com.centafrique.lancelinvestment.authentication.service_class.service.RoleService;
import com.centafrique.lancelinvestment.authentication.service_class.service.UserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class UserDetailsServiceImpl implements UserDetailsService, RoleService{

    @Autowired
    private UserDetailsRepository userDetailsRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private RoleRepository roleRepository;

    @Override
    public boolean isEmailAddress(String emailAddress) {
        return userDetailsRepository.existsByEmailAddress(emailAddress);
    }

    @Override
    public boolean isUserName(String userName) {
        return userDetailsRepository.existsByUsername(userName);
    }

    @Override
    public boolean isRoleExists(String roleName) {
        return roleRepository.existsByName(roleName);
    }

    @Override
    public Role addRole(Role roles) {
        return roleRepository.save(roles);
    }

    public void saveRoles(Role role){
        if (!isRoleExists(role.getName())){
            addRole(role);
        }
    }

    @Override
    public List<UserDetails> getAllUsers() {
        return userDetailsRepository.findAll();
    }

    @Override
    public UserDetails getUserDetailsByEmailAddress(String emailAddress) {

        return userDetailsRepository.findAllByEmailAddress(emailAddress);
    }


    @Override
    public Role getRoleDetails(String roleName) {
        return roleRepository.findByName(roleName);
    }

    @Override
    public void addRoleToUser(Long roleId, String userId) {

        UserDetails userDetails = userDetailsRepository.findAllByUserId(userId);
        Role roles = getRoles(roleId);
        userDetails.getRolesCollection().add(roles);

    }

    public Role getRoles(Long id){
        Optional<Role> optionalRole = roleRepository.findById(id);
        return optionalRole.orElse(null);
    }

    @Override
    public UserDetails addUserDetails(UserDetails userDetails) {
        return userDetailsRepository.save(userDetails);
    }

    public Results registerUser(UserDetails userDetails){

        String error = "";

        boolean isEmailExists = isEmailAddress(userDetails.getEmailAddress());
        boolean isUserExists = isUserName(userDetails.getUsername());

        if (!isEmailExists){

            if (!isUserExists){

                userDetails.setPassword(passwordEncoder.encode(userDetails.getPassword()));
                UserDetails userDetails1 = addUserDetails(userDetails);
                if (userDetails1 != null){
                    String userId = userDetails1.getUserId();
                    Long roleId = getRoleDetails("ROLE_USER").getId();
                    addRoleToUser(roleId, userId);

//                    Long roleId1 = getRoleDetails("ROLE_ADMIN").getId();
//                    addRoleToUser(roleId1, userId);

                    return new Results(201, userDetails1);

                }

            }else {
                error = error + "Username already exists.";
            }

        }else {
            error = error + "Email already exists.";

        }

        return new Results(400, error);

    }

    public UserDetails getLoggedInUser(){

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (!(authentication instanceof AnonymousAuthenticationToken)) {

            String currentEmailAddress = authentication.getName();
            UserDetails userDetails = getUserDetailsByEmailAddress(currentEmailAddress);
            return userDetails;
        }else {
            return null;
        }

    }

}