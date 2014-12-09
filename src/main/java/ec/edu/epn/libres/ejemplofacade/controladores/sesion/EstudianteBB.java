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
package ec.edu.epn.libres.ejemplofacade.controladores.sesion;

import ec.edu.epn.libres.ejemplofacade.persistencia.entidades.Estudiante;

import java.io.Serializable;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

/**
 *
 * @author Xavier Naunay <xavierxc14@gmail.com>
 */
@ManagedBean(name = "estudianteBB")
@SessionScoped
public class EstudianteBB implements Serializable {

    private List<Estudiante> items = null;
    private Estudiante selected;

    public EstudianteBB() {
    }

    public Estudiante getSelected() {
        return selected;
    }

    public void setSelected(Estudiante selected) {
        this.selected = selected;
    }

    public List<Estudiante> getItems() {
        return items;
    }

    public void setItems(List<Estudiante> items) {
        this.items = items;
    }
    
}
