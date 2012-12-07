package com.demo.uml.object;


import java.awt.Color;
import java.awt.Point;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class BasicObject extends JPanel{
	public int w = 0;
	public int h = 0;
	
	protected int[] border = new int[4];
	protected Point[] connectedPoint = new Point[4];
	
	protected Point location;
	private boolean isGrouped;
	private boolean isSelected;
	private boolean isListened=false;
	private int depth;
	
	private JLabel bg;
	private JLabel select;
	private JLabel name;
	
	
	static public boolean isClicked(int x,int y,BasicObject obj){		
		if(x >= obj.border[2] && x <= obj.border[3] && y >= obj.border[0] && y<= obj.border[1]){	
			return true;
		}
		else
			return false;
	}
	
	public Point getConnectedPoint(int cloest){
		return connectedPoint[cloest];
	}
	
	public int findConnectedPoint(int x,int y){
		double min = 999999;
		int closest=0;
		
		for(int i=0;i<4;i++){
			double distance = Math.sqrt((x-connectedPoint[i].x)*(x-connectedPoint[i].x)
					+ (y-connectedPoint[i].y)*(y-connectedPoint[i].y));
			if(min >= distance){
				min = distance;
				closest = i;
			}
			
		}
		return closest;
	}
	
	public boolean isInSelectedArea(int px,int py,int ex,int ey){
		int x = getLocation().x;
		int y = getLocation().y;
		if(x >= px && x <= ex && y >= py && y<= ey){	
			return true;
		}
		else
			return false;
	}

	public void initBg(Icon icon,int width,int height) {
		bg = new JLabel(icon);
		bg.setBounds(0, 0, width, height);	
		bg.setOpaque(false);
		add(bg);
	}

	public void initSelect(Icon icon,int width,int height) {
		select = new JLabel(icon);
		select.setOpaque(false);
		select.setBounds(0, 0, width, height);
		select.setVisible(false);
		add(select);	
	}
	
	public void initName(String name,int x,int y,int width,int height) {
		this.name = new JLabel(name);
		this.name.setBounds(41, 15, 30, 30);
		add(this.name);
	}
	
	public void setName(String name){
		this.name.setText(name);
		this.name.setForeground(Color.RED);
	}

	public boolean isSelected() {
		return isSelected;
	}

	public void setSelected(boolean isSelected) {
		this.select.setVisible(isSelected);
		this.isSelected = isSelected;
	}
	
	public void setConnectedPoint(){
	}
	
	public void setConnectedPoint(int dx,int dy){
	}
	
	public void reviseConnectedPoint(int dx,int dy){
		setConnectedPoint(dx,dy);
	}
	
	public void setBorder(){
		location = getLocation();
		border[0] = location.y;
		border[1] = location.y+h;
		border[2] = location.x;
		border[3] = location.x+w;
	}
	
	public void myAddMouseListener(MouseListener ml,MouseMotionListener mml){
		if(!isListened){
			addMouseListener(ml);
			addMouseMotionListener(mml);
		}
		isListened = true;
	}
	
	public void myRemoveMouseListener(MouseListener ml,MouseMotionListener mml){
		if(isListened){
			removeMouseListener(ml);
			removeMouseMotionListener(mml);
		}
		isListened = false;
	}
	

	public boolean isListened() {
		return isListened;
	}

	public void setListened(boolean isListened) {
		this.isListened = isListened;
	}

	public boolean isGrouped() {
		return isGrouped;
	}

	public void setGrouped(boolean isGrouped) {
		this.isGrouped = isGrouped;
	}
}
