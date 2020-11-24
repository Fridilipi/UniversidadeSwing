package view;

import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.table.TableModel;

import controller.ControllerAluno;
import controller.ControllerGraduacao;
import dao.DAOAluno;
import model.Aluno;
import model.Graduacao;

public class TelaAluno extends JFrame {

    private final JTable table;
    TelaAluno frame;

    ArrayList<Graduacao> arrayListGraduacao = null;

    ControllerAluno controllerAluno = ControllerAluno.getInstance();
    ControllerGraduacao controllerGraduacao = ControllerGraduacao.getInstance();

    DAOAluno daoAluno = new DAOAluno();

    public TelaAluno() throws Exception {
        frame = this;
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 743, 411);

        JPanel contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);

        JMenuBar menuBar = new JMenuBar();
        setJMenuBar(menuBar);

        JMenu mnOperaes = new JMenu("Operações");
        menuBar.add(mnOperaes);

        JMenuItem mntmIncluir = new JMenuItem("Incluir");
        mnOperaes.add(mntmIncluir);
        mntmIncluir.addMouseListener(new MouseAdapter() {

            @Override
            public void mouseReleased(MouseEvent mouseEvent) {
                try {
                    Aluno aluno = new Aluno();

                    aluno.setId(UUID.randomUUID().toString());

                    arrayListGraduacao = controllerGraduacao.listarGraduacao();

                    List<String> arrayNomeGraduacao = new ArrayList<>();

                    arrayListGraduacao.forEach(item -> arrayNomeGraduacao.add(item.getNome()));

                    String nomeGraduacao = (String) JOptionPane.showInputDialog(null, "Choose now...",
                            "The Choice of a Lifetime", JOptionPane.QUESTION_MESSAGE, null,
                            arrayNomeGraduacao.toArray(), arrayNomeGraduacao.toArray()[0]);

                    Graduacao graduacao = arrayListGraduacao.stream()
                            .filter(item -> nomeGraduacao.equals(item.getNome())).findAny().orElse(null);

                    aluno.setGraduacao(graduacao);

                    String nomeAluno = JOptionPane.showInputDialog("Digite o nome do aluno: ");
                    aluno.setNome(nomeAluno);

                    String dataNascimentoAluno = JOptionPane.showInputDialog("Digite a data de nascimento: ");

                    aluno.setDataNascimento(new SimpleDateFormat("dd/MM/yyyy").parse(dataNascimentoAluno));

                    if (controllerAluno.incluirAluno(aluno)) {
                        JOptionPane.showMessageDialog(null, "Aluno incluido com sucesso.");
                    } else {
                        JOptionPane.showMessageDialog(null, "Erro ao incluir aluno.");
                    }

                    table.setModel(daoAluno.listarTableModel());
                } catch (Exception exception) {
                    System.err.println("TelaAluno " + exception.getMessage());
                }
            }
        });

        JMenuItem mntmAlterar = new JMenuItem("Alterar");
        mnOperaes.add(mntmAlterar);

        JLabel labelListaAlunos = new JLabel("Lista de Alunos");
        labelListaAlunos.setHorizontalAlignment(SwingConstants.LEFT);
        labelListaAlunos.setFont(new Font("Baskerville Old Face", Font.ITALIC, 16));
        labelListaAlunos.setBounds(16, 16, 168, 15);
        contentPane.add(labelListaAlunos);

        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setBounds(16, 52, 707, 128);
        contentPane.add(scrollPane);

        table = new JTable();
        table.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent arg0) {
                try {
                    ListSelectionModel listSelectionModel = table.getSelectionModel();

                    Aluno aluno = new Aluno();

                    TableModel tableModel = table.getModel();

                    aluno.setId(tableModel.getValueAt(listSelectionModel.getMinSelectionIndex(), 0).toString());
                    aluno.setGraduacao(
                            controllerGraduacao.consultarGraduacao(
                                    tableModel.getValueAt(listSelectionModel.getMinSelectionIndex(), 1).toString()));
                    aluno.setNome(tableModel.getValueAt(listSelectionModel.getMinSelectionIndex(), 2).toString());
                    aluno.setDataNascimento(
                            new SimpleDateFormat("yyyy-MM-dd").parse(
                                    tableModel.getValueAt(listSelectionModel.getMinSelectionIndex(), 3).toString()));

                    Object[] options = {"Alterar", "Excluir"};
                    int n = JOptionPane.showOptionDialog(frame,
                            "Nome: " + aluno.getNome() + "\n" +
                                    "Graduação: " + aluno.getGraduacao().getNome() + "\n" +
                                    "Data de Nascimento: " + new SimpleDateFormat("yyyy-MM-dd").format(aluno.getDataNascimento()) + "\n",
                            aluno.getNome(),
                            JOptionPane.YES_NO_CANCEL_OPTION,
                            JOptionPane.QUESTION_MESSAGE,
                            null,
                            options, null);

                    if (n == 0) {
                        try {
                            arrayListGraduacao = controllerGraduacao.listarGraduacao();

                            List<String> arrayNomeGraduacao = new ArrayList<>();

                            arrayListGraduacao.forEach(item -> arrayNomeGraduacao.add(item.getNome()));

                            String nomeGraduacao = (String) JOptionPane.showInputDialog(null, "Selecione uma graduação",
                                    null, JOptionPane.QUESTION_MESSAGE, null, arrayNomeGraduacao.toArray(), arrayNomeGraduacao.toArray()[0]);

                            Graduacao graduacao = arrayListGraduacao.stream()
                                    .filter(item -> nomeGraduacao.equals(item.getNome())).findAny().orElse(null);

                            aluno.setGraduacao(graduacao);

                            String nomeAluno = JOptionPane.showInputDialog("Digite o nome do aluno: ");
                            aluno.setNome(nomeAluno);

                            String dataNascimentoAluno = JOptionPane.showInputDialog("Digite a data de nascimento: ");

                            aluno.setDataNascimento(new SimpleDateFormat("dd/MM/yyyy").parse(dataNascimentoAluno));

                            if (controllerAluno.alterarAluno(aluno)) {
                                JOptionPane.showMessageDialog(null, "Aluno alterado com sucesso.");
                            } else {
                                JOptionPane.showMessageDialog(null, "Erro ao alterar aluno.");
                            }

                            table.setModel(daoAluno.listarTableModel());
                        } catch (Exception exception) {
                            System.err.println("TelaAluno " + exception.getMessage());
                        }
                    } else {
                        if (JOptionPane.showConfirmDialog(null, "Deseja excluir este aluno?", null, JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
                            if (controllerAluno.removerAluno(aluno.getId())) {
                                JOptionPane.showMessageDialog(null, "Aluno removido com sucesso.");

                                table.setModel(daoAluno.listarTableModel());
                            } else {
                                JOptionPane.showMessageDialog(null, "Erro ao remover aluno.");
                            }
                        }
                    }

                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, "Atenção: " + ex.getMessage());
                }
            }
        });

        table.setModel(daoAluno.listarTableModel());
        scrollPane.setViewportView(table);

        JButton btnVoltar = new JButton("Voltar");
        btnVoltar.addActionListener(arg0 -> {
            try {
                frame.setVisible(false);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, "Atenção: " + ex.getMessage());
            }
        });
        btnVoltar.setBounds(317, 325, 89, 23);
        contentPane.add(btnVoltar);
    }
}
