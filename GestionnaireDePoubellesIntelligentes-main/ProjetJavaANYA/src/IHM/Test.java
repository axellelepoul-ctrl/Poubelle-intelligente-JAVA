package IHM;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import anya.poubelle.*; // Assurez-vous que ces classes sont bien importées

import java.util.ArrayList;
import java.util.List;
import java.time.LocalDate;


public class Test extends Application {

    private BorderPane root;
    private int indexMin = 0;
    private int indexMax = 5;

    // Déclaration des variables globales
    private List<BonAchat> bonsDisponibles;
    private List<Commerce> magasinsPartenaires;  // Liste des magasins partenaires
    private ComboBox<BonAchat> comboBons;
    private ComboBox<String> comboMagasins;
    private Label labelBons;

    @Override
    public void start(Stage primaryStage) {
        // Créer un CentreDeTri et un Utilisateur
        CentreDeTri centreDeTri = new CentreDeTri("Centre de tri de Saint-Germain-En-Laye");
        Utilisateur utilisateur = new Utilisateur("Yassine", centreDeTri);

        
        
        // Création de quelques bons d'achat
        BonAchat bon1 = new BonAchat("Bon Carrefour", 50);
        BonAchat bon2 = new BonAchat("Bon IKEA", 30);
        BonAchat bon3 = new BonAchat("Bon Decathlon", 20);

        // Ajouter ces bons d'achat à la liste des bons disponibles
        bonsDisponibles = new ArrayList<>();
        bonsDisponibles.add(bon1);
        bonsDisponibles.add(bon2);
        bonsDisponibles.add(bon3);

        // Créer la liste des magasins partenaires (exemples)
        magasinsPartenaires = new ArrayList<>();
        magasinsPartenaires.add(new Commerce("Carrefour"));
        magasinsPartenaires.add(new Commerce("IKEA"));
        magasinsPartenaires.add(new Commerce("Decathlon"));

        PoubelleIntelligente poubelle1 = new PoubelleIntelligente(48.8566, 2.3522, centreDeTri); // Exemple : Paris
        PoubelleIntelligente poubelle2 = new PoubelleIntelligente(48.8584, 2.2945, centreDeTri); // Exemple : Tour Eiffel
        PoubelleIntelligente poubelle3 = new PoubelleIntelligente(48.8700, 2.3750, centreDeTri); // Exemple : Montmartre

     // Initialiser l'utilisateur avec quelques valeurs d'exemple
        utilisateur.setPointsFidelite(120);  // Exemples d'initialisation
        utilisateur.setArgentEpargne(1500); // Argent épargné de 1500€
        utilisateur.setXP(200); // XP de 200

        // Ajouter ces poubelles à une liste
        List<PoubelleIntelligente> poubellesDisponibles = new ArrayList<>();
        poubellesDisponibles.add(poubelle1);
        poubellesDisponibles.add(poubelle2);
        poubellesDisponibles.add(poubelle3);

        // Initialisation de la fenêtre principale
        primaryStage.setTitle("Interface Utilisateur");
        root = new BorderPane();

        // Barre supérieure avec les informations utilisateur
        HBox topBar = new HBox();
        Label labelInfos = new Label("Nom: " + utilisateur.getNom() + " | XP: " + utilisateur.getXP() + " | Argent: " + utilisateur.getArgentEpargne() + "€");
        topBar.setStyle("-fx-padding: 10;");
        topBar.getChildren().add(labelInfos);
        root.setTop(topBar);

     // Mettre à jour les valeurs après une certaine action (par exemple, l'utilisateur gagne des XP)
        utilisateur.setXP(utilisateur.getXP() + 100);  // Ajouter 100 XP
        utilisateur.setPointsFidelite(utilisateur.getPointsFidelite() + 50);  // Ajouter 50 points de fidélité
        utilisateur.setArgentEpargne(utilisateur.getArgentEpargne() + 100);  // Ajouter 100€ à l'argent épargné

        // Mettre à jour le label avec les nouvelles informations
        labelInfos.setText("Nom: " + utilisateur.getNom() +
                            " | XP: " + utilisateur.getXP() +
                            " | Points Fidélité: " + utilisateur.getPointsFidelite() +
                            " | Argent: " + utilisateur.getArgentEpargne() + "€");

        // Ajouter les boutons principaux dans la fenêtre principale
        afficherMenuPrincipal(utilisateur);

        // Création de la scène
        Scene scene = new Scene(root, 600, 400);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void afficherMenuPrincipal(Utilisateur utilisateur) {
        VBox centerPanel = new VBox(20);
        centerPanel.setStyle("-fx-alignment: center; -fx-padding: 20;");

        Button btnInterfacePoubelle = new Button("Interface Poubelle");
        Button btnMesBonsAchats = new Button("Mes Bons d'Achats");
        Button btnReclamerBonsAchats = new Button("Réclamer Bons d'Achats");

        // Actions des boutons
        btnInterfacePoubelle.setOnAction(e -> afficherInterfacePoubelle(utilisateur));
        btnMesBonsAchats.setOnAction(e -> afficherBonsAchats(utilisateur));
        btnReclamerBonsAchats.setOnAction(e -> afficherReclamerBonAchat(utilisateur));

        btnInterfacePoubelle.setStyle("-fx-font-size: 16px; -fx-padding: 10;");
        centerPanel.getChildren().addAll(btnInterfacePoubelle, btnMesBonsAchats, btnReclamerBonsAchats);

        // Mettre à jour le contenu central
        root.setCenter(centerPanel);
    }


    private void afficherReclamerBonAchat(Utilisateur utilisateur) {
        VBox vbox = new VBox(10);
        vbox.setStyle("-fx-padding: 20; -fx-alignment: center;");

        Label labelPoints = new Label("Points de fidélité disponibles : " + utilisateur.getPointsFidelite());
        ListView<BonAchat> listBonsDisponibles = new ListView<>();
        listBonsDisponibles.getItems().addAll(bonsDisponibles);

        Button btnConvertirPoints = new Button("Convertir Points en Bons d'Achat");
        btnConvertirPoints.setOnAction(e -> {
            BonAchat selectedBon = listBonsDisponibles.getSelectionModel().getSelectedItem();
            if (selectedBon != null && utilisateur.getPointsFidelite() >= selectedBon.getPrix()) {
                utilisateur.reclamerBonAchat(selectedBon);
                bonsDisponibles.add(selectedBon);
                listBonsDisponibles.getItems().add(selectedBon);
                labelPoints.setText("Points de fidélité disponibles : " + utilisateur.getPointsFidelite());
                afficherMessage("Bon d'achat réclamé avec succès !");
            } else {
                afficherMessage("Points insuffisants ou aucun bon sélectionné.");
            }
        });

        Button btnRetour = new Button("Retour");
        btnRetour.setOnAction(e -> afficherMenuPrincipal(utilisateur));

        vbox.getChildren().addAll(labelPoints, listBonsDisponibles, btnConvertirPoints, btnRetour);
        root.setCenter(vbox);
    }

	private void afficherInterfacePoubelle(Utilisateur utilisateur) {
        VBox vbox = new VBox(10);
        vbox.setStyle("-fx-padding: 20; -fx-alignment: center;");

        ComboBox<String> comboPoubelle = new ComboBox<>();
        List<PoubelleIntelligente> poubellesDisponibles = utilisateur.consulterPoubellesDisponibles();
        for (PoubelleIntelligente poubelle : poubellesDisponibles) {
            comboPoubelle.getItems().add(poubelle.toStringCoordonnees()); // Ajoute les coordonnées des poubelles
        }

        ComboBox<String> comboCouleur = new ComboBox<>();
        for (CouleurBac couleur : CouleurBac.values()) {
            comboCouleur.getItems().add(couleur.toString());
        }

        Button btnDeverrouillerPoubelle = new Button("Déverrouiller Poubelle");
        btnDeverrouillerPoubelle.setOnAction(e -> {
            String poubelleSelectionnee = comboPoubelle.getValue();
            String couleurSelectionnee = comboCouleur.getValue();
            if (poubelleSelectionnee != null && couleurSelectionnee != null) {
                afficherMessage("La poubelle " + poubelleSelectionnee + " avec la couleur " + couleurSelectionnee + " a été déverrouillée !");
            } else {
                afficherMessage("Veuillez sélectionner une poubelle et une couleur.");
            }
        });

        Button btnRetour = new Button("Retour");
        btnRetour.setOnAction(e -> afficherMenuPrincipal(utilisateur));

        vbox.getChildren().addAll(new Label("Poubelle :"), comboPoubelle, new Label("Couleur :"), comboCouleur, btnDeverrouillerPoubelle, btnRetour);

        root.setCenter(vbox);
    }

	  private void updateListBonsDisponibles(ListView<BonAchat> listBons, Utilisateur utilisateur) {
	        listBons.getItems().clear();
	        listBons.getItems().addAll(utilisateur.consulterBonsAchatDisponibles(indexMin, indexMax));
	    }


 // Méthode pour afficher les bons d'achat et les magasins partenaires associés
	  private void afficherBonsAchats(Utilisateur utilisateur) {
		    VBox vbox = new VBox(10);
		    vbox.setStyle("-fx-padding: 20; -fx-alignment: center;");

		    // Création du label pour afficher le nombre de bons d'achat disponibles
		    labelBons = new Label("Nombre de bons d'achat valides : " + bonsDisponibles.size());

		    // Création de la ComboBox pour afficher les bons d'achat
		    comboBons = new ComboBox<>();
		    // On s'assure que la ComboBox est mise à jour avec les bons disponibles
		    comboBons.getItems().clear();  // Vider d'abord la ComboBox
		    comboBons.getItems().addAll(bonsDisponibles);  // Ajouter les bons disponibles dans la ComboBox

		    // Bouton pour sélectionner un bon d'achat
		    Button btnSelectBon = new Button("Sélectionner");
		    btnSelectBon.setOnAction(e -> {
		        BonAchat selectedBon = comboBons.getValue();
		        if (selectedBon != null) {
		            // Aucun magasin partenaire nécessaire, on ne fait plus rien ici
		        } else {
		            afficherMessage("Veuillez sélectionner un bon d'achat.");
		        }
		    });

		    // Bouton pour utiliser un bon d'achat
		    Button btnUseBon = new Button("Utiliser le Bon d'Achat");
		    btnUseBon.setOnAction(e -> {
		        BonAchat selectedBon = comboBons.getValue();
		        if (selectedBon != null) {
		            // Vérifier si le bon d'achat est valide avant de l'utiliser
		            boolean bonValide = false;

		            // On suppose que `BonAchat` a une méthode `estValide()` pour vérifier la validité du bon
		            if (selectedBon != null && selectedBon.estValide()) {
		                bonValide = true;
		            }

		            if (bonValide) {
		                // Utilisation du bon d'achat
		                utilisateur.utiliserBonAchat(selectedBon);

		                // Suppression du bon d'achat de la liste des bons disponibles
		                bonsDisponibles.remove(selectedBon);

		                // Mettre à jour la ComboBox pour refléter le changement
		                comboBons.getItems().clear(); // Vider la ComboBox
		                comboBons.getItems().addAll(bonsDisponibles); // Ajouter les bons restants

		                // Mettre à jour le label du nombre de bons
		                labelBons.setText("Nombre de bons d'achat valides : " + bonsDisponibles.size());

		                afficherMessage("Bon d'achat utilisé avec succès !");
		            } else {
		                afficherMessage("Le bon d'achat est invalide ou expiré.");
		            }
		        } else {
		            afficherMessage("Veuillez sélectionner un bon d'achat.");
		        }
		    });

		    Button btnRetour = new Button("Retour");
		    btnRetour.setOnAction(e -> afficherMenuPrincipal(utilisateur));

		    // Ajouter tous les éléments à la fenêtre sans la ComboBox des magasins
		    vbox.getChildren().addAll(labelBons, new Label("Sélectionnez un bon d'achat :"), comboBons, btnSelectBon,
		                              btnUseBon, btnRetour);

		    root.setCenter(vbox);
		}

    // Méthode pour afficher les messages (par exemple dans un pop-up)
    private void afficherMessage(String message) {
        // Affichage du message dans une fenêtre, une alerte, ou un label
        System.out.println(message); // Exemple simple
    }

    // Méthode pour afficher le menu principal
    private void afficherMenuPrincipal1(Utilisateur utilisateur) {
        // Logique pour afficher le menu principal de l'utilisateur
    }



    public static void main(String[] args) {
        launch(args);
    }}
