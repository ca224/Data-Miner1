import java.awt.EventQueue;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;
import javax.swing.JScrollPane;
import javax.swing.UIManager;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.awt.Font;

public class Data_miner1 {

	// static UI widget
	private JFrame frmDataminer;
	private JTextField textField_support;
	private JTextField textField_confidence;
	private JTextArea textArea;

	private BufferedReader in;
	private boolean Cal_switch = false;

	// Max number of transactions in each database file
	private final int Maxtrans = 20;
	// Probability of each item exists in one transaction
	private final int Itemprobability = 5;
	// Control the decimal precision
	java.text.DecimalFormat df = new java.text.DecimalFormat("#.00");

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Data_miner1 window = new Data_miner1();
					window.frmDataminer.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public Data_miner1() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmDataminer = new JFrame();
		frmDataminer.setResizable(false);
		frmDataminer.setTitle("Data_Miner1");
		frmDataminer.setBounds(100, 100, 1000, 600);
		frmDataminer.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmDataminer.getContentPane().setLayout(null);
		try {
			UIManager
					.setLookAndFeel("com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel");
		} catch (Exception e) {
			e.printStackTrace();
		}
		JLabel label1 = new JLabel("Support");
		label1.setHorizontalAlignment(SwingConstants.RIGHT);
		label1.setBounds(22, 10, 54, 15);
		frmDataminer.getContentPane().add(label1);

		JLabel label2 = new JLabel("Confidence");
		label2.setHorizontalAlignment(SwingConstants.RIGHT);
		label2.setBounds(10, 35, 66, 15);
		frmDataminer.getContentPane().add(label2);

		textField_support = new JTextField();
		textField_support.setHorizontalAlignment(SwingConstants.RIGHT);
		textField_support.setText("70");
		textField_support.setBounds(86, 7, 54, 21);
		frmDataminer.getContentPane().add(textField_support);
		textField_support.setColumns(10);

		textField_confidence = new JTextField();
		textField_confidence.setText("70");
		textField_confidence.setHorizontalAlignment(SwingConstants.RIGHT);
		textField_confidence.setBounds(86, 32, 54, 21);
		frmDataminer.getContentPane().add(textField_confidence);
		textField_confidence.setColumns(10);

		JLabel label3 = new JLabel("%");
		label3.setBounds(143, 10, 54, 15);
		frmDataminer.getContentPane().add(label3);

		JLabel label4 = new JLabel("%");
		label4.setBounds(143, 35, 54, 15);
		frmDataminer.getContentPane().add(label4);

