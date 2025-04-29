package Modelo;

import Factory.ConnectionFactory;
import java.sql.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class Verificacoes {
    private Connection connection;
    ConnectionFactory ConnectionF = new ConnectionFactory();
    
    public Verificacoes() {
        this.connection = ConnectionF.getConnection();
    }
    
    // Outras funções de validação, como verifCPF e CPFSQL, podem continuar aqui...

    public void capturaSQL(String nome, String cpf, JTable tabelaClientes) {
        String sql = "SELECT * FROM cliente WHERE cliente_nome = ? OR cliente_cpf = ?";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, nome);
            stmt.setString(2, cpf);

            ResultSet rs = stmt.executeQuery();

            // Modificando o modelo da tabela para garantir que ID e CPF não sejam editáveis
            DefaultTableModel model = new DefaultTableModel() {
                @Override
                public boolean isCellEditable(int row, int column) {
                    // Não permite editar o ID (coluna 0) nem o CPF (coluna 2)
                    return column != 0 && column != 2;
                }
            };

            model.addColumn("ID");
            model.addColumn("Nome");
            model.addColumn("CPF");
            model.addColumn("Telefone");
            model.addColumn("Endereço");
            model.addColumn("Data");
            model.addColumn("Email");

            while (rs.next()) {
                model.addRow(new Object[]{
                    rs.getInt("cliente_id"),
                    rs.getString("cliente_nome"),
                    rs.getString("cliente_cpf"),
                    rs.getString("cliente_telefone"),
                    rs.getString("cliente_endereco"),
                    rs.getString("cliente_data"),
                    rs.getString("cliente_email")
                });
            }

            tabelaClientes.setModel(model);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void geralSQL(JTable tabelaClientes) {
    String sql = "SELECT * FROM cliente";  

    try (PreparedStatement stmt = connection.prepareStatement(sql)) {
        ResultSet rs = stmt.executeQuery();

        DefaultTableModel model = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return column != 0 && column != 2;
            }
        };

        model.addColumn("ID");
        model.addColumn("Nome");
        model.addColumn("CPF");
        model.addColumn("Telefone");
        model.addColumn("Endereço");
        model.addColumn("Data");
        model.addColumn("Email");

        while (rs.next()) {
            Object[] row = new Object[7]; 
            row[0] = rs.getInt("cliente_id");
            row[1] = rs.getString("cliente_nome");
            row[2] = rs.getString("cliente_cpf");
            row[3] = rs.getString("cliente_telefone");
            row[4] = rs.getString("cliente_endereco");
            row[5] = rs.getString("cliente_data");
            row[6] = rs.getString("cliente_email");

            model.addRow(row);
        }

        tabelaClientes.setModel(model);

    } catch (SQLException e) {
        e.printStackTrace();
    }
}

    public boolean dataNasc(String data){
        data = data.replace("-", "");
    
        if (data.length() != 8) {
            return false;
        }

        try {
            int ano = Integer.parseInt(data.substring(0, 4));
            int mes = Integer.parseInt(data.substring(4, 6));
            int dia = Integer.parseInt(data.substring(6, 8));

            if (mes < 1 || mes > 12) {
                return false;
            }

            if (dia < 1 || dia > 31) {
                return false;
            }

            if ((mes == 4 || mes == 6 || mes == 9 || mes == 11) && dia > 30) {
                return false;
            }

            boolean bissexto = (ano % 4 == 0 && ano % 100 != 0) || (ano % 400 == 0);
            if (mes == 2 && ((bissexto && dia > 29) || (!bissexto && dia > 28))) {
                return false;
            }
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public void atualizarBancoDeDados(int id, String nome, String cpf, String telefone, String endereco, String data, String email) {
        String sql = "UPDATE cliente SET cliente_nome = ?, cliente_telefone = ?, cliente_endereco = ?, cliente_data = ?, cliente_email = ? WHERE cliente_id = ?";
                
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, nome);
            stmt.setString(2, telefone);
            stmt.setString(3, endereco);
            stmt.setString(4, data);
            stmt.setString(5, email);
            stmt.setInt(6, id); 

            int rowsUpdated = stmt.executeUpdate();
            if (rowsUpdated > 0) {
                JOptionPane.showMessageDialog(null, "Dados atualizados com sucesso para o cliente ID: " + id);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args){
        Verificacoes v = new Verificacoes();
        v.dataNasc("2005-01-24");
    }
}
