package com.jrodolfo.firebaseuserpwauth;

import android.app.Activity;
import android.app.AlertDialog;
import android.view.LayoutInflater;

class LoadingAnimation {

    private Activity activity;
    private AlertDialog dialog;

    LoadingAnimation(Activity activity) {
        this.activity = activity;
    }

    void loadingAnimationDialog() {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(activity);
        LayoutInflater inflater = activity.getLayoutInflater();
        alertDialogBuilder.setView(inflater.inflate(R.layout.loading, null));
        alertDialogBuilder.setCancelable(false);
        dialog = alertDialogBuilder.create();
        dialog.show();
    }

    void dismissLoadingAnimation() {
        dialog.dismiss();
    }
}
