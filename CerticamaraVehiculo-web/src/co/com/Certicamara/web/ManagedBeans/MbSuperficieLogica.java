package co.com.Certicamara.web.ManagedBeans;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import co.com.Certicamara.web.Enum.ConexionServiciosRest;

@ManagedBean(name = "MbSuperficieLogica")
@SessionScoped

/**
 * Clase que administra los componentes del JSF
 * @author Andres Julian Rincon
 *
 */
public class MbSuperficieLogica implements Serializable {
	private static final long serialVersionUID = 1L;

	private int PosicionM = 0;
	private int PosicionN = 0;
	private String Direcciones;
	private List<String> EjecucionDirecciones;
	private String PosicionVehiculo;
	private UIComponent Componente;

	/**
	 * 
	 * @return
	 */
	public int getPosicionM() {
		return PosicionM;
	}

	/**
	 * 
	 * @param posicionM
	 */
	public void setPosicionM(int posicionM) {
		PosicionM = posicionM;
	}

	/**
	 * 
	 * @return
	 */
	public int getPosicionN() {
		return PosicionN;
	}
	
	/**
	 * 
	 * @param posicionN
	 */
	public void setPosicionN(int posicionN) {
		PosicionN = posicionN;
	}

	/**
	 * 
	 * @return
	 */
	public String getDirecciones() {
		return Direcciones;
	}

	/**
	 * 
	 * @param direcciones
	 */
	public void setDirecciones(String direcciones) {
		Direcciones = direcciones;
	}

	/**
	 * 
	 * @return
	 */
	public List<String> getEjecucionDirecciones() {
		return EjecucionDirecciones;
	}
	
	/**
	 * 
	 * @param ejecucionDirecciones
	 */
	public void setEjecucionDirecciones(List<String> ejecucionDirecciones) {
		EjecucionDirecciones = ejecucionDirecciones;
	}
	/**
	 * 
	 * @return
	 */
	public String getPosicionVehiculo() {
		return PosicionVehiculo;
	}

	/**
	 * 
	 * @param posicionVehiculo
	 */
	public void setPosicionVehiculo(String posicionVehiculo) {
		PosicionVehiculo = posicionVehiculo;
	}

	/**
	 * 
	 * @return
	 */
	public UIComponent getComponente() {
		return Componente;
	}

	/**
	 * 
	 * @param componente
	 */
	public void setComponente(UIComponent componente) {
		Componente = componente;
	}
	
	/**
	 * Metodo para crear la superficie Logica
	 */
	public void CrearSuperficieLogica() {
		List<NameValuePair> Parametros = new ArrayList<>();
		FacesContext context = FacesContext.getCurrentInstance();
		if (this.PosicionM != 0 && this.PosicionN != 0) {
			Parametros.add(new BasicNameValuePair("x", String.valueOf(PosicionN)));
			Parametros.add(new BasicNameValuePair("y", String.valueOf(PosicionM)));
		}
		try {
			ConexionServiciosRest.POST.ResponseConexion(ConexionServiciosRest.POST,
					ConexionServiciosRest.CREAR_SUPERFICIE.getUrl(), Parametros, MediaType.APPLICATION_FORM_URLENCODED);
			context.addMessage(Componente.getClientId(), new FacesMessage("Se realizo la asignacion correctamente"));
		} catch (Exception e) {
			context.addMessage(Componente.getClientId(), new FacesMessage("Error al realizar la asignacion de la Superficie Logica"));
		}
	}

	/**
	 * Metodo para ingresar las direcciones de ubicacion 
	 */
	public void IngresarDirecciones() {
		Response Respuesta;
		List<NameValuePair> Parametros = new ArrayList<>();
		FacesContext context = FacesContext.getCurrentInstance();
		if (!Direcciones.equals("")) {
			Parametros.add(new BasicNameValuePair("Direcciones", Direcciones));
		}
		try {
			Respuesta = ConexionServiciosRest.POST.ResponseConexion(ConexionServiciosRest.POST,
					ConexionServiciosRest.ENVIAR_POSICIONES.getUrl(), Parametros,
					MediaType.APPLICATION_FORM_URLENCODED);
			EjecucionDirecciones = Respuesta.readEntity(new GenericType<List<String>>() {
			});
		} catch (Exception e) {
			context.addMessage(Componente.getClientId(), new FacesMessage("Error al realizar el envio de direccion "+e.getMessage()));
		}
	}

	/**
	 * Metodo para obtener la posicion del vehiculo
	 */
	public void PosicionVehiculo() {
		Response Respuesta;
		FacesContext context = FacesContext.getCurrentInstance();
		try {
			Respuesta = ConexionServiciosRest.GET.ResponseConexion(ConexionServiciosRest.GET,
					ConexionServiciosRest.OBTENER_POSICION_VEHICULO.getUrl(), null, MediaType.APPLICATION_JSON);
			this.PosicionVehiculo = Respuesta.readEntity(String.class);
		} catch (Exception e) {
			context.addMessage(Componente.getClientId(), new FacesMessage("Error al realizar traer la ubicacion del Vehiculo "+e.getMessage()));
		}
	}
}
