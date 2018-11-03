package com.core.app.services;


import com.core.app.entities.database.User;

import java.util.concurrent.CompletableFuture;

public interface MailService {

	CompletableFuture<String> sendSignUpConfirmation(User user);

	CompletableFuture<String> sendSignUpCompleted(User user);
}
