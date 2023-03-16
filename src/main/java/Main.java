import component.TrianglePanel;
import model.PhoneNumber;
import repository.EmployeeRepository;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Main {
    static JFrame jFrame = getFrame();
    static JPanel jPanel = new JPanel();

    public static void main(String[] args) {
        EmployeeRepository employeeRepository = new EmployeeRepository();

        JList<String> employees = new JList<>(employeeRepository.findAll()
                .stream()
                .map(e -> e.getName() + " " + e.getPhoneNumbers().get(0).getPhoneNumber())
                .toArray(String[]::new));

        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setViewportView(employees);
        scrollPane.createHorizontalScrollBar();

        TrianglePanel trianglePanel = new TrianglePanel();

        JTextField jTextField = new JTextField(10);

        JButton jButton = new JButton("submit");
        jButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String employeeName = jTextField.getText();
                String title = employeeName + "'s" + " phone numbers";
                String[] phoneNumbers = employeeRepository.findNumbersByName(employeeName)
                        .stream()
                        .map(PhoneNumber::getPhoneNumber)
                        .toArray(String[]::new);
                JOptionPane.showConfirmDialog(jPanel, new JList<>(phoneNumbers),
                        title, JOptionPane.DEFAULT_OPTION);

                jFrame.setContentPane(jPanel);
                jFrame.revalidate();
                jPanel.revalidate();
            }
        });

        jPanel.add(jTextField);
        jPanel.add(jButton);
        jPanel.add(scrollPane);
        jFrame.setContentPane(trianglePanel);
        jFrame.add(jPanel);
        jFrame.revalidate();
        jPanel.updateUI();
    }

    static JFrame getFrame() {
        JFrame jFrame = new JFrame() {};
        jFrame.setVisible(true);
        jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Toolkit toolkit = Toolkit.getDefaultToolkit();
        Dimension dimension = toolkit.getScreenSize();
        jFrame.setBounds(dimension.width/2 - 250, dimension.height/2 - 150, 800, 500);
        jFrame.setTitle("main window");
        jFrame.setLocationRelativeTo(null);
        return jFrame;
    }
}
