package anya.poubelle;
import java.util.ArrayList;
import java.util.List;


public class Utilisateur {
	private static int nbUtilisateurs = 0;
	private final int idUtilisateur = nbUtilisateurs + 1;
	private String nom;
	private double pointsFidelite = 0;
	private ArrayList<Depot> historiqueDepots = new ArrayList<Depot>();
	private double argentEpargne = 0;
	private ArrayList<BonAchat> bonsAchatNonUtilises = new ArrayList<BonAchat>();
	private ArrayList<BonAchat> bonsAchatUtilises = new ArrayList<BonAchat>();
	private double xp = 0;
	private CentreDeTri centreDeTriMere;
	private Object listeBonsAchats;
	
    public Utilisateur(String nom, CentreDeTri centreDeTriMere) {
        nbUtilisateurs ++;
        this.nom = nom;
        this.centreDeTriMere = centreDeTriMere;
        centreDeTriMere.ajouterUtilisateur(this);
        
    }
    
    
    public void genererBonsAchatExemple() {
        // Générer quelques bons d'achat non utilisés
        BonAchat bon1 = new BonAchat("Bon Carrefour", 50);
        bon1.setValide(true); // On suppose qu'il y a une méthode pour définir la validité
        BonAchat bon2 = new BonAchat("Bon Amazon", 20);
        bon2.setValide(true);
        BonAchat bon3 = new BonAchat("Bon Fnac", 30);
        bon3.setValide(false); // Ce bon ne sera pas valide

        bonsAchatNonUtilises.add(bon1);
        bonsAchatNonUtilises.add(bon2);
        bonsAchatNonUtilises.add(bon3);
    }

    
    public double getXP() {
    	return this.xp;
    }
	
    public String getNom() {
    	return this.nom;
    }
    public double getPointsFidelite() {
    	return this.pointsFidelite;
    }
    public double getArgentEpargne() {
    	return this.argentEpargne;
    }
    public ArrayList<Depot> getHistoriqueDepots() {
    	return this.historiqueDepots;
    }
    public ArrayList<BonAchat> getBonsAchat() {
    	return this.bonsAchatNonUtilises;
    }
    
    public ArrayList<BonAchat> getBonsAchatValides() {
    	ArrayList<BonAchat> valides = new ArrayList<BonAchat>();
    	for (BonAchat bon : this.bonsAchatNonUtilises) {
    		if (bon.estValide()) {
    			valides.add(bon);
    		}
    	}
    	return valides;
    }
    
    public void utiliserBonAchat(BonAchat bon) {
    	this.bonsAchatNonUtilises.remove(bon);
    	this.bonsAchatUtilises.add(bon);
    	bon.utiliser();
    }
    
    public boolean seConnecterEtDeverouiller(PoubelleIntelligente poubelle) {
        poubelle.seConnecterEtDeverouiller(this);
        if (poubelle.getEtat() == EtatPoubelle.Deverouillee) {
        	return true;
        } else {
        	return false;
        }
    }
    
    public boolean placerDechetDansPoubelle(Dechet dechet, PoubelleIntelligente poubelle, CouleurBac couleur) {
        try {
            BacSpecialise bacSpe = poubelle.getBacSpecialise(couleur);
            if (!bacSpe.peutRecevoir(dechet.getPoid())) {
                return false;
            }
            Depot depot = bacSpe.placerDechet(dechet, this);
            if (depot == null) {
                return false;
            }
            this.getHistoriqueDepots().add(depot);
            double score = depot.getCreditScore();
            if (this.pointsFidelite <= 0 & score <= 0) {
            	score /= 2;
            }
            this.pointsFidelite += score;
            this.xp += score;
            return true;
        } finally {
            poubelle.seDeconnecterEtVerouiller();
        }
    }
    public ArrayList<BonAchat> consulterBonsAchatDisponibles(int iMin, int iMax) {
    	ArrayList<BonAchat> bonsDisponibles = new ArrayList<BonAchat>();
    	bonsDisponibles.addAll(this.centreDeTriMere.consulterBonsAchatsDisponibles(iMin, iMax));
    	return bonsDisponibles;
    }
    
    public boolean reclamerBonAchat(BonAchat bonAchat) {
    	double pointsRequis = bonAchat.getPointsRequis();
    	if (this.pointsFidelite >= pointsRequis) {
    		this.pointsFidelite -= pointsRequis;
    		bonAchat.reclamePar(this);
    		this.argentEpargne += bonAchat.getMontant();
    		this.bonsAchatNonUtilises.add(bonAchat);
    		return true;
    	} else {
    		return false;
    	}
    }
    
    public ArrayList<PoubelleIntelligente> consulterPoubellesDisponibles() {
    	return centreDeTriMere.getPoubelles();
    }

	public void setPointsFidelite(double d) {
		this.pointsFidelite += d;
	}


	public void convertirPointsEnBonAchat(BonAchat selectedBon) {
		// TODO Auto-generated method stub
		
	}


	public CentreDeTri getCentreDeTri() {
		// TODO Auto-generated method stub
		return null;
	}


	public void setArgentEpargne(double d) {
		this.argentEpargne += d;
		
	}


	public void setXP(double d) {
		this.xp += d;
		
	}



}
