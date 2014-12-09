/*
 * Copyright (C) 2014 Xavier Naunay <xavierxc14@gmail.com>
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package ec.edu.epn.libres.ejemplofacade.enums;

/**
 *
 * @author Xavier Naunay <xavierxc14@gmail.com>
 */
public enum BDDEnum {

    /**
     * POSTGRES.
     */
    POSTGRES(1, "POSTGRES"),
    /**
     * DERBY.
     */
    DERBY(2, "DERBY");
    /**
     * Codigo de la enumeracion.
     */
    int codigo;
    /**
     * Codigo de la enumeracion.
     */
    private String descripcion;

    /**
     * constructor de clase con parametros.
     *
     * @param descripcion
     * @param codigo
     */
    private BDDEnum(int codigo, String descripcion) {
        this.descripcion = descripcion;
        this.codigo = codigo;
    }

    /**
     * Este metodo recupera la descripcion del parametro.
     *
     * @param codigo String
     * @return String
     */
    public static String obtenerDescripcion(final int codigo) {
        String des = "";
        for (final BDDEnum cre : values()) {
            if (codigo == cre.getCodigo()) {
                des = cre.getDescripcion();
                break;
            }
        }
        return des;
    }

    /**
     * @return the descripcion
     */
    public String getDescripcion() {
        return descripcion;
    }

    /**
     * @param descripcion the descripcion to set
     */
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    /**
     * @return the codigo
     */
    public int getCodigo() {
        return codigo;
    }

    /**
     * @param codigo the codigo to set
     */
    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }
}
