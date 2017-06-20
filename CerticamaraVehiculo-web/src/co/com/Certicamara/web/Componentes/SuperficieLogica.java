package co.com.Certicamara.web.Componentes;

import co.com.Certicamara.Utilitarios.ControlSuperficie;

public class SuperficieLogica extends ControlSuperficie{

	public SuperficieLogica(int x, int y) {
		super(x, y);
	}
	
	/**
	 * Metodo extendido para realizar la validacion del registro de movimientos en la Matriz Logica
	 * @param Posicion
	 * @param Cardinalidad
	 * @return
	 */
	public String RegistrarMovimientos(int Posicion, String Cardinalidad) {
		String Resultado=null;
		boolean Retorno = true;
		int ValidacionPosicion;
		if (Cardinalidad.toUpperCase().equals("N")) {
			ValidacionPosicion = (this.getSuperficie().getVehiculoRemoto().getPosicionX() + Posicion);
			if (ValidacionPosicion > this.getSuperficie().getLimiteX() - 1) {
				Resultado="La Posicion de esta direccion " + Posicion + Cardinalidad + " esta fuera del rango X";
				Retorno = false;
			}
		} else if (Cardinalidad.toUpperCase().equals("S")) {
			ValidacionPosicion = (this.getSuperficie().getVehiculoRemoto().getPosicionX() - Posicion);
			if (ValidacionPosicion < 0) {
				Resultado="La Posicion de esta direccion " + Posicion + Cardinalidad + " esta fuera del rango X";
				Retorno = false;
			}
		} else if (Cardinalidad.toUpperCase().equals("O")) {
			ValidacionPosicion = (this.getSuperficie().getVehiculoRemoto().getPosicionY() + Posicion);
			if (ValidacionPosicion > this.getSuperficie().getLimiteY() - 1) {
				Resultado="La Posicion de esta direccion " + Posicion + Cardinalidad + " esta fuera del rango Y";
				Retorno = false;
			}
		} else {
			ValidacionPosicion = (this.getSuperficie().getVehiculoRemoto().getPosicionY() - Posicion);
			if (ValidacionPosicion < 0) {
				Resultado="La Posicion de esta direccion " + Posicion + Cardinalidad + " esta fuera del rango Y";
				Retorno = false;
			}
		}
		if (Retorno) {
			Resultado = "El movimeinto " + Posicion + Cardinalidad + " se realizo correctamente";
			this.getSuperficie().getVehiculoRemoto().AsignaPosicionVehiculo(ValidacionPosicion, Cardinalidad);
		}
		return Resultado;
	}
}
