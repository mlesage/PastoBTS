package com.termex.pasto.Vue;

import java.util.ArrayList;
import java.util.Calendar;

import DAO.PizzaManager;
import Metier.M_Pizzas;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.view.ViewPager.LayoutParams;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.termex.pasto.R;

public class Pizzas extends Activity{
	
	//L'accès a la base de données
	private PizzaManager pm = new PizzaManager(this);

	private String[] tabCategories = {"Pizza à base de Tomate",
									  "Pizza à base de Crème Fraîche",
									  "Pizza à base de Truffes Noires",
									  "Pizza à base de Moutarde à l'ancienne", //Tableau de catégories
									  "Spécialité de Pasto",
									  "Pizza Dessert",
									  "Pizza Bambino",
									  "Boisson"};
	
	private String[] tabPizzasTomate ={"Marguerita", "Regina", "La Biloute", "Royale",
								 "Buffalo Chevre", "Steak Auvergne", "Mythic bacon", "Chicken BBQ",
								 "Chorizo", "Merguez", "Hot Fever", "chevre Miel",
								 "5 Fromages", "Dauphinoise", "Raclette", "Tartiflette", //Tableau des noms de Pizzas Base Tomates 
								 "La Ch'ti", "Rustique au chevre","Bolognaise",  
								 "Chicken cheese", "Vegetariano","Burger", "Kebab", 
								 "Mexicaine", "Hawaïenne", "Royale Offerte", "Pizza du Moment", "Pizza Apéro"}; 
	private String[] tabPizzasCremeFraiche ={"Saumon", "Normande", "Indienne"}; //Tableau de pizzas base creme fraiche
	private String[] tabPizzasTruffes ={ "Venezia", "Milano"}; //Pizzas aux truffes
	private String[] tabPizzasMoutarde ={"Palermo", "Napoli", "Pisa"}; //Pizzas base moutarde
	private String[] tabPizzasSpecialite ={"La Parma", "La Savoie"}; //Pizza speciale
	private String[] tabPizzasDessert ={"Delice Poire", "Delice Ananas"}; //Pizza dessert
	private String[] tabPizzasBambino ={"Tête de Lapin", "Tête de Mickey"}; //Pizza Enfant
	private String[] tabVins = {"Gran Passione", "Chianti", "Rosato", "Bière", "Coca Cola"}; //Boissons

	private double[][] tabPrixTomates = {{7.50,12.50},{8,13.50},{10, 17.50},{8.50,14.50},{9,15.50},{9.50,16.50},{9,15.50},{9.50,16.50},{8.50,14.50},//8
								{8.50,14.50},{10, 17.50},{9.50,16.50},{9,15.50},{9.50,16.50},{9.50,16.50},{9.50,16.50},//7
								{10.50, 18.50},{9,15.50},{9.50,16.50},{9.50,16.50},{10,17.50},{10.50,18.50},{9.50,16.50},//7
								{10,17.50},{9.50,16.50},{0,0},{9,15.50},{19,19}};//2
	private double[][] tabPrixCremeFraîche = {{10.50,18.50},{9.50,16.50},{9.50,16.50}};
	private double[][] tabPrixTruffe = {{12,21.50},{12,21.50}};
	private double[][] tabPrixMoutarde = {{9.50,16.50},{10,17.50},{10.50,18.50}};
	private double[][] tabPrixSpeciale = {{12,21.50},{11.50,19.50}};
	private double[][] tabPrixDessert = {{8,13.50},{8,13.50}};
	private double[][] tabPrixEnfant = {{7,11.50},{7,11.50}};
	private double[][] tabPrixBoissons = {{9.50,9.50},{8.50,8.50},{7,7},{3,3},{2,2}};
	
	
	 ArrayList<M_Pizzas> listPizzas = new ArrayList<M_Pizzas>(); //Liste des pizzas
	 
	 /*Calendrier*/
	 Calendar myCalendar;
	 
	/*Chiffres concernant les boucles*/
	private int nbPizzas = 0;
	private int nbCat = 0;
	private double tabTaille[]= new double[8];
	private int boucle = 0;
	private int incrementeListPizzas = 0;
	
	/*Resultat de la commande*/
	private double resultat = 0;
	
	/*Police pour le texte*/
	Typeface police;
	
	/*Element de la vue*/
	private LinearLayout layoutPizzas;
	private LinearLayout layoutBouton;
	private LinearLayout layoutTitre;
	private Button bouton;
	private Button btnValider;
	private LinearLayout layoutCommande;
	private LayoutParams linLayoutParam = new LayoutParams();
	private LayoutParams lp = new LayoutParams(); //Nouveau parametre pour texteView
	private TextView txtResultat;
	
