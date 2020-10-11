package com.segelzwerg.familyfotoandroid.ui;

import android.content.Context;
import android.content.Intent;

import com.segelzwerg.familyfotoandroid.familyfotoservice.AuthToken;

import lombok.SneakyThrows;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * Handles call backs after login requests.
 * @param <T> token that is returned from server
 */
class LoginCallBack<T extends AuthToken> implements Callback<AuthToken> {
    /**
     * Context from where the Callback is called.
     */
    private final transient Context context;

    LoginCallBack(Context context) {
        this.context = context;
    }

    /**
     * Redirects to MainActivity.
     * {@inheritDoc}
     */
    @Override
    public void onResponse(Call<AuthToken> call, Response<AuthToken> response) {
        Intent intent = new Intent(context, MainActivity.class);
        context.startActivity(intent, null);
    }

    /**
     * {@inheritDoc}
     **/
    @SneakyThrows
    @Override
    public void onFailure(Call<AuthToken> call, Throwable throwable) {
        throw throwable;
    }

}