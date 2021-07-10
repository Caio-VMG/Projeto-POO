package projeto.cartas;

import java.util.ArrayList;
import java.util.Scanner;

import projeto.Jogador;
import projeto.cartas.efeitos.Efeito;
import projeto.cartas.efeitos.TipoChamada;

public class Feitico extends Carta{
	private ArrayList<Efeito> efeitos;
	private TipoFeitico tipo;
	
	
	public Feitico(String nome, int custo, TipoFeitico tipo) {
		super(nome, custo);
		this.efeitos = new ArrayList<>();
		this.tipo = tipo;
		
	}

	@Override
	public boolean canSummon(int manaAtual, int manaFeitico) {
		if(this.getCusto() > manaAtual + manaFeitico){
			return false;
		} else {
			return true;
		}
	}

	public int calcularCustoManaFeitico(int manaFeitico){
		if(manaFeitico - super.getCusto() <= 0){
			return 0;
		} else {
			return manaFeitico - super.getCusto();
		}
	}

	/**
	 * O custo normal de um feitico é dado pelo que sobrou pagar
	 * considerando que uma quantia ja foi paga usando-se mana de feitico.
	 */
	public int calcularCustoNormal(int manaFeitico, int manaNormal){
		return manaNormal - (super.getCusto() - manaFeitico);
	}

	public void addEfeito(Efeito efeito) {
		efeitos.add(efeito);
	}

	@Override
	public void printCarta(){
		System.out.printf("%s (%d)", super.getNome(), super.getCusto());
	}

	public void printDetalhes() {
		System.out.printf("%s (%d) - ", super.getNome(), super.getCusto());
		if(!efeitos.isEmpty()) {
			System.out.printf("Efeitos: ");
			for (Efeito efeito : efeitos) {
				efeito.printNome();
				System.out.printf(" ");
			}
		}
	}

	public void mostrarCarta(){

	}

	@Override
	public void usarCarta(Jogador atacante, Jogador defensor) {
		if(tipo == TipoFeitico.MULTUO && atacante.getQtdEvocadas() > 0) {
			for(int i = 0; i < atacante.getQtdEvocadas(); i++) {
				Unidade escolhida = (Unidade)atacante.getEvocadas().get(i);
				for(int j = 0; i < efeitos.size(); i++) {
					efeitos.get(j).aplicarEfeito(atacante, defensor, escolhida, TipoChamada.FEITICO);
				}
			}
		}
		else if(tipo == TipoFeitico.UNICO && atacante.getQtdEvocadas() > 0) {
			System.out.printf("Escolha um aliado para usar %s\n", super.getNome());
			atacante.imprimeEvocadas();
			Scanner ler = new Scanner(System.in);
			int escolha = ler.nextInt();
			while(escolha > atacante.getEvocadas().size() || escolha < 1) {
				System.out.println("Escolha inv�lida");
				escolha = ler.nextInt();
			}
			Unidade escolhida = (Unidade)atacante.getEvocadas().get(escolha - 1);
			for(int i = 0; i < efeitos.size(); i++) {
				efeitos.get(i).aplicarEfeito(atacante, defensor, escolhida, TipoChamada.FEITICO);
			}
		}
		else if(tipo == TipoFeitico.ADVERSARIO) {
			for(int i = 0; i < efeitos.size(); i++) {
				efeitos.get(i).aplicarEfeito(atacante, defensor, null, TipoChamada.FEITICO);
			}
		}
		else {
			System.out.println("Você não possui cartas evocadas para usar feitiço");
			atacante.addCartaMao(this);
			atacante.ganharMana(this.getCusto());
		}
	}
	

	@Override
	public Carta getUnidade(){
		return null;
	}

	@Override
	public boolean ehElusivo() {
		return false;
	}

	public boolean ehTrocavel(){
		return false;
	}
}
