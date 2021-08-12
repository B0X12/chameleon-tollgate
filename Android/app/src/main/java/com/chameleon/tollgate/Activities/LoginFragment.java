package com.chameleon.tollgate.Activities;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.chameleon.tollgate.R;

public class LoginFragment extends Fragment {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    private ServerActivity serverActivity;
    @Override
    public void onAttach(Context context){
        super.onAttach(context);
        serverActivity = (ServerActivity)getActivity();
    }
    @Override
    public void onDetach(){
        super.onDetach();
        serverActivity = null;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_login, container, false);
        Button btnToLogin = (Button)rootView.findViewById(R.id.button_login_2);
        btnToLogin.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                serverActivity.LoginAction();
            }
        });

        serverActivity.setLoginStep();

        return rootView;
    }

    @Override
    public void onStart() {
        super.onStart();

        serverActivity.initLogin();
    }
}