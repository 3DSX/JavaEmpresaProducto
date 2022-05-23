// (Auto)
// Created by Alberto Romero on 23/05/2022
// (Auto)

package com.albertoromero;

import java.io.*;
import java.lang.reflect.Array;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class Utils {
    
    public final static String ReturnMessage = "\n\nOperación ejecutada. Presiona enter para volver al menú principal.";
    public final static String ProductCreatedMessage = "\nOperación ejecutada. Producto guardado en memoria. Recuerda guardarlo permanentemente con la opción 4.";

    public static ArrayList<String> ReadLines(String filename) 
    {
        BufferedReader bw = null;

        try {
            Path path = FileSystems.getDefault().getPath("").toAbsolutePath();
            System.out.println("\nDirectorio actual: " + path.toString() + "\n");

            bw = new BufferedReader(new FileReader(filename));
        } catch (FileNotFoundException ex) {
            System.out.println("\nNo se encontró el archivo: " + filename + "\n");
            return new ArrayList<String>();
        }

        ArrayList<String> lines = new ArrayList<String>();
        String currentLine;

        while (true) {
            try {
                if ((currentLine = bw.readLine()) == null)
                    break;
            } catch (IOException e) {
                e.printStackTrace(); // este catch lo requiere el compilador
                break;
            }

            lines.add(currentLine);
        }

        // Evitar fuga de memoria
        try {
            bw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return lines;
    }

    public static ArrayList<Producto> ParseStringProducts(ArrayList<String> productsString) 
    {
        ArrayList<Producto> products = new ArrayList<Producto>();

        for (String str : productsString) {
            String[] words = str.split(" ");
            
            int id = Integer.parseInt(words[0]);
            words[0] = "";
            
            double weight = Double.parseDouble(words[words.length - 1]);
            words[words.length - 1] = "";
            
            Producto product = new Producto(id, String.join(" ", words), weight);
            products.add(product);
        }

        return products;
    }

}
