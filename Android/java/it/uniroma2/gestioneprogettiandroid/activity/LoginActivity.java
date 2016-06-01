package it.uniroma2.gestioneprogettiandroid.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import it.uniroma2.gestioneprogettiandroid.MainContext;
import it.uniroma2.gestioneprogettiandroid.R;
import it.uniroma2.gestioneprogettiandroid.server.ISessionServer;
import it.uniroma2.gestioneprogettiandroid.server.IUserServer;
import it.uniroma2.gestioneprogettiandroid.tasks.LoginTask;
import it.uniroma2.gestioneprogettiandroid.tasks.params.LoginParams;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        MainContext mainContext = ((MainContext) getApplication());

        final IUserServer userServer = mainContext.getUserServer();
        final ISessionServer sessionServer = mainContext.getSessionServer();

        // Prendo il pulsante presente nell'activity_login
        Button loginButton = (Button) findViewById(R.id.login_loginButton);
        final Spinner profileSpinner = (Spinner) findViewById(R.id.login_profileSpinner);
        final EditText usernameEditText = (EditText) findViewById(R.id.login_username);
        final EditText passwordEditText = (EditText) findViewById(R.id.login_password);
        /*
         @TODO
         Da capire come utilizzare questo menu a tendina
         */
        /* Popolo il menu a tendina con i profili definiti in strings.xml (login_profiles) .*/
        final ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.login_profiles, android.R.layout.simple_spinner_item);

        // Seleziono il layout da usare (uno di default)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // Uso l'adapter
        profileSpinner.setAdapter(adapter);

        // Questo metodo imposta un evento quando viene cliccato il pulsante
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast toast = ((MainContext) LoginActivity.this.getApplicationContext()).getToast();
                int profile = profileSpinner.getSelectedItemPosition();
                if (profile == profileSpinner.INVALID_POSITION) {
                    toast.setText("Specificare un profilo");
                    toast.show();
                    return;
                }
                profile++;

                if (profile != 4) {
                    toast.setText("L'accesso con questo profilo non è stato implementato.");
                    toast.show();
                    return;
                }

                String username = usernameEditText.getText().toString();
                String password = passwordEditText.getText().toString();
                new LoginTask(LoginActivity.this).execute(new LoginParams(username, password, profile));
            }
        });


    }
}
