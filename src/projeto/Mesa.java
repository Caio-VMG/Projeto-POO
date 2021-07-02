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
		limparMesa(atacante, defensor);
		
	}
	
	/*private void confereEfeitoKillAtacante(Jogador atacante) {
		for(int i = 0; i < atacantes.size(); i++) {
			atacantes.get(i).confereEfeitoKill(atacante);
		}
	}*/

	/*private void confereEfeitoKillDefensor(Jogador defensor) {
		for(int i = 0; i < defensores.size(); i++) {
			atacantes.get(i).confereEfeitoKill(defensor);
		}
	}*/

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

	/*Recebe um jogador e confere se as unidades dele morreram ou não, ativando
	efeitos de morte/kill caso isso ocorra. Caso uma unidade morra, ela é retirada
	da array de atacantes/defensores e é "deletada" do jogo.
	*/
	private void confereMortes(Jogador jogador) {
		
		if (jogador.getTurno() == TipoTurno.ATAQUE) {
			
			for(int i = 0; i < qtdAtacantes; i++) {
				if(atacantes.get(i) != null) {
					if(atacantes.get(i).getVida() <= 0) {
						mensagemMorte(atacantes.get(i));
						/*Aqui vai precisar conferir se tem efeito de morte
						Ai se morreu já confere a mesma posição de defensores
						pra ver se o defensor tem efeito pra quando mata 
						*/
						atacantes.remove(i);
						qtdAtacantes--;
					}
				}
			}
		} else {
			
			for(int i = 0; i < qtdDefensores; i++) {
				if(defensores.get(i) != null) {
					if(defensores.get(i).getVida() <= 0) {
						mensagemMorte(defensores.get(i));
						/*Aqui vai precisar conferir se tem efeito de morte
						ai se morreu já confere a mesma posição de atacantes
						pra ver se o atacante tem efeito pra quando mata 
						*/
						defensores.remove(i);
						qtdDefensores--;
					}
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


	//======================== Impressão ========================


	/**
	 * Imprime o lado dos atacantes e o lados dos defensores da mesa.
	 */
	public void printMesa(){
		for(int i = 0; i < qtdAtacantes; i++){
			System.out.printf("[%d] ",i+1);
			atacantes.get(i).printUnidade();
			//Unidade atacante = atacantes.get(i);
			//Unidade defensor = defensores.get(i);
			//if(atacante != null){
				//System.out.printf("[%d] ",i+1);
				//atacante.printUnidade();
			}
		System.out.printf("\n");
		if(qtdAtacantes < 4) {
				for(int i = 0; i < 4 - qtdAtacantes; i++) {
					System.out.printf("--\n");
				}
		}
			System.out.printf("\t \t");
		
		for(int i = 0; i < qtdDefensores; i++) {
			System.out.printf("[%d] ",i+1);
			atacantes.get(i).printUnidade();
		}
		System.out.printf("\n");
		if(qtdDefensores < 4) {
			for(int i = 0; i < 4 - qtdDefensores; i++) {
				System.out.printf("--\n");
			}
	}
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


