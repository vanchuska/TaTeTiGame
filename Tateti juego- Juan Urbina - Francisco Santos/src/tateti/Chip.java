
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tateti;

import java.awt.*;

/**
 *
 * @author Equipo
 */
public class Chip {
    private int x;
    private int y;
    private int img;
    
    public Chip(){
        this.x=0;
        this.y=0;
        this.img=0;
    }
    
    public Chip(int x,int y){
        this.x=x;
        this.y=y;
    }
    public Chip(int x,int y, int img){
        this.x=x;
        this.y=y;
        this.img=img;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getImg() {
        return img;
    }

    public void setImg(int img) {
        this.img = img;
    }
    
    
}
