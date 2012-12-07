package com.demo.uml;


import java.awt.BasicStroke;
import java.awt.Canvas;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Stroke;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.GeneralPath;
import java.util.ArrayList;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import com.demo.uml.object.BasicObject;
import com.demo.uml.object.ConnectedLine;




public class UMLCanvas extends JLayeredPane{
	final static int maxObject = 100;
	
	List<UMLComposite> comps = new ArrayList(); 
	List<UMLClass> classes = new ArrayList();
	List<UMLUsecase> usecases = new ArrayList();
	List<UMLAccline> acclines = new ArrayList();
	List<UMLGenline> genlines = new ArrayList();
	List<UMLComline> comlines = new ArrayList();
	
	static BasicObject selected;
	UMLComposite selecteds;
	Point[] selectArea = new Point[2];
	
	SelectedListener sl = new SelectedListener();
	
	private boolean isDrawPath=false;
	private boolean isSelectedArea=false;
	private boolean isListened=true;;
	
	int px;
	int py;
	int ex;
	int ey;
	
	UMLCanvas(){
		this.setLayout(null);
		addMouseListener(sl);
		addMouseMotionListener(sl);
	}
	
	@Override
	public void paintComponent(Graphics g) {  
        super.paintComponent(g);  
        
        Graphics2D g2 = (Graphics2D)g;
        Stroke st = g2.getStroke();     
        Stroke bs = new BasicStroke(1,BasicStroke.CAP_BUTT,
        		BasicStroke.JOIN_BEVEL,0,new float[]{4,4},0);     
        
        g2.setColor(Color.RED);
        g2.setStroke(bs);
        
        //draw Path
        if(isDrawPath){
			g2.drawLine(px, py, ex, ey);
        }
        //draw Area
        if(isSelectedArea){
        	g2.drawRect(px, py, ex-px, ey-py);           
        }
        
        g2.setStroke(st);   
        //draw connectedLine
        for(UMLAccline accline : acclines){
        	g2.drawLine(accline.px, accline.py, accline.ex, accline.ey);
        }
        for(UMLGenline genline : genlines){
        	drawGenline(genline,(Graphics2D)g);
        }     
        for(UMLComline comline : comlines){
        	drawComline(comline,(Graphics2D)g);
        }     
    } 
	
	public void drawGenline(UMLGenline line, Graphics2D g){
		/**
		 * Principle:
		 * Draw line first, then counting the points of arrow. 
		 * After counting,use GeneralPath to draw a filled triangle.	
		 */
		int lx,ly; //left of arrow
		int rx,ry; //right of arrow 
		line.countSidesPoint();
        lx = line.getLeftPoint().x;  
        ly = line.getLeftPoint().y;
        rx = line.getRightPoint().x;
        ry = line.getRightPoint().y;

        g.drawLine(line.px, line.py, line.ex, line.ey);  
        
        GeneralPath triangle = new GeneralPath();  
        triangle.moveTo(line.ex, line.ey);  
        triangle.lineTo(lx, ly);  
        triangle.lineTo(rx, ry);  
        triangle.closePath();  
        g.fill(triangle);
    }  
	
	public void drawComline(UMLComline line, Graphics2D g){
		/**
		 * Principle:
		 * Draw line first, then counting the points of arrow. 
		 * After counting,use GeneralPath to draw a filled triangle.	
		 */
		int lx,ly; //left of square
		int rx,ry; //right of square
		int vx,vy;
		line.countSidesPoint();
		lx = line.getLeftPoint().x;  
        ly = line.getLeftPoint().y;
        rx = line.getRightPoint().x;
        ry = line.getRightPoint().y;
        vx = line.getVertexPoint().x;
        vy = line.getVertexPoint().y;
		

        g.drawLine(line.px, line.py, line.ex, line.ey);  
        
        GeneralPath square = new GeneralPath();  
        square.moveTo(line.ex, line.ey);  
        square.lineTo(lx, ly);  
        square.lineTo(vx, vy);  
        square.lineTo(rx, ry);  
        square.closePath();  
        g.fill(square);
    }  
	
