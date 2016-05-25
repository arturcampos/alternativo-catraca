package app.view;

import java.util.HashMap;
import java.util.Scanner;

import app.control.CatracaController;

public class TesteLeitor {
	public static CatracaController cc;
	static{
		 if(cc == null){
			 cc = new CatracaController();
		 }
	}
	public static void main(String[] args) {
		boolean aberto = true;
		do{
			Scanner s = new Scanner(System.in);
			System.out.println("Passe o cartão:\n");
			String s2 = s.nextLine(); //12345678
			
			HashMap<String, Object> hs = cc.novoEvento(s2);

			System.out.println(hs.get("mensagem"));
			try {
				Thread.sleep(10);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}while(aberto);
	}

}
