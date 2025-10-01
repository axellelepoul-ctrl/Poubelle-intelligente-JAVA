package IHM;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import anya.poubelle.*; // Assurez-vous que ces classes sont bien importées

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.time.LocalDate;

public class dsfdgfg extends Application {

    private BorderPane root;

    // Déclaration des variables globales
    private List<BonAchat> bonsDisponibles;
    private List<Commerce> magasinsPartenaires;  // Liste des magasins partenaires
    private ComboBox<BonAchat> comboBons;
    private ComboBox<String> comboMagasins;
    private Label labelBons;
    private Map<BonAchat, Commerce> bonToMagasinMap; // Associe un bon à un magasin

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
        Commerce carrefour = new Commerce("Carrefour");
        Commerce ikea = new Commerce("IKEA");
        Commerce decathlon = new Commerce("Decathlon");
        magasinsPartenaires.add(carrefour);
        magasinsPartenaires.add(ikea);
        magasinsPartenaires.add(decathlon);

        // Associer les bons aux magasins
        bonToMagasinMap = new HashMap<>();
        bonToMagasinMap.put(bon1, carrefour);
        bonToMagasinMap.put(bon2, ikea);
        bonToMagasinMap.put(bon3, decathlon);

        // Initialisation de la fenêtre principale
        primaryStage.setTitle("Interface Utilisateur");
        root = new BorderPane();

        // Barre supérieure avec les informations utilisateur
        HBox topBar = new HBox();
        Label labelInfos = new Label("Nom: " + utilisateur.getNom() + " | Points Fidélité: " + utilisateur.getPointsFidelite());
        topBar.setStyle("-fx-padding: 10;");
        topBar.getChildren().add(labelInfos);
        root.setTop(topBar);

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

        Button btnMesBonsAchats = new Button("Mes Bons d'Achats");
        Button btnReclamerBonsAchats = new Button("Réclamer Bons d'Achats");

        // Actions des boutons
        btnMesBonsAchats.setOnAction(e -> afficherBonsAchats(utilisateur));
        btnReclamerBonsAchats.setOnAction(e -> afficherReclamerBonAchat(utilisateur));

        btnMesBonsAchats.setStyle("-fx-font-size: 16px; -fx-padding: 10;");
        centerPanel.getChildren().addAll(btnMesBonsAchats, btnReclamerBonsAchats);

        // Mettre à jour le contenu central
        root.setCenter(centerPanel);
    }

    private void afficherBonsAchats(Utilisateur utilisateur) {
        VBox vbox = new VBox(10);
        vbox.setStyle("-fx-padding: 20; -fx-alignment: center;");

        labelBons = new Label("Nombre de bons d'achat valides : " + bonsDisponibles.size());

        comboBons = new ComboBox<>();
        comboBons.getItems().addAll(bonsDisponibles);

        comboMagasins = new ComboBox<>();

        Button btnSelectBon = new Button("Sélectionner");
        btnSelectBon.setOnAction(e -> {
            BonAchat selectedBon = comboBons.getValue();
            if (selectedBon != null) {
                comboMagasins.getItems().clear();
                Commerce associatedMagasin = bonToMagasinMap.get(selectedBon);
                if (associatedMagasin != null) {
                    comboMagasins.getItems().add(associatedMagasin.getNom());
                }
            } else {
                afficherMessage("Veuillez sélectionner un bon d'achat.");
            }
        });

        Button btnUseBon = new Button("Utiliser le Bon d'Achat");
        btnUseBon.setOnAction(e -> {
            BonAchat selectedBon = comboBons.getValue();
            String selectedMagasin = comboMagasins.getValue();
            if (selectedBon != null && selectedMagasin != null) {
                utilisateur.utiliserBonAchat(selectedBon);
                bonsDisponibles.remove(selectedBon);
                comboBons.getItems().remove(selectedBon);
                labelBons.setText("Nombre de bons d'achat valides : " + bonsDisponibles.size());
                afficherMessage("Bon d'achat utilisé avec succès dans le magasin " + selectedMagasin + " !");
            } else {
                afficherMessage("Veuillez sélectionner un bon d'achat et un magasin.");
            }
        });

        Button btnRetour = new Button("Retour");
        btnRetour.setOnAction(e -> afficherMenuPrincipal(utilisateur));

        vbox.getChildren().addAll(labelBons, new Label("Sélectionnez un bon d'achat :"), comboBons, btnSelectBon,
                new Label("Sélectionnez un magasin :"), comboMagasins, btnUseBon, btnRetour);

        root.setCenter(vbox);
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

    private void afficherMessage(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setContentText(message);
        alert.showAndWait();
    }

    public static void main(String[] args) {
        launch(args);
    }
}



