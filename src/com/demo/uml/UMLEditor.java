package com.demo.uml;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JOptionPane;
import javax.swing.JToggleButton;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;



public class UMLEditor extends JFrame{
	//component
	private JToggleButton[] btnFunction;
	private UMLCanvas canvas;

	
	public static int chosenFunction=0;
	private FunctionListener fl = new FunctionListener();
	private PaintListener pl = new PaintListener();
	
	
	UMLEditor() {
        // set the property of frame
        setSize(800, 600);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
 
        setMenubar();
        setFunctionArea();
        setPaintingArea();

    }
	
	private void setMenubar()
	{
		// set the menubar
        JMenuBar bar = new JMenuBar();
        setJMenuBar(bar);
 
        // set a menu.
        JMenu menuFile = new JMenu("File");
        bar.add(menuFile);
        JMenu menuEdit = new JMenu("Edit");
        bar.add(menuEdit);
 
        // add sub menus.
        menuFile.add(new JMenuItem("Open File"));
        menuFile.addSeparator();
        menuFile.add(new JMenuItem("Close"));
        
        JMenuItem group = new JMenuItem("Group");
        group.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				canvas.selecteds.setTemp(false);
				canvas.selecteds.group();
			}      	
        });
        menuEdit.add(group);
        
        JMenuItem ungroup = new JMenuItem("Ungroup");
        ungroup.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				canvas.selecteds.setTemp(true);
				canvas.ungroupSelectArea();
			}      	
        });
        menuEdit.add(ungroup);
        
        menuEdit.addSeparator();
        JMenuItem changeObjName = new JMenuItem("Change Object Name");
        //changeObjName.setEnabled(false);
        changeObjName.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				String objName = JOptionPane.showInputDialog(null,"Change Object Name");
				if (objName != null)
					canvas.changeObjectName(objName);				
			}      	
        });
        menuEdit.add(changeObjName);
	}
	
	private void setFunctionArea()
	{
		//set function area
        JPanel panelFunction = new JPanel(new GridLayout(6,1));
        
        //set button
        btnFunction = new JToggleButton[6];
        
        btnFunction[0] = new JToggleButton(new ImageIcon("umleditor/select.png"));
        btnFunction[1] = new JToggleButton(new ImageIcon("umleditor/accline.png"));
        btnFunction[2] = new JToggleButton(new ImageIcon("umleditor/genline.png"));
        btnFunction[3] = new JToggleButton(new ImageIcon("umleditor/comline.png"));
        btnFunction[4] = new JToggleButton(new ImageIcon("umleditor/addclass.png"));
        btnFunction[5] = new JToggleButton(new ImageIcon("umleditor/addusecase.png"));
        for(int i=0;i<6;i++){
        	btnFunction[i].addActionListener(fl);
            panelFunction.add(btnFunction[i]);
        }
        add(panelFunction,BorderLayout.WEST);
	}
	
	private void setPaintingArea(){
		//set draw area
		canvas = new UMLCanvas();
        canvas.setBackground(Color.WHITE);
        canvas.addMouseListener(pl);
        canvas.addMouseMotionListener(pl);
        add(canvas,BorderLayout.CENTER);
	}
	
	
	public static void main( String args[] ){
        // Create an instance of the test application
		UMLEditor frame = new UMLEditor();
        frame.setVisible(true);
    }
	
	class PaintListener extends MouseAdapter{
		int px=0,py=0;
		int ex=0,ey=0;
		
		@Override
		public void mouseClicked(MouseEvent e) {
			// TODO Auto-generated method stub
			switch(chosenFunction){
				case 0:
					break;
				case 1:
					
					break;
				case 2:
					
					break;
				case 3:
					canvas.updateUI();
					break;
				case 4:
					canvas.addClass(e.getX(),e.getY());
					canvas.updateUI();
					break;
				case 5:
					canvas.addUsecase(e.getX(),e.getY());
					canvas.updateUI();
					break;
					
			}			
		}

		@Override
		public void mousePressed(MouseEvent e) {
	        px = e.getX();
	        py = e.getY();
		}

		@Override
		public void mouseReleased(MouseEvent e) {
			switch(UMLEditor.chosenFunction){
				case 1:
				case 2:
				case 3:
					canvas.connectLine(px,py,ex,ey,chosenFunction);
					break;
			}

		}
		
		@Override
		public void mouseDragged(MouseEvent e){
			switch(UMLEditor.chosenFunction){
			case 0:					
				break;
			case 1:
			case 2:
			case 3:
				ex = e.getX();  
		        ey = e.getY();			        
		        canvas.drawConnectPath(px,py,ex,ey);
				break;
			}
		}
	}
	
	class FunctionListener implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent e) {
			JToggleButton btnPressed = (JToggleButton)e.getSource();
			//cancel all buttons state;
			for(int i=0;i<6;i++){
				btnFunction[i].setSelected(false);
			}
			
			canvas.ungroupSelectArea();
			if(btnPressed == btnFunction[0]){
				chosenFunction = 0;
				canvas.setSelectedListener(true);
			}
			else if(btnPressed == btnFunction[1]){
				chosenFunction = 1;
				canvas.setSelectedListener(false);
			}
			else if(btnPressed == btnFunction[2]){
				chosenFunction = 2;
				canvas.setSelectedListener(false);
			}
			else if(btnPressed == btnFunction[3]){
				chosenFunction = 3;
				canvas.setSelectedListener(false);
			}
			else if(btnPressed == btnFunction[4]){
				chosenFunction = 4;
				canvas.setSelectedListener(true);
			}
			else{
				chosenFunction = 5;
				canvas.setSelectedListener(true);
			}
			btnFunction[chosenFunction].setSelected(true);
			
		}	
	}
}
