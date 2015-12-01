package devmobile.tvshow.db.object;

/**
 * Created by Elsio on 31.10.15.
 */
public class Actor {
    private int idActor;
    private String firstName;
    private String lastName;

    // Constructeur vide
    public Actor(){}


    // Constructeur avec paramètres firstName et lastName
    public Actor(String firstName, String lastName){
        this.firstName = firstName;
        this.lastName = lastName;
    }

    // Getters et setters d'Actor
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

    public int getIdActor() {
        return idActor;
    }

    public void setIdActor(int idActor) {
        this.idActor = idActor;
    }
}
