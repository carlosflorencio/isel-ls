package commands.get;

import app.entities.PropertyEntity;
import app.entities.UserEntity;
import database.DB;
import database.lib.SqlConnection;
import database.models.DbProperties;
import database.models.DbUsers;
import exceptions.CommandFailedException;
import exceptions.NoSuchDataException;
import request.IRequest;
import response.IResponse;
import utils.MimeType;
import view.universals.entity.UniversalEntityJsonView;
import view.universals.entity.UniversalEntityTextView;
import view.users.SingleUserView;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class GetUserByUsernameCommand extends GetCommand {

    public GetUserByUsernameCommand(String path) {
        super(path, "Get user by username.");
    }

    public void run(IRequest request, IResponse response) throws CommandFailedException, NoSuchDataException {
        Map<String, String> binds = request.binds(getRoute());
        String username = binds.get("username");
        UserEntity user = null;
        List<PropertyEntity> prop = new ArrayList<PropertyEntity>();

        SqlConnection sqlcon = null;
        try {
            sqlcon = DB.getConnection();
            user = DbUsers.getUserByUserName(sqlcon, username);
            prop = DbProperties.getPropertiesByOwner(sqlcon, username);
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

        if(user == null) throw new NoSuchDataException();

        this.addView(new MimeType ("text/html"), new SingleUserView(user, prop));
        this.addView(new MimeType ("text/plain"), new UniversalEntityTextView(user));
        this.addView(new MimeType ("application/json"), new UniversalEntityJsonView(user));
    }

}
