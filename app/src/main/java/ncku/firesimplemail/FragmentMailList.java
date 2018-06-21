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
import android.widget.Button;
import android.widget.ListView;
import java.util.ArrayList;
import FSMServer.*;
import static ncku.firesimplemail.ActivityLogin.client;

public class FragmentMailList extends Fragment {

    private ArrayList<MailHead> mails = new ArrayList<>();
    private ArrayList<String> mailTitles=new ArrayList<>();
    MailHead[] mh;
    boolean result;
    Button deleteAllMailButton;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.layout_mail_list, container, false);

        Thread thread = new Thread(connect);
        thread.start();
        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        if(mh!=null)
        {
            for(int i=0;i<=mh.length-1;i++)
            {
                mails.add(mh[i]);
                mailTitles.add(mh[i].getTitle());
            }

            ArrayAdapter<String> adapter = new ArrayAdapter<String> (getActivity(),
                    android.R.layout.simple_list_item_1, mailTitles);
            ListView listView = rootView.findViewById(R.id.listView2);
            listView.setAdapter(adapter);
            listView.setOnItemClickListener(newMailClickedHandler);
        }

        deleteAllMailButton = rootView.findViewById(R.id.deleteAllMailButton);
        deleteAllMailButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Thread thread = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        if(mh!=null)
                        {

                            for(int i=0;i<=mh.length-1;i++)
                            {
                                client.deleteMail(mh[i].getId());
                            }
                            result=true;
                        }
                        else{
                            result=false;
                        }
                    }
                });
                thread.start();
                try {
                    thread.join();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                // try to refresh page after delete all
                Intent myIntent = new Intent(getActivity(), FragmentMailList.class);
                startActivity(myIntent);
            }
        });

        return rootView;
    }


    private AdapterView.OnItemClickListener newMailClickedHandler = new AdapterView.OnItemClickListener() {
        public void onItemClick(AdapterView parent, View v, int position, long id) {
            Intent myIntent = new Intent(getActivity(), ActivityMailView.class);
            MailHead selected = mails.get(position);
            myIntent.putExtra("ID", selected.getId());
            startActivity(myIntent);
        }
    };

    private Runnable connect = new Runnable() {
        public void run() {
            mh=client.getAllMail();
        }
    };

}