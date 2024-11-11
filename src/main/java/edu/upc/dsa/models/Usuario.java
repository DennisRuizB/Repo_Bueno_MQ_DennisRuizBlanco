package edu.upc.dsa.models;
import edu.upc.dsa.util.RandomUtils;

public class Usuario {
    String id;
    String nombre;
    String apellidos;
    String email;
    String fechaNacimiento;

    public Usuario() {
        this.setId(RandomUtils.getId());
    }

    public Usuario(String nombre, String apellidos, String email, String fechaNacimiento) {
        this();
        this.setNombre(nombre);
        this.setApellidos(apellidos);
        this.setEmail(email);
        this.setFechaNacimiento(fechaNacimiento);
    }

    public Usuario(String id, String nombre, String apellidos, String email, String fechaNacimiento) {
        this();
        if (id != null) this.setId(id);
        this.setNombre(nombre);
        this.setApellidos(apellidos);
        this.setEmail(email);
        this.setFechaNacimiento(fechaNacimiento);
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public String getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(String fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }
}
