package anya.poubelle;
import java.time.LocalDate;

public class BonAchat {
	private static int nbBonsAchat;
	private final int idBonAchat;
	private final double pointsRequis;
	private double montant;
	private LocalDate dateExpiration;
	private Commerce commerceMere;
	private EtatBonAchat etat = EtatBonAchat.DISPONIBLE;
	private String categorieProduits;
	private Utilisateur proprietaire;
	private final CentreDeTri centreDeTriMere;
	private double prix;
	
	public BonAchat(double pointsRequis, double montant, LocalDate dateExpiration, Commerce commerceMere, String categorieProduits, CentreDeTri centreDeTriMere) {
		this.pointsRequis = pointsRequis;
		this.montant = montant;
		this.dateExpiration = dateExpiration;
		this.commerceMere = commerceMere;
		this.centreDeTriMere = centreDeTriMere;
		this.idBonAchat = nbBonsAchat;
		nbBonsAchat++;
	}
	
	public BonAchat(String string, int i) {
		this.idBonAchat = 0;
		this.pointsRequis = 0;
		// TODO Auto-generated constructor stub
		this.centreDeTriMere = null;
	}

	public double getMontant() {
		return this.montant;
	}
	
	public double getPointsRequis() {
		return this.pointsRequis;
	}
	
	public CentreDeTri getCentreDeTriMere() {
		return this.centreDeTriMere;
	}
	
	public void reclamePar(Utilisateur utilisateur) {
		this.etat = EtatBonAchat.RECLAME;
		this.proprietaire = utilisateur;
		this.getCentreDeTriMere().signalerChangementEtatBonAchat(this);
	}

	public boolean estValide() {
	    if (dateExpiration == null) {
	        // Si la date d'expiration est null, le bon est considéré comme invalide
	        return false;
	    }
	    return !LocalDate.now().isAfter(dateExpiration);
	}

	
	
	
	public boolean utiliser() {
		if (this.estValide() & this.etat == EtatBonAchat.DISPONIBLE) {
			this.etat = EtatBonAchat.UTILISE;
			return true;
		} else {
			this.etat = EtatBonAchat.EXPIRE;
			return false;
		}
	}

	public String getMagasinsAssocies() {
		// TODO Auto-generated method stub
		return null;
	}

	public void setValide(boolean b) {
		// TODO Auto-generated method stub
		
	}

	public String getNom() {
		// TODO Auto-generated method stub
		return null;
	}

	public String getValeur() {
		// TODO Auto-generated method stub
		return null;
	}

	public double getPrix() {
		// TODO Auto-generated method stub
		return 0;
	}

	}

