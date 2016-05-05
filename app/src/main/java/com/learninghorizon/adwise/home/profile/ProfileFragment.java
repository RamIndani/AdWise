package com.learninghorizon.adwise.home.profile;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.learninghorizon.adwise.R;
import com.learninghorizon.adwise.home.user.UserDataUtil;

/**
 * Fragment to display user profile
 */
public class ProfileFragment extends Fragment implements View.OnClickListener {

    private TextView usernameTextView;
    private ImageView profilePic;
    private FloatingActionButton profilePicFAB;
    private Button logout;
    public ProfileFragment() {
        // Required empty public constructor
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_profile, container, false);
         usernameTextView = (TextView) view.findViewById(R.id.username);
         profilePic = (ImageView) view.findViewById(R.id.profile_pic);
         profilePicFAB = (FloatingActionButton) view.findViewById(R.id.profile_fab);
         logout = (Button) view.findViewById(R.id.logout);
        profilePicFAB.setOnClickListener(this);
        logout.setOnClickListener(this);
        setProfileData();
        return view;
    }

    private void setProfileData(){
        if(null != usernameTextView){
            try {
                usernameTextView.setText(UserDataUtil.getInstance().getUserEmailId());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        if(null != profilePic){
            loadProfilePic();
        }
    }

    private void loadProfilePic() {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.profile_fab:{
                break;
            }
            case R.id.logout: {
                break;
            }
        }
    }
}
