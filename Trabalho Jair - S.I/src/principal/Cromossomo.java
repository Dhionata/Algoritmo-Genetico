package principal;

import java.util.Random;

public class Cromossomo {
	private int genes[];
	private int nota;
	private int x;
	private int y;
	private Random gerador;

	public Cromossomo(int x1, int y1) {
		this.gerador = new Random();
		this.genes = new int[64];
		this.x = x1;
		this.y = y1;

		gerarGenes();
	}

	public void gerarGenes() {
		for (int i = 0; i < 63; i++) {
			this.genes[i] = this.gerador.nextInt(8) + 1;
		}
	}

	public void mostrar() {
		System.out.print("Genes: ");
		for (int i = 0; i < 63; i++) {
			System.out.print(this.genes[i]);
		}
		System.out.println(" Nota: " + this.nota);
	}

	// Getters e Setters
	public int[] getGenes() {
		return this.genes;
	}

	public void setGenes(int[] genes1) {
		this.genes = genes1;
	}

	public void setGenes(String genesStr) {
		for (int i = 0; i < genesStr.length(); i++) {
			this.genes[i] = Character.getNumericValue(genesStr.charAt(i));
		}
	}

	public int getNota() {
		return this.nota;
	}

	public void setNota(int nota1) {
		this.nota = nota1;
	}

	public int getX() {
		return this.x;
	}

	public void setX(int x1) {
		this.x = x1;
	}

	public int getY() {
		return this.y;
	}

	public void setY(int y1) {
		this.y = y1;
	}
}
