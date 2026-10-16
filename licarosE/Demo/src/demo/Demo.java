/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package demo;

import java.util.Arrays;
public class Demo {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
      int[] [] matrix ={
      {1, 2, 3},
      {4, 5, 6},
      {7, 8, 9}
      };
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[i].length; j++) {
                System.err.println(matrix [i][j] + " ");
                
            }
            System.err.println(" ");
 
            
        }
    }
    
}
