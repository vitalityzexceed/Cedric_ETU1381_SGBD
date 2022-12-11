package traitement;

import java.io.IOException;

public class Checkeur {
	static public String verifier( String a) throws Exception {
		if (a.contains("create database ")) {
			String nomdatabase="";
			for (int i = 16;  a.charAt(i)!=' '&& a.charAt(i)!=';'; i++) {
				
					nomdatabase=nomdatabase+a.charAt(i);
				
				
			}
			System.out.println("Nom database : " + nomdatabase);
			
			Database.createdatabase(nomdatabase);
			return "Database créée";
		}
		if (a.contains("create table ")) {
			String nomdatable="";
			int i=13;
			for (;  a.charAt(i)!=' '&& a.charAt(i)!='('; i++) {
				
				nomdatable=nomdatable+a.charAt(i);
			}
			System.out.println("Nom de la table créée :" + nomdatable);
			
			Database.createtable(nomdatable);
			int j=i+2;
			String insertrehetra="";
			for (;a.charAt(j)!=')' || a.charAt(j+1)!=';'; j++) {
				insertrehetra=insertrehetra+a.charAt(j);
			}
            System.out.println("Insertrehetra : " + insertrehetra);
			if (insertrehetra.contains(",")) {
				String[] insertvoazara=insertrehetra.split(",");
				for (int k = 0; k < insertvoazara.length; k++) {
					System.out.println(insertvoazara[k]);
				}
				Database.createinsert(insertvoazara);
				return "Voambotra ny tablenao ";
			}else {
				Database.createinsertstring(insertrehetra);
				return "Voamboatra ny tablenao";
			}
			
		}
		if (a.contains("use ")) {
			String nomdatable="";
			for (int i = 4;  a.charAt(i)!=' '&& a.charAt(i)!=';'; i++) {
				
				nomdatable=nomdatable+a.charAt(i);
			}
			System.out.println(nomdatable);
			
			Database.usedatabase(nomdatable);
			return "Tafiditra tsara ao am'ilay database "+nomdatable+" enao";
		}
		if (a.toLowerCase().contains("insert")) {
			System.out.println("okey");
			
			return Database.manaoinsert(a.toLowerCase());
		}
		if (a.toLowerCase().contains("select")) {
			return Database.manaoselect(a);
		}
		if (a.toLowerCase().contains("update")) {
			return Database.manaoupdate(a);
		}
		if (a.toLowerCase().contains("fafao")) {
			return Database.manaodelete(a);
		}
		return "Misy diso ny fanoratanao anazy";
		

	}
}

