package app;

import javax.swing.*;
import javax.swing.text.*;
import conexao.Conexao;
import java.awt.*;
import java.awt.event.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class App extends JFrame {
    public App() {
        setTitle("Cadastro de Produtos");
        setSize(400, 230);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(null); 
        setResizable(false);

        try {
            for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
                if ("Windows".equals(info.getName())) {
                    UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("icon.png")));

        JLabel TextoCodigoProduto = new JLabel("Codigo:");
        TextoCodigoProduto.setBounds(20, 20, 100, 20);
        add(TextoCodigoProduto);

        JTextField Codigo = new JTextField();
        Codigo.setBounds(80, 20, 80, 20);
        Codigo.setForeground(Color.black);

        add(Codigo);

        JLabel TextoProduto = new JLabel("Produto:");
        TextoProduto.setBounds(195, 20, 120, 20);
        add(TextoProduto);

        JTextField NomeProduto = new JTextField("");
        NomeProduto.setBounds(250, 20, 100, 20);
        NomeProduto.setForeground(Color.black);
        add(NomeProduto);

        JLabel TextoFornecedor = new JLabel("Fornecedor:");
        TextoFornecedor.setBounds(20, 60, 100, 20);
        add(TextoFornecedor);

        String[] Fornecedores = { "Microsoft", "Pichau", "Kabum", "Mancer", "Acer", "Duex", "Outro" };
        JComboBox<String> Opcoes = new JComboBox<>(Fornecedores);
        Opcoes.setBounds(90, 60, 100, 20);
        add(Opcoes);

        JLabel TextOutroFornecedor = new JLabel("Outro:");
        TextOutroFornecedor.setBounds(210, 60, 100, 20);
        TextOutroFornecedor.setVisible(false);
        add(TextOutroFornecedor);

        JTextField outroFornecedor = new JTextField();
        outroFornecedor.setBounds(250, 60, 100, 20);
        outroFornecedor.setVisible(false);
        add(outroFornecedor);

        Opcoes.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String selecionado = (String) Opcoes.getSelectedItem();
                if (selecionado.equals("Outro")) {
                    TextOutroFornecedor.setVisible(true);
                    outroFornecedor.setVisible(true);
                } else {
                    TextOutroFornecedor.setVisible(false);
                    outroFornecedor.setVisible(false);
                }
            }
        });

        JLabel TextoQuantidade = new JLabel("Quantidade:");
        TextoQuantidade.setBounds(20, 100, 100, 20);
        add(TextoQuantidade);

        JTextField Quantidade = new JTextField();
        Quantidade.setBounds(90, 100, 30, 20);
        Quantidade.setVisible(true);
        add(Quantidade);

        JButton Cadastro = new JButton("Cadastrar");
        Cadastro.setBounds(130, 140, 120, 30);
        add(Cadastro);

        Cadastro.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                PreparedStatement cursor = null;
                try {
                    int quantidade = Integer.parseInt(Quantidade.getText());
                    Connection conn = Conexao.getConexao();
                    cursor = conn.prepareStatement("INSERT INTO produtos (codigo, produto, fornecedor) VALUES (?, ?, ?)");
                    if(quantidade <= 0){
                        JOptionPane.showMessageDialog(null,"A quantidade não pode ser 0!!","Erro", JOptionPane.ERROR_MESSAGE);
                        return;
                    }

                    for (int l = 0; l < quantidade; l++) {
                        cursor.setString(1, Codigo.getText());
                        cursor.setString(2, NomeProduto.getText());

                        if (Opcoes.getSelectedItem().equals("Outro")) {
                            cursor.setString(3, outroFornecedor.getText());
                        } else {
                            cursor.setString(3, (String) Opcoes.getSelectedItem());
                        }

                        cursor.executeUpdate();
                    }

                    JOptionPane.showMessageDialog(null, "Produtos Cadastrados com Sucesso!!", "Cadastro", JOptionPane.INFORMATION_MESSAGE);
                } catch (Exception ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(null, "Verifique os dados e tente novamente!", "Erro", JOptionPane.ERROR_MESSAGE);
                } finally {
                    try {
                        if (cursor != null) cursor.close();
                    } catch (SQLException m) {
                        m.printStackTrace();
                    }
                }
            }
        });

        setVisible(true);
    }
}
