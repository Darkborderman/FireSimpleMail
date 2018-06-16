package ncku.firesimplemail;

import android.app.Activity;
import android.app.DialogFragment;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;

public class DropdownList {

    public Spinner spinner;
    public ArrayAdapter adapter;
    public ArrayList<String> options;
    private Activity act;
    private DropdownList self = this;

    public DropdownList(Activity activity) {
        act = activity;
        spinner = new Spinner(activity);
        options = new ArrayList<>();
        addDefaultOptions();
        adapter = new ArrayAdapter<> (activity,
            android.R.layout.simple_spinner_dropdown_item, options);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(itemSelectedHandler);
    }

    private void addDefaultOptions() {
        options.add("");
        options.add("<Add Item>");
        options.add("<Delete List>");
    }

    private AdapterView.OnItemSelectedListener itemSelectedHandler = new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            Toast.makeText(act, "Select:" + options.get(position), Toast.LENGTH_SHORT).show();

            switch(options.size() - position) {
                case 2:
                    NewOptionDialog dialog = new NewOptionDialog(self);
                    dialog.show(act.getFragmentManager(), "TBD");
                    break;
                case 1:
                    callback = (Callback) act;
                    callback.notifyDestruction(self);
                    break;
            }
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }
    };

    Callback callback;

    public interface Callback {
        void notifyDestruction(DropdownList dropdownList);
    }
}
