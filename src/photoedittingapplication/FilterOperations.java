/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package photoedittingapplication;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.swing.ImageIcon;

/**
 *
 * @author Kusal
 */
public class FilterOperations {
    
    public PhotoInterface photoInterface;
    public List<BufferedImage> imageStack;
    public BufferedImage tempImage;
    public ImageInput imageInput;
    
    int n = 3;
    int[][] filterMask = new int[n][n];
    
    void initFilterMask(){
        for(int i=0;i<3;i++){
            for(int j=0;j<3;j++){
                filterMask[i][j] = 1;
            }
        }
    }
    
    public ArrayList<Integer> getOrderedPoints(BufferedImage image,int x, int y, int rgbVal){
        ArrayList<Integer> neighbourPoints = new ArrayList();
        Color c;
        for(int i=x-(n-1)/2; i<=x+(n-1)/2; i++){
            for(int j=y-(n-1)/2; j<=y+(n-1)/2; j++){
                try{
                    c=new Color(image.getRGB(i, j));
                    if(rgbVal == 0){
                        neighbourPoints.add(c.getRed());
                    }
                    else if(rgbVal == 1){
                        neighbourPoints.add(c.getGreen());
                    }
                    else{
                        neighbourPoints.add(c.getBlue());
                    }
                }
                catch(IndexOutOfBoundsException e){}
            }
        }
        
        sortList(neighbourPoints);
        return neighbourPoints;
    }
    
    public void sortList(ArrayList<Integer> a){
        Collections.sort(a);
    }
    
    
    public void meanFilter(BufferedImage image, int p){
        initFilterMask();
        
        tempImage = new BufferedImage(image.getWidth(), image.getHeight(), image.getType());
        Color colour = null;
        int red=0,green=0,blue=0;
        
        for(int i=0; i<tempImage.getWidth(); i++){
            for(int j=0; j<tempImage.getHeight(); j++){
                red = calAverage(getOrderedPoints(image, i, j, 0), p);
                green = calAverage(getOrderedPoints(image, i, j, 1), p);
                blue = calAverage(getOrderedPoints(image, i, j, 2), p);
                
                colour = new Color(red,green,blue,255);
                
                tempImage.setRGB(i, j, new Color(red,green,blue,colour.getAlpha()).getRGB());
            }
        }
        
        photoInterface.lblimage.setIcon(new ImageIcon(tempImage));
        imageStack.add(tempImage);
        
    }
    
    public int calAverage(ArrayList<Integer> a, int p){
        
        if(p<0){
            p=(n*n-1)/2;
        }
        int sum=0;
        for(int i=p; i<n*n-p; i++){
            try{
                sum+=a.get(i);
            }
            catch(IndexOutOfBoundsException e){
                return Math.round(sum/i);
            }
        }
        
        return Math.round(sum/(n*n-2*p));
    }
    
    public void resetImage(){
        imageStack.removeAll(imageStack);
        imageStack.add(imageInput.image);
        photoInterface.lblimage.setIcon(new ImageIcon(imageStack.get(0)));

    }
    
}
