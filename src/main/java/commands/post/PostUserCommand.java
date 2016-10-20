package commands.post;

import app.entities.UserEntity;
import database.DB;
import database.lib.SqlConnection;
import database.models.DbUsers;
import exceptions.CommandFailedException;
import request.IRequest;
import response.IResponse;
import utils.ResolveUrl;

import java.sql.SQLException;

public class PostUserCommand extends PostCommand {

	public PostUserCommand(String path) {
		super(path, "Create user. Necessary parameters: username, password, email, name. Also need auth.");

        necessaryFields.add("username");
        necessaryFields.add("password");
        necessaryFields.add("email");
        necessaryFields.add("fullname");
	}

	@Override
    public void run(IRequest request, IResponse response) throws CommandFailedException {

        SqlConnection sqlcon = null;
		try {
            sqlcon = DB.getConnection();

            if(!haveAuth(request, response, sqlcon)) return; //already ask for login or send errors

            String backUrl = ResolveUrl.ofRoute("get.users");

            if (!validateNecessaryFields(request)) {
                errorView("Some required fields are missing.", backUrl);
                return;
            }

            String username = request.getData("username");
            String password = request.getData("password");
            String email = request.getData("email");
            String fullname = request.getData("fullname").replace('+', ' ');

            UserEntity u = new UserEntity(username, password, email, fullname);

            int insertedId = DbUsers.insertUser(sqlcon, u);

            setUniversalView(u);
            response.redirect(ResolveUrl.of("get.userByUsername", u.getUserName()));
		} catch (SQLException e) {
            if(e.getErrorCode() == 2627) {
                errorView("That username/email already exists!", ResolveUrl.ofRoute("get.users"));
                return;
            }
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
