package utils;

import app.entities.body.IEntityResponsable;

import java.util.LinkedList;
import java.util.List;

public class EntityUtils {

    /**
     * Plain text of a responsable entity
     * @param item item to be converted
     * @return String Formated string
     */
    public static String plainTextEntity(IEntityResponsable item) {
        StringBuilder sb = new StringBuilder();

        if(item == null) return sb.toString();

        for (Pair<String, Object> itemPair : item.response()) {
            sb.append(itemPair.getFirst());
            sb.append( " = ");
            sb.append( itemPair.getSecond());
            sb.append("\n");
        }

        return sb.toString();
    }

    /**
     * Generate columns list from response list to create tables
     * @param list Entity list
     * @return List with columns
     */
    public static List<String> getColumns(List<? extends IEntityResponsable> list) {
        LinkedList<String> res = new LinkedList<String>();

        if(list == null || list.isEmpty() || list.get(0) == null) return  res;

        IEntityResponsable item = list.get(0); //we only need to see one

        for (Pair<String, Object> pair : item.response()) {
            res.add(pair.getFirst());
        }

        return res;
    }

    public static List<String> getColumns(IEntityResponsable in){
        LinkedList<String> res = new LinkedList<String>();

        if(in==null) return res;

        for(Pair<String, Object> pair : in.response()){
            res.add(pair.getFirst());
        }
        return res;
    }

    /**
     * Convert response list to data rows to create tables
     * @param list Entity list
     * @return List of lists with rows and data
     */
    public static List<List<Object>> getRows(List<? extends IEntityResponsable> list) {
        LinkedList<List<Object>> res = new LinkedList<List<Object>>();

        if(list == null || list.isEmpty() || list.get(0) == null) return res;

        for (IEntityResponsable itemList : list) {
            LinkedList<Object> dataList = new LinkedList<Object>();
            for (Pair<String, Object> item : itemList.response()) {
                dataList.add(item.getSecond());
            }
            res.add(dataList);
        }

        return res;
    }

    public static List<Object> getRows(IEntityResponsable in){
        LinkedList<Object> res = new LinkedList<Object>();

        if(in==null) return res;

        for(Pair<String,Object> pair : in.response()){
            res.add(pair.getSecond());
        }
        return res;
    }
}
