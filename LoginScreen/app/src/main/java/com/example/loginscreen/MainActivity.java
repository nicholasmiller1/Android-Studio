package com.example.loginscreen;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.TooltipCompat;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class MainActivity extends AppCompatActivity {

    private EditText usernameInput;
    private EditText passwordInput;
    private CheckBox showPassword;
    private Button login;
    private Button signup;
    private TextView error;
    private ImageView question;

    private Pattern p = Pattern.compile("((?=.*[a-z]+)(?=.*[A-Z]+)(?=.*\\d+)(?=.*[!@#$%*?.]+))[a-zA-Z\\d!@#$%*?.]{8,16}");
    private Matcher m;

    private FirebaseAuth auth;
    private Gson gson;
    private Map<String, String> userData;

    private static final String FILE_NAME = "data.json";
    public static final String TEXT_IDENTIFIER = "text";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        createAuth();
        createData();
        createViews();

        Log.i("TEST", userData.toString());
    }

    private void createAuth() {
        auth = FirebaseAuth.getInstance();
    }

    private void createData() {
        gson = new GsonBuilder().setPrettyPrinting().create();
        Type listType = new TypeToken<Map<String, String>>(){}.getType();

        try {
            FileInputStream fileInputStream = openFileInput(FILE_NAME);
            int size = fileInputStream.available();
            byte[] buffer = new byte[size];
            fileInputStream.read(buffer);
            String json = new String(buffer);
            fileInputStream.close();

            userData = gson.fromJson(json, listType);

            Log.i("TEST", userData.toString());
        }
        catch ( Exception e ) {
            e.printStackTrace();
        }

        if (userData == null) {
            userData = new TreeMap<>();
            try {
                String newJson = gson.toJson(userData, listType);
                FileOutputStream fileOutputStream = openFileOutput(FILE_NAME, Context.MODE_PRIVATE);
                fileOutputStream.write(newJson.getBytes());
                fileOutputStream.close();
            }
            catch ( Exception error ) {
                error.printStackTrace();
            }
        }
    }

    private void createViews() {
        passwordInput = findViewById(R.id.editTextPasswordInput);
        usernameInput = findViewById(R.id.editTextUsernameInput);
        showPassword = findViewById(R.id.checkBoxShow);
        login = findViewById(R.id.buttonLogin);
        signup = findViewById(R.id.buttonSignup);
        error = findViewById(R.id.textViewPasswordError);
        question = findViewById(R.id.imageViewQuestion);

        error.setAlpha(0.0f);
        TooltipCompat.setTooltipText(question, getString(R.string.tooltip));

        showPassword.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    passwordInput.setTransformationMethod(new HideReturnsTransformationMethod());
                }
                else {
                    passwordInput.setTransformationMethod(new PasswordTransformationMethod());
                }
            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startLogin();
            }
        });

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startSignup();
            }
        });
    }

    private void startLogin() {
        if (validateUsername() && validatePassword(false)) {
            String email = usernameInput.getText().toString() + "@gmail.com";
            String password = passwordInput.getText().toString();

            Log.i("TEST3", userData.toString());

            auth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {
                        Intent intent = new Intent(MainActivity.this, AccountScreen.class);

                        String userID = auth.getUid();
                        if (userData.keySet().contains(userID)) {
                            intent.putExtra(TEXT_IDENTIFIER, userData.get(userID));
                        }

                        startActivityForResult(intent, 1);

                        usernameInput.setText("");
                        passwordInput.setText("");
                    }
                    else {
                        task.getException().printStackTrace();
                        error.setText(R.string.error_message_wrong_password);
                        error.animate().alpha(1.0f).setDuration(100);
                    }
                }
            });
        }
    }

    private void startSignup() {
        if (validateUsername() && validatePassword(true)) {
            String email = usernameInput.getText().toString() + "@gmail.com";
            String password = passwordInput.getText().toString();

            auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {
                        userData.put(auth.getUid(), "");

                        try {
                            String newJson = gson.toJson(userData);

                            Log.i("TEST", newJson);

                            FileOutputStream fileOutputStream = openFileOutput(FILE_NAME, Context.MODE_PRIVATE);
                            fileOutputStream.write(newJson.getBytes());
                            fileOutputStream.close();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                        try {
                            FileInputStream fileInputStream = openFileInput(FILE_NAME);
                            int size = fileInputStream.available();
                            byte[] buffer = new byte[size];
                            fileInputStream.read(buffer);
                            String json = new String(buffer);
                            fileInputStream.close();

                            Log.i("TEST2", json);
                        }
                        catch (Exception e) {
                            e.printStackTrace();
                        }

                        Intent intent = new Intent(MainActivity.this, AccountScreen.class);

                        String userID = auth.getUid();
                        if (userData.keySet().contains(userID)) {
                            intent.putExtra(TEXT_IDENTIFIER, userData.get(userID));
                        }

                        startActivityForResult(intent, 1);

                        usernameInput.setText("");
                        passwordInput.setText("");
                    }
                    else {
                        task.getException().printStackTrace();

                        try {
                            throw task.getException();
                        } catch(FirebaseAuthWeakPasswordException e) {
                            error.setText(R.string.error_message_weak_password);
                            error.animate().alpha(1.0f).setDuration(100);
                        } catch(FirebaseAuthInvalidCredentialsException e) {
                            error.setText(R.string.error_message_invalid_username);
                            error.animate().alpha(1.0f).setDuration(100);
                        } catch(FirebaseAuthUserCollisionException e) {
                            error.setText(R.string.error_message_user_exists);
                            error.animate().alpha(1.0f).setDuration(100);
                        } catch(Exception e) {
                            Log.e("ERROR", e.getMessage());
                        }
                    }
                }
            });
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (resultCode == RESULT_CANCELED) {
            return;
        }

        if (resultCode == 1) {
            String userID = auth.getUid();

            if (data.getStringExtra(AccountScreen.TEXT_RETURN_IDENTIFIER) == null) {
                userData.replace(userID, "");
            }
            else {
                userData.replace(userID, data.getStringExtra(AccountScreen.TEXT_RETURN_IDENTIFIER));
            }
            auth.signOut();

            try {
                String newJson = gson.toJson(userData);

                FileOutputStream fileOutputStream = openFileOutput(FILE_NAME, Context.MODE_PRIVATE);
                fileOutputStream.write(newJson.getBytes());
                fileOutputStream.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private boolean validatePassword(boolean checkValidity) {
        m = p.matcher(passwordInput.getText().toString());

        if (passwordInput.getText() == null || passwordInput.getText().toString().equals("")) {
            error.setText(R.string.error_message_no_password);
            error.animate().alpha(1.0f).setDuration(100);
            return false;
        }
        else if (checkValidity && !m.matches()) {
            error.setText(R.string.error_message_invalid);
            error.animate().alpha(1.0f).setDuration(100);
            return false;
        }
        else {
            return true;
        }
    }

    private boolean validateUsername() {
        if (usernameInput.getText() == null || usernameInput.getText().toString().equals("")) {
            error.setText(R.string.error_message_no_username);
            error.animate().alpha(1.0f).setDuration(100);
            return false;
        }
        else {
            return true;
        }
    }

    private void clearUserData() {
        try {
            FileOutputStream fileOutputStream = openFileOutput(FILE_NAME, Context.MODE_PRIVATE);
            fileOutputStream.write("".getBytes());
            fileOutputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