	public void connectLine(int px,int py,int ex,int ey,int type){
		isDrawPath = false;
		try{
			BasicObject obj1 = findConnectedObj(px,py);
			BasicObject obj2 = findConnectedObj(ex,ey);
			
			//connected point
			int cp1 = obj1.findConnectedPoint(px,py);
			int cp2 = obj2.findConnectedPoint(ex,ey);
			
			switch(type){
				case 1:
					UMLAccline accline = new UMLAccline();
					accline.px = obj1.getConnectedPoint(cp1).x;
					accline.py = obj1.getConnectedPoint(cp1).y;
					accline.ex = obj2.getConnectedPoint(cp2).x;
					accline.ey = obj2.getConnectedPoint(cp2).y;
					accline.setConnectedObjs(obj1, obj2);
					accline.setConnectedPoints(cp1,cp2);
					acclines.add(accline);
					break;
				case 2:	
					UMLGenline genline = new UMLGenline();
					genline.px = obj1.getConnectedPoint(cp1).x;
					genline.py = obj1.getConnectedPoint(cp1).y;
					genline.ex = obj2.getConnectedPoint(cp2).x;
					genline.ey = obj2.getConnectedPoint(cp2).y;
					genline.setConnectedObjs(obj1, obj2);
					genline.setConnectedPoints(cp1,cp2);
					genlines.add(genline);
					break;
				case 3:
					UMLComline comline = new UMLComline();
					comline.px = obj1.getConnectedPoint(cp1).x;
					comline.py = obj1.getConnectedPoint(cp1).y;
					comline.ex = obj2.getConnectedPoint(cp2).x;
					comline.ey = obj2.getConnectedPoint(cp2).y;
					comline.setConnectedObjs(obj1, obj2);
					comline.setConnectedPoints(cp1,cp2);
					comlines.add(comline);
					break;
			}
			
			System.out.println("Connect line "+obj1+" to "+obj2);
		}
		catch (NullPointerException exception){
			System.out.println("Not connect any object.");
		}
		finally{
			repaint();
		}		
	}
	
	public void groupSelectArea(){
		selectArea[0] = new Point(ex,ey);
		selectArea[1] = new Point(px,py);
		
		selecteds = new UMLComposite();
		comps.add(selecteds);
		selecteds.setOpaque(false);
		selecteds.myAddMouseListener(sl,sl);
		
		for(UMLClass clas : classes){
			if(clas.isSelected()){
				countSelectAreaBounds(clas);						
				selecteds.addObjToList(clas);
				clas.myRemoveMouseListener(sl,sl);
				remove(clas);
			}
		}
		for(UMLUsecase usecase : usecases){
			if(usecase.isSelected()){
				countSelectAreaBounds(usecase);
				selecteds.addObjToList(usecase);
				usecase.myRemoveMouseListener(sl,sl);
				remove(usecase);
			}
		}
		for(UMLComposite comp : comps){
			if(comp.isSelected()){
				countSelectAreaBounds(comp);						
				selecteds.addObjToList(comp);
				comp.myRemoveMouseListener(sl,sl);
				remove(comp);
			}
		}
		
		selecteds.setBounds(selectArea[0].x, selectArea[0].y,
				selectArea[1].x-selectArea[0].x, selectArea[1].y-selectArea[0].y);
		selecteds.addObject();
		selecteds.setSelected(true);

		add(selecteds,2);
		moveToFront(selecteds);
	}
	
	public void ungroupSelectArea(){
		try{
			if(selecteds.isTemp()){
				for(BasicObject obj : selecteds.objs){
					obj.setSelected(false);
					//recount location			
					obj.setLocation(obj.getLocation().x+selecteds.getX() , obj.getLocation().y+selecteds.getY());
					
					//restore listener
					obj.myAddMouseListener(sl,sl);
					
					obj.setGrouped(false);
					add(obj,2);
				}
				selecteds.myRemoveMouseListener(sl,sl);
				remove(selecteds);
				comps.remove(selecteds);
				selecteds = null;
			}
		}
		catch (NullPointerException e){
			System.out.println("Doesn't exist any group.");
		}
	}
	
