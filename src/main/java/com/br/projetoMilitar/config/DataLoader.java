package com.br.projetoMilitar.config;

import java.time.LocalDate;
import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.br.projetoMilitar.modeloBD.Atividades;
import com.br.projetoMilitar.modeloBD.Equipamentos;
import com.br.projetoMilitar.modeloBD.Insumos;
import com.br.projetoMilitar.modeloBD.MovimentacoesInsumos;
import com.br.projetoMilitar.modeloBD.Soldados;
import com.br.projetoMilitar.modeloBD.SoldadosEquipamentos;
import com.br.projetoMilitar.modeloBD.Usuarios;
import com.br.projetoMilitar.repositories.AtividadesRepository;
import com.br.projetoMilitar.repositories.EquipamentosRepository;
import com.br.projetoMilitar.repositories.InsumosRepository;
import com.br.projetoMilitar.repositories.MovimentacaoInsumosRepository;
import com.br.projetoMilitar.repositories.SoldadosEquipamentosRepository;
import com.br.projetoMilitar.repositories.SoldadosRepository;
import com.br.projetoMilitar.repositories.UsuariosRepository;

@Component
public class DataLoader implements CommandLineRunner {

    @Autowired
    private UsuariosRepository usuariosRepository;

    @Autowired
    private SoldadosRepository soldadosRepository;

    @Autowired
    private EquipamentosRepository equipamentosRepository;

    @Autowired
    private InsumosRepository insumosRepository;

    @Autowired
    private MovimentacaoInsumosRepository movimentacaoInsumosRepository;

    @Autowired
    private AtividadesRepository atividadesRepository;

    @Autowired
    private SoldadosEquipamentosRepository soldadosEquipamentosRepository;

    @Override
    public void run(String... args) throws Exception {
        System.out.println("=== INICIANDO CARREGAMENTO DE DADOS DO SGM-001 ===");

        // Limpa dados existentes - (primeiro as que têm foreign keys)
        // soldados_equipamentos depende de soldados, equipamentos e usuarios
        soldadosEquipamentosRepository.deleteAll();
        movimentacaoInsumosRepository.deleteAll();
        atividadesRepository.deleteAll();
        equipamentosRepository.deleteAll();
        soldadosRepository.deleteAll();
        insumosRepository.deleteAll();
        usuariosRepository.deleteAll();

        // Criar dados - ORDEM CORRETA (primeiro as tabelas pai)
        criarUsuarios();
        criarSoldados();
        criarEquipamentos();
        criarInsumos();
        criarMovimentacoes();          // depende de usuarios + insumos
        criarAtividades();             // depende de usuarios
        criarSoldadosEquipamentos();   // depende de soldados + equipamentos + usuarios

        System.out.println("=== DADOS CARREGADOS COM SUCESSO ===");
        System.out.println("Usuários: " + usuariosRepository.count());
        System.out.println("Soldados: " + soldadosRepository.count());
        System.out.println("Equipamentos: " + equipamentosRepository.count());
        System.out.println("Insumos: " + insumosRepository.count());
        System.out.println("Movimentações: " + movimentacaoInsumosRepository.count());
        System.out.println("Atividades: " + atividadesRepository.count());
        System.out.println("Atribuições Soldado-Equipamento: " + soldadosEquipamentosRepository.count());
    }

    private void criarUsuarios() {
        Usuarios admin = new Usuarios();
        admin.setNome("Coronel João Almeida");
        admin.setIdentificador("202600000000001");
        admin.setPatente("Coronel");
        admin.setAnoEntrada("2026");
        admin.setPerfil("ADMINISTRADOR");
        admin.setCpf("12345678901");
        admin.setTelefone("11988887777");
        admin.setEmail("coronel@exercito.def.br");
        admin.setSenha("admin123");
        usuariosRepository.save(admin);

        Usuarios operador1 = new Usuarios();
        operador1.setNome("Capitão Marcos Silva");
        operador1.setIdentificador("202600000000002");
        operador1.setPatente("Capitão");
        operador1.setAnoEntrada("2026");
        operador1.setPerfil("OPERADOR");
        operador1.setCpf("23456789012");
        operador1.setTelefone("11977776666");
        operador1.setEmail("marcos@exercito.def.br");
        operador1.setSenha("operador123");
        usuariosRepository.save(operador1);

        Usuarios operador2 = new Usuarios();
        operador2.setNome("Tenente Rafael Costa");
        operador2.setIdentificador("202600000000003");
        operador2.setPatente("Tenente");
        operador2.setAnoEntrada("2026");
        operador2.setPerfil("OPERADOR");
        operador2.setCpf("34567890123");
        operador2.setTelefone("11966665555");
        operador2.setEmail("rafael@exercito.def.br");
        operador2.setSenha("rafael123");
        usuariosRepository.save(operador2);

        System.out.println("Usuários criados com sucesso!");
    }

