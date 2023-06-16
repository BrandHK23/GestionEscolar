package org.ge.br.view.Alumno;


import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import java.util.Map;

public class CardLayoutManager {
    private JPanel cardPanel;
    private CardLayout cardLayout;
    private Map<String, JPanel> panels;
    public CardLayoutManager(JPanel cardPanel) {
        this.cardPanel = cardPanel;
        cardLayout = new CardLayout();
        cardPanel.setLayout(cardLayout);
        panels = new HashMap<>();
        cardPanel.setPreferredSize(new Dimension(1200, 400)); // Establecer el tamaño deseado del cardPanel
    }


    public void addPanel(String name, JPanel panel) {
        panels.put(name, panel);
        cardPanel.add(panel, name);
    }

    public void showPanel(String name) {
        cardLayout.show(cardPanel, name);
    }

    public void showPreviousPanel() {
        cardLayout.previous(cardPanel);
    }
}
