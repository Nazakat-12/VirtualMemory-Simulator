package our_os_project;

import java.awt.*;
import javax.swing.*;
import javax.swing.border.*;
import java.awt.event.*;
import java.util.ArrayList;
import javax.swing.table.*;

public class UserInterface {
	private ArrayList<Process> P;
	private int algorithmFlag = 1;
	private int user_ram = 0;
	private int total_CPU_time = 0;
	private int total_CS = 0;
	private int total_faults = 0;
	private int total_cycles = 0;
	private float avg_WT = 0;
	private float avg_TAT = 0;
	private JFrame frmVirtualMemorySimulator;
	private JTextField textField1;
	private JTextField textField2;
	private JTable table;
	private JLabel lbl1;
	private JLabel lbl2;
	private JLabel lbl4;
	private JLabel lbl8;
	private JButton btn1;
	private JComboBox<String> comboBox1;
	private JComboBox<String> comboBox2;
	private JComboBox<String> comboBox4;
	private JScrollPane scrollPane;
	private JTextField textField3;
	private JTextField textField6;
	private JTextField textField5;
	private JTextField textField4;

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					UserInterface window = new UserInterface();
					window.frmVirtualMemorySimulator.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public UserInterface() {
		initialize();
	}

	private void initialize() {
		// Main frame setup
		frmVirtualMemorySimulator = new JFrame();
		frmVirtualMemorySimulator.setTitle("NEO Virtual Memory Simulator");
		frmVirtualMemorySimulator.setBounds(100, 100, 950, 650);
		frmVirtualMemorySimulator.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmVirtualMemorySimulator.getContentPane().setBackground(new Color(0, 18, 30));
		frmVirtualMemorySimulator.getContentPane().setLayout(null);

		// Custom font
		Font futura = new Font("Futura", Font.PLAIN, 14);
		Font futuraBold = new Font("Futura", Font.BOLD, 14);

		// Left control panel
		JPanel controlPanel = new JPanel();
		controlPanel.setBounds(20, 20, 400, 580);
		controlPanel.setBackground(new Color(10, 30, 45));
		controlPanel.setBorder(new MatteBorder(1, 1, 1, 1, new Color(80, 80, 100)));
		controlPanel.setLayout(null);
		frmVirtualMemorySimulator.getContentPane().add(controlPanel);

		// Right results panel
		JPanel resultsPanel = new JPanel();
		resultsPanel.setBounds(440, 20, 750, 580);
		resultsPanel.setBackground(new Color(10, 30, 45));
		resultsPanel.setBorder(new MatteBorder(1, 1, 1, 1, new Color(80, 80, 100)));
		resultsPanel.setLayout(null);
		frmVirtualMemorySimulator.getContentPane().add(resultsPanel);

		// ===== CONTROL PANEL ELEMENTS =====
		// Title
		JLabel titleLabel = new JLabel("VIRTUAL MEMORY SIMULATOR");
		titleLabel.setBounds(50, 20, 300, 30);
		titleLabel.setFont(new Font("Futura", Font.BOLD, 18));
		titleLabel.setForeground(new Color(0, 150, 200));
		controlPanel.add(titleLabel);

		// File selection
		lbl1 = createLabel("Select File:", 50, 70, futuraBold, new Color(200, 220, 255));
		controlPanel.add(lbl1);

		comboBox1 = createComboBox(new String[]{"use configuration file", "use generated file"},
				50, 95, 300, 30, futura, new Color(40, 40, 60), new Color(0, 200, 255));
		comboBox1.addActionListener(e -> jComboBox1ActionPerformed(e));
		controlPanel.add(comboBox1);

		// Memory size selection
		lbl2 = createLabel("Select memory size:", 50, 150, futuraBold, new Color(200, 220, 255));
		controlPanel.add(lbl2);

		comboBox2 = createComboBox(new String[]{"10K ,40 frames", "50K ,200 frames", "125K , 500 frames"},
				50, 175, 300, 30, futura, new Color(40, 40, 60), new Color(0, 200, 255));
		comboBox2.addActionListener(e -> jComboBox2ActionPerformed(e));
		controlPanel.add(comboBox2);

		// Algorithm selection
		lbl4 = createLabel("Select page replacement algorithm:", 50, 230, futuraBold, new Color(200, 220, 255));
		controlPanel.add(lbl4);

		comboBox4 = createComboBox(new String[]{"FIFO", "Second-chance FIFO", "LRU"},
				50, 255, 300, 30, futura, new Color(40, 40, 60), new Color(0, 200, 255));
		comboBox4.addActionListener(e -> jComboBox4ActionPerformed(e));
		controlPanel.add(comboBox4);

		// Simulate button
		btn1 = new JButton("RUN SIMULATION");
		btn1.setBounds(100, 320, 200, 45);
		btn1.setFont(new Font("Futura", Font.BOLD, 14));
		btn1.setForeground(Color.WHITE);
		btn1.setBackground(new Color(0, 150, 200));
		btn1.setBorder(new MatteBorder(1, 1, 1, 1, new Color(0, 200, 255)));
		btn1.setFocusPainted(false);
		btn1.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

		// Hover effects
		btn1.addMouseListener(new MouseAdapter() {
			public void mouseEntered(MouseEvent evt) {
				btn1.setBackground(new Color(0, 180, 230));
			}
			public void mouseExited(MouseEvent evt) {
				btn1.setBackground(new Color(0, 150, 200));
			}
		});

		btn1.addActionListener(e -> jButton1ActionPerformed(e));
		controlPanel.add(btn1);

		// ===== RESULTS PANEL ELEMENTS =====
		// Results title
		JLabel resultsTitle = new JLabel("SIMULATION RESULTS");
		resultsTitle.setBounds(30, 20, 400, 30);
		resultsTitle.setFont(new Font("Futura", Font.BOLD, 18));
		resultsTitle.setForeground(new Color(20, 200, 255));
		resultsPanel.add(resultsTitle);

		// Stats panel
		JPanel statsPanel = new JPanel();
		statsPanel.setBounds(30, 60, 700, 220);
		statsPanel.setBackground(new Color(8, 95, 93));
		statsPanel.setBorder(new MatteBorder(1, 1, 1, 1, new Color(80, 80, 100)));
		statsPanel.setLayout(null);
		resultsPanel.add(statsPanel);

		// Stats labels and fields
		addStatRow(statsPanel, "Total page faults:", 20, 20, futuraBold);
		textField1 = createResultField(200, 20, futura);
		statsPanel.add(textField1);

		addStatRow(statsPanel, "Total cycles:", 20, 50, futuraBold);
		textField2 = createResultField(200, 50, futura);
		statsPanel.add(textField2);

		addStatRow(statsPanel, "Total context switches:", 20, 80, futuraBold);
		textField3 = createResultField(200, 80, futura);
		statsPanel.add(textField3);

		addStatRow(statsPanel, "Total CPU time:", 20, 110, futuraBold);
		textField4 = createResultField(200, 110, futura);
		statsPanel.add(textField4);

		addStatRow(statsPanel, "Avg wait time:", 20, 140, futuraBold);
		textField5 = createResultField(200, 140, futura);
		statsPanel.add(textField5);

		addStatRow(statsPanel, "Avg turn around time:", 20, 170, futuraBold);
		textField6 = createResultField(200, 170, futura);
		statsPanel.add(textField6);

		// Process info title
		lbl8 = new JLabel("PROCESS INFORMATION");
		lbl8.setBounds(30, 300, 400, 30);
		lbl8.setFont(new Font("Futura", Font.BOLD, 16));
		lbl8.setForeground(new Color(0, 200, 255));
		resultsPanel.add(lbl8);

		// Table panel
		JPanel tablePanel = new JPanel();
		tablePanel.setBounds(30, 330, 700, 220);
		tablePanel.setBackground(new Color(8, 95, 93));
		tablePanel.setBorder(new MatteBorder(1, 1, 1, 1, new Color(80, 80, 100)));
		tablePanel.setLayout(new BorderLayout());
		resultsPanel.add(tablePanel);

		// Table setup
		table = new JTable();
		table.setBackground(new Color(8, 95, 93));
		table.setForeground(Color.cyan);
		table.setGridColor(new Color(0, 80, 100));
		table.setFont(futura);
		table.setRowHeight(25);
		table.setSelectionBackground(new Color(0, 150, 200));
		table.setSelectionForeground(Color.cyan);
		table.setShowGrid(true);

		// Table header styling
		JTableHeader header = table.getTableHeader();
		header.setBackground(new Color(30, 30, 45));
		header.setForeground(new Color(0, 200, 255));
		header.setFont(futuraBold);

		// Scroll pane
		scrollPane = new JScrollPane(table);
		scrollPane.setBorder(null);
		tablePanel.add(scrollPane, BorderLayout.CENTER);

		// Table model
		table.setModel(new DefaultTableModel(new Object[][] {},
				new String[] { "PID", "Duration", "Start", "Finish", "TAT", "Wait", "Faults", "Pages" }));
	}