    private void criarSoldados() {
        Soldados soldado1 = new Soldados();
        soldado1.setNome("João Silva");
        soldado1.setIdentificador("202500000000001");
        soldado1.setPatente("Capitão");
        soldado1.setPelotao("SELVA");
        soldado1.setFuncao("Comandante");
        soldado1.setDataIngresso(LocalDate.of(2020, 1, 15));
        soldado1.setStatus("ATIVO");
        soldadosRepository.save(soldado1);

        Soldados soldado2 = new Soldados();
        soldado2.setNome("Pedro Santos");
        soldado2.setIdentificador("202500000000002");
        soldado2.setPatente("Tenente");
        soldado2.setPelotao("SELVA");
        soldado2.setFuncao("Instrutor de Sobrevivência");
        soldado2.setDataIngresso(LocalDate.of(2021, 8, 20));
        soldado2.setStatus("LICENCA");
        soldadosRepository.save(soldado2);

        Soldados soldado3 = new Soldados();
        soldado3.setNome("Carlos Souza");
        soldado3.setIdentificador("202500000000003");
        soldado3.setPatente("Tenente");
        soldado3.setPelotao("TANQUE");
        soldado3.setFuncao("Oficial de Operações");
        soldado3.setDataIngresso(LocalDate.of(2021, 3, 10));
        soldado3.setStatus("ATIVO");
        soldadosRepository.save(soldado3);

        Soldados soldado4 = new Soldados();
        soldado4.setNome("Ricardo Alves");
        soldado4.setIdentificador("202500000000004");
        soldado4.setPatente("Major");
        soldado4.setPelotao("TANQUE");
        soldado4.setFuncao("Comandante");
        soldado4.setDataIngresso(LocalDate.of(2018, 2, 14));
        soldado4.setStatus("ATIVO");
        soldadosRepository.save(soldado4);

        Soldados soldado5 = new Soldados();
        soldado5.setNome("Roberto Lima");
        soldado5.setIdentificador("202500000000005");
        soldado5.setPatente("Sargento");
        soldado5.setPelotao("JEEP");
        soldado5.setFuncao("Instrutor de Direção");
        soldado5.setDataIngresso(LocalDate.of(2019, 7, 22));
        soldado5.setStatus("ATIVO");
        soldadosRepository.save(soldado5);

        Soldados soldado6 = new Soldados();
        soldado6.setNome("André Costa");
        soldado6.setIdentificador("202500000000006");
        soldado6.setPatente("Capitão");
        soldado6.setPelotao("PARAQUEDISTA");
        soldado6.setFuncao("Comandante");
        soldado6.setDataIngresso(LocalDate.of(2020, 5, 18));
        soldado6.setStatus("ATIVO");
        soldadosRepository.save(soldado6);

        Soldados soldado7 = new Soldados();
        soldado7.setNome("Felipe Rocha");
        soldado7.setIdentificador("202500000000007");
        soldado7.setPatente("Tenente");
        soldado7.setPelotao("PARAQUEDISTA");
        soldado7.setFuncao("Instrutor de Salto");
        soldado7.setDataIngresso(LocalDate.of(2022, 1, 10));
        soldado7.setStatus("ATIVO");
        soldadosRepository.save(soldado7);

        System.out.println("Soldados criados com sucesso!");
    }

