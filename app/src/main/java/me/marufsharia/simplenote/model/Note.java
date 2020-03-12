package me.marufsharia.simplenote.model;

public class Note {
    private int id;
    private String title;
    private String description;
    private int category;
    private int priority;
    private String created_at;
    private String updated_at;
    
    
    public Note() {
    }
    
    public Note(String title, String description) {
        this.title = title;
        this.description = description;
    }
    
    public Note(String title, String description, int category, int priority) {
        this.title = title;
        this.description = description;
        this.category = category;
        this.priority = priority;
    }
    
    public int getId() {
        return id;
    }
    
    public void setId(int id) {
        this.id = id;
    }
    
    public String getTitle() {
        return title;
    }
    
    public void setTitle(String title) {
        this.title = title;
    }
    
    public String getDescription() {
        return description;
    }
    
    public void setDescription(String description) {
        this.description = description;
    }
    
    public int getCategory() {
        return category;
    }
    
    public void setCategory(int category) {
        this.category = category;
    }
    
    public int getPriority() {
        return priority;
    }
    
    public void setPriority(int priority) {
        this.priority = priority;
    }
    
    
    public String getCreated_at() {
        return created_at;
    }
    
    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }
    
    
    public String getUpdated_at() {
        return updated_at;
    }
    
    public void setUpdated_at(String updated_at) {
        this.updated_at = updated_at;
    }
    
}
