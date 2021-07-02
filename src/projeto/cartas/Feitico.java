package projeto.cartas;

import java.util.ArrayList;

import projeto.Jogador;
import projeto.cartas.efeitos.AtacaTodosInimigos;
import projeto.cartas.efeitos.Efeito;

public class Feitico extends Carta{
	private ArrayList<Efeito> efeitos;
	
	public Feitico(String nome, int custo/*, Efeito efeito*/) {
		super(nome, custo);
		this.efeitos = new ArrayList<>();
	}
	
	public void addEfeito(Efeito efeito) {
		efeitos.add(efeito);
	}
	
	@Override
	public void usarCarta(Jogador atacante, Jogador defensor) {

	}


	@Override
	public Carta getUnidade(){
		return null;
	}
}
