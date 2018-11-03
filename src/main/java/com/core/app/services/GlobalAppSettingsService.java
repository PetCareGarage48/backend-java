package com.core.app.services;


import com.core.app.entities.database.user.GlobalAppSettings;

public interface GlobalAppSettingsService {

	GlobalAppSettings initializeAppSettings();

	GlobalAppSettings getAppSettings();

	GlobalAppSettings saveAppSettings();
}
