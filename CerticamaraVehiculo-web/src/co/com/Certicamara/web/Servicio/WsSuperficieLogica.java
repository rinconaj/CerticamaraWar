package co.com.Certicamara.web.Servicio;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import co.com.Certicamara.Utilitarios.ValidadorDatos;
import co.com.Certicamara.web.Componentes.SuperficieLogica;

@Path ("/RestSuperficieLogica")
public class WsSuperficieLogica {
	/**
	 * Se realiza la asignacion estatica dado que es para una superficie logica en caso de concurrencia
	 */
	static SuperficieLogica SuperficieLogica;
	
	/**
	 * Servicio para realizar la creacion de la superficie logica
	 * @param x
	 * @param y
	 * @throws Exception
	 */
	@POST
	@Path("/CrearSuperficie")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.APPLICATION_JSON)
	public void CrearSuperficie(@FormParam("x") int x,@FormParam("y") int y) throws Exception{
		SuperficieLogica = new SuperficieLogica(x, y);
		System.out.println("Se creo la superficie "+SuperficieLogica.getClass()+" con las Posiciones "+x+","+y);
	}
	
	/**
	 * Servicio para enviar los comandos de direccion los cuales realiza las validaciones en el Core
	 * @param Direcciones
	 * @return
	 */
	@POST
	@Path("/EnviarComandos")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.APPLICATION_JSON)
	public List<String> EnviarComandos(@FormParam("Direcciones") String Direcciones){
		List<String> ResultadoDirecciones = new ArrayList<>();
		for (String Direccion : ValidadorDatos.SegmentarDirecciones(Direcciones)) {
			if (ValidadorDatos.ValidarDirecciones(Direccion)){
				ResultadoDirecciones.add(SuperficieLogica.RegistrarMovimientos(ValidadorDatos.RetornarNumeros(Direccion), ValidadorDatos.RetornaCaracteres(Direccion)));
			}else {
				ResultadoDirecciones.add("La Direccion "+Direccion+" no tiene la codificacion adecuada (debe ser N,S,E,O)");
			}
		}
		return ResultadoDirecciones;
	}
	
	/**
	 * Servicio para obtener la posicion del Vehiculo
	 * @return
	 */
	@GET
	@Path("/ObtenerPosicionVehiculo")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public String ObtenerPosicionVehiculo(){
		return "La posicion del Vehiculo es ("+SuperficieLogica.getSuperficie().getVehiculoRemoto().getPosicionX()+","+SuperficieLogica.getSuperficie().getVehiculoRemoto().getPosicionY()+")";
	}

}
