import java.util.ArrayList;

public class Dossier extends Component {
    
    private ArrayList<Component> components = new ArrayList<Component>();
    private static int LEVEL = 0;

    public Dossier(String nom){
        super(nom);
    }

    public void ajouter(Component c){
        this.components.add(c);
    }

    public void afficher(){
        System.out.println("\u001B[34m" + this.getNom() + "\u001B[0m");
        for(Component component : components){
            for (int i = 0; i < Dossier.LEVEL; i++) {
                System.out.printf("│   ");
            }
            System.out.printf("├───");
            if(component instanceof Dossier){
                Dossier.LEVEL++;
                component.afficher();
                Dossier.LEVEL--;
            }else{
                component.afficher();
            }
        }
    }
}
