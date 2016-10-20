package app.entities;

import app.entities.body.ResponsableEntity;
import utils.Pair;

import java.util.LinkedList;
import java.util.List;

public class PropertyEntity extends ResponsableEntity {
	private int id;
	private String imoType;
	private String imoDesc;
	private double price;
	private String localization;
	private String ownerName;
	
	public PropertyEntity(int id, String type, String desc, double price, String local, String owner){
		this.id=id;
		imoType=type;
		imoDesc=desc;
		this.price=price;
		localization=local;
		ownerName=owner;
	}
	
	public PropertyEntity(String type, String desc, double price, String local, String owner){
		imoType=type;
		imoDesc=desc;
		this.price=price;
		localization=local;
		ownerName=owner;
	}
	
	public int getId(){
		return this.id;
	}
	
	public String getType(){
		return imoType;
	}
	
	public String getDesc(){
		return this.imoDesc;
	}
	
	public double getPrice(){
		return this.price;
	}
	
	public String getLocalization(){
		return this.localization;
	}
	
	public String getOwnerName(){
		return this.ownerName;
	}
	
	public void setId(int id){
		this.id=id;
	}


    @Override
    public List<Pair<String, Object>> response() {
        LinkedList<Pair<String, Object>> list = new LinkedList<Pair<String, Object>>();

        list.add(new Pair<String, Object>("id", this.id));
        list.add(new Pair<String, Object>("type", this.imoType));
        list.add(new Pair<String, Object>("description", this.imoDesc));
        list.add(new Pair<String, Object>("price", this.price));
        list.add(new Pair<String, Object>("localization", this.localization));
        list.add(new Pair<String, Object>("owner", this.ownerName));

        return list;
    }
}
