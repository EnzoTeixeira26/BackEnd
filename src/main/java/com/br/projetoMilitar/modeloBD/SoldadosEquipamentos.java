package com.br.projetoMilitar.modeloBD;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "soldados_equipamentos")
public class SoldadosEquipamentos {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idsoldadoequipamento")
    private Long idSoldadoEquipamento;

    @Column(name = "soldado_id", nullable = false)
    private Long soldadoId;

    @Column(name = "equipamento_id", nullable = false)
    private Long equipamentoId;

    @Column(name = "quantidade", nullable = false)
    private Integer quantidade;

    @Column(name = "data_atribuicao")
    private LocalDateTime dataAtribuicao;

    @Column(name = "observacao")
    private String observacao;

    @Column(name = "usuario_id")
    private Long usuarioId;

    // Getters e Setters

    public Long getIdSoldadoEquipamento() {
        return idSoldadoEquipamento;
    }

    public void setIdSoldadoEquipamento(Long idSoldadoEquipamento) {
        this.idSoldadoEquipamento = idSoldadoEquipamento;
    }

    public Long getSoldadoId() {
        return soldadoId;
    }

    public void setSoldadoId(Long soldadoId) {
        this.soldadoId = soldadoId;
    }

    public Long getEquipamentoId() {
        return equipamentoId;
    }

    public void setEquipamentoId(Long equipamentoId) {
        this.equipamentoId = equipamentoId;
    }

    public Integer getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(Integer quantidade) {
        this.quantidade = quantidade;
    }

    public LocalDateTime getDataAtribuicao() {
        return dataAtribuicao;
    }

    public void setDataAtribuicao(LocalDateTime dataAtribuicao) {
        this.dataAtribuicao = dataAtribuicao;
    }

    public String getObservacao() {
        return observacao;
    }

    public void setObservacao(String observacao) {
        this.observacao = observacao;
    }

    public Long getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(Long usuarioId) {
        this.usuarioId = usuarioId;
    }
}