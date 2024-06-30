package ApiNasa.view;

import ApiNasa.modelo.Mars;
import ApiNasa.service.MarsService;

import javax.swing.*;


import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URL;
import java.util.List;

public class MarsViewer extends JFrame {

    private JLabel photoLabel;
    private MarsService service;

    public MarsViewer() {
        setTitle("Mars Photo Viewer");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Panel para los botones
        JPanel buttonPanel = new JPanel();
        JButton filterBySolButton = new JButton("Filter by Sol 1000");
        JButton filterByDateButton = new JButton("Filter by Date 2015-05-30");

        buttonPanel.add(filterBySolButton);
        buttonPanel.add(filterByDateButton);

        // Label para mostrar la foto
        photoLabel = new JLabel();
        photoLabel.setHorizontalAlignment(SwingConstants.CENTER);
        photoLabel.setVerticalAlignment(SwingConstants.CENTER);

        add(buttonPanel, BorderLayout.NORTH);
        add(photoLabel, BorderLayout.CENTER);

        // Inicializar el servicio
        try {
            String apiUrl = "https://api.nasa.gov/mars-photos/api/v1/rovers/curiosity/photos?sol=1000&api_key=DEMO_KEY";
            service = new MarsService(apiUrl);
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Acción del botón de filtro por sol
        filterBySolButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                List<Mars> photos = service.filterBySol("1000");
                if (!photos.isEmpty()) {
                    displayPhoto(photos.get(0).getImgSrc());
                } else {
                    System.out.println("No photos found for sol 1000.");
                }
            }
        });

        // Acción del botón de filtro por fecha de la Tierra
        filterByDateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                List<Mars> photos = service.filterByEarthDate("2015-05-30");
                if (!photos.isEmpty()) {
                    displayPhoto(photos.get(0).getImgSrc());
                } else {
                    System.out.println("No photos found for date 2015-05-30.");
                }
            }
        });
    }

    private void displayPhoto(String url) {
        try {
            System.out.println("Loading image from URL: " + url);  // Log de depuración

            ImageIcon imageIcon = new ImageIcon(new URL(url));
            Image image = imageIcon.getImage(); // Transformar
            Image newImg = image.getScaledInstance(600, 400,  java.awt.Image.SCALE_SMOOTH); // Escalar
            imageIcon = new ImageIcon(newImg);  // Transformar de nuevo
            photoLabel.setIcon(imageIcon);

            System.out.println("Image loaded successfully.");  // Log de depuración

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error loading image from URL: " + url);  // Log de depuración
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new MarsViewer().setVisible(true);
            }
        });
    }


}
