package com.demo.uml;

import java.awt.Point;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JLabel;

import com.demo.uml.object.BasicObject;




public class UMLClass extends BasicObject{
	UMLClass(){
		w = 96;
		h = 117;
		location = getLocation();
		setSize(w,h);
		setLayout(null);
		setOpaque(false);
		
		initName("",41, 15, 30, 30);
		initSelect(new ImageIcon("umleditor/classSelected.png"),w,h);
		initBg(new ImageIcon("umleditor/class.png"),w,h);
	}
	
	@Override
	public void setConnectedPoint(){
		location = getLocation();
		connectedPoint[0] = new Point(location.x+45,location.y+10);
		connectedPoint[1] = new Point(location.x+45,location.y+106);
		connectedPoint[2] = new Point(location.x+10,location.y+60);
		connectedPoint[3] = new Point(location.x+80,location.y+60);
	}
	
	@Override
	public void setConnectedPoint(int dx,int dy){
		location = getLocation();
		connectedPoint[0] = new Point(location.x+45+dx,location.y+10+dy);
		connectedPoint[1] = new Point(location.x+45+dx,location.y+106+dy);
		connectedPoint[2] = new Point(location.x+10+dx,location.y+60+dy);
		connectedPoint[3] = new Point(location.x+80+dx,location.y+60+dy);
	}
	
	@Override
	public void reviseConnectedPoint(int dx,int dy){
		setConnectedPoint(dx,dy);
	}
	
	@Override
	public void setLocation(int x,int y){
		super.setLocation(x,y);
		location = getLocation();
		setBorder();
		setConnectedPoint();		
	}
}
