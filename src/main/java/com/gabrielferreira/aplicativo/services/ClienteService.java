package com.gabrielferreira.aplicativo.services;

import com.gabrielferreira.aplicativo.dominio.Cidade;
import com.gabrielferreira.aplicativo.dominio.Cliente;
import com.gabrielferreira.aplicativo.dominio.Endereco;
import com.gabrielferreira.aplicativo.dominio.enums.TipoCliente;
import com.gabrielferreira.aplicativo.dto.ClienteDTO;
import com.gabrielferreira.aplicativo.dto.ClienteNewDTO;
import com.gabrielferreira.aplicativo.repositories.ClienteRepository;
import com.gabrielferreira.aplicativo.repositories.EnderecoRepository;
import com.gabrielferreira.aplicativo.services.exceptions.DataIntegrityException;
import com.gabrielferreira.aplicativo.services.exceptions.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class ClienteService {

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private EnderecoRepository enderecoRepository;

    @Autowired
    private BCryptPasswordEncoder senhaEncodada;

    public Cliente obter(Integer id) {
        Optional<Cliente> cliente = clienteRepository.findById(id);
        return cliente.orElseThrow(() -> new ObjectNotFoundException("Cliente não encontrado! Id: " + id +
                ", Tipo: " + Cliente.class.getName()));
    }

    @Transactional
    public Cliente criar(Cliente cliente) {
        cliente.setId(null);
        cliente = clienteRepository.save(cliente);
        enderecoRepository.saveAll(cliente.getEnderecos());
        return cliente;
    }

    public Cliente atualizar(Cliente cliente) {
        Cliente novoCliente = obter(cliente.getId());
        atualizarDados(novoCliente, cliente);
        return clienteRepository.save(novoCliente);
    }

    public void deletar(Integer id) {
        obter(id);
        try {
            clienteRepository.deleteById(id);
        } catch (DataIntegrityViolationException e) {
            throw new DataIntegrityException("Não é possível excluir um cliente com pedido");
        }

    }

    public List<Cliente> obter() {
        return clienteRepository.findAll();
    }

    public Page<Cliente> obterPagina(Integer page, Integer linesPerPage, String direction, String orderBy) {
        PageRequest pageRequest = PageRequest.of(page, linesPerPage, Sort.Direction.valueOf(direction), orderBy);
        return clienteRepository.findAll(pageRequest);
    }

    public Cliente converteDTO(ClienteDTO clienteDTO) {
        return new Cliente(clienteDTO.getId(), clienteDTO.getNome(), clienteDTO.getEmail(), null,
                null, null);
    }

    public Cliente converteDTO(ClienteNewDTO clienteNewDTO) {
        Cliente cliente = new Cliente(null, clienteNewDTO.getNome(), clienteNewDTO.getEmail(),
                clienteNewDTO.getCpfOuCnpj(), TipoCliente.converterParaEnum(clienteNewDTO.getTipo()),
                senhaEncodada.encode(clienteNewDTO.getSenha()));

        Cidade cidade = new Cidade(clienteNewDTO.getCidadeId(), null, null);

        Endereco endereco = new Endereco(null, clienteNewDTO.getLogradouro(), clienteNewDTO.getNumero(),
                clienteNewDTO.getComplemento(), clienteNewDTO.getBairro(), clienteNewDTO.getCep(), cliente, cidade);

        cliente.getEnderecos().add(endereco);

        cliente.getTelefones().add(clienteNewDTO.getTelefone1());

        if (clienteNewDTO.getTelefone2() != null) {
            cliente.getTelefones().add(clienteNewDTO.getTelefone2());
        }

        if (clienteNewDTO.getTelefone3() != null) {
            cliente.getTelefones().add(clienteNewDTO.getTelefone3());
        }

        return cliente;
    }

    private void atualizarDados(Cliente novoCliente, Cliente cliente) {
        novoCliente.setNome(cliente.getNome());
        novoCliente.setEmail(cliente.getEmail());
    }
}
