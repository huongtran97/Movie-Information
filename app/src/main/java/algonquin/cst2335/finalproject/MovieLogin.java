package algonquin.cst2335.finalproject;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class MovieLogin extends AppCompatActivity {
    /**
     *
     */

        private static final String TAG = "MovieLogin";

        private SharedPreferences mPreferences;
        private SharedPreferences.Editor mEditor;

        private EditText mName, mPassword;
        private Button btnLogin;

    /**
     *
     * @param savedInstanceState
     */
        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.movie_login);
            mName = (EditText) findViewById(R.id.name);
            mPassword = (EditText) findViewById(R.id.inputPass);
            btnLogin = (Button) findViewById(R.id.loginBtn);

            mPreferences = PreferenceManager.getDefaultSharedPreferences(this);
            mEditor = mPreferences.edit();

            checkSharedPreferences();
            /**
             *
             */
            btnLogin.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String nameUser = mName.getText().toString();
                    String passWord = mPassword.getText().toString();

                    if (nameUser.equals("") || passWord.equals("")) {
                        if (nameUser.equals("")) {
                            Toast.makeText(MovieLogin.this, "How can I call you?",
                                    Toast.LENGTH_LONG).show();

                        }
                        if (passWord.equals("")) {
                            Toast.makeText(MovieLogin.this, "What is your password?",
                                    Toast.LENGTH_LONG).show();

                        }

                    }
                    if(CheckPassword(passWord)) {
                        AlertDialog accept = new AlertDialog.Builder(MovieLogin.this)
                                .setTitle("Yooo!!!!!")
                                .setMessage("Good movies are waiting for you, let's enjoy them!!")
                                .setView(new ProgressBar(MovieLogin.this))
                                .show();

                        //save the name
                        String name = mName.getText().toString();
                        mEditor.putString(getString(R.string.name), name);
                        mEditor.commit();

                        //save the password
                        String password = mPassword.getText().toString();
                        mEditor.putString(getString(R.string.password), password);
                        mEditor.commit();

                        Intent i = new Intent(getApplicationContext(), FindMovie.class);
                        startActivity(i);
                    }
                }
            });


        }

    /**
     *
     * @param passWord
     * @return
     */
    private boolean CheckPassword(String passWord) {
        boolean foundUpperCase, foundLowerCase, foundNumber, foundSpecial;
        foundUpperCase = foundLowerCase = foundNumber = foundSpecial = false;
        for(int i = 0; i < passWord.length(); i++) {
            Character chr = passWord.charAt(i);
            if(Character.isDigit(chr))  foundNumber = true;
            if(Character.isUpperCase(chr))  foundUpperCase = true;
            if(Character.isLowerCase(chr))  foundLowerCase = true;
            if(isSpecialCharacter(chr)) foundSpecial = true;

        }

        if (!foundUpperCase) {
            Toast.makeText(this, "You are missing an upper case letter!", Toast.LENGTH_LONG)// Say that they are missing an upper case letter;
                    .show();
            return false;
        } else if (!foundLowerCase) {
            Toast.makeText(this, "You are missing a lower case letter!", Toast.LENGTH_LONG) // Say that they are missing a lower case letter;
                    .show();
            return false;
        } else if (!foundNumber) {
            Toast.makeText(this, "You are missing number in your password!", Toast.LENGTH_LONG) // Say that they are missing a number;
                    .show();
            return false;
        } else if (!foundSpecial) {
            Toast.makeText(this, "Your password does not have a special symbol!", Toast.LENGTH_LONG) // Say that they are missing a special symbol;
                    .show();
            return false;
        } else

            return true;//only get here if they're all true
    }

    /**
     *
     * @param c
     * @return
     */
    private boolean isSpecialCharacter(char c) {
        //return true if c is one of: #$%^&*!@?
        //return false otherwise
        switch (c) {
            case '#':
            case '?':
            case '*':
            case '$':
            case '%':
            case '^':
            case '&':
            case '!':
            case '@':
                return true;
            default:
                return false;
        }
    }

    /**
     *
     */
    private void checkSharedPreferences() {
        String name = mPreferences.getString(getString(R.string.name), "");
        String password = mPreferences.getString(getString(R.string.password), "");

        mName.setText(name);
        mPassword.setText(password);
    }
}

