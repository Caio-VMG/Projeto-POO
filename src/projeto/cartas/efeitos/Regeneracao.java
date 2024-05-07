package projeto.cartas.efeitos;
import projeto.Jogador;
import projeto.cartas.Unidade;
	

public class Regeneracao extends Efeito{
	
	
		public Regeneracao(){
	        super.nome = "Regeneracao";
	    }

		@Override
		public void aplicarEfeito(Jogador atacante, Jogador defensor, Unidade escolhida, TipoChamada tipo) {
			if (!escolhida.getEfeitos().contains(this)) {
				escolhida.addEfeito(this);
			}
		}
		
		@Override
		public void removerEfeito(Unidade unidade) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void ativarEfeitoKill(Jogador jogador) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void ativarEfeitoMorte(Jogador jogador) {
			// TODO Auto-generated method stub
			
		}

		
		public void passouRodada(Unidade unidade) {
			unidade.aumentarVida(unidade.getVidaMaxima());
			
		}

		

}
