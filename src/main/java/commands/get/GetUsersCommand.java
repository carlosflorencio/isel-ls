package commands.get;

import app.entities.UserEntity;
import database.DB;
import database.lib.SqlConnection;
import database.models.DbUsers;
import exceptions.CommandFailedException;
import exceptions.NoSuchDataException;
import request.IRequest;
import response.IResponse;
import utils.MimeType;
import view.universals.entities.UniversalEntitiesJsonView;
import view.universals.entities.UniversalEntitiesTextView;
import view.users.UsersView;

import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

public class GetUsersCommand extends GetCommand {
	
	public GetUsersCommand(String path) {
		super(path, "List all users");
	}

	@Override
    public void run(IRequest request, IResponse response) throws CommandFailedException, NoSuchDataException {
		List<UserEntity> users = new LinkedList<UserEntity>();

        SqlConnection sqlcon = null;
        try {
            sqlcon = DB.getConnection();
            users = DbUsers.getUsers(sqlcon);
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

        if(users == null) throw new NoSuchDataException();
        
        this.addView(new MimeType ("text/html"), new UsersView(users));
        this.addView(new MimeType ("text/plain"), new UniversalEntitiesTextView(users));
        this.addView(new MimeType ("application/json"), new UniversalEntitiesJsonView("users", users));
	}

}
