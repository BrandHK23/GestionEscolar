package org.ge.br.config;

import javax.swing.DefaultCellEditor;
import javax.swing.JTextField;

public class NonEditableCellEditor extends DefaultCellEditor {
    public NonEditableCellEditor() {
        super(new JTextField());
    }

    @Override
    public boolean isCellEditable(java.util.EventObject e) {
        return false;
    }
}
