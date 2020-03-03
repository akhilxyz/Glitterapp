package com.akhilmca.glitterapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class SignupActivity extends AppCompatActivity {
    EditText fullname , password, e_mail ,username;
    Button register;

    FirebaseAuth auth;
    DatabaseReference reference;
    ProgressDialog pd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        TextView tv_login = findViewById(R.id.login_txt);
        FirebaseApp.initializeApp(this);


//___________________________ID's____________________________________________

        fullname = findViewById(R.id.fullName);
        password = findViewById(R.id.password);
        e_mail = findViewById(R.id.userEmailId);
        username = findViewById(R.id.user_name);
        register = findViewById(R.id.signUpBtn);
        auth = FirebaseAuth.getInstance();


//________________________Login Button________________________________________


        tv_login.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                startActivity(new Intent(SignupActivity.this, LoginActivity.class));
                finish();
            }
        });
//__________________________SignUp Button______________________________________

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pd = new ProgressDialog(SignupActivity.this);
                pd.setMessage("Please Wait....");
                pd.show();

                String str_fullname = fullname.getText().toString();
                String str_username = username.getText().toString();
                String str_password = password.getText().toString();
                String str_email = e_mail.getText().toString();

//____________________________SignUP_Fields_Validation___________________________

                if (TextUtils.isEmpty(str_fullname) || TextUtils.isEmpty(str_username) || TextUtils.isEmpty(str_email) || TextUtils.isEmpty(str_password)) {
                    Toast.makeText(SignupActivity.this, "All fields are required !", Toast.LENGTH_SHORT).show();
                } else if (str_password.length() < 6) {
                    Toast.makeText(SignupActivity.this, "Password must have 6 characters", Toast.LENGTH_SHORT).show();



                }
                else{
                    register(str_username,str_fullname,str_email,str_password );

                }
            }
        });
    }

        public void register(final String username , final String fullname , final String e_mail , final String password )
        {
            auth.createUserWithEmailAndPassword(e_mail,password).addOnCompleteListener(SignupActivity.this, new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if(task.isSuccessful())
                    {
                        FirebaseUser firebaseUser = auth.getCurrentUser();
                        String userid = firebaseUser.getUid();
                         reference = FirebaseDatabase.getInstance().getReference().child("Users").child(userid);

                        HashMap<String,Object> hashMap = new HashMap<>();
                        hashMap.put("id",userid);
                        hashMap.put("username",username.toLowerCase());
                        hashMap.put("fullname",fullname);
                        hashMap.put("bio","");
                        hashMap.put("image","https://firebasestorage.googleapis.com/v0/b/glitterapplication-5a494.appspot.com/o/place_holder.png?alt=media&token=e4a79002-6703-43be-a68d-62c425398d73");
                        reference.setValue(hashCode()).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {

                                if(task.isSuccessful())
                                {
                                    pd.dismiss();
                                     Intent intent= new Intent(SignupActivity.this, MainActivity.class);
                                     intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK| Intent.FLAG_ACTIVITY_NEW_TASK);
                                     startActivity(intent);
                                }
                            }
                        });

                    }
                    else {
                        pd.dismiss();
                        Toast.makeText(SignupActivity.this, "You can't register with this email and password", Toast.LENGTH_SHORT).show();
                    }

                }
            });
        }


}
