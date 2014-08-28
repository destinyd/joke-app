package dd.android.joke.fragment.base;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import dd.android.joke.R;
import roboguice.fragment.RoboFragment;

/**
 * Created by dd on 14-8-27.
 */
public class FragmentBase extends RoboFragment {

    protected Activity activity;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        this.activity = activity;
    }

    ProgressDialog progressDialog = null;

    protected void progressDialogDismiss() {
        if (progressDialog != null) {
            progressDialog.dismiss();
            progressDialog = null;
        }
    }

    protected void progressDialogShow() {
        progressDialogShow(activity, "正在拼命读取中...");
    }

    protected void progressDialogShow(Activity activity) {
        progressDialogShow(activity, "正在拼命读取中...");
    }

    protected void progressDialogShow(Activity activity, String message) {
        progressDialogShow(activity, message, true);
    }

    protected void progressDialogShow(Activity activity, String message, boolean cancelable) {
        progressDialogDismiss();
        progressDialog = ProgressDialog.show(activity, "", message, true, cancelable);
        progressDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialogInterface) {
                progressDialogDismiss();
            }
        });
    }

    @Override
    public void onDestroyView() {
        progressDialogDismiss();
        super.onDestroyView();
    }
}
