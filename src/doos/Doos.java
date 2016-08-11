package doos;

public class Doos<T extends Verpakking> implements Comparable{

	@Override
	public String toString() {
		return "Doos [hoogte=" + hoogte + ", breedte=" + breedte + ", kleur=" + kleur + ", gewicht=" + gewicht
				+ ", gevaargoed=" + gevaargoed + "]";
	}

	private double hoogte;
	private double breedte;
	private Kleur kleur;
	private double gewicht;
	private boolean gevaargoed;
	
	public Doos(double hoogte, double breedte, Kleur kleur, double gewicht, boolean gevaargoed) {
		this.setHoogte(hoogte);
		this.setBreedte(breedte);
		this.setGevaargoed(gevaargoed);
		this.setKleur(kleur);
		this.setGewicht(gewicht);
	}
	
	public Doos()
	{
		
	}

	public double getHoogte() {
		return hoogte;
	}

	public void setHoogte(double hoogte) {
		this.hoogte = hoogte;
	}

	public double getBreedte() {
		return breedte;
	}

	public void setBreedte(double breedte) {
		this.breedte = breedte;
	}

	public Kleur getKleur() {
		return kleur;
	}

	public void setKleur(Kleur kleur) {
		this.kleur = kleur;
	}

	public double getGewicht() {
		return gewicht;
	}

	public void setGewicht(double gewicht) {
		this.gewicht = gewicht;
	}

	public boolean isGevaargoed() {
		return gevaargoed;
	}

	public void setGevaargoed(boolean gevaargoed) {
		this.gevaargoed = gevaargoed;
	}

	public int compareTo(Doos<Verpakking> o) {
		int comparage = (int) (this.getGewicht()-o.getGewicht());
		return comparage;
	}

	@Override
	public int compareTo(Object o) {
		int compare = (int) ((Doos<Verpakking>)o).getGewicht();
		return (int) (this.getGewicht()-compare);
	}
	
	
}
