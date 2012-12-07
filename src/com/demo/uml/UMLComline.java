package com.demo.uml;

import java.awt.Point;

import com.demo.uml.object.ConnectedLine;

public class UMLComline extends ConnectedLine{
	final double sideLength = 15; 
	
    double[] arrXY_1;
    double[] arrXY_2;
    double awrad = Math.atan(1);
    
    double lx;
    double ly;
    double rx;
    double ry;
    double vx;
    double vy;
    
    public void countSidesPoint(){
    	arrXY_1 = rotateVec(ex - px, ey - py, awrad, true);  
        arrXY_2 = rotateVec(ex - px, ey - py, -awrad, true); 
        
        lx = ex - arrXY_1[0];
        ly = ey - arrXY_1[1];  
        rx = ex - arrXY_2[0];
        ry = ey - arrXY_2[1]; 
        
        double length =  Math.sqrt((ex-px)*(ex-px) + (ey-py)*(ey-py));
        vx = ex - 1.4 * sideLength * ((ex-px)/length);
        vy = ey - 1.4 * sideLength * ((ey-py)/length);
    }
	
	private double[] rotateVec(int px, int py, double ang,boolean isChLen) {  
        double mathstr[] = new double[2];  
        // 矢量旋转函数，参数含义分别是x分量、y分量、旋转角、是否改变长度、新长度  
        double vx = px * Math.cos(ang) - py * Math.sin(ang);  
        double vy = px * Math.sin(ang) + py * Math.cos(ang);  
        if (isChLen) {  
            double d = Math.sqrt(vx * vx + vy * vy);  
            vx = vx / d * sideLength;  
            vy = vy / d * sideLength;  
            mathstr[0] = vx;  
            mathstr[1] = vy;  
        }  
        return mathstr;  
    }
    
	public Point getLeftPoint(){
		Point lp = new Point(new Double(lx).intValue(),new Double(ly).intValue());
		return lp;
	}

	public Point getRightPoint(){
		Point rp = new Point(new Double(rx).intValue(),new Double(ry).intValue());
		return rp;
	}
	
	public Point getVertexPoint(){
		Point vp = new Point(new Double(vx).intValue(),new Double(vy).intValue());
		return vp;
	}
}
