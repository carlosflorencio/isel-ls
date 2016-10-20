package exceptions;


import app.ImoProject;

import java.sql.SQLException;

public class CommandFailedException extends ImoProjectException {

    public CommandFailedException(SQLException ex) {
        super("Message: " + ex.getMessage() + " State: " + ex.getSQLState() + " Code: " + ex.getErrorCode());
        ImoProject.trace(ex.getMessage());
    }

    public CommandFailedException(String reason) {
        super(reason);
        ImoProject.trace(reason);
    }

    public CommandFailedException() {
    }

    public CommandFailedException(Exception e) {
        super(e.getMessage());
        ImoProject.trace(e.getMessage());
    }


}
