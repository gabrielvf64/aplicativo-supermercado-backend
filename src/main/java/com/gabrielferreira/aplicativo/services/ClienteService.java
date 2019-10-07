package com.gabrielferreira.aplicativo.services;

import com.gabrielferreira.aplicativo.dominio.Cidade;
import com.gabrielferreira.aplicativo.dominio.Cliente;
import com.gabrielferreira.aplicativo.dominio.Endereco;
import com.gabrielferreira.aplicativo.dominio.enums.Perfil;
import com.gabrielferreira.aplicativo.dominio.enums.TipoCliente;
import com.gabrielferreira.aplicativo.dto.ClienteDTO;
import com.gabrielferreira.aplicativo.dto.ClienteNewDTO;
import com.gabrielferreira.aplicativo.repositories.ClienteRepository;
import com.gabrielferreira.aplicativo.repositories.EnderecoRepository;
import com.gabrielferreira.aplicativo.seguranca.Usuario;
import com.gabrielferreira.aplicativo.services.exceptions.AuthorizationException;
import com.gabrielferreira.aplicativo.services.exceptions.DataIntegrityException;
import com.gabrielferreira.aplicativo.services.exceptions.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.awt.image.BufferedImage;
import java.net.URI;
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

    @Autowired
    private AmazonS3Service amazonS3Service;

    @Autowired
    private ImageService imageService;

    @Value("${img.prefix.client.profile}")
    private String prefix;

    public Cliente obter(Integer id) {

        Usuario usuario = UserService.authenticated();
        if (usuario == null || !usuario.hasRole(Perfil.ADMIN) && !id.equals(usuario.getId())) {
            throw new AuthorizationException("Acesso negado");
        }

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

    public URI uploadFotoPerfil(MultipartFile multipartFile) {
        Usuario usuario = UserService.authenticated();

        if (usuario == null) {
            throw new AuthorizationException("Acesso negado");
        }

        BufferedImage jpgImage = imageService.getJpgImageFromFile(multipartFile);
        String fileName = prefix + usuario.getId() + ".jpg";

        return amazonS3Service.uploadArquivo(fileName, imageService.getInputStream(jpgImage, "jpg"),
                "image");
    }

    private void atualizarDados(Cliente novoCliente, Cliente cliente) {
        novoCliente.setNome(cliente.getNome());
        novoCliente.setEmail(cliente.getEmail());
    }
}
