package angelcotes.viewmovil;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;

/**
 * Created by Alberto Mario Camargo Castro on 30-Apr-16.
 */
public class ContinueDialogFragment extends DialogFragment {

    private String message, option;
    public  Context context;

    public void setvalues(String message, String option, Context context) {
        this.message = message;
        this.option = option;
        this.context = context;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the Builder class for convenient dialog construction
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        builder.setCancelable(false);

        builder.setMessage(this.message)
                .setPositiveButton(this.option, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                        MenuActivity.gameManagement.newGame();
                        GameActivity.setValues();
                        GameActivity.target.setBackgroundDrawable(getResources().getDrawable(R.drawable.border2));
                    }
                })
                .setNegativeButton("Volver al menu", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // Call Activity

                        ((Activity) ContinueDialogFragment.this.context).finish();
                    }
                });
        setCancelable(false);

        // Create the AlertDialog object and return
        return builder.create();
    }
}
