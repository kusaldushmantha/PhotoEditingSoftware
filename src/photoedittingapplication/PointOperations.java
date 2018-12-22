/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package photoedittingapplication;

import java.awt.Color;
import java.util.List;
import java.awt.image.BufferedImage;

import java.util.ArrayList;

import javax.swing.ImageIcon;

/**
 *
 * @author Kusal
 */
public class PointOperations {

    ImageInput imageInput;
    PhotoInterface photoInterface;

    BufferedImage tempImage = null;

    List<BufferedImage> imageStack;

    public void verticalFlip(BufferedImage image) {
        tempImage = new BufferedImage(image.getWidth(), image.getHeight(), image.getType());
        for (int i = 0; i < image.getWidth(); i++) {
            for (int j = 0; j < image.getHeight(); j++) {
                tempImage.setRGB(i, j, image.getRGB(i, j));
            }
        }
        for (int i = 0; i < image.getWidth(); i++) {
            for (int j = 0; j < image.getHeight() / 2; j++) {
                int tmp = image.getRGB(i, j);
                tempImage.setRGB(i, j, image.getRGB(i, image.getHeight() - j - 1));
                tempImage.setRGB(i, image.getHeight() - j - 1, tmp);
            }
        }
        imageStack.add(tempImage);
        photoInterface.lblimage.setIcon(new ImageIcon(tempImage));
    }

    public void Transpose(BufferedImage image) {
        tempImage = new BufferedImage(image.getWidth(), image.getHeight(), image.getType());
        for (int i = 0; i < image.getWidth(); i++) {
            for (int j = 0; j < image.getHeight(); j++) {
                tempImage.setRGB(i, j, image.getRGB(image.getHeight() - 1 - j, i));
            }
        }
        imageStack.add(tempImage);
        photoInterface.lblimage.setIcon(new ImageIcon(tempImage));
    }

    public void Mirror(BufferedImage image) {
        tempImage = new BufferedImage(image.getWidth(), image.getHeight(), image.getType());
        for (int i = 0; i < image.getWidth(); i++) {
            for (int j = 0; j < image.getHeight(); j++) {
                tempImage.setRGB(image.getWidth() - 1 - i, j, image.getRGB(i, j));
            }
        }
        imageStack.add(tempImage);
        photoInterface.lblimage.setIcon(new ImageIcon(tempImage));
    }

    public void resetImage() {
        imageStack.removeAll(imageStack);
        imageStack.add(imageInput.image);
        photoInterface.lblimage.setIcon(new ImageIcon(imageStack.get(0)));
    }

    public void negative(BufferedImage image) {
        tempImage = new BufferedImage(image.getWidth(), image.getHeight(), image.getType());
        for (int i = 0; i < image.getWidth(); i++) {
            for (int j = 0; j < image.getHeight(); j++) {
                tempImage.setRGB(i, j, 255 - image.getRGB(i, j));
            }
        }
        imageStack.add(tempImage);
        photoInterface.lblimage.setIcon(new ImageIcon(tempImage));
    }

    public void dithering(BufferedImage image) {
        tempImage = new BufferedImage(image.getWidth() * 2, image.getHeight() * 2, image.getType());
            
        for(int i=0;i<tempImage.getWidth();i+=2){
            for(int j=0;j<tempImage.getHeight();j+=2){
                doDither(tempImage,getGreyLevel(image.getRGB(i/2, j/2)),i,j);
            }
        }
    
        imageStack.add(tempImage);
        photoInterface.lblimage.setIcon(new ImageIcon(tempImage));
    }
    
    public int getGreyLevel(int rgbVal){
        Color color = new Color(rgbVal);
        return (color.getRed()+color.getBlue()+color.getGreen())/3;
    }
    
    public void doDither(BufferedImage tempImage,int greyLevel,int x,int y){
        if(0<=greyLevel && greyLevel<51){
            tempImage.setRGB(x, y, 0);
            tempImage.setRGB(x, y+1, 0);
            tempImage.setRGB(x+1, y, 0);
            tempImage.setRGB(x+1, y+1, 0);
        }
        else if(51<=greyLevel && greyLevel<102){
            tempImage.setRGB(x, y, -1);
            tempImage.setRGB(x, y+1, 0);
            tempImage.setRGB(x+1, y, 0);
            tempImage.setRGB(x+1, y+1, 0);
        }
        else if(102<=greyLevel && greyLevel<153){
            tempImage.setRGB(x, y, -1);
            tempImage.setRGB(x, y+1, -1);
            tempImage.setRGB(x+1, y, 0);
            tempImage.setRGB(x+1, y+1, 0);
        }
        else if(153<=greyLevel && greyLevel<204){
            tempImage.setRGB(x, y, -1);
            tempImage.setRGB(x, y+1, -1);
            tempImage.setRGB(x+1, y, -1);
            tempImage.setRGB(x+1, y+1, 0);
        }
        else if(204<=greyLevel && greyLevel<256){
            tempImage.setRGB(x, y, -1);
            tempImage.setRGB(x, y+1, -1);
            tempImage.setRGB(x+1, y, -1);
            tempImage.setRGB(x+1, y+1, -1);
        }
    }

}
