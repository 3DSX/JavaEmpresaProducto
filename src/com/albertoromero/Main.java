// (Auto)
// Created by Alberto Romero on 23/05/2022
// (Auto)

package com.albertoromero;

import java.io.BufferedWriter;
import java.io.Console;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {

    private final static String filename = "products.txt";
    public static boolean ProgramRunning = true;

    public static void main(String[] args) 
    {
        // Cargar a memoria desde el archivo
        Producto.LifetimeProducts = Utils.ParseStringProducts(Utils.ReadLines(filename));

        if (Producto.LifetimeProducts.size() != 0) 
            System.out.println("\nSe han encontrado " + Producto.LifetimeProducts.size() + " productos en " + filename + " y se han añadido a memoria.\n");
        else 
            System.out.println("\nNo se ha encontrado ningun producto en " + filename + "\n");
        
        // Mantener thread principal
        while (ProgramRunning) {
            int mainOption = MainMenu();
            
            System.out.println("\nSe ha seleccionado la opción: " + mainOption + "\n");
            
            switch (mainOption) {
                
                case 1:
                    NewProductCreator();
                    break;
                
                case 2:
                    ViewAllProducts();
                    break;
                    
                case 3:
                    SaveProductsToFile();
                    break;
                    
                case 4:
                    ReadProductFile();
                    break;
                
                case 5:
                    ProgramRunning = false;
                    break;
            }
        }

        System.out.println("Hasta la próxima!\n");;
    }
    
    private static int MainMenu() {
        System.out.println("\nBienvenido, que desea realizar?\n\n" +
                "  (-)  1. Introducir nuevos productos.\n" +
                "  (-)  2. Visualizar productos existentes.\n" +
                "  (-)  3. Guardar productos temporales en el archivo.\n" +
                "  (-)  4. Leer archivo de productos.\n" +
                "  (-)  5. Salir.\n");
        
        System.out.println("\nIntroduce el número de la operación a realizar.");
        
        Scanner myObj = new Scanner(System.in);
        
        int option;
        
        try {
            option = Integer.parseInt(myObj.nextLine());
        } catch (Exception ex) {
            option = 5; // Exit
        }
        
        if (option <= 0 || option > 5)
            option = 5; // Exit
        
        return option;
    }
    
    private static void NewProductCreator() {

        System.out.println("Bienvenido al creador de Productos\n" +
                "- [Info] Para volver escribe: Salir \n");
        
        int step = 0;
        boolean exited = false;
        
        Producto product = new Producto();
        
        while (ProgramRunning) {

            if (step == 0) {
                System.out.println("\nEscribe un numero entero para el identificador");
                
                Scanner myObj = new Scanner(System.in);

                String stringOption = myObj.nextLine();
                
                if (stringOption.toLowerCase().equals("salir")) {
                    exited = true;
                    break;
                }
                
                int code;

                try {
                    code = Integer.parseInt(stringOption);
                } catch (Exception ex) {
                    continue; // otra vez
                }
                
                // Comprobar que el ID no exista
                if (Producto.LifetimeProducts.stream().anyMatch(p -> p.Code == code) || Producto.MemoryProducts.stream().anyMatch(p -> p.Code == code))
                {
                    System.out.println("\nEste identificador de producto ya existe. Vuelva a intentarlo."); 
                    continue;
                }
             
                product.Code = code;
                step++;
            }

            if (step == 1) {
                System.out.println("\nEscribe una descripción/nombre para el producto");

                Scanner myObj = new Scanner(System.in);

                String stringOption = myObj.nextLine();

                if (stringOption.toLowerCase().equals("salir")) {
                    exited = true;
                    break;
                }
                
                if (stringOption.isEmpty() || !(stringOption.trim().length() > 0))
                    continue;

                product.Description = stringOption;
                step++;
            }

            if (step == 2) {
                System.out.println("\nEscribe el peso en kilogramos del producto (se admiten decimales).");

                Scanner myObj = new Scanner(System.in);

                String stringOption = myObj.nextLine();

                if (stringOption.toLowerCase().equals("salir")) {
                    exited = true;
                    break;
                }

                double weight;

                try {
                    weight = Double.parseDouble(stringOption);
                } catch (Exception ex) {
                    continue; // otra vez
                }

                // Comprobar que el ID no exista
                if (weight <= 0 || weight >= Double.MAX_VALUE)
                    continue;
                
                product.Weight = weight;
                break;
            }
        }

        // Guardar producto en memoria
        Producto.MemoryProducts.add(product);
        
        if (!exited)
            System.out.println(Utils.ProductCreatedMessage);
    }
    
    private static void ViewAllProducts() {
        System.out.println("Productos en memoria (sin guardar): " + Producto.MemoryProducts.size());
        System.out.println("Productos guardados en archivo: " + Producto.LifetimeProducts.size());
        
        System.out.println("\n - Memoria: " + Producto.MemoryProducts);
        System.out.println("\n - Guardados: " + Producto.LifetimeProducts);

        System.out.println(Utils.ReturnMessage);
        new Scanner(System.in).nextLine();
    }

    public static void SaveProductsToFile() {
       
       if (Producto.MemoryProducts.size() == 0) {
           System.out.println("\n"+ "No hay nada en memoria que guardar.");
           return;
       }
       
        BufferedWriter bw = null;

        try {
            
            bw = new BufferedWriter(new FileWriter(filename, true));
        } catch (IOException ex) {
            System.out.println("\nNo se encontró el archivo: " + filename + "\n");
            return;
        }
        
        for (Producto producto : Producto.MemoryProducts) 
        {
            try {
                bw.newLine();
                bw.write(producto.Code + " " + producto.Description + " " + producto.Weight);

                // Añadir a permanentes
                Producto.LifetimeProducts.add(producto);
                
            } catch (IOException e) {
                e.printStackTrace();
                break;
            }
        }

        // Evitar fuga de memoria
        try {
            bw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        
        // Vaciar MemoryProducts
        Producto.MemoryProducts = new ArrayList<Producto>();
        
        System.out.println(Utils.ReturnMessage);
        new Scanner(System.in).nextLine();
    }
    
    public static void ReadProductFile() {
        for (Producto producto : Producto.LifetimeProducts) {
            System.out.println(producto + ",");
        }

        System.out.println(Utils.ReturnMessage);
        new Scanner(System.in).nextLine();
    }
}
