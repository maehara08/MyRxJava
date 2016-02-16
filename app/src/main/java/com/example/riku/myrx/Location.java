
package com.example.riku.myrx;

import java.util.HashMap;
import java.util.Map;
import javax.annotation.Generated;

@Generated("org.jsonschema2pojo")
public class Location {

    private String city;
    private String area;
    private String prefecture;
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    /**
     * 
     * @return
     *     The city
     */
    public String getCity() {
        return city;
    }

    /**
     * 
     * @param city
     *     The city
     */
    public void setCity(String city) {
        this.city = city;
    }

    /**
     * 
     * @return
     *     The area
     */
    public String getArea() {
        return area;
    }

    /**
     * 
     * @param area
     *     The area
     */
    public void setArea(String area) {
        this.area = area;
    }

    /**
     * 
     * @return
     *     The prefecture
     */
    public String getPrefecture() {
        return prefecture;
    }

    /**
     * 
     * @param prefecture
     *     The prefecture
     */
    public void setPrefecture(String prefecture) {
        this.prefecture = prefecture;
    }

    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

}
