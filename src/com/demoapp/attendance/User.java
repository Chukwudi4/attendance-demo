/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.demoapp.attendance;

/**
 *
 * @author Dell
 */
public class User {
    private String name;
    private String role;
    private boolean checked;
    private boolean enrolled;
    public User(String name, String role){
        this.name = name;
        this.role = role;
        this.checked = false;
        this.enrolled = false;
    }
    
    User(){
        this("Jane Doe", "Manager");
    }

    public String getName() {
        return name;
    }

    public String getRole() {
        return role;
    }
    
    public boolean isChecked(){
        return checked;
    }
    
    public void setChecked(){
        checked = !checked;
    }

    public boolean isEnrolled() {
        return enrolled;
    }

    public void setEnrolled(boolean enrolled) {
        this.enrolled = enrolled;
    }
    
    
}
