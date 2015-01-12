package com.kritsit.casetracker.client.domain.userinterface;

import com.jgoodies.forms.factories.FormFactory;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.RowSpec;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

public class LoginFrame {

	private JDialog frmCaseTrackerLogin;
	private JTextField textField;
	private JPasswordField passwordField;
	private JLabel lblPassword;
	private JPasswordField passwordField_1;
	private JPanel panel;
	private JButton btnLogin;
	private JButton btnCancel;
	private JLabel lblImage;

	public LoginFrame() {
		initialize();
	}

	private void initialize() {
		frmCaseTrackerLogin = new JDialog();
		frmCaseTrackerLogin.setTitle("CaseTracker - Login");
		frmCaseTrackerLogin.setBounds(100, 100, 397, 239);
		frmCaseTrackerLogin.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		frmCaseTrackerLogin.getContentPane().setLayout(new FormLayout(new ColumnSpec[] {
				FormFactory.RELATED_GAP_COLSPEC,
				FormFactory.PREF_COLSPEC,
				FormFactory.LABEL_COMPONENT_GAP_COLSPEC,
				ColumnSpec.decode("default:grow"),
				FormFactory.RELATED_GAP_COLSPEC,},
			new RowSpec[] {
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC,
				RowSpec.decode("default:grow"),
				FormFactory.RELATED_GAP_ROWSPEC,}));
		
		lblImage = new JLabel("");
		lblImage.setIcon(new ImageIcon("/home/kyle/java/CaseTrackerGui/src/resources/login.jpg"));
		frmCaseTrackerLogin.getContentPane().add(lblImage, "2, 2, 3, 1");
		
		JLabel lblUsername = new JLabel("Username:");
		frmCaseTrackerLogin.getContentPane().add(lblUsername, "2, 4, left, top");
		
		textField = new JTextField();
		frmCaseTrackerLogin.getContentPane().add(textField, "4, 4, fill, default");
		textField.setColumns(10);
		
		lblPassword = new JLabel("Password:");
		frmCaseTrackerLogin.getContentPane().add(lblPassword, "2, 6, right, default");
		
		passwordField_1 = new JPasswordField();
		frmCaseTrackerLogin.getContentPane().add(passwordField_1, "4, 6, fill, default");
		
		panel = new JPanel();
		frmCaseTrackerLogin.getContentPane().add(panel, "2, 8, 3, 1, fill, fill");
		
		btnLogin = new JButton("Login");
		panel.add(btnLogin);
		
		btnCancel = new JButton("Cancel");
		panel.add(btnCancel);
	}
}
