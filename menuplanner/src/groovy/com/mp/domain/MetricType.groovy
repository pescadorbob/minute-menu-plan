package com.mp.domain
public enum MetricType {

    UNIT("Unit"),
    TIME("Time"), 
    METRIC("Metric")

    String name

   MetricType(String name){
        this.name = name
    }

    public static List<MetricType> list(){
        return [UNIT,TIME,METRIC]
    }

    String toString(){
        return name
    }
}