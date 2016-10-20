package app.entities;

import app.entities.body.ResponsableEntity;
import utils.Pair;

import java.util.LinkedList;
import java.util.List;

public class UserEntity extends ResponsableEntity {
    private int id;
	private String userName;
	private String passW;
	private String email;
	private String name;
	
	public UserEntity(String userName, String passW, String email, String name){
		this.userName=userName;
		this.passW=passW;
		this.email=email;
		this.name=name;
	}

    public UserEntity(int id, String userName, String passW, String email, String name){
        this.id = id;
        this.userName=userName;
        this.passW=passW;
        this.email=email;
        this.name=name;
    }
	
	public String getUserName(){
		return this.userName;
	}
	
	public String getPassW(){
		return this.passW;
	}
	
	public String getEmail(){
		return this.email;
	}
	
	public String getName(){
		return this.name;
	}

    public int getId() {
        return this.id;
    }

    @Override
    public List<Pair<String, Object>> response() {
        LinkedList<Pair<String, Object>> list = new LinkedList<Pair<String, Object>>();

        list.add(new Pair<String, Object>("username", this.userName));
        list.add(new Pair<String, Object>("email", this.email));
        list.add(new Pair<String, Object>("name", this.name));

        return list;
    }
}
