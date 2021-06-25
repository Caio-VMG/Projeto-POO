package projeto;

import projeto.cartas.Unidade;
import projeto.cartas.Carta;
import projeto.cartas.TipoTurno;
import projeto.cartas.Unidade;

import java.util.ArrayList;

public class Mesa {
    private ArrayList<Unidade> atacantes;
    private ArrayList<Unidade> defensores;

    public Mesa(){
        atacantes = new ArrayList<>();
        defensores = new ArrayList<>();
    }
    
    public void preencheMesas() {
    	for(int i = 0; i < 4; i++) {
    		atacantes.add(i, null);
    		defensores.add(i, null);
    	}
    }
    
    private void mensagemMorte(Unidade derrotada) {
    	System.out.printf("%s foi derrotado(a)\n", derrotada.getNome());
    	System.out.println("");
    }
    
    public void inverteMesa() {
    	ArrayList<Unidade> aux = atacantes;
    	atacantes = defensores;
    	defensores = aux;
    }

    public void adicionarAtacante(Unidade unidade, int posicao){
        atacantes.add(posicao - 1, unidade);
    }

    public void adicionarDefensor(Unidade unidade, int posicao){
        // Condicao Elusivo deve ser respeitada.
        defensores.add(posicao - 1, unidade);
    }
    
    public void batalha(Jogador atacante, Jogador defensor) {
    	for(int i = 0; i < 4; i++) {
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
    	for(int i = 0; i < 4; i++) {
    		if(defensores.get(i).getVida() <= 0) {
    			mensagemMorte(defensores.get(i));
    			defensores.remove(i);
    		}
    		if(atacantes.get(i).getVida() <= 0) {
    			mensagemMorte(atacantes.get(i));
    			atacantes.remove(i);
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

    /**
     * Cada atacante vai atacar um defensor, se este estiver na coluna, ou
     * o nexus do "defensor", se não houver defensor.
     */
    public void realizarCombate(Jogador defensor){
        for(int i = 0; i < atacantes.size(); i++){
            if(defensores.get(i) != null){
                Unidade.batalhar(atacantes.get(i), defensores.get(i));
            } else {
                defensor.sofrerDano(atacantes.get(i));
            }
        }
    }

}