    private void criarEquipamentos() {
        Equipamentos equip1 = new Equipamentos();
        equip1.setNome("FACÃO");
        equip1.setCodigo("SEL-001");
        equip1.setQuantidade(50);
        equip1.setFabricante("Tramontina");
        equip1.setPeso(0.8f);
        equip1.setDescricao("FACÃO DE MATO PARA SELVA");
        equip1.setStatus("DISPONIVEL");
        equip1.setTipoPelotao("selva");
        equipamentosRepository.save(equip1);

        Equipamentos equip2 = new Equipamentos();
        equip2.setNome("CANTIL");
        equip2.setCodigo("SEL-002");
        equip2.setQuantidade(100);
        equip2.setFabricante("Camelbak");
        equip2.setPeso(0.3f);
        equip2.setDescricao("CANTIL 2L EM ALUMÍNIO");
        equip2.setStatus("DISPONIVEL");
        equip2.setTipoPelotao("selva");
        equipamentosRepository.save(equip2);

        Equipamentos equip3 = new Equipamentos();
        equip3.setNome("REDE");
        equip3.setCodigo("SEL-003");
        equip3.setQuantidade(30);
        equip3.setFabricante("Rede Militar");
        equip3.setPeso(1.5f);
        equip3.setDescricao("REDE DE CAMPANHA");
        equip3.setStatus("MANUTENCAO");
        equip3.setTipoPelotao("selva");
        equipamentosRepository.save(equip3);

        Equipamentos equip4 = new Equipamentos();
        equip4.setNome("MUNIÇÃO 120mm");
        equip4.setCodigo("TAN-001");
        equip4.setQuantidade(200);
        equip4.setFabricante("IMBEL");
        equip4.setPeso(25.0f);
        equip4.setDescricao("MUNIÇÃO PARA TANQUE");
        equip4.setStatus("DISPONIVEL");
        equip4.setTipoPelotao("tanque");
        equipamentosRepository.save(equip4);

        Equipamentos equip5 = new Equipamentos();
        equip5.setNome("RÁDIO TÁTICO");
        equip5.setCodigo("JEEP-001");
        equip5.setQuantidade(15);
        equip5.setFabricante("Motorola");
        equip5.setPeso(2.5f);
        equip5.setDescricao("RÁDIO DE COMUNICAÇÃO");
        equip5.setStatus("MANUTENCAO");
        equip5.setTipoPelotao("jeep");
        equipamentosRepository.save(equip5);

        Equipamentos equip6 = new Equipamentos();
        equip6.setNome("PARAQUEDAS");
        equip6.setCodigo("PAR-001");
        equip6.setQuantidade(30);
        equip6.setFabricante("ParaTech");
        equip6.setPeso(12.0f);
        equip6.setDescricao("PARAQUEDAS TÁTICO");
        equip6.setStatus("DISPONIVEL");
        equip6.setTipoPelotao("paraquedista");
        equipamentosRepository.save(equip6);

        System.out.println("Equipamentos criados com sucesso!");
    }

    private void criarInsumos() {
        Insumos insumo1 = new Insumos();
        insumo1.setNome("Munição 5.56mm");
        insumo1.setCategoria("MUNICAO");
        insumo1.setQuantidade(5000);
        insumo1.setQuantidadeMinima(1000);
        insumo1.setUnidade("UN");
        insumo1.setLocalizacao("Arsenal Central");
        insumosRepository.save(insumo1);

        Insumos insumo2 = new Insumos();
        insumo2.setNome("Munição 9mm");
        insumo2.setCategoria("MUNICAO");
        insumo2.setQuantidade(8000);
        insumo2.setQuantidadeMinima(1500);
        insumo2.setUnidade("UN");
        insumo2.setLocalizacao("Arsenal Central");
        insumosRepository.save(insumo2);

        Insumos insumo3 = new Insumos();
        insumo3.setNome("Combustível Diesel");
        insumo3.setCategoria("COMBUSTIVEL");
        insumo3.setQuantidade(800);
        insumo3.setQuantidadeMinima(500);
        insumo3.setUnidade("L");
        insumo3.setLocalizacao("Depósito de Combustível");
        insumosRepository.save(insumo3);

        Insumos insumo4 = new Insumos();
        insumo4.setNome("Ração Operacional");
        insumo4.setCategoria("ALIMENTACAO");
        insumo4.setQuantidade(150);
        insumo4.setQuantidadeMinima(200);
        insumo4.setUnidade("KG");
        insumo4.setLocalizacao("Cozinha Central");
        insumosRepository.save(insumo4);

        Insumos insumo5 = new Insumos();
        insumo5.setNome("Kit Médico");
        insumo5.setCategoria("MEDICAMENTO");
        insumo5.setQuantidade(25);
        insumo5.setQuantidadeMinima(10);
        insumo5.setUnidade("PC");
        insumo5.setLocalizacao("Enfermaria");
        insumosRepository.save(insumo5);

        System.out.println("Insumos criados com sucesso!");
    }

