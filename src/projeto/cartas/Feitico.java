package projeto.cartas;

public class Feitico extends Carta{
	private Efeito efeito;
	
	public Feitico(String nome, int custo, Efeito efeito) {
		super(nome, custo);
		this.efeito = efeito;
	}
	
	public void usarFeitico() {
		efeito.ativarEfeito();
	}
}
