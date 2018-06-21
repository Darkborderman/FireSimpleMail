package ncku.firesimplemail;

import android.app.Activity;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

public class NewOptionDialog extends DialogFragment{

    private DropdownList dropdownList;

    public NewOptionDialog() {
        super();
        dropdownList = null;
    }

    public NewOptionDialog(DropdownList list) {
        super();
        dropdownList = list;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        LayoutInflater inflater = getActivity().getLayoutInflater();
        final View view = inflater.inflate(R.layout.new_option_prompt, null);
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setView(view);
        builder.setTitle(R.string.new_option_title);
        builder.setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {
                EditText editText = view.findViewById(R.id.editText);
                dropdownList.options.add(1, editText.getText().toString());
                dropdownList.adapter.notifyDataSetChanged();
                dropdownList.spinner.setSelection(1);
                listener.onDialogPositiveClick();
            }
        });
        builder.setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                listener.onDialogNegativeClick();
            }
        });

        return builder.create();
    }

    public interface Callback {
        void onDialogPositiveClick();
        void onDialogNegativeClick();
    }

    Callback listener;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        // Verify that the host activity implements the callback interface
        try {
            // Instantiate the Callback so we can send events to the host
            listener = (Callback) activity;
        } catch (ClassCastException e) {
            // The activity doesn't implement the interface, throw exception
            throw new ClassCastException(activity.toString()
                    + " must implement Callback");
        }
    }

}
