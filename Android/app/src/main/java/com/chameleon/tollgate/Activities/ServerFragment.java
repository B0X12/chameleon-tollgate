package com.chameleon.tollgate.Activities;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.chameleon.tollgate.R;
import com.chameleon.tollgate.login.MainActivity;

public class ServerFragment extends Fragment {
    interface LoginTask{
        public void beforeSetService(AppCompatActivity activity);
    }

    public ServerFragment() {
        // Required empty public constructor
    }

    private Button button_server;
    private EditText edit_server;
    private boolean isOpenedBefore;

    private ServerActivity serverActivity;
    @Override
    public void onAttach(Context context){
        super.onAttach(context);
        serverActivity = (ServerActivity)getActivity();
        System.out.println("1");
    }

    @Override
    public void onDetach(){
        super.onDetach();
        serverActivity = null;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        isOpenedBefore = false;
        System.out.println("2");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        System.out.println("3");
        // Inflate the layout for this fragment
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_server, container, false);
        edit_server = (EditText) rootView.findViewById(R.id.edit_IP);
        edit_server.setOnKeyListener(new View.OnKeyListener(){
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if(keyCode != KeyEvent.KEYCODE_ENTER)
                    return false;
//                serverActivity.helloServer(true);
                return false;
            }
        });
        button_server = (Button)rootView.findViewById(R.id.button_server);
        button_server.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                serverActivity.helloServer(true);
            }
        });

        serverActivity.setServerStep();

        return rootView;
    }

    @Override
    public void onStart() {
        super.onStart();

        serverActivity.checkServer();
        serverActivity.helloServer(isOpenedBefore);
        isOpenedBefore = true;

    }
}