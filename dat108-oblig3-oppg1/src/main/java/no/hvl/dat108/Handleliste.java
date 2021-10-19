package no.hvl.dat108;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map.Entry;

public class Handleliste {
	
	private HashMap<Integer,String> handleliste; // Int = ID, String = value
	private int teller;
	
	public Handleliste() {
		handleliste = new HashMap<>();
		teller = 1;
	}
	
	public void leggTil(String vare) {
		handleliste.put(teller++, vare);
	}
	
	public void fjern(int id) {
		handleliste.remove(id);
	}
	
	public Collection<Entry<Integer,String>> hentListe(){
		return handleliste.entrySet();
	}

}
