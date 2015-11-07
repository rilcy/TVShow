package devmobile.tvshow.db.object;

/**
 * Created by Elsio on 31.10.15.
 */
public class Actor {
    private String firstName;
    private String lastName;

    // CONSTRUCTEUR A SUPPRIMER PAR LA SUITE
    public Actor(String firstName, String lastName){
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lasstName) {
        this.lastName = lasstName;
    }
}
