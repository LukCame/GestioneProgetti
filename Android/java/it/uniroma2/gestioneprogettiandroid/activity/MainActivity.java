package it.uniroma2.gestioneprogettiandroid.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import it.uniroma2.gestioneprogettiandroid.MainContext;
import it.uniroma2.gestioneprogettiandroid.R;
import it.uniroma2.gestioneprogettiandroid.server.IProjectServer;
import it.uniroma2.gestioneprogettiandroid.server.ISessionServer;
import it.uniroma2.gestioneprogettiandroid.server.IUserServer;
import it.uniroma2.gestioneprogettiandroid.tasks.GetProjectsTask;
import it.uniroma2.gestioneprogettiandroid.tasks.LogoutTask;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        MainContext mainContext = ((MainContext) getApplication());

        final IProjectServer projectServer = mainContext.getProjectServer();
        final IUserServer userServer = mainContext.getUserServer();
        final ISessionServer sessionServer = mainContext.getSessionServer();

        final ListView listView = (ListView) findViewById(R.id.main_listView); // prendo la listView definita in activity_main
        Button logoutButton = (Button) findViewById(R.id.main_logoutButton);
        Button refreshButton = (Button) findViewById(R.id.main_refreshButton);

        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new LogoutTask(MainActivity.this).execute();
            }
        });

        refreshButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new GetProjectsTask(MainActivity.this).execute();
            }
        });
    }

    @Override
    protected void onResume()
    {
        super.onResume();

        new GetProjectsTask(MainActivity.this).execute();
    }

}
