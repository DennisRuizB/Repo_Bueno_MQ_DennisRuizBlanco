package edu.upc.dsa;

import edu.upc.dsa.models.PuntoDeInteres;
import edu.upc.dsa.models.RegistroPuntoDeInteres;
import edu.upc.dsa.models.Usuario;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import org.apache.log4j.Logger;

public class JuegoVirtualManagerImpl implements JuegoVirtualManager {
    private static JuegoVirtualManager instance;
    protected List<Usuario> usuarios;
    protected List<PuntoDeInteres> puntosDeInteres;
    protected Queue<RegistroPuntoDeInteres> registros;
    List<Usuario> listaUsuariosPasadosPorUnPuntoInteres;
    final static Logger logger = Logger.getLogger(JuegoVirtualManagerImpl.class);

    private JuegoVirtualManagerImpl() {
        this.usuarios = new LinkedList<>();
        this.puntosDeInteres = new LinkedList<>();
        this.registros = new LinkedList<>();
        this.listaUsuariosPasadosPorUnPuntoInteres = new LinkedList<>();
    }
    public static JuegoVirtualManager getInstance() {
        if (instance==null) instance = new JuegoVirtualManagerImpl();
        return instance;
    }

    @Override
    public Usuario addUsuario(Usuario u){
        logger.info("new Usuario Id" + u.getId()+ " Nombre: " + u.getNombre() + " Apellidos: " + u.getApellidos() + " Email: " + u.getEmail() + " Fecha de Nacimiento: " + u.getFechaNacimiento());
        this.usuarios.add(u);
        logger.info("new Usuario added");
        return u;
    }

    @Override
    public Usuario addUsuario(String nombre, String apellidos, String email, String fechaNacimiento){
        return this.addUsuario(null, nombre, apellidos, email, fechaNacimiento);
    }

    @Override
    public Usuario addUsuario(String id, String nombre, String apellidos, String email, String fechaNacimiento){
        return this.addUsuario(new Usuario(id, nombre, apellidos, email, fechaNacimiento));
    }

    @Override
    public List<Usuario> listarUsuariosPorOrdenAlfabetico(){
        this.usuarios.sort((u1, u2) -> {
            int lastNameComparison = u1.getApellidos().compareToIgnoreCase(u2.getApellidos());
            if (lastNameComparison != 0) {
                return lastNameComparison;
            }
            return u1.getNombre().compareToIgnoreCase(u2.getNombre());
        });
        if(this.usuarios.isEmpty()){
            logger.error("No hay usuarios");
        }
        logger.info("Se ha usado listarUsuariosPorOrdenAlfabetico");
        return this.usuarios;
    }

    @Override
    public Usuario getUsuario(String id){
        logger.info("getUsuario("+id+")");
        for (Usuario u: this.usuarios) {
            if (u.getId().equals(id)) {
                logger.info("getUsuario("+id+"): "+u);
                return u;
            }
        }
        logger.error("not found " + id);
        return null;
    }

    @Override
    public PuntoDeInteres addPuntoDeInteres(int CordHoritzontal, int CordVertical, PuntoDeInteres.ElementType tipo){
        logger.info("new PuntoDeInteres " + CordHoritzontal + " " + CordVertical + " " + tipo);
        this.puntosDeInteres.add(new PuntoDeInteres(CordHoritzontal, CordVertical, tipo));
        PuntoDeInteres p = new PuntoDeInteres(CordHoritzontal, CordVertical, tipo);
        if (p == null) {
            logger.error("PuntoDeInteres not added, invalid format");
        }
        else logger.info("new PuntoDeInteres added");
        return p;
    }

    @Override
    public PuntoDeInteres addPuntoDeInteres(PuntoDeInteres p){
        logger.info("new PuntoDeInteres " + p.getCordHoritzontal() + " " + p.getCordVertical() + " " + p.getTipo());
        this.puntosDeInteres.add(p);
        if (p == null) {
            logger.error("PuntoDeInteres not added, invalid format");
        }
        else
            logger.info("new PuntoDeInteres added");
        return p;
    }

