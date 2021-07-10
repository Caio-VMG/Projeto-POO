package projeto;

import projeto.cartas.Unidade;
import projeto.cartas.Campeao;
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
		confereMortes(atacante, defensor);
		confereMortes(defensor, atacante);
		removerMortos();
		passarTurnoEfeitos();
		conferirEvolucoes();
		limparMesa(atacante, defensor);
		
	}

	/**
	 * Vai receber o jogador defendendo e realizar a batalha entre cada  personagem
	 * 	atacante e cada personagem defensor causando dano no nexus do defensor caso
	 * 	não tenha um defensor na posição onde o atacante está atacando
	*/
	private void batalhar(Jogador defensor) {
		for(int i = 0; i < qtdAtacantes; i++) {
			if(defensores.get(i) == null) {
				defensor.sofrerDanoNexus(atacantes.get(i).getDano());
				if (atacantes.get(i).getAtaqueDuplo()) {
					defensor.sofrerDanoNexus(atacantes.get(i).getDano());
				}
				atacantes.get(i).aumentaAtaque();
			}
			else {
				Unidade.batalhaIndividual(atacantes.get(i), defensores.get(i));
				if(atacantes.get(i).getAtaqueDuplo() && atacantes.get(i).getVida() >= 0) {
					if (defensores.get(i).getVida() <= 0) {
						defensor.sofrerDanoNexus(atacantes.get(i).getDano());
					} else {
						Unidade.batalhaIndividual(atacantes.get(i), defensores.get(i));
					}	
				}
			}
		}
	}

	/**
	 * Recebe um jogador e confere se as unidades dele morreram ou não, ativando
	 * efeitos de morte/kill caso isso ocorra. Caso uma unidade morra, ela é retirada
	 * da array de atacantes/defensores e é "deletada" do jogo.
	*/
	private void confereMortes(Jogador jogador1, Jogador jogador2) {
		
		if (jogador1.getTurno() == TipoTurno.ATAQUE) {
			for(int i = 0; i < qtdAtacantes; i++) {
				if(atacantes.get(i).getVida() <= 0) {
					mensagemMorte(atacantes.get(i));
					atacantes.get(i).confereEfeitoMorte(jogador1);
					defensores.get(i).confereEfeitoKill(jogador2);
				}
			}
		} else {
			for(int i = 0; i < qtdAtacantes; i++) {
				if(defensores.get(i) != null) {
					if(defensores.get(i).getVida() <= 0) {
						mensagemMorte(defensores.get(i));
						defensores.get(i).confereEfeitoMorte(jogador1);
						atacantes.get(i).confereEfeitoKill(jogador2);
					}
				}
			}
		}
		
	}
	
	private void conferirEvolucoes() {
		for (int i = 0; i < qtdAtacantes; i++ ) {
			if (atacantes.get(i).ehEvoluivel()) {
				Campeao c = (Campeao)atacantes.get(i);
				c.tentarEvoluir();
			}
		}
		
		for (int i = 0; i < 4; i++ ) {
			if (defensores.get(i) != null && defensores.get(i).ehEvoluivel()) {
				Campeao c = (Campeao)defensores.get(i);
				c.tentarEvoluir();
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
	
	private void passarTurnoEfeitos() {
		for(int i = 0; i < qtdAtacantes; i++) {
			if (atacantes.get(i) != null) {
				atacantes.get(i).passarTurnoEfeitos();
			}
		}
		
		for (int i = 0; i < qtdDefensores; i++){
			if (defensores.get(i) != null) {
				defensores.get(i).passarTurnoEfeitos();
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

    public boolean posEhValida(int posicao, Carta carta) {
    	if (defensores.get(posicao-1) != null) {
    		return false;
    	} else if (atacantes.get(posicao-1).ehElusivo()) {
    		if (!carta.ehElusivo()) {
    			return false;
    		}
    	}
    	return true;
    }
	//======================== Impressão ========================

	public void printLado(ArrayList<Unidade> unidades){
		for(Unidade unidade: unidades){
			if(unidade != null){
				unidade.printUnidade();
			} else {
				System.out.printf(" -- ");
			}
		}
	}

	public void printIndicesDefesa(){
		int i = 1;
		for(Unidade defensor: defensores){
			if(defensor != null){
				defensor.printUnidade();
			} else {
				System.out.printf(" -(%d)- ",i);
			}
			i++;
		}
	}

	/**
	 * Imprime o lado dos atacantes e o lados dos defensores da mesa.
	 */
	public void printMesa(){
		printLado(atacantes);
		System.out.println();
		printIndicesDefesa();
		System.out.println();
	}

	public void mostrarLado(Jogador jogador){
		if(jogador.getTurno() == TipoTurno.ATAQUE){
			printLado(atacantes);
		} else {
			printLado(defensores);
		}
	}


	private void mensagemMorte(Unidade derrotada) {
		System.out.printf("%s foi derrotado(a)\n", derrotada.getNome());
		System.out.println();
	}


	//======================== Getters ========================


	public int getQtdDefensores() {
		return qtdDefensores;
	}

	public int getQtdAtacantes() {
		return qtdAtacantes;
	}
}


