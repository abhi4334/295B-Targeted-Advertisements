package webservice.beans;

import java.io.Serializable;
/*import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement*/
public class InventoryInfo implements Serializable {
	
	private static final long serialVersionUID = 1L;
	private String id;
/*	public InventoryInfo(String id)
	{
		this.id = id;
	}*/

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
	
	

}
