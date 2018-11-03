package com.core.app.startup;

import com.core.app.services.AdminService;
import com.core.app.services.GlobalAppSettingsService;
import com.core.app.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class OnStartup implements CommandLineRunner {

	private final GlobalAppSettingsService settingsService;
	private final AdminService adminService;

	@Autowired
	OnStartup(GlobalAppSettingsService settingsService,
	          AdminService adminService) {
		this.settingsService = settingsService;
		this.adminService = adminService;
	}

	@Override
	public void run(String... strings) throws Exception {
		settingsService.initializeAppSettings();
		adminService.registerSuperAdminAccount();
	}
}
