package commands.post;

import app.entities.PropertyEntity;
import app.entities.UserEntity;
import database.DB;
import database.lib.SqlConnection;
import database.models.DbProperties;
import exceptions.CommandFailedException;
import request.IRequest;
import response.IResponse;
import utils.ResolveUrl;

import java.sql.SQLException;

public class PostPropertyCommand extends PostCommand {


    public PostPropertyCommand(String path) {
        super(path, "Create a property. Necessary parameters: type, description, price, location. Also need auth.");

        necessaryFields.add("type");
        necessaryFields.add("description");
        necessaryFields.add("price");
        necessaryFields.add("location");
    }

    @Override
    public void run(IRequest request, IResponse response) throws CommandFailedException {

        SqlConnection sqlcon = null;
        try {
            sqlcon = DB.getConnection();

            if(!haveAuth(request, response, sqlcon)) return;

            String backUrl = ResolveUrl.ofRoute("post.property");

            if (!validateNecessaryFields(request)) {
                errorView("Required fields not present.", backUrl);
                return;
            }

            String type = request.getData("type").replace("+", " ");
            String description = request.getData("description").replace("+", " ");
            Double price;
            try {
                 price = Double.parseDouble(request.getData("price"));
            } catch (NumberFormatException e) {
                errorView("Price must have digits and points", backUrl);
                return;
            }

            String local = request.getData("location").replace("+", " ");
            UserEntity user = getAuthUser(request, response, sqlcon);

            PropertyEntity prop = new PropertyEntity(type, description, price, local, user.getUserName());
            int id = DbProperties.insertProperty(sqlcon, prop);
            prop.setId(id);

            setUniversalView(prop);
            response.redirect(ResolveUrl.of("get.propertiesById", id));
        } catch (Exception e) {
            throw new CommandFailedException(e);
        } finally {
            if (sqlcon != null)
                try {
                    sqlcon.close();
                } catch (SQLException e) {
                    throw new CommandFailedException(e);
                }
        }
    }

}
