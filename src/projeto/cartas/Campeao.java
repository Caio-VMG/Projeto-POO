package projeto.cartas;

import java.util.ArrayList;

import projeto.Jogador;
import projeto.cartas.efeitos.Efeito;

public class Campeao extends Unidade{	
	private TipoCondicao condicao;
	private boolean jaEvoluiu = false;
	private int condAtaques = 0;
	private int condDanoC = 0;
	private int condDanoS = 0;
	private int condKill = 0;
	private int hpEvo = 0;
	private int poderEvo = 0;
	private int atkFuria = 0;
	private int hpFuria = 0;
	private ArrayList<Efeito> efeitosEvo;
	private ArrayList<Traco> tracosEvo;
	
	public Campeao(String nome, int custo, int vida, int poder, TipoCondicao condicao) {
		super(nome, custo, vida, poder);
		this.condicao = condicao;
		this.efeitosEvo = new ArrayList<>();
		this.tracosEvo = new ArrayList<>();		
	}
	
	public void addCondAtk(int atks) {
		this.condAtaques = atks;
	}
	
	public void addCondDanoC(int danoc) {
		this.condDanoC = danoc;
	}
	
	public void addCondDanoS(int danoS) {
		this.condDanoS = danoS;
	}
	
	public void addCondKill(int kills) {
		this.condKill = kills;
	}
	
	public void addEfeitoEvo(Efeito efeito) {
		efeitosEvo.add(efeito);
	}
	
	public void addTracoEvo(Traco traco) {
		tracosEvo.add(traco);
	}
	
	public void addPoderEvo(int poder) {
		this.poderEvo = poder;
	}
	
	public void addHpEvo(int hp) {
		this.hpEvo = hp;
	}
	
	public void addAtkFuria(int atk) {
		this.atkFuria = atk;
	}
	
	public void addHpFuria(int hp) {
		this.hpFuria = hp;
	}
	
	public void usarCarta(Jogador jogador, int entrada) {
    	if(jogador.getMana() >= jogador.getMao().get(entrada).getCusto()) {
			jogador.getEvocadas().add(jogador.getMao().get(entrada));
			jogador.setMana(jogador.getMao().get(entrada).getCusto());
		}
		else {
			System.out.println("Voc� n�o possui mana suficiente");
		}
    }
	
	public int getDano() {
		return this.poder;
	}
	
	public void sofrerDano(int danoRecebido) {
		this.vida -= danoRecebido;
	}
	
	public void tentarEvoluir() {
		if (podeEvoluir()) {
			jaEvoluiu = true;
			System.out.printf("%s acaba de evoluir\n\n", super.getNome());
			super.aumentarDano(poderEvo);
			super.aumentarVida(hpEvo);
			
			for (int i = 0; i < efeitosEvo.size(); i++) {
				super.addEfeito(efeitosEvo.get(i));
			}
			
			for (int i = 0; i < tracosEvo.size(); i++) {
				if (tracosEvo.get(i) == Traco.ATAQUEDUPLO) {
					super.addAtaqueDuplo();
				} else if (tracosEvo.get(i) == Traco.ELUSIVO) {
					super.addElusivo();
				} else if (tracosEvo.get(i) == Traco.FURIA) {
					super.addFuria(atkFuria, hpFuria);
				}
			}
			
		}
	}
	
	private boolean podeEvoluir() {
		if (jaEvoluiu) {
			return false;
		}
		if (condicao == TipoCondicao.ATAQUES && super.ataques >= this.condAtaques) {
			return true;
		} else if (condicao == TipoCondicao.DANOC && super.danoCausado >= this.condDanoC) {
			return true;
		} else if (condicao == TipoCondicao.DANOS && super.danoSofrido >= this.condDanoS) {
			return true;
		} else if (condicao == TipoCondicao.KILLS && super.kills >= this.condKill) {
			return true;
		}
		return false;
		
	}	

	public boolean ehEvoluivel() {
		return true;
	}
}
