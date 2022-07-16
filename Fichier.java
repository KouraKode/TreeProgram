public class Fichier extends Component {
    public Fichier(String nom){
        super(nom);
    }

    public void afficher(){
        System.out.printf("%s\n", this.getNom());
    }
}
