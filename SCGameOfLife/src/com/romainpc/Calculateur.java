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

import javafx.scene.control.CheckBox;

public class Calculateur extends Thread{
	
	private boolean partieEnCours;
	private int[][] tabcalc;
	private Case[][] tab;
	private CheckBox[] tabsurvie;
	private CheckBox[] tabnaissance;
	
	public Calculateur(int[][] infoplateau, Case[][] plateau, CheckBox[] tabsurv, CheckBox[] tabnaiss){
		partieEnCours = true;
		tabcalc = infoplateau;
		tab = plateau;
		tabsurvie = tabsurv;
		tabnaissance = tabnaiss;
	}
	
	public synchronized void run(){
		
		while(partieEnCours){
			
			try {
				this.wait();
			} catch (InterruptedException e) {
				System.out.println("Interruption");
				partieEnCours = false;
				return;
			}
			
			if(Main.isInExecution()){
				
			
			
//			System.out.println("calculs");
			
			//calculs:
			for(int i = 0 ; i < 40 ; i++){
				for (int j = 0 ; j < 40 ; j++){
					
					int voisins = 0;
					
					if(tab[(i-1+40)%40][(j-1+40)%40].isOccupee())
						voisins++;
					
					if(tab[i][(j-1+40)%40].isOccupee())
						voisins++;
					
					if(tab[(i+1)%40][(j-1+40)%40].isOccupee())
						voisins++;
					
					if(tab[(i+1)%40][j].isOccupee())
						voisins++;
					
					if(tab[(i+1)%40][(j+1)%40].isOccupee())
						voisins++;
					
					if(tab[i][(j+1)%40].isOccupee())
						voisins++;
					
					if(tab[(i-1+40)%40][(j+1)%40].isOccupee())
						voisins++;
					
					if(tab[(i-1+40)%40][j].isOccupee())
						voisins++;
					
					if(tab[i][j].isOccupee()){
						//conditions de décès:
						if(!tabsurvie[voisins].isSelected()){
							tabcalc[i][j] = 0;
						}
						
						
					}else{
						//conditions de naissance:
						if(tabnaissance[voisins].isSelected()){
							tabcalc[i][j] = 1;
						}
						
						
					}
					
					
				}
			}
			
			
			
			
			
			
			}
		}
		System.out.println("fermeture pile");
	}
	
}
