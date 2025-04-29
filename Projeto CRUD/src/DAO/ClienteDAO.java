package DAO;

import Factory.ConnectionFactory;
import Modelo.Cliente;
import java.sql.*;

public class ClienteDAO {
    private Connection connection;
    ConnectionFactory ConnectionF = new ConnectionFactory();
    
    public ClienteDAO() {
        this.connection = ConnectionF.getConnection();
    }
    
    public void adicionar(Cliente cliente) {
        String sql = "INSERT INTO cliente(cliente_nome, cliente_cpf, cliente_telefone, cliente_endereco, cliente_data, cliente_email) VALUES(?,?,?,?,?,?)";
        
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setString(1, cliente.getNome());
            stmt.setString(2, cliente.getCpf());
            stmt.setString(3, cliente.getTelefone());
            stmt.setString(4, cliente.getEndereco());
            stmt.setString(5, cliente.getData());
            stmt.setString(6, cliente.getEmail());
            stmt.execute();
            stmt.close();
        } catch (SQLException excecao) {
            throw new RuntimeException(excecao);
        }
    }
    
    public static void main(String[] args){
    }
}
