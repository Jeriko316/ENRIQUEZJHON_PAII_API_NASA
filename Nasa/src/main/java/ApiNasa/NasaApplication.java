package ApiNasa;

import ApiNasa.modelo.Mars;
import ApiNasa.service.MarsService;
import ApiNasa.view.MarsViewer;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.swing.*;
import java.util.Arrays;
import java.util.List;

@SpringBootApplication
public class NasaApplication {

	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				new MarsViewer().setVisible(true);
			}
		});
	}

}
