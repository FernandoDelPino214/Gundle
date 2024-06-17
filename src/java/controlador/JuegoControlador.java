
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
    private List<Arma> listaSeleccArma;
    private List<Arma> listaIntentos;
    private String campoFiltro;
    private boolean finalizado;
    private boolean victoria;
    
    
    private final String CORREC = "celdaCorrecto";
    private final String INCORREC = "celdaIncorrecto";
    private final String MAYOR = "celdaMayor";
    private final String MENOR = "celdaMenor";
    private final String PARCI = "celdaParcial";
    
    public JuegoControlador() {
    }
    
    @PostConstruct
    public void init(){
        emf = Persistence.createEntityManagerFactory("GundlePU");
        partidaActiva = false;
        finalizado = false;
        listaArmas = listadoArmas();
        listaSeleccArma = new ArrayList(listaArmas);
    }
    
    
    
    public void iniciarPartida(){
        listaArmas = listadoArmas();
        partidaActiva = true;
        listaIntentos = new ArrayList();
        armaJuego = armaAleatoria();
        finalizado = false;
        victoria = false;
    }
    
    public void adivinar(Arma arma){
        if(listaIntentos.contains(arma))
            return;
        
        listaIntentos.add(arma);
        if(arma.equals(armaJuego)){
            finalizado = true;
            victoria = true;

        }
    }
    
    public void rendirse(){
        finalizado = true;
        victoria = false;
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
    
    public void filtrarArmas(){
        EntityManager em = emf.createEntityManager();
        List<Arma> armas = new ArrayList<>();
        try{
            if(campoFiltro != null && !campoFiltro.equals("")){
                Query q = em.createQuery("SELECT a FROM Arma a WHERE LOWER(a.nombre) LIKE LOWER(:nombre)").setParameter("nombre", "%"+campoFiltro+"%");
                armas = q.getResultList();
                listaSeleccArma = armas;
            }
            else{
                listaSeleccArma = listadoArmas();
            }
            
        }
        catch(Exception e){
            e.printStackTrace();
        }
        finally{
            em.close();
        }
    }
    
    public String estiloCalidad(Character valor){
        // compareTo devuelve 0 si el carácter es el mismo
        if(valor.compareTo(armaJuego.getCalidad()) == 0)
            return CORREC;
        else
            return INCORREC;
    }
    
    public String estiloTipo(TipoArma valor){
        if(valor.equals(armaJuego.getTipo()))
            return CORREC;
        else
            return INCORREC;
    }
    
    public String estiloClase(ClaseArma valor){
        if(valor.equals(armaJuego.getClase()))
            return CORREC;
        else
            return INCORREC;
    }
    
    public String estiloCargador(Integer valor){
        if(valor == null)
            return (armaJuego.getCargador() == null)? CORREC : INCORREC;
        
        // si no tiene cargador devuelve siempre menor, a no ser que se compare con otro arma sin cargador
        if(armaJuego.getCargador() == null){
            return MENOR;
        }
        
        if(armaJuego.getCargador().intValue() == valor.intValue())
            return CORREC;
        
        return (armaJuego.getCargador() > valor)? MAYOR : MENOR;
    }
    
    public String estiloMunicion(Integer valor){
        if(valor == null)
            return (armaJuego.getMunicion()== null)? CORREC : INCORREC;
        
        // si munición infinita devuelve siempre mayor,
        // a no ser que se compare con otro arma con munición infinita
        if(armaJuego.getMunicion()== null){
            return MAYOR;
        }
        
        if(armaJuego.getMunicion().intValue() == valor.intValue())
            return CORREC;
        
        return (armaJuego.getMunicion()> valor)? MAYOR : MENOR;
    }
    
    public String estiloProyectiles(Integer valor){
        if(valor == null){
            return (armaJuego.getProyectiles()== null)? CORREC : INCORREC;
        }  
        
        if(armaJuego.getProyectiles()== null){
            return INCORREC;
        }
        
        if(armaJuego.getProyectiles().intValue() == valor.intValue())
            return CORREC;
        
        return (armaJuego.getProyectiles()> valor)? MAYOR : MENOR;
    }
    
    public String estiloColores(List<Color> colores){
        if(colores == null || colores.isEmpty()){
            if(armaJuego.getColorList() == null || armaJuego.getColorList().isEmpty())
                return CORREC;
            else
                return INCORREC;
        }
        
        boolean perfecto = true;
        boolean parcial = false;
        
        for(Color c : colores){
            if(armaJuego.getColorList().contains(c))
                parcial = true;
            else
                perfecto = false;
        }
        
        if(perfecto && colores.size() == armaJuego.getColorList().size())
            return CORREC;
        else
            return (parcial)? PARCI : INCORREC;
        
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

    public String getCampoFiltro() {
        return campoFiltro;
    }

    public void setCampoFiltro(String campoFiltro) {
        this.campoFiltro = campoFiltro;
    }

    public List<Arma> getListaSeleccArma() {
        return listaSeleccArma;
    }

    public void setListaSeleccArma(List<Arma> listaSeleccArma) {
        this.listaSeleccArma = listaSeleccArma;
    }

    public PrincipalControlador getPrincipalCtrl() {
        return principalCtrl;
    }

    public void setPrincipalCtrl(PrincipalControlador principalCtrl) {
        this.principalCtrl = principalCtrl;
    }

    public boolean isFinalizado() {
        return finalizado;
    }

    public void setFinalizado(boolean finalizado) {
        this.finalizado = finalizado;
    }

    public boolean isVictoria() {
        return victoria;
    }

    public void setVictoria(boolean victoria) {
        this.victoria = victoria;
    }
    
    
    
}
