package app.entities.body;

import utils.Pair;

import java.util.List;

public interface IEntityResponsable {

    /**
     * Convert Entity properties to a list of pairs to build a response
     * @return Pair list
     */
	public List<Pair<String, Object>> response();
	
}
