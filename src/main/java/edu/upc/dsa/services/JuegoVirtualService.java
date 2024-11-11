package edu.upc.dsa.services;


import edu.upc.dsa.JuegoVirtualManager;
import edu.upc.dsa.JuegoVirtualManagerImpl;
import edu.upc.dsa.models.Usuario;
import edu.upc.dsa.models.PuntoDeInteres;
import edu.upc.dsa.models.RegistroPuntoDeInteres;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

import javax.ws.rs.*;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Api(value = "/juegoVirtual", description = "Endpoint to Juego Virtual Service")
@Path("/juegoVirtual")
public class JuegoVirtualService {

    private JuegoVirtualManager jvm;

    public JuegoVirtualService() {
        this.jvm = JuegoVirtualManagerImpl.getInstance();
        if (jvm.sizeU()==0) {
            this.jvm.addUsuario("1","Dennis", "Ruiz", "denn@gmail.com", "18/10/2004");
            this.jvm.addUsuario("2","Alba", "A", "a@gmail.com", "28/1/2004");
            this.jvm.addUsuario("3","Marina", "Mar", "MM@gmail.com","1/1/2000");
        }
        if (jvm.sizeP()==0) {
            this.jvm.addPuntoDeInteres(1, 1, PuntoDeInteres.ElementType.COIN);
            this.jvm.addPuntoDeInteres(2, 2, PuntoDeInteres.ElementType.BRIDGE);
            this.jvm.addPuntoDeInteres(3, 3, PuntoDeInteres.ElementType.COIN);
        }
    }

    @POST
    @ApiOperation(value = "add a new User", notes = "añade un usuario")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Successful", response= Usuario.class),
            @ApiResponse(code = 500, message = "Validation Error")
    })
    @Path("/addUsuario")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response addUsuario(Usuario u) {

        if (u.getId()==null || u.getNombre()==null || u.getApellidos()==null || u.getEmail()==null || u.getFechaNacimiento()==null)
            return Response.status(500).entity(u).build();
        this.jvm.addUsuario(u);
        return Response.status(201).entity(u).build();
    }

    @GET
    @ApiOperation(value = "get all Users Ordered by Aplhabet", notes = "listar usuarios por orden alfabetico")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Successful", response = Usuario.class, responseContainer="List"),
    })
    @Path("/listarUsuariosPorOrdenAlfabetico")
    @Produces(MediaType.APPLICATION_JSON)
    public Response listarUsuariosPorOrdenAlfabetico() {

        List<Usuario> usuarios = this.jvm.listarUsuariosPorOrdenAlfabetico();

        GenericEntity<List<Usuario>> entity = new GenericEntity<List<Usuario>>(usuarios) {};
        return Response.status(201).entity(entity).build()  ;

    }

    @GET
    @ApiOperation(value = "get a User", notes = "get de un usuario con cierta id")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Successful", response = Usuario.class),
            @ApiResponse(code = 404, message = "User not found")
    })
    @Path("/getUsuario/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getUsuario(@PathParam("id") String id) {
        Usuario u = this.jvm.getUsuario(id);
        if (u == null) return Response.status(404).build();
        else  return Response.status(201).entity(u).build();
    }

    @POST
    @ApiOperation(value = "add a new Point of Interest", notes = "los tipos de puntos de interes son DOOR, WALL, BRIDGE, POTION, SWORD, COIN, GRASS, TREE")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Successful", response= PuntoDeInteres.class),
            @ApiResponse(code = 500, message = "Validation Error")
    })
    @Path("/addPuntoDeInteres")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response addPuntoDeInteres(PuntoDeInteres p){
        if (p.getTipo()==null)
            return Response.status(500).entity(p).build();

        this.jvm.addPuntoDeInteres(p);
        return Response.status(201).entity(p).build();
    }

    @POST
    @ApiOperation(value = "add new Register of Point of Interest", notes = "aaa")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Successful", response= RegistroPuntoDeInteres.class),
            @ApiResponse(code = 500, message = "Validation Error")
    })
    @Path("/addRegistroPuntoDeInteres")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response addRegistroPuntoDeInteres(RegistroPuntoDeInteres r){
        if (r.getUserId()==null || r==null)
            return Response.status(500).entity(r).build();
        this.jvm.addRegistroPuntoDeInteres(r.getUserId(), r.getCordHoritzontal(), r.getCordVertical());
        return Response.status(201).entity(r).build();
    }

    @GET
    @ApiOperation(value = "get all Registers of Point of Interest", notes = "listar registros por orden de pasada")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Successful", response = RegistroPuntoDeInteres.class, responseContainer="List"),
            @ApiResponse(code = 404, message = "Register not found")
    })
    @Path("/listarRegistrosPorOrdenDePasada/{userId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response listarRegistrosPorOrdenDePasada(@PathParam("userId") String userId) {

        List<RegistroPuntoDeInteres> registros = this.jvm.listarRegistrosPorOrdenDePasada(userId);

        GenericEntity<List<RegistroPuntoDeInteres>> entity = new GenericEntity<List<RegistroPuntoDeInteres>>(registros) {};
        return Response.status(201).entity(entity).build();

    }


    @GET
    @ApiOperation(value = "get all Users that have passed through a Point of Interest", notes = "listar usuarios que han pasado por un punto de interes")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Successful", response = Usuario.class, responseContainer="List"),
            @ApiResponse(code = 404, message = "User not found")
    })
    @Path("/listarUsuariosPasadosPorUnPuntoInteres/{cordHoritzontal}/{cordVertical}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response listarUsuariosPasadosPorUnPuntoInteres(@PathParam("cordHoritzontal") int cordHoritzontal, @PathParam("cordVertical") int cordVertical) {

        List<Usuario> usuariosDePuntoInteres = this.jvm.listarUsuariosPasadosPorUnPuntoInteres(cordHoritzontal, cordVertical);

        GenericEntity<List<Usuario>> entity = new GenericEntity<List<Usuario>>(usuariosDePuntoInteres) {};
        return Response.status(201).entity(entity).build();

    }

    @GET
    @ApiOperation(value = "get all Points of Interest of the Map", notes = "listar puntos de interes del mapa")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Successful", response = PuntoDeInteres.class, responseContainer="List"),
            @ApiResponse(code = 404, message = "Point of Interest not found")
    })
    @Path("/listarPuntosDeInteresDelMapa/{tipo}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response listarPuntosDeInteresDelMapa(@PathParam("tipo") PuntoDeInteres.ElementType tipo) {

        List<PuntoDeInteres> puntos = this.jvm.listarPuntosDeInteresDelMapa(tipo);

        GenericEntity<List<PuntoDeInteres>> entity = new GenericEntity<List<PuntoDeInteres>>(puntos) {};
        return Response.status(201).entity(entity).build();

    }

}
