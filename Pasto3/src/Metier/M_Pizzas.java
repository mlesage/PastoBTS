package Metier;

public class M_Pizzas {
	
	private int id, nbClique, nbSemaine, nbMois, nbAnnee, nbTotal;
	private double statistique;
	private String nom;
	private double[] prix;
	private String categorie;
	
	public M_Pizzas (){
		
	}

	public M_Pizzas(String nom, double[] prix, String categorie) {
		super();
		this.nom = nom;
		this.prix = prix;
		this.categorie = categorie;
	}

	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}

	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	public double[] getPrix() {
		return prix;
	}

	public void setPrix(double[] prix) {
		this.prix = prix;
	}

	public String getCategorie() {
		return categorie;
	}

	public void setCategorie(String categorie) {
		this.categorie = categorie;
	}

	public int getNbClique() {
		return nbClique;
	}

	public void setNbClique(int nbClique) {
		this.nbClique = nbClique;
	}
	
	public int getNbSemaine() {
		return nbSemaine;
	}

	public void setNbSemaine(int nbSemaine) {
		this.nbSemaine = nbSemaine;
	}

	public int getNbMois() {
		return nbMois;
	}

	public void setNbMois(int nbMois) {
		this.nbMois = nbMois;
	}

	public int getNbAnnee() {
		return nbAnnee;
	}

	public void setNbAnnee(int nbAnnee) {
		this.nbAnnee = nbAnnee;
	}

	public int getNbTotal() {
		return nbTotal;
	}

	public void setNbTotal(int nbTotal) {
		this.nbTotal = nbTotal;
	}

	public double getStatistique() {
		return statistique;
	}

	public void setStatistique(double statistique) {
		this.statistique = statistique;
	}
	
}
