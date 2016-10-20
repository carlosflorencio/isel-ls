package app.entities;

import app.entities.body.ResponsableEntity;
import org.joda.time.DateTime;
import utils.Pair;

import java.util.LinkedList;
import java.util.List;

public class RentalEntity extends ResponsableEntity {
	private int idProp;
	private String userName;
	private int year;
	private int week;
	private String state;
	private DateTime dateReq;
	private DateTime dateAccept;
	
	public RentalEntity(int id, String user, int y, int w, String s, DateTime req, DateTime accept){
		idProp=id;
		userName=user;
		year=y;
		week=w;
		state=s;
		dateReq = req;
        if(accept != null)
		    dateAccept = accept;
        else
            dateAccept = null;
	}
	
	public int getIdProp(){
		return this.idProp;
	}
	
	public String getUserName(){
		return this.userName;
	}
	
	public int getYear(){
		return this.year;
	}
	
	public int getWeek(){
		return this.week;
	}
	
	public String getState(){
		return this.state;
	}
	
	public DateTime getDataRequest(){
		return this.dateReq;
	}
	public DateTime getDataAccept(){
		return this.dateAccept;
	}

	@Override
	public List<Pair<String, Object>> response() {
		 LinkedList<Pair<String, Object>> list = new LinkedList<Pair<String, Object>>();

	        list.add(new Pair<String, Object>("idProp", this.idProp));
	        list.add(new Pair<String, Object>("userName", this.userName));
	        list.add(new Pair<String, Object>("year", this.year));
	        list.add(new Pair<String, Object>("week", this.week));
	        list.add(new Pair<String, Object>("state", this.state));
	        list.add(new Pair<String, Object>("dateRequest", this.dateReq));
	        list.add(new Pair<String, Object>("dateAccept", this.dateAccept));

	        return list;
	}

}
