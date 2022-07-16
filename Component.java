abstract public class Component {
    private String nom;

    public Component(String nom){
        this.nom = nom;
    }

    public String getNom(){
        return this.nom;
    }

    abstract public void afficher();

    public void ajouter(Component c){}
    
}
