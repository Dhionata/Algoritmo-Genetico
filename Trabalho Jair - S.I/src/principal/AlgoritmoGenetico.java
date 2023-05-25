package principal;

import java.util.Random;

public class AlgoritmoGenetico {
	private Cromossomo populacao[];
	private int tabuleiro[][];
	private Random gerador;
	private int qtdPopulacao;
	private int x;
	private int y;

	public AlgoritmoGenetico(int qtdPais, int qtdFilhosPorCasal, int maximoGeracoes, int x1, int y1) {
		this.tabuleiro = new int[8][8];
		this.gerador = new Random();
		this.qtdPopulacao = calcularQtdPopulacao(qtdPais, qtdFilhosPorCasal);
		System.out.println("Populacao: " + this.qtdPopulacao);
		this.populacao = new Cromossomo[this.qtdPopulacao];
		this.x = x1;
		this.y = y1;
		Cromossomo melhorCromossomo = new Cromossomo(x1, y1);
		int geracao = 0;
		int ultimaGeracao = 0;
		long tempoInicial = System.currentTimeMillis();
		criarPais(qtdPais);

		while (true) {
			acasalamento(qtdPais, qtdFilhosPorCasal);
			selecaoNatural();
			if (this.populacao[0].getNota() > melhorCromossomo.getNota()) {
				ultimaGeracao = geracao;
				long tempoFinal = System.currentTimeMillis();
				melhorCromossomo = this.populacao[0];
				melhorCromossomo.mostrar();
				System.out.println("new AlgoritmoGenetico(" + qtdPais + ", " + qtdFilhosPorCasal + ", " + maximoGeracoes
						+ ", " + x1 + ", " + y1 + ")");
				System.out.println(
						"Geração: " + geracao + " Tempo: " + ((tempoFinal - tempoInicial) / 1000.0) + " segundos");
				mostrarPercursoMelhorCromossomo();
			}
			if (melhorCromossomo.getNota() == 63) {
				System.out.println("Encontrado melhor cromossomo!");
				break;
			}
			if ((geracao - ultimaGeracao) > maximoGeracoes) {
				ultimaGeracao = geracao;
				criarPais(qtdPais);
			}
			geracao++;
		}
	}

	private void criarPais(int qtdPais) {
		for (int i = 0; i < qtdPais; i++) {
			this.populacao[i] = new Cromossomo(this.x, this.y);
			gerarNota(this.populacao[i]);
		}
	}

	private void acasalamento(int qtdPais, int qtdFilhos) {
		int posicao = qtdPais;
		for (int pai = 0; pai < (qtdPais - 1); pai++) {
			for (int mae = pai + 1; mae < qtdPais; mae++) {
				for (int filho = 0; filho < qtdFilhos; filho++) {
					this.populacao[posicao] = crossover(this.populacao[pai], this.populacao[mae]);
					gerarNota(this.populacao[posicao]);
					posicao++;
				}
			}
		}
	}

	public void mostrarDados() {
		System.out.println("Quantidade de populacao: " + this.qtdPopulacao);
		System.out.println("Populacao: ");
		for (int i = 0; i < this.qtdPopulacao; i++) {
			if (this.populacao[i] != null) {
				this.populacao[i].mostrar();
			}
		}
	}

	private static int calcularQtdPopulacao(int qtdPais, int qtdFilhos) {
		return (calcularQtdCasais(qtdPais) * qtdFilhos) + qtdPais;
	}

	private static int calcularQtdCasais(int qtdPais) {
		return fatorial(qtdPais) / (fatorial(qtdPais - 2) * 2);
	}

	private static int fatorial(int n) {
		int resultado = 1;
		for (int i = 1; i <= n; i++) {
			resultado = resultado * i;
		}

		return resultado;
	}

	private void limparTabuleiro() {
		for (int y1 = 0; y1 < 8; y1++) {
			for (int x1 = 0; x1 < 8; x1++) {
				this.tabuleiro[x1][y1] = 0;
			}
		}
	}

