package edu.upc.dsa;

import edu.upc.dsa.models.Usuario;
import edu.upc.dsa.models.PuntoDeInteres;
import edu.upc.dsa.models.RegistroPuntoDeInteres;

import java.util.List;
import java.util.Queue;

public interface JuegoVirtualManager {

    public Usuario addUsuario(String id, String nombre, String apellidos, String email, String fechaNacimiento);
    public Usuario addUsuario(String nombre, String apellidos, String email, String fechaNacimiento);
    public Usuario addUsuario(Usuario u);
    public List<Usuario> listarUsuariosPorOrdenAlfabetico();
    public Usuario getUsuario(String id);

    public PuntoDeInteres addPuntoDeInteres(int CordHoritzontal, int CordVertical, PuntoDeInteres.ElementType tipo);
    public PuntoDeInteres addPuntoDeInteres(PuntoDeInteres p);

    public RegistroPuntoDeInteres addRegistroPuntoDeInteres(String userId, int cordHoritzontal, int cordVertical);
    public List<RegistroPuntoDeInteres> listarRegistrosPorOrdenDePasada(String userId);

    public List<Usuario> listarUsuariosPasadosPorUnPuntoInteres(int cordHoritzontal, int cordVertical);
    public List<PuntoDeInteres> listarPuntosDeInteresDelMapa(PuntoDeInteres.ElementType tipo);



    public void clear();
    public int sizeP();
    public int sizeU();
    public int sizeR();
}
