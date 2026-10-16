/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package linkedlist;

public class linkedlist {
 
    static class Node {
        int number;
        Node next;
        Node(int number) {
            this.number = number;
        }
    }
 
    static Node head;
 
    static void insert(int value) {
        Node node = new Node(value);
        if (head == null) {
            head = node;
            return;
        }
        Node current = head;
        while (current.next != null) current = current.next;
        current.next = node;
    }
 
    static void insertAtBeginning(int value) {
        Node node = new Node(value);
        node.next = head;
        head = node;
    }
 
    static boolean search(int value) {
        Node current = head;
        while (current != null) {
            if (current.number == value) return true;
            current = current.next;
        }
        return false;
    }
 
    static void delete(int value) {
        if (head == null) return;
        if (head.number == value) {
            head = head.next;
            return;
        }
        Node current = head;
        while (current.next != null && current.next.number != value) {
            current = current.next;
        }
        if (current.next != null) current.next = current.next.next;
    }
 
    static void display() {
        Node current = head;
        while (current != null) {
            System.out.print(current.number + " -> ");
            current = current.next;
        }
        System.out.println("null");
    }
 
    public static void main(String[] args) {
        insert(101);
        insert(102);
        insert(103);
        insert(104);
        insert(105);
 
        System.out.println("Initial:");
        display();
 
        insertAtBeginning(100);
        System.out.println("Insert 100:");
        display();
 
        System.out.println("Search 104: " + search(104));
 
        delete(103);
        System.out.println("Delete 103:");
        display();
    }
}
}