    private void criarMovimentacoes() {
        // Buscar usuários pelo identificador
        Usuarios admin = usuariosRepository.findByIdentificador("202600000000001").orElse(null);
        Usuarios operador = usuariosRepository.findByIdentificador("202600000000002").orElse(null);

        if (admin == null || operador == null) {
            System.out.println("Erro: Usuários não encontrados para criar movimentações");
            return;
        }

        // Buscar insumos pelo nome
        Insumos munição556 = insumosRepository.findByNome("Munição 5.56mm").orElse(null);
        Insumos combustivel = insumosRepository.findByNome("Combustível Diesel").orElse(null);
        Insumos racão = insumosRepository.findByNome("Ração Operacional").orElse(null);

        if (munição556 == null || combustivel == null || racão == null) {
            System.out.println("Erro: Insumos não encontrados para criar movimentações");
            System.out.println("Munição 5.56mm: " + (munição556 != null));
            System.out.println("Combustível Diesel: " + (combustivel != null));
            System.out.println("Ração Operacional: " + (racão != null));
            return;
        }

        // Movimentação 1: Entrada de munição
        MovimentacoesInsumos mov1 = new MovimentacoesInsumos();
        mov1.setInsumoId(munição556.getId());
        mov1.setTipo("ENTRADA");
        mov1.setQuantidade(1000);
        mov1.setObservacao("Compra mensal de munição");
        mov1.setUsuarioId(admin.getId());
        mov1.setData(LocalDateTime.now().minusDays(5));
        movimentacaoInsumosRepository.save(mov1);

        // Movimentação 2: Saída de combustível
        MovimentacoesInsumos mov2 = new MovimentacoesInsumos();
        mov2.setInsumoId(combustivel.getId());
        mov2.setTipo("SAIDA");
        mov2.setQuantidade(100);
        mov2.setObservacao("Abastecimento viaturas");
        mov2.setUsuarioId(operador.getId());
        mov2.setData(LocalDateTime.now().minusDays(2));
        movimentacaoInsumosRepository.save(mov2);

        // Movimentação 3: Saída de ração
        MovimentacoesInsumos mov3 = new MovimentacoesInsumos();
        mov3.setInsumoId(racão.getId());
        mov3.setTipo("SAIDA");
        mov3.setQuantidade(50);
        mov3.setObservacao("Distribuição para tropa");
        mov3.setUsuarioId(operador.getId());
        mov3.setData(LocalDateTime.now().minusDays(1));
        movimentacaoInsumosRepository.save(mov3);

        System.out.println("Movimentações criadas com sucesso!");
    }

    private void criarAtividades() {
        Usuarios admin = usuariosRepository.findByIdentificador("202600000000001").orElse(null);
        Usuarios operador = usuariosRepository.findByIdentificador("202600000000002").orElse(null);

        if (admin == null || operador == null) {
            System.out.println("Erro: Usuários não encontrados para criar atividades");
            return;
        }

        Atividades atv1 = new Atividades();
        atv1.setUsuarioId(admin.getId());
        atv1.setAcao("Login");
        atv1.setDescricao("Usuário administrador acessou o sistema");
        atv1.setTipo("login");
        atv1.setData(LocalDateTime.now().minusDays(1));
        atividadesRepository.save(atv1);

        Atividades atv2 = new Atividades();
        atv2.setUsuarioId(operador.getId());
        atv2.setAcao("Consulta");
        atv2.setDescricao("Consulta de equipamentos do pelotão Selva");
        atv2.setTipo("consulta");
        atv2.setData(LocalDateTime.now().minusHours(5));
        atividadesRepository.save(atv2);

        Atividades atv3 = new Atividades();
        atv3.setUsuarioId(admin.getId());
        atv3.setAcao("Cadastro");
        atv3.setDescricao("Cadastro de novo soldado");
        atv3.setTipo("cadastro");
        atv3.setData(LocalDateTime.now().minusHours(3));
        atividadesRepository.save(atv3);

        System.out.println("Atividades criadas com sucesso!");
    }

