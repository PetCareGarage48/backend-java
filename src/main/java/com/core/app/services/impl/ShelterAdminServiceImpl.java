package com.core.app.services.impl;

import com.core.app.entities.database.shelter.Shelter;
import com.core.app.entities.database.shelter.ShelterAdmin;
import com.core.app.entities.database.shelter.ShelterTokenEntity;
import com.core.app.entities.database.user.GlobalAppSettings;
import com.core.app.entities.dto.Response;
import com.core.app.entities.dto.UserCredentials;
import com.core.app.repositories.ShelterAdminRepository;
import com.core.app.services.GlobalAppSettingsService;
import com.core.app.services.ShelterAdminService;
import com.core.app.services.ShelterService;
import com.core.app.services.ShelterTokenService;
import com.core.app.utils.Helper;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import static com.core.app.constants.GeneralConstants.FALSE;
import static com.core.app.constants.GeneralConstants.SUCCESS;
import static com.core.app.constants.GeneralConstants.TRUE;
import static com.core.app.constants.HttpConstants.*;
import static com.core.app.constants.HttpConstants.INVALID_CREDENTIALS;

@Service
public class ShelterAdminServiceImpl implements ShelterAdminService {

    private final ShelterAdminRepository shelterAdminRepository;
    private final GlobalAppSettingsService settingsService;
    private final ShelterTokenService shelterTokenEntityService;
    private final ShelterService shelterService;

    @Autowired
    public ShelterAdminServiceImpl(ShelterAdminRepository shelterAdminRepository, GlobalAppSettingsService settingsService, ShelterTokenService shelterTokenEntityService, ShelterService shelterService) {
        this.shelterAdminRepository = shelterAdminRepository;
        this.settingsService = settingsService;
        this.shelterTokenEntityService = shelterTokenEntityService;
        this.shelterService = shelterService;
    }


    @Override
    public Boolean shelterAdminExists(String email) {
        if(shelterAdminRepository.findByEmail(email) != null) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public ResponseEntity<Response> buildShelterAdmin(ShelterAdmin user) {
        return null;
    }

    @Override
    public ResponseEntity<Response> register(ShelterAdmin admin) {
        GlobalAppSettings appSettings = settingsService.getAppSettings();
        admin.setPassword(Helper.hashPassword(admin.getPassword()));
        admin.setEmail(emailToLowerCase(admin));
        return !shelterAdminExists(admin.getEmail())
                ? proceedRegistration(appSettings, admin)
                : Helper.buildHttpResponse(HttpStatus.CONFLICT, TRUE, USER_EXISTS, null);
    }

    @Override
    public ResponseEntity<Response> login(UserCredentials credentials) {
        ShelterAdmin admin  = shelterAdminRepository.findByEmail(credentials.getEmail().toLowerCase());
        if (admin == null) {
            return Helper.buildHttpResponse(HttpStatus.NOT_FOUND, TRUE, INVALID_CREDENTIALS, 1);
        } else {
            return Helper.passwordsMatch(credentials.getPassword(), admin.getPassword())
                    ? proceedWithLogin(admin)
                    : Helper.buildHttpResponse(HttpStatus.NOT_FOUND, TRUE, INVALID_CREDENTIALS, null);
        }
    }

    @Override
    public ResponseEntity<Response> logout(String token) {
        shelterTokenEntityService.deleteToken(token);
        return Helper.buildHttpResponse(HttpStatus.OK, FALSE, LOGOUT_SUCCESSFULLY, null);
    }

    @Override
    public ResponseEntity<Response> get(String adminId) {
        if (ObjectId.isValid(adminId)) {
            ShelterAdmin user = shelterAdminRepository.findById(new ObjectId(adminId)).orElse(null);
            return user != null
                    ? Helper.buildHttpResponse(HttpStatus.OK, FALSE, SUCCESS, user)
                    : Helper.buildHttpResponse(HttpStatus.NOT_FOUND, TRUE, USER_NOT_FOUND, null);
        } else {
            return Helper.buildHttpResponse(HttpStatus.BAD_REQUEST, TRUE, INVALID_USER_ID, null);
        }
    }

    private String emailToLowerCase(ShelterAdmin admin) {
        return admin.getEmail().toLowerCase();
    }

    private ResponseEntity<Response> proceedRegistration(GlobalAppSettings appSettings, ShelterAdmin admin) {
        return registerWithoutEmailVerification(admin);
    }

    private ResponseEntity<Response> registerWithoutEmailVerification(ShelterAdmin admin) {
        Shelter shelter = shelterService.save(shelterService.buildShelter());
        admin.setShelterId(shelter.getId());
        shelterAdminRepository.save(admin);
        String token = shelterTokenEntityService.createToken(admin);
        ShelterTokenEntity tokenEntity = shelterTokenEntityService.save(token, admin.getId());

        return tokenEntity != null
                ? Helper.buildHttpResponse(HttpStatus.OK, FALSE, USER_REGISTERED, token)
                : Helper.buildHttpResponse(HttpStatus.SERVICE_UNAVAILABLE, TRUE, DATA_SAVING_FAILURE, null);
    }

    private ResponseEntity<Response> proceedWithLogin(ShelterAdmin dbUser) {
        return completeLogin(dbUser);
    }

    private ResponseEntity<Response> completeLogin(ShelterAdmin dbUser) {
        String token = shelterTokenEntityService.createToken(dbUser);
        if (token != null) {
            shelterTokenEntityService.save(token, dbUser.getId());
            return Helper.buildHttpResponse(HttpStatus.OK, FALSE, LOGIN_SUCCESSFULLY, token);
        } else {
            return Helper.buildHttpResponse(HttpStatus.INTERNAL_SERVER_ERROR, TRUE, TRY_AGAIN_LATER, null);
        }
    }
}
