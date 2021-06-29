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
    
    private void mensagemMorte(Unidade derrotada) {
    	System.out.printf("%s foi derrotado(a)\n", derrotada.getNome());
    	System.out.println("");
    }
    
    public void inverteMesa() {
    	ArrayList<Unidade> aux = this.atacantes;
    	this.atacantes = defensores;
    	this.defensores = aux;
    }

    public void adicionarAtacante(Unidade unidade){
        if(qtdAtacantes < 4) {
        	atacantes.add(qtdAtacantes, unidade);
        	this.qtdAtacantes++;
        }
        else {
        	System.out.println("Não é possível atacar com mais de 4 cartas");
        	System.out.println("");
        	
        }
    }

    public void adicionarDefensor(Unidade unidade, int posicao){
        // Condicao Elusivo deve ser respeitada.
        defensores.add(posicao - 1, unidade);
        this.qtdDefensores++;
    }
    
    public void batalha(Jogador atacante, Jogador defensor) {
    	for(int i = 0; i < qtdAtacantes; i++) {
    		if(atacantes.get(i) != null) {
    			if(defensores.get(i) == null) {
    				defensor.sofrerDanoNexus(atacantes.get(i).getDano());
    			}
    			else {
    				defensores.get(i).sofrerDano(atacantes.get(i).getDano());
    				atacantes.get(i).sofrerDano(defensores.get(i).getDano());
    			}
    		}
    	}
    	for(int i = 0; i < qtdDefensores; i++) {
    		if(defensores.get(i) != null) {
    			if(defensores.get(i).getVida() <= 0) {
    				mensagemMorte(defensores.get(i));
    				defensores.remove(i);
    			}
    		}    		
    	}
    	
    	for(int i = 0; i < qtdAtacantes; i++) {
			if(atacantes.get(i) != null) {
				if(atacantes.get(i).getVida() <= 0) {
					mensagemMorte(atacantes.get(i));
					atacantes.remove(i);
				}
			}
    	}
    }

    /**
     * Todas as cartas em atacantes e defensores são retiradas.
     */
    public void limparMesa(){
        atacantes.clear();
        defensores.clear();
    }
    
    public int temAtacante() {
    	return this.qtdAtacantes;    
    }
    
    public void preencheMesa() {
    	for(int i = 0; i < 4; i++) {
    		atacantes.add(null);
    		defensores.add(null);
    	}
    }

    public void printMesa(){
		for(int i = 0; i < 4; i++){
			Unidade atacante = atacantes.get(i);
			Unidade defensor = defensores.get(i);
			if(atacante != null){
				atacante.printUnidade();
			} else {
				System.out.printf("--");
			}
			System.out.printf("\t \t");
			if(defensor != null){
				defensor.printUnidade();
			} else {
				System.out.printf("--");
			}
		}
    }

}