	private int wrap = LayoutParams.WRAP_CONTENT; //Taille des vue celon contenue
    private int match = LayoutParams.MATCH_PARENT; //Taille de la vue prend tout l'espace
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pizzas);
    	
        police = Typeface.createFromAsset(getAssets(),"fonts/heart.ttf"); //Police définit 
        
        layoutCommande = (LinearLayout) findViewById(R.id.layout_Commande);
        layoutPizzas = (LinearLayout) findViewById(R.id.layout_pizzas); //Layout principal pour pizzas
        txtResultat = (TextView) findViewById(R.id.txtResultat);
        btnValider = (Button) findViewById(R.id.btnValider);
        TextView txtTotal = (TextView) findViewById(R.id.txtTotal);
        TextView txtTitreTicket = (TextView) findViewById(R.id.txtTitreTicket);
        txtTitreTicket.setTypeface(police); //Police titre ticket
        txtTotal.setTypeface(police); //Police total
        txtResultat.setTypeface(police); //Police prix total pizza.
        
        /*test pour la date du jour*/
        myCalendar = myCalendar.getInstance(); //Récupère le calendrier du jour   
        
        //Vérification du jour
    	int date= new java.util.Date().getDate();
    	pm.open();
    	pm.dateJour(date);
    	pm.close();
    	//Vérification de la semaine
    	int dateWeek = myCalendar.get(Calendar.WEEK_OF_YEAR);
    	pm.open();
    	pm.dateSemaine(dateWeek);
    	pm.close();
    	//Vérification du mois
    	int dateMonth = myCalendar.get(Calendar.MONTH);
    	pm.open();
    	pm.dateMois(dateMonth);
    	pm.close();
    	//Vérification de l'année
    	int dateYears = myCalendar.get(Calendar.YEAR);
    	pm.open();
    	pm.dateAnnee(dateYears);
    	pm.close();
        
        btnValider.setOnClickListener(reinitialiser); //Efface la commande
        
        /*Ajout a la liste les pizzas type tomate*/
        for(int i = 0; i < tabPizzasTomate.length; i++){
        	double prix[] = {tabPrixTomates[i][0], tabPrixTomates[i][1]};
        	M_Pizzas unePizza = new M_Pizzas(tabPizzasTomate[i], prix, "Tomate");
        	unePizza.setId(Integer.parseInt(1+""+i));
        	listPizzas.add(unePizza);
        	
        	//Ajout pizza base de données
	        pm.open();
	        long pizza = pm.addPizza(unePizza);
	        pm.close();
        }
        
        /*Ajout a la liste les pizzas type Creme Fraiche*/
        for(int i = 0; i < tabPizzasCremeFraiche.length; i++){
        	double prix[] = {tabPrixCremeFraîche[i][0], tabPrixCremeFraîche[i][1]};
        	M_Pizzas unePizza = new M_Pizzas(tabPizzasCremeFraiche[i], prix, "CremeFraiche");
        	unePizza.setId(Integer.parseInt(2+""+i));
        	listPizzas.add(unePizza);
        	//Ajout pizza base de données
	        pm.open();
	        long pizza = pm.addPizza(unePizza);
	        pm.close();
        }
        
        /*Ajout a la liste les pizzas type Truffe*/
        for(int i = 0; i < tabPizzasTruffes.length; i++){
        	double prix[] = {tabPrixTruffe[i][0], tabPrixTruffe[i][1]};
        	M_Pizzas unePizza = new M_Pizzas(tabPizzasTruffes[i], prix, "Truffes");
        	unePizza.setId(Integer.parseInt(3+""+i));
        	listPizzas.add(unePizza);
        	//Ajout pizza base de données
	        pm.open();
	        long pizza = pm.addPizza(unePizza);
	        pm.close();
        }
        
        /*Ajout a la liste les pizzas type Moutarde*/
        for(int i = 0; i < tabPizzasMoutarde.length; i++){
        	double prix[] = {tabPrixMoutarde[i][0], tabPrixMoutarde[i][1]};
        	M_Pizzas unePizza = new M_Pizzas(tabPizzasMoutarde[i], prix, "Moutarde");
        	unePizza.setId(Integer.parseInt(4+""+i));
        	listPizzas.add(unePizza);
        	//Ajout pizza base de données
	        pm.open();
	        long pizza = pm.addPizza(unePizza);
	        pm.close();
        }
        
        /*Ajout a la liste les pizzas type Specialité*/
        for(int i = 0; i < tabPizzasSpecialite.length; i++){
        	double prix[] = {tabPrixSpeciale[i][0], tabPrixSpeciale[i][1]};
        	M_Pizzas unePizza = new M_Pizzas(tabPizzasSpecialite[i], prix, "Specialite");
        	unePizza.setId(Integer.parseInt(5+""+i));
        	listPizzas.add(unePizza);
        	//Ajout pizza base de données
	        pm.open();
	        long pizza = pm.addPizza(unePizza);
	        pm.close();
        }
        
        /*Ajout a la liste les pizzas type Dessert*/
        for(int i = 0; i < tabPizzasDessert.length; i++){
        	double prix[] = {tabPrixDessert[i][0], tabPrixDessert[i][1]};
        	M_Pizzas unePizza = new M_Pizzas(tabPizzasDessert[i], prix, "Dessert");
        	unePizza.setId(Integer.parseInt(6+""+i));
        	listPizzas.add(unePizza);
        	//Ajout pizza base de données
	        pm.open();
	        long pizza = pm.addPizza(unePizza);
	        pm.close();
        }
        
        /*Ajout a la liste les pizzas type Bambino*/
        for(int i = 0; i < tabPizzasBambino.length; i++){
        	double prix[] = {tabPrixEnfant[i][0], tabPrixEnfant[i][1]};
        	M_Pizzas unePizza = new M_Pizzas(tabPizzasBambino[i], prix, "Bambino");
        	unePizza.setId(Integer.parseInt(7+""+i));
        	listPizzas.add(unePizza);
        	//Ajout pizza base de données
	        pm.open();
	        long pizza = pm.addPizza(unePizza);
	        pm.close();
        }
        
        /*Ajout a la liste les pizzas type Boisson*/
        for(int i = 0; i < tabVins.length; i++){
        	double prix[] = {tabPrixBoissons[i][0], tabPrixBoissons[i][1]};
        	M_Pizzas unePizza = new M_Pizzas(tabVins[i], prix, "Boissons");
        	unePizza.setId(Integer.parseInt(8+""+i));
        	listPizzas.add(unePizza);
        	//Ajout pizza base de données
	        pm.open();
	        long pizza = pm.addPizza(unePizza);
	        pm.close();
        }
        
        /*Définition de la taille des tableau celon les catégories*/
        tabTaille[0] = tabPizzasTomate.length; //Taille tableau pizzas dans double
        tabTaille[1] = tabPizzasCremeFraiche.length;
        tabTaille[2] = tabPizzasTruffes.length;
        tabTaille[3] = tabPizzasMoutarde.length;
        tabTaille[4] = tabPizzasSpecialite.length;
        tabTaille[5] = tabPizzasDessert.length;
        tabTaille[6] = tabPizzasBambino.length;
        tabTaille[7] = tabVins.length;
        
        /*Taille des layout*/
        linLayoutParam.height = wrap; //Taille celon le composant
        linLayoutParam.width = match; //Taille tout l'espace du parent
        
        /*Paramètres Text View*/        
        lp.height = wrap; //hauteur
        lp.width = match; //Largeur

        for(nbCat = 0; nbCat < 8; nbCat++){
        	
        	boucle = (int) Math.ceil(tabTaille[nbCat]/4); //Arrondi au chiffre au dessus, si virgule
	        /*Le texte View*/
	        TextView textview = new TextView(this); //Text view qui serra dans titrelayout
	        textview.setText(tabCategories[nbCat]); //Texte du textView
	        textview.setTextSize(25); //Taille du texte
	        textview.setTextColor(Color.RED); //Couleur du texte
	        textview.setLayoutParams(lp); //Définition de la taille du textView
	        textview.setGravity(Gravity.CENTER); //texte affiché au centre du textView
	        
	        /*Linear Layout pour le titre*/
	        layoutTitre = new LinearLayout(this); //Nouveau linear layout pour le titre
	        LinearLayout.LayoutParams lpTitre = new LinearLayout.LayoutParams(match, wrap); //Parametre linear layout
	        lpTitre.setMargins(0, 0, 0, 2); //Margin bottom 2 pixels
	        layoutTitre.setLayoutParams(lpTitre); //Ajout des parametre au Layout
	        layoutTitre.setPadding(0, 0, 0, 5); //Padding bottom de 5 pixels
	        layoutTitre.addView(textview); //Ajout textView au Layout
	        layoutTitre.setBackgroundColor(Color.BLACK); //Fond du layout en noir   
	        layoutPizzas.addView(layoutTitre); //Ajout titre au layout principal Pizzas
	        
	        nbPizzas = 0;
	        //Boucle pour créer un layout par ligne
	        for(int j = 0; j < boucle; j++){
	        	
	        	 layoutBouton = new LinearLayout(this); //Création d'un linearLayout
	             layoutBouton.setOrientation(LinearLayout.HORIZONTAL); //Layout affichage horizontal
	             layoutBouton.setPadding(0, 0, 2, 0); //Padding right de 2 pixels
	             layoutBouton.setLayoutParams(linLayoutParam); //Ajout des parametre de tailles au layout
	        
	            //Boucle pour afficher 4 bouton sur une ligne
		        for(int i = 0; i < 4; i++){
		        	
		        	//Création d'un layout pour les quatre bouton de la ligne
		        	LinearLayout.LayoutParams lpBouton = new LinearLayout.LayoutParams(wrap, wrap);
		        	lpBouton.height = 150; //Taille hauteur layout
		        	lpBouton.width = 190; //Taille largeur layout
		        	lpBouton.gravity = Gravity.CENTER;
		        	lpBouton.setMargins(2, 0, 0, 2); //Margin left espacé de 2 pixels
		        	
		        	//On verifie qu'il y a toujour des bouton à afficher
		        	if(nbPizzas < tabTaille[nbCat]){
				        bouton = new Button(this); //Implémentation d'un nouveau bouton
				        if(listPizzas.get(incrementeListPizzas).getNom().equals("Royale Offerte")){
				        	bouton.setBackgroundResource(R.drawable.selectorticket); //Style du bouton royale offerte
				        }
				        else if(listPizzas.get(incrementeListPizzas).getNom().equals("Pizza du Moment")){
				        	bouton.setBackgroundResource(R.drawable.selector_pizza_moment); //Style du bouton royale offerte
				        }
				        else if(listPizzas.get(incrementeListPizzas).getNom().equals("Pizza Apéro")){
				        	bouton.setBackgroundResource(R.drawable.selector_pizza_apero); //Style du bouton royale offerte
				        }
				        else{
					        bouton.setBackgroundResource(R.drawable.selector); //Style lorsque que l'on clique sur un bouton        	
				        }
				       
				        bouton.setTextColor(Color.WHITE); //Couleur texte blanc
				        bouton.setTextSize(23); //Taille texte de 20 pixels
				        bouton.setGravity(Gravity.CENTER); //Bouton au centre
				        bouton.setText(listPizzas.get(incrementeListPizzas).getNom()); //texte du bouton
				        
				        bouton.setOnClickListener(clicChoixPizzas);
				        
				        bouton.setLayoutParams(lpBouton); //Ajout parametre au bouton
			        
			        	layoutBouton.addView(bouton); //Ajout du bouton au layout
			        	
			        	incrementeListPizzas++; //Parcours liste pizzas
		        	}
		        	
		        	nbPizzas++; //Incrémente pour parcours des pizzas
		        }
		        
		        //On ajoute le Layout de 4 boutons au layout général de pizzas
		        layoutPizzas.addView(layoutBouton);
	        }
        }
        
    }
    
    /*Action lors du clique sur un bouton*/
    private OnClickListener clicChoixPizzas = new View.OnClickListener() {
		
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			M_Pizzas pizzaSelectionne = null;
			Button boutonSelection = (Button) v; //Instanciation bouton
			String nomPizza = boutonSelection.getText().toString();
			
			/*Parcour de la liste des pizzas pour trouver la selectionnée*/
	         for(M_Pizzas unePizzas : listPizzas){
	        	 if(unePizzas.getNom().equals(nomPizza)){
	        		 pizzaSelectionne = unePizzas;
	        	 }
	         }
	         
	         //Mise à jour base de données pour les statistiques
	         pm.open();
	         pm.updatePizza(pizzaSelectionne);
	         pm.close();
	         
	        /*Si c'est une boisson pas besoin de la taille donc pas de Pop Up*/
			if(pizzaSelectionne.getCategorie().equals("Boissons") || pizzaSelectionne.getNom().equals("Pizza Apéro")){
				afficherCommande(true, pizzaSelectionne);
			}
			/*Sinon on affcihe une boite de fialogue pour choisir la taille*/
			else{
				
				onCreateDialog(0, pizzaSelectionne).show();//Envoie texte bouton
			}
		}
	};
	
	
	/*Afficher boite de dialogue*/
	 public Dialog onCreateDialog(int Chiffre, final M_Pizzas pizza) {
	        // Use the Builder class for convenient dialog construction
	        AlertDialog.Builder builder = new AlertDialog.Builder(Pizzas.this);
		    
	        builder.setMessage("Quelle est la taille de la pizza '"+pizza.getNom()+"' ?")
	               .setPositiveButton("Piccolo", new DialogInterface.OnClickListener() {
	                   public void onClick(DialogInterface dialog, int id) {
	                       // FIRE ZE MISSILES!
	                	   afficherCommande(false, pizza);//envoie pizza et taille avec boolean
	                   }
	               })
	               .setNegativeButton("Molto", new DialogInterface.OnClickListener() {
	                   public void onClick(DialogInterface dialog, int id) {
	                	   afficherCommande(true, pizza);//envoie pizza et taille avec boolean
	                   }
	               });
	        // Create the AlertDialog object and return it
	        return builder.create();
	    }
	 
	 private void afficherCommande(Boolean taille, M_Pizzas pizza){
		 double tableauPrix[] = new double[2]; //les deux prix de la pizza dans un tableau
		 double prixDouble = 0; //Le prix a afficher entre les deux du tableau
		 String prix ="";
		 final M_Pizzas pizzaSelectionne = pizza; //pizza egal pizza envoyer
		 final RelativeLayout layoutUnePizzaCommande = new RelativeLayout(Pizzas.this);
		 final double prixSupp;
		 
		 RelativeLayout.LayoutParams rlp = new RelativeLayout.LayoutParams(match, wrap);

         layoutUnePizzaCommande.setPadding(35, 0, 50, 0); //padding right and left
         
         tableauPrix = pizzaSelectionne.getPrix();
         
         //Selon la taille de la pizza on pend un prix différent
         if(!taille){
        	 prixDouble = tableauPrix[0];
        	 prix = ""+tableauPrix[0];
        	 prixSupp = prixDouble;
         }
         else{
        	 prixDouble = tableauPrix[1];
        	 prix = ""+tableauPrix[1];
        	 prixSupp = prixDouble;
         }
         
         /*Taille du layout*/
         lp.height = wrap;
         lp.width = wrap;
         
         TextView txtNomPizza = new TextView(Pizzas.this);
         txtNomPizza.setText(pizzaSelectionne.getNom()); //Texte du textView
         txtNomPizza.setTextSize(30); //Taille du texte
         txtNomPizza.setTextColor(Color.WHITE); //Couleur du texte
         txtNomPizza.setLayoutParams(lp); //Définition de la taille du textView
         txtNomPizza.setGravity(Gravity.LEFT); //texte affiché au centre du textView
         txtNomPizza.setTypeface(police); //Police nom pizza
         
         TextView txtPrixPizza = new TextView(Pizzas.this);
         
         txtPrixPizza.setText(prix+" € "); //Texte du textView
         txtPrixPizza.setTextSize(30); //Taille du texte
         txtPrixPizza.setTextColor(Color.WHITE); //Couleur du texte
         txtPrixPizza.setLayoutParams(lp); //Définition de la taille du textView
         txtPrixPizza.setGravity(Gravity.RIGHT); //texte affiché a droite du textView
         txtPrixPizza.setTypeface(police); //Police prix pizza
         
         rlp.addRule(RelativeLayout.ALIGN_LEFT, txtNomPizza.getId());
         rlp.addRule(RelativeLayout.ALIGN_RIGHT, txtPrixPizza.getId());
         
         layoutUnePizzaCommande.setLayoutParams(rlp); //Ajout des parametre de tailles au layout
         
         
         layoutUnePizzaCommande.setOnClickListener(new View.OnClickListener() {
        	 
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				layoutUnePizzaCommande.removeAllViews();
				resultat = resultat - prixSupp;
				txtResultat.setText(resultat+" € ");
			}
		});
         
         /*Ajout de la pizza dans le layout*/
         layoutUnePizzaCommande.addView(txtNomPizza, rlp);
         layoutUnePizzaCommande.addView(txtPrixPizza, rlp);
         
         //Afficher layout pizzas dans ticket
         layoutCommande.addView(layoutUnePizzaCommande);
         
         resultat = resultat+prixDouble; //calcul du prix
         
         /*Affichage du prix*/
         txtResultat.setText(resultat+" € ");
	 }
	 
	 /*Reinitialiser toute la vue ticket*/
	 private OnClickListener reinitialiser = new View.OnClickListener() {
		
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			resultat = 0; //remet le resulat a zéro
			txtResultat.setText(resultat+" € "); //On affiche zéro euro
			
			layoutCommande.removeAllViews(); //On supprime toutes les commandes du ticket
		}
	};
	
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
