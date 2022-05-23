// (Auto)
// Created by Alberto Romero on 23/05/2022
// (Auto)

package com.albertoromero;

import java.util.ArrayList;

public class Producto {

    // for all runtime
    public static ArrayList<Producto> MemoryProducts = new ArrayList<Producto>();
    public static ArrayList<Producto> LifetimeProducts;
    
    public int Code;
    public String Description;
    public double Weight;
    
    public Producto(int code, String description, double weight) {
        this.Code = code;
        this.Description = description;
        this.Weight = weight;
    }
    
    public Producto() {}

    @Override
    public String toString() {
        return "Producto {" +
                "Identificador = " + Code +
                ", Descripcion = '" + Description + '\'' +
                ", Peso = " + Weight + " kg" + 
                "}";
    }
}
