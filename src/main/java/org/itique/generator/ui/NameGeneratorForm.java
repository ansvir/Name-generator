package org.itique.generator.ui;

import org.itique.generator.core.NameGenerator;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import java.util.Map;

import static org.itique.generator.core.Constant.PROBABILITIES_LENGTH;
import static org.itique.generator.core.Constant.SUM_OF_PROBABILITIES;
import static org.itique.generator.core.NameGenerator.DEFAULT_PROBABILITIES;

/**
 * Standalone UI implementation for Name Generator application.
 */
public class NameGeneratorForm {

    private JTextField generate_field, name_starts_with, surname_starts_with, name_ends_with, surname_ends_with, name_length_field, surname_length_field;
    private JTextField[] letters_tf;
    private JButton generate_button, settings_confirm, settings_default_prob;
    private JCheckBox generate_using_lenghts, generate_using_parts;
    private int name_length, surname_length;
    private JFrame jfrm;
    private JLabel maximum_length_label, name_starts_with_label, surname_starts_with_label, name_ends_with_label, surname_ends_with_label, status_line;
    private JLabel[] letters_l;
    private JPanel generate, settings;
    private JTabbedPane jtp;
    private JList<String> generates_list;
    private DefaultListModel<String> list_model;
    private GridBagConstraints c, c2, c3;
    private GridBagLayout gbl;
    private JScrollPane jsp;

