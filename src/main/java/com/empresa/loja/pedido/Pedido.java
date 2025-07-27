package com.empresa.loja.pedido;

import com.empresa.loja.cliente.Cliente;
import com.empresa.loja.produto.Produto;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "pedidos")
public class Pedido {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String numero;

    @Column(nullable = false, name = "data_pedido")
    private LocalDateTime dataPedido;
    
    @Column(name = "valor_total", nullable = false, precision = 10, scale = 2)
    private BigDecimal valorTotal;
    
    @Enumerated(EnumType.STRING)
    private StatusPedido status;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cliente_id",  nullable = false)
    private Cliente cliente;

    @OneToMany(mappedBy = "pedido", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @Column(nullable = false)
    private List<ItemPedido> itens = new ArrayList<>();

    public Pedido() {
    }

    public Pedido(LocalDateTime dataPedido, BigDecimal valorTotal, Cliente cliente, StatusPedido status,
            List<ItemPedido> itens) {
        this.dataPedido = dataPedido;
        this.valorTotal = valorTotal;
        this.cliente = cliente;
        this.status = status;
        this.itens = itens;
    }

    public void adicionarItem(Produto produto, int quantidade){
        ItemPedido itemPedido = new ItemPedido();
        itemPedido.setPedido(this);
        itemPedido.setProduto(produto);
        itemPedido.setQuantidade(quantidade);
        itemPedido.setPrecoUnitario(produto.getPreco());
        itemPedido.calcularSubtotal();
    }

    public void removerItem(ItemPedido item){
        this.itens.remove(item);
        item.setPedido(null);
        calcularValorTotal();
    }

    private void calcularValorTotal(){
        this.valorTotal = itens.stream()
                .map(ItemPedido::getSubtotal)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public Long getId() {
        return id;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public LocalDateTime getDataPedido() {
        return dataPedido;
    }

    public void setDataPedido(LocalDateTime dataPedido) {
        this.dataPedido = dataPedido;
    }

    public BigDecimal getValorTotal() {
        return valorTotal;
    }

    public StatusPedido getStatus() {
        return status;
    }

    public void setStatus(StatusPedido status) {
        this.status = status;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }
}
