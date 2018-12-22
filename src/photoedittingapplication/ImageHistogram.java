/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package photoedittingapplication;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.util.List;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

/**
 *
 * @author Kusal
 */
public class ImageHistogram {

    BufferedImage tempImage;
    List<BufferedImage> imageStack;
    PhotoInterface photoInterface;
    ImageInput imageInput;
    float[] histogram;
    
    
    
    public void makeHistogram(BufferedImage image){
        
         histogram = new float[256];
        
        for(int i=0;i<image.getWidth();i++){
            for(int j=0;j<image.getHeight();j++){
                int greyVal = getGreyValue(image.getRGB(i, j));
                histogram[greyVal] = histogram[greyVal]+1;
            }
        }
        
    }
    
    public JFreeChart plotHistogram(BufferedImage image){
    
        makeHistogram(image);
        
        XYSeries series = new XYSeries("Histogram Data");
        
        for(int i=0;i<histogram.length;i++){
            series.add(i, histogram[i]);
        }
        
        XYSeriesCollection seriesCollection = new XYSeriesCollection(series);
        JFreeChart chart = ChartFactory.createXYLineChart(
                "Image Histogram",
                "Intensity",
                "PDF",
                seriesCollection,
                PlotOrientation.VERTICAL,
                true,true,false);
        
        return chart;
    }
    
    public int getGreyValue(int val){
        Color color = new Color(val);
        return (color.getRed()+color.getBlue()+color.getGreen())/3;
    }
    
}
