package villagegaulois;

import personnages.Chef;
import personnages.Gaulois;

public class Village {
	private String nom;
	private Chef chef;
	private Gaulois[] villageois;
	private int nbVillageois = 0;
	private Marche marche;
	private int nbEtals = 0;

	public Village(String nom, int nbVillageoisMaximum, int nbEtals) {
		this.nom = nom;
		villageois = new Gaulois[nbVillageoisMaximum];
		marche = new Marche(nbEtals);
	}

	public String getNom() {
		return nom;
	}

	public void setChef(Chef chef) {
		this.chef = chef;
	}

	public void ajouterHabitant(Gaulois gaulois) {
		if (nbVillageois < villageois.length) {
			villageois[nbVillageois] = gaulois;
			nbVillageois++;
		}
	}

	public Gaulois trouverHabitant(String nomGaulois) {
		if (nomGaulois.equals(chef.getNom())) {
			return chef;
		}
		for (int i = 0; i < nbVillageois; i++) {
			Gaulois gaulois = villageois[i];
			if (gaulois.getNom().equals(nomGaulois)) {
				return gaulois;
			}
		}
		return null;
	}

	public String afficherVillageois() throws VillageSansChefException {
	    if (chef == null) {
	        throw new VillageSansChefException("Le village '" + nom + "' ne peut pas exister sans chef !");
	    }
		StringBuilder chaine = new StringBuilder();
		if (nbVillageois < 1) {
			chaine.append("Il n'y a encore aucun habitant au village du chef " + chef.getNom() + ".\n");
		} else {
			chaine.append("Au village du chef " + chef.getNom() + " vivent les légendaires gaulois :\n");
			for (int i = 0; i < nbVillageois; i++) {
				chaine.append("- " + villageois[i].getNom() + "\n");
			}
		}
		return chaine.toString();
	}

	public String installerVendeur(Gaulois vendeur, String produit, int nbProduit) {
		StringBuilder chaine = new StringBuilder();
		int i = marche.trouverEtalLibre();
		marche.utiliserEtal(i, vendeur, produit, nbProduit);
		chaine.append("Le vendeur " + vendeur.getNom() + " vend des " + produit + " a l'etal " + i + ".\n");
		return chaine.toString();
	}

	public String rechercherVendeursProduit(String produit) {
		StringBuilder chaine = new StringBuilder();
		Etal[] vendeursProduit = marche.trouverEtals(produit);
		if (vendeursProduit.length > 1) {
			chaine.append("Les vendeurs qui proposent des fleurs sont :\n");
			for (int i = 0; i < vendeursProduit.length; i++) {
				chaine.append("- " + vendeursProduit[i].getVendeur().getNom() + "\n");
			}
		} else if (vendeursProduit.length == 1) {
			chaine.append("Seul le vendeur "+ vendeursProduit[0].getVendeur().getNom() +" propose des " + produit + " au marche.\n");
		} else {
			chaine.append("Il n'y a pas de vendeur qui propose des " + produit + " au marche.\n");
		}
		return chaine.toString();
	}
	
	public Etal rechercherEtal(Gaulois vendeur) {
		for (int i = 0; i < marche.etals.length; i++) {
			if (marche.etals[i].getVendeur().equals(vendeur)) {
				return marche.etals[i];
			}
		}
		return null;
	}
	
	public String partirVendeur(Gaulois vendeur) {
		Etal partirEtal = rechercherEtal(vendeur);
		String chaine = partirEtal.libererEtal();
		return chaine;
	}
	
	public String afficherMarche() {
		String chaine = marche.afficherMarche();
		return chaine;
	}

	private static class Marche {
		private Etal[] etals;

		private Marche(int nbEtals) {
			etals = new Etal[nbEtals];
			for (int i = 0; i < nbEtals; i++) {
				etals[i] = new Etal();
			}
		}

		private void utiliserEtal(int indiceEtal, Gaulois vendeur, String produit, int nbProduit) {
			etals[indiceEtal].occuperEtal(vendeur, produit, nbProduit);
		}

		private int trouverEtalLibre() {
			for (int i = 0; i < etals.length; i++) {
				if (etals[i].isEtalOccupe() == false) {
					return i;
				}
			}
			return -1;
		}

		private Etal[] trouverEtals(String produit) {
			Etal[] etalsProduit;
			int taille = 0;

			for (int i = 0; i < etals.length; i++) {
				if (etals[i].contientProduit(produit) == true) {
					taille++;
				}
			}

			int index = 0;
			etalsProduit = new Etal[taille];

			for (int j = 0; j < etals.length; j++) {
				if (etals[j].contientProduit(produit)) {
					etalsProduit[index] = etals[j];
					index++;
				}
			}

			return etalsProduit;
		}

		private Etal trouverVendeur(Gaulois gaulois) {
			for (int i = 0; i < etals.length; i++) {
				if ((etals[i].getVendeur().getNom()).equals(gaulois.getNom())) {
					return etals[i];
				}
			}
			return null;
		}

		private String afficherMarche() {
			StringBuilder chaine = new StringBuilder();
			int nbVide = 0;
			for (int i = 0; i < etals.length; i++) {
				if (etals[i].isEtalOccupe() == true) {
					chaine.append(etals[i].afficherEtal());
				} else {
					nbVide++;
				}
			}
			chaine.append(("Il reste " + nbVide + " etals non utilises dans le marche."));
			return chaine.toString();
		}

	}
}