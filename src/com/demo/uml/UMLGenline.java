package com.demo.uml;

import java.awt.Point;

import com.demo.uml.object.ConnectedLine;

public class UMLGenline extends ConnectedLine{
	final double H = 20; // height of arrow
	final double L = 8; // half of underside  
	
	double awrad = Math.atan(L / H); //angle of arrow
	double arraow_len = Math.sqrt(L * L + H * H); //length of arrw
    double[] arrXY_1;
    double[] arrXY_2;
    
    double lx;
    double ly;
    double rx;
    double ry;
    
    public void countSidesPoint(){
    	arrXY_1 = rotateVec(ex - px, ey - py, awrad, true, arraow_len);  
        arrXY_2 = rotateVec(ex - px, ey - py, -awrad, true, arraow_len); 
        
        lx = ex - arrXY_1[0]; // (x3,y3)是第一端点  
        ly = ey - arrXY_1[1];  
        rx = ex - arrXY_2[0]; // (x4,y4)是第二端点  
        ry = ey - arrXY_2[1]; 
    }
	
	private double[] rotateVec(int px, int py, double ang,boolean isChLen, double newLen) {  
        double mathstr[] = new double[2];  
        // 矢量旋转函数，参数含义分别是x分量、y分量、旋转角、是否改变长度、新长度  
        double vx = px * Math.cos(ang) - py * Math.sin(ang);  
        double vy = px * Math.sin(ang) + py * Math.cos(ang);  
        if (isChLen) {  
            double d = Math.sqrt(vx * vx + vy * vy);  
            vx = vx / d * newLen;  
            vy = vy / d * newLen;  
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
	
	@Override
	public void updateConnectedPoint(){
		super.updateConnectedPoint();
		countSidesPoint();
	}
}
