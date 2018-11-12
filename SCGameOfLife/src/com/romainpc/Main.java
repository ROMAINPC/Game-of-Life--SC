/*******************************************************************************
 * Copyright (C) 2018 ROMAINPC_LECHAT
 * 
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 ******************************************************************************/
package com.romainpc;
	
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Slider;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;


public class Main extends Application {
	
	private static boolean execution;
	private static boolean cliqueDroit;
	
	public static Timeline actualisation;
	public static int[][] infoPlateau;
	
	@Override
	public void start(Stage primaryStage) {
		try {
			VBox root = new VBox();
			Scene scene = new Scene(root,601,601 + 100);
			
			//fond:
			root.setBackground(new Background(new BackgroundFill(Color.rgb(20, 90, 200), null, null)));
			
			//plateau:
			GridPane plateau = new GridPane();
			plateau.setVgap(1);
			plateau.setHgap(1);
			plateau.setPadding(new Insets(1,0,0,1));
			root.getChildren().add(plateau);
			
			
			Case[][] plateauTab = new Case[40][40];
			infoPlateau = new int[40][40];
			
			for(int i = 0 ; i < 40 ; i++){
				for(int j = 0 ; j < 40 ; j++){
					plateauTab[i][j] = new Case(i, j);
					plateau.add(plateauTab[i][j], i, j);
					plateauTab[i][j].HProperty().bind(scene.heightProperty().subtract(41 + 100).divide(40));
				}
			}
			
			
			
			
			
			
			//gestion des règles du jeu:
			Text texte1 = new Text("Nombre de voisins pour survivre:");
			Text texte2 = new Text("Nombre de voisins pour naître:");
			HBox boite1 = new HBox();
			boite1.getChildren().addAll(texte1, texte2);
			boite1.setPrefHeight(20);
			boite1.setSpacing(30);
			root.getChildren().add(boite1);
			
			HBox boite2 = new HBox();
			boite2.setPrefHeight(30);
			boite2.setSpacing(30);
			FlowPane boite21 = new FlowPane();
			FlowPane boite22 = new FlowPane();
			boite2.getChildren().addAll(boite21, boite22);
			root.getChildren().add(boite2);
			
			//stockage des valeurs:
			CheckBox[] tabsurvie = new CheckBox[9];
			for(int i=0; i < 9 ; i++){
				tabsurvie[i] = new CheckBox(String.valueOf(i));
				tabsurvie[i].setFont(Font.font("Verdana", FontWeight.EXTRA_BOLD, 14));
				tabsurvie[i].setTextFill(Color.BLACK);
				boite21.getChildren().add(tabsurvie[i]);
			}
			tabsurvie[2].setSelected(true);
			tabsurvie[3].setSelected(true);
			
			
			CheckBox[] tabnaissance = new CheckBox[9];
			for(int i=0; i < 9 ; i++){
				tabnaissance[i] = new CheckBox(String.valueOf(i));
				tabnaissance[i].setFont(Font.font("Verdana", FontWeight.EXTRA_BOLD, 14));
				tabnaissance[i].setTextFill(Color.BLACK);
				boite22.getChildren().add(tabnaissance[i]);
			}
			tabnaissance[3].setSelected(true);
			
			
			HBox boite3 = new HBox();
			//bouton play pause:
			
			Button bouton = new Button("Start");
			boite3.getChildren().add(bouton);
			root.getChildren().addAll(boite3);
			bouton.setPrefSize(200, 50);
			
			execution = false;
			
			bouton.setOnAction(e ->{
				if(!execution){
					//lancement animation
					bouton.setText("Stop");
					execution = true;
					
				}else{
					//arrêt animation
					bouton.setText("Start");
					execution = false;
					
				}
			});
			
			
			//bouton clear:
			Button boutonEff = new Button("Effacer");
			boite3.getChildren().add(0,boutonEff);
			boutonEff.setPrefSize(200, 50);
			boutonEff.setOnAction(e ->{
				for(int i = 0 ; i < 40 ; i++){
					for(int j = 0 ; j < 40 ; j++){
						infoPlateau[i][j] = 0;
						plateauTab[i][j].deces();
					}
				}
			});
			
			
			
			//slider vitesse:
			Slider slider = new Slider();
			slider.setMin(1);
			slider.setMax(459);
			boite3.getChildren().add(slider);
			slider.setPrefSize(300, 50);
			slider.setValue(400);
			
			
			
			
			
			
			
			
			//gestion layout:
			primaryStage.heightProperty().addListener(e -> {
				primaryStage.setWidth(primaryStage.getHeight() - 100);
			});
			
			primaryStage.widthProperty().addListener(e -> {
				primaryStage.setHeight(primaryStage.getWidth() + 100);
				texte1.setFont(Font.font("Comic Sans MS", FontWeight.EXTRA_BOLD, primaryStage.getWidth() / 40));
				texte2.setFont(Font.font("Comic Sans MS", FontWeight.EXTRA_BOLD, primaryStage.getWidth() / 40));
				bouton.setFont(Font.font("Comic Sans MS", FontWeight.EXTRA_BOLD, primaryStage.getWidth() / 40));
				boutonEff.setFont(Font.font("Comic Sans MS", FontWeight.EXTRA_BOLD, primaryStage.getWidth() / 45));
			});
			
			
			
			
			
			//rafraichissement de l'image:
			
			Calculateur calculs = new Calculateur(infoPlateau, plateauTab, tabsurvie, tabnaissance);
			calculs.start();
			
			actualisation = new Timeline(new KeyFrame(Duration.millis(100), new EventHandler<ActionEvent>(){
				public void handle(ActionEvent arg0) {
					
					
					
					//rafraichissement:
//					System.out.println("refresh");
					for(int i = 0 ; i < 40 ; i++){
						for(int j = 0 ; j < 40 ; j++){
							if(infoPlateau[i][j] == 1 && !plateauTab[i][j].isOccupee()){
								plateauTab[i][j].naissance();
							}
							if(infoPlateau[i][j] == 0 && plateauTab[i][j].isOccupee()){
								plateauTab[i][j].deces();
							}
						}
					}
					
					
					synchronized(calculs){
						calculs.notify();
					}
					
					
				}
			}));
			actualisation.setCycleCount(Timeline.INDEFINITE);
			actualisation.play();
			
			
			
			slider.valueProperty().addListener(e->{
				actualisation.stop();
				
				KeyFrame key = new KeyFrame(Duration.millis(500-slider.getValue()), new EventHandler<ActionEvent>(){
					public void handle(ActionEvent arg0) {
						
						
						
						//rafraichissement:
//						System.out.println("refresh");
						for(int i = 0 ; i < 40 ; i++){
							for(int j = 0 ; j < 40 ; j++){
								if(infoPlateau[i][j] == 1 && !plateauTab[i][j].isOccupee()){
									plateauTab[i][j].naissance();
								}
								if(infoPlateau[i][j] == 0 && plateauTab[i][j].isOccupee()){
									plateauTab[i][j].deces();
								}
							}
						}
						
						
						synchronized(calculs){
							calculs.notify();
						}
						
						
					}
				});
				
				actualisation.getKeyFrames().setAll(key);
				actualisation.play();
				
			});
			
			
			
			
			primaryStage.setTitle("Game of Life by ROMAINPC");
			primaryStage.setScene(scene);
			primaryStage.show();
			
			
			primaryStage.setOnCloseRequest(e ->{
				calculs.interrupt();
				Platform.exit();
				
			});
			
			
			
			
			//souris:
			cliqueDroit = false;
			scene.setOnMouseClicked(e ->{
				if(e.getButton().equals(MouseButton.SECONDARY)){
					if(cliqueDroit)
						cliqueDroit = false;
					else
						cliqueDroit = true;
				}
			});
			
			
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}

	public static boolean isInExecution() {
		return execution;
	}

	public static boolean cliqueDroit() {
		return cliqueDroit;
	}
}
