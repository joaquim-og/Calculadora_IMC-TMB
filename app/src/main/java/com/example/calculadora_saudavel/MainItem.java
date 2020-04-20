package com.example.calculadora_saudavel;

public class MainItem {

    private final int id;
    private final int imgId;
    private final int textId;
    private final int colorValue;


    public MainItem(int id, int imgId, int textId, int colorValue) {
        this.id = id;
        this.imgId = imgId;
        this.textId = textId;
        this.colorValue = colorValue;
    }

    public int getColorValue() {
        return colorValue;
    }

    public int getImgId() {
        return imgId;
    }

    public int getTextId() {
        return textId;
    }

    public int getId() {
        return id;
    }
}