	// Action methods (keep your original implementations)
	private void jComboBox1ActionPerformed(ActionEvent e) {
		FileInput fi = new FileInput();
		switch (comboBox1.getSelectedIndex()) {
			case 0: // Configuration file
				P = fi.readConfigurationFile();
				break;
			case 1: // Generated file
				P = fi.readGeneratedFile();
				break;
			default:
				break;
		}
	}

	private void jComboBox2ActionPerformed(ActionEvent e) {
		switch (comboBox2.getSelectedIndex()) {
			case 0:
				user_ram = 40;
				break;
			case 1:
				user_ram = 200;
				break;
			case 2:
				user_ram = 500;
				break;
			default:
				break;
		}
	}

	private void jComboBox4ActionPerformed(ActionEvent e) {
		switch (comboBox4.getSelectedIndex()) {
			case 0:
				algorithmFlag = 1; // fifo
				break;
			case 1:
				algorithmFlag = 2; // second chance
				break;
			case 2:
				algorithmFlag = 3; // lru
				break;
			default:
				break;
		}
	}

	private void jButton1ActionPerformed(ActionEvent e) {
		MyThread thread = new MyThread();

		// Clear table
		DefaultTableModel dm = (DefaultTableModel) table.getModel();
		int rowCount = dm.getRowCount();
		for (int i = rowCount - 1; i >= 0; i--) {
			dm.removeRow(i);
		}

		// Change array list to array
		Process[] ar_p = new Process[P.size()];
		ar_p = P.toArray(new Process[0]);
		thread.setFlag(algorithmFlag);
		thread.setRamsize(user_ram);
		thread.setArrp(ar_p);
		thread.run();

		total_cycles = thread.getF().getCycles();
		for (int i = 0; i < P.size(); i++) {
			total_CPU_time += P.get(i).getDuration();
			total_faults += ar_p[i].getFaultsNumber();
			total_cycles += thread.getPr().getCycles();
		}
		thread.getF().setContext_switch(ar_p.length);
		total_CS = thread.getF().getContext_switch();
		avg_WT = thread.getF().getAVG_WT();
		avg_TAT = thread.getF().getAVG_TAT();

		// Update text fields
		textField1.setText(String.valueOf(total_faults));
		textField2.setText(String.valueOf(total_cycles));
		textField3.setText(String.valueOf(total_CS));
		textField4.setText(String.valueOf(total_CPU_time));
		textField5.setText(String.valueOf(avg_WT));
		textField6.setText(String.valueOf(avg_TAT));

		// Add to table
		DefaultTableModel model = (DefaultTableModel) table.getModel();
		for (int k = 0; k < P.size(); k++) {
			int finish_time = P.get(k).getTAtime() + P.get(k).getArrivalTime();
			model.addRow(new Object[] {
					P.get(k).getProcessID(),
					P.get(k).getDuration(),
					String.valueOf(P.get(k).getArrivalTime()),
					String.valueOf(finish_time),
					String.valueOf(ar_p[k].getTAtime()),
					String.valueOf(ar_p[k].getWTime()),
					String.valueOf(ar_p[k].getFaultsNumber()),
					P.get(k).getSize()
			});
		}
	}

