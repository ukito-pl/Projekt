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

    private final double g;
    private static double h1[];
    private static double h2[];
    //private static double u[];
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
           projekt.calkowanie(okno.A1, okno.A2, okno.S1, okno.S2, okno.H1, okno.H2,okno.t,okno.T, okno.RP, okno.AP, okno.TP, okno.RM);
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
    
    public double pobudzenie(int rodzaj, double amplituda, double okres, double t, double T, double i){
        double pobudzenie = 0;
        //pobudzenie = new double[(int) (t/T +1)];
        switch(rodzaj){
            case 0: pobudzenie = prostokat(amplituda,okres, t, T,i);
                    break;
            case 1: pobudzenie = trojkat(amplituda,okres, t, T);
                    break;
            case 2: pobudzenie = sinusoida(amplituda,okres, t, T);
                    break;
        }
        
        return pobudzenie;
    }
    
    public double prostokat(double amplituda, double okres, double t, double T, double i){
        double p ;
        
        
        int cykl = (int) (i/(okres/T) );
        if (i <= ((okres/(2*T)) + (cykl)*(okres/(T))) & i >= cykl*(okres/T) ){
           p = amplituda;
        }
        else p = 0;
        
        return p;
    }
    
    public double trojkat(double amplituda, double okres, double t, double T){
        double tr = 0;
        
        //Do wypełnienia
        return tr;
    }
    
    public double sinusoida(double amplituda, double okres, double t, double T){
        double s = 0;
        //s = new double[(int) (t/T +1)];
        //Do wypełnienia
        return s;
    }
    
    public void calkowanie(double A1, double A2, double S1, double S2, double H1, double H2, double t, double T, int rodzaj_pob, double amplituda_pob, double okres_pob, int rodzaj_metody){
        if (sprawdzenie(A1,A2,S1,S2,H1,H2,t,T)){
            int liczba_krokow = (int)(t/T);
            
            
            switch(rodzaj_metody){
                case 0:     metoda_Eulera(A1, A2, S1, S2, H1, H2, t, T, rodzaj_pob, amplituda_pob, okres_pob, liczba_krokow);
                            break;
                case 1:     metoda_Midpoint(A1, A2, S1, S2, H1, H2, t, T, rodzaj_pob, amplituda_pob, okres_pob, liczba_krokow);
                            break;
            }
            
        }
    }
    
    public void metoda_Eulera(double A1, double A2, double S1, double S2, double H1, double H2, double t, double T,int rodzaj_pob, double amplituda_pob, double okres_pob, int liczba_krokow){
        //kolejne wartości obliczane są ze wzoru: y(t+h)=y(t) + h*f(t,y(t))
        h1 = new double[liczba_krokow+1];
        h1[0]=0;
        time = new double[ liczba_krokow+1 ];
        time[0]=0; 
        for (int i=0; i < liczba_krokow; i++ ){
            double u = pobudzenie(rodzaj_pob,amplituda_pob,okres_pob,t,T, i+1);
            h1[i+1] = h1[i] + (T * (u/S1 - A1/S1*sqrt(2*g*h1[i])));
            time[i+1] = time[i]+T;
            if (h1[i+1] < 0) h1[i+1] = 0;
            if (h1[i+1] > H1 ) h1[i+1] = H1;
                
        }

        h2 = new double[liczba_krokow+1 ];
        h2[0]=0;
        for (int i=0; i < liczba_krokow; i++ ){
            h2[i+1] = h2[i] + (T * (A1/S1*sqrt(2*g*h1[i])/S1 - A2/S2*sqrt(2*g*h2[i])));
            time[i+1] = time[i]+T;
            if (h2[i+1] < 0) h2[i+1] = 0;
            if (h2[i+1] > H2 ) h2[i+1] = H2;
                
        }
    }
    
     public void metoda_Midpoint(double A1, double A2, double S1, double S2, double H1, double H2, double t, double T,int rodzaj_pob, double amplituda_pob, double okres_pob, int liczba_krokow){
        h1 = new double[liczba_krokow+1];
        h1[0]=0;
        time = new double[ liczba_krokow+1 ];
        time[0]=0; 
        for (int i=0; i < liczba_krokow; i++ ){
            double u1 = pobudzenie(rodzaj_pob,amplituda_pob,okres_pob,t,T, i);
            double k1 = T * (u1/S1 - A1/S1*sqrt(2*g*h1[i]));
            double u2 = pobudzenie(rodzaj_pob,amplituda_pob,okres_pob,t,T, i+0.5);
            h1[i+1] = h1[i] + (T * (u2/S1 - A1/S1*sqrt(2*g*(h1[i]+(1/2)*k1))));
            time[i+1] = time[i]+T;
            if (h1[i+1] < 0) h1[i+1] = 0;
            if (h1[i+1] > H1 ) h1[i+1] = H1;
                
        }
        //do dokończenia
     }
    
}
