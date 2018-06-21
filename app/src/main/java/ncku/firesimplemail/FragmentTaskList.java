package ncku.firesimplemail;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.content.Intent;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import java.util.ArrayList;
import FSMServer.*;
import static ncku.firesimplemail.ActivityLogin.client;


public class FragmentTaskList extends Fragment {

    TaskHead[] th;

    private ArrayList<TaskHead> tasks = new ArrayList<>();
    private ArrayList<String> taskTitles = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.layout_task_list, container, false);

        Thread thread = new Thread(connect);
        thread.start();

        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        if(th!=null)
        {
            for(int i=0;i<=th.length-1;i++)
            {
                tasks.add(th[i]);
                taskTitles.add(th[i].getTitle());
            }

            ArrayAdapter<String> adapter = new ArrayAdapter<> (getActivity(),
                    android.R.layout.simple_list_item_1, taskTitles);
            ListView listView = rootView.findViewById(R.id.listView);
            listView.setAdapter(adapter);
            listView.setOnItemClickListener(newTaskClickedHandler);
        }


        return rootView;
    }

    private AdapterView.OnItemClickListener newTaskClickedHandler = new AdapterView.OnItemClickListener() {
        public void onItemClick(AdapterView parent, View v, int position, long id) {
            Intent myIntent = new Intent(getActivity(), ActivityTaskWrite.class);
            TaskHead selected = tasks.get(position);
            myIntent.putExtra("ID",selected.getId());
            myIntent.putExtra("Operation","update");
            startActivity(myIntent);
        }
    };

    private Runnable connect = new Runnable() {
        public void run() {
            th=client.getAllTask();
        }
    };
}
