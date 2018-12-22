/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package photoedittingapplication;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.util.List;
import javax.swing.ImageIcon;

/**
 *
 * @author Kusal
 */
public class EdgeOperations {
    
    BufferedImage tempImage;
    List<BufferedImage> imageStack;
    PhotoInterface photoInterface;
    ImageInput imageInput;
    
    int thresholdVal = 5;
    
    public BufferedImage convoluteImage(BufferedImage image,int[][] mask){
        tempImage = new BufferedImage(image.getWidth(), image.getHeight(), image.getType());
        int maskSize = mask.length;
        int val = 0, greyVal = 0;
        Color color = null;
        
        for(int i=0;i<image.getWidth();i++){
            for(int j=0;j<image.getHeight();j++){
                for(int x=0;x<maskSize;x++){
                    for(int y=0;y<maskSize;y++){
                        try{
                            color = new Color(image.getRGB(i-maskSize/2+x, j-maskSize/2+y));
                        }catch(ArrayIndexOutOfBoundsException ex){
                            continue;
                        }
                        greyVal = (color.getRed()+color.getBlue()+color.getGreen())/3;
                        val+=greyVal*mask[x][y];
                    }
                }
                if(val>thresholdVal){
                    tempImage.setRGB(i, j, new Color(255,255,255).getRGB());
                }else{
                    tempImage.setRGB(i, j, new Color(0,0,0).getRGB());
                }
                val = 0;
            }
        }
        return tempImage;
    }
    
    public void prewittDetector(BufferedImage image, int threshold){
        int[][] mask = new int[][]{{-1,-1,-1},{0,0,0},{1,1,1}};
        imageStack.add(convoluteImage(image, mask));
        photoInterface.lblimage.setIcon(new ImageIcon(tempImage));
    }
    
    public void sobelDetector(BufferedImage image,int threshold){
        int[][] mask = new int[][]{{-1,-2,-1},{0,0,0},{1,2,1}};
        imageStack.add(convoluteImage(image, mask));
        photoInterface.lblimage.setIcon(new ImageIcon(tempImage));
    }
    
    public void laplacienInwardsDetector(BufferedImage image, int threshold){
        int[][] mask = new int[][]{{0,1,0},{1,-4,1},{0,1,0}};
        imageStack.add(convoluteImage(image, mask));
        photoInterface.lblimage.setIcon(new ImageIcon(tempImage));
    }
    
    public void laplacienOutwardsDetector(BufferedImage image, int threshold){
        int[][] mask = new int[][]{{0,1,0},{1,-4,1},{0,1,0}};
        imageStack.add(convoluteImage(image, mask));
        photoInterface.lblimage.setIcon(new ImageIcon(tempImage));
    }
    
    public void resetImage(){
        imageStack.removeAll(imageStack);
        imageStack.add(imageInput.image);
        photoInterface.lblimage.setIcon(new ImageIcon(imageStack.get(0)));

    }
    
    
}
