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
	public void usarCarta(Jogador atacante, Jogador defensor) {
		for(int i = 0; i < efeitos.size(); i++) {
			efeitos.get(i).aplicarEfeito(atacante, defensor, atacante);
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
}
