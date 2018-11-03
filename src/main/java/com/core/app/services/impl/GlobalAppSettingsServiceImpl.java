package com.core.app.services.impl;

import com.core.app.constants.GeneralConstants;
import com.core.app.entities.database.user.GlobalAppSettings;
import com.core.app.repositories.GlobalAppSettingsRepository;
import com.core.app.services.GlobalAppSettingsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GlobalAppSettingsServiceImpl implements GlobalAppSettingsService {

	private final GlobalAppSettingsRepository settingsRepository;

	@Autowired
	GlobalAppSettingsServiceImpl(GlobalAppSettingsRepository settingsRepository) {
		this.settingsRepository = settingsRepository;
	}

	@Override
	public GlobalAppSettings initializeAppSettings() {
		return getAppSettings() == null ? saveAppSettings() : null;
	}

	@Override
	public GlobalAppSettings getAppSettings() {
		return settingsRepository.findTopByOrderById();
	}

	@Override
	public GlobalAppSettings saveAppSettings() {
		return settingsRepository.save(buildSettings());
	}

	private GlobalAppSettings buildSettings() {
		return GlobalAppSettings
				.builder()
					.emailVerificationEnabled(GeneralConstants.FALSE)
				.build();
	}
}
