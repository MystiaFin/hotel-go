package com.hotelgo.model;

public class SideNavLinks {
    private String url;
    private String text;
    private boolean active;
    
    public SideNavLinks(String url, String text) {
        this.url = url;
        this.text = text;
        this.active = false;
    }
    
    public SideNavLinks(String url, String text, boolean active) {
        this.url = url;
        this.text = text;
        this.active = active;
    }
    
    // Getters and setters
    public String getUrl() { return url; }
    public void setUrl(String url) { this.url = url; }
    
    public String getText() { return text; }
    public void setText(String text) { this.text = text; }
    
    public boolean isActive() { return active; }
    public void setActive(boolean active) { this.active = active; }
}
