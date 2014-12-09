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
package ec.edu.epn.libres.ejemplofacade.controladores.vista;

import ec.edu.epn.libres.ejemplofacade.enums.BDDEnum;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.model.SelectItem;

/**
 *
 * @author Xavier Naunay <xavierxc14@gmail.com>
 */
@ManagedBean(name = "inicioBean")
@SessionScoped
public class InicioControlador implements Serializable {

    private List<SelectItem> base;
    public static int seleccion;

    public InicioControlador() {
    }

    @PostConstruct
    public void init() {
        setBase(new ArrayList<SelectItem>());
        buscarBasesDisponibles();
    }

    public String buscarBasesDisponibles() {
        getBase().clear();
        getBase().add(new SelectItem(0, "Seleccione..."));
        for (BDDEnum bdd : BDDEnum.values()) {
            getBase().add(new SelectItem(bdd.getCodigo(), bdd.getDescripcion()));
        }
        return null;
    }

    public String imprimirSeleccion() {
        System.out.println(seleccion);
        return null;
    }

    /**
     * @return the base
     */
    public List<SelectItem> getBase() {
        return base;
    }

    /**
     * @param base the base to set
     */
    public void setBase(List<SelectItem> base) {
        this.base = base;
    }

    /**
     * @return the seleccion
     */
    public int getSeleccion() {
        return seleccion;
    }

    /**
     * @param seleccion the seleccion to set
     */
    public void setSeleccion(int seleccion) {
        InicioControlador.seleccion = seleccion;
    }
}
