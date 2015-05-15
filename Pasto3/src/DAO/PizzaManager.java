package DAO;

import java.util.ArrayList;

import Metier.M_Pizzas;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class PizzaManager {

	
	DBManager dbm;
	SQLiteDatabase db;
	String TABLE_DROP =  "DROP TABLE IF EXISTS pizza;";
	/*test pour la date du jour*/
	int date= new java.util.Date().getDate();
	String sqltable = "create table pizza (id INT NOT NULL PRIMARY KEY, NOM TEXT NOT NULL, CATEGORIE TEXT NOT NULL, STJOUR INT DEFAULT '0', STSEMAINE INT DEFAULT '0', STMOIS INT DEFAULT '0', STANNEE INT DEFAULT '0', STTOTAL INT DEFAULT '0', DATEJOUR INT DEFAULT '0', DATESEMAINE INT DEFAULT '0', DATEMOIS INT DEFAULT '0', DATEANNEE INT DEFAULT '0' )";
	
	public PizzaManager(Context ctx) {
		// TODO Auto-generated constructor stub
		dbm = new DBManager(ctx, "mabase", null, 1);
	}
	
	public void open(){
		
		db = dbm.getWritableDatabase();
		
	}
	
	public void close(){
		
		db.close();
	}
	
	public void deleteTable(){
		
		db.execSQL(TABLE_DROP);
		
	}
	
	public void createTable(){
		db.execSQL(sqltable);
	}
	
	//Ajouter une pizza
	public long addPizza(M_Pizzas p){
		
		/*Valeur entrées*/
		ContentValues vals = new ContentValues();
		vals.put("id", p.getId());
		vals.put("NOM", p.getNom());
		vals.put("CATEGORIE", p.getCategorie());
		
		
		return db.insert("pizza", null, vals);
	}
	
	//Supprimer une pizza
	public long deletePizza(int id){
		
		return db.delete("pizza", "id="+id, null);
	}
	
	//Trouver une pizza avec son ID
	public M_Pizzas findPizza(int id){
		M_Pizzas p = null;
		
		Cursor c = db.query("pizza", new String[] {"id, nom, STJOUR"} , "id="+id, null, null, null, null);
		
		if(c.moveToFirst()){
			p = new M_Pizzas();
			
			p.setId(c.getInt(0));
			p.setNom(c.getString(1));
			p.setNbClique(c.getInt(2));
			
		}
		
		return p;
	}
	
	
	//Retourne toute les pizza dans arraylist
	public ArrayList<M_Pizzas> allPizza(){
		ArrayList<M_Pizzas> listPizzas = new ArrayList<M_Pizzas>();
		Cursor c = db.query("pizza", new String[] {"id, nom, CATEGORIE, STJOUR, STSEMAINE, STMOIS, STANNEE, STTOTAL"} , null, null, null, null, null);
		c.moveToFirst();
		
		while(!c.isAfterLast()){
			M_Pizzas p = new M_Pizzas();
			
			p.setId(c.getInt(0));
			p.setNom(c.getString(1));
			p.setCategorie(c.getString(2));
			p.setNbClique(c.getInt(3));
			p.setNbSemaine(c.getInt(4));
			p.setNbMois(c.getInt(5));
			p.setNbAnnee(c.getInt(6));
			p.setNbTotal(c.getInt(7));
			
			listPizzas.add(p);
			
			c.moveToNext();
			
		}
		
		return listPizzas;
	}
	
	//Ajout un clique sur la pizza selectionnée
public long updatePizza(M_Pizzas p){
		Integer[] tabclique = increaseClick(p.getId());
		for(int i = 0; i < tabclique.length; i++){
			tabclique[i] = tabclique[i]+1;
		}
		ContentValues vals = new ContentValues();
		vals.put("STJOUR", tabclique[0]);
		vals.put("STSEMAINE", tabclique[1]);
		vals.put("STMOIS", tabclique[2]);
		vals.put("STANNEE", tabclique[3]);
		vals.put("STTOTAL", tabclique[4]);
		
		return db.update("pizza", vals, "id = ?",  new String[] {String.valueOf(p.getId())});
	}


		//Augmente clique pizza
		public Integer[] increaseClick(int id){
			Integer[] cliquetab = new Integer[5];
			
			Cursor c = db.query("pizza", new String[] {"STJOUR, STSEMAINE, STMOIS, STANNEE, STTOTAL"} , "id="+id, null, null, null, null);
			
			if(c.moveToFirst()){
				cliquetab[0] = c.getInt(0);
				cliquetab[1] = c.getInt(1);
				cliquetab[2] = c.getInt(2);
				cliquetab[3] = c.getInt(3);
				cliquetab[4] = c.getInt(4);
				
			}
			
			return cliquetab;
		}
	public void dateJour(int dateDuJour){
		int dateBDD = 0;
		Cursor c = db.query("pizza", new String[] {"DATEJOUR"} , null, null, null, null, null);

		if(c.moveToFirst()){
			dateBDD = c.getInt(0);	
		}
		
		
		if(dateDuJour != dateBDD){
			ContentValues vals = new ContentValues();
			vals.put("STJOUR", 0);
			vals.put("DATEJOUR", dateDuJour);
			
			db.update("pizza", vals, null, null);
		}
		
		
	}
	
	public void dateSemaine(int dateDuJour){
		int dateBDD = 0;
		Cursor c = db.query("pizza", new String[] {"DATESEMAINE"} , null, null, null, null, null);

		if(c.moveToFirst()){
			dateBDD = c.getInt(0);	
		}
		
		
		if(dateDuJour != dateBDD){
			ContentValues vals = new ContentValues();
			vals.put("STSEMAINE", 0);
			vals.put("DATESEMAINE", dateDuJour);
			
			db.update("pizza", vals, null, null);
		}
		
		
	}
	
	public void dateMois(int dateDuJour){
		int dateBDD = 0;
		Cursor c = db.query("pizza", new String[] {"DATEMOIS"} , null, null, null, null, null);

		if(c.moveToFirst()){
			dateBDD = c.getInt(0);	
		}
		
		
		if(dateDuJour != dateBDD){
			ContentValues vals = new ContentValues();
			vals.put("STMOIS", 0);
			vals.put("DATEMOIS", dateDuJour);
			
			db.update("pizza", vals, null, null);
		}
		
		
	}
	
	public void dateAnnee(int dateDuJour){
		int dateBDD = 0;
		Cursor c = db.query("pizza", new String[] {"DATEANNEE"} , null, null, null, null, null);

		if(c.moveToFirst()){
			dateBDD = c.getInt(0);	
		}
		
		
		if(dateDuJour != dateBDD){
			ContentValues vals = new ContentValues();
			vals.put("STANNEE", 0);
			vals.put("DATEANNEE", dateDuJour);
			
			db.update("pizza", vals, null, null);
		}
		
		
	}
}
