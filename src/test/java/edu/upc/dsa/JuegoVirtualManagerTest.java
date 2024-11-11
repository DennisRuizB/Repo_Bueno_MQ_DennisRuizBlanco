package edu.upc.dsa;

import edu.upc.dsa.models.Usuario;
import edu.upc.dsa.models.PuntoDeInteres;
import edu.upc.dsa.models.RegistroPuntoDeInteres;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.List;
public class JuegoVirtualManagerTest {
    JuegoVirtualManager jvm;

    @Before
    public void setUp() {
        this.jvm = JuegoVirtualManagerImpl.getInstance();
        this.jvm.addUsuario("1","Dennis","Ruiz", "d@gmail.com", "18/10/2004");
        this.jvm.addUsuario("2","Alba","A", "2/2/2002", "2/2/2002");
        this.jvm.addUsuario("3","Marina","Mar", "3/3/2003", "3/3/2003");
        this.jvm.addPuntoDeInteres(1, 1, PuntoDeInteres.ElementType.COIN);
        this.jvm.addPuntoDeInteres(2, 2, PuntoDeInteres.ElementType.BRIDGE);
        this.jvm.addPuntoDeInteres(3, 3, PuntoDeInteres.ElementType.COIN);

    }

    @After
    public void tearDown() {
        // És un Singleton
        this.jvm.clear();
    }

    @Test
    public void addUsuarioTest() {
        Assert.assertEquals(3, jvm.sizeU());
        this.jvm.addUsuario("4", "Pau", "Ruiz", "d@gmail.com", "22/10/2004");
        Assert.assertEquals(4, jvm.sizeU());
    }

    @Test
    public void addPuntoDeInteresTest() {
        Assert.assertEquals(3, jvm.sizeP());
        this.jvm.addPuntoDeInteres(4, 4, PuntoDeInteres.ElementType.COIN);
        Assert.assertEquals(4, jvm.sizeP());
    }

    @Test
    public void addRegistroPuntoDeInteresTest() {
        Assert.assertEquals(0, jvm.sizeR());
        this.jvm.addRegistroPuntoDeInteres("1", 1, 1);
        Assert.assertEquals(1, jvm.sizeR());
    }

    @Test
    public void listarUsuariosPorOrdenAlfabeticoTest() {
        List<Usuario> usuarios = jvm.listarUsuariosPorOrdenAlfabetico();
        Assert.assertEquals("A", usuarios.get(0).getApellidos());
        Assert.assertEquals("Mar", usuarios.get(1).getApellidos());
        Assert.assertEquals("Ruiz", usuarios.get(2).getApellidos());
    }

    @Test
    public void getUsuarioTest() {
        Usuario u = jvm.getUsuario("1");
        Assert.assertEquals("Dennis", u.getNombre());
        Assert.assertEquals("Ruiz", u.getApellidos());
    }

    @Test
    public void listarRegistrosPorOrdenDePasadaTest() {
        jvm.addRegistroPuntoDeInteres("1", 1, 1);
        jvm.addRegistroPuntoDeInteres("1", 2, 2);
        jvm.addRegistroPuntoDeInteres("1", 3, 3);
        List<RegistroPuntoDeInteres> registros = jvm.listarRegistrosPorOrdenDePasada("1");
        Assert.assertEquals(3, registros.size());
    }

    @Test
    public void listarUsuariosPasadosPorUnPuntoInteresTest() {
        jvm.addRegistroPuntoDeInteres("1", 1, 1);
        jvm.addRegistroPuntoDeInteres("2", 1, 1);
        jvm.addRegistroPuntoDeInteres("3", 1, 1);
        List<Usuario> usuarios = jvm.listarUsuariosPasadosPorUnPuntoInteres(1, 1);
        Assert.assertEquals(3, usuarios.size());
    }

    @Test
    public void listarPuntosDeInteresDelMapaTest() {
        List<PuntoDeInteres> puntos = jvm.listarPuntosDeInteresDelMapa(PuntoDeInteres.ElementType.COIN);
        Assert.assertEquals(2, puntos.size());
    }


}
