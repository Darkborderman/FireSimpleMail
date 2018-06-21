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
import android.widget.Toast;

import java.util.ArrayList;
import FSMServer.*;
import static ncku.firesimplemail.ActivityLogin.client;

public class FragmentMailList extends Fragment {

    private ArrayList<MailHead> mails = new ArrayList<>();
    private ArrayList<String> mailTitles=new ArrayList<>();
    MailHead[] mh;
    Button logoutButton,deleteAllMailButton;
    boolean result;

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
            listView.setOnItemLongClickListener(deleteMailHandler);
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
                Intent myIntent = new Intent(getActivity(), ActivityFacilityList.class);
                startActivity(myIntent);
            }
        });

        //logout button
        logoutButton = rootView.findViewById(R.id.logoutButton);
        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Thread thread = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        result=client.logout();
                    }
                });
                thread.start();
                try {
                    thread.join();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                if(result){
                    Intent myIntent = new Intent(getActivity(), ActivityLogin.class);
                    startActivity(myIntent);
                }
                else{
                    Toast toast = Toast.makeText(getActivity(),"Logout Failed", Toast.LENGTH_SHORT);
                    toast.show();
                }

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
    private AdapterView.OnItemLongClickListener deleteMailHandler=new AdapterView.OnItemLongClickListener() {
        @Override
        public boolean onItemLongClick(AdapterView parent, View view, int position, long id) {

            final MailHead selected = mails.get(position);
            Thread thread=new Thread(new Runnable() {
                @Override
                public void run() {
                    result=client.deleteMail(selected.getId());
                }
            });
            thread.start();
            try {
                thread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            if(result){
                Intent myIntent = new Intent(getActivity(), ActivityFacilityList.class);
                startActivity(myIntent);
                Toast.makeText(getActivity(),"Remove success",Toast.LENGTH_SHORT).show();
            }
            else{
                Toast.makeText(getActivity(),"Remove failed",Toast.LENGTH_SHORT).show();
            }
            return false;
        }
    };

    private Runnable connect = new Runnable() {
        public void run() {
            mh=client.getAllMail();
        }
    };

}