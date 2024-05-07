package projeto.decks;

import projeto.cartas.Campeao;
import projeto.cartas.Feitico;
import projeto.cartas.TipoCondicao;
import projeto.cartas.TipoFeitico;
import projeto.cartas.Traco;
import projeto.cartas.Unidade;
import projeto.cartas.efeitos.*;

import java.util.Random;

public class Demacia extends Deck {

    public Demacia(String nome){
        super(nome);
        montarDeck();
    }

    private void montarDeck() {
        Random random = new Random();
        for (int i = 0; i<40; i++) {
            int valor = random.nextInt(11);
            if (valor == 0) {
                Campeao garen = new Campeao("Garen", 5, 5, 5, TipoCondicao.ATAQUES);
                garen.addCondAtk(2);
                garen.addHpEvo(1);
                garen.addPoderEvo(1);
                garen.addTracoEvo(Traco.ELUSIVO);
                garen.addEfeito(new Regeneracao());
                this.add(garen);
            } else if (valor == 1) {
                Unidade tiana = new Unidade("Tiana", 8, 7, 7);
                tiana.addEfeito(new UnidadeAtacaNexus());
                this.add(tiana);
            } else if (valor == 2) {
                Unidade vanguarda = new Unidade("Vanguarda", 4, 3, 3);
                vanguarda.addEfeito(new BuffAliadosInvocados(1,1));
                this.add(vanguarda);
            } else if (valor == 3) {
                Unidade duelista = new Unidade("Duelista", 3, 2, 3);
                duelista.addEfeito(new MatouComprou());
                this.add(duelista);
            } else if (valor == 4) {
                Unidade defensor = new Unidade("Defensor", 2, 2, 2);
                defensor.addFuria(0, 1);
                this.add(defensor);
            } else if (valor == 5) {
                Unidade poro = new Unidade("Poro", 1, 1, 2);
                this.add(poro);
            } else if (valor == 6) {
                Unidade poroD = new Unidade("Poro Defensor", 1, 2, 1);
                poroD.addEfeito(new MorreuComprou());
                this.add(poroD);
            } else if (valor == 7) {
                Feitico julgamento = new Feitico("Julgamento", 8, TipoFeitico.ADVERSARIO);
                julgamento.addEfeito(new AtacaTodosInimigos(2, 3));
                this.add(julgamento);
            } else if (valor == 8) {
                Feitico valorR = new Feitico("Valor Redobrado", 6, TipoFeitico.UNICO);
                valorR.addEfeito(new CuraUnidade());
                valorR.addEfeito(new Dobradinha());
                this.add(valorR);
            } else if (valor == 9) {
                Feitico golpeC = new Feitico("Golpe Certeiro", 1, TipoFeitico.UNICO);
                golpeC.addEfeito(new BuffAliadoInvocado(1, 1));
                this.add(golpeC);
            } else{
                Feitico combate1a1 = new Feitico("Combate um a um", 2, TipoFeitico.UNICO);
                combate1a1.addEfeito(new ChamouX1());
                this.add(combate1a1);
            }
        }
    }

}