    @Override
    public RegistroPuntoDeInteres addRegistroPuntoDeInteres(String userId, int cordHoritzontal, int cordVertical) {
        Usuario u = this.getUsuario(userId);
        if (u == null) {
            logger.error("ERROR, Usuario con id " + userId + " no encontrado");
            return null;
        }

        PuntoDeInteres p = null;
        for (PuntoDeInteres punto : this.puntosDeInteres) {
            if (punto.getCordHoritzontal() == cordHoritzontal && punto.getCordVertical() == cordVertical) {
                p = punto;
                break;
            }
        }

        if (p == null) {
            logger.error("ERROR, Punto de interés en coordenadas (" + cordHoritzontal + ", " + cordVertical + ") no encontrado");
            return null;
        }

        RegistroPuntoDeInteres registro = new RegistroPuntoDeInteres(userId, cordHoritzontal, cordVertical);
        this.registros.add(registro);
        logger.info("Registro de punto de interés añadido para el usuario " + userId + " en coordenadas (" + cordHoritzontal + ", " + cordVertical + ")");
        return registro;
    }

    @Override
    public List<RegistroPuntoDeInteres> listarRegistrosPorOrdenDePasada(String userId) {
        List<RegistroPuntoDeInteres> registrosUsuarioOrdenado = new LinkedList<>();
        for (RegistroPuntoDeInteres registro : this.registros) {
            if (registro.getUserId().equals(userId)) {
                registrosUsuarioOrdenado.add(registro);
            }
        }
        if (registrosUsuarioOrdenado.isEmpty()) {
            logger.error("No hay registros para el usuario con id " + userId);
        } else {
            logger.info("Se ha usado listarRegistrosPorOrdenDePasada para el usuario con id " + userId);
        }
        return registrosUsuarioOrdenado;
    }

    @Override
    public List<Usuario> listarUsuariosPasadosPorUnPuntoInteres(int cordHoritzontal, int cordVertical){
        listaUsuariosPasadosPorUnPuntoInteres.clear();
        boolean puntoEncontrado = false;
        for (PuntoDeInteres punto : this.puntosDeInteres) {
            if (punto.getCordHoritzontal() == cordHoritzontal && punto.getCordVertical() == cordVertical) {
                puntoEncontrado = true;
                break;
            }
        }
        if (!puntoEncontrado) {
            logger.error("Punto de interés en coordenadas (" + cordHoritzontal + ", " + cordVertical + ") no encontrado");
            return listaUsuariosPasadosPorUnPuntoInteres;
        }
        for (RegistroPuntoDeInteres registro : this.registros) {
            if (registro.getCordHoritzontal() == cordHoritzontal && registro.getCordVertical() == cordVertical) {
                Usuario u = this.getUsuario(registro.getUserId());
                listaUsuariosPasadosPorUnPuntoInteres.add(u);
            }
        }
        if (listaUsuariosPasadosPorUnPuntoInteres.isEmpty()) {
            logger.error("No hay usuarios que hayan pasado por el punto de interés en coordenadas (" + cordHoritzontal + ", " + cordVertical + ")");
        } else {
            logger.info("Se ha usado listarUsuariosPasadosPorUnPuntoInteres");
        }
        return listaUsuariosPasadosPorUnPuntoInteres;
    }

    @Override
    public List<PuntoDeInteres> listarPuntosDeInteresDelMapa(PuntoDeInteres.ElementType tipo) {
        List<PuntoDeInteres> puntosFiltrados = new LinkedList<>();
        for (PuntoDeInteres punto : this.puntosDeInteres) {
            if (punto.getTipo() == tipo) {
                puntosFiltrados.add(punto);
            }
        }
        if (puntosFiltrados.isEmpty()) {
            logger.error("No hay puntos de interés del tipo " + tipo);
        } else {
            logger.info("Se ha usado listarPuntosDeInteresDelMapa");
        }
        return puntosFiltrados;
    }






    @Override
    public int sizeU() {
        int ret = this.usuarios.size();
        logger.info("size " + ret);
        return ret;
    }

    @Override
    public int sizeP() {
        int ret = this.puntosDeInteres.size();
        logger.info("size " + ret);
        return ret;
    }

    @Override
    public int sizeR() {
        int ret = this.registros.size();
        logger.info("size " + ret);
        return ret;
    }

    @Override
    public void clear() {
        this.usuarios.clear();
        this.puntosDeInteres.clear();
        this.registros.clear();
    }


}
