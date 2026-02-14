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
package Ex_1;

/**
 * Code for end-of-chapter exercises on asymptotics.
 *
 * @author Michael T. Goodrich
 * @author Roberto Tamassia
 * @author Michael H. Goldwasser
 */
class Exercises {

  /** Returns the sum of the integers in given array. */
  public static int example1(int[] arr) { 
    int n = arr.length, total = 0;								//this is 3 steps
    for (int j=0; j < n; j++)       // loop from 0 to n-1 		//this is 2n
      total += arr[j];											//this is n steps
    return total;												//this is 1 step
  } // we're looking at 3n + 4 steps which can be asymptotically bound by O(n) as there is c,n0 for 4n where n>4

  /** Returns the sum of the integers with even index in given array. */
  public static int example2(int[] arr) {
    int n = arr.length, total = 0;									//this is 3 steps
    for (int j=0; j < n; j += 2)    // note the increment of 2		//this is 1n (2 * 1/2 n)
      total += arr[j];												//this also runs only 1/2n steps
    return total;													//this is still 1 step
  } // we're looking at 1.5n + 4 steps which can be asymptotically bound by O(n) as there is c,n0 for 2n where n>8

  /** Returns the sum of the prefix sums of given array. */
  public static int example3(int[] arr) {
    int n = arr.length, total = 0;								//this is 3 steps
    for (int j=0; j < n; j++)       // loop from 0 to n-1		//this is 2n steps
      for (int k=0; k <= j; k++)    // loop from 0 to j			//this is 2n steps that runs n times so 2n^2
        total += arr[j];										//this will also run  as n^2 steps
    return total;												// 1 steps
  }// we're looking at 3n^2 + 2n + 4 steps which can be asymptotically bound by O(n^2) as there is some c,n0  for 4x^2 where n>~3.24

  /** Returns the sum of the prefix sums of given array. */
  public static int example4(int[] arr) {
    int n = arr.length, prefix = 0, total = 0;					//this is 4 steps
    for (int j=0; j < n; j++) {     // loop from 0 to n-1		//this is 2n steps
      prefix += arr[j];											//this runs as n steps
      total += prefix;											//this also runs as n steps
    }
    return total;		//this is always 1 step
  }// we're looking at 4n + 5 steps which can be asymptotically bound by O(n) as there is c,n0 for 5n where n>5

  /** Returns the number of times second array stores sum of prefix sums from first. */
  public static int example5(int[] first, int[] second) { // assume equal-length arrays
    int n = first.length, count = 0;								//this is 3 steps
    for (int i=0; i < n; i++) {     // loop from 0 to n-1			//this is 2n steps
      int total = 0;												//this is n step
      for (int j=0; j < n; j++)     // loop from 0 to n-1			//this is 2n steps occuring n times so 2n^2
        for (int k=0; k <= j; k++)  // loop from 0 to j				//we saw this example, it runs 2(n(n+1)/2) steps, n^2 times so n^4 + n^3 steps
          total += first[k];										//this runs n^2 * ((n^2 + n)/2) times, (n^4 + n^3)/2
      if (second[i] == total) count++;								//the check runs n steps, the count could run up to n steps
    }
    return count;													// 1 step
  }// we're looking at 1.5n^4 + 1.5n^3 + 2n^2 + 5n + 4 which can be asymptotically bound by O(n) as there is c,n0 for 5n where n>~4.48

}
