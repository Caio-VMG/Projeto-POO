package projeto;

import projeto.cartas.Unidade;
import projeto.cartas.Carta;
import projeto.cartas.TipoTurno;
import projeto.cartas.Unidade;

import java.util.ArrayList;

public class Mesa {
    private ArrayList<Unidade> atacantes;
    private ArrayList<Unidade> defensores;
    private int qtdAtacantes = 0;
    private int qtdDefensores = 0;

    public Mesa(){
        atacantes = new ArrayList<>();
        defensores = new ArrayList<>();
    }


	//===================== Funções de Batalha =====================


	public void batalhaMesa(Jogador atacante, Jogador defensor) {
		
		batalhar(defensor);		
		confereMortes(atacante);
		confereMortes(defensor);
		removerMortos();
		limparMesa(atacante, defensor);
		
	}

	/*Vai receber o jogador defendendo e realizar a batalha entre cada  personagem 
	atacante e cada personagem defensor causando dano no nexus do defensor caso 
	não tenha um defensor na posição onde o atacante está atacando
	*/
	private void batalhar(Jogador defensor) {
		for(int i = 0; i < qtdAtacantes; i++) {
			if(atacantes.get(i) != null) {
				if(defensores.get(i) == null) {
					defensor.sofrerDanoNexus(atacantes.get(i).getDano());
				}
				else {
					Unidade.batalhaIndividual(atacantes.get(i), defensores.get(i));
				}
			}
		}
	}

	/**
	 * Recebe um jogador e confere se as unidades dele morreram ou não, ativando
	 * efeitos de morte/kill caso isso ocorra. Caso uma unidade morra, ela é retirada
	 * da array de atacantes/defensores e é "deletada" do jogo.
	*/
	private void confereMortes(Jogador jogador) {
		
		if (jogador.getTurno() == TipoTurno.ATAQUE) {
			for(int i = 0; i < qtdAtacantes; i++) {
				if(atacantes.get(i).getVida() <= 0) {
					mensagemMorte(atacantes.get(i));
					atacantes.get(i).confereEfeitoMorte(jogador);
					defensores.get(i).confereEfeitoKill(jogador);
				}
			}
		} else {
			for(int i = 0; i < qtdAtacantes; i++) {
				if(defensores.get(i) != null) {
					if(defensores.get(i).getVida() <= 0) {
						mensagemMorte(defensores.get(i));
						defensores.get(i).confereEfeitoMorte(jogador);
						atacantes.get(i).confereEfeitoKill(jogador);
					}
				}
			}
		}
		
	}

	private void removerMortos(){
		for(int i = 0; i < 4; i++) {
			if(atacantes.get(i) != null){
				if(atacantes.get(i).getVida() <= 0) {
					atacantes.remove(i);
					qtdAtacantes--;
				}
			}
			if(defensores.get(i) != null){
				if(defensores.get(i).getVida() <= 0) {
					defensores.remove(i);
					qtdDefensores--;
				}
			}

		}
	}


    //===================== Manipulação dos Lados (Ataque/Defesa) =====================


	/**
	 * Adiciona uma Unidade no lado de ataque da mesa.
	 */
	public void adicionarAtacante(Unidade unidade) {
		atacantes.add(qtdAtacantes, unidade);
		this.qtdAtacantes++;
	}

	/**
	 * Adiciona uma Unidade no lado de defesa da mesa.
	 */
    public void adicionarDefensor(Unidade unidade, int posicao){
        // Condicao Elusivo deve ser respeitada.
        defensores.add(posicao - 1, unidade);
        this.qtdDefensores++;
    }


	/**
	 * Cada atacante vai atacar um defensor, se este estiver na coluna, ou
	 * o nexus do "defensor", se não houver defensor.
	 */
	public int temAtacante() {
		return this.qtdAtacantes;
	}


	//===================== Manipulação da Mesa =====================


	public void inverteMesa() {
		ArrayList<Unidade> aux = this.atacantes;
		this.atacantes = defensores;
		this.defensores = aux;
	}
	
	/*Rece um jogador e retorna as cartas em atacantes ou defensores para sua
	 * mão, de acordo com seu TipoTurno
	 * */
	public void devolverCartas(Jogador jogador) {
		
		if (jogador.getTurno() == TipoTurno.ATAQUE) {
			for(int i = 0; i < atacantes.size(); i++) {
				if(atacantes.get(i) != null && atacantes.get(i).getVida() > 0) {
					jogador.sumonar(atacantes.get(i));
				}
			}
		} else {
			for(int i = 0; i < defensores.size(); i++) {
				if(defensores.get(i) != null && defensores.get(i).getVida() > 0) {
					jogador.sumonar(defensores.get(i));			
				}
			}
		}
	}

    /**
     * Todas as cartas em atacantes e defensores são retiradas e retornadas as
     * mãos dos devidos jogadores.
     */
    public void limparMesa(Jogador atacante, Jogador defensor){
    	//Acredito que as cartas só deveriam ser devolvidas se estiverem vivas
    	devolverCartas(atacante);
    	devolverCartas(defensor);
        atacantes.clear();
        defensores.clear();
        this.preencheMesa();
        qtdAtacantes = 0;
        qtdDefensores = 0;
    }

    
    public void preencheMesa() {
    	for(int i = 0; i < 4; i++) {
    		atacantes.add(null);
    		defensores.add(null);
    	}
    }

    public boolean posEhValida(int posicao) {
    	if (defensores.get(posicao-1) != null) {
    		return false;
    	}
    	return true;
    }
	//======================== Impressão ========================


	/**
	 * Imprime o lado dos atacantes e o lados dos defensores da mesa.
	 */
	public void printMesa(){

		for(Unidade atacante: atacantes){
			if(atacante != null){
				atacante.printUnidade();
			} else {
				System.out.printf(" -- ");
			}
		}
		System.out.println();
		int i = 1;
		for(Unidade defensor: defensores){
			if(defensor != null){
				defensor.printUnidade();
			} else {
				System.out.printf(" -(%d)- ",i);
			}
			i++;
		}
		System.out.println();
	}
	


	private void mensagemMorte(Unidade derrotada) {
		System.out.printf("%s foi derrotado(a)\n", derrotada.getNome());
		System.out.println("");
	}


	//======================== Getters ========================


	public int getQtdDefensores() {
		return qtdDefensores;
	}

	public int getQtdAtacantes() {
		return qtdAtacantes;
	}
}


