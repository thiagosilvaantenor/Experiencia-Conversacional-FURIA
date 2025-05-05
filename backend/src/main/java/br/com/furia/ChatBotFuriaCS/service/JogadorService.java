package br.com.furia.ChatBotFuriaCS.service;

import br.com.furia.ChatBotFuriaCS.model.jogador.Jogador;
import br.com.furia.ChatBotFuriaCS.model.mapa_favorito.MapaFavorito;
import br.com.furia.ChatBotFuriaCS.model.redes_sociais.RedesSociais;
import br.com.furia.ChatBotFuriaCS.model.skin_favorita.SkinFavorita;
import br.com.furia.ChatBotFuriaCS.repository.JogadorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
//Classe service, oferece os serviços do banco de dados da classe Jogador
@Service
public class JogadorService {
    //repositorio e services que o Spring irá instânciar
    @Autowired
    private JogadorRepository repository;
    @Autowired
    private MapaFavoritoService mapaService;
    @Autowired
    private SkinFavoritaService skinService;
    @Autowired
    private RedesSociaisService redesService;


    //Metodo para salvar no banco de dados um jogador
    public Jogador salvar(Jogador jogador) {
        //Se o jogador recebido for diferente de null e não existir jogador com este nickName
        if ((jogador != null) && repository.findById(jogador.getNickName()).isEmpty()) {
            //Se sim, verifica se ele tem um mapaFavorito, adiciona o jogador a lista do mapa e salva o mapa primeiro
            if (jogador.getMapaFavorito() != null) {
                MapaFavorito mapa = null;
                //Para evitar duplicatas, verifica se o mapa já foi criado, se a lista estiver vazia quer dizer que não foi salvo
                if (mapaService.buscaPeloNome(jogador.getMapaFavorito().getNome()).isEmpty()) {
                    //Continua usando o mapa recebeido de Jogador
                    mapa = jogador.getMapaFavorito();
                    //salva o mapa
                    mapaService.salvar(mapa);
                } else{
                    //Pega a referencia do mapa salvo com o id que esta no banco de dados, coloca esse mapa no jogador
                    mapa = mapaService.buscaPeloNome(jogador.getMapaFavorito().getNome()).get(0);
                    jogador.setMapaFavorito(mapa);
                }
                //adiciona jogador na lista de mapa
                mapa.getJogadores().add(jogador);
            }

            //Verifica se ele tem um skinFavorita, adiciona o jogador a lista da skinFavorita e salva a skin primeiro
            if (jogador.getSkinFavorita() != null) {
                SkinFavorita skin = null;
                //Para evitar duplicatas, verifica se a skin já foi criada, se não foi então salva
                if (skinService.buscaPeloNomeEArma(jogador.getSkinFavorita().getNome(),
                        jogador.getSkinFavorita().getArma()).isEmpty()) {
                    //Continua usando o mapa recebeido de Jogador
                    skin = jogador.getSkinFavorita();
                    //salva o mapa
                    skinService.salvar(jogador.getSkinFavorita());
                } else{
                    //Pega a referencia da skin salva com o id que esta no banco de dados, coloca essa skin no jogador
                    skin = skinService.buscaPeloNomeEArma(jogador.getSkinFavorita().getNome(),
                            jogador.getSkinFavorita().getArma()).get(0);
                    jogador.setSkinFavorita(skin);
                    //Não precisa salvar pois ja esta no banco
                }
                //adiciona jogador na lista de skin
                skin.getJogadores().add(jogador);
            }
            //Verifica se ele tem um skinFavorita, adiciona o jogador a lista da skinFavorita e salva a skin primeiro
            if (jogador.getRedesSociais() != null) {
                RedesSociais redes = null;
                //Para evitar duplicatas, verifica se a rede já foi criada, se não foi então salva
                if (redesService.buscaPorTwitch(jogador.getRedesSociais().getTwitch()).isEmpty()) {
                    //Continua usando o mapa recebeido de Jogador
                    redes = jogador.getRedesSociais();
                    //salva a rede
                    redesService.salvar(jogador.getRedesSociais());
                } else{
                    //Pega a referencia da rede salva com o id que esta no banco de dados, coloca essa rede no jogador
                    redes = redesService.buscaPorTwitch(jogador.getRedesSociais().getTwitch()).get(0);
                    jogador.setRedesSociais(redes);
                    //Não precisa salvar pois ja esta no banco
                }
                //adiciona jogador em redes
                redes.setJogador(jogador);
            }
            return repository.save(jogador);
        }
        //Se não, o jogador é nulo ou nickName ja esta cadastrado, então não é possivel salvar, retornar nullo
        return null;
    }
    //Busca todos os jogadores e retorna tudo em lista
    public List<Jogador> buscarTodos(){
        return repository.findAll();
    }
    //Procura um jogador especifico pelo id, nesse caso pelo nickName, retorna em Optional, que pode ser verificado se contem jogador ou nada
    public Optional<Jogador> buscarPeloId(String nickName) {
        return repository.findById(nickName);
    }

}
