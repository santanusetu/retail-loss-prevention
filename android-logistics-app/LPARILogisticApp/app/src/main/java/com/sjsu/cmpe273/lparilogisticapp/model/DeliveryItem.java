package com.sjsu.cmpe273.lparilogisticapp.model;


public class DeliveryItem {

    private int thumbnail;
    private String title;
    private String category;
    private String instructions;

    private boolean isScanned;

    public int getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(int thumbnail) {
        this.thumbnail = thumbnail;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getInstructions() {
        return instructions;
    }

    public void setInstructions(String instructions) {
        this.instructions = instructions;
    }


    public boolean getIsScanned() {
        return isScanned;
    }

    public void setIsScanned(boolean isScanned) {
        this.isScanned = isScanned;
    }
}