	private boolean isMovimentoValido(int movimento, int x1, int y1) {
		switch (movimento) {
		case 1:
			x1 += 1;
			y1 += 2;
			break;
		case 2:
			x1 += 2;
			y1 += 1;
			break;
		case 3:
			x1 += 2;
			y1 -= 1;
			break;
		case 4:
			x1 += 1;
			y1 -= 2;
			break;
		case 5:
			x1 -= 1;
			y1 -= 2;
			break;
		case 6:
			x1 -= 2;
			y1 -= 1;
			break;
		case 7:
			x1 -= 2;
			y1 += 1;
			break;
		case 8:
			x1 -= 1;
			y1 += 2;
			break;
		}
		if ((x1 <= 7) && (x1 >= 0) && (y1 <= 7) && (y1 >= 0) && (this.tabuleiro[x1][y1] == 0)) {
			return true;
		}
		return false;
	}

	private void moverCavalo(int movimento, int jogada, Cromossomo c) {
		int x1 = c.getX();
		int y1 = c.getY();
		switch (movimento) {
		case 1:
			x1 += 1;
			y1 += 2;
			break;
		case 2:
			x1 += 2;
			y1 += 1;
			break;
		case 3:
			x1 += 2;
			y1 -= 1;
			break;
		case 4:
			x1 += 1;
			y1 -= 2;
			break;
		case 5:
			x1 -= 1;
			y1 -= 2;
			break;
		case 6:
			x1 -= 2;
			y1 -= 1;
			break;
		case 7:
			x1 -= 2;
			y1 += 1;
			break;
		case 8:
			x1 -= 1;
			y1 += 2;
			break;
		}
		this.tabuleiro[x1][y1] = jogada;
		c.setX(x1);
		c.setY(y1);
	}

	private void gerarNota(Cromossomo c) {
		int[] genes = c.getGenes();
		limparTabuleiro();
		this.tabuleiro[c.getX()][c.getY()] = 1;
		c.setNota(0);
		for (int i = 0; i <= 63; i++) {
			if (isMovimentoValido(genes[i], c.getX(), c.getY())) {
				c.setNota(c.getNota() + 1);
				moverCavalo(genes[i], i + 2, c);
			} else {
				break;
			}
		}
		c.setX(this.x);
		c.setY(this.y);
	}

	private Cromossomo crossover(Cromossomo pai, Cromossomo mae) {
		Cromossomo filho = new Cromossomo(mae.getX(), mae.getY());

		for (int i = 0; i <= 63; i++) {
			if (this.gerador.nextInt(2) == 0) {
				filho.getGenes()[i] = pai.getGenes()[i];
			} else {
				filho.getGenes()[i] = mae.getGenes()[i];
			}
		}
		gerarNota(filho);
		if (this.gerador.nextInt(2) == 0) {
			mutacao(filho);
			gerarNota(filho);
		}
		return filho;
	}

	private void mutacao(Cromossomo c) {
		int[] genes = c.getGenes();
		int nota = c.getNota();
		if (nota <= 1) {
			nota = 0;
		}
		genes[nota] = this.gerador.nextInt(8) + 1;
	}

	public void mostrarTabuleiro() {
		for (int linha = 0; linha < 8; linha++) {
			for (int coluna = 0; coluna < 8; coluna++) {
				if (this.tabuleiro[linha][coluna] < 10) {
					System.out.print("  " + this.tabuleiro[linha][coluna]);
				} else {
					System.out.print(" " + this.tabuleiro[linha][coluna]);
				}
			}
			System.out.println();
		}
		System.out.println();
	}

	public void mostrarPercursoMelhorCromossomo() {
		gerarNota(this.populacao[0]);
		mostrarTabuleiro();
	}

	private void selecaoNatural() {
		Cromossomo aux;
		for (int i = 0; i < (this.qtdPopulacao - 1); i++) {
			int indiceComMaiorNota = i;
			for (int j = i; j < this.qtdPopulacao; j++) {
				if (this.populacao[j].getNota() > this.populacao[indiceComMaiorNota].getNota()) {
					indiceComMaiorNota = j;
				}
			}
			aux = this.populacao[i];
			this.populacao[i] = this.populacao[indiceComMaiorNota];
			this.populacao[indiceComMaiorNota] = aux;
		}
	}
}