	public void selectAreaObj(){
		//stop drawing area
		isSelectedArea = false;
		
		//cancel all select
		for(UMLClass clas : classes){
			clas.setSelected(false);
		}
		for(UMLUsecase usecase : usecases){
			usecase.setSelected(false);
		}
		for(UMLComposite comp : comps){
			comp.setSelected(false);
		}
		
		//find obj in area
		for(UMLClass clas : classes){
			if(clas.isInSelectedArea(px, py, ex, ey) && !clas.isGrouped()){
				clas.setSelected(true);				

			}
		}
		for(UMLUsecase usecase : usecases){
			if(usecase.isInSelectedArea(px, py, ex, ey) && !usecase.isGrouped()){
				usecase.setSelected(true);			
			}
		}
		for(UMLComposite comp : comps){
			if(comp.isInSelectedArea(px, py, ex, ey) && !comp.isGrouped()){
				comp.setSelected(true);			
			}
		}
		
		groupSelectArea();
	}
	
	private void countSelectAreaBounds(BasicObject obj){
		//count bounds
		//left
		if(obj.getLocation().x < selectArea[0].x){
			selectArea[0].x = obj.getLocation().x;
		}
		//right
		if(obj.getLocation().x + obj.w > selectArea[1].x){
			selectArea[1].x = obj.getLocation().x + obj.w;
		}
		//top
		if(obj.getLocation().y < selectArea[0].y){
			selectArea[0].y = obj.getLocation().y;
		}
		//bot
		if(obj.getLocation().y + obj.h > selectArea[1].y){
			selectArea[1].y = obj.getLocation().y + obj.h;
		}
	}
	
	private BasicObject findConnectedObj(int x,int y){
		for(UMLClass clas : classes){
			if(BasicObject.isClicked(x, y, clas)){
				return clas;
			}
		}
		for(UMLUsecase usecase : usecases){
			if(BasicObject.isClicked(x, y, usecase)){
				return usecase;
			}
		}
		return null;
	}
	
	public void drawConnectPath(int px,int py,int ex,int ey){	
		this.px = px;
		this.py = py;
		this.ex = ex;
		this.ey = ey;
		
		isSelectedArea = false;
		isDrawPath = true;
		repaint();
	}
	
	public void drawSelectedArea(int px,int py,int ex,int ey){
		this.px = (px>ex)? ex:px;
		this.py = (py>ey)? ey:py;
		this.ex = (px>ex)? px:ex;
		this.ey = (py>ey)? py:ey;
		
		isDrawPath = false;
		isSelectedArea = true;
		repaint();
	}
	
	public void addClass(int x,int y){	
		UMLClass clas = new UMLClass();
		clas.myAddMouseListener(sl, sl);
		clas.setLocation(new Point(x,y));		
		clas.setConnectedPoint();
		clas.setBorder();
		classes.add(clas);
		add(clas,2);
		moveToFront(clas);
	}
	
	
	public void addUsecase(int x,int y){
		UMLUsecase usecase = new UMLUsecase();
		usecase.myAddMouseListener(sl, sl);
		usecase.setLocation(new Point(x,y));		
		usecase.setConnectedPoint();
		usecase.setBorder();
		usecases.add(usecase);
		add(usecase,2);
		moveToFront(usecase);
	}
	
