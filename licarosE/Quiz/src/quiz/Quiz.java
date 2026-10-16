/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package quiz;

import java.util.Arrays;
import java.util.Scanner;

public class Quiz {

    public static void main(String[] args) {
        Scanner scaner = new Scanner(System.in);
        int[] grades = new int[5];

        for (int i = 0; i < grades.length; i++) {
            System.out.println("Input grade no " + (i + 1) + ": ");
            grades[i] = scaner.nextInt();
        }

        System.out.println(Arrays.toString(grades));

        double ave = calculateAverage(grades);
        System.out.println("Average value is: " + ave);

        if (ave >= 75) {
            System.out.println("PASSED");
        } else {
            System.out.println("FAILED");
        }

        scaner.close();
    }

    public static double calculateAverage(int[] grades) {
        double total = 0;

        for (int i = 0; i < grades.length; i++) {
            total += grades[i];
        }

        return total / grades.length;
    }
}