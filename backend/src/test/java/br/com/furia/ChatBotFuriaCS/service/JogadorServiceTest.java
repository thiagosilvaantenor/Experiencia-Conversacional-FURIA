package br.com.furia.ChatBotFuriaCS.service;

import br.com.furia.ChatBotFuriaCS.model.jogador.Jogador;
import br.com.furia.ChatBotFuriaCS.model.mapa_favorito.MapaFavorito;
import br.com.furia.ChatBotFuriaCS.model.redes_sociais.RedesSociais;
import br.com.furia.ChatBotFuriaCS.model.skin_favorita.SkinFavorita;
import br.com.furia.ChatBotFuriaCS.repository.JogadorRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class JogadorServiceTest {

    //Usa Mock, para criar uma instância FAKE, como se tivesse a instancia com os métodos vazios
    @Mock
    private JogadorRepository jogadorRepository;
    @Mock
    private MapaFavoritoService mapaService;
    @Mock
    private SkinFavoritaService skinService;
    @Mock
    private RedesSociaisService redesService;

    //Injeta os @Mocks acima e cria uma instância real
    @InjectMocks
    private JogadorService jogadorService;

    @BeforeEach
    void setUp(){
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("Deve salvar o jogador com sucesso, quando tudo estiver OK")
    void deveCriarJogadorComSucesso() {
        //DADO / GIVEN = Preparamos todos os dados de entrada e o comportamento dos mocks.
        LocalDate dt = LocalDate.of(1991,5,30);

        Jogador dados = new Jogador("FalleN", "Gabriel Toledo", dt,
                new RedesSociais(1, "https://www.twitch.tv/gafallen", "https://www.instagram.com/fallen",
                        "https://www.youtube.com/c/Fallen", null),
                new SkinFavorita(1, "Dragon Lore", "AWP", new HashSet<>()),
                new MapaFavorito(1, "DUST 2", new HashSet<>()));

        // 2. Defina o comportamento dos mocks quando eles forem chamados pelo serviço.
        // Dizemos ao Mockito: "Quando o método 'save' do repositório for chamado com QUALQUER
        // objeto da classe X, retorne este objeto salvo que preparamos".

        //Quando procurado o jogador pelo Id e for verificado se o Optional está vazio, retorna TRUE
        when(jogadorRepository.findById("FalleN")).thenReturn(Optional.empty());
        //Garante que o mapa vai ser criado e salvo, quando usar o método de busca retornará uma lista vazia
        when(mapaService.buscaPeloNome("DUST 2")).thenReturn(List.of());
        when(mapaService.salvar(any(MapaFavorito.class))).thenAnswer(invocation -> invocation.getArgument(0));
        //Garante que a skin vai ser criado e salvo, quando usar o método de busca retornará uma lista vazia
        when(skinService.buscaPeloNomeEArma("Dragon Lore", "AWP")).thenReturn(List.of());
        when(skinService.salvar(any(SkinFavorita.class))).thenAnswer(invocation -> invocation.getArgument(0));
        //Garante que as redesSociais vai ser criado e salvo, quando usar o método de busca retornará uma lista vazia
        when(redesService.buscaPorTwitch("https://www.twitch.tv/gafallen")).thenReturn(List.of());
        when(redesService.salvar(any(RedesSociais.class))).thenAnswer(invocation -> invocation.getArgument(0));

        when(jogadorRepository.save(any(Jogador.class))).thenAnswer(invocation -> {
            Jogador jogador = invocation.getArgument(0);
            return jogador;
        });

        // WHEN (Quando): Executamos o método que queremos testar.
        Jogador resultado = jogadorService.salvar(dados);

        // THEN (Então): Verificamos se o resultado foi o esperado.

        // 1. Verificações simples no objeto retornado
        assertNotNull(resultado);
        assertEquals("FalleN", resultado.getNickName());
        assertEquals("Gabriel Toledo", resultado.getNome());
        assertNotNull(resultado.getRedesSociais());
        assertEquals("https://www.twitch.tv/gafallen", resultado.getRedesSociais().getTwitch());
        assertEquals("DUST 2", resultado.getMapaFavorito().getNome());

        // Aqui, nós garantimos que o serviço interagiu com seus mocks da forma correta.

        // ArgumentCaptor é uma ferramenta poderosa para "capturar" o objeto exato
        // que foi passado para um método do mock.
        ArgumentCaptor<Jogador> jogadorArgumentCaptor = ArgumentCaptor.forClass(Jogador.class);

        // Verificamos se o método 'salvar' de cada repositório foi chamado exatamente 1 vez.
        verify(redesService, times(1)).salvar(any(RedesSociais.class));
        verify(mapaService, times(1)).salvar(any(MapaFavorito.class));
        verify(skinService, times(1)).salvar(any(SkinFavorita.class));

        // Agora, a verificação final: O objeto Jogador foi montado corretamente antes de salvar?
        // Verificamos se o save do jogadorRepository foi chamado 1 vez e capturamos o argumento.
        verify(jogadorRepository, times(1)).save(jogadorArgumentCaptor.capture());

        // Pegamos o objeto que foi capturado.
        Jogador jogadorCapturado = jogadorArgumentCaptor.getValue();

        // Fazemos asserções sobre o objeto que o serviço tentou salvar.
        // Isso prova que sua lógica de montagem de objeto está correta!
        assertNotNull(jogadorCapturado);
        assertEquals("FalleN", jogadorCapturado.getNickName());
        assertNotNull(jogadorCapturado.getRedesSociais());
        assertEquals("https://www.instagram.com/fallen", jogadorCapturado.getRedesSociais().getInstagram());
        assertNotNull(jogadorCapturado.getMapaFavorito());
        assertEquals("DUST 2", jogadorCapturado.getMapaFavorito().getNome());
        assertNotNull(jogadorCapturado.getSkinFavorita());
        assertEquals("Dragon Lore", jogadorCapturado.getSkinFavorita().getNome());
    }

    @Test
    @DisplayName("Deve salvar o jogador com sucesso e reaproveitar o Mapa já salvo, sem criar duplicata de MapaFavorito")
    void deveReutilizarMapaExistenteAoCriarJogador() {
        //DADO / GIVEN = Preparamos todos os dados de entrada e o comportamento dos mocks.
        LocalDate dt = LocalDate.of(1991,5,30);

        Jogador dados = new Jogador("FalleN", "Gabriel Toledo", dt,
                new RedesSociais(1, "https://www.twitch.tv/gafallen", "https://www.instagram.com/fallen",
                        "https://www.youtube.com/c/Fallen", null),
                new SkinFavorita(1, "Dragon Lore", "AWP", new HashSet<>()),
                new MapaFavorito(1, "DUST 2", new HashSet<>()));

        MapaFavorito mapaExistente = new MapaFavorito("DUST 2");

        //Quando procurado o jogador pelo Id e for verificado se o Optional está vazio, retorna TRUE
        when(jogadorRepository.findById(any())).thenReturn(Optional.empty());
        // Simulamos que o mapa "DUST 2" JÁ EXISTE no banco.
        when(mapaService.buscaPeloNome("DUST 2")).thenReturn(List.of(mapaExistente));
        //when(mapaService.salvar(any(MapaFavorito.class))).thenAnswer(invocation -> invocation.getArgument(0));
        //Garante que a skin vai ser criado e salvo
        when(skinService.buscaPeloNomeEArma(any(), any())).thenReturn(List.of());
        when(skinService.salvar(any(SkinFavorita.class))).thenAnswer(invocation -> invocation.getArgument(0));
        //Garante que as redesSociais vai ser criado e salvo
        when(redesService.buscaPorTwitch(any())).thenReturn(List.of());
        when(redesService.salvar(any(RedesSociais.class))).thenAnswer(invocation -> invocation.getArgument(0));

        when(jogadorRepository.save(any(Jogador.class))).thenAnswer(invocation -> {
            Jogador jogador = invocation.getArgument(0);
            return jogador;
        });

        // WHEN
        jogadorService.salvar(dados);

        // THEN
        // Verificamos que o serviço NUNCA tentou salvar um novo mapa, pois ele reutilizou o existente.
        verify(mapaService, never()).salvar(any(MapaFavorito.class));

        // Verificamos que os outros saves ocorreram normalmente.
        verify(redesService, times(1)).salvar(any(RedesSociais.class));
        verify(jogadorRepository, times(1)).save(any(Jogador.class));
    }
}

