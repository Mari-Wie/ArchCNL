package org.archcnl.stardogwrapper.api.exceptions;

public class NoConnectionToStardogServerException extends Exception {

    private static final long serialVersionUID = 3814730901226539698L;

    @Override
    public String getMessage() {

        return "No server was specified for which the database should be created. Please specify a Stardog server URL, "
                + "e.g. http://localhost:5820, by using the method addDatabase(String stardogServerURL, String userName, String password, String databaseName)"
                + "or by first establishing a server connection using createAdminConnection(String stardogServerURL) or createUserConnection(String stardogServerURL, String userName, String password)";
    }
}
