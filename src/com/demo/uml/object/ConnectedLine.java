package com.demo.uml.object;

import java.awt.Point;

public class ConnectedLine{
	public int px,py;
	public int ex,ey;
	
	BasicObject obj1;
	BasicObject obj2;
	int cp1;
	int cp2;
	
	public void setConnectedObjs(BasicObject obj1,BasicObject obj2){
		this.obj1 = obj1;
		this.obj2 = obj2;
	}
	
	public void setConnectedPoints(int cp1,int cp2){
		this.cp1 = cp1;
		this.cp2 = cp2;
	}
	
	public void updateConnectedPoint(){
		px = obj1.getConnectedPoint(cp1).x;
		py = obj1.getConnectedPoint(cp1).y;
		ex = obj2.getConnectedPoint(cp2).x;
		ey = obj2.getConnectedPoint(cp2).y;
	}
}
