package view.utils;

import app.entities.PropertyEntity;
import utils.ResolveUrl;
import utils.libs.html.HtmlBlock;
import utils.libs.html.HtmlBootstrap;
import utils.writer.Writable;

import java.util.Iterator;
import java.util.List;

public class PropertiesUtilsView extends HtmlBootstrap {


    public static Writable getList(List<PropertyEntity> p) {
        Writable[] data = new Writable[p.size()];

        Iterator<PropertyEntity> itr = p.iterator();

        int n = 0;
        while (itr.hasNext()) {
            PropertyEntity prop = itr.next();
            Writable label = spanLabel(prop.getType(), "danger");
            Writable badge = badge(prop.getPrice() + "€");
            Writable link = a(ResolveUrl.of("get.propertiesById",prop.getId()), prop.getLocalization());
            data[n++] = new HtmlBlock(label, text(" "), link, badge);
        }

        return listGroup(data);
    }

}
