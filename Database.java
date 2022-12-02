package Elementsdetyraitements;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Vector;

public class Database {
	static String chemin="D://sgbd/";
	static String dernierchemin=""; 
	static String chemintxt="";
	static String nomdebaseactuel="";
	static public void createdatabase(String namedata) {
		File databases=new File("D://sgbd/"+namedata);
		databases.mkdir();
	}
	static public void usedatabase(String namedata){
		try {
			File databases=new File("D://sgbd/"+namedata);
			if (databases.exists()) {
				
				System.out.println("Database existant");
				nomdebaseactuel=namedata;
			}
			
			
			
			
		} catch (Exception e) {
			System.out.println("Database inexistant");
		}
	}
	static public void createtable(String nametable) throws IOException{
		File javadoc=new File(chemin+nomdebaseactuel+"\\"+nametable+"build.txt");
		System.out.println(chemin+nomdebaseactuel+"\\"+nametable+"build.txt");
		File datadoc=new File(chemin+nomdebaseactuel+"\\"+nametable+"donne.txt");
		dernierchemin=chemin+nomdebaseactuel+"\\"+nametable+"build.txt";
		chemintxt=chemin+nomdebaseactuel+"\\"+nametable+".txt";
		if (!javadoc.exists()
				) {
			javadoc.createNewFile();
			datadoc.createNewFile();
		}else {
			System.out.println("Table deja existant");
			return;
		}
		
		FileWriter fwrite=new FileWriter(dernierchemin,true);
		BufferedWriter bfwr=new BufferedWriter(fwrite);
		
		String restedeclasse="";
		for (int i = 1; i < nametable.length(); i++) {
			restedeclasse=restedeclasse+nametable.charAt(i);
		}
		String prems=""+nametable.charAt(0);
		bfwr.close();
		fwrite.close();
	}
	static public String[] createinsert(String[] tabinsert) throws IOException
	{	
		String[] tabinsertanatyjava=new String[tabinsert.length];
		for (int i = 0; i < tabinsert.length; i++) {
			
			
			if (tabinsert[i].contains("int")) {
				int indexfarany=tabinsert[i].indexOf("int");
				tabinsertanatyjava[i]="int ";
				for (int j = 0; j < indexfarany; j++) {
					if (tabinsert[i].indexOf("int")!=0) {
						tabinsertanatyjava[i]=tabinsertanatyjava[i]+tabinsert[i].charAt(j);
					}
				}
			}
			if (tabinsert[i].contains("varchar(")) {
				int indexfarany=tabinsert[i].indexOf("varchar(");
				tabinsertanatyjava[i]="String ";
				for (int j = 0; j < indexfarany; j++) {
					if (tabinsert[i].indexOf("varchar(")!=0) {
						tabinsertanatyjava[i]=tabinsertanatyjava[i]+tabinsert[i].charAt(j);
					}
				}
			}
			System.out.println(tabinsertanatyjava[i]);
			
		}
		Database.mampiditraattribut(tabinsertanatyjava, dernierchemin);
		return tabinsertanatyjava;
		
		
	}
	//creation table
	static public String createinsertstring ( String tabinsert)
	{	
		String tabinsertanatyjava=new String();
		for (int i = 0; i < tabinsert.length(); i++) {
			
			
			if (tabinsert.contains("int")) {
				int indexfarany=tabinsert.indexOf("int");
				tabinsertanatyjava="int ";
				for (int j = 0; j < indexfarany; j++) {
					if (tabinsert.indexOf("int")!=0) {
						tabinsertanatyjava=tabinsertanatyjava+tabinsert.charAt(j);
					}
				}
			}
			if (tabinsert.contains("varchar(")) {
				int indexfarany=tabinsert.indexOf("varchar(");
				tabinsertanatyjava="String ";
				for (int j = 0; j < indexfarany; j++) {
					if (tabinsert.indexOf("varchar(")!=0) {
						tabinsertanatyjava=tabinsertanatyjava+tabinsert.charAt(j);
					}
				}
			}
			
		}
		System.out.println(tabinsertanatyjava);
		return tabinsertanatyjava;
		 
	}
	static public void mampiditraattribut(String[] tabstring,String chemin2) throws IOException{
		FileWriter fwrite=new FileWriter(chemin2,true);
		BufferedWriter bfwr=new BufferedWriter(fwrite);
		for (int i = 0; i < tabstring.length; i++) {
			bfwr.write(tabstring[i]+";");
			bfwr.newLine();
			
		}
		bfwr.close();
		fwrite.close();
		
	}
	static public String manaoselect(String requete) throws Exception {
		try {
			requete=requete.replaceAll(" ","");
			int last=requete.lastIndexOf("from")+4;
			int deptvalues=requete.length()-1;
			if (requete.contains("where")) {
				deptvalues=requete.indexOf("where");
			}
			
			String nomdetable=requete.substring(last, deptvalues); 
			System.out.println("Nom de table : " + nomdetable);
			//String valString="<html>";
			String valString="";
			if (requete.toLowerCase().contains("select*from")) {
				BufferedReader reader=new BufferedReader(new InputStreamReader(new FileInputStream(chemin+nomdebaseactuel+"\\"+nomdetable+"donne.txt"),"UTF-8"));
				BufferedReader buiReader=new BufferedReader(new InputStreamReader(new FileInputStream(chemin+nomdebaseactuel+"\\"+nomdetable+"build.txt"),"UTF-8"));
				String lignebuild=buiReader.readLine();
				while (lignebuild!=null) {
					if (lignebuild.contains("String")) {
						valString=valString+lignebuild.substring(6,lignebuild.length()-1)+"\t";
					}else {
						valString=valString+lignebuild.substring(3,lignebuild.length()-1)+"\t";
					}
					lignebuild=buiReader.readLine();
				}
				// valString=valString+"<br>";
				//valString=valString+"\n";
				int taille=valString.length();
				for (int i = 0; i <taille ; i++) {
					//valString=valString+"-";
				}
				buiReader.close();
				String ligne=reader.readLine();
				while (ligne!=null) {
					// valString=valString+"<br>";
					valString=valString+"\n";
					String[] donne=ligne.split(";");
                    //mila asiana condition where
                    if (requete.toLowerCase().contains("where"))
                    {
                        int indicedebutcondition = requete.indexOf("where") + 6;
                        int indicefin = requete.indexOf(";");
                        String condition = requete.substring(indicedebutcondition, indicefin-1);
                        if (requete.toLowerCase().contains(" and ")) {
                            String[] clevaleursarespecter = condition.split("and");
                            //manala espace
							System.out.println("Cles valeurs a respecter : ");
                            for (int i = 0; i < clevaleursarespecter.length; i++) {
                                clevaleursarespecter[i] = clevaleursarespecter[i].replace(" ", "");
								System.out.println("" + clevaleursarespecter[i]);
                            }
                            String[] valeursarespecter = new String[clevaleursarespecter.length];
                            for (int i = 0; i < clevaleursarespecter.length; i++) {
                                valeursarespecter[i] = clevaleursarespecter[i].substring(clevaleursarespecter[i].indexOf("=")+1);
								System.out.println(valeursarespecter[i]);
                            }
							for (int i = 0; i < donne.length; i++) {
								for (int j = 0; j < valeursarespecter.length; j++) 
								{
									if (donne[i].contains(valeursarespecter[j])) 
									{
										if (j == valeursarespecter.length-1) 
										{
											if (donne[i].startsWith("'") && donne[i].endsWith("'")) {
												valString=valString+donne[i].substring(1,donne[i].length()-1)+"\t";
											}else {
												valString=valString+donne[i]+"\t";
											}
										}	
										else
										{
											continue;
										}
									}
									else
									{
										continue;
									}
								}								
							}

                        }
                        else
                        {
                            String clevaleurarespecter = condition.substring(indicedebutcondition);
							String valeurarespecter = clevaleurarespecter.substring(clevaleurarespecter.indexOf("=")+1);
							for (int i = 0; i < donne.length; i++) {
								if (donne[i].contains(valeurarespecter)) 
								{
									if (donne[i].startsWith("'") && donne[i].endsWith("'")) {
										valString=valString+donne[i].substring(1,donne[i].length()-1)+"\t";
									}else {
										valString=valString+donne[i]+"\t";
									}
								}
								else
								{
									continue;
								}
							}
                        }
                    }
					else
					{
						for (int i = 0; i < donne.length; i++) {
						
							if (donne[i].startsWith("'") && donne[i].endsWith("'")) {
								valString=valString+donne[i].substring(1,donne[i].length()-1)+"\t";
							}else {
								valString=valString+donne[i]+"\t";
							}
							
						}
					}
					//System.out.println(valString);
					
					ligne=reader.readLine();
					
				}
				reader.close();
			}
			// valString=valString+"</html>";
			System.out.println("Taille resultat : " + valString.length());
            System.out.println(valString);
			return valString;
			
		} catch (Exception e) {
			if (e instanceof FileNotFoundException) {
				return "Mety tsy miexiste ilay table nao na tsy ao anatin'ilay database tokony ho izy enao";
				
			}
			return "Misy olana eo aminy syntaxe anao";
			
		}
		

	}
	static public String manaodelete(String requete) throws Exception
	{
		BufferedReader reader = null;
		BufferedReader readerdonne = null;
		FileWriter write = null;
		BufferedWriter bfwr = null;
		String valeurretour = "";
		try {
			requete=requete.toLowerCase();
			requete=requete.replaceAll(" ","");
			int last=requete.indexOf("fafao");
			int lastvalues=requete.lastIndexOf("rehefa");
			int debutvalues=requete.indexOf(";");
			String nomdetable=requete.substring(last+5,lastvalues);
			String rehefa=requete.substring(lastvalues+6,debutvalues);
			String[]rehefatab=rehefa.split(",");
			String[][] rehefatab2d=new String[rehefatab.length][];
			for (int i = 0; i < rehefatab.length; i++) {
				rehefatab2d[i]=rehefatab[i].split("=");
			}
			reader=new BufferedReader(new InputStreamReader(new FileInputStream(chemin+nomdebaseactuel+"\\"+nomdetable+"build.txt"),"UTF-8"));
			readerdonne=new BufferedReader(new InputStreamReader(new FileInputStream(chemin+nomdebaseactuel+"\\"+nomdetable+"donne.txt"),"UTF-8"));
			String ligne=reader.readLine();
			String linedonne=readerdonne.readLine();
			int itewhere=0;
			Vector<Integer>	indtabdesmodifiers=new Vector<Integer>();
			Vector<Integer> indtabdesmodifiena=new Vector<Integer>();
				//it�rations an'ilay tab2d
				//int ittab2d=0;
				Vector<String> lignehosoratana=new Vector<String>();
				while (ligne!=null) {
					for (int i = 0; i < rehefatab2d.length; i++) {
						System.out.println("Tab"+rehefatab2d[i][0]);
						if (ligne.contains(rehefatab2d[i][0])) {
							indtabdesmodifiers.add(Integer.valueOf(i));
							
						}
						
					}
					ligne=reader.readLine();
					itewhere++;
				}
				
				if (indtabdesmodifiers.size()<rehefatab.length) {
					return "Tsy miexiste le ligne kasainao ataono condition";
				}
				ArrayList<Integer> indicenalignehomodifiena=new ArrayList<>();
				
				
				Vector<String[]> tabdonne2d=new Vector<String[]>();
				
				int compteur=0;
				while (linedonne!=null) {
					
					String[]tabdonne=linedonne.split(";");
					tabdonne2d.add(tabdonne);
					for (int i = 0; i < indtabdesmodifiers.size(); i++) {
						String ligneamizao=new String();
						
						if (tabdonne[indtabdesmodifiers.get(i)].contains(rehefatab2d[indtabdesmodifiers.get(i)][1])) {
						indicenalignehomodifiena.add(Integer.valueOf(compteur));
						}else {
							
						}
					}
					linedonne=readerdonne.readLine();
					compteur++;
				}
				
				File fileop=new File(chemin+nomdebaseactuel+"\\"+nomdetable+"donne.txt");
				fileop.createNewFile();
				
				write=new FileWriter(chemin+nomdebaseactuel+"\\"+nomdetable+"donne.txt");
				bfwr=new BufferedWriter(write);
				String finalst="<html>";
				if (indicenalignehomodifiena.size()==0) {
					write.close();
					bfwr.close();
					reader.close();
					readerdonne.close();
					return "Tsisy donnees ho deletena na tsy mifanaraka amin'ilay conditions";
				}
				for (int i = 0; i < tabdonne2d.size(); i++) {
					int occurence=0;
					for (int j = 0; j < indicenalignehomodifiena.size(); j++) {
						if (i==indicenalignehomodifiena.get(j)) {
							occurence++;
						}
					}
					if (occurence==0) {
						for (int j = 0; j < tabdonne2d.get(i).length; j++) {
							finalst=finalst+tabdonne2d.get(i)[j]+";";
							bfwr.write(tabdonne2d.get(i)[j]+";");
						}
						bfwr.newLine();
						finalst=finalst+"<br>";
					}
					
				}
				System.out.println(finalst);
				bfwr.close();
				write.close();
		} catch (Exception e) {
			if (e instanceof FileNotFoundException) {
				return "Tsy misy ny tableanao na tsy ao amin'ilay base de donnees tokony hisy anao enao";
			}
			return "misy olana ny syntaxe anao";
			
		}
		finally {
			write.close();
			bfwr.close();
			reader.close();
			readerdonne.close();
			valeurretour = "Tontosa ny requete-nao";
		}
		return valeurretour;	
	}
	static public String manaoupdate(String requete) {
		try {
			requete=requete.toLowerCase();
			requete=requete.replaceAll(" ","");
			int last=requete.indexOf("update");
			int deptvalues=requete.indexOf("set");
			int lastvalues=requete.lastIndexOf("rehefa");
			int debutvalues=requete.indexOf(";");
			String nomdetable=requete.substring(last+6,deptvalues);
			String donneeshovaina=requete.substring(deptvalues+3,lastvalues);
			String rehefa=requete.substring(lastvalues+6,debutvalues);
			System.out.println(nomdetable);
			System.out.println(donneeshovaina);
			System.out.println(rehefa);
			String[] donneeshovainatab=donneeshovaina.split(",");
			String[]rehefatab=rehefa.split(",");
			String[][] rehefatab2d=new String[rehefatab.length][];
			String[][] settab2d=new String[donneeshovainatab.length][];
			for (int i = 0; i < rehefatab.length; i++) {
				rehefatab2d[i]=rehefatab[i].split("=");
			}
			for (int i = 0; i < donneeshovainatab.length; i++) {
				settab2d[i]=donneeshovainatab[i].split("=");
			}
			BufferedReader reader=new BufferedReader(new InputStreamReader(new FileInputStream(chemin+nomdebaseactuel+"\\"+nomdetable+"build.txt"),"UTF-8"));
			BufferedReader readerdonne=new BufferedReader(new InputStreamReader(new FileInputStream(chemin+nomdebaseactuel+"\\"+nomdetable+"donne.txt"),"UTF-8"));
			
			
			String ligne=reader.readLine();
			String linedonne=readerdonne.readLine();
			int itewhere=0;
		Vector<Integer>	indtabdesmodifiers=new Vector<Integer>();
		Vector<Integer> indtabdesmodifiena=new Vector<Integer>();
			//iterations an'ilay tab2d
			//int ittab2d=0;
			Vector<String> lignehosoratana=new Vector<String>();
			while (ligne!=null) {
				for (int i = 0; i < rehefatab2d.length; i++) {
					System.out.println("Tab"+rehefatab2d[i][0]);
					if (ligne.contains(rehefatab2d[i][0])) {
						indtabdesmodifiers.add(Integer.valueOf(itewhere));
						
					}
					
				}
				for (int i = 0; i < settab2d.length; i++) {
					
					if (ligne.contains(settab2d[i][0])) {
						System.out.println("Tab"+settab2d[i][0]);
						indtabdesmodifiena.add(Integer.valueOf(itewhere));
					}
				}
				ligne=reader.readLine();
				itewhere++;
			}
			if (indtabdesmodifiena.size()<donneeshovainatab.length) {
				return "Tsy miexiste le ligne kasainao modifiena";
			}
			if (indtabdesmodifiers.size()<rehefatab.length) {
				return "Tsy miexiste le ligne kasainao ataono condition";
			}
			ArrayList<Integer> indicenalignehomodifiena=new ArrayList<>();
			
			
			Vector<String[]> tabdonne2d=new Vector<String[]>();
			
			int compteur=0;
			while (linedonne!=null) {
				
				String[]tabdonne=linedonne.split(";");
				tabdonne2d.add(tabdonne);
				for (int i = 0; i < indtabdesmodifiers.size(); i++) {
					String ligneamizao=new String();
					
					if (tabdonne[indtabdesmodifiers.get(i)].contains(rehefatab2d[indtabdesmodifiers.get(i)][1])) {
					indicenalignehomodifiena.add(Integer.valueOf(compteur));
					}else {
						
					}
				}
				linedonne=readerdonne.readLine();
				compteur++;
			}
			for (int i = 0; i < indicenalignehomodifiena.size(); i++) {
				for (int j = 0; j < indtabdesmodifiena.size(); j++) {
					tabdonne2d.get(indicenalignehomodifiena.get(i))[indtabdesmodifiena.get(j)]=settab2d[indtabdesmodifiena.get(j)][1];
					System.out.println();
				}
				
			}
			File fileop=new File(chemin+nomdebaseactuel+"\\"+nomdetable+"donne.txt");
			fileop.createNewFile();
			
			FileWriter write=new FileWriter(chemin+nomdebaseactuel+"\\"+nomdetable+"donne.txt");
			BufferedWriter bfwr=new BufferedWriter(write);
			String finalst=new String();
			for (int i = 0; i < tabdonne2d.size(); i++) {
				for (int j = 0; j < tabdonne2d.get(i).length; j++) {
					finalst=finalst+tabdonne2d.get(i)[j]+";";
					bfwr.write(tabdonne2d.get(i)[j]+";");
				}
				bfwr.newLine();
				finalst=finalst+"<br>";
			}
			System.out.println(finalst);
			bfwr.close();
			write.close();
			
			for (int i = 0; i < indtabdesmodifiers.size(); i++) {
				System.out.println(rehefatab[indtabdesmodifiers.get(i)]);
			}
			
		} catch (Exception e) {
			System.out.println(e.getMessage());
			System.out.println(e.getCause());
			if (e instanceof FileNotFoundException) {
				return "table inexistant ou ou vous n'ete pas dans une base de donnee";
				
			}
			return e.getMessage();
		}
		return "Nety";
		
		
	}
	@SuppressWarnings("finally")
	static public String manaoinsert(String requete) throws IOException{
		try {
			String trime=requete.replaceAll(" ","");
			int last=trime.lastIndexOf("insertinto");
			int deptvalues=trime.indexOf("values");
			int lastvalues=trime.lastIndexOf("values(");
			int debutvalues=trime.indexOf(");");
			String nomdetable=trime.substring(last+10, deptvalues);
			String donneesinsert=trime.substring(lastvalues+7,debutvalues);
			System.out.println(nomdetable);
			System.out.println(donneesinsert);
			
			String[] donnes=donneesinsert.split(",");
			
			BufferedReader reader=new BufferedReader(new InputStreamReader(new FileInputStream(chemin+nomdebaseactuel+"\\"+nomdetable+"build.txt"),"UTF-8"));
		
			FileWriter write=new FileWriter(chemin+nomdebaseactuel+"\\"+nomdetable+"donne.txt",true);
			
			BufferedWriter bfwr=new BufferedWriter(write);
			int lignezao=0;
			String ligne=reader.readLine();
			bfwr.newLine();
			while (lignezao<donnes.length) {
				
			
				if (ligne.contains("int") && !donnes[lignezao].startsWith("'") && !donnes[lignezao].endsWith("'")) {
					if (lignezao!=donnes.length) {
						bfwr.write(donnes[lignezao]+";");
					}else {
						bfwr.write(donnes[lignezao]+";");
					}
					
				}else if (ligne.toLowerCase().contains("string")&& donnes[lignezao].startsWith("'") && donnes[lignezao].endsWith("'")) {
					if (lignezao!=donnes.length) {
						bfwr.write(donnes[lignezao]+";");
					}else {
						bfwr.write(donnes[lignezao]+";");
					}
					
				}
				lignezao++;
				ligne=reader.readLine();
			}
			
			bfwr.close();
			
			write.close();
			reader.close();
			
			return "Tafiditra tsara ny donnees anao";
		} catch (Exception e) {
			return e.getMessage();
		} finally {
			return "Reussi";
		}
		
		
	}

}

