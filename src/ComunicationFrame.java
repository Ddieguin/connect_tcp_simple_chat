import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JButton;
import javax.swing.JTextField;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Font;

public class ComunicationFrame extends JFrame {

    private JPanel panelChat;

    private JTextArea chatArea;

    private JButton botaoEnviar;

    private JLabel titulo;

    private JPanel panelEnvio;
    
    private  JPanel panelTitulo;

    private JTextField chatField;

    private JScrollPane panelRolagem;

 
    public ComunicationFrame() {
        super("Comunicação entre servidores");
        criarMenu();
    }

    public void sendMenssage(String msg) {
        this.chatArea.append(msg);
    }

    private void criarMenu() {
        setLayout(new BorderLayout());
        panelTitulo = new JPanel();
        panelTitulo.setLayout(new FlowLayout());
        
        titulo = new JLabel("CHAT DOS CRIAS");
        titulo.setFont(new Font("Arial", Font.PLAIN, 20));
        panelTitulo.add(titulo);

        //Falta fazer o Painel de chat
        panelChat = new JPanel();;
        
        panelChat.setLayout(new FlowLayout());

        chatArea = new JTextArea(10, 50);

        panelRolagem = new JScrollPane(chatArea);
        panelRolagem.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        panelRolagem.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);

        //panelChat.add(chatArea);
        panelChat.add(panelRolagem);
        //Fim do painel do chat

        panelEnvio = new JPanel();
        panelEnvio.setLayout(new FlowLayout());
        
        chatField = new JTextField(40);
        botaoEnviar = new JButton("Enviar");
        
        panelEnvio.add(chatField);
        panelEnvio.add(botaoEnviar);

        //add(panelTitulo, BorderLayout.NORTH);
        add(panelChat, BorderLayout.CENTER);
        //Falta o central, que é onde vai mostrar o chat
        add(panelEnvio, BorderLayout.SOUTH);
        
    }

}