    NameGeneratorForm() {

        name_length = surname_length = 0;

        jfrm = new JFrame("Name generator v1.0");

        gbl = new GridBagLayout();

        jfrm.setLayout(new GridLayout());

        jfrm.setSize(700, 400);

        jfrm.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        jfrm.setLocationRelativeTo(null);

        jfrm.setResizable(false);

        //set constraints for swing components
        c = new GridBagConstraints();

        c.anchor = GridBagConstraints.NORTH;
        c.fill = GridBagConstraints.NONE;
        c.gridx = 1;
        c.gridy = 0;
        c.gridheight = 10;
        c.gridwidth = 200;
        c.insets = new Insets(10, 10, 10, 10);

        c2 = new GridBagConstraints();

        c2.gridx = 1;
        c2.gridy = 1;
        c2.gridheight = 10;
        c2.gridwidth = 200;
        c2.anchor = GridBagConstraints.CENTER;
        c2.fill = GridBagConstraints.NONE;
        c2.insets = new Insets(50, 50, 50, 50);

        c3 = new GridBagConstraints();

        c3.anchor = GridBagConstraints.SOUTH;
        c3.fill = GridBagConstraints.NONE;
        c3.gridx = 1;
        c3.gridy = 10;
        c3.gridheight = 200;
        c3.gridwidth = 200;
        c3.insets = new Insets(50, 50, 50, 50);

        //create components
        name_length_field = new JTextField("4", 2);

        surname_length_field = new JTextField("5", 2);

        generate_field = new JTextField("Go to settings to setup your own name and surname lengths.", 40);

        name_starts_with = new JTextField("", 5);
        name_starts_with.setVisible(false);

        surname_starts_with = new JTextField("", 5);
        surname_starts_with.setVisible(false);

        name_ends_with = new JTextField("", 5);
        name_ends_with.setVisible(false);

        surname_ends_with = new JTextField("", 5);
        surname_ends_with.setVisible(false);

        generate_button = new JButton("Generate");

        settings_confirm = new JButton("Confirm");

        settings_default_prob = new JButton("Default");

        generate_using_lenghts = new JCheckBox("Generate using certain lenghts", false);

        generate_using_parts = new JCheckBox("Generate using start and end parts", false);

        maximum_length_label = new JLabel("Enter maximum lengths of the name and surname.");

        name_starts_with_label = new JLabel("Name's start");
        name_starts_with_label.setVisible(false);

        surname_starts_with_label = new JLabel("Surname's start");
        surname_starts_with_label.setVisible(false);

        name_ends_with_label = new JLabel("Name's end");
        name_ends_with_label.setVisible(false);

        surname_ends_with_label = new JLabel("Surname's end");
        surname_ends_with_label.setVisible(false);

        status_line = new JLabel("");

        letters_l = new JLabel[26];
        letters_tf = new JTextField[26];
        char ch_temp = 'A';
        for (int i = 0; i < 26; i++, ch_temp++) {
            letters_l[i] = new JLabel(Character.toString(ch_temp));
            letters_tf[i] = new JTextField(Double.toString(NameGenerator.getProbability(i)));
        }

        list_model = new DefaultListModel<>();
        generates_list = new JList<>(list_model);
        jsp = new JScrollPane(generates_list);
        jsp.setPreferredSize(new Dimension(300, 150));

        //create panels and main pane
        generate = new JPanel(gbl);
        generate.setPreferredSize(new Dimension(jfrm.getWidth(), jfrm.getHeight()));

        settings = new JPanel(new FlowLayout());
        settings.setPreferredSize(new Dimension(jfrm.getWidth(), jfrm.getHeight()));
        jtp = new JTabbedPane();

        generate_button.addActionListener(ae -> {
			NameGenerator ng = new NameGenerator();
			name_length = surname_length = 0;
			try {
				name_length = Integer.parseInt(name_length_field.getText());
				surname_length = Integer.parseInt(surname_length_field.getText());

				//handling exception of length shorter than 2
				if (name_length < 2) {
					generate_field.setText("The length of the name should be 2 or more.");
					name_length_field.setText("2");
					return;
				}

				if (surname_length < 2) {
					generate_field.setText("The length of the surname must be 2 or more.");
					surname_length_field.setText("2");
					return;
				}

				if (generate_using_lenghts.isSelected()) {
					if (generate_using_parts.isSelected()) {
						NameGenerator.setPart(0, name_starts_with.getText());
						NameGenerator.setPart(1, surname_starts_with.getText());
						NameGenerator.setPart(2, name_ends_with.getText());
						NameGenerator.setPart(3, surname_ends_with.getText());
						generate_field.setText(ng.generateNameAndSurname(name_length, surname_length, true, true));
					} else
						generate_field.setText(ng.generateNameAndSurname(name_length, surname_length, true, false));
				} else
					generate_field.setText(ng.generateNameAndSurname(name_length, surname_length, false, false));


				list_model.addElement(generate_field.getText());
			} catch (NumberFormatException exc) {
				generate_field.setText("Invalid length.");
			}
		});

        settings_confirm.addActionListener(ae -> {
			double d_temp, sum_of_changed_prob = 0.0;
			Map<Integer, Double> changed_prob = new HashMap<>();
			double[] prev_prob = new double[PROBABILITIES_LENGTH];
			for (int i = 0; i < PROBABILITIES_LENGTH; i++) {
				prev_prob[i] = NameGenerator.getProbability(i);
			}

			//fill map with changed probabilities and their indexes
			for (int i = 0; i < prev_prob.length; i++) {
				try {
					d_temp = Double.parseDouble(letters_tf[i].getText());
				} catch (NumberFormatException e) {
					status_line.setText("<html><body style=\"color:#FF0000\">Invalid probability. Field's/Fields' probability(ies) was(were) set to previous.</body></html>");
					for (int j = 0; j < prev_prob.length; j++) {
						NameGenerator.setProbability(j, prev_prob[j]);
						letters_tf[j].setText(Double.toString(NameGenerator.getProbability(j)));
					}
					return;
				}

				if (d_temp != prev_prob[i]) {
					if (d_temp == 100.0) {
						status_line.setText("<html><body style=\"color:#FF0000\">100% cannot be set.</body></html>");
						for (int j = 0; j < prev_prob.length; j++) {
							NameGenerator.setProbability(j, prev_prob[j]);
							letters_tf[j].setText(Double.toString(NameGenerator.getProbability(j)));
						}
						return;
					}

					if (d_temp < 0.0) {
						changed_prob.put(i, 0.0);
						NameGenerator.setProbability(i, 0.0);
						generate_field.setText("Probability's value cannot be set as negative. Field's probability was set to 0%.");
					} else {
						changed_prob.put(i, d_temp);
						NameGenerator.setProbability(i, d_temp);
					}

				}
			}

			//fill sum value with all changed probabilities' values
			for (int i = 0; i < prev_prob.length; i++) {
				if (changed_prob.containsKey(i)) sum_of_changed_prob += changed_prob.get(i);
			}

			//values of probabilities were not changed exception handling. It helps to avoid next repeated distribution of d_temp among all probabilities, thereby their values become identical
			if (changed_prob.size() == 0) return;

			//sum of changed probabilities' values is more than 100% exception handling
			if (sum_of_changed_prob > 100.0) {
				status_line.setText("<html><body style=\"color:#FF0000\">Sum of new probabilities is more than 100. Field's/Fields' probability(ies) was(were) set to previous.</body></html>");
				for (int i = 0; i < prev_prob.length; i++) {
					NameGenerator.setProbability(i, prev_prob[i]);
					letters_tf[i].setText(Double.toString(NameGenerator.getProbability(i)));
				}
				return;
			}

			//fill d_temp with value that will be used to ramp up each of the rest of probabilities' values
			d_temp = (SUM_OF_PROBABILITIES - sum_of_changed_prob) / (PROBABILITIES_LENGTH - changed_prob.size());

			//distribute difference between previous and new probabilities among values of not changed probabilities
			for (Integer i = 0; i < prev_prob.length; i++) {
				if (!changed_prob.containsKey(i)) {
					NameGenerator.setProbability(i, d_temp);
				}
			}

			for (int i = 0; i < PROBABILITIES_LENGTH; i++) {
				letters_tf[i].setText(Double.toString(NameGenerator.getProbability(i)));
			}

			status_line.setText("Probability's settings confirmed");
		});

        settings_default_prob.addActionListener(ae -> {
			for (int i = 0; i < PROBABILITIES_LENGTH; i++) {
				letters_tf[i].setText(Double.toString(DEFAULT_PROBABILITIES[i]));
			}
		});

        generate_using_parts.addItemListener(ie -> {
			if (generate_using_parts.isSelected()) {
				name_starts_with_label.setVisible(true);
				name_starts_with.setVisible(true);
				surname_starts_with_label.setVisible(true);
				surname_starts_with.setVisible(true);
				name_ends_with_label.setVisible(true);
				name_ends_with.setVisible(true);
				surname_ends_with_label.setVisible(true);
				surname_ends_with.setVisible(true);
			} else {
				name_starts_with_label.setVisible(false);
				name_starts_with.setVisible(false);
				surname_starts_with_label.setVisible(false);
				surname_starts_with.setVisible(false);
				name_ends_with_label.setVisible(false);
				name_ends_with.setVisible(false);
				surname_ends_with_label.setVisible(false);
				surname_ends_with.setVisible(false);
			}
		});

        generate.add(generate_field, c);
        generate.add(generate_button, c2);
        generate.add(jsp, c3);

        settings.add(generate_using_lenghts);
        settings.add(generate_using_parts);
        settings.add(maximum_length_label);
        settings.add(name_length_field);
        settings.add(surname_length_field);
        settings.add(name_starts_with_label);
        settings.add(name_starts_with);
        settings.add(surname_starts_with_label);
        settings.add(surname_starts_with);
        settings.add(name_ends_with_label);
        settings.add(name_ends_with);
        settings.add(surname_ends_with_label);
        settings.add(surname_ends_with);
        for (int i = 0; i < letters_l.length; i++) {
            settings.add(letters_l[i]);
            settings.add(letters_tf[i]);
        }
        settings.add(settings_confirm);
        settings.add(settings_default_prob);
        settings.add(status_line);

        jtp.add("generate", generate);
        jtp.add("settings", settings);


        jfrm.add(jtp);
        jfrm.setVisible(true);

    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(NameGeneratorForm::new);
    }

}
