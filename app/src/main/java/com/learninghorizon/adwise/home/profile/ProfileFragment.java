package com.learninghorizon.adwise.home.profile;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
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
import com.learninghorizon.adwise.home.offers.util.RestUtil;
import com.learninghorizon.adwise.home.user.UserDataUtil;
import com.learninghorizon.adwise.loginsignup.AdWiseApplication;

/**
 * Fragment to display user profile
 */
public class ProfileFragment extends Fragment implements View.OnClickListener {

    private TextView usernameTextView;
    private ImageView profilePic;
    private FloatingActionButton profilePicFAB;
    private Button logout;
    private int RESULT_OK = 100;
    private String picturePath;
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
        SharedPreferences sharedPreferences = AdWiseApplication.getIntance().getSharedPreferences(
                AdWiseApplication.getIntance().getString(R.string.adwise_shared_preferences), Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        String imagePath = sharedPreferences.getString("profilepic", "");
        if(null != imagePath && !imagePath.isEmpty()) {
            loadImage(imagePath);
        }
    }

    private void loadImage(String imageUri){
        String[] filePath = { MediaStore.Images.Media.DATA };

        Cursor c = getActivity().getContentResolver().query(Uri.parse(imageUri),filePath, null, null, null);

        c.moveToFirst();

        int columnIndex = c.getColumnIndex(filePath[0]);

        picturePath = c.getString(columnIndex);

        c.close();

        Bitmap thumbnail = (BitmapFactory.decodeFile(picturePath));
        profilePic.setImageBitmap(thumbnail);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.profile_fab:{
                captureProfilePic();
                break;
            }
            case R.id.logout: {
                break;
            }
        }
    }

    private void captureProfilePic() {
        Intent i = new Intent(Intent.ACTION_PICK,android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(i, RESULT_OK);
    }

    public  void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {

            Uri selectedImage = data.getData();

            String[] filePath = { MediaStore.Images.Media.DATA };

            Cursor c = getActivity().getContentResolver().query(selectedImage,filePath, null, null, null);

            c.moveToFirst();

            int columnIndex = c.getColumnIndex(filePath[0]);

            picturePath = c.getString(columnIndex);

            c.close();

            Bitmap thumbnail = (BitmapFactory.decodeFile(picturePath));

            saveImage(selectedImage);
        }
    }

    private void saveImage(Uri selectedImage) {
        SharedPreferences adWisePreferences = AdWiseApplication.getIntance().getSharedPreferences(
                AdWiseApplication.getIntance().getString(R.string.adwise_shared_preferences), Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = adWisePreferences.edit();
        editor.putString("profilepic", selectedImage.toString());
        editor.commit();
    }
}
