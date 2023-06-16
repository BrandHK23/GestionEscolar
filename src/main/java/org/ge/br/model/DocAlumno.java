package org.ge.br.model;

public class DocAlumno {
    private int id;
    private String nombre_doc;

    public DocAlumno() {

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    private String ruta_doc;
    private boolean existe_doc;
    private int num_doc;
    private int id_alumno;

    public int getId_alumno() {
        return id_alumno;
    }

    public void setId_alumno(int id_alumno) {
        this.id_alumno = id_alumno;
    }

    public String getNombre_doc() {
        return nombre_doc;
    }

    public void setNombre_doc(String nombre_doc) {
        this.nombre_doc = nombre_doc;
    }

    public String getRuta_doc() {
        return ruta_doc;
    }

    public void setRuta_doc(String ruta_doc) {
        this.ruta_doc = ruta_doc;
    }

    public boolean isExiste_doc() {
        return existe_doc;
    }

    public void setExiste_doc(boolean existe_doc) {
        this.existe_doc = existe_doc;
    }

    public int getNum_doc() {
        return num_doc;
    }

    public void setNum_doc(int num_doc) {
        this.num_doc = num_doc;
    }

    public DocAlumno(String nombre, String ruta, boolean existe, int num_doc) {
        this.nombre_doc = nombre;
        this.ruta_doc = ruta;
        this.existe_doc = existe;
        this.num_doc = num_doc;
    }
}