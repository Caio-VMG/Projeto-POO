package projeto.decks;

import java.util.Random;

import projeto.cartas.Campeao;
import projeto.cartas.Feitico;
import projeto.cartas.TipoCondicao;
import projeto.cartas.TipoFeitico;
import projeto.cartas.Unidade;
import projeto.cartas.efeitos.*;

public class Noxus extends Deck {
	
	public Noxus(String nome){
        super(nome);
        montarDeck();
    }
	
	private void montarDeck() {
        Random random = new Random();
        for (int i = 0; i<40; i++) {
            int valor = random.nextInt(11);
            if (valor == 0) {
                Unidade berserker = new Unidade("Berserker Enfurecido", 3, 8, 1);
                berserker.addFuria(2, 0);
                this.add(berserker);
            } else if (valor == 1) {
                Unidade katarina = new Unidade("Katarina", 5, 4, 4);
                katarina.addAtaqueDuplo();
                this.add(katarina);
            } else if (valor == 2) {
                Unidade AranhaG = new Unidade("Aranha Gigante", 4, 4, 3);
                AranhaG.addEfeito(new MatouComprou());
                this.add(AranhaG);
            } else if (valor == 3) {
                Unidade ballista = new Unidade("Ballista de ferro", 3, 4, 3);
                ballista.addEfeito(new AtaqueAoNexus(2));
                this.add(ballista);
            } else if (valor == 4) {
                Campeao darius = new Campeao("Darius", 6, 6, 6, TipoCondicao.KILLS);
                darius.addCondKill(1);
                darius.addPoderEvo(4);
                darius.addHpEvo(2);
                darius.addEfeitoEvo(new MatouComprou());
                this.add(darius);
            } else if (valor == 5) {
                Unidade NoxianoB = new Unidade("Noxiano Bajulador", 1, 2, 1);
                NoxianoB.addEfeito(new MorreuComprou());
                this.add(NoxianoB);
            } else if (valor == 6) {
                Unidade NoxianoI = new Unidade("Noxiano Impiedoso", 3, 4, 5);
                NoxianoI.addElusivo();
                this.add(NoxianoI);
            } else if (valor == 7) {
                Feitico LacoI = new Feitico("Laço de irmãos", 3, TipoFeitico.MUTUO);
                LacoI.addEfeito(new BuffAliadoInvocado(2, 0));
                this.add(LacoI);
            } else if (valor == 8) {
                Feitico equiparM = new Feitico("Equipar machado", 0, TipoFeitico.UNICO);
                equiparM.addEfeito(new BuffAliadoInvocado(1, 0));
                this.add(equiparM);
            } else if (valor == 9) {
                Feitico incentivar = new Feitico("Incentivar", 2, TipoFeitico.MUTUO);
                incentivar.addEfeito(new BuffAliadosInvocados(1, 0));
                this.add(incentivar);
            } else{
                Feitico dizimar = new Feitico("Dizimar", 5, TipoFeitico.ADVERSARIO);
                dizimar.addEfeito(new AtaqueAoNexus(4));
                this.add(dizimar);
            }
        }
    }
}
