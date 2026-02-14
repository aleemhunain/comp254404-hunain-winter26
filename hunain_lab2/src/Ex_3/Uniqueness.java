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
package Ex_3;

import java.util.Arrays;

/**
 * Demonstration of algorithms for testing element uniqueness.
 *
 * @author Michael T. Goodrich
 * @author Roberto Tamassia
 * @author Michael H. Goldwasser
 */
class Uniqueness {

  /** Returns true if there are no duplicate elements in the array. */
  public static boolean unique1(int[] data) {
    int n = data.length;
    for (int j=0; j < n-1; j++)
      for (int k=j+1; k < n; k++)
        if (data[j] == data[k])
          return false;                    // found duplicate pair
    return true;                           // if we reach this, elements are unique
  }

  /** Returns true if there are no duplicate elements in the array. */
  public static boolean unique2(int[] data) {
    int n = data.length;
    int[] temp = Arrays.copyOf(data, n);   // make copy of data
    Arrays.sort(temp);                     // and sort the copy
    for (int j=0; j < n-1; j++)
      if (temp[j] == temp[j+1])            // check neighboring entries
        return false;                      // found duplicate pair
    return true;                           // if we reach this, elements are unique
  }
  
  public static void main(String[] args) {
	  	/* Key considerations for this implementation of main is that n (array size) was changed from incrementing by 1 to being doubled.
	  	 * this is because every iteration of n did not have meaningful advances past 0-1 milisecond and it would taken an 
	  	 * exhaustive amount of time to get to the proper results. The second change was that 60 seconds as the elapsed time
	  	 * was changed to 0.6 seconds as the efficient or fast algorithm exhausted the allocated heap memory before even breaking
	  	 * 1 second and so aiming for 60 seconds would have required far more memory then is necessary to convey the rate of growth
	  	 * for each function.
	  	 * */
	    int n = 2;
	    int priorN = 2;
	    int lowBound = 0;
	    int highBound = 0;
	  	int[] uniqList = {0, -1};
	    
	    //removed choice of n, trials selection, not relevant for this question
	    long elapsed = 0;  // remember the original starting value
	    int repeats = 0; //used to track any repeats as high-low becomes 0
	    
	    // let's run version 2 (the quicker one) first, still the faster one even when copied over
	    System.out.println("Testing unique1...");
	    while (elapsed <= 600 && elapsed != -404) { //im setting the -404 as an informal exit condition to assign when we've hit the largest
	      long startTime = System.currentTimeMillis();
	      boolean result = unique1(uniqList);
	      long endTime = System.currentTimeMillis();
	      elapsed = endTime - startTime;
	      System.out.println(String.format("n: %9d took %12d milliseconds", n, elapsed));
	      
	      
	      if (elapsed <= 600  && highBound == 0) {
	    	  priorN = n;
	    	  n *= 2; //increase the problem size
	      } else if (elapsed > 600 && highBound == 0) {
	    	  if (highBound - lowBound <= 1) {repeats += 1;} else {repeats = 0;}
	    	  lowBound = priorN;
	    	  highBound = n;
	    	  n = lowBound + (highBound - lowBound)/2;
	    	  elapsed = 0;
	    	  if (n == lowBound){elapsed = -404;} //in the odd chance our immediate fix does catch exact lowerbound, just end the loop
	      } else if (elapsed > 600 && highBound != 0) {
	    	  if (highBound - lowBound <= 1) {repeats += 1;} else {repeats = 0;}
	    	  priorN = n;
	    	  highBound = n;
	    	  n = lowBound + (highBound - lowBound)/2; //if we exceed 600 but never set an upper bound, now is the time to do so 
	    	  elapsed = 0;								//we keep aiming for the middle value and resetting elapsed 
	    	  if (n == lowBound){elapsed = -404;}
	      } else if (elapsed <= 600 && highBound != 0) {
	    	  if (highBound - lowBound <= 1) {repeats += 1;} else {repeats = 0;}
	    	  priorN = n;
	    	  lowBound = n;
	    	  n = lowBound + (highBound - lowBound)/2;	//this is accomadating and under shoot, we move bounds the other way 
	    	  elapsed = 0;
	    	  if (n == lowBound){elapsed = -404;}
	      } 								// exhaustively we will eventually reach a discrete case that is smaller than 600 getting out largest n
	      
	      if (repeats >= 5) {elapsed = -404;} //if we start spiraling on one number, just accept its the largest N
	       
	      uniqList = new int[n];
	      for (int i = 0; i < n; i++) {
	    	  uniqList[i] = 0 - i;
	      }    
	    }
	    System.out.println(String.format("Largest n under 0.6 seconds is %9d", n));

	    System.out.println("Testing unique2...");
	    n = 2;                               // restore n to its start value as well as out priors and bounds
	    priorN = 2;
	    lowBound = 0;
	    highBound = 0;
	    uniqList = new int[]{0, -1};		//reset array back to default size and values
	    elapsed = 0;
	    repeats = 0;
	    
	    while (elapsed <= 600) {
	      long startTime = System.currentTimeMillis();
	      boolean result = unique2(uniqList);
	      long endTime = System.currentTimeMillis();
	      elapsed = endTime - startTime;
	      System.out.println(String.format("n: %9d took %12d milliseconds", n, elapsed));

	      
	      if (elapsed <= 600  && highBound == 0) {
	    	  priorN = n;
	    	  n *= 2; //increase the problem size
	      } else if (elapsed > 600 && highBound == 0) {
	    	  if (highBound - lowBound <= 1) {repeats += 1;} else {repeats = 0;}
	    	  lowBound = priorN;
	    	  highBound = n;
	    	  n = lowBound + (highBound - lowBound)/2;
	    	  elapsed = 0;
	    	  if (n == lowBound){elapsed = -404;} //in the odd chance our immediate fix does catch exact lowerbound, just end the loop
	      } else if (elapsed > 600 && highBound != 0) {
	    	  if (highBound - lowBound <= 1) {repeats += 1;} else {repeats = 0;}
	    	  priorN = n;
	    	  highBound = n;
	    	  n = lowBound + (highBound - lowBound)/2; //if we exceed 600 but never set an upper bound, now is the time to do so 
	    	  elapsed = 0;								//we keep aiming for the middle value and resetting elapsed 
	    	  if (n == lowBound){elapsed = -404;}
	      } else if (elapsed <= 600 && highBound != 0) {
	    	  if (highBound - lowBound <= 1) {repeats += 1;} else {repeats = 0;}
	    	  priorN = n;
	    	  lowBound = n;
	    	  n = lowBound + (highBound - lowBound)/2;	//this is accomadating and under shoot, we move bounds the other way 
	    	  elapsed = 0;
	    	  if (n == lowBound){elapsed = -404;}
	      } 								// exhaustively we will eventually reach a discrete case that is smaller than 600 getting out largest n
	      
	      if (repeats >= 5) {break;} //if we start spiraling on one number, just accept its the largest N
	      
	      uniqList = new int[n];
	      for (int i = 0; i < n; i++) {
	    	  uniqList[i] = 0 - i;
	      }
	    }
	    System.out.println(String.format("Largest n under 0.6 seconds is %9d", n));
	    
  }
  

}
