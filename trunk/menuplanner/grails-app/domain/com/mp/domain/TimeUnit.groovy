package com.mp.domain

class TimeUnit extends Unit{
     static constraints = {
    }
    static mapping = {
        tablePerHierarchy false
    }
    String toString(){
        return this.name;
    }
}
