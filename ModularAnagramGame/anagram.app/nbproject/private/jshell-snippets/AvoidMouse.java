package test;


import com.toy.anagram.app.Anagrams;
import java.awt.Component;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


/**
 *
 * @author sdedic
 */
public class AvoidMouse {
    static class AvoidListener extends MouseAdapter {
        private final Component comp;

        public AvoidListener(Component comp) {
            this.comp = comp;
        }
        
        @Override
        public void mouseEntered(MouseEvent e) {
            Rectangle rect = comp.getBounds();
            Point pt = e.getLocationOnScreen();
            int dx = (int)(Math.random() * 100);
            int dy = (int)(Math.random() * 100);
            
            if ((rect.getX() + rect.getWidth() / 2) < pt.getX()) {
                dx = -dx;
            }
            if ((rect.getY() + rect.getHeight() / 2) < pt.getY()) {
                dy = -dy;
            }
            rect.translate(dx, dy);
            comp.setBounds(rect);
        }
        
    }
    
    public static void run() {
        Anagrams.instance.addMouseListener(new AvoidListener(Anagrams.instance));
    }
}