	public void setSelectedListener(boolean isActive){
		if(isActive){		
			for(UMLClass clas : classes){		
				if(clas.getParent() instanceof UMLCanvas){
					clas.myAddMouseListener(sl,sl);
				}
			}
			for(UMLUsecase usecase : usecases){
				if(usecase.getParent() instanceof UMLCanvas){
					usecase.myAddMouseListener(sl,sl);
				}
			}
		}
		else{
			for(UMLClass clas : classes){	
				if(clas.getParent() instanceof UMLCanvas){
					clas.myRemoveMouseListener(sl,sl);
				}
			}
			for(UMLUsecase usecase : usecases){
				if(usecase.getParent() instanceof UMLCanvas){
					usecase.myRemoveMouseListener(sl,sl);
				}
			}
		}
	}
	
	
	public void changeObjectName(String objname){
		if(selected != null)
			selected.setName(objname);
	}
	
	private void updateConnecedPoint(Object obj){
		if (obj instanceof UMLClass){
			UMLClass clas = (UMLClass)obj;
			clas.setConnectedPoint();
			clas.setBorder();
		}
		else if(obj instanceof UMLUsecase){
			UMLUsecase usecase = (UMLUsecase) obj;
			usecase.setConnectedPoint();
			usecase.setBorder();
		}
		else if(obj instanceof UMLComposite){
			UMLComposite comp = (UMLComposite) obj;
			comp.setBorder();
			comp.reviseConnectedPoint();
		}
		
		//update all connected line
		for(UMLAccline accline : acclines){
			accline.updateConnectedPoint();
		}
		for(UMLGenline genline : genlines){
			genline.updateConnectedPoint();
		}
		for(UMLComline comline : comlines){
			comline.updateConnectedPoint();
		}
		repaint();
	}
	
	
	class SelectedListener extends MouseAdapter{		
		@Override
		public void mouseReleased(MouseEvent e){
			if(isSelectedArea){
				selectAreaObj();
				repaint();
			}
		}
		
		@Override
		public void mousePressed(MouseEvent e) {
			if (UMLEditor.chosenFunction == 0){
				//cancel select
				for(UMLClass clas : classes){
					if(clas.getParent() != selecteds)
						clas.setSelected(false);
				}
				for(UMLUsecase usecase : usecases){
					if(usecase.getParent() != selecteds)
					usecase.setSelected(false);
				}
				for(UMLComposite comp : comps){
					comp.setSelected(false);
					comp.repaint();
				}
					
				if(e.getSource() instanceof UMLClass){
					selected = (UMLClass)e.getSource();
					selected.setSelected(true);
					moveToFront(selected);
				}
				else if(e.getSource() instanceof UMLUsecase){
					selected = (UMLUsecase)e.getSource();
					selected.setSelected(true);
					moveToFront(selected);
				}
				else if(e.getSource() instanceof UMLCanvas){
					if(selecteds != null){
						if(selecteds.isTemp()){
							ungroupSelectArea();
						}
						else{
							selecteds.setSelected(false);
							selecteds.repaint();
						}
					}
					px = e.getX();
					py = e.getY();
				}
				else if(e.getSource() instanceof UMLComposite){
					selecteds = (UMLComposite) e.getSource();
					selecteds.setSelected(true);
					selecteds.repaint();
				}
			}
		}
		
		
		@Override
		public void mouseDragged(MouseEvent e){
			int dx = e.getX();
			int dy = e.getY();
			switch(UMLEditor.chosenFunction){
				case 0:
					if (selected != null && e.getSource()==selected){
						//pre-calculator location to prevent picture shining
						selected.setLocation(selected.getX()+dx-48,selected.getY()+dy-40);
						moveToFront(selected);
						updateConnecedPoint(e.getSource());						
					}
					else if(e.getSource() instanceof UMLCanvas){
						drawSelectedArea(px,py,dx,dy);
					}
					else if(e.getSource() instanceof UMLComposite){
						UMLComposite comp = (UMLComposite)e.getSource();
						comp.setLocation(comp.getX()+dx-comp.getWidth()/2,comp.getY()+dy-comp.getHeight()/2);
						moveToFront(comp);
						updateConnecedPoint(e.getSource());	
					}
					break;
			}
		}
	}
	

}
