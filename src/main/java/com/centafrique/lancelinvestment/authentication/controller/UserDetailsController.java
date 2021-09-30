package com.centafrique.lancelinvestment.authentication.controller;


import com.centafrique.lancelinvestment.authentication.entity.UserDetails;
import com.centafrique.lancelinvestment.authentication.service_class.impl.UserDetailsServiceImpl;
import com.centafrique.lancelinvestment.authentication.helper_class.ErrorMessage;
import com.centafrique.lancelinvestment.authentication.helper_class.Results;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class UserDetailsController {

    @Autowired
    private UserDetailsServiceImpl userDetailsService;

//    @GetMapping
//    public Map<String, String> home()
//    {
//        return Collections.singletonMap("name", "JSBLOGS");
//    }

    @RequestMapping(value = "/api/v1/auth/registration", method = RequestMethod.POST)
    public ResponseEntity registerUser(@RequestBody UserDetails userDetails){

        Results addedResults = userDetailsService.registerUser(userDetails);
        if (addedResults != null){

            var statusCode = addedResults.getStatusCode();
            var results = addedResults.getDetails();

            if (statusCode == 201){
                return new ResponseEntity(results, HttpStatus.CREATED);
            }else {
                var errorMessage = results.toString();
                return ResponseEntity.badRequest().body(new ErrorMessage(errorMessage));
            }

        }else {
            return ResponseEntity.internalServerError().body(new ErrorMessage("There was an error with your request."));
        }

    }

    @RequestMapping(value = "/api/v1/users/get-users/", method = RequestMethod.GET)
    public ResponseEntity getAllStaff(){

        List<UserDetails> usersList = userDetailsService.getAllUsers();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        return new ResponseEntity(usersList,headers, HttpStatus.OK);
    }



}