    private void criarSoldadosEquipamentos() {
        // Buscar usuário admin (quem está registrando a atribuição)
        Usuarios admin = usuariosRepository.findByIdentificador("202600000000001").orElse(null);
        if (admin == null) {
            System.out.println("Erro: Usuário admin não encontrado para criar atribuições");
            return;
        }

        // Buscar soldados por identificador
        Soldados soldadoJoaoSilva   = soldadosRepository.findByIdentificador("202500000000001").orElse(null); // SELVA
        Soldados soldadoCarlosSouza = soldadosRepository.findByIdentificador("202500000000003").orElse(null); // TANQUE
        Soldados soldadoAndreCosta  = soldadosRepository.findByIdentificador("202500000000006").orElse(null); // PARAQUEDISTA
        Soldados soldadoRobertoLima = soldadosRepository.findByIdentificador("202500000000005").orElse(null); // JEEP

        // Buscar equipamentos por código
        Equipamentos facao       = equipamentosRepository.findByCodigo("SEL-001").orElse(null);
        Equipamentos cantil      = equipamentosRepository.findByCodigo("SEL-002").orElse(null);
        Equipamentos municao120  = equipamentosRepository.findByCodigo("TAN-001").orElse(null);
        Equipamentos radioTatico = equipamentosRepository.findByCodigo("JEEP-001").orElse(null);
        Equipamentos paraquedas  = equipamentosRepository.findByCodigo("PAR-001").orElse(null);

        if (soldadoJoaoSilva == null || soldadoCarlosSouza == null
                || soldadoAndreCosta == null || soldadoRobertoLima == null
                || facao == null || cantil == null || municao120 == null
                || radioTatico == null || paraquedas == null) {
            System.out.println("Erro: Soldados ou equipamentos não encontrados para criar atribuições");
            return;
        }

        // Atribuição 1: João Silva (SELVA) - Facão
        SoldadosEquipamentos se1 = new SoldadosEquipamentos();
        se1.setSoldadoId(soldadoJoaoSilva.getId());
        se1.setEquipamentoId(facao.getId());
        se1.setQuantidade(1);
        se1.setObservacao("Equipamento padrão de selva");
        se1.setUsuarioId(admin.getId());
        se1.setDataAtribuicao(LocalDateTime.now().minusDays(10));
        soldadosEquipamentosRepository.save(se1);

        // Atribuição 2: João Silva (SELVA) - Cantil
        SoldadosEquipamentos se2 = new SoldadosEquipamentos();
        se2.setSoldadoId(soldadoJoaoSilva.getId());
        se2.setEquipamentoId(cantil.getId());
        se2.setQuantidade(1);
        se2.setObservacao("Cantil operacional");
        se2.setUsuarioId(admin.getId());
        se2.setDataAtribuicao(LocalDateTime.now().minusDays(10));
        soldadosEquipamentosRepository.save(se2);

        // Atribuição 3: Carlos Souza (TANQUE) - Munição 120mm
        SoldadosEquipamentos se3 = new SoldadosEquipamentos();
        se3.setSoldadoId(soldadoCarlosSouza.getId());
        se3.setEquipamentoId(municao120.getId());
        se3.setQuantidade(20);
        se3.setObservacao("Lote de treinamento de tiro");
        se3.setUsuarioId(admin.getId());
        se3.setDataAtribuicao(LocalDateTime.now().minusDays(5));
        soldadosEquipamentosRepository.save(se3);

        // Atribuição 4: Roberto Lima (JEEP) - Rádio Tático
        SoldadosEquipamentos se4 = new SoldadosEquipamentos();
        se4.setSoldadoId(soldadoRobertoLima.getId());
        se4.setEquipamentoId(radioTatico.getId());
        se4.setQuantidade(1);
        se4.setObservacao("Rádio para instrução de direção");
        se4.setUsuarioId(admin.getId());
        se4.setDataAtribuicao(LocalDateTime.now().minusDays(3));
        soldadosEquipamentosRepository.save(se4);

        // Atribuição 5: André Costa (PARAQUEDISTA) - Paraquedas
        SoldadosEquipamentos se5 = new SoldadosEquipamentos();
        se5.setSoldadoId(soldadoAndreCosta.getId());
        se5.setEquipamentoId(paraquedas.getId());
        se5.setQuantidade(1);
        se5.setObservacao("Paraquedas principal de salto");
        se5.setUsuarioId(admin.getId());
        se5.setDataAtribuicao(LocalDateTime.now().minusDays(2));
        soldadosEquipamentosRepository.save(se5);

        System.out.println("Atribuições Soldado-Equipamento criadas com sucesso!");
    }
}