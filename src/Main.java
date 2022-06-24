import javax.swing.JFrame;


public class Main {
    
    public static void main(String[] args) {
        ComunicationFrame comunicationFrame = new ComunicationFrame();

        comunicationFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        comunicationFrame.setSize(530, 260);
        comunicationFrame.setVisible(true);
        
        comunicationFrame.sendMenssage("Diego Basilio Arruda");

    }

}
