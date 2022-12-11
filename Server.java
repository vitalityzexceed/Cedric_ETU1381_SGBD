package modele;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.util.Scanner;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

import traitement.Checkeur;

public class Server {
	
	
	public static void main(String[] args) throws IOException {
		final BufferedReader in;
		final BufferedWriter out;
		final Socket s;
		final Scanner scan=new Scanner(System.in);
		final ServerSocket ss;
		try {
			ss=new ServerSocket(1234);
			s=ss.accept();
			System.out.println("Un utilisateur s'est connecte");
			in=new BufferedReader(new InputStreamReader(s.getInputStream()));
			out=new BufferedWriter(new OutputStreamWriter(s.getOutputStream()));
			Thread recevoir=new Thread(new Runnable() {
				String msg;
				
				public void run() {
					
					
					try {
						OutputStream outputStream = s.getOutputStream();
						BufferedImage image = ImageIO.read(new File("D://sgbd/fond.jpg"));
						ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
				        ImageIO.write(image, "jpg", byteArrayOutputStream);

				        byte[] size = ByteBuffer.allocate(4).putInt(byteArrayOutputStream.size()).array();
				        outputStream.write(size);
				        outputStream.write(byteArrayOutputStream.toByteArray());
				        outputStream.flush();
						
						
						
						while (in!=null) {
							msg=in.readLine();
							
							out.write(Checkeur.verifier(msg));
							
							out.newLine();
							out.flush();
							
						}
						in.close();
						s.close();
						ss.close();
						
					} catch (Exception e) {
						
						e.printStackTrace();
					}
				}
			});
			recevoir.start();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
	}

}
