package com.segelzwerg.familyfotoandroid.familyfotoservice;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.accounts.AccountManagerFuture;
import android.accounts.AuthenticatorException;
import android.accounts.OperationCanceledException;
import android.os.Bundle;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class TestUserManager {

    public static final String ACCOUNT_TYPE = "com.segelzwerg.familyfotoandroid";
    public static final String TOKEN_VALUE = "token";
    private AccountManager accountManager;
    private LoginCredentials credentials;
    private Account account;
    private AccountManagerFuture<Bundle> accountManagerFuture;

    @BeforeEach
    public void setUp() throws AuthenticatorException, OperationCanceledException, IOException {
        accountManager = mock(AccountManager.class);
        credentials = new LoginCredentials("marcel", "123123");
        account = new Account(credentials.getUsername(), ACCOUNT_TYPE);
        accountManagerFuture = mock(AccountManagerFuture.class);
        Bundle bundle = mock(Bundle.class);
        when(bundle.getString(AccountManager.KEY_AUTHTOKEN)).thenReturn(TOKEN_VALUE);
        when(accountManagerFuture.getResult()).thenReturn(bundle);
        when(accountManager.getAuthToken(eq(account),
                eq(ACCOUNT_TYPE),
                eq(null),
                eq(true),
                eq(null),
                eq(null))).thenReturn(accountManagerFuture);
    }

    @Test
    public void saveAccount() {
        UserManager userManager = new UserManager(accountManager);
        Account savedUser = userManager.saveAccount(credentials);
        verify(accountManager, times(1))
                .addAccountExplicitly(any(Account.class),
                eq(credentials.getPassword()),
                eq(null));
        assertThat(savedUser).usingRecursiveComparison().isEqualTo(account);
    }
    @Test
    public void getAuthToken() throws Exception {
        UserManager userManager = new UserManager(accountManager);
        AuthToken expectedToken = new AuthToken(TOKEN_VALUE);
        AuthToken token = userManager.getAuthToken(account);
        assertThat(token).isEqualTo(expectedToken);
    }

    @Test
    public void getAuthTokenAuthError() throws AuthenticatorException, OperationCanceledException, IOException {
        when(accountManagerFuture.getResult()).thenThrow(AuthenticatorException.class);
        UserManager userManager = new UserManager(accountManager);
        assertThatExceptionOfType(ManagerExtractionException.class).isThrownBy(() ->
                userManager.getAuthToken(account));
    }

    @Test
    public void getAuthTokenIOError() throws AuthenticatorException, OperationCanceledException, IOException {
        when(accountManagerFuture.getResult()).thenThrow(IOException.class);
        UserManager userManager = new UserManager(accountManager);
        assertThatExceptionOfType(ManagerExtractionException.class).isThrownBy(() ->
                userManager.getAuthToken(account));
    }

    @Test
    public void getAuthTokenOpError() throws AuthenticatorException, OperationCanceledException, IOException {
        when(accountManagerFuture.getResult()).thenThrow(OperationCanceledException.class);
        UserManager userManager = new UserManager(accountManager);
        assertThatExceptionOfType(ManagerExtractionException.class).isThrownBy(() ->
                userManager.getAuthToken(account));
    }
}