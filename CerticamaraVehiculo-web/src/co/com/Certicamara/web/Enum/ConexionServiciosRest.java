package co.com.Certicamara.web.Enum;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Form;
import javax.ws.rs.core.Response;

import org.apache.http.NameValuePair;

public enum ConexionServiciosRest {
	URL, CREAR_SUPERFICIE, OBTENER_POSICION_VEHICULO, ENVIAR_POSICIONES, POST, GET;

	private final static String Url = "http://localhost:8081/CerticamaraVehiculo-web/services/RestSuperficieLogica/";

	/**
	 * Obtiene la URL de conexion del servicio
	 * @return
	 * @throws IOException
	 * @throws URISyntaxException
	 */
	public URI getUrl() throws IOException, URISyntaxException {
		switch (this) {
		case CREAR_SUPERFICIE:
			return new URI(Url + "CrearSuperficie");
		case OBTENER_POSICION_VEHICULO:
			return new URI(Url + "ObtenerPosicionVehiculo");
		case ENVIAR_POSICIONES:
			return new URI(Url + "EnviarComandos");
		default:
			throw new AssertionError("Enumerador no definido");
		}
	}
	
	/**
	 * Metodo para obtener la respuesta de la invocacion del servicio REST 
	 * @param Metodo
	 * @param Url
	 * @param Parametros
	 * @param Tipo
	 * @return
	 * @throws IOException
	 */
	public Response ResponseConexion(ConexionServiciosRest Metodo, URI Url, List<NameValuePair> Parametros, String Tipo) throws IOException {
		Client Cliente = ClientBuilder.newClient();
		WebTarget Contenedor = Cliente.target(Url);
		Form FormularioRequest = null;
		if (Parametros instanceof Object) {
			FormularioRequest = new Form();
			for (NameValuePair Parametro : Parametros) {
				FormularioRequest.param(Parametro.getName(), Parametro.getValue());
			}
			
		}
		switch (Metodo) {
		case POST:
			return Contenedor.request().post(Entity.entity(FormularioRequest, Tipo));
		case GET:
			return (Response) Contenedor.request(Tipo).get();
		default:
			throw new AssertionError("Enumerador no definido");
		}
	}
}
