package cz.GravelCZ.PasteeeAPITest;

import java.util.ArrayList;
import java.util.List;

import cz.GravelCZ.PasteeeAPI.PasteEEAPI;
import cz.GravelCZ.PasteeeAPI.Responses.ListPasteResponse;
import cz.GravelCZ.PasteeeAPI.Utils.Pair;
import cz.GravelCZ.PasteeeAPI.Utils.PasteSection;
import cz.GravelCZ.PasteeeAPI.Utils.Syntax;

public class MainTest {

	public static void main(String[] args) {
		PasteEEAPI api = new PasteEEAPI();
		api.debug();
		api.init("your key here");
		
		System.out.println("Creating paste");
		List<PasteSection> pastes = new ArrayList<>();
		for (int i = 0; i < 10; i++) {
			pastes.add(new PasteSection("Test", null, "Testing"));
		}
		
		api.submitPaste(true, "test", pastes);
		System.out.println("Finished creating paste");
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		System.out.println("Getting syntaxes");
		Pair<Boolean, List<Syntax>> resp1 = api.getSyntaxes();
		if (resp1.getLeft()) {
			System.out.println("syntaxes:");
			for (Syntax s : resp1.getRight()) {
				System.out.println(s.name);
			}
			try {
				Thread.sleep(5000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			System.out.println("Getting single syntax");
			Pair<Boolean, Syntax> s = api.getSyntax(resp1.getRight().get(0).id);
			if (s.getLeft()) {
				System.out.println(s.getRight().name);	
			}
		}
		
		System.out.println("Finished syntaxes");
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		api.useAsUser("your name here", "your password here");
		
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		System.out.println("Getting user pastes");
		ListPasteResponse response = api.listPastes();
		System.out.println("Total pastes: " + response.getTotal());
		System.out.println("Finished single syntax");
		
		System.out.println("End test");
	}

}
