package unipesquisas.diversos;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import unipesquisas.model.entity.Curso;

@FacesConverter("cursoConverter")
public class CursoConverter implements Converter {
	@Override
	public Object getAsObject(FacesContext context, UIComponent component, String value) {
		Curso curso = new Curso();
		curso.setIdcurso(Integer.parseInt(value));
		return curso;
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component, Object value) {
		Curso curso = (Curso) value;
		return curso.getIdcurso().toString();
	}
}
