package com.termex.pasto.Vue;

import java.text.DecimalFormat;
import java.util.ArrayList;

import DAO.PizzaManager;
import Metier.M_Pizzas;
import android.app.Activity;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View.MeasureSpec;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout.LayoutParams;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.termex.pasto.R;

public class Statistique extends Activity{
	
	private ArrayList<M_Pizzas> listPizza;
	private PizzaManager pm;
	private double totalPizza = 0, totalBoisson = 0;
	private RelativeLayout rlayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statistique);
        
        rlayout = (RelativeLayout) findViewById(R.id.layout_statistique); //Layout ou serra mis le tableau
        
        /*On prend la liste de toutes les pizzas dans la base de données*/
        pm = new PizzaManager(this);
        pm.open();
        listPizza = pm.allPizza(); //Pizza BDD dans list Pizza
        pm.close();
        
        
        String[] row = new String[listPizza.size()]; //Taille de la liste pour le nombre des lignes
        
        /*On parcour les pizzas pour prendre les noms des pizza à afficher pour le titre des lignes*/
        int i =0;
        for(M_Pizzas unePizza : listPizza){
        	
        	row[i] = unePizza.getNom();
        	i++;
        	
        	/*etablissement d'un total celon la catégorie boisson ou pizza*/
        	if(unePizza.getCategorie().equals("Boissons")){
        		
        		totalBoisson = totalBoisson + unePizza.getNbTotal(); //Total du nombre de boissons
        	}
        	else{
        		totalPizza = totalPizza + unePizza.getNbTotal(); //Total du nombre de pizzas
        	}
        }
        
        /*etablissement d'un pourcentage celon la catégorie boisson ou pizza*/
        for(M_Pizzas unePizza : listPizza){
        	double pourcentage = 0;
        	
        	if(unePizza.getCategorie().equals("Boissons")){
        		pourcentage = (double) (unePizza.getNbTotal()/totalBoisson)*100;
        	}
        	else{
        		pourcentage = (double) (unePizza.getNbTotal()/totalPizza)*100;
        		
        	}
        	
        	
        	unePizza.setStatistique(pourcentage);
        }
        
        
          String[] column = { "Jour", "Semaine", "Mois", "Année", "Total", "Statistique" }; //Titres des colonnes
          int rl=row.length+1; int cl=column.length+1; //Taille pour le nombre de lignes et de colonnes

          TableLayout tableLayout = createTableLayout(row, column,rl, cl); //Create tableau avec envoie données ligne, colonne et leur taille respectives
          rlayout.addView(tableLayout); //Ajout du tableau au layout
       }

       public void makeCellEmpty(TableLayout tableLayout, int rowIndex, int columnIndex) {
           // get row from table with rowIndex
           TableRow tableRow = (TableRow) tableLayout.getChildAt(rowIndex);

           // get cell from row with columnIndex
           TextView textView = (TextView)tableRow.getChildAt(columnIndex);

           // make it black
           textView.setBackgroundColor(Color.BLACK);
       }
       public void setHeaderTitle(TableLayout tableLayout, int rowIndex, int columnIndex){
        
           // get row from table with rowIndex
           TableRow tableRow = (TableRow) tableLayout.getChildAt(rowIndex);

           // get cell from row with columnIndex
           TextView textView = (TextView)tableRow.getChildAt(columnIndex);
           
           textView.setText("Hello");
       }

       private TableLayout createTableLayout(String [] rv, String [] cv,int rowCount, int columnCount) {
           // 1) Create a tableLayout and its params
    	   /*Création table layout*/
           TableLayout.LayoutParams tableLayoutParams = new TableLayout.LayoutParams();
           tableLayoutParams.width = tableLayoutParams.MATCH_PARENT;
           TableLayout tableLayout = new TableLayout(this);
           tableLayout.setStretchAllColumns(true);
           
           tableLayout.setBackgroundColor(Color.WHITE);//Border white

           // 2) create tableRow params
           TableRow.LayoutParams tableRowParams = new TableRow.LayoutParams();
           tableRowParams.width = tableRowParams.MATCH_PARENT; //Largeur parent
           tableRowParams.setMargins(0, 1, 0, 1); //Taille des bordures
           tableRowParams.weight = 1;

           int tailleCasePizza = 0;
           
           /*Parcours lignes*/
           for (int i = 0; i < rowCount; i++) {
               // 3) create tableRow
               TableRow tableRow = new TableRow(this);
               tableRow.setBackgroundColor(Color.WHITE);
               
               /*Parcours colonnes*/
               for (int j= 0; j < columnCount; j++) {
                   //create textView
                   TextView textView = new TextView(this);
                   /*Parametre du text view*/
                   textView.setBackgroundColor(Color.WHITE);
                   textView.setGravity(Gravity.CENTER);
                   textView.setTextSize(30);
                   
                    String s1 = Integer.toString(i);
			        String s2 = Integer.toString(j);
			        String s3 = s1 + s2;
			        int id = Integer.parseInt(s3);
			        
			        if(i%2 == 0){
			        	textView.setBackgroundColor(Color.rgb(209, 238, 238)); //Couleur une ligne sur deux
			        }
			        /*Condition concernant la cellule du tableau*/
                    if (i ==0 && j==0){//Premiere cellule
	                    textView.setText("PIZZA");
	                    textView.setTypeface(null, Typeface.BOLD);
	                    textView.setTextSize(50);
                     
                    } else if(i==0){//Ligne de titre
	                    textView.setText(cv[j-1]);
	                    textView.setTypeface(null, Typeface.BOLD);
	                    textView.setHeight(66);
	                    textView.setPadding(0, 20, 0, 13);
                    
                    }else if( j==0){//Colonne de titre
	                    textView.setText(rv[i-1]);
	                    textView.setPadding(10, 0, 10, 0);
	                    textView.setTypeface(null, Typeface.BOLD);
                    }else if( j==1){//Premiere colonne
                        textView.setText(""+listPizza.get(i-1).getNbClique());
                        textView.setTypeface(null, Typeface.NORMAL);
                    }else if( j==2){//Deuxième colonne
                        textView.setText(""+listPizza.get(i-1).getNbSemaine());
                    }else if( j==3){//Deuxième colonne
                        textView.setText(""+listPizza.get(i-1).getNbMois());
                    }else if( j==4){//Deuxième colonne
                        textView.setText(""+listPizza.get(i-1).getNbAnnee());
                    }else if( j==5){//Deuxième colonne
                        textView.setText(""+listPizza.get(i-1).getNbTotal());
                    }else if( j==6){//Dernière colonne
                    	DecimalFormat df = new DecimalFormat ( ) ; 
                    	df.setMaximumFractionDigits(2);//deux chiffre apèrs la virgule
                        textView.setText(""+df.format(listPizza.get(i-1).getStatistique())+" %");//affiche statistique en pourcentages
                    }else {
                    	textView.setText(""+id);
                    // check id=23
                    
                    if(id==23){
                    	textView.setText("ID=23");
                      
                     }
                    }

                   //add textView to tableRow
                   tableRow.addView(textView, tableRowParams);
               }

               //add tableRow to tableLayout
               tableLayout.addView(tableRow, tableLayoutParams);
           }

           return tableLayout;
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