	// Helper methods
	private JLabel createLabel(String text, int x, int y, Font font, Color color) {
		JLabel label = new JLabel(text);
		label.setBounds(x, y, 300, 20);
		label.setFont(font);
		label.setForeground(color);
		return label;
	}

	private JComboBox<String> createComboBox(String[] items, int x, int y, int width, int height,
											 Font font, Color bg, Color fg) {
		JComboBox<String> comboBox = new JComboBox<>(items);
		comboBox.setBounds(x, y, width, height);
		comboBox.setFont(font);
		comboBox.setBackground(bg);
		comboBox.setForeground(fg);
		comboBox.setBorder(new MatteBorder(1, 1, 1, 1, new Color(80, 80, 100)));
		comboBox.setRenderer(new DefaultListCellRenderer() {
			@Override
			public Component getListCellRendererComponent(JList<?> list, Object value, int index,
														  boolean isSelected, boolean cellHasFocus) {
				super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
				setBackground(isSelected ? new Color(0, 150, 200) : bg);
				setForeground(isSelected ? Color.WHITE : fg);
				return this;
			}
		});
		return comboBox;
	}

	private JTextField createResultField(int x, int y, Font font) {
		JTextField field = new JTextField();
		field.setBounds(x, y, 150, 25);
		field.setFont(font);
		field.setBackground(new Color(50, 50, 70));
		field.setForeground(Color.cyan);
		field.setBorder(new MatteBorder(1, 1, 1, 1, new Color(80, 80, 100)));
		field.setEditable(false);
		field.setHorizontalAlignment(JTextField.RIGHT);
		return field;
	}

	private void addStatRow(JPanel panel, String text, int x, int y, Font font) {
		JLabel label = new JLabel(text);
		label.setBounds(x, y, 170, 25);
		label.setFont(font);
		label.setForeground(new Color(200, 220, 255));
		panel.add(label);
	}
}