package madbomberlabs.com.connectdbprototype;

public class Api
{
    private static final String ROOT_URL = "http://niagaraDB.madbomberlabs.com/v1/Api.php?apicall=";

    public static final String URL_CREATE_ORG = ROOT_URL + "createOrg";
    public static final String URL_READ_ORG = ROOT_URL + "getAllOrgs";
    public static final String URL_UPDATE_ORG = ROOT_URL + "updateOrg";
    public static final String URL_DELETE_ORG = ROOT_URL + "deleteOrg&id=";
}
