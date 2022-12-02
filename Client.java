package sgbd;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.nio.ByteBuffer;
import java.util.Scanner;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import Elementsdetyraitements.Checkeur;

public class Client extends JFrame implements ActionListener{
	int id;
	JLabel jlab=new JLabel(new ImageIcon());
	JLabel anatiny=new JLabel("");
	JButton bouButton=new JButton();
	JTextField textfield=new JTextField();
	static Socket s=new Socket();
	public Client() {
		anatiny.setVisible(true);
		anatiny.setForeground(Color.WHITE);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		this.setLayout(null);
		this.setVisible(true);
		this.setSize(new Dimension(1280,720));
		JPanel panelambony=new JPanel();
		
		panelambony.add(jlab);	
		this.jlab.setBounds(0,0,300,200);
		this.jlab.setVisible(true);
		this.add(panelambony);
		
		panelambony.setBounds(0,0,400,200);
		JPanel panelambany=new JPanel();
		panelambany.setLayout(null);
		this.add(panelambany);
		panelambany.setBounds(0,200,800, 600);
		panelambany.setBackground(Color.BLACK);
		JLabel indicedeconnexion=new JLabel("Vous etes connecte au serveur de base de donnees");
		panelambany.add(indicedeconnexion); 
		indicedeconnexion.setBounds(25,20,800,600);
		indicedeconnexion.setVisible(true);
		indicedeconnexion.setFont(new Font("Ink Free",Font.BOLD,22));
		indicedeconnexion.setForeground(Color.GREEN);
		
		
		panelambany.add(textfield);
		//panelambany.add(anatiny);
		anatiny.setBounds(50,120,300,300);
		textfield.setBounds(50,60,700,80);
		textfield.setFont(new Font("Consolas", Font.BOLD, 16));
		textfield.setVisible(true);
		this.bouButton.setVisible(true);
		panelambany.add(bouButton);
		bouButton.setBounds(140,160,200,40);
		bouButton.addActionListener(this);
		
		this.bouButton.setText("Alefa ilay requete");
	}
	public static void main(String[] args) throws Exception {
		Client fen=new Client();
		
		
		
		final Scanner scan=new Scanner(System.in);
	try {
		s=new Socket("localhost", 1234);
		//Maka sary
		InputStream inputStream = s.getInputStream();
        byte[] sizeAr = new byte[4];
        inputStream.read(sizeAr);
        int size = ByteBuffer.wrap(sizeAr).asIntBuffer().get();

        byte[] imageAr = new byte[size];
        inputStream.read(imageAr);

        BufferedImage image = ImageIO.read(new ByteArrayInputStream(imageAr));

        ImageIO.write(image, "png", new File("D://sgbd/fond.png"));
       fen.jlab.setIcon(new ImageIcon("D://sgdb/fond.png"));
		
		BufferedReader in=new BufferedReader(new InputStreamReader(s.getInputStream()));
		PrintWriter out=new PrintWriter(s.getOutputStream());
		Thread envoi=new Thread(new Runnable() {
			String message;
			public void run() {
				while (true) {
					
					
					String valiny=scan.nextLine();
					
					out.println(valiny);
				
					out.flush();
				}
				
			}
		});
		envoi.start();
		Thread recevoir=new Thread(new Runnable() {
			String msg;
			public void run() {
				try {
					
					while (in!=null) {
						msg=in.readLine();
						System.out.println("Server  :"+msg);
						JOptionPane.showMessageDialog(fen.anatiny,msg);
						fen.anatiny.setText(msg);
						if (msg=="") {
							throw new Exception("Misy diso ny syntaxe anao");
						}
					}
					in.close();
					s.close();
					
				} catch (Exception e) {
					
					e.printStackTrace();
				}
			}
		});
		recevoir.start();
	} catch (Exception e) {
		// TODO: handle exception
	}
	
		
	}
	@Override
	public void actionPerformed(ActionEvent a) {
		if (a.getSource()==bouButton) {
		
			PrintWriter out;
			try {
				
				out = new PrintWriter(s.getOutputStream());
				String valiny=textfield.getText();
				
				out.println(valiny);
				
				out.flush();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			this.textfield.setText("");
			this.textfield.grabFocus();
		}
		
	}

	
}
