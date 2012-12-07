package com.demo.uml;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Stroke;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JPanel;

import com.demo.uml.object.BasicObject;

public class UMLComposite extends BasicObject{
	static final int maxObject = 300;
	public List<BasicObject> objs = new ArrayList();
	
	private Point location;
	private boolean isTemp = true;
	private boolean isSelected = false;

	public UMLComposite(){
		super();
		setLayout(null);
	}
	
	@Override
	public void paintComponent(Graphics g) {  	
		Graphics2D g2 = (Graphics2D) g;
		Stroke bs = new BasicStroke(1,BasicStroke.CAP_BUTT,
        		BasicStroke.JOIN_BEVEL,0,new float[]{4,4},0);     
        
        g2.setColor(Color.RED);
        g2.setStroke(bs);
		
		//draw border
        if(!isTemp && isSelected){
        	g2.drawRect(0, 0, getWidth()-1, getHeight()-1);          
        }
	}
	
	@Override
	public void setBounds(int x,int y,int width,int height){
		super.setBounds(x, y, width, height);
		location = getLocation();
		this.w = width;
		this.h = height;
	}
	
	public void reviseConnectedPoint(){
		location = getLocation();
		for(BasicObject obj : objs){
			if(obj instanceof UMLComposite){
				UMLComposite temp = (UMLComposite)obj;
				temp.reviseConnectedPoint(location.x+temp.getX() , location.y+temp.getY());
			}
			else
				obj.reviseConnectedPoint(location.x,location.y);
		}
	}
	
	@Override
	public void reviseConnectedPoint(int dx,int dy){
		for(BasicObject obj : objs){
			obj.reviseConnectedPoint(dx,dy);
		}
	}
	
	public void addObjToList(BasicObject obj){
		objs.add(obj);
	}
	
	public void addObject(){	
		location = getLocation();
		for(BasicObject obj : objs){
			obj.setLocation(obj.getX()-location.x , obj.getY()-location.y);
			add(obj);
		}
	}
	
	public void group(){
		for(BasicObject obj : objs){
			obj.setSelected(false);
			obj.setGrouped(true);
		}
		repaint();
	}

	public boolean isTemp() {
		return isTemp;
	}

	public void setTemp(boolean isTemp) {
		this.isTemp = isTemp;
	}

	public boolean isSelected() {
		return isSelected;
	}

	public void setSelected(boolean isSelected) {
		this.isSelected = isSelected;
	}
}
