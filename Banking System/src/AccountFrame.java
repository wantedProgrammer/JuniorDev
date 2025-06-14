import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

public class AccountFrame extends JFrame {
    JLabel accnNoLBL, ownerLBL, balanceLBL, cityLBL, genderLBL, amountLBL;
    JTextField accNoTXT, ownerTXT, balanceTXT, amountTXT;
    JComboBox<City> citiesCMB;

    JButton newBTN, saveBTN, showBTN, quitBTN, depositBTN, withdrawBTN;
    JRadioButton maleRDB, femaleRDB;
    ButtonGroup genderBTNGRP;

    JList<Accounts> accountsLST;
    JPanel p1, p2, p3, p4, p5;

    Set<Accounts> accountsSet = new TreeSet<>();
    Accounts acc, x;
    boolean newRec = true;

    //ComboBox Data
    DefaultComboBoxModel<City> citiesCMBMDL;
    DefaultListModel<Accounts> accountsLSTMDL;

    // Table
    JTable table;
    DefaultTableModel tablemodel;
    ArrayList<Transaction> transList = new ArrayList<>();

    // Constructor
    public AccountFrame(){
        super("Tanaka's Bank Account Operation");
        setLayout(null);
        setSize(600, 400);

        // Adding components to the Frame
        // 1 - Labels
        accnNoLBL = new JLabel("Account No.");
        ownerLBL = new JLabel("Owner");
        balanceLBL = new JLabel("Balance");
        cityLBL = new JLabel("City");
        genderLBL = new JLabel("Gender");
        amountLBL = new JLabel("Amount");

        // 2 - TextFields
        accNoTXT = new JTextField(); accNoTXT.setEditable(false);
        ownerTXT = new JTextField();
        balanceTXT = new JTextField(); balanceTXT.setEditable(false);
        amountTXT = new JTextField();
        amountTXT.setPreferredSize(new Dimension(150, 25));

        // 3 - ComboBox
        citiesCMBMDL = new DefaultComboBoxModel<City>();
        citiesCMBMDL.addElement(null);
        citiesCMBMDL.addElement(new City("Pietermaritzburg", "KwaZulu-Natal"));
        citiesCMBMDL.addElement(new City("Johannesburg", "Gauteng"));
        citiesCMBMDL.addElement(new City("Cape Town", "Western Cape"));
        citiesCMBMDL.addElement(new City("Port Elizabeth", "Eastern Cape"));

        // Adding to JCOMBOBOX
        citiesCMB = new JComboBox<City>(citiesCMBMDL);

        // 4 - Radio Buttons
        maleRDB = new JRadioButton("Male", true);
        femaleRDB = new JRadioButton("Female");
        genderBTNGRP = new ButtonGroup();
        genderBTNGRP.add(maleRDB);
        genderBTNGRP.add(femaleRDB);

        // 5 - Buttons
        newBTN = new JButton("New");
        saveBTN = new JButton("Save");
        showBTN = new JButton("Show");
        quitBTN = new JButton("Quit");
        depositBTN = new JButton("Deposit");
        withdrawBTN = new JButton("Withdraw");

        // 6 - Table
        accountsLSTMDL = new DefaultListModel<>();
        accountsLST = new JList<>(accountsLSTMDL);

        // 7 - Panels
        p1 = new JPanel(); p1.setBounds(5, 5, 300, 150);
        p1.setLayout(new GridLayout(6, 2));

        p2 = new JPanel(); p2.setBounds(5, 155, 300, 40);
        p2.setLayout(new FlowLayout());

        p3 = new JPanel(); p3.setBounds(5, 195, 600, 40);
        p3.setLayout(new FlowLayout());

        p4 = new JPanel(); p4.setBounds(305, 5, 300, 190);
        p4.setLayout(new BorderLayout());

        p5 = new JPanel(); p5.setBounds(5, 240, 580, 120);
        p5.setLayout(new BorderLayout());

        // Adding Components to Panel
        p1.add(accnNoLBL);
        p1.add(accNoTXT);
        p1.add(ownerLBL);
        p1.add(ownerTXT);
        p1.add(balanceLBL);
        p1.add(balanceTXT);
        p1.add(cityLBL);
        p1.add(citiesCMB);
        p1.add(maleRDB);
        p1.add(femaleRDB);

        // 2nd Panel
        p2.add(newBTN);
        p2.add(saveBTN);
        p2.add(showBTN);
        p2.add(quitBTN);

        // 3rd Panel
        p3.add(amountLBL);
        p3.add(amountTXT);
        p3.add(depositBTN);
        p3.add(withdrawBTN);

        // 4th Panel
        p4.add(new JScrollPane(accountsLST), BorderLayout.CENTER);

        // Adding Panels to Frame
        add(p1);
        add(p2);
        add(p3);
        add(p4);
        add(p5);

        // Table Creation
        tablemodel = new DefaultTableModel();
        table = new JTable(tablemodel);

        tablemodel.addColumn("Tansaction Number");
        tablemodel.addColumn("Tansaction Date");
        tablemodel.addColumn("Transaction Type");
        tablemodel.addColumn("Transaction Amount");

        JScrollPane scrollPane = new JScrollPane(table);
        p5.add(scrollPane);

        // Methods
        newBTN.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                accNoTXT.setText("");
                ownerTXT.setText("");
                citiesCMB.setSelectedIndex(0);
                maleRDB.setSelected(true);
                balanceTXT.setText("");
                amountTXT.setText("");
                newRec = true;
            }
        });

        saveBTN.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(newRec){
                    if(ownerTXT.getText().length() != 0){
                        acc = new Accounts(
                                ownerTXT.getText(),
                                (City) citiesCMB.getSelectedItem(),
                                maleRDB.isSelected()? 'M': 'F');

                        accNoTXT.setText(String.valueOf(acc.accNo));
                        accountsSet.add(acc);
                        accountsLSTMDL.addElement(acc);
                        newRec = false;
                    }else{
                        JOptionPane.showMessageDialog(null, "PLEASE FILL IN ALL THE FIELDS");
                    }
                }else{
                    accountsSet.remove(acc);

                    int a = Integer.parseInt(accNoTXT.getText());
                    String o = ownerTXT.getText();
                    City c = (City) citiesCMB.getSelectedItem();

                    char g = maleRDB.isSelected()? 'M': 'F';
                    double b = Double.parseDouble(balanceTXT.getText());
                    acc = new Accounts(a, o, c, g, b);
                    accountsSet.add(acc);
                    accountsLSTMDL.setElementAt(acc, accountsLST.getSelectedIndex());
                    newRec = false;


                }

            }
        });

        showBTN.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String s = "";
                Iterator<Accounts> it = accountsSet.iterator();
                while(it.hasNext()){
                    s += it.next().toString()+"\n";
                    JOptionPane.showMessageDialog(null, s);
                }
            }
        });

        depositBTN.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(!newRec && amountTXT.getText().length() != 0){
                    // Adding Transaction
                    Transaction t = new Transaction(acc, LocalDate.now(), 'D',Double.parseDouble(amountTXT.getText()));
                    DisplayTransactionInTable(t);

                    // Perform deposit from account
                    acc.deposit(Double.parseDouble(amountTXT.getText()));
                    balanceTXT.setText(String.valueOf(acc.balance));


                }
            }
        });

        withdrawBTN.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(!newRec && amountTXT.getText().length() != 0){
                    // Adding transaction to table
                    Transaction t = new Transaction(acc, LocalDate.now(), 'W', Double.parseDouble(amountTXT.getText()));
                    DisplayTransactionInTable(t);

                    // Perform withdrawl on account
                    acc.withdraw(Double.parseDouble(amountTXT.getText()));
                    balanceTXT.setText(String.valueOf(acc.balance));

                }
            }
        });

        accountsLST.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                acc = x = accountsLST.getSelectedValue();

                accNoTXT.setText(String.valueOf(acc.accNo));
                ownerTXT.setText(acc.owner);
                citiesCMB.setSelectedItem(acc.city);

                if(acc.gender == 'M') maleRDB.setSelected(true);
                else femaleRDB.setSelected(true);

                balanceTXT.setText(String.valueOf(acc.balance));
                amountTXT.setEnabled(true);
                depositBTN.setEnabled(true);
                newRec = false;

                // Clear table
                for(int i = tablemodel.getRowCount() - 1; i >= 0; i--){
                    tablemodel.removeRow(i);
                }

                // Get Transaction to selected account
                SearchTransactionList(acc.accNo);


            }
        });

    }

    private void SearchTransactionList(int accNo) {
        List<Transaction> filteredList = new ArrayList<>();

        // Iterate through the list
        for(Transaction t: transList){
            // filter values that contain trsNo
            if(t.getAcc().accNo == accNo){
                filteredList.add(t);
            }
        }

        // Display the filtered list
        for(int i = 0; i < filteredList.size(); i++){
            // Displaying Data into table
            tablemodel.addRow(new Object[]{
                    filteredList.get(i).getTrsNo(),
                    filteredList.get(i).getDate(),
                    filteredList.get(i).getOperation(),
                    filteredList.get(i).getAmount()
            });
        }



    }

    private void DisplayTransactionInTable(Transaction t) {
        // Displaying data in table
        tablemodel.addRow(new Object[]{
                t.getTrsNo(),
                t.getDate(),
                t.getOperation(),
                t.getAmount()
        });
        // Adding objects to array
        transList.add(t);
    }

    public static void main(String[] args){
        AccountFrame af = new AccountFrame();
        af.setVisible(true);
        af.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    }
}