		JButton Calbtn = new JButton("Calculate");
		Calbtn.setFont(new Font("SansSerif", Font.PLAIN, 11));
		Calbtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				CalbuttonactionPerformed(e);
			}
		});
		Calbtn.setBounds(163, 10, 94, 40);
		frmDataminer.getContentPane().add(Calbtn);

		JButton Filebtn = new JButton("Choose File");
		Filebtn.setFont(new Font("SansSerif", Font.PLAIN, 11));
		Filebtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				FilebuttonactionPerformed(e);
			}
		});
		Filebtn.setBounds(264, 10, 102, 40);
		frmDataminer.getContentPane().add(Filebtn);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 60, 900, 500);
		frmDataminer.getContentPane().add(scrollPane);

		textArea = new JTextArea();
		scrollPane.setViewportView(textArea);

		JButton Savebtn = new JButton("Generate Data");
		Savebtn.setFont(new Font("SansSerif", Font.PLAIN, 11));
		Savebtn.setBounds(373, 10, 102, 40);
		frmDataminer.getContentPane().add(Savebtn);
		Savebtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				SavebuttonactionPerformed(e);
			}
		});
	}

	public void SavebuttonactionPerformed(ActionEvent e) {
		// String itemlist[] = { "A", "B", "C", "D", "E", "F", "G", "H", "I", "J" };
		String itemlist[] = { "Banana", "Coke", "Pear", "Chips", "Orange",
		"Grape", "Chocolate", "Plum", "Avocado", "Cheese" };
		// Only .txt file is valid for this application
		// Set the default output filename
		String defaultFileName = "database.txt";
		JFileChooser filechooser1 = new JFileChooser();
		FileNameExtensionFilter filter = new FileNameExtensionFilter(
				"TXT FILE", "txt");
		filechooser1.setFileFilter(filter);
		filechooser1.setSelectedFile(new File(defaultFileName));
		if (filechooser1.showSaveDialog(frmDataminer) == JFileChooser.APPROVE_OPTION) {
			File file = filechooser1.getSelectedFile();
			try {
				// I/O process
				FileWriter fw = new FileWriter(file);
				BufferedWriter bw = new BufferedWriter(fw);
				PrintWriter outFile = new PrintWriter(bw);
				// Generate database file
				for (int i = 0; i < Maxtrans; i++) {
					String thisline = "T" + (i + 1);
					for (int j = 0; j < itemlist.length; j++) {
						int k = (int) Math.round(Math.random() * 10);
						if (k >= (10 - Itemprobability)) {
							thisline = thisline + " " + itemlist[j];
						}
					}
					outFile.println(thisline);
				}
				outFile.flush();
				outFile.close();
			} catch (IOException event) {
				event.printStackTrace();
			}
		}
		textArea.setText("New database file has been generated!");
	}

	public void FilebuttonactionPerformed(ActionEvent e) {
		// Choosing file to be imported
		JFileChooser filechooser = new JFileChooser();
		// Only .txt file is valid for this application
		FileNameExtensionFilter filter = new FileNameExtensionFilter(
				"TXT FILE", "txt");
		filechooser.setFileFilter(filter);

		if (filechooser.showOpenDialog(frmDataminer) == JFileChooser.APPROVE_OPTION) {
			File file = filechooser.getSelectedFile();
			try {
				// I/O process
				FileInputStream fis = new FileInputStream(
						file.getAbsoluteFile());
				InputStreamReader isr = new InputStreamReader(fis, "UTF-8");
				in = new BufferedReader(isr);
				in.mark((int) file.length() + 1);
				// Inform user the file has been loaded
				textArea.setText("The data file has been loaded.");
				Cal_switch = true;
			} catch (FileNotFoundException event) {
				textArea.setText("The data file could not be found.");
			} catch (IOException event) {
				event.printStackTrace();
			}
		}
	}

	public void CalbuttonactionPerformed(ActionEvent e) {
		if (Cal_switch == false) {
			textArea.setText("Please select data file first!");
			return;
		}
		float support = Float.parseFloat(textField_support.getText()) / 100;
		float confidence = Float.parseFloat(textField_confidence.getText()) / 100;
		if (support * 100 > 100 || confidence * 100 > 100) {
			textArea.setText("Input is out of bounds!");
			return;
		}
		// Used to store all the itemset that meet the support threshold
		ArrayList<String> Resultlist = new ArrayList<String>();
		// Used to store the support for above itemsets
		ArrayList<Integer> Counters = new ArrayList<Integer>();
		// Used to store the support for every candidate of each round
		ArrayList<Integer> Can_counters = new ArrayList<Integer>();
		// Used to store the winner of each round
		ArrayList<String> Winner = new ArrayList<String>();
		// Used to store the loser of each round
		ArrayList<String> Loser = new ArrayList<String>();
		// Superset of last round's itemset
		ArrayList<String> Candidate = new ArrayList<String>();
		StringBuffer result = new StringBuffer();
		String thisline = "";
		String parts[] = null;
		// Total amount of transaction
		float Transcounter = 0;
		try {
			in.reset();
		} catch (IOException e2) {
			e2.printStackTrace();
		}
		try {
			// First time of scanning data file, gathering following
			// information: Item type amount, support of each single item
			while ((thisline = in.readLine()) != null) {
				Transcounter++;
				// Print out the database
				result.append(thisline + "\n");
				parts = thisline.split(" ");
				for (int i = 1; i < parts.length; i++) {
					int tempint = Candidate.indexOf(parts[i]);
					// If found a new item
					if (tempint == -1) {
						Candidate.add(parts[i]);
						Can_counters.add(new Integer(1));
						// If the item is already in the list
					} else {
						int tempint_2 = Can_counters.get(tempint).intValue() + 1;
						Can_counters.set(tempint, new Integer(tempint_2));
					}
				}
			}

			for (int i = 0; i <= Candidate.size() - 1; i++) {
				// Calculate the support
				float tempfloat = Can_counters.get(i).floatValue()
						/ Transcounter;
				// Select the high support item to winner group
				if (tempfloat >= support) {
					Winner.add(Candidate.get(i));
					Resultlist.add(Candidate.get(i));
					Counters.add(Can_counters.get(i));
				}
			}

			// For every round, new candidates will be generated from old winner
			// list, than test every candidate to generate the new winner list.
			do {
				Candidate = new ArrayList<String>();
				for (int i = 0; i < Winner.size() - 1; i++) {
					for (int j = i + 1; j < Winner.size(); j++) {
						// Check the intersection of 2 itemsets
						ArrayList<String> templist = exist(
								Winner.get(i).split(" "),
								Winner.get(j).split(" "));
						String tempstring = Winner.get(i);
						// Generate the new candidate
						tempstring = tempstring + " " + templist.get(0);

						boolean breakflag = false;
						// If the new candidate is a superset of unfrequent
						// itemset, discard it
						for (int i_3 = 0; i_3 < Loser.size(); i_3++) {
							String arr1[] = Loser.get(i_3).split(" ");
							String arr2[] = tempstring.split(" ");
							if (exist(arr2, arr1).size() == 0) {
								breakflag = true;
							}
						}

						if (breakflag)
							continue;

						// Always put the first candidate into the list
						if (Candidate.size() == 0) {
							Candidate.add(tempstring);
							continue;
						}

						// Check the candidate list to prevent duplicate
						for (int i_4 = 0; i_4 < Candidate.size(); i_4++) {
							String arr1[] = Candidate.get(i_4).split(" ");
							String arr2[] = tempstring.split(" ");
							Arrays.sort(arr1);
							Arrays.sort(arr2);
							if (Arrays.equals(arr1, arr2)) {
								break;
							}
							if (i_4 == Candidate.size() - 1) {
								Candidate.add(tempstring);
							}
						}
					}
				}

				in.reset();
				Can_counters = new ArrayList<Integer>();
				// Initialize the candidates' counter list
				for (int i = 0; i < Candidate.size(); i++) {
					Can_counters.add(new Integer(0));
				}

				// Rescanning the whole database
				while ((thisline = in.readLine()) != null) {
					parts = thisline.split(" ");
					for (int i = 0; i < Candidate.size(); i++) {
						String Can_parts[] = Candidate.get(i).split(" ");
						if (exist(parts, Can_parts).size() == 0) {
							int j = Can_counters.get(i).intValue() + 1;
							Can_counters.set(i, new Integer(j));
						}
					}
				}

				Winner = new ArrayList<String>();
				Loser = new ArrayList<String>();
				// Calculate the support
				for (int i = 0; i <= Candidate.size() - 1; i++) {
					float tempfloat = Can_counters.get(i).floatValue()
							/ Transcounter;
					// Select the high support item to winner group
					if (tempfloat >= support) {
						Winner.add(Candidate.get(i));
						Resultlist.add(Candidate.get(i));
						Counters.add(Can_counters.get(i));
					} else {
						// Select the low support item to loser group
						Loser.add(Candidate.get(i));
					}
				}
				// if there is only 1 itemset left, then no more new candidate
				// can be generated
			} while (Winner.size() >= 2);

			// Following part for calculating the confidence
			int rulecounter = 0;
			for (int i = 0; i < Resultlist.size() - 1; i++) {
				for (int j = i + 1; j < Resultlist.size(); j++) {
					ArrayList<String> templist = exist(
							Resultlist.get(i).split(" "), Resultlist.get(j)
									.split(" "));
					if (templist.size() == Resultlist.get(j).split(" ").length) {
						String tempstring = Resultlist.get(i);

						for (int i_2 = 0; i_2 < templist.size(); i_2++) {
							tempstring = tempstring + " " + templist.get(i_2);
						}

						boolean conflag = false;
						for (int i_3 = 0; i_3 < Resultlist.size(); i_3++) {
							String arr1[] = Resultlist.get(i_3).split(" ");
							String arr2[] = tempstring.split(" ");
							Arrays.sort(arr1);
							Arrays.sort(arr2);
							if (Arrays.equals(arr1, arr2)) {
								tempstring = Resultlist.get(i_3);
								break;
							}
							if (i_3 == Resultlist.size() - 1) {
								conflag = true;
							}
						}
						if (conflag == true) {
							continue;
						}

						int tempint = Resultlist.indexOf(tempstring);
						float numerator = Counters.get(tempint).floatValue();
						float denominator = Counters.get(i).floatValue();
						float denominator2 = Counters.get(j).floatValue();
						float j_2 = numerator / denominator;
						float j_3 = numerator / denominator2;
						if (j_2 >= confidence) {
							result.append(
									Resultlist.get(i)
									+ " --> "
									+ Resultlist.get(j)
									+ " with support "
									+ df.format(Counters.get(tempint)
											.floatValue() / Transcounter * 100)
									+ "%" + " and confidence "
									+ df.format(j_2 * 100) + "%" + "\n");
							rulecounter++;
						}

						if (j_3 >= confidence) {
							result.append(Resultlist.get(j)
									+ " --> "
									+ Resultlist.get(i)
									+ " with support "
									+ df.format(Counters.get(tempint)
											.floatValue() / Transcounter * 100)
									+ "%" + " and confidence "
									+ df.format(j_3 * 100) + "%" + "\n");
							rulecounter++;
						}
					}
				}
			}
			result.append(rulecounter + " association rules are found!");
			System.out.println(Resultlist);
			textArea.setText(result.toString());
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}

	public ArrayList<String> exist(String[] arr1, String[] arr2) {
		ArrayList<String> strs = new ArrayList<>();
		for (int i = 0; i < arr2.length; i++) {
			boolean exists = Arrays.asList(arr1).contains((arr2[i]));
			if (!exists) {
				strs.add(arr2[i]);
			}
		}
		return strs;
	}
}
