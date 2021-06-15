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

    public void adicionarAtacante(Unidade unidade){
        atacantes.add(unidade);
    }

    public void adicionarDefensor(Unidade unidade, int pos){
        // Condicao Elusivo deve ser respeitada.
        if(pos < atacantes.size())
            defensores.add(pos, unidade);
        else
            System.out.println("Não é possível adicionar a carta nessa posição.");
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


