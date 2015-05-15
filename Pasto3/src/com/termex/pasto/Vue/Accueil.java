package com.termex.pasto.Vue;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.termex.pasto.R;


public class Accueil extends Activity {

	private Button btnPizzas, btnstats;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_accueil);
        
        btnPizzas = (Button) findViewById(R.id.btnPizza);
        btnstats = (Button) findViewById(R.id.btnStats);
        
        
        btnPizzas.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				//Lors du clique sur le bouton on ouvre l'activité pizzas
				Intent intent = new Intent(Accueil.this, Pizzas.class);
				startActivity(intent);
			}
		});
        
        btnstats.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				//Lors du clique sur le bouton on ouvre l'activité pizzas
				Intent intent = new Intent(Accueil.this, Statistique.class);
				startActivity(intent);
			}
		});
        
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.accueil, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
