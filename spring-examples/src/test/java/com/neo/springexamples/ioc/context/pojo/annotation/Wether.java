package com.neo.springexamples.ioc.context.pojo.annotation;

import org.springframework.stereotype.Component;

@Component
public class Wether {

    private int    temparature;
    private String type;

    @Override
    public String toString() {
        return "Wether{" +
                "temparature=" + temparature +
                ", type='" + type + '\'' +
                '}';
    }

    public Wether() {
        this.type = "default";
    }

    public Wether(String type, int temparature) {
        this.temparature = temparature;
        this.type = type;
    }

    public int getTemparature() {
        return temparature;
    }

    public void setTemparature(int temparature) {
        this.temparature = temparature;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
