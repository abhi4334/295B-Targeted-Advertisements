package webservice.beans;

import java.io.IOException;
import java.util.ArrayList;

import org.json.simple.parser.ContentHandler;
import org.json.simple.parser.ParseException;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class ProductInfo implements java.io.Serializable {
	
	private static final long serialVersionUID = 1L;
	private String id;
	private String name;
	private String brand;
	private String size;
	private String category;
	private String manufacturer;
	private String avgPrice;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getBrand() {
		return brand;
	}
	public void setBrand(String brand) {
		this.brand = brand;
	}
	public String getSize() {
		return size;
	}
	public void setSize(String size) {
		this.size = size;
	}
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	public String getManufacturer() {
		return manufacturer;
	}
	public void setManufacturer(String manufacturer) {
		this.manufacturer = manufacturer;
	}
	public String getAvgPrice() {
		return avgPrice;
	}
	public void setAvgPrice(String avgPrice) {
		this.avgPrice = avgPrice;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	
	
}
