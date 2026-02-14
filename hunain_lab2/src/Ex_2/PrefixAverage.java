/*
 * Copyright 2014, Michael T. Goodrich, Roberto Tamassia, Michael H. Goldwasser
 *
 * Developed for use with the book:
 *
 *    Data Structures and Algorithms in Java, Sixth Edition
 *    Michael T. Goodrich, Roberto Tamassia, and Michael H. Goldwasser
 *    John Wiley & Sons, 2014
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package Ex_2;
import java.util.Arrays;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.stage.Stage;

/**
 * Demonstration of algorithms for computing the prefix averages of an array.
 *
 * @author Michael T. Goodrich
 * @author Roberto Tamassia
 * @author Michael H. Goldwasser
 */
public class PrefixAverage extends Application{		//public is required to run the class that extends Application

  /** Returns an array a such that, for all j, a[j] equals the average of x[0], ..., x[j]. */
  public static double[] prefixAverage1(double[] x) {
    int n = x.length;
    double[] a = new double[n];    // filled with zeros by default
    for (int j=0; j < n; j++) {
      double total = 0;            // begin computing x[0] + ... + x[j]
      for (int i=0; i <= j; i++)
        total += x[i];
      a[j] = total / (j+1);        // record the average
    }
    return a;
  }

  /** Returns an array a such that, for all j, a[j] equals the average of x[0], ..., x[j]. */
  public static double[] prefixAverage2(double[] x) {
    int n = x.length;
    double[] a = new double[n];    // filled with zeros by default
    double total = 0;              // compute prefix sum as x[0] + x[1] + ...
    for (int j=0; j < n; j++) {
      total += x[j];               // update prefix sum to include x[j]
      a[j] = total / (j+1);        // compute average based on current sum
    }
    return a;
  }
  
  private static int trials = 10;		//for start method implementation main variables need to be out of main for graph values and size.
  private static int[] xSeries = new int[trials];
  private static double[] pATwoSeries = new double[trials];
  private static double[] pAOneSeries = new double[trials];

  public static void main(String[] args) {
	    int n = 1000;                           // starting size
	    double[] numbers = new double[n];
	    Arrays.fill(numbers, 1.2);	 
	    
	    //removed choice of n, trials selection, not relevant for this question
	    int start = n;  // remember the original starting value
	    
	    // let's run version 2 (the quicker one) first, still the faster one even when copied over
	    System.out.println("Testing prefixAverage2...");
	    for (int t=0; t < trials; t++) {
	      long startTime = System.currentTimeMillis();
	      double[] avg = prefixAverage2(numbers);
	      long endTime = System.currentTimeMillis();
	      long elapsed = endTime - startTime;
	      System.out.println(String.format("n: %9d took %12d milliseconds", n, elapsed));
	      xSeries[t] = n;
	      pATwoSeries[t] = Math.log10(elapsed);
	      
	      n *= 2;
	      numbers = new double[n];
		  Arrays.fill(numbers, 1.2);// double the problem size
	    }

	    System.out.println("Testing prefixAverage1...");
	    n = start;                               // restore n to its start value
	    numbers = new double[n];		//reset array back to default size and values
		Arrays.fill(numbers, 1.2);
		
	    for (int t=0; t < trials; t++) {
	      long startTime = System.currentTimeMillis();
	      double[] avg = prefixAverage1(numbers);
	      long endTime = System.currentTimeMillis();
	      long elapsed = endTime - startTime;
	      System.out.println(String.format("n: %9d took %12d milliseconds", n, elapsed));
	      pAOneSeries[t] = Math.log10(elapsed);
	      
	      n *= 2;                                // double the problem size
	      numbers = new double[n];
		  Arrays.fill(numbers, 1.2);// double the problem size
	    }
	    
	    launch(args);	//required to start the start method to start setting up graph
	  }
  
  public void start(Stage stage) {		//start method must be included when using graph through JavaFX and Application extension
	  stage.setTitle("Prefix Averages Performance Comparison test");
	  
	  NumberAxis x = new NumberAxis();
	  x.setLabel("List size");
	  NumberAxis y = new NumberAxis(0,6,1); //force scaling, might need to make dynamic for future
	  y.setLabel("Log of Milliseconds");
	  
	  LineChart<Number, Number> lineChart = new LineChart<>(x,y);
	  lineChart.setTitle("Prefix Averages Performance Comparison test");
	  
	  XYChart.Series<Number, Number> prefixAverage2Series = new XYChart.Series<>(); //the series that runs faster
	  prefixAverage2Series.setName("prefixAverage2");
	  for (int i = 0; i < trials; i++) {
		  prefixAverage2Series.getData().add(
				  new XYChart.Data<>(xSeries[i], pATwoSeries[i])
			);
      }
	  
	  XYChart.Series<Number, Number> prefixAverage1Series = new XYChart.Series<>(); //the slower series
	  prefixAverage1Series.setName("prefixAverage1");
	  for (int i = 0; i < trials; i++) {
		  prefixAverage1Series.getData().add(
				  new XYChart.Data<>(xSeries[i], pAOneSeries[i])
			);
      }
	  
	  lineChart.getData().addAll(prefixAverage2Series, prefixAverage1Series);		//final set of code to set and launch the graph
	  lineChart.setCreateSymbols(false);
	  Scene scene = new Scene(lineChart, 900, 600);
	  stage.setScene(scene);
	  stage.show();
	  
	  
  }
  
}
