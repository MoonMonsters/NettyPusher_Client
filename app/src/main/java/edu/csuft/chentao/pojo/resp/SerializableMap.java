package edu.csuft.chentao.pojo.resp;

import java.io.Serializable;
import java.util.Map;

/**
 * 对Map进行封装
 */
public class SerializableMap implements Serializable {

    private Map<Integer, Integer> map;

    public SerializableMap() {
    }

    public SerializableMap(Map<Integer, Integer> map) {
        this.map = map;
    }

    public Map<Integer, Integer> getMap() {
        return map;
    }

    public void setMap(Map<Integer, Integer> map) {
        this.map = map;
    }
}