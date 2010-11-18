package com.mp.domain
public enum MetricType {

    UNIT("Unit"),
    TIME("Time"), 
    METRIC("Metric")

    String name

   MetricType(String name){
        this.name = name
    }

    String toString(){
        return name
    }
}