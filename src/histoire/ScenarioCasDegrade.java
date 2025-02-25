package histoire;

import personnages.*;
import villagegaulois.*;

public class ScenarioCasDegrade {
	public static void main(String[] args) throws VillageSansChefException { 
		Etal etal = new Etal(); 
		etal.libererEtal(); 
		Gaulois vendourix = new Gaulois("Vendourix", 25);
		
		Gaulois acheteur = null;
		Gaulois achetourix = new Gaulois("Achetourix", 25);
		
		
		System.out.println(etal.acheterProduit(0, acheteur));
		
		etal.occuperEtal(vendourix, "fleurs", 20);
		
		System.out.println(etal.acheterProduit(0, acheteur));
		System.out.println(etal.acheterProduit(0, achetourix));
		System.out.println(etal.acheterProduit(5, achetourix));
		
		System.out.println("Fin du test Acheteur/Vendeur \n");
		
		Village village = new Village("Petit Village", 5, 3);
		System.out.println(village.afficherVillageois());
		
		System.out.println("Fin du test Village");
	}
}