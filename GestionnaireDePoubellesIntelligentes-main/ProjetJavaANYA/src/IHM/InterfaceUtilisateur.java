package IHM;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.util.List;

import anya.poubelle.CentreDeTri;
import anya.poubelle.CouleurBac;
import anya.poubelle.EtatPoubelle;
import anya.poubelle.PoubelleIntelligente;
import anya.poubelle.Utilisateur;

import java.util.Arrays;

public class InterfaceUtilisateur extends Application {

	 @Override
	    public void start(Stage primaryStage) {
	        // Créer un CentreDeTri et un Utilisateur
	        CentreDeTri centreDeTri = new CentreDeTri("Centre de tri de Saint-Germain-En-Laye");
	        Utilisateur user = new Utilisateur("Yassine", centreDeTri);
	        
	        // Récupérer les informations utilisateur
	        String infosUtilisateur = "Utilisateur: " + user.getNom() +
	                " | XP: " + user.getXP() + " | Argent: " + user.getArgentEpargne() + "€";

	        // Création de la fenêtre principale
	        primaryStage.setTitle("Interface Utilisateur");

	        // Créer le BorderPane pour la structure de la fenêtre
	        BorderPane root = new BorderPane();

	        // Section supérieure avec les informations de l'utilisateur
	        HBox topPanel = new HBox();
	        Label labelInfos = new Label(infosUtilisateur);
	        topPanel.getChildren().add(labelInfos);
	        root.setTop(topPanel);

	        // Ajouter un espace entre la barre du haut et les boutons
	        topPanel.setStyle("-fx-padding: 10;");

	        // Section centrale avec les boutons
	        VBox buttonPanel = new VBox(10);
	        Button boutonPoubelle = new Button("Interface Poubelle");
	        Button boutonBonsAchats = new Button("Mes Bons d'Achats");
	        Button boutonReclamerBonsAchats = new Button("Réclamer Bons d'Achats");

	        // Actions pour les boutons
	        boutonPoubelle.setOnAction(e -> openPoubelleWindow());
	        boutonBonsAchats.setOnAction(e -> afficherMessage("Mes Bons d'Achats"));
	        boutonReclamerBonsAchats.setOnAction(e -> afficherMessage("Réclamation de Bons d'Achats"));

	        // Ajouter les boutons dans le panel central
	        buttonPanel.getChildren().addAll(boutonPoubelle, boutonBonsAchats, boutonReclamerBonsAchats);
	        root.setCenter(buttonPanel);

	        // Ajouter un peu d'espace autour des boutons
	        buttonPanel.setStyle("-fx-padding: 20; -fx-alignment: center;");

	        // Création de la scène
	        Scene scene = new Scene(root, 600, 400);
	        primaryStage.setScene(scene);

	        // Affichage de la fenêtre
	        primaryStage.show();
	    }

	    private Object ouvrirInterfacePoubelle(Stage primaryStage) {
		// TODO Auto-generated method stub
		return null;
	}

		// Méthode pour afficher un message (pour simuler l'ouverture de la nouvelle fenêtre)
	    private void afficherMessage(String message) {
	        Alert alert = new Alert(Alert.AlertType.INFORMATION);
	        alert.setTitle("Information");
	        alert.setHeaderText(null);
	        alert.setContentText(message);
	        alert.showAndWait();
	    }

	    public void openPoubelleWindow() {
	        // Créer une instance de Utilisateur
	    	CentreDeTri centreDeTri = new CentreDeTri("Centre de tri de Saint-Germain-En-Laye");
	        Utilisateur utilisateur = new Utilisateur("Yassine", centreDeTri);
	        
	        Stage poubelleStage = new Stage();
	        
	        VBox vbox = new VBox(10);

	            // Créer une ComboBox pour la liste des poubelles disponibles
	            ComboBox<String> comboPoubelle = new ComboBox<>();
	            
	            // Si consulterPoubellesDisponibles() retourne une liste d'objets Poubelle
	            List<PoubelleIntelligente> poubellesDisponibles = utilisateur.consulterPoubellesDisponibles();
	            
	            for (PoubelleIntelligente poubelle : poubellesDisponibles) {
	                comboPoubelle.getItems().add(poubelle.toString());  // Ajouter le nom de la poubelle
	            }

	            // Créer une ComboBox pour les couleurs des bacs
	            ComboBox<String> comboCouleur = new ComboBox<>();
	            
	            // Ajouter les valeurs de l'énumération CouleurBac (en convertissant chaque élément en String)
	            for (CouleurBac couleur : CouleurBac.values()) {
	                comboCouleur.getItems().add(couleur.toString());  // Ajouter la couleur du bac
	            }

	            // Ajouter des labels et des ComboBox
	            vbox.getChildren().add(new Label("Poubelle:"));
	            vbox.getChildren().add(comboPoubelle);
	            
	            vbox.getChildren().add(new Label("Couleur:"));
	            vbox.getChildren().add(comboCouleur);
	            
	            // Création de la scène pour la fenêtre Poubelle
	            Scene scene = new Scene(vbox, 300, 200);
	            poubelleStage.setTitle("Interface Poubelle");
	            poubelleStage.setScene(scene);
	            poubelleStage.show();
	        }
	  


	    public static void main(String[] args) {
	        launch(args);
	    }
	}

