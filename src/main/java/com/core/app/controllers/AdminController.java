package com.core.app.controllers;

import com.core.app.constants.GeneralConstants;
import com.core.app.entities.database.User;
import com.core.app.entities.dto.Response;
import com.core.app.services.AdminService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

import static com.core.app.constants.GeneralConstants.*;

@RestController
@RequestMapping(value = "/admin")
public class AdminController {

    private AdminService adminService;

    @Autowired
    AdminController(AdminService adminService) {
        this.adminService = adminService;
    }

    @GetMapping(value = "/users/all")
    public ResponseEntity<Response> getAllUsers(@RequestParam(PAGE) Optional<Integer> page,
                                                @RequestParam(SIZE) Optional<Integer> size) {
        return adminService.getAllUsersPage(page, size);
    }
    
    @PostMapping(value = "/users/account/change/status")
	public ResponseEntity<Response> changeAccountStatus(@RequestParam(USER_ID) ObjectId userId) {
    	return adminService.changeSuspendedStatus(userId);
    }

    @DeleteMapping(value = "/users/account/delete")
	public ResponseEntity<Response> deleteUserAccount(@RequestParam(USER_ID) ObjectId userId) {
    	return adminService.deleteUserAccount(userId);
    }

    @PostMapping(value = "/create/admin")
	public ResponseEntity<Response> addNewAdmin(@RequestHeader(GeneralConstants.AUTHORIZATION) String token,
												@RequestBody User user) {
    	return adminService.addNewAdmin(token, user);
    }
}
