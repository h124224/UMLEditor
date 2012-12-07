package com.demo.uml;

import java.awt.Point;

import javax.swing.ImageIcon;

import com.demo.uml.object.BasicObject;


public class UMLUsecase extends BasicObject{	
	private Point location;
	
	UMLUsecase(){
		w = 96;
		h = 57;
		location = getLocation();
		setSize(w,h);
		setLayout(null);
		setOpaque(false);
		
		initName("",47, 10, 30, 30);
		initSelect(new ImageIcon("umleditor/usecaseSelected.png"),w,h);
		initBg(new ImageIcon("umleditor/usecase.png"),w,h);
	}
	
	@Override
	public void setConnectedPoint(){
		location = getLocation();
		connectedPoint[0] = new Point(location.x+48,location.y+3);
		connectedPoint[1] = new Point(location.x+48,location.y+53);
		connectedPoint[2] = new Point(location.x+0,location.y+25);
		connectedPoint[3] = new Point(location.x+95,location.y+25);
	}
	
	@Override
	public void setConnectedPoint(int dx,int dy){
		location = getLocation();
		connectedPoint[0] = new Point(location.x+48+dx,location.y+3+dy);
		connectedPoint[1] = new Point(location.x+48+dx,location.y+53+dy);
		connectedPoint[2] = new Point(location.x+0+dx,location.y+25+dy);
		connectedPoint[3] = new Point(location.x+95+dx,location.y+25+dy);
	}
	
	public void setBorder(){
		location = getLocation();
		border[0] = location.y;
		border[1] = location.y+h;
		border[2] = location.x;
		border[3] = location.x+w;
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
