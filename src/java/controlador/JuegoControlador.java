
package controlador;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import modelo.*;


@ManagedBean(name="juegoControlador")
@SessionScoped
public class JuegoControlador implements Serializable{

    @ManagedProperty(value = "#{prplControlador}")
    private PrincipalControlador principalCtrl;
    
    private EntityManagerFactory emf;
    private boolean partidaActiva;
    private Arma armaJuego;
    private List<Arma> listaArmas;
    private List<Arma> listaIntentos;
    
    public JuegoControlador() {
    }
    
    @PostConstruct
    public void init(){
        emf = Persistence.createEntityManagerFactory("GundlePU");
        partidaActiva = false;
        
    }
    
    public Arma armaAleatoria(){
        if(listaArmas == null || listaArmas.isEmpty())
            return null;
        
        int numArmas = listaArmas.size();
        Arma arma = null;
        do{
            int id = (int) Math.floor(Math.random() * numArmas + 1);
            arma = armaPorId(id);
        }
        while(arma == null);
        return arma;
    }
    
    public List<Arma> listadoArmas(){
        EntityManager em = emf.createEntityManager();
        List<Arma> armas = new ArrayList<>();
        try{
            armas = em.createNamedQuery("Arma.findAll").getResultList();
        }
        catch(Exception e){
            e.printStackTrace();
        }
        finally{
            em.close();
        }
        return armas;
    }
    
    public Arma armaPorId(int id){
        EntityManager em = emf.createEntityManager();
        Arma arma = null;
        try{
            Query q = em.createNamedQuery("Arma.findById").setParameter("id", id);
            arma = (Arma) q.getSingleResult();
        }
        catch(Exception e){
            e.printStackTrace();
        }
        finally{
            em.close();
        }
        return arma;
    }
    
    // ----- GETTER / SETTER -----

    public boolean isPartidaActiva() {
        return partidaActiva;
    }

    public void setPartidaActiva(boolean partidaActiva) {
        this.partidaActiva = partidaActiva;
    }

    public Arma getArmaJuego() {
        return armaJuego;
    }

    public void setArmaJuego(Arma armaJuego) {
        this.armaJuego = armaJuego;
    }

    public List<Arma> getListaArmas() {
        return listaArmas;
    }

    public void setListaArmas(List<Arma> listaArmas) {
        this.listaArmas = listaArmas;
    }

    public List<Arma> getListaIntentos() {
        return listaIntentos;
    }

    public void setListaIntentos(List<Arma> listaIntentos) {
        this.listaIntentos = listaIntentos;
    }
    
    
    
}
