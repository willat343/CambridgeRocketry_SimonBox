package net.sf.openrocket.gui.dialogs;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import net.miginfocom.swing.MigLayout;
import net.sf.openrocket.gui.components.StyledLabel;
import net.sf.openrocket.gui.util.GUIUtil;
import net.sf.openrocket.l10n.Translator;
import net.sf.openrocket.startup.Application;

public class LicenseDialog extends JDialog {
	private static final String LICENSE_FILENAME = "LICENSE.TXT";
	private static final Translator trans = Application.getTranslator();

	private static final String DEFAULT_LICENSE_TEXT =
		"\n" +
		"Error:  Unable to load " + LICENSE_FILENAME + "!\n" +
		"\n" +
		"Cambridge Rocketry Simulator v2 is licensed under the GNU GPL version 3.\n";

	public LicenseDialog(JFrame parent) {
		super(parent, true);
		
		JPanel panel = new JPanel(new MigLayout("fill"));
		
		panel.add(new StyledLabel("Cambridge Rocketry v2 license", 10), "ax 50%, wrap para");

		String licenseText;
		try {
			
			File licenseFile = new File(LICENSE_FILENAME);
			
			//InputStream inputStream = ClassLoader.getSystemResourceAsStream(LICENSE_FILENAME);
			InputStream inputStream = new FileInputStream(licenseFile);
			
			BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
			
			StringBuffer sb = new StringBuffer();
			for (String s = reader.readLine(); s != null; s = reader.readLine()) {
				sb.append(s);
				sb.append('\n');
			}
			licenseText = sb.toString();
			
			reader.close();
			
		} catch (IOException e) {

			licenseText = DEFAULT_LICENSE_TEXT;
			
		}
		
		JTextArea text = new JTextArea(licenseText);
		text.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 12));
		text.setRows(20);
		text.setColumns(80);
		text.setEditable(false);
		panel.add(new JScrollPane(text),"grow, wrap para");
		
		//Close button
		JButton close = new JButton(trans.get("dlg.but.close"));
		close.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				LicenseDialog.this.dispose();
			}
		});
		panel.add(close, "right");
		
		this.add(panel);
		this.setTitle("OpenRocket license");
		this.pack();
		this.setLocationRelativeTo(parent);
		
		GUIUtil.setDisposableDialogOptions(this, close);
	}
	
}
