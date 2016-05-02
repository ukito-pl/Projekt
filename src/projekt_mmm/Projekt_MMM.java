/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projekt_mmm;
import static java.lang.Math.*;
import javax.swing.JFrame;

import java.awt.Color; 
import java.awt.BasicStroke; 
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.Timer;
import org.jfree.chart.ChartPanel; 
import org.jfree.chart.JFreeChart; 
import org.jfree.data.xy.XYDataset; 
import org.jfree.data.xy.XYSeries; 
import org.jfree.ui.ApplicationFrame; 
import org.jfree.ui.RefineryUtilities; 
import org.jfree.chart.plot.XYPlot;  
import org.jfree.chart.plot.PlotOrientation; 
import org.jfree.data.xy.XYSeriesCollection; 
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.chart.ChartFactory;
import org.jfree.ui.ApplicationFrame;
import org.jfree.ui.RefineryUtilities;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;


/**
 *
 * @author ukito
 */
public class Projekt_MMM extends Okno {
   public Projekt_MMM(){
       g = 10;
       
   }

    private double g;
    private static double h1[];
    private static double h2[];
    private static double u[];
    private static double time[];
    Timer timer;
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
        Projekt_MMM projekt = new Projekt_MMM();
        Okno okno = new Okno();
        okno.setVisible(true);
        RefineryUtilities.centerFrameOnScreen(okno );
        int delay = 100; //milliseconds
        ActionListener taskPerformer;
       taskPerformer = (ActionEvent evt) -> {
           projekt.calkowanie(okno.A1, okno.A2, okno.S1, okno.S2, okno.H1, okno.H2,okno.t,okno.T, okno.RP, okno.AP, okno.TP);
           okno.przeslij_dane(time , h1, time, h2);
        };
        new Timer(delay, taskPerformer).start();
        
//       
        } 
    
    public boolean sprawdzenie(double A1, double A2, double S1, double S2, double H1, double H2, double t, double T){
        if (A1 > 0 & A2 > 0 & S1 > 0 & S2 > 0 & H1 >0 & H2 > 0 & t>0 & T > 0){
            return true;
        }
        else return false;
    }
    
    public double[] pobudzenie(int rodzaj, double amplituda, double okres, double t, double T){
        double[] pobudzenie;
        pobudzenie = new double[(int) (t/T +1)];
        switch(rodzaj){
            case 0: pobudzenie = prostokat(amplituda,okres, t, T);
                    break;
            case 1: pobudzenie = trojkat(amplituda,okres, t, T);
                    break;
            case 2: pobudzenie = sinusoida(amplituda,okres, t, T);
                    break;
        }
        
        return pobudzenie;
    }
    
    public double[] prostokat(double amplituda, double okres, double t, double T){
        double[] p;
        p = new double[(int) (t/T +1)];
        for (int i = 0; i < ((int)(t/T+1)); i++){
            int cykl = (int) (i/(okres/T) );
            if (i <= ((okres/(2*T)) + (cykl)*(okres/(T))) & i >= cykl*(okres/T) ){
                p[i] = amplituda;
            }
            else p[i] = 0;
        }
        return p;
    }
    
    public double[] trojkat(double amplituda, double okres, double t, double T){
        double[] tr;
        tr = new double[(int) (t/T +1)];
        //Do wypełnienia
        return tr;
    }
    
    public double[] sinusoida(double amplituda, double okres, double t, double T){
        double[] s;
        s = new double[(int) (t/T +1)];
        //Do wypełnienia
        return s;
    }
    
    public void calkowanie(double A1, double A2, double S1, double S2, double H1, double H2, double t, double T, int rodzaj_pob, double amplituda_pob, double okres_pob){
        if (sprawdzenie(A1,A2,S1,S2,H1,H2,t,T)){
            int liczba_krokow = (int)(t/T);
            
            u = new double[liczba_krokow+1];
            u = pobudzenie(rodzaj_pob,amplituda_pob,okres_pob,t,T);
            
            h1 = new double[liczba_krokow+1];
            h1[0]=0;
            time = new double[ liczba_krokow+1 ];
            time[0]=0; 
            for (int i=1; i < liczba_krokow+1; i++ ){
                h1[i] = h1[i-1] + (T * (u[i]/S1 - A1/S1*sqrt(2*g*h1[i-1])));
                time[i] = time[i-1]+T;
                if (h1[i] < 0) h1[i] = 0;
                if (h1[i] > H1 ) h1[i] = H1;
                
            }

            h2 = new double[liczba_krokow+1 ];
            h2[0]=0;
            for (int i=1; i < liczba_krokow+1; i++ ){
                h2[i] = h2[i-1] + (T * (A1/S1*sqrt(2*g*h1[i-1])/S1 - A2/S2*sqrt(2*g*h2[i-1])));
                time[i] = time[i-1]+T;
                if (h2[i] < 0) h2[i] = 0;
                if (h2[i] > H2 ) h2[i] = H2;
                
            }
            
        }
    }
}
