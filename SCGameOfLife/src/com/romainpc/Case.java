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

import javafx.beans.property.SimpleDoubleProperty;
import javafx.scene.Parent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;

public class Case extends Parent{
	
	private int emplacementX;
	private int emplacementY;
	private SimpleDoubleProperty h;
	private SimpleDoubleProperty l;
	
	private boolean occupee;
	private Circle cell;
	
	public Case(int X, int Y){
		emplacementX = X;
		emplacementY = Y;
		
		h = new SimpleDoubleProperty();
		l = new SimpleDoubleProperty();
		h.set(14);
		l.set(14);
		l.bind(h);
		
		
		
		//fond:
		Rectangle fond = new Rectangle(0,0,14,14);
		fond.setFill(Color.rgb(0, 0, 50));
		fond.heightProperty().bind(h);
		fond.widthProperty().bind(l);
		
		this.getChildren().add(fond);
		
		
		//cellule:
		occupee = false;
		cell = new Circle(7,7,7);
		cell.setFill(Color.ORANGERED);
		cell.radiusProperty().bind(h.divide(2));
		cell.centerXProperty().bind(cell.radiusProperty());
		cell.centerYProperty().bind(cell.radiusProperty());
		this.getChildren().add(cell);
		cell.setVisible(false);
		
		
		//placement de cellules
		this.setOnMouseClicked(e -> {
			if(!Main.isInExecution()){
				if(occupee)
					this.deces();
				else
					this.naissance();
			}
		});
		
		this.setOnMouseExited(e -> {
			if(!Main.isInExecution() && Main.cliqueDroit()){
				if(occupee)
					this.deces();
				else
					this.naissance();
			}
		});
		
		
	}
	
	
	public void naissance(){
		cell.setVisible(true);
		occupee = true;
		Main.infoPlateau[getX()][getY()] = 1;
	}
	
	public void deces(){
		cell.setVisible(false);
		occupee = false;
		Main.infoPlateau[getX()][getY()] = 0;
	}
	public boolean isOccupee(){
		return occupee;
	}
	
	public int getX(){
		return emplacementX;
	}
	public int getY(){
		return emplacementY;
	}
	public SimpleDoubleProperty HProperty(){
		return h;
	}
	public SimpleDoubleProperty LProperty(){
		return l;
	}
	